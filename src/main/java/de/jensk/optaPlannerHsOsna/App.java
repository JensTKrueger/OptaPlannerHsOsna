package de.jensk.optaPlannerHsOsna;

import org.optaplanner.core.api.score.ScoreManager;
import org.optaplanner.core.api.solver.Solver;
import org.optaplanner.core.api.solver.SolverFactory;

import java.util.List;

public class App {

    public static void main(String[] args) {
        while(true){
            try {
                Thread.sleep(5000);
                String command = DbConnector.getCommand();
                if(command != null && command.equals("start_calculation")){
                    start();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public static void start(){
        CourseSchedule unsolvedSchedule = new CourseSchedule();
        unsolvedSchedule.setRoomList(DbConnector.getRoomIdList());
        unsolvedSchedule.setEventList(DbConnector.getEventList());
        unsolvedSchedule.setTimePreferenceMap(DbConnector.getTimePreferenceMap());
        unsolvedSchedule.setRoomMap(DbConnector.getRoomMap());
        unsolvedSchedule.setStudentLoadMap(DbConnector.getStudentLoadMap());
        unsolvedSchedule.setCustomScoreMethodHolder(new CustomScoreMethodHolder());
        SolverFactory<CourseSchedule> solverFactory = SolverFactory
                .createFromXmlResource("de/jensk/optaPlannerHsOsna/solverConfig.xml");
        Solver solver = solverFactory.buildSolver();
        CourseSchedule solved = (CourseSchedule) solver.solve(unsolvedSchedule);
        DbConnector.writeResultsToDb(solved);
        /*
        ScoreManager<CourseSchedule> manager = ScoreManager.create(solverFactory);
        System.out.println(manager.explainScore(solved));
        */

    }
}
