package com.fromLab.utils;

import java.math.BigDecimal;

/**
 * @author wsh
 * @date 2020-05-01
 * The utility class for time calculation
 */
public class NumberUtils {

    public static final BigDecimal BASE = BigDecimal.valueOf(3600000);

    /**
     * Covert the time to hour
     *
     * @param timeSpent
     * @return BigDecimal
     */
    public static BigDecimal covertTimeToHour(Long timeSpent) {
        BigDecimal result = BigDecimal.ZERO;
        BigDecimal temp = BigDecimal.valueOf(timeSpent);
        result = temp.divide(BASE, 3, BigDecimal.ROUND_HALF_UP);
        return result;
    }

    /**
     * Calculate the spent time when updating
     *
     * @param oriTime
     * @param time
     * @return
     */
    public static BigDecimal calUpdateTimeSpent(BigDecimal oriTime, BigDecimal time) {
        BigDecimal result = oriTime.add(time);
        return result.setScale(2, BigDecimal.ROUND_HALF_UP);
    }
}
