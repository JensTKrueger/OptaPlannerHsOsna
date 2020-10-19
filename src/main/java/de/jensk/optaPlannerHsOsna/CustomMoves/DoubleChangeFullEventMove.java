package de.jensk.optaPlannerHsOsna.CustomMoves;

import de.jensk.optaPlannerHsOsna.Event;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.optaplanner.core.impl.heuristic.move.AbstractMove;
import org.optaplanner.core.impl.score.director.ScoreDirector;

import java.util.Arrays;
import java.util.Collection;

/**
 * This Move can be performed with the Event entities with the same id.
 */
public class DoubleChangeFullEventMove extends AbstractMove {

    private Event event1;
    private Event event2;
    private Integer toDay1;
    private Integer toDay2;
    private Integer toTimeSlot1;
    private Integer toTimeSlot2;
    private Integer toRoom1;
    private Integer toRoom2;

    public DoubleChangeFullEventMove(Event event1, Event event2, Integer toDay1, Integer toDay2, Integer toTimeSlot1, Integer toTimeSlot2, Integer toRoom1, Integer toRoom2) {
        this.event1 = event1;
        this.event2 = event2;
        this.toDay1 = toDay1;
        this.toDay2 = toDay2;
        this.toTimeSlot1 = toTimeSlot1;
        this.toTimeSlot2 = toTimeSlot2;
        this.toRoom1 = toRoom1;
        this.toRoom2 = toRoom2;
    }


    @Override
    public boolean isMoveDoable(ScoreDirector scoreDirector) {
        return true;
    }

    @Override
    protected AbstractMove createUndoMove(ScoreDirector scoreDirector) {
        return new DoubleChangeFullEventMove(event1, event2, event1.getDay(), event2.getDay(), event1.getTimeSlot(), event2.getTimeSlot(), event1.getRoomId(), event2.getRoomId());
    }

    @Override
    protected void doMoveOnGenuineVariables(ScoreDirector scoreDirector) {
        scoreDirector.beforeVariableChanged(event1, "day");
        scoreDirector.beforeVariableChanged(event1, "timeSlot");
        scoreDirector.beforeVariableChanged(event1,"roomId");
        scoreDirector.beforeVariableChanged(event2, "day");
        scoreDirector.beforeVariableChanged(event2, "timeSlot");
        scoreDirector.beforeVariableChanged(event2,"roomId");
        event1.setDay(toDay1);
        event1.setTimeSlot(toTimeSlot1);
        event1.setRoomId(toRoom1);
        event2.setDay(toDay2);
        event2.setTimeSlot(toTimeSlot2);
        event2.setRoomId(toRoom2);
        scoreDirector.afterVariableChanged(event1, "day");
        scoreDirector.afterVariableChanged(event1, "timeSlot");
        scoreDirector.afterVariableChanged(event1, "roomId");
        scoreDirector.afterVariableChanged(event2, "day");
        scoreDirector.afterVariableChanged(event2, "timeSlot");
        scoreDirector.afterVariableChanged(event2, "roomId");
    }

    @Override
    public DoubleChangeFullEventMove rebase(ScoreDirector destinationScoreDirector) {
        return new DoubleChangeFullEventMove(
                (Event)destinationScoreDirector.lookUpWorkingObject(event1),(Event)destinationScoreDirector.lookUpWorkingObject(event2),
                (Integer)destinationScoreDirector.lookUpWorkingObject(toDay1), (Integer)destinationScoreDirector.lookUpWorkingObject(toDay2),
                (Integer)destinationScoreDirector.lookUpWorkingObject(toTimeSlot1), (Integer)destinationScoreDirector.lookUpWorkingObject(toTimeSlot2),
                (Integer)destinationScoreDirector.lookUpWorkingObject(toRoom1),(Integer)destinationScoreDirector.lookUpWorkingObject(toRoom2));
    }


    @Override
    public Collection<?> getPlanningEntities() {
        return Arrays.asList(event1, event2);
    }

    @Override
    public Collection<?> getPlanningValues() {
        return Arrays.asList(toDay1, toTimeSlot1, toRoom1, toDay2, toTimeSlot2, toRoom2);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o instanceof DoubleChangeFullEventMove) {
            DoubleChangeFullEventMove other = (DoubleChangeFullEventMove) o;
            return new EqualsBuilder()
                    .append(event1, other.event1)
                    .append(event2, other.event2)
                    .append(toDay1, other.toDay1)
                    .append(toDay2, other.toDay2)
                    .append(toTimeSlot1, other.toTimeSlot1)
                    .append(toTimeSlot2, other.toTimeSlot2)
                    .append(toRoom1, other.toRoom1)
                    .append(toRoom2, other.toRoom2)
                    .isEquals();
        } else {
            return false;
        }
    }


    public int hashCode() {
        return new HashCodeBuilder()
                .append(event1)
                .append(event2)
                .append(toDay1)
                .append(toDay2)
                .append(toTimeSlot1)
                .append(toTimeSlot2)
                .append(toRoom1)
                .append(toRoom2)
                .toHashCode();
    }


}
