package de.jensk.optaPlannerHsOsna.CustomMoves;

import de.jensk.optaPlannerHsOsna.Event;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.optaplanner.core.impl.heuristic.move.AbstractMove;
import org.optaplanner.core.impl.score.director.ScoreDirector;

import java.util.Arrays;
import java.util.Collection;

public class DoubleSwapFullEventMove extends AbstractMove {

    private Event event1;
    private Event event2;
    private Event event3;
    private Event event4;
    private Integer toDay1;
    private Integer toDay2;
    private Integer toDay3;
    private Integer toDay4;
    private Integer toTimeSlot1;
    private Integer toTimeSlot2;
    private Integer toTimeSlot3;
    private Integer toTimeSlot4;
    private Integer toRoom1;
    private Integer toRoom2;
    private Integer toRoom3;
    private Integer toRoom4;

    public DoubleSwapFullEventMove(Event event1, Event event2, Event event3, Event event4, Integer toDay1, Integer toDay2, Integer toDay3, Integer toDay4,
                                   Integer toTimeSlot1, Integer toTimeSlot2, Integer toTimeSlot3, Integer toTimeSlot4, Integer toRoom1, Integer toRoom2,
                                   Integer toRoom3, Integer toRoom4) {
        this.event1 = event1;
        this.event2 = event2;
        this.event3 = event3;
        this.event4 = event4;
        this.toDay1 = toDay1;
        this.toDay2 = toDay2;
        this.toDay3 = toDay3;
        this.toDay4 = toDay4;
        this.toTimeSlot1 = toTimeSlot1;
        this.toTimeSlot2 = toTimeSlot2;
        this.toTimeSlot3 = toTimeSlot3;
        this.toTimeSlot4 = toTimeSlot4;
        this.toRoom1 = toRoom1;
        this.toRoom2 = toRoom2;
        this.toRoom3 = toRoom3;
        this.toRoom4 = toRoom4;
    }

    @Override
    public boolean isMoveDoable(ScoreDirector scoreDirector) {
        return true;
    }

    @Override
    protected void doMoveOnGenuineVariables(ScoreDirector scoreDirector) {
        scoreDirector.beforeVariableChanged(event1, "day");
        scoreDirector.beforeVariableChanged(event1, "timeSlot");
        scoreDirector.beforeVariableChanged(event1,"roomId");
        scoreDirector.beforeVariableChanged(event2, "day");
        scoreDirector.beforeVariableChanged(event2, "timeSlot");
        scoreDirector.beforeVariableChanged(event2,"roomId");
        scoreDirector.beforeVariableChanged(event3, "day");
        scoreDirector.beforeVariableChanged(event3, "timeSlot");
        scoreDirector.beforeVariableChanged(event3,"roomId");
        scoreDirector.beforeVariableChanged(event4, "day");
        scoreDirector.beforeVariableChanged(event4, "timeSlot");
        scoreDirector.beforeVariableChanged(event4,"roomId");
        event1.setDay(toDay1);
        event1.setTimeSlot(toTimeSlot1);
        event1.setRoomId(toRoom1);
        event2.setDay(toDay2);
        event2.setTimeSlot(toTimeSlot2);
        event2.setRoomId(toRoom2);
        event3.setDay(toDay3);
        event3.setTimeSlot(toTimeSlot3);
        event3.setRoomId(toRoom3);
        event4.setDay(toDay4);
        event4.setTimeSlot(toTimeSlot4);
        event4.setRoomId(toRoom4);
        scoreDirector.afterVariableChanged(event1, "day");
        scoreDirector.afterVariableChanged(event1, "timeSlot");
        scoreDirector.afterVariableChanged(event1, "roomId");
        scoreDirector.afterVariableChanged(event2, "day");
        scoreDirector.afterVariableChanged(event2, "timeSlot");
        scoreDirector.afterVariableChanged(event2, "roomId");
        scoreDirector.afterVariableChanged(event3, "day");
        scoreDirector.afterVariableChanged(event3, "timeSlot");
        scoreDirector.afterVariableChanged(event3, "roomId");
        scoreDirector.afterVariableChanged(event4, "day");
        scoreDirector.afterVariableChanged(event4, "timeSlot");
        scoreDirector.afterVariableChanged(event4, "roomId");
    }

