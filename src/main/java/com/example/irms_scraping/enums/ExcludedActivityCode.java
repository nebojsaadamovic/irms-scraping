package com.example.irms_scraping.enums;

public enum ExcludedActivityCode {

    PROGRAMIRANJE("6201"),
    IT_KONSULTING("6202"),
    ODRZAVANJE_SISTEMA("6203"),
    OSTALO_IT("6209"),
    HOSTING("6311"),
    WEB_PORTALI("6312"),
    RAČUNARSKO_PROGRAMIRANJE("6210");

    private final String code;

    ExcludedActivityCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static boolean contains(String code) {
        for (ExcludedActivityCode c : values()) {
            if (c.code.equals(code)) {
                return true;
            }
        }
        return false;
    }
}