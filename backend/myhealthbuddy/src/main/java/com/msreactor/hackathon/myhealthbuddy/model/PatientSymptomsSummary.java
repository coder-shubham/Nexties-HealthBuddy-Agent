/*
 * Copyright (c) 2025 Nextiva, Inc. to Present.
 * All rights reserved.
 */

package com.msreactor.hackathon.myhealthbuddy.model;

/**
 * Class Description goes here.
 * Created by shubhamsarraf on 30/04/25
 */
public record PatientSymptomsSummary(Integer patientId, String[] symptoms, String symptomStartDateAndTime,
                                     SymptomCategory symptomCategory, String[] medications,
                                     String[] allergies, String[] medicalHistory) {
}
