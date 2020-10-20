package de.jensk.optaPlannerHsOsna;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * This class provides the possibility to store the capacity, the building
 * and the features of a room.
 */
public class RoomMap {

    /**
     * Stores the capacity of each room.
     */
    private HashMap<Integer, Integer> capacityMap;

    /**
     * Stores the building id of each room.
     */
    private HashMap<Integer, Integer> buildingIdMap;

    /**
     * Stores a list of all ids of features each room has.
     */
    private HashMap<Integer, List<Integer>> featureListMap;


    /**
     * The default constructor.
     */
    public RoomMap() {
        capacityMap = new HashMap<>();
        buildingIdMap = new HashMap<>();
        featureListMap = new HashMap<>();
    }

    /**
     * Checks if two rooms are in different buildings.
     * @param roomId1 The id of the first room to compare.
     * @param roomId2 The id of the second room to compare.
     * @return Returns true if both rooms are in the same building. Returns false if not.
     */
    public Boolean areRoomsInDifferentBuildings(Integer roomId1, Integer roomId2) {
        return !getBuildingId(roomId1).equals(getBuildingId(roomId2));
    }

    /**
     * Checks if a room has a specific feature.
     * @param roomId The id of the room to check.
     * @param featureId The id of the feature, which the room should have.
     * @return Returns true if the room has the feature. Returns false if not.
     */
    public Boolean doesRoomHaveFeature(Integer roomId, Integer featureId) {
        return featureListMap.containsKey(roomId) && featureListMap.get(roomId).contains(featureId);
    }

    /**
     * Adds a feature to a room.
     * @param roomId The id of the room to which a new feature is to be added.
     * @param featureId The id of the feature to add.
     */
    public void putFeature(int roomId, int featureId) {
        if (!featureListMap.containsKey(roomId)) {
            featureListMap.put(roomId, new ArrayList<>());
        }
        featureListMap.get(roomId).add(featureId);
    }

    public List<Integer> getFeatureList(int roomId) {
        return featureListMap.get(roomId);
    }

    /**
     * Sets the capacity of a specific room.
     * @param roomId The id of the room to which a new capacity should be assigned.
     * @param capacity The capacity that should be assigned to a room.
     */
    public void putCapacity(int roomId, int capacity) {
        capacityMap.put(roomId, capacity);
    }

    /**
     * Sets the building id of a specific room.
     * @param roomId The id of the room to which a new capacity should be assigned.
     * @param buildingId The id of the building the room is located in.
     */
    public void putBuildingId(int roomId, int buildingId) {
        buildingIdMap.put(roomId, buildingId);
    }

    public Integer getCapacity(int roomId) {
        return capacityMap.get(roomId);
    }

    public Integer getBuildingId(int roomId) {
        return buildingIdMap.get(roomId);
    }
}
