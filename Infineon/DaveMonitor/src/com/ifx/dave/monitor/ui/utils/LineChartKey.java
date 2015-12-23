package com.ifx.dave.monitor.ui.utils;

public enum LineChartKey {

    SERIES1, SERIES2, SERIES3, SERIES4;

    public static LineChartKey getChartKey(int number) {
        switch (number) {
        case 1:
            return SERIES1;
        case 2:
            return SERIES2;
        case 3:
            return SERIES3;
        case 4:
            return SERIES4;

        default:
            break;
        }
        return null;
    }
}
