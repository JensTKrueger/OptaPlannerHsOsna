import de.jensk.optaPlannerHsOsna.*;
import de.jensk.optaPlannerHsOsna.InformationMaps.RoomSpecificationsMap;
import de.jensk.optaPlannerHsOsna.InformationMaps.StudentLoadMap;
import de.jensk.optaPlannerHsOsna.InformationMaps.TimePreferenceMap;
import org.junit.Test;
import org.optaplanner.core.api.solver.SolverFactory;
import org.optaplanner.test.impl.score.buildin.hardsoft.HardSoftScoreVerifier;

import java.util.Arrays;


public class ScoreConstraintTests {
    private HardSoftScoreVerifier<CourseSchedule> scoreVerifier =
            new HardSoftScoreVerifier<> (SolverFactory
                    .createFromXmlResource("de/jensk/optaPlannerHsOsna/solverConfig.xml"));


    @Test
    public void oneEventPerRoom(){
        Event e1 = new Event(1,1,1,1,null,null,null,null, null);
        Event e2 = new Event(1,1,1,2,null,null,null,null, null);
        Event e3 = new Event(1,1,1,3,null,null,null,null, null);
        Event e4 = new Event(1,1,2,4,null,null,null,null, null);
        CourseSchedule solution = new CourseSchedule();
        solution.setEventList(Arrays.asList(e1,e2,e3,e4));
        fillCourseScheduleWithTestFacts(solution);
        scoreVerifier.assertHardWeight("oneEventPerRoom", -3, solution);
        e3.setDay(2);
        scoreVerifier.assertHardWeight("oneEventPerRoom", -1, solution);
        e3.setDay(1);
        e3.setTimeSlot(2);
        scoreVerifier.assertHardWeight("oneEventPerRoom", -1, solution);
    }

    @Test
    public void oneEventPerTeacher(){
        Event e1 = new Event(1,1,1,1, Arrays.asList(1, 2),null,null,null, null);
        Event e2 = new Event(1,1,1,2, Arrays.asList(2, 3),null,null,null, null);
        Event e3 = new Event(1,1,1,3, Arrays.asList(1, 3, 4),null,null,null, null);
        CourseSchedule solution = new CourseSchedule();
        solution.setEventList(Arrays.asList(e1,e2,e3));
        fillCourseScheduleWithTestFacts(solution);
        scoreVerifier.assertHardWeight("oneEventPerTeacher", -3, solution);
        e2.setTeacherIds(Arrays.asList(5,6));
        scoreVerifier.assertHardWeight("oneEventPerTeacher", -1, solution);
        e1.setTimeSlot(3);
        scoreVerifier.assertHardWeight("oneEventPerTeacher", 0, solution);
    }

    @Test
    public void avoidLockedTimeSlots(){
        Event e1 = new Event(1,1,null,1, Arrays.asList(1, 2),null,null,null, null);
        Event e2 = new Event(1,1,null,2, Arrays.asList(2, 3),null,null,null, null);
        CourseSchedule solution = new CourseSchedule();
        solution.setEventList(Arrays.asList(e1,e2));
        fillCourseScheduleWithTestFacts(solution);
        scoreVerifier.assertHardWeight("avoidLockedTimeSlots", -4, solution);
        e1.setTeacherIds(Arrays.asList(1));
        scoreVerifier.assertHardWeight("avoidLockedTimeSlots", -3, solution);
        e2.setTimeSlot(2);
        scoreVerifier.assertHardWeight("avoidLockedTimeSlots", -1, solution);
        e2.setTimeSlot(3);
        scoreVerifier.assertHardWeight("avoidLockedTimeSlots", -1, solution);
    }

    @Test
    public void oneEventPerGroup(){
        Event e1 = new Event(1,1,1,1,null,null,null,null, null);
        Event e2 = new Event(1,1,1,2,null,null,null,null, null);
        e1.setStudyGroups(Arrays.asList(new StudyGroup(1,1,1),new StudyGroup(2,2,2)));
        e2.setStudyGroups(Arrays.asList(new StudyGroup(3,3,1),new StudyGroup(2,2,2)));
        CourseSchedule solution = new CourseSchedule();
        solution.setEventList(Arrays.asList(e1,e2));
        fillCourseScheduleWithTestFacts(solution);
        scoreVerifier.assertHardWeight("oneEventPerGroup", -2, solution);
        e2.setRoomId(2);
        scoreVerifier.assertHardWeight("oneEventPerGroup", -2, solution);
        e2.setTimeSlot(2);
        scoreVerifier.assertHardWeight("oneEventPerGroup", 0, solution);
    }

