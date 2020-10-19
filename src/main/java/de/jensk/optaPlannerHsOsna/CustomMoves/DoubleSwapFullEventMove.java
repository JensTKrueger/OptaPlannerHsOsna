package de.jensk.optaPlannerHsOsna.CustomMoves;

import de.jensk.optaPlannerHsOsna.CourseSchedule;
import de.jensk.optaPlannerHsOsna.Event;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.optaplanner.core.impl.heuristic.move.AbstractMove;
import org.optaplanner.core.impl.score.director.ScoreDirector;
import java.util.Arrays;
import java.util.Collection;

/**
 * This class represents a custom move that can swap two sets of events.<br>
 * Each set contains two events having the same id.<br>
 * A swap means that the first event of the first set swaps
 * day, timeslot and room with the first event of the second set.<br>
 * Its the same procedure for the second events of each set.<br>
 */
public class DoubleSwapFullEventMove extends AbstractMove<CourseSchedule> {

    /**
     * The first event that will be changed in this move.
     */
    private final Event event1;

    /**
     * The second event that will be changed in this move.
     */
    private final Event event2;

    /**
     * The third event that will be changed in this move.
     */
    private final Event event3;

    /**
     * The fourth event that will be changed in this move.
     */
    private final Event event4;

    /**
     * The day, the first event will be moved to.
     */
    private final Integer toDay1;

    /**
     * The day, the second event will be moved to.
     */
    private final Integer toDay2;

    /**
     * The day, the third event will be moved to.
     */
    private final Integer toDay3;

    /**
     * The day, the fourth event will be moved to.
     */
    private final Integer toDay4;

    /**
     * The timeslot, the first event will be moved to.
     */
    private final Integer toTimeSlot1;

    /**
     * The timeslot, the second event will be moved to.
     */
    private final Integer toTimeSlot2;

    /**
     * The timeslot, the third event will be moved to.
     */
    private final Integer toTimeSlot3;

    /**
     * The timeslot, the fourth event will be moved to.
     */
    private final Integer toTimeSlot4;

    /**
     * The room, the first event will be moved to.
     */
    private final Integer toRoom1;
    /**
     * The room, the second event will be moved to.
     */
    private final Integer toRoom2;

    /**
     * The room, the third event will be moved to.
     */
    private final Integer toRoom3;

    /**
     * The room, the fourth event will be moved to.
     */
    private final Integer toRoom4;

