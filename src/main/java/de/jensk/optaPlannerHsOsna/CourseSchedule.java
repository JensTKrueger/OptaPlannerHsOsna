package de.jensk.optaPlannerHsOsna;

import org.optaplanner.core.api.domain.solution.*;
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;

import java.util.ArrayList;
import java.util.List;

@PlanningSolution
public class CourseSchedule {

    private List<Integer> roomList;
    private RoomMap roomMap;
    private List<Integer> timeSlotList;
    private List<Integer> dayList;
    private List<Event> eventList;
    private HardSoftScore score;
    private TimePreferenceMap timePreferenceMap;


    public CourseSchedule(){
        System.out.println("new Schedule");
        timeSlotList = new ArrayList<Integer>();
        timeSlotList.add(0);
        timeSlotList.add(1);
        timeSlotList.add(2);
        timeSlotList.add(3);
        timeSlotList.add(4);
        timeSlotList.add(5);

        dayList = new ArrayList<Integer>();
        dayList.add(0);
        dayList.add(1);
        dayList.add(2);
        dayList.add(3);
        dayList.add(4);
    }



    @ProblemFactProperty
    public RoomMap getRoomMap() {
        return roomMap;
    }

    public void setRoomMap(RoomMap roomMap) {
        this.roomMap = roomMap;
    }

    @ProblemFactProperty
    public TimePreferenceMap getTimePreferenceMap() {
        return timePreferenceMap;
    }

    @ValueRangeProvider(id = "availableRooms")
    @ProblemFactCollectionProperty
    public List<Integer> getRoomList() {
        return roomList;
    }

    public void setRoomList(List<Integer> roomList) {
        this.roomList = roomList;
    }

    public void setTimePreferenceMap(TimePreferenceMap timePreferenceMap) {
        this.timePreferenceMap = timePreferenceMap;
    }


    public void setEventList(List<Event> eventList) {
        System.out.println("getEventList");
        this.eventList = eventList;
    }



    @ValueRangeProvider(id = "availableTimeSlots")
    @ProblemFactCollectionProperty
    public List<Integer> getTimeSlotList() {
        return timeSlotList;
    }

    @ValueRangeProvider(id = "availableDays")
    @ProblemFactCollectionProperty
    public List<Integer> getDayList() {
        return dayList;
    }




    @PlanningScore
    public HardSoftScore getScore() {
        return score;
    }

    public void setScore(HardSoftScore score) {
        this.score = score;
    }

    @PlanningEntityCollectionProperty
    public List<Event> getEventList() {
        System.out.println("getEventList");
        return eventList;
    }
}