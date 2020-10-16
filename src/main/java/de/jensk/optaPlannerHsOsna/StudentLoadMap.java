package de.jensk.optaPlannerHsOsna;

import java.util.HashMap;

public class StudentLoadMap {
    private final HashMap<Integer,Integer> minHoursOfCohort = new HashMap<>();
    private final HashMap<Integer,Integer> maxHoursOfCohort = new HashMap<>();

    public void setMinHoursOfCohort(int cohortId, int hours){
        minHoursOfCohort.put(cohortId, hours);
    }

    public int getMinHoursOfCohort(int cohortId){
        return minHoursOfCohort.get(cohortId);
    }

    public void setMaxHoursOfCohort(int cohortId, int hours){
        maxHoursOfCohort.put(cohortId,hours);
    }

    public int getMaxHoursOfCohort(int cohortId){
        return maxHoursOfCohort.get(cohortId);
    }
}
