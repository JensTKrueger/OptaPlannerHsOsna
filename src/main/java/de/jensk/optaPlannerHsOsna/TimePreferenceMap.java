package de.jensk.optaPlannerHsOsna;

import java.util.HashMap;

public class TimePreferenceMap {

    private HashMap<Integer, Integer[][]> timePreference; // 0 => teacherId, 1=> day, 2=> timeSlot, value =Y prefernce 1-3 ; 1 is bad 3 is good

    public TimePreferenceMap(){
        timePreference = new HashMap<>();
    }

    public Integer getPreference(int teacherId, int day, int timeSlot){
            if (!timePreference.containsKey(Integer.valueOf(teacherId)) || timePreference.get(Integer.valueOf(teacherId))[day][timeSlot] == null) {
                return 2;
            } else {
                return timePreference.get(teacherId)[day][timeSlot];
            }

    }

    public void putPreference(int teacherId, int day, int timeSlot, int preference){
        if(!timePreference.containsKey(Integer.valueOf(teacherId))){
            timePreference.put(Integer.valueOf(teacherId), new Integer[5][6]);
        }
        timePreference.get(Integer.valueOf(teacherId))[day][timeSlot] = preference;
    }
}
