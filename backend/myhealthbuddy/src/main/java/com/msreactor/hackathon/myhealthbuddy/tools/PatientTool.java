/*
 * Copyright (c) 2025 Nextiva, Inc. to Present.
 * All rights reserved.
 */

package com.msreactor.hackathon.myhealthbuddy.tools;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Component;

import com.msreactor.hackathon.myhealthbuddy.model.Patient;
import com.msreactor.hackathon.myhealthbuddy.model.PatientSymptomsSummary;
import com.msreactor.hackathon.myhealthbuddy.model.SymptomCategory;

import dev.langchain4j.agent.tool.Tool;

/**
 * Class Description goes here.
 * Created by shubhamsarraf on 30/04/25
 */

@Component
public class PatientTool {

    // A static list of patients to simulate a database
    public List<Patient> patients = new ArrayList<>(List.of(
            new Patient(1, "John Doe", "30-40", "New York", "15551234"),
            new Patient(2, "Emily Green", "20-30", "California", "15555678"),
            new Patient(3, "David Smith", "40-50", "Texas", "15559876"),
            new Patient(4, "Sophia Johnson", "50-60", "Florida", "15556543"),
            new Patient(5, "Michael Lee", "30-40", "Illinois", "15558765"),
            new Patient(6, "Olivia Brown", "20-30", "Washington", "15554321"),
            new Patient(7, "Liam White", "40-50", "Nevada", "15551357"),
            new Patient(8, "Mia Davis", "30-40", "Ohio", "15552468"),
            new Patient(9, "Ethan Wilson", "50-60", "Georgia", "15553690"),
            new Patient(10, "Isabella Martinez", "20-30", "Arizona", "15554567"),
            new Patient(11, "Lucas Anderson", "30-40", "Colorado", "15558901"),
            new Patient(12, "Charlotte Taylor", "40-50", "Michigan", "15551122"),
            new Patient(13, "James Harris", "50-60", "North Carolina", "15553344"),
            new Patient(14, "Amelia Moore", "20-30", "South Carolina", "15555566"),
            new Patient(15, "Benjamin King", "30-40", "Pennsylvania", "15557788"),
            new Patient(16, "Harper Clark", "40-50", "Maryland", "15559900"),
            new Patient(17, "William Robinson", "50-60", "New Jersey", "15552233"),
            new Patient(18, "Avery Lewis", "30-40", "Virginia", "15554455"),
            new Patient(19, "Ella Walker", "20-30", "Tennessee", "15556677"),
            new Patient(20, "Jack Allen", "40-50", "Massachusetts", "15558899")
    ));

    public List<PatientSymptomsSummary> symptomSummaries = new ArrayList<>(List.of(
            new PatientSymptomsSummary(
                    1,
                    new String[]{"Fever", "Cough"},
                    "2025-04-29T08:00:00",
                    SymptomCategory.BASIC,
                    new String[]{"Paracetamol"},
                    new String[]{"None"},
                    new String[]{"Seasonal Flu"}
            ),
            new PatientSymptomsSummary(
                    2,
                    new String[]{"Headache", "Nausea"},
                    "2025-04-30T09:30:00",
                    SymptomCategory.MODERATE,
                    new String[]{"Ibuprofen"},
                    new String[]{"Penicillin"},
                    new String[]{"Migraine"}
            ),
            new PatientSymptomsSummary(
                    3,
                    new String[]{"Skin rash"},
                    "2025-04-28T16:00:00",
                    SymptomCategory.BASIC,
                    new String[]{"Antihistamine"},
                    new String[]{"Dust", "Pollen"},
                    new String[]{"Eczema"}
            ),
            new PatientSymptomsSummary(
                    4,
                    new String[]{"Sneezing", "Runny nose"},
                    "2025-04-27T10:15:00",
                    SymptomCategory.BASIC,
                    new String[]{"Cetirizine"},
                    new String[]{"Peanuts"},
                    new String[]{"Allergic Rhinitis"}
            ),
            new PatientSymptomsSummary(
                    5,
                    new String[]{"Chest pain", "Shortness of breath"},
                    "2025-04-30T22:45:00",
                    SymptomCategory.CRITICAL,
                    new String[]{"Aspirin"},
                    new String[]{"None"},
                    new String[]{"Hypertension"}
            ),
            new PatientSymptomsSummary(
                    6,
                    new String[]{"Itching", "Red patches"},
                    "2025-04-25T14:00:00",
                    SymptomCategory.BASIC,
                    new String[]{"Hydrocortisone cream"},
                    new String[]{"Nickel"},
                    new String[]{"Psoriasis"}
            ),
            new PatientSymptomsSummary(
                    7,
                    new String[]{"Ear pain"},
                    "2025-04-26T18:00:00",
                    SymptomCategory.BASIC,
                    new String[]{"Amoxicillin"},
                    new String[]{"None"},
                    new String[]{"Otitis media"}
            ),
            new PatientSymptomsSummary(
                    8,
                    new String[]{"Sore throat", "Mild fever"},
                    "2025-04-30T07:30:00",
                    SymptomCategory.BASIC,
                    new String[]{"Paracetamol", "Salt water gargle"},
                    new String[]{"None"},
                    new String[]{"Tonsillitis"}
            ),
            new PatientSymptomsSummary(
                    9,
                    new String[]{"Back pain"},
                    "2025-04-29T11:00:00",
                    SymptomCategory.MODERATE,
                    new String[]{"Ibuprofen", "Muscle relaxants"},
                    new String[]{"None"},
                    new String[]{"Muscle strain"}
            ),
            new PatientSymptomsSummary(
                    10,
                    new String[]{"Abdominal pain", "Bloating"},
                    "2025-04-30T15:20:00",
                    SymptomCategory.MODERATE,
                    new String[]{"Antacids"},
                    new String[]{"Lactose"},
                    new String[]{"IBS"}
            )
    ));


