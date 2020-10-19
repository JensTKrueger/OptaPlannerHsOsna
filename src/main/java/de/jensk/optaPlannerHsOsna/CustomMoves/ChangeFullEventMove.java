package de.jensk.optaPlannerHsOsna.CustomMoves;

import de.jensk.optaPlannerHsOsna.Event;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.optaplanner.core.impl.heuristic.move.AbstractMove;
import org.optaplanner.core.impl.score.director.ScoreDirector;

import java.util.*;

public class ChangeFullEventMove extends AbstractMove {

    private Event event;
    private Integer toDay;
    private Integer toTimeSlot;
    private Integer toRoom;

    public ChangeFullEventMove(Event event, Integer toDay, Integer toTimeSlot, Integer toRoom) {
        this.event = event;
        this.toDay = toDay;
        this.toTimeSlot = toTimeSlot;
        this.toRoom = toRoom;
    }

    @Override
    public boolean isMoveDoable(ScoreDirector scoreDirector) {
        return true;
    }


    @Override
    public ChangeFullEventMove createUndoMove(ScoreDirector scoreDirector) {
        ChangeFullEventMove undoMove = new ChangeFullEventMove(event, event.getDay(), event.getTimeSlot(), event.getRoomId());
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
    public ChangeFullEventMove rebase(ScoreDirector destinationScoreDirector) {
        return new ChangeFullEventMove((Event)destinationScoreDirector.lookUpWorkingObject(event),
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
        } else if (o instanceof ChangeFullEventMove) {
            ChangeFullEventMove other = (ChangeFullEventMove) o;
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