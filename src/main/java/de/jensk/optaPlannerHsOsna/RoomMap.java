package de.jensk.optaPlannerHsOsna;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RoomMap {
    private HashMap<Integer,Integer> capacityMap;
    private HashMap<Integer,Integer> buildingIdMap;
    private HashMap<Integer, List<Integer>> featureListMap;



    public RoomMap(){
        capacityMap = new HashMap<>();
        buildingIdMap = new HashMap<>();
        featureListMap = new HashMap<>();
    }

    public Boolean test(){
        System.out.println("TEST");
        return true;
    }

    public Boolean areRoomsInDifferentBuildings(Integer roomId1, Integer roomId2){
        return !getBuildingId(roomId1).equals(getBuildingId(roomId2));
    }

    public Boolean doesRoomHaveFeature(Integer roomId, Integer featureId){
        if(featureListMap.containsKey(roomId) && featureListMap.get(roomId).contains(featureId)){
            return true;
        } else {
            return false;
        }
    }

    public void putFeature(int roomId, int featureId){
        if(!featureListMap.containsKey(roomId)){
            featureListMap.put(roomId, new ArrayList<>());
        }
        featureListMap.get(roomId).add(featureId);
    }

    public List<Integer> getFeatureList(int roomId){
        return featureListMap.get(roomId);
    }

    public void putCapacity(int roomId, int capacity){
        capacityMap.put(roomId, capacity);
    }

    public void putBuildingId(int roomId, int buildingId){
        buildingIdMap.put(roomId, buildingId);
    }

    public Integer getCapacity(int roomId){
        return capacityMap.get(roomId);
    }

    public Integer getBuildingId(int roomId){
        return buildingIdMap.get(roomId);
    }
}
