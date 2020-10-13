package de.jensk.optaPlannerHsOsna.Customs;

import de.jensk.optaPlannerHsOsna.CourseSchedule;
import de.jensk.optaPlannerHsOsna.Event;
import org.optaplanner.core.impl.heuristic.move.Move;
import org.optaplanner.core.impl.heuristic.selector.move.factory.MoveListFactory;

import java.util.ArrayList;
import java.util.List;

@Deprecated
public class ChangeDayTimeRoomMoveFactory implements MoveListFactory {

    @Override
    public List<? extends Move> createMoveList(Object o) {
        CourseSchedule courseSchedule = (CourseSchedule) o;
        List<Move> moveList = new ArrayList<Move>();
        for (Event event : courseSchedule.getEventList()) {
            for(Integer day: courseSchedule.getDayList()) {
                for (Integer timeSlot : courseSchedule.getTimeSlotList()) {
                    for (Integer roomId : courseSchedule.getRoomList()) {
                        moveList.add(new ChangeDayTimeRoomMove(event, day, timeSlot, roomId));
                    }
                }
            }
        }
        return moveList;
    }

}
