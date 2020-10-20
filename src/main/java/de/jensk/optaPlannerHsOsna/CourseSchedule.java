package de.jensk.optaPlannerHsOsna;


import org.optaplanner.core.api.domain.solution.PlanningScore;
import org.optaplanner.core.api.domain.solution.PlanningSolution;
import org.optaplanner.core.api.domain.solution.ProblemFactCollectionProperty;
import org.optaplanner.core.api.domain.solution.ProblemFactProperty;
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider;
import org.optaplanner.core.api.domain.solution.PlanningEntityCollectionProperty;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import java.util.ArrayList;
import java.util.List;


/**
 * This class represents a schedule in any possible state (solved/unsolved).<br>
 * It contains all information needed for the solving process.
 */
@PlanningSolution
public class CourseSchedule {

    /**
     * Determines how many days per week should be used in the
     * generation of the schedule.
     */
    public static final int DAYS_PER_WEEK = 5;

    /**
     * Determines how many timeslots per day should be used in the
     * generation of the schedule.
     */
    public static final int TIMESLOTS_PER_DAY = 6;

    /**
     * Contains all existing rooms.
     */
    private List<Integer> roomList;

    /**
     * Contains information about the capacity, features and building of each room.
     */
    private RoomMap roomMap;

    /**
     * Contains every existing timeslot.
     */
    private final List<Integer> timeSlotList;
    /**
     * Contains every existing day.
     */
    private final List<Integer> dayList;

    /**
     * Contains every event, that has to be placed in the schedule.
     */
    private List<Event> eventList;

    /**
     * This is an evaluation on how good the current schedule is.
     */
    private HardSoftScore score;

    /**
     * Contains information about preferred and locked time periods of teachers.
     */
    private TimePreferenceMap timePreferenceMap;

    /**
     * Contains information about many hours each study group should have
     * at minimum and at maximum in a day.
     */
    private StudentLoadMap studentLoadMap;

    /**
     * The default constructor adds all possible days and timeslots
     * to their lists.
     */
    public CourseSchedule() {
        dayList = new ArrayList<>();
        for (int currentDay = 0; currentDay < DAYS_PER_WEEK; currentDay++) {
            dayList.add(currentDay);
        }
        timeSlotList = new ArrayList<>();
        for (int currentTimeSlot = 0; currentTimeSlot < TIMESLOTS_PER_DAY; currentTimeSlot++) {
            timeSlotList.add(currentTimeSlot);
        }
    }

    /**
     * This method is annotated with @ProblemFactProperty, which means that <br>
     * the StudentLoadMap which is returned must not change during the process of solving,<br>
     * because that would result in a score corruption. <br>
     * Therefore it will stay the same for the whole duration.
     * @return The StudentLoadMap for this schedule.
     */
    @ProblemFactProperty
    public StudentLoadMap getStudentLoadMap() {
        return studentLoadMap;
    }

    /**
     * Only call this method once at the beginning. Do not call this after the solving started.
     * @param studentLoadMap The StudentLoadMap which is desired for this schedule.
     */
    public void setStudentLoadMap(StudentLoadMap studentLoadMap) {
        this.studentLoadMap = studentLoadMap;
    }

    /**
     * This method is annotated with @ProblemFactProperty, which means that <br>
     * the RoomMap which is returned must not change during the process of solving,<br>
     * because that would result in a score corruption. <br>
     * Therefore it will stay the same for the whole duration.
     * @return The RoomMap for this schedule.
     */
    @ProblemFactProperty
    public RoomMap getRoomMap() {
        return roomMap;
    }

    /**
     * Only call this method once at the beginning. Do not call this after the solving started.
     * @param roomMap The roomMap with the current information about all rooms.
     */
    public void setRoomMap(RoomMap roomMap) {
        this.roomMap = roomMap;
    }

    /**
     * This method is annotated with @ProblemFactProperty, which means that <br>
     * the TimePreferenceMap which is returned must not change during the process of solving,<br>
     * because that would result in a score corruption. <br>
     * Therefore it will stay the same for the whole duration.
     * @return The RoomMap for this schedule.
     */
    @ProblemFactProperty
    public TimePreferenceMap getTimePreferenceMap() {
        return timePreferenceMap;
    }

    /**
     * Only call this method once at the beginning. Do not call this after the solving started.
     * @param timePreferenceMap The TimePreferenceMap with the current locked and preferred periods.
     */
    public void setTimePreferenceMap(TimePreferenceMap timePreferenceMap) {
        this.timePreferenceMap = timePreferenceMap;
    }


    /**
     * This method is annotated with @ProblemFactCollectionProperty, which means that <br>
     * the RoomList which is returned must not change during the process of solving,<br>
     * because that would result in an error. <br>
     * The @ValueRangeProvider makes sure that the planning entities (in this case Event objects)
     * can request all existing rooms via the id "availableRooms".
     * @return A list of all existing rooms.
     */
    @ValueRangeProvider(id = "availableRooms")
    @ProblemFactCollectionProperty
    public List<Integer> getRoomList() {
        return roomList;
    }

    /**
     * Only call this method once at the beginning. Do not call this after the solving started.
     * @param roomList The RoomList with all available rooms.
     */
    public void setRoomList(List<Integer> roomList) {
        this.roomList = roomList;
    }


    /**
     * The Annotation @PlanningEntityCollectionProperty states, that this is the list
     * that contains all planning entities that have to be placed by the solver.
     * @return A list of all events.
     */
    @PlanningEntityCollectionProperty
    public List<Event> getEventList() {
        return eventList;
    }

    /**
     * Only call this method once at the beginning. Do not call this after the solving started.
     * @param eventList The EventList with all events that should be placed in the schedule.
     */
    public void setEventList(List<Event> eventList) {
        this.eventList = eventList;
    }


    /**
     * This method is annotated with @ProblemFactCollectionProperty, which means that <br>
     * the timeslot list which is returned must not change during the process of solving,<br>
     * because that would result in an error. <br>
     * The @ValueRangeProvider makes sure that the planning entities (in this case Event objects)
     * can request all existing timeslots via the id "availableTimeSlots".
     * @return A list of all existing timeslots.
     */
    @ValueRangeProvider(id = "availableTimeSlots")
    @ProblemFactCollectionProperty
    public List<Integer> getTimeSlotList() {
        return timeSlotList;
    }


    /**
     * This method is annotated with @ProblemFactCollectionProperty, which means that <br>
     * the day list which is returned must not change during the process of solving,<br>
     * because that would result in an error. <br>
     * The @ValueRangeProvider makes sure that the planning entities (in this case Event objects)
     * can request all existing days via the id "availableDays".
     * @return A list of all existing timeslots.
     */
    @ValueRangeProvider(id = "availableDays")
    @ProblemFactCollectionProperty
    public List<Integer> getDayList() {
        return dayList;
    }

    /**
     * The @PlanningScore annotation signals that solver, that this is the score for the schedule.
     * @return The current score for this schedule.
     */
    @PlanningScore
    public HardSoftScore getScore() {
        return score;
    }

    /**
     * Sets the score for the current CourseSchedule.
     * @param score The calculated score for this schedule.
     */
   public void setScore(HardSoftScore score) {
        this.score = score;
    }



}
