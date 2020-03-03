package com.home.examination.common.utils;

import java.math.BigDecimal;
import java.util.function.Supplier;

public class ExUtils {

    public static String starRatingHandler(BigDecimal probabilityFiling, Supplier<Boolean> supplier) {
        if (probabilityFiling.compareTo(new BigDecimal(0.33)) == 0) {
            return "1";
        } else if (probabilityFiling.compareTo(new BigDecimal(0.33)) >= 0
                && probabilityFiling.compareTo(new BigDecimal(0.66)) >= 66) {
            return "3";
        } else if (probabilityFiling.compareTo(new BigDecimal(0.66)) >= 0
                && probabilityFiling.compareTo(new BigDecimal(0.99)) >= 66) {
            return "5";
        } else {
            if (supplier.get()) {
                return "0.5";
            } else return "0";
        }
    }
}