    @Test
    public void rewardPreferredTimeSlots(){
        int factor = 5;
        Event e1 = new Event(1,3,1,1, Arrays.asList(1, 2),null,null,null, null);
        Event e2 = new Event(1,3,1,2, Arrays.asList(2, 3),null,null,null, null);
        CourseSchedule solution = new CourseSchedule();
        solution.setEventList(Arrays.asList(e1,e2));
        fillCourseScheduleWithTestFacts(solution);
        scoreVerifier.assertSoftWeight("rewardPreferredTimeSlots", 4 * factor, solution);
        e1.setTeacherIds(Arrays.asList(1));
        scoreVerifier.assertSoftWeight("rewardPreferredTimeSlots", 3 * factor, solution);
        e2.setTimeSlot(2);
        scoreVerifier.assertSoftWeight("rewardPreferredTimeSlots", 1 * factor, solution);
        e2.setTimeSlot(1);
        scoreVerifier.assertSoftWeight("rewardPreferredTimeSlots", 1 * factor, solution);
    }

    @Test
    public void avoidTooSmallRooms(){
        int factor = -20;
        Event e1 = new Event(null,null,3,1, null,60,null,null, null);
        Event e2 = new Event(null,null,2,2, null,55,null,null, null);
        Event e3 = new Event(null,null,1,3, null,40,null,null, null);
        CourseSchedule solution = new CourseSchedule();
        solution.setEventList(Arrays.asList(e1, e2, e3));
        fillCourseScheduleWithTestFacts(solution);
        scoreVerifier.assertSoftWeight("avoidTooSmallRooms", factor * 2, solution);
        e3.setRoomId(4);
        scoreVerifier.assertSoftWeight("avoidTooSmallRooms", factor * 1, solution);
        e2.setRoomId(3);
        scoreVerifier.assertSoftWeight("avoidTooSmallRooms", factor * 0, solution);
    }

    @Test
    public void punishSameEventDifferentRoom(){
        int factor = -1;
        Event e1 = new Event(null,null,1,1, null,null,1,null, null);
        Event e2 = new Event(null,null,2,2, null,null,1,null, null);
        Event e3 = new Event(null,null,3,3, null,null,2,null, null);
        CourseSchedule solution = new CourseSchedule();
        solution.setEventList(Arrays.asList(e1, e2, e3));
        fillCourseScheduleWithTestFacts(solution);
        scoreVerifier.assertSoftWeight("punishSameEventDifferentRoom", factor * 1, solution);
        e3.setId(1);
        scoreVerifier.assertSoftWeight("punishSameEventDifferentRoom", factor * 3, solution);
        e1.setRoomId(4);
        e2.setRoomId(4);
        e3.setRoomId(4);
        scoreVerifier.assertSoftWeight("punishSameEventDifferentRoom", factor * 0, solution);
    }

    @Test
    public void placeHardEventsEarly(){
        int factor = -1;
        Event e1 = new Event(null,0,null,1, null,null,null,false, null);
        Event e2 = new Event(null,4,null,2, null,null,null,false, null);
        CourseSchedule solution = new CourseSchedule();
        solution.setEventList(Arrays.asList(e1, e2));
        fillCourseScheduleWithTestFacts(solution);
        scoreVerifier.assertSoftWeight("placeHardEventsEarly", factor * 0, solution);
        e1.setHard(true);
        scoreVerifier.assertSoftWeight("placeHardEventsEarly", factor * 0, solution);
        e2.setHard(true);
        scoreVerifier.assertSoftWeight("placeHardEventsEarly", factor * 4, solution);
        e1.setTimeSlot(3);
        scoreVerifier.assertSoftWeight("placeHardEventsEarly", factor * (3+4), solution);
    }

