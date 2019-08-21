package com.bigohealth.utils;

import com.bigohealth.BuildConfig;

import java.util.ArrayList;
import java.util.Arrays;

public class Constant {
    public static final String INTENT_EXTRA_SPECIALIZATION = BuildConfig.APPLICATION_ID + ".specialization";

    public static final ArrayList<String> specializations = new ArrayList<>(
            Arrays.asList(
                    "Dermatologist",
                    "Gynaecology",
                    "Paediatrician",
                    "General Medicine",
                    "Dietician",
                    "Dentist",
                    "Others"
            )
    );
}
