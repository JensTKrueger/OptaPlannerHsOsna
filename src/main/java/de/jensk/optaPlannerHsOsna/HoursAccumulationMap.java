package de.jensk.optaPlannerHsOsna;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * This class has the purpose to calculate the minimum and maximum amount of hours per day,
 * which can be reached with any possible combination of specialisations. <br>
 * This is less trivial than expected, because some cohorts have many specialisation categories
 * and every student can have one specialization per category.
 * This means there are a lot of possible combinations which have to be considered. <br>
 * There needs to be a separate instance for every cohort.
 */
public class HoursAccumulationMap {

    /**
     * The id of the cohort to inspect. It is final because a new instance of this class is needed
     * for every cohort.
     */
    private final int cohortId;

    /**
     * A HashMap that stores the amount of hours of a specialisation as the value and the id of the
     * specialisation as a key.
     */
    private HashMap<Integer, Integer> specialIdToHours = new HashMap<>();

    /**
     * A HashMap that stores the the id of the specialisation category of a specialisation
     * as the value and the id of the specialisation as a key.
     */
    private HashMap<Integer, Integer> specialIdToSpecialCatId = new HashMap<>();

    /**
     * The object can not be created without a cohort id.
     * @param cohortId The id of the cohort that will be inspected.
     */
    public HoursAccumulationMap(int cohortId) {
        this.cohortId = cohortId;
    }

    /**
     * Adds an event that should be considered in the calculation
     * of the maximum and the minimum possible amount of hours per day. <br>
     * Automatically ignores every studygroup of the provided event, which has the wrong cohortId.
     * @param event The event that should be considered in the calculation.
     */
    public void addEvent(Event event) {
        event.getStudyGroups().forEach((studyGroup -> {
            if (studyGroup.getCohortId() == cohortId) {
                addHour(studyGroup.getSpecialCatId(), studyGroup.getSpecialId());
            }
        }));
    }

    /**
     * Removes an event that should not longer be considered in the calculation
     * of the maximal and the minimal possible amount of hours per day. <br>
     * Will do nothing if the provided event was not added previously.
     * @param event The event that should no longer be considered in the calculation.
     */
    public void removeEvent(Event event) {
        event.getStudyGroups().forEach((studyGroup -> {
                removeHour(studyGroup.getSpecialId());
        }));
    }

    /**
     * Increased the hour counter of a specialisation.
     * @param specialCatId The category id of that specialsation.
     * @param specialId The id of the specialisation.
     */
    public void addHour(int specialCatId, int specialId) {
        if (specialIdToHours.containsKey(specialId)) {
            specialIdToHours.put(specialId, specialIdToHours.get(specialId) + 1);
        } else {
            specialIdToHours.put(specialId, 1);
            specialIdToSpecialCatId.put(specialId, specialCatId);
        }
    }

    /**
     * Decreases the hour counter of a specialisation.
     * @param specialId The id of the specialisation.
     */
    public void removeHour(int specialId) {
        int hoursBefore = specialIdToHours.get(specialId);
        if (hoursBefore == 1) {
            specialIdToHours.remove(specialId);
            specialIdToSpecialCatId.remove(specialId);
        } else {
            specialIdToHours.put(specialId, hoursBefore - 1);
        }
    }

    /**
     * Calculates the maximum amount of hours per day that can be reached with any
     * allowed combination of specialisations.
     * @return The calculated maximum amount of hours per day.
     */
    public int getMaxHours() {
        HashMap<Integer, Integer> bestSpecialOfCat = new HashMap<>();
        specialIdToHours.forEach((k, v) -> {
            if (!bestSpecialOfCat.containsKey(specialIdToSpecialCatId.get(k))
                    || bestSpecialOfCat.get(specialIdToSpecialCatId.get(k)) < v) {
                bestSpecialOfCat.put(specialIdToSpecialCatId.get(k), v);
            }
        });
        AtomicInteger sum = new AtomicInteger();
        bestSpecialOfCat.forEach((k, v) -> {
            sum.addAndGet(v);
        });
        return sum.get();
    }

    /**
     * Calculates the minimum amount of hours per day that can be reached with any
     * allowed combination of specialisations.
     * @return The calculated maximum amount of hours per day.
     */
    public int getMinHours() {
        HashMap<Integer, Integer> bestSpecialOfCat = new HashMap<>();
        specialIdToHours.forEach((k, v) -> {
            if (!bestSpecialOfCat.containsKey(specialIdToSpecialCatId.get(k))
                    || bestSpecialOfCat.get(specialIdToSpecialCatId.get(k)) > v) {
                bestSpecialOfCat.put(specialIdToSpecialCatId.get(k), v);
            }
        });
        AtomicInteger sum = new AtomicInteger();
        bestSpecialOfCat.forEach((k, v) -> {
            sum.addAndGet(v);
        });
        return sum.get();
    }
}


