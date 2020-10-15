package de.jensk.optaPlannerHsOsna;

import java.util.HashMap;

public class MinMaxHoursMap {

    private HashMap<Integer,Integer> minHoursOfCohort = new HashMap<>();
    private HashMap<Integer,Integer> maxHoursOfCohort = new HashMap<>();

    public void setMaxHoursOfCohort(int cohortId, int maxHours){
        maxHoursOfCohort.put(cohortId,maxHours);
    }

    public void setMinHoursOfCohort(int cohortId, int minHours){
        minHoursOfCohort.put(cohortId,minHours);
    }

    public int getMaxHoursOfCohort(int cohortId){
        return maxHoursOfCohort.get(cohortId);
    }

    public int getMinHoursOfCohort(int cohortId){
        return minHoursOfCohort.get(cohortId);
    }


}
