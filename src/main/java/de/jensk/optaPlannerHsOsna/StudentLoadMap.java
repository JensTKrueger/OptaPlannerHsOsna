package de.jensk.optaPlannerHsOsna;

import java.util.HashMap;

/**
 * This class stores the desired minimum and maximum amounts of hours per day for each cohort.
 */
public class StudentLoadMap {

    /**
     * The HashMap stores the minimum amount of hours a cohort should have per day. <br>
     * The id of the cohort is the key and the minimum amount of hours is the value of the map.
     */
    private final HashMap<Integer, Integer> minHoursOfCohort = new HashMap<>();

    /**
     * The HashMap stores the minimum amount of hours a cohort should have per day. <br>
     * The id of the cohort is the key and the minimum amount of hours is the value of the map.
     */
    private final HashMap<Integer, Integer> maxHoursOfCohort = new HashMap<>();

    /**
     * Sets the minimum amount of hours a specific cohort should have.
     * @param cohortId The id of the cohort for which the minimum amount of hours is to be changed.
     * @param hours The new minimum amount of hours.
     */
    public void setMinHoursOfCohort(int cohortId, int hours) {
        minHoursOfCohort.put(cohortId, hours);
    }

    public int getMinHoursOfCohort(int cohortId) {
        return minHoursOfCohort.get(cohortId);
    }

    /**
     * Sets the maximum amount of hours a specific cohort should have.
     * @param cohortId The id of the cohort for which the maximum amount of hours is to be changed.
     * @param hours The new maximum amount of hours.
     */
    public void setMaxHoursOfCohort(int cohortId, int hours) {
        maxHoursOfCohort.put(cohortId, hours);
    }

    public int getMaxHoursOfCohort(int cohortId) {
        return maxHoursOfCohort.get(cohortId);
    }
}
