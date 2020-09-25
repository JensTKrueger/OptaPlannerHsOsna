package de.jensk.optaPlannerHsOsna;

import org.optaplanner.core.api.solver.Solver;
import org.optaplanner.core.api.solver.SolverFactory;

import java.util.List;

public class App {

    public static void main(String[] args) {

        CourseSchedule unsolvedSchedule = new CourseSchedule();
        unsolvedSchedule.setRoomList(DbConnector.getRoomIdList());
        unsolvedSchedule.setEventList(DbConnector.getEventList());
        unsolvedSchedule.setTimePreferenceMap(DbConnector.getTimePreferenceMap());
        unsolvedSchedule.setRoomMap(DbConnector.getRoomMap());



        SolverFactory<CourseSchedule> solverFactory = SolverFactory
        .createFromXmlResource("de/jensk/optaPlannerHsOsna/solverConfig.xml");
        Solver solver = solverFactory.buildSolver();

        CourseSchedule solved = (CourseSchedule) solver.solve(unsolvedSchedule);

        DbConnector.writeResultsToDb(solved);
    }
}