    /**
     * Constructor for the DoubleSwapFullEventMove.
     * @param event1 The first event that will be changed in this move.
     * @param event2 The second event that will be changed in this move.
     * @param event3 The third event that will be changed in this move.
     * @param event4 The fourth event that will be changed in this move.
     * @param toDay1 The day, the first event will be moved to.
     * @param toDay2 The day, the second event will be moved to.
     * @param toDay3 The day, the third event will be moved to.
     * @param toDay4 The day, the fourth event will be moved to.
     * @param toTimeSlot1 The timeslot, the first event will be moved to.
     * @param toTimeSlot2 The timeslot, the second event will be moved to.
     * @param toTimeSlot3 The timeslot, the third event will be moved to.
     * @param toTimeSlot4 The timeslot, the fourth event will be moved to.
     * @param toRoom1 The room, the first event will be moved to.
     * @param toRoom2 The room, the second event will be moved to.
     * @param toRoom3 The room, the third event will be moved to.
     * @param toRoom4 The room, the fourth event will be moved to.
     */
    public DoubleSwapFullEventMove(Event event1, Event event2, Event event3, Event event4,
                                   Integer toDay1, Integer toDay2,
                                   Integer toDay3, Integer toDay4,
                                   Integer toTimeSlot1, Integer toTimeSlot2,
                                   Integer toTimeSlot3, Integer toTimeSlot4,
                                   Integer toRoom1, Integer toRoom2,
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

    /**
     * Checks whether the move is doable. In this case it always returns true
     * because the factory will not create illegal moves in the first place.
     * @param scoreDirector The current ScoreDirector.
     * @return Returns whether the move is doable.
     */
    @Override
    public boolean isMoveDoable(ScoreDirector scoreDirector) {
        return true;
    }

    /**
     * Executes the current move and informs the ScoreDirector.
     * @param scoreDirector The current ScoreDirector.
     */
    @Override
    protected void doMoveOnGenuineVariables(ScoreDirector scoreDirector) {
        scoreDirector.beforeVariableChanged(event1, "day");
        scoreDirector.beforeVariableChanged(event1, "timeSlot");
        scoreDirector.beforeVariableChanged(event1, "roomId");
        scoreDirector.beforeVariableChanged(event2, "day");
        scoreDirector.beforeVariableChanged(event2, "timeSlot");
        scoreDirector.beforeVariableChanged(event2, "roomId");
        scoreDirector.beforeVariableChanged(event3, "day");
        scoreDirector.beforeVariableChanged(event3, "timeSlot");
        scoreDirector.beforeVariableChanged(event3, "roomId");
        scoreDirector.beforeVariableChanged(event4, "day");
        scoreDirector.beforeVariableChanged(event4, "timeSlot");
        scoreDirector.beforeVariableChanged(event4, "roomId");
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

    /**
     * Creates a move that will reverse this move.
     * @param scoreDirector The current ScoreDirector.
     * @return The undo move.
     */
    @Override
    public DoubleSwapFullEventMove createUndoMove(ScoreDirector scoreDirector) {
        DoubleSwapFullEventMove doubleSwapFullEventMove = new DoubleSwapFullEventMove(
                event1, event2, event3, event4,
                event1.getDay(), event2.getDay(),
                event3.getDay(), event4.getDay(),
                event1.getTimeSlot(), event2.getTimeSlot(),
                event3.getTimeSlot(), event4.getTimeSlot(),
                event1.getRoomId(), event2.getRoomId(),
                event3.getRoomId(), event4.getRoomId());
        return doubleSwapFullEventMove;
    }

    /**
     * Rebases a move from an origin ScoreDirector to another destination
     * ScoreDirector which is usually on another Thread.
     * @param destinationScoreDirector The destination ScoreDirector.
     * @return A move that does the same thing as this one but on
     * another solution instance.
     */
    @Override
    public DoubleSwapFullEventMove rebase(ScoreDirector destinationScoreDirector) {
        return new DoubleSwapFullEventMove(
                (Event) destinationScoreDirector.lookUpWorkingObject(event1),
                (Event) destinationScoreDirector.lookUpWorkingObject(event2),
                (Event) destinationScoreDirector.lookUpWorkingObject(event3),
                (Event) destinationScoreDirector.lookUpWorkingObject(event4),
                (Integer) destinationScoreDirector.lookUpWorkingObject(toDay1),
                (Integer) destinationScoreDirector.lookUpWorkingObject(toDay2),
                (Integer) destinationScoreDirector.lookUpWorkingObject(toDay3),
                (Integer) destinationScoreDirector.lookUpWorkingObject(toDay4),
                (Integer) destinationScoreDirector.lookUpWorkingObject(toTimeSlot1),
                (Integer) destinationScoreDirector.lookUpWorkingObject(toTimeSlot2),
                (Integer) destinationScoreDirector.lookUpWorkingObject(toTimeSlot3),
                (Integer) destinationScoreDirector.lookUpWorkingObject(toTimeSlot4),
                (Integer) destinationScoreDirector.lookUpWorkingObject(toRoom1),
                (Integer) destinationScoreDirector.lookUpWorkingObject(toRoom2),
                (Integer) destinationScoreDirector.lookUpWorkingObject(toRoom3),
                (Integer) destinationScoreDirector.lookUpWorkingObject(toRoom4));
    }

    /**
     * Returns a list of all entities that are changed in this move.
     */
    @Override
    public Collection<?> getPlanningEntities() {
        return Arrays.asList(event1, event2, event3, event4);
    }

    /**
     * Returns a list of all values that are changed in this move.
     */
    @Override
    public Collection<?> getPlanningValues() {
        return Arrays.asList(toDay1, toTimeSlot1, toRoom1, toDay2, toTimeSlot2, toRoom2,
                toDay3, toTimeSlot3, toRoom3, toDay4, toTimeSlot4, toRoom4);
    }

    /**
     * Compares this move to another move.
     * @param obj The object, this move will be compared to.
     * @return Return true if its the same object or if the move
     * does exactly the same.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj instanceof DoubleSwapFullEventMove) {
            DoubleSwapFullEventMove other = (DoubleSwapFullEventMove) obj;
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

    /**
     * Generates a HashCode for this move.
     * @return A HashCode for this move.
     */
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
