package de.jensk.optaPlannerHsOsna.Customs;

import de.jensk.optaPlannerHsOsna.Event;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.optaplanner.core.impl.heuristic.move.AbstractMove;
import org.optaplanner.core.impl.score.director.ScoreDirector;

import java.util.*;

public class ChangeDayTimeRoomMove extends AbstractMove {

    private Event event;
    private Integer toDay;
    private Integer toTimeSlot;
    private Integer toRoom;

    public ChangeDayTimeRoomMove(Event event, Integer toDay, Integer toTimeSlot, Integer toRoom) {
        this.event = event;
        this.toDay = toDay;
        this.toTimeSlot = toTimeSlot;
        this.toRoom = toRoom;
    }

    @Override
    public boolean isMoveDoable(ScoreDirector scoreDirector) {
        return !toDay.equals(event.getDay()) || !toTimeSlot.equals(event.getTimeSlot()) || !toRoom.equals(event.getRoomId());
    }


    @Override
    public ChangeDayTimeRoomMove createUndoMove(ScoreDirector scoreDirector) {
        ChangeDayTimeRoomMove undoMove = new ChangeDayTimeRoomMove(event, event.getDay(), event.getTimeSlot(), event.getRoomId());
        return undoMove;
    }

    @Override
    protected void doMoveOnGenuineVariables(ScoreDirector scoreDirector) {
        scoreDirector.beforeVariableChanged(event, "day");
        scoreDirector.beforeVariableChanged(event, "timeSlot");
        scoreDirector.beforeVariableChanged(event,"roomId");
        event.setDay(toDay);
        event.setTimeSlot(toTimeSlot);
        event.setRoomId(toRoom);
        scoreDirector.afterVariableChanged(event, "day");
        scoreDirector.afterVariableChanged(event, "timeSlot");
        scoreDirector.afterVariableChanged(event, "roomId");
    }


    @Override
    public ChangeDayTimeRoomMove rebase(ScoreDirector destinationScoreDirector) {
        return new ChangeDayTimeRoomMove((Event)destinationScoreDirector.lookUpWorkingObject(event),
                (Integer)destinationScoreDirector.lookUpWorkingObject(toDay), (Integer)destinationScoreDirector.lookUpWorkingObject(toTimeSlot),
                (Integer)destinationScoreDirector.lookUpWorkingObject(toRoom));
    }

    @Override
    public Collection<?> getPlanningEntities() {
        return Collections.singletonList(event);
    }

    @Override
    public Collection<?> getPlanningValues() {
        return Arrays.asList(toDay, toTimeSlot, toRoom);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o instanceof ChangeDayTimeRoomMove) {
            ChangeDayTimeRoomMove other = (ChangeDayTimeRoomMove) o;
            return new EqualsBuilder()
                    .append(event, other.event)
                    .append(toDay, other.toDay)
                    .append(toTimeSlot, other.toTimeSlot)
                    .append(toRoom, other.toRoom)
                    .isEquals();
        } else {
            return false;
        }
    }


    public int hashCode() {
        return new HashCodeBuilder()
                .append(event)
                .append(toDay)
                .append(toTimeSlot)
                .append(toRoom)
                .toHashCode();
    }
}