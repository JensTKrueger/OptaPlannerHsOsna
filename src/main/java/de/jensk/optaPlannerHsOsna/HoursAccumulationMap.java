package de.jensk.optaPlannerHsOsna;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class HoursAccumulationMap {

    private final int cohortId;
    private HashMap<Integer,Integer> specialIdToHours = new HashMap<>();
    private HashMap<Integer,Integer> specialIdToSpecialCatId = new HashMap<>();

    public HoursAccumulationMap(int cohortId) {
        this.cohortId = cohortId;
    }


    public void addEvent(Event event){
        event.getStudyGroups().forEach((studyGroup -> {
            if(studyGroup.getCohortId() == cohortId){
                addHour(studyGroup.getSpecialCatId(), studyGroup.getSpecialId());
            }
        }));
    }

    public void removeEvent(Event event){
        event.getStudyGroups().forEach((studyGroup -> {
                removeHour(studyGroup.getSpecialId());
        }));
    }

    public void addHour(int specialCatId, int specialId){
        if(specialIdToHours.containsKey(specialId)){
            specialIdToHours.put(specialId, specialIdToHours.get(specialId) + 1);
        } else {
            specialIdToHours.put(specialId, 1);
            specialIdToSpecialCatId.put(specialId, specialCatId);
        }
    }

    public void removeHour(int specialId){
        //todo das hinterher aus performancegründen wieder raus. nur drin um bugs auszuschließen
        if(!specialIdToHours.containsKey(specialId) || !specialIdToSpecialCatId.containsKey(specialId)){
            throw new RuntimeException("Tried to remove and entity that wasnt in the list.");
        }
        int hoursBefore = specialIdToHours.get(specialId);
        if(hoursBefore == 0){
            specialIdToHours.remove(specialId);
            specialIdToSpecialCatId.remove(specialId);
        } else {
            specialIdToHours.put(specialId, hoursBefore -1);
        }

    }

    public int getMaxHours(){
        HashMap<Integer, Integer> bestSpecialOfCat = new HashMap<>();
        specialIdToHours.forEach((k, v) -> {
            if(!bestSpecialOfCat.containsKey(specialIdToSpecialCatId.get(k)) || bestSpecialOfCat.get(specialIdToSpecialCatId.get(k)) < v) {
                bestSpecialOfCat.put(specialIdToSpecialCatId.get(k),v);
            }
        });
        AtomicInteger sum = new AtomicInteger();
        bestSpecialOfCat.forEach((k,v) -> {
            sum.addAndGet(v);
        });
        return sum.get();
    }

    public int getMinHours(){
        HashMap<Integer, Integer> bestSpecialOfCat = new HashMap<>();
        specialIdToHours.forEach((k, v) -> {
            if(!bestSpecialOfCat.containsKey(specialIdToSpecialCatId.get(k)) || bestSpecialOfCat.get(specialIdToSpecialCatId.get(k)) > v) {
                bestSpecialOfCat.put(specialIdToSpecialCatId.get(k),v);
            }
        });
        AtomicInteger sum = new AtomicInteger();
        bestSpecialOfCat.forEach((k,v) -> {
            sum.addAndGet(v);
        });
        return sum.get();
    }


}