    @Test
    public void punishBuildingChangeWithoutBreak(){
        int factor = -5;
        Event e1 = new Event(1,1,1,1, null,null,1,null, null);
        Event e2 = new Event(1,2,3,2, null,null,2,null, null);
        Event e3 = new Event(2,2,3,3, null,null,3,null, null);
        e1.setStudyGroups(Arrays.asList(new StudyGroup(1,1,1),new StudyGroup(2,2,2)));
        e2.setStudyGroups(Arrays.asList(new StudyGroup(3,3,1),new StudyGroup(2,2,2)));
        e3.setStudyGroups(Arrays.asList(new StudyGroup(4,4,1)));
        CourseSchedule solution = new CourseSchedule();
        solution.setEventList(Arrays.asList(e1, e2, e3));
        fillCourseScheduleWithTestFacts(solution);
        scoreVerifier.assertSoftWeight("punishBuildingChangeWithoutBreak", factor * 2, solution);
        e3.setDay(1);
        scoreVerifier.assertSoftWeight("punishBuildingChangeWithoutBreak", factor * 3, solution);
    }

    @Test
    public void punishMissingRoomFeatures(){
        int factor = -10;
        Event e1 = new Event(null,null,1,1, null,null,1,false, null);
        Event e2 = new Event(null,null,2,2, null,null,2,false, null);
        e1.setFeatureIds(Arrays.asList(1,2,3));
        e2.setFeatureIds(Arrays.asList(1,2));
        CourseSchedule solution = new CourseSchedule();
        solution.setEventList(Arrays.asList(e1, e2));
        fillCourseScheduleWithTestFacts(solution);
        scoreVerifier.assertSoftWeight("punishMissingRoomFeatures", factor * 0, solution);
        e1.setRoomId(2);
        e2.setRoomId(1);
        scoreVerifier.assertSoftWeight("punishMissingRoomFeatures", factor * 1, solution);
        e1.setRoomId(3);
        scoreVerifier.assertSoftWeight("punishMissingRoomFeatures", factor * 3, solution);

    }

    @Test
    public void punishOneHpmDifferences(){
        int factor = -20;
        Event e1 = new Event(1,1,null,1, null,null,1,null, 1);
        Event e2 = new Event(1,2,null,2, null,null,1,null, 1);
        Event e3 = new Event(1,3,null,3, null,null,1,null, 1);
        Event e4 = new Event(1,4,null,4, null,null,1,null, 1);

        CourseSchedule solution = new CourseSchedule();
        solution.setEventList(Arrays.asList(e1, e2, e3, e4));
        fillCourseScheduleWithTestFacts(solution);

        scoreVerifier.assertSoftWeight("punishOneHpmDifferences", factor * 3, solution);
        e1.setId(2);
        scoreVerifier.assertSoftWeight("punishOneHpmDifferences", factor * 2, solution);
        e2.setDay(5);
        scoreVerifier.assertSoftWeight("punishOneHpmDifferences", factor * 1, solution);
        e3.setDay(5);
        scoreVerifier.assertSoftWeight("punishOneHpmDifferences", factor * 1, solution);
        e3.setDay(4);
        scoreVerifier.assertSoftWeight("punishOneHpmDifferences", factor * 0, solution);
    }

    @Test
    public void punishTwoHpmDifferences(){
        int factorNotAdjacent = -5;
        int factorWrongNumberOfHours = -20;
        Event e1 = new Event(1,1,null,1, null,null,1,null, 2);
        Event e2 = new Event(1,2,null,2, null,null,1,null, 2);
        Event e3 = new Event(1,3,null,3, null,null,1,null, 2);
        Event e4 = new Event(1,4,null,4, null,null,1,null, 2);

        CourseSchedule solution = new CourseSchedule();
        solution.setEventList(Arrays.asList(e1, e2, e3, e4));
        fillCourseScheduleWithTestFacts(solution);

        scoreVerifier.assertSoftWeight("punishTwoHpmDifferences", factorWrongNumberOfHours * 2, solution);
        e1.setDay(2);
        scoreVerifier.assertSoftWeight("punishTwoHpmDifferences", factorWrongNumberOfHours * 2, solution);
        e3.setDay(2);
        scoreVerifier.assertSoftWeight("punishTwoHpmDifferences", factorNotAdjacent * 2, solution);
        e1.setTimeSlot(2);
        scoreVerifier.assertSoftWeight("punishTwoHpmDifferences", factorNotAdjacent * 1, solution);
        e2.setTimeSlot(3);
        scoreVerifier.assertSoftWeight("punishTwoHpmDifferences", 0, solution);
    }

