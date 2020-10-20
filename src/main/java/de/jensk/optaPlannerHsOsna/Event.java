package de.jensk.optaPlannerHsOsna;

import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.lookup.PlanningId;
import org.optaplanner.core.api.domain.variable.PlanningVariable;

import java.util.List;

/**
 * This class represents a single Event that has to be placed in the schedule. <br>
 * Each object of this class contains all the information about a single event.
 * The annotation @PlanningEntity implies that all objects of this class need to be
 * placed during the solving process.
 */
@PlanningEntity
public class Event {

    /**
     * The day, at which this event is currently scheduled.
     */
    private Integer day;

    /**
     * The timeslot, at which this event is currently scheduled.
     */
    private Integer timeSlot;

    /**
     * The room, in which this event is currently scheduled.
     */
    private Integer roomId;

    /**
     * The amount of events of this type per week.
     */
    private Integer hpw;

    /**
     * The desired amount of events of this type per meeting.<br>
     * Can be 1 or 2.
     */
    private Integer hpm;

    /**
     * An id the identifies all events of this type.<br>
     * For example if discrete math is scheduled 4 times per week,
     * all those 4 events will have the same id.
     */
    private Integer id;

    /**
     * This is makes every event unique. Every uniqueId is only assigned once.
     */
    private Integer uniqueId;

    /**
     * A list of all studygroups participating in this event.
     */
    private List<StudyGroup> studyGroups;

    /**
     * A list of all ids of teachers participating in this event.
     */
    private List<Integer> teacherIds;

    /**
     * This list contains the ids of all the features that are requested for this event. <br>
     * A feature is a smartboard for example.
     */
    private List<Integer> featureIds;

    /**
     * The amount of students participating in this event.
     */
    private Integer groupSize;

    /**
     * Defines whether this event is considered to be hard. The solver tries to place hard events
     * in early timeslots.
     */
    private Boolean hard;

    /**
     * Default constructor, used by the solver.
     */
    public Event() {
    }

    public void setHard(Boolean hard) {
        this.hard = hard;
    }

    public Boolean getHard() {
        return hard;
    }

    public void setGroupSize(Integer groupSize) {
        this.groupSize = groupSize;
    }

    public Integer getGroupSize() {
        return groupSize;
    }

    public void setFeatureIds(List<Integer> featureIds) {
        this.featureIds = featureIds;
    }

    public List<Integer> getFeatureIds() {
        return featureIds;
    }

    /**
     * Checks whether a studygroup participating in this events is part of a certain cohort.
     *
     * @param cohortId The cohortId for which all studygroups will be checked
     * @return Returns true if a studygroup of the given cohort participates in this event.
     * False if not.
     */
    public boolean containsCohort(int cohortId) {
        for (StudyGroup studyGroup : studyGroups) {
            if (studyGroup.getCohortId() == cohortId) {
                return true;
            }
        }
        return false;
    }

    public void setTeacherIds(List<Integer> teacherIds) {
        this.teacherIds = teacherIds;
    }

    public List<Integer> getTeacherIds() {
        return teacherIds;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    /**
     * The @PlanningVariable indicates that the day is one the variables,
     * that will be changed during the process of solving. <br>
     * The valueRangeProviderRefs means that the solution class,
     * in this case the CourseSchedule class, has a method which will return all existing days.
     * This method also has an annotation where the id "availableDays" is given.
     * @return The day, the event is currently scheduled at.
     */
    @PlanningVariable(valueRangeProviderRefs = {"availableDays"})
    public Integer getDay() {
        return day;
    }

    public void setTimeSlot(Integer timeSlot) {
        this.timeSlot = timeSlot;
    }

    /**
     * The @PlanningVariable indicates that the timeslot is one the variables,
     * that will be changed during the process of solving. <br>
     * The valueRangeProviderRefs means that the solution class,
     * in this case the CourseSchedule class, has a method which will return
     * all existing timeslots. This method also has an annotation,
     * where the id "availableTimeSlots" is given.
     * @return The time, the event is currently scheduled at.
     */
    @PlanningVariable(valueRangeProviderRefs = {"availableTimeSlots"})
    public Integer getTimeSlot() {
        return timeSlot;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    /**
     * The @PlanningVariable indicates that the room is one the variables,
     * that will be changed during the process of solving. <br>
     * The valueRangeProviderRefs means that the solution class,
     * in this case the CourseSchedule class, has a method which will return all existing rooms.
     * This method also has an annotation where the id "availableRooms" is given.
     * @return The room, the event is currently scheduled in.
     */
    @PlanningVariable(valueRangeProviderRefs = {"availableRooms"})
    public Integer getRoomId() {
        return roomId;
    }

    public void setHpw(Integer hpw) {
        this.hpw = hpw;
    }

    public Integer getHpw() {
        return hpw;
    }

    public void setHpm(Integer hpm) {
        this.hpm = hpm;
    }

    public Integer getHpm() {
        return hpm;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setUniqueId(Integer uniqueId) {
        this.uniqueId = uniqueId;
    }

    /**
     * The @PlanningId annotation indicates,
     * that every event can be uniquely identified by this value.
     * @return The uniqueId.
     */
    @PlanningId
    public Integer getUniqueId() {
        return uniqueId;
    }

    public void setStudyGroups(List<StudyGroup> studyGroups) {
        this.studyGroups = studyGroups;
    }

    public List<StudyGroup> getStudyGroups() {
        return studyGroups;
    }

    /**
     * This constructor is only used for testing.
     */
    public Event(Integer day, Integer timeSlot, Integer roomId, Integer uniqueId,
                 List<Integer> teacherIds, Integer groupSize, Integer id, Boolean hard) {
        this.day = day;
        this.timeSlot = timeSlot;
        this.roomId = roomId;
        this.uniqueId = uniqueId;
        this.teacherIds = teacherIds;
        this.groupSize = groupSize;
        this.id = id;
        this.hard = hard;
    }


}
