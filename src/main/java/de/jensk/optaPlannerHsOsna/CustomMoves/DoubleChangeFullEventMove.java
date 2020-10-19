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
 * This class represents a custom move that can change the day,
 * timeslot and room of two events which have the same id. <br>
 * They will be moved to the same room, the same day and
 * to adjacent timeslots. <br>
 * The chance that the same room is picked again is a bit more than
 * 50 % so that unnecessary room shifting is reduced.
 */
public class DoubleChangeFullEventMove extends AbstractMove<CourseSchedule> {

    /**
     * The first event that will be changed in this move.
     */
    private final Event event1;

    /**
     * The second event that will be changed in this move.
     */
    private final Event event2;

    /**
     * The day, the first event will be moved to.
     */
    private final Integer toDay1;

    /**
     * The day, the second event will be moved to.
     */
    private final Integer toDay2;

    /**
     * The timeslot, the first event will be moved to.
     */
    private final Integer toTimeSlot1;

    /**
     * The timeslot, the second event will be moved to.
     */
    private final Integer toTimeSlot2;

    /**
     * The room, the first event will be moved to.
     */
    private final Integer toRoom1;

    /**
     * The room, the second event will be moved to.
     */
    private final Integer toRoom2;

    /**
     * Constructor for the DoubleChangeFullEventMove.
     * @param event1 The first event that will be changed in this move.
     * @param event2 The second event that will be changed in this move.
     * @param toDay1 The day, the first event will be moved to.
     * @param toDay2 The day, the second event will be moved to.
     * @param toTimeSlot1 The timeslot, the first event will be moved to.
     * @param toTimeSlot2 The timeslot, the second event will be moved to.
     * @param toRoom1 The room, the first event will be moved to.
     * @param toRoom2 The room, the second event will be moved to.
     */
    public DoubleChangeFullEventMove(Event event1, Event event2,
                                     Integer toDay1, Integer toDay2,
                                     Integer toTimeSlot1, Integer toTimeSlot2,
                                     Integer toRoom1, Integer toRoom2) {
        this.event1 = event1;
        this.event2 = event2;
        this.toDay1 = toDay1;
        this.toDay2 = toDay2;
        this.toTimeSlot1 = toTimeSlot1;
        this.toTimeSlot2 = toTimeSlot2;
        this.toRoom1 = toRoom1;
        this.toRoom2 = toRoom2;
    }

    /**
     * Checks whether the move is doable. In this case it always s true
     * because the factory will not create illegal moves in the first place.
     * @param scoreDirector The current ScoreDirector.
     * @return Returns whether the move is doable.
     */
    @Override
    public boolean isMoveDoable(ScoreDirector scoreDirector) {
        return true;
    }

    /**
     * Creates a move that will reverse this move.
     * @param scoreDirector The current ScoreDirector.
     * @return The undo move.
     */
    @Override
    protected AbstractMove createUndoMove(ScoreDirector scoreDirector) {
        return new DoubleChangeFullEventMove(event1, event2,
                event1.getDay(), event2.getDay(), event1.getTimeSlot(),
                event2.getTimeSlot(), event1.getRoomId(), event2.getRoomId());
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

    /**
     * Rebases a move from an origin ScoreDirector to another destination
     * ScoreDirector which is usually on another Thread.
     * @param destinationScoreDirector The destination ScoreDirector.
     * @return A move that does the same thing as this one but on
     * another solution instance.
     */
    @Override
    public DoubleChangeFullEventMove rebase(ScoreDirector destinationScoreDirector) {
        return new DoubleChangeFullEventMove(
                (Event) destinationScoreDirector.lookUpWorkingObject(event1),
                (Event) destinationScoreDirector.lookUpWorkingObject(event2),
                (Integer) destinationScoreDirector.lookUpWorkingObject(toDay1),
                (Integer) destinationScoreDirector.lookUpWorkingObject(toDay2),
                (Integer) destinationScoreDirector.lookUpWorkingObject(toTimeSlot1),
                (Integer) destinationScoreDirector.lookUpWorkingObject(toTimeSlot2),
                (Integer) destinationScoreDirector.lookUpWorkingObject(toRoom1),
                (Integer) destinationScoreDirector.lookUpWorkingObject(toRoom2));
    }


    /**
     * Returns a list of all entities that are changed in this move.
     */
    @Override
    public Collection<?> getPlanningEntities() {
        return Arrays.asList(event1, event2);
    }

    /**
     * Returns a list of all values that are changed in this move.
     */
    @Override
    public Collection<?> getPlanningValues() {
        return Arrays.asList(toDay1, toTimeSlot1, toRoom1, toDay2, toTimeSlot2, toRoom2);
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
        } else if (obj instanceof DoubleChangeFullEventMove) {
            DoubleChangeFullEventMove other = (DoubleChangeFullEventMove) obj;
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


    /**
     * Generates a HashCode for this move.
     * @return A HashCode for this move.
     */
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