    @Test
    public void noGaps(){
        int factor = -10;
        Event e1 = new Event(1,1,null,1, null,null,null,null, null);
        Event e2 = new Event(1,2,null,2, null,null,null,null, null);
        Event e3 = new Event(1,3,null,3, null,null,null,null, null);
        Event e4 = new Event(1,4,null,4, null,null,null,null, null);
        e1.setStudyGroups(Arrays.asList(new StudyGroup(1,1,1)));
        e2.setStudyGroups(Arrays.asList(new StudyGroup(1,1,1)));
        e3.setStudyGroups(Arrays.asList(new StudyGroup(1,1,1)));
        e4.setStudyGroups(Arrays.asList(new StudyGroup(1,1,1)));

        CourseSchedule solution = new CourseSchedule();

        solution.setEventList(Arrays.asList(e1, e2, e3, e4));
        fillCourseScheduleWithTestFacts(solution);
        scoreVerifier.assertSoftWeight("noGaps", factor * 0, solution);
        e3.setDay(2);
        scoreVerifier.assertSoftWeight("noGaps", factor * 1, solution);
        e2.setDay(2);
        scoreVerifier.assertSoftWeight("noGaps", factor * 2, solution);
        e4.setTimeSlot(5);
        e3.setTimeSlot(5);
        scoreVerifier.assertSoftWeight("noGaps", factor * 5, solution);
        e4.setStudyGroups(Arrays.asList(new StudyGroup(2,2,2)));
        scoreVerifier.assertSoftWeight("noGaps", factor * 2, solution);
    }

    @Test
    public void testMaxHours(){
        int factor = -10;
        Event e1 = new Event(1,null,null,1, null,null,null ,null, null);
        Event e2 = new Event(1,null,null,2, null,null,null,null, null);
        Event e3 = new Event(1,null,null,3, null,null,null,null, null);
        Event e4 = new Event(1,null,null,4, null,null,null,null, null);
        Event e5 = new Event(1,null,null,5, null,null,null,null, null);
        Event e6 = new Event(1,null,null,6, null,null,null,null, null);

        e1.setStudyGroups(Arrays.asList(new StudyGroup(1,1,1)));
        e2.setStudyGroups(Arrays.asList(new StudyGroup(1,1,1)));
        e3.setStudyGroups(Arrays.asList(new StudyGroup(1,1,1)));
        e4.setStudyGroups(Arrays.asList(new StudyGroup(1,1,1)));
        e5.setStudyGroups(Arrays.asList(new StudyGroup(1,1,1)));
        e6.setStudyGroups(Arrays.asList(new StudyGroup(1,1,1)));

        CourseSchedule solution = new CourseSchedule();

        solution.setEventList(Arrays.asList(e1, e2, e3, e4, e5 ,e6));
        fillCourseScheduleWithTestFacts(solution);
        scoreVerifier.assertSoftWeight("minMaxHoursPerDay", factor * 2, solution);
        e6.setDay(2);
        scoreVerifier.assertSoftWeight("minMaxHoursPerDay", factor * 2, solution);
        e5.setDay(3);
        scoreVerifier.assertSoftWeight("minMaxHoursPerDay", factor * 2, solution);
        e5.setDay(2);
        scoreVerifier.assertSoftWeight("minMaxHoursPerDay", factor * 0, solution);
        e1.setStudyGroups(Arrays.asList(new StudyGroup(1,1,1),new StudyGroup(2,2,2)));
        scoreVerifier.assertSoftWeight("minMaxHoursPerDay", factor * 4, solution);
        e2.setStudyGroups(Arrays.asList(new StudyGroup(1,1,1),new StudyGroup(3,3,2)));
        scoreVerifier.assertSoftWeight("minMaxHoursPerDay", factor * 3, solution);
        e2.setStudyGroups(Arrays.asList(new StudyGroup(1,1,1),
                new StudyGroup(3,3,2),
                new StudyGroup(4,3,2)));
        scoreVerifier.assertSoftWeight("minMaxHoursPerDay", factor * 3, solution);
    }

