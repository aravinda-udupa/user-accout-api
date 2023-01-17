package com.udupa.useraccoutapi;

import java.util.HashMap;
import java.util.Map;

public class Utils {

    public static final double MONTHLY_CREDIT = 1000.00;
    public static Map<String, Double> ACCOUNT_TYPE_TO_INTEREST_RATE_MAP = new HashMap<>();
    static {
        prepareAccountTypeToInterestRateMap();
    }

    private static void prepareAccountTypeToInterestRateMap() {
        ACCOUNT_TYPE_TO_INTEREST_RATE_MAP.put("SAVINGS", 4.5);
        ACCOUNT_TYPE_TO_INTEREST_RATE_MAP.put("CURRENT", 3.5);
        ACCOUNT_TYPE_TO_INTEREST_RATE_MAP.put("LOAN", 8.5);
    }
}
