/*
 * Copyright (c) 2025 Nextiva, Inc. to Present.
 * All rights reserved.
 */

package com.msreactor.hackathon.myhealthbuddy.model;

/**
 * Class Description goes here.
 * Created by shubhamsarraf on 30/04/25
 */
public record Booking(Integer id, Patient patientDetail, PatientSymptomsSummary patientSymptomsSummary,
                      Doctor doctorDetail,  String appointmentDateAndTime,
                      Boolean teleConsultation, String location, String teleConsultationUrl, String appointmentStatus) {
}