    @Override
    public DoubleSwapFullEventMove createUndoMove(ScoreDirector scoreDirector) {
        DoubleSwapFullEventMove doubleSwapFullEventMove = new DoubleSwapFullEventMove(event1, event2, event3, event4, event1.getDay(), event2.getDay(),
                event3.getDay(), event4.getDay(), event1.getTimeSlot(), event2.getTimeSlot(), event3.getTimeSlot(), event4.getTimeSlot(),
                event1.getRoomId(), event2.getRoomId(), event3.getRoomId(), event4.getRoomId());
        return doubleSwapFullEventMove;
    }

    @Override
    public DoubleSwapFullEventMove rebase(ScoreDirector destinationScoreDirector) {
        return new DoubleSwapFullEventMove(
                (Event)destinationScoreDirector.lookUpWorkingObject(event1),(Event)destinationScoreDirector.lookUpWorkingObject(event2),
                (Event)destinationScoreDirector.lookUpWorkingObject(event3),(Event)destinationScoreDirector.lookUpWorkingObject(event4),
                (Integer)destinationScoreDirector.lookUpWorkingObject(toDay1), (Integer)destinationScoreDirector.lookUpWorkingObject(toDay2),
                (Integer)destinationScoreDirector.lookUpWorkingObject(toDay3), (Integer)destinationScoreDirector.lookUpWorkingObject(toDay4),
                (Integer)destinationScoreDirector.lookUpWorkingObject(toTimeSlot1), (Integer)destinationScoreDirector.lookUpWorkingObject(toTimeSlot2),
                (Integer)destinationScoreDirector.lookUpWorkingObject(toTimeSlot3), (Integer)destinationScoreDirector.lookUpWorkingObject(toTimeSlot4),
                (Integer)destinationScoreDirector.lookUpWorkingObject(toRoom1),(Integer)destinationScoreDirector.lookUpWorkingObject(toRoom2),
                (Integer)destinationScoreDirector.lookUpWorkingObject(toRoom3),(Integer)destinationScoreDirector.lookUpWorkingObject(toRoom4));
    }

    @Override
    public Collection<?> getPlanningEntities() {
        return Arrays.asList(event1, event2, event3, event4);
    }

    @Override
    public Collection<?> getPlanningValues() {
        return Arrays.asList(toDay1, toTimeSlot1, toRoom1, toDay2, toTimeSlot2, toRoom2, toDay3, toTimeSlot3, toRoom3, toDay4, toTimeSlot4, toRoom4);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o instanceof DoubleSwapFullEventMove) {
            DoubleSwapFullEventMove other = (DoubleSwapFullEventMove) o;
            return new EqualsBuilder()
                    .append(event1, other.event1)
                    .append(event2, other.event2)
                    .append(event3, other.event3)
                    .append(event4, other.event4)
                    .append(toDay1, other.toDay1)
                    .append(toDay2, other.toDay2)
                    .append(toDay3, other.toDay3)
                    .append(toDay4, other.toDay4)
                    .append(toTimeSlot1, other.toTimeSlot1)
                    .append(toTimeSlot2, other.toTimeSlot2)
                    .append(toTimeSlot3, other.toTimeSlot3)
                    .append(toTimeSlot4, other.toTimeSlot4)
                    .append(toRoom1, other.toRoom1)
                    .append(toRoom2, other.toRoom2)
                    .append(toRoom3, other.toRoom3)
                    .append(toRoom4, other.toRoom4)
                    .isEquals();
        } else {
            return false;
        }
    }


    public int hashCode() {
        return new HashCodeBuilder()
                .append(event1)
                .append(event2)
                .append(event3)
                .append(event4)
                .append(toDay1)
                .append(toDay2)
                .append(toDay3)
                .append(toDay4)
                .append(toTimeSlot1)
                .append(toTimeSlot2)
                .append(toTimeSlot3)
                .append(toTimeSlot4)
                .append(toRoom1)
                .append(toRoom2)
                .append(toRoom3)
                .append(toRoom4)
                .toHashCode();
    }


}
