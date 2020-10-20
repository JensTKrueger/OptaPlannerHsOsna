package de.jensk.optaPlannerHsOsna;

import java.util.HashMap;

/**
 * This class stores information about which time periods are preferred and which time
 * periods are blocked by each teacher.
 */
public class TimePreferenceMap {

    /**
     * This HashMap stores all the information in this class. The id of the teacher is the key.
     * The value is a two dimensional integer array. The first dimension specifies the day,
     * the second dimension specifies the timeslot and the value in the array is a number
     * between 1 and 3. <br>
     * 1 means the period is blocked. <br>
     * 2 means the period is neither blocked or preferred.<br>
     * 3 means the period is preferred.<br>
     */
    private HashMap<Integer, Integer[][]> timePreference;

    /**
     * This is the default constructor.
     */
    public TimePreferenceMap() {
        timePreference = new HashMap<>();
    }

    /**
     * Checks for a specific teacherId, day and timeslot whether that period is preferred,
     * blocked or neither of them.
     * @param teacherId The id of the teacher to check.
     * @param day The day to check.
     * @param timeSlot The timeslot to check.
     * @return Returns 1 for blocked, 2 for neutral and 3 for preferred.
     */
    public Integer getPreference(int teacherId, int day, int timeSlot) {
            if (!timePreference.containsKey(Integer.valueOf(teacherId))
                    || timePreference.get(Integer.valueOf(teacherId))[day][timeSlot] == null) {
                return 2;
            } else {
                return timePreference.get(teacherId)[day][timeSlot];
            }

    }

    /**
     * Puts a Preference to the Map.
     * @param teacherId The id of the teacher who prefers or blocks a period.
     * @param day The day to block a period in.
     * @param timeSlot The timeslot to block.
     * @param preference 1 means blocked, 2 means neutral, 3 means preferred.
     */
    public void putPreference(int teacherId, int day, int timeSlot, int preference) {
        if (!timePreference.containsKey(Integer.valueOf(teacherId))) {
            timePreference.put(Integer.valueOf(teacherId),
                    new Integer[CourseSchedule.DAYS_PER_WEEK][CourseSchedule.TIMESLOTS_PER_DAY]);
        }
        timePreference.get(Integer.valueOf(teacherId))[day][timeSlot] = preference;
    }
}