    @Tool(
            name = "CreatePatientTool",
            value = "This tool is used to create a new patient. "
                    + "Input should be in the format: 'name: <name>, ageGroup: <age_group>, location: <location>, mobileNumber: <mobile_number>'"
    )
    public Patient createPatient(String name, String ageGroup, String location, String mobileNumber) {
        Integer newId = patients.size() + 1;
        Patient newPatient = new Patient(newId, name, ageGroup, location, mobileNumber);
        patients.add(newPatient); // Adding to the list (simulating database insertion)
        System.out.println("Creating patient with name: " + name + ", age group: " + ageGroup + ", location: " + location);
        return newPatient;
    }

    @Tool(
            name = "PatientTool",
            value = "This tool is used to get patient information. To get information either patientId or "
                    + "patient mobileNumber is required. "
                    + "Input should be in the format: 'patientId: <patient_id>', or 'mobileNumber: <mobile_number>'"
    )
    public Patient getPatientDetails(Integer patientId, String mobileNumber) {

        for (Patient patient : patients) {
            if (patientId != null && Objects.equals(patient.id(), patientId)) {
                System.out.println("Fetching details for patient with ID: " + patientId);
                return patient;
            }
            if (mobileNumber != null && patient.mobileNumber().equals(mobileNumber)) {
                System.out.println("Fetching details for patient with mobile number: " + mobileNumber);
                return patient;
            }
        }
        return null;
    }

    @Tool(
            name = "UpdatePatientTool",
            value = "This tool is used to update patient information. "
                    + "Input should be in the format: 'patientId: <patient_id>, name: <name>, ageGroup: <age_group>, location: <location>'"
    )
    public Patient updatePatient(Integer patientId, String name, String ageGroup, String location) {

        for (Patient patient : patients) {
            if (Objects.equals(patient.id(), patientId)) {
                Patient newPatient = new Patient(patient.id(), name, ageGroup, location, patient.mobileNumber());
                System.out.println("Updating patient with ID: " + patientId + ", new name: " + name + ", new age group: " + ageGroup + ", new location: " + location);
                return newPatient;
            }
        }
        return null; // Return null if no patient found with the given ID
    }

    @Tool(
            name = "GetPatientSymptomsHistoryTool",
            value = "This tool is used to get the symptoms history of a patient. "
                    + "Input should be in the format: 'patientId: <patient_id>'"
    )
    public List<PatientSymptomsSummary> getPatientSymptomsHistory(Integer patientId) {

        System.out.println("Fetching history for patient with ID: " + patientId);
        return Collections.singletonList(new PatientSymptomsSummary(patientId,
                new String[] {"Fever", "Cough"},
                "2025-04-28T10:00:00Z",
                SymptomCategory.BASIC, null, null, null));


    }

    @Tool(
            name = "CreatePatientSymptomsSummaryTool",
            value = "This tool is used to create a new patient symptoms summary. "
                    + "Input should be in the format: 'patientId: <patient_id>, symptoms: <symptoms>,"
                    + " timestamp: <timestamp>, category: <category>,"
                    + " optional medications: <medications>,optional allergies: <allergies>,"
                    + "optional medicalHistory: <medical_history>'"
    )
    public PatientSymptomsSummary createPatientSymptomsSummary(Integer patientId, String[] symptoms,
                                                               String timestamp, String category,
                                                               String[] medications, String[] allergies, String[] medicalHistory) {

        SymptomCategory symptomCategory = SymptomCategory.valueOf(category.toUpperCase());

        System.out.println("Creating symptoms summary for patient with ID: " + patientId);
        PatientSymptomsSummary symptomsSummary = new PatientSymptomsSummary(patientId, symptoms, timestamp, symptomCategory,
                medications, allergies, medicalHistory);
        symptomSummaries.add(symptomsSummary);
        return symptomsSummary;
    }

}
