package com.home.examination.common.utils;

import com.home.examination.entity.domain.UserDO;

import java.math.BigDecimal;
import java.util.function.Supplier;

public class ExUtils {

    private final static BigDecimal zero = new BigDecimal(0);

    public static String starRatingHandler(BigDecimal probabilityFiling) {
        if (probabilityFiling.compareTo(new BigDecimal(0.33)) == 0) {
            return "1";
        } else if (probabilityFiling.compareTo(new BigDecimal(0.33)) >= 0
                && probabilityFiling.compareTo(new BigDecimal(0.66)) <= 0) {
            return "3";
        } else if (probabilityFiling.compareTo(new BigDecimal(0.66)) >= 0) {
            return "5";
        } else {
            return "0";
        }
    }

    public static Supplier<Boolean> getUserHalfStarSupplier(UserDO userDO, String scoreParagraph) {
        return () -> {
            String minScore = scoreParagraph.split("-")[0];
            BigDecimal score = userDO.getCollegeScore();
            if (score == null || score.compareTo(zero) == 0) {
                score = userDO.getPredictedScore();
            }

            BigDecimal divisor = new BigDecimal(minScore);

            BigDecimal subtract = divisor.subtract(score);
            return subtract.compareTo(new BigDecimal(15)) < 0;
        };
    }

}
