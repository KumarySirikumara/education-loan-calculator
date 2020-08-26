package com.example.educationloancalculator.Database;

import android.provider.BaseColumns;

public final class HistoryMaster {
    private HistoryMaster(){}

    public static class Calculations implements BaseColumns{
        public static final String TABLE_NAME = "calculations";
        public static final String COLUMN_NAME_LOAN_AMOUNT = "loan_amount";
        public static final String COLUMN_NAME_INTEREST_RATE = "interest_rate";
        public static final String COLUMN_NAME_RATE_PER = "rate_per";
        public static final String COLUMN_NAME_LOAN_TERM = "loan_term";
        public static final String COLUMN_NAME_TERM_PERIOD = "term_period";
        public static final String COLUMN_NAME_SYSTEM_DATE = "system_date";
    }
}
