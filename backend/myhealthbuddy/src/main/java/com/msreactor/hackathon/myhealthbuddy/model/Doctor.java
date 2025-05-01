/*
 * Copyright (c) 2025 Nextiva, Inc. to Present.
 * All rights reserved.
 */

package com.msreactor.hackathon.myhealthbuddy.model;

import java.util.List;

/**
 * Class Description goes here.
 * Created by shubhamsarraf on 30/04/25
 */
public record Doctor(Integer id, String name, String specialist, String location,
                     List<String> slots) {
}
