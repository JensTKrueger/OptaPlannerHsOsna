package de.jensk.optaPlannerHsOsna;

import de.jensk.optaPlannerHsOsna.InformationMaps.RoomSpecificationsMap;
import de.jensk.optaPlannerHsOsna.InformationMaps.StudentLoadMap;
import de.jensk.optaPlannerHsOsna.InformationMaps.TimePreferenceMap;
import org.apache.ibatis.jdbc.ScriptRunner;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * This class is used for all types of communcation with the database.
 */
public final class DbConnector {

    /**
     * The name of the database.
     */
    private static String dbName = "schedule_data";


    /**
     * The connection to the database.
     */
    private static Connection con;

    public static void setDbName(String newDbName) {
        dbName = newDbName;
    }

    /**
     * Generates an url to the database.
     * @param includeDbName If set to true, includes the dbname in the url.
     * @return The URL to the database as a String
     */
    private static String getDbUrl(boolean includeDbName) {
        return "jdbc:mysql://localhost/" + (includeDbName ? dbName : "")
                + "?user=optaplanner&password=optaplanner";
    }

    static {
        try {
            //makes sure, the jdbc driver is loaded
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    private DbConnector() {
        throw new RuntimeException("This class should not be instantiated.");
    }

    /**
     * Connects to the Database and stores the connection in con.
     * @param connectWithoutSpecificDb Set to true if the connection
     * should not target a specific database.
     */
    private static void connectToDb(boolean connectWithoutSpecificDb) {
        try {
            if (con != null && !con.isClosed()) {
                con.close();
            }
            if (!connectWithoutSpecificDb) {
                con = DriverManager.getConnection(getDbUrl(true));
            } else {
                con = DriverManager.getConnection(getDbUrl(false));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void createDataBase() {
        try {
            connectToDb(true);
            InputStream sqlInputStream = App.class.getClassLoader().getResourceAsStream(
                    "de/jensk/optaPlannerHsOsna/create_database.sql");
            ScriptRunner sr = new ScriptRunner(con);
            sr.setLogWriter(null);
            Reader reader = new BufferedReader(new InputStreamReader(sqlInputStream));
            sr.runScript(reader);
            con.close();
            sqlInputStream.close();
            System.out.println("==================================");
            System.out.println("Since the database " + dbName + " was not found,"
                    + " this seems to be the first time this application has been started.");
            System.out.println("The database and a default account with administrative"
                    + " privileges has been created.");
            System.out.println("Use this account only to create the first real account and"
                    + " delete it immediately afterwards.\n");
            System.out.println("Default username: admin@admin.de");
            System.out.println("Default password: admin");
            System.out.println("==================================");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Checks if the database already exists. If it doesnt that means this is the first start
     * of this application so it creates the database.
     */
    public static void checkIfDataBaseExistsAndCreateIfNot() {
        try {
            connectToDb(true);
            String query = "SELECT SCHEMA_NAME"
                    + " FROM INFORMATION_SCHEMA.SCHEMATA"
                    + " WHERE SCHEMA_NAME = '" + dbName + "';";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if (!rs.next()) {
                createDataBase();
            }
            con.close();
        } catch (Exception e) {

        }
    }


    /**
     * Connects to the database and requests data from the timepreference table. <br>
     * This data is stored in a new TimePreferenceMap.
     * @return The filled TimePreferenceMap.
     */
    public static TimePreferenceMap getTimePreferenceMap() {
        TimePreferenceMap result = new TimePreferenceMap();
        try {
            connectToDb(false);
            String query = "SELECT * FROM timepreference;";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                result.putPreference(rs.getInt("user_id"), rs.getInt("day"),
                        rs.getInt("timeslot"), rs.getInt("pref"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Connects to the database and requests data from the communication table. <br>
     * Checks if a command was issued and deletes the command from the database
     * before returning it.
     * Only one command per method call will be returned and deleted.
     * @return Null if no new command was issued. The command string if a command was found.
     */
    public static String getCommand() {
        try {
            connectToDb(false);
            String query = "SELECT * FROM communication ORDER BY id;";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                String command = rs.getString("command");
                String query2 = "DELETE FROM communication WHERE id = '" + rs.getInt("id") + "';";
                Statement stmt2 = con.createStatement();
                stmt2.executeUpdate(query2);
                return command;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Connects to the database and requests all data regarding events from multiple tables. <br>
     * Create an ArrayList containing all events.
     * The events are filled with every information they need.
     * @return The ArrayList containing all events which are stored in the database.
     */
    public static ArrayList<Event> getEventList() {
        ArrayList<Event> result = new ArrayList<>();
        try {
            connectToDb(false);
            String query = "SELECT * FROM event;";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                /*
                This for loop creates events multiple times
                if they are scheduled more than once a week.
                */
                for (int perWeekCount = 0;
                     perWeekCount < rs.getInt("hours_per_week"); perWeekCount++) {
                    Event currentEvent = new Event();
                    currentEvent.setGroupSize(0);
                    currentEvent.setHpm(rs.getInt("hours_per_meeting"));
                    currentEvent.setHpw(rs.getInt("hours_per_week"));
                    currentEvent.setHard(rs.getBoolean("is_hard"));
                    currentEvent.setId(rs.getInt("id"));
                    currentEvent.setUniqueId(UniqueIdGenerator.generateUniqueId());

                    //from here inner rs that manages all teachers
                    ArrayList<Integer> teacherList = new ArrayList<>();
                    String innerQuery1 =
                            "SELECT * FROM teacher_event WHERE event_id='" + rs.getInt("id") + "';";
                    Statement innerStmt1 = con.createStatement();
                    ResultSet innerRs1 = innerStmt1.executeQuery(innerQuery1);
                    while (innerRs1.next()) {
                        teacherList.add(innerRs1.getInt("teacher_id"));
                    }
                    currentEvent.setTeacherIds(teacherList);

                    //from here inner rs that manages all features
                    ArrayList<Integer> featureList = new ArrayList<>();
                    String innerQuery2 =
                            "SELECT * FROM event_feature WHERE event_id='" + rs.getInt("id") + "';";
                    Statement innerStmt2 = con.createStatement();
                    ResultSet innerRs2 = innerStmt2.executeQuery(innerQuery2);
                    while (innerRs2.next()) {
                        featureList.add(innerRs2.getInt("feature_id"));
                    }
                    currentEvent.setFeatureIds(featureList);

                    //from here inner rs than manages all studygroups
                    ArrayList<StudyGroup> studyGroups = new ArrayList<>();
                    String innerQuery3 = "SELECT * FROM event_participation "
                            + "JOIN special ON special.special_id=event_participation.special_id "
                            + "WHERE event_id='" + rs.getInt("id") + "';";
                    Statement innerStmt3 = con.createStatement();
                    ResultSet innerRs3 = innerStmt3.executeQuery(innerQuery3);
                    while (innerRs3.next()) {
                        StudyGroup currentGroup = new StudyGroup();
                        currentGroup.setSpecialId(innerRs3.getInt("special_id"));
                        currentGroup.setSpecialCatId(innerRs3.getInt("special_cat_id"));
                        currentGroup.setCohortId(innerRs3.getInt("cohort_id"));
                        studyGroups.add(currentGroup);
                        currentEvent.setGroupSize(currentEvent.getGroupSize()
                                + innerRs3.getInt("size"));
                    }
                    currentEvent.setStudyGroups(studyGroups);

                    result.add(currentEvent);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Connects to the database and requests data from the tables room and room_feature.<br>
     * This data is stored in a new TimePreferenceMap.
     * @return The filled RoomSpecificationsMap.
     */
    public static RoomSpecificationsMap getRoomMap() {
        RoomSpecificationsMap result = new RoomSpecificationsMap();
        try {
            connectToDb(false);
            String query = "SELECT * FROM room;";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                result.putCapacity(rs.getInt("id"), rs.getInt("capacity"));
                result.putBuildingId(rs.getInt("id"), rs.getInt("building_id"));
            }
            String query2 = "SELECT * FROM room_feature;";
            Statement stmt2 = con.createStatement();
            ResultSet rs2 = stmt2.executeQuery(query2);
            while (rs2.next()) {
                result.putFeature(rs2.getInt("room_id"), rs2.getInt("feature_id"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Connects to the database and requests all roomIds from the table room.<br>
     * This data is stored in a new ArrayList.
     * @return The filled ArrayList containing all roomIds.
     */
    public static ArrayList<Integer> getRoomIdList() {
        ArrayList<Integer> result = new ArrayList<>();
        try {
            connectToDb(false);
            String query = "SELECT * FROM room;";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                result.add(rs.getInt("id"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Connects to the database and requests the minimum and maximum amount
     * of hours for each cohort per day from the table cohort.<br>
     * This data is stored in a new StudentLoadMap.
     * @return The filled StudentLoadMap.
     */
    public static StudentLoadMap getStudentLoadMap() {
        StudentLoadMap studentLoadMap = new StudentLoadMap();
        try {
            connectToDb(false);
            String query = "SELECT * FROM cohort;";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                studentLoadMap.setMinHoursOfCohort(rs.getInt("id"), rs.getInt("min_hours"));
                studentLoadMap.setMaxHoursOfCohort(rs.getInt("id"), rs.getInt("max_hours"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return studentLoadMap;
    }

    /**
     * This method should be called after the solving process.<br>
     * It connects to the database, deletes old results and writes the results to the results table.
     * @param courseSchedule The solved course schedule that will be written to the database.
     */
    public static void writeResultsToDb(CourseSchedule courseSchedule) {
        try {
            connectToDb(false);
            String query = "DELETE FROM results;";
            Statement stmt = con.createStatement();
            stmt.executeUpdate(query);
            for (Event event : courseSchedule.getEventList()) {
                query = "INSERT INTO results (event_id, room_id, timeslot, day) "
                        + "VALUES ('" + event.getId() + "','" + event.getRoomId() + "','"
                        + event.getTimeSlot() + "','" + event.getDay() + "');";
                stmt.executeUpdate(query);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * A helper class, that can generate unique ids.
     */
    private static final class UniqueIdGenerator {
        /**
         * The number that will be given to the next method that requests an unique id.
         */
        private static int currentNumber = 0;

        /**
         * Generates a unique number that will only be generated once.
         * @return The generated unique number.
         */
        static int generateUniqueId() {
            return currentNumber++;
        }
    }

}