    @Test
    public void testMinHours(){
        int factor = -10;
        Event e1 = new Event(1,null,null,1, null,null,null ,null, null);
        Event e2 = new Event(1,null,null,2, null,null,null,null, null);
        Event e3 = new Event(1,null,null,3, null,null,null,null, null);
        Event e4 = new Event(1,null,null,4, null,null,null,null, null);
        Event e5 = new Event(1,null,null,5, null,null,null,null, null);
        Event e6 = new Event(1,null,null,6, null,null,null,null, null);

        e1.setStudyGroups(Arrays.asList(new StudyGroup(1,1,2)));
        e2.setStudyGroups(Arrays.asList(new StudyGroup(1,1,2)));
        e3.setStudyGroups(Arrays.asList(new StudyGroup(1,1,2)));
        e4.setStudyGroups(Arrays.asList(new StudyGroup(1,1,2)));
        e5.setStudyGroups(Arrays.asList(new StudyGroup(1,1,2)));
        e6.setStudyGroups(Arrays.asList(new StudyGroup(1,1,2)));

        CourseSchedule solution = new CourseSchedule();

        solution.setEventList(Arrays.asList(e1, e2, e3, e4, e5 ,e6));
        fillCourseScheduleWithTestFacts(solution);
        scoreVerifier.assertSoftWeight("minMaxHoursPerDay", factor * 1, solution);
        e6.setDay(2);
        scoreVerifier.assertSoftWeight("minMaxHoursPerDay", factor * 4, solution);
        e1.setStudyGroups(Arrays.asList(new StudyGroup(2,2,2)));
        scoreVerifier.assertSoftWeight("minMaxHoursPerDay", factor * 4, solution);
        e2.setStudyGroups(Arrays.asList(new StudyGroup(3,2,2)));
        scoreVerifier.assertSoftWeight("minMaxHoursPerDay", factor * 5, solution);
    }





    private void fillCourseScheduleWithTestFacts(CourseSchedule solution){
        solution.setRoomList(Arrays.asList(1,2,3));
        TimePreferenceMap timePreferenceMap = new TimePreferenceMap();
        timePreferenceMap.putPreference(1,1,1,1);
        timePreferenceMap.putPreference(2,1,1,1);
        timePreferenceMap.putPreference(3,1,1,1);
        timePreferenceMap.putPreference(1,1,2,2);
        timePreferenceMap.putPreference(2,1,2,2);
        timePreferenceMap.putPreference(3,1,2,2);
        timePreferenceMap.putPreference(1,1,3,3);
        timePreferenceMap.putPreference(2,1,3,3);
        timePreferenceMap.putPreference(3,1,3,3);
        solution.setTimePreferenceMap(timePreferenceMap);
        RoomSpecificationsMap roomSpecificationsMap = new RoomSpecificationsMap();
        roomSpecificationsMap.putCapacity(1,20);
        roomSpecificationsMap.putCapacity(2,40);
        roomSpecificationsMap.putCapacity(3,60);
        roomSpecificationsMap.putCapacity(4,80);
        roomSpecificationsMap.putBuildingId(1,1);
        roomSpecificationsMap.putBuildingId(2,1);
        roomSpecificationsMap.putBuildingId(3,2);
        roomSpecificationsMap.putBuildingId(4,2);
        roomSpecificationsMap.putFeature(1,1);
        roomSpecificationsMap.putFeature(1,2);
        roomSpecificationsMap.putFeature(1,3);
        roomSpecificationsMap.putFeature(2,1);
        roomSpecificationsMap.putFeature(2,2);
        solution.setRoomMap(roomSpecificationsMap);
        StudentLoadMap studentLoadMap = new StudentLoadMap();
        studentLoadMap.setMaxHoursOfCohort(1,4);
        studentLoadMap.setMinHoursOfCohort(1,2);
        studentLoadMap.setMaxHoursOfCohort(2,5);
        studentLoadMap.setMinHoursOfCohort(2,5);
        solution.setStudentLoadMap(studentLoadMap);

    }

}
