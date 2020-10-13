package de.jensk.optaPlannerHsOsna;

import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.lookup.PlanningId;
import org.optaplanner.core.api.domain.variable.PlanningVariable;

import java.util.List;

@PlanningEntity
public class Event {
    private Integer day;
    private Integer timeSlot;
    private Integer roomId;
    private Integer hpw;
    private Integer hpm;
    private Integer id;
    private Integer uniqueId;
    private List<StudyGroup> studyGroups;
    private List<Integer> teacherIds;
    private List<Integer> featureIds;
    private Integer groupSize;
    private Boolean hard;

    public Event() {
    }

    public Boolean getHard() {
        return hard;
    }

    public void setHard(Boolean hard) {
        this.hard = hard;
    }

    public Integer getGroupSize() {
        return groupSize;
    }

    public void setGroupSize(Integer groupSize) {
        this.groupSize = groupSize;
    }

    public List<Integer> getFeatureIds() {
        return featureIds;
    }

    public void setFeatureIds(List<Integer> featureIds) {
        this.featureIds = featureIds;
    }



    public List<Integer> getTeacherIds() {
        return teacherIds;
    }

    public void setTeacherIds(List<Integer> teacherIds) {
        this.teacherIds = teacherIds;
    }

    @PlanningVariable(valueRangeProviderRefs = {"availableDays"})
    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    @PlanningVariable(valueRangeProviderRefs = {"availableTimeSlots"})
    public Integer getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(Integer timeSlot) {
        this.timeSlot = timeSlot;
    }

    @PlanningVariable(valueRangeProviderRefs = {"availableRooms"})
    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public Integer getHpw() {
        return hpw;
    }

    public void setHpw(Integer hpw) {
        this.hpw = hpw;
    }

    public Integer getHpm() {
        return hpm;
    }

    public void setHpm(Integer hpm) {
        this.hpm = hpm;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @PlanningId
    public Integer getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(Integer uniqueId) {
        this.uniqueId = uniqueId;
    }

    public List<StudyGroup> getStudyGroups() {
        return studyGroups;
    }

    public void setStudyGroups(List<StudyGroup> studyGroups) {
        this.studyGroups = studyGroups;
    }


    //the following constructors are only used by the JUnit tests!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!


    public Event(Integer day, Integer timeSlot, Integer roomId, Integer uniqueId, List<Integer> teacherIds, Integer groupSize, Integer id, Boolean hard) {
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
