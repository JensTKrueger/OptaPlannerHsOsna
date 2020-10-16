package de.jensk.optaPlannerHsOsna;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class DbConnector {
    private static Connection con;

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void connectToDb(){
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost/planungstoolv2?user=root&password=root");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static TimePreferenceMap getTimePreferenceMap(){
        TimePreferenceMap result = new TimePreferenceMap();
        try {
            if(con == null || con.isClosed()) connectToDb();
            String query = "SELECT * FROM timepreference;";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()){
                result.putPreference(rs.getInt("user_id"), rs.getInt("day"), rs.getInt("timeslot"), rs.getInt("pref"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    public static String getCommand(){
        try {
            if(con == null || con.isClosed()) connectToDb();
            String query = "SELECT * FROM communication ORDER BY id;";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()){
                String command = rs.getString("command");
                String query2 = "DELETE FROM communication WHERE id = '"+rs.getInt("id")+"';";
                Statement stmt2 = con.createStatement();
                stmt2.executeUpdate(query2);
                return command;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


    public static  ArrayList<Event> getEventList(){
        ArrayList<Event> result = new ArrayList<>();
        try {
            if(con == null || con.isClosed()) connectToDb();
            String query = "SELECT * FROM event;";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()){
                //this for loop creates events multiple times if they are scheduled more than once a week
                for(int per_week_count = 0; per_week_count < rs.getInt("hours_per_week"); per_week_count++) {
                    Event currentEvent = new Event();
                    currentEvent.setGroupSize(0);
                    currentEvent.setHpm(rs.getInt("hours_per_meeting"));
                    currentEvent.setHpw(rs.getInt("hours_per_week"));
                    currentEvent.setHard(rs.getBoolean("is_hard"));
                    currentEvent.setId(rs.getInt("id"));
                    currentEvent.setUniqueId(UniqueIdGenerator.generateUniqueId());

                    //from here inner rs that manages all teachers
                    ArrayList<Integer> teacherList = new ArrayList<>();
                    String innerQuery1 = "SELECT * FROM teacher_event WHERE event_id='" + rs.getInt("id") + "';";
                    Statement innerStmt1 = con.createStatement();
                    ResultSet innerRs1 = innerStmt1.executeQuery(innerQuery1);
                    while (innerRs1.next()) {
                        teacherList.add(innerRs1.getInt("teacher_id"));
                    }
                    currentEvent.setTeacherIds(teacherList);

                    //from here inner rs that manages all features
                    ArrayList<Integer> featureList = new ArrayList<>();
                    String innerQuery2 = "SELECT * FROM event_feature WHERE event_id='" + rs.getInt("id") + "';";
                    Statement innerStmt2 = con.createStatement();
                    ResultSet innerRs2 = innerStmt2.executeQuery(innerQuery2);
                    while (innerRs2.next()) {
                        featureList.add(innerRs2.getInt("feature_id"));
                    }
                    currentEvent.setFeatureIds(featureList);

                    //from here inner rs than manages all studygroups
                    ArrayList<StudyGroup> studyGroups = new ArrayList<>();
                    String innerQuery3 = "SELECT * FROM event_participation " +
                            "JOIN special ON special.special_id=event_participation.special_id " +
                            "WHERE event_id='" + rs.getInt("id") + "';";
                    Statement innerStmt3 = con.createStatement();
                    ResultSet innerRs3 = innerStmt3.executeQuery(innerQuery3);
                    while (innerRs3.next()) {
                        StudyGroup currentGroup = new StudyGroup();
                        currentGroup.setSpecialId(innerRs3.getInt("special_id"));
                        currentGroup.setSpecialCatId(innerRs3.getInt("special_cat_id"));
                        currentGroup.setCohortId(innerRs3.getInt("cohort_id"));
                        studyGroups.add(currentGroup);
                        currentEvent.setGroupSize(currentEvent.getGroupSize() + innerRs3.getInt("size"));
                    }
                    currentEvent.setStudyGroups(studyGroups);

                    result.add(currentEvent);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    public static RoomMap getRoomMap(){
        RoomMap result = new RoomMap();
        try {
            if(con == null || con.isClosed()) connectToDb();
            String query = "SELECT * FROM room;";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()){
                result.putCapacity(rs.getInt("id"), rs.getInt("capacity"));
                result.putBuildingId(rs.getInt("id"), rs.getInt("building_id"));
            }
            String query2 = "SELECT * FROM room_feature;";
            Statement stmt2 = con.createStatement();
            ResultSet rs2 = stmt2.executeQuery(query2);
            while(rs2.next()){
                result.putFeature(rs2.getInt("room_id"), rs2.getInt("feature_id"));
            }


        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }


    public static ArrayList<Integer> getRoomIdList(){
        ArrayList<Integer> result = new ArrayList<>();
        try {
            if(con == null || con.isClosed()) connectToDb();
            String query = "SELECT * FROM room;";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()){
                result.add(rs.getInt("id"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    public static StudentLoadMap getStudentLoadMap(){
        StudentLoadMap studentLoadMap = new StudentLoadMap();
        try {
            if(con == null || con.isClosed()) connectToDb();
            String query = "SELECT * FROM cohort;";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()){
                studentLoadMap.setMinHoursOfCohort(rs.getInt("id"), rs.getInt("min_hours"));
                studentLoadMap.setMaxHoursOfCohort(rs.getInt("id"), rs.getInt("max_hours"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return studentLoadMap;
    }


    public static void writeResultsToDb(CourseSchedule courseSchedule){
        try {
            if(con == null || con.isClosed()) connectToDb();
            String query = "DELETE FROM results;";
            Statement stmt = con.createStatement();
            stmt.executeUpdate(query);
            for(Event event: courseSchedule.getEventList()){
                query = "INSERT INTO results (event_id, room_id, timeslot, day) VALUES ('"+event.getId()+"','"+event.getRoomId()+"','"+event.getTimeSlot()+"','"+event.getDay()+"');";
                stmt.executeUpdate(query);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    static class UniqueIdGenerator{
        private static Integer currentNumber = -1;
        static Integer generateUniqueId(){
            currentNumber++;
            return currentNumber;
        }
    }

}
