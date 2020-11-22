package de.jensk.optaPlannerHsOsna;

import org.optaplanner.core.api.solver.Solver;
import org.optaplanner.core.api.solver.SolverFactory;


/**
 * The class App has the main method which checks for
 * commands and the start method which solves a schedule.
 */
public final class App {

    /**
     * This int value defines how long will be waited before
     * issuing a new request to the database, whether a
     * valid command has been written or not. The time
     * has to be provided in milliseconds.
     */
    private static final int TIME_BETWEEN_DATABASE_CHECKS = 5000;

    /**
     * The starting point of the program. Checks every x (default = 5) Seconds,
     * whether the command "start_calculation" has been written to the database
     * and start the schedule generation if it was.
     * Exits if the command "exit" was written to the database.
     * @param args This parameter is not used.
     */
    public static void main(final String[] args) {
        DbConnector.checkIfDataBaseExistsAndCreateIfNot();
        while (true) {
            try {
                Thread.sleep(TIME_BETWEEN_DATABASE_CHECKS);
                String command = DbConnector.getCommand();
                if (command != null) {
                    if (command.equals("start_calculation")) {
                        startSolving();
                    } else if (command.equals("exit")) {
                        System.exit(0);
                    } else {
                        throw new RuntimeException("Unknown command: " + command);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Everything is loaded from the database and a new CourseSchedule,
     * containing all the information is created and solved to create
     * the best fitting solution. This solution is written to the database afterwards.
     */
    private static void startSolving() {
        CourseSchedule unsolvedSchedule = new CourseSchedule();
        unsolvedSchedule.setRoomList(DbConnector.getRoomIdList());
        unsolvedSchedule.setEventList(DbConnector.getEventList());
        unsolvedSchedule.setTimePreferenceMap(DbConnector.getTimePreferenceMap());
        unsolvedSchedule.setRoomMap(DbConnector.getRoomMap());
        unsolvedSchedule.setStudentLoadMap(DbConnector.getStudentLoadMap());
        SolverFactory<CourseSchedule> solverFactory = SolverFactory.createFromXmlResource(
                        "de/jensk/optaPlannerHsOsna/solverConfig.xml");
        Solver<CourseSchedule> solver = solverFactory.buildSolver();
        CourseSchedule solvedSchedule = solver.solve(unsolvedSchedule);
        DbConnector.writeResultsToDb(solvedSchedule);
    }

    private App() {
        throw new RuntimeException("This is the main class and it should not be instantiated.");
    }
}
