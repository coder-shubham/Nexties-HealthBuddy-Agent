/*
 * Copyright (c) 2025 Nextiva, Inc. to Present.
 * All rights reserved.
 */

package com.msreactor.hackathon.myhealthbuddy.tools;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.msreactor.hackathon.myhealthbuddy.model.Booking;
import com.msreactor.hackathon.myhealthbuddy.model.Doctor;
import com.msreactor.hackathon.myhealthbuddy.model.Patient;
import com.msreactor.hackathon.myhealthbuddy.model.PatientSymptomsSummary;

import dev.langchain4j.agent.tool.Tool;

/**
 * Class Description goes here.
 * Created by shubhamsarraf on 30/04/25
 */

@Component
public class DoctorTool {


    @Autowired
    private PatientTool patientTool;

    @Tool(
            name = "GetSpecialistAvailabilityTool",
            value = "Get the availability of a doctor based on the specialist. "
                    + "Input should be in the format: 'specialist: <specialist_name>' and optional 'date: <date>'"
    )
    public List<Doctor> getSpecialistAvailability(String specialist, String date) {

        System.out.println("Getting availability for specialist: " + specialist);

        return loadDoctorData().stream()
                .filter(d -> d.specialist().equalsIgnoreCase(specialist))
                .filter(d -> hasMatchingSlot(d.slots(), date))
                .limit(3)
                .collect(Collectors.toList());
//        return List.of(
//                new Doctor(1, "Dr. John Doe", "Cardiologist",
//                        "New York", List.of("2025-05-01T10:00:00", "2025-05-01T11:00:00")),
//                new Doctor(2, "Dr. Jane Smith", "Dermatologist", "Los Angeles",
//                        List.of("2025-05-01T12:00:00", "2025-05-01T13:00:00"))
//        );
    }


    @Tool(
            name = "GetSpecificDoctorAvailabilityTool",
            value = "Get the availability of a specific doctor. "
                    + "Input should be in the format: 'doctor: <doctor_name>' and optional 'date: <date>'"
    )
    public Doctor getSpecificDoctorAvailability(String doctorName, String date) {

        System.out.println("Getting specialist for doctor: " + doctorName);
        return loadDoctorData().stream()
                .filter(d -> d.name().equalsIgnoreCase(doctorName))
                .filter(d -> hasMatchingSlot(d.slots(), date))
                .findFirst()
                .orElse(null);
    }


    @Tool(
            name = "BookAppointmentTool",
            value = "Book an appointment with a doctor. "
                    + "Input should be in the format: 'doctorId: <doctor_id>, patientId: <patientId>, dateTime: <date_time> IN ISO Format'"
    )
    public Booking bookAppointment(Integer doctorId, Integer patientId, String dateTime) {

        System.out.println("Booking appointment with doctor ID: " + doctorId +
                ", patient ID: " + patientId +
                ", on date and time: " + dateTime);

        Patient patient = patientTool.getPatientDetails(patientId, null);
        if (patient == null) {
            System.out.println("Patient not found with ID: " + patientId);
            return null;
        }

        Doctor doctor = loadDoctorData().stream()
                .filter(d -> d.id() == doctorId)
                .findFirst()
                .orElse(null);

        if (doctor != null) {
            List<PatientSymptomsSummary> patientSymptomsSummary = patientTool.getPatientSymptomsHistory(patientId);
            if (patientSymptomsSummary != null && !patientSymptomsSummary.isEmpty()) {
                PatientSymptomsSummary latestHistory = patientSymptomsSummary.get(patientSymptomsSummary.size() - 1);
                if (latestHistory != null) {
                    return new Booking(1, patient, latestHistory, doctor, dateTime,
                            false, doctor.location(), null, "Booked");
                }

            }

        }

        return null;
    }

    private List<Doctor> loadDoctorData() {
        ObjectMapper mapper = new ObjectMapper();
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("doctors_availability_data.json")) {
            if (is == null) {
                System.err.println("Could not find doctors_availability_data.json in resources folder.");
                return Collections.emptyList();
            }
            return Arrays.asList(mapper.readValue(is, Doctor[].class));
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    private boolean hasMatchingSlot(List<String> slots, String date) {
        if (date == null || date.isEmpty()) return true; // No filter applied
        return slots.stream().anyMatch(slot -> slot.startsWith(date));
    }

}
