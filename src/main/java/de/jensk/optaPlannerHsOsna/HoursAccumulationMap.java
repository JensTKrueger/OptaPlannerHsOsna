package de.jensk.optaPlannerHsOsna;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class HoursAccumulationMap {

    private HashMap<Integer,Integer> specialIdToHours = new HashMap<>();
    private HashMap<Integer,Integer> specialIdToSpecialCatId = new HashMap<>();

    public void addHour(int specialCatId, int specialId){
        if(specialIdToHours.containsKey(specialId)){
            specialIdToHours.put(specialId, specialIdToHours.get(specialId) + 1);
        } else {
            specialIdToHours.put(specialId, 1);
            specialIdToSpecialCatId.put(specialId, specialCatId);
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


