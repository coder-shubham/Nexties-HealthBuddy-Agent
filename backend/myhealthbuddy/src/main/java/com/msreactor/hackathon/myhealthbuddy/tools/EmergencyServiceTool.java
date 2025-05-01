/*
 * Copyright (c) 2025 Nextiva, Inc. to Present.
 * All rights reserved.
 */

package com.msreactor.hackathon.myhealthbuddy.tools;

import org.springframework.stereotype.Component;

import com.msreactor.hackathon.myhealthbuddy.model.EmergencyService;

import dev.langchain4j.agent.tool.Tool;

/**
 * Class Description goes here.
 * Created by shubhamsarraf on 30/04/25
 */

@Component
public class EmergencyServiceTool {


    @Tool(
            name = "book_ambulance",
            value = "Book an ambulance for a patient. "
                    + "Input should be in the format: 'patientId: <patient_id>, patientSymptomSummaryId: <patientSymptomSummaryId>, location: <location>'"
                    + "Output will be the details of the booked ambulance service."
    )
    public EmergencyService bookAmbulance(Integer patientId, Integer patientSymptomSummaryId, String location) {
        // Logic to book an ambulance
        // This is a placeholder implementation
        System.out.println("Booking ambulance for patient ID: " + patientId + " at location: " + location +
                " with symptom summary ID: " + patientSymptomSummaryId);

        return new EmergencyService(
                1,
                "Ambulance Service",
                location,
                "123-456-7890",
                "Ambulance",
                "Emergency ambulance service"
        );

    }
}
