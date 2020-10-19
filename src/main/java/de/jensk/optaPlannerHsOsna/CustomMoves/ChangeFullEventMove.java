package de.jensk.optaPlannerHsOsna.CustomMoves;

import de.jensk.optaPlannerHsOsna.CourseSchedule;
import de.jensk.optaPlannerHsOsna.Event;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.optaplanner.core.impl.heuristic.move.AbstractMove;
import org.optaplanner.core.impl.score.director.ScoreDirector;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

/**
 * This class represents a custom move which can change the day,
 * timeslot and room of an event at once.
 */
public class ChangeFullEventMove extends AbstractMove<CourseSchedule> {

    /**
     * The event that will be changed in this move.
     */
    private final Event event;

    /**
     * The day at which the event will be placed.
     */
    private final Integer toDay;

    /**
     * The timeslot at which the event will be placed.
     */
    private final Integer toTimeSlot;

    /**
     * The room at which the event will be placed.
     */
    private final Integer toRoom;

    /**
     * Constructor for the ChangeFullEventMove.
     * @param event The event that will be changed in this move.
     * @param toDay The day at which the event will be placed.
     * @param toTimeSlot The timeslot at which the event will be placed.
     * @param toRoom The room at which the event will be placed.
     */
    public ChangeFullEventMove(Event event, Integer toDay,
                               Integer toTimeSlot, Integer toRoom) {
        this.event = event;
        this.toDay = toDay;
        this.toTimeSlot = toTimeSlot;
        this.toRoom = toRoom;
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
     * Creates a move that will reverse this move.
     * @param scoreDirector The current ScoreDirector.
     * @return The undo move.
     */
    @Override
    public ChangeFullEventMove createUndoMove(ScoreDirector scoreDirector) {
        return new ChangeFullEventMove(event, event.getDay(),
                event.getTimeSlot(), event.getRoomId());
    }

    /**
     * Executes the current move and informs the ScoreDirector.
     * @param scoreDirector The current ScoreDirector.
     */
    @Override
    protected void doMoveOnGenuineVariables(ScoreDirector scoreDirector) {
        scoreDirector.beforeVariableChanged(event, "day");
        scoreDirector.beforeVariableChanged(event, "timeSlot");
        scoreDirector.beforeVariableChanged(event, "roomId");
        event.setDay(toDay);
        event.setTimeSlot(toTimeSlot);
        event.setRoomId(toRoom);
        scoreDirector.afterVariableChanged(event, "day");
        scoreDirector.afterVariableChanged(event, "timeSlot");
        scoreDirector.afterVariableChanged(event, "roomId");
    }

    /**
     * Rebases a move from an origin ScoreDirector to another destination
     * ScoreDirector which is usually on another Thread.
     * @param destinationScoreDirector The destination ScoreDirector.
     * @return A move that does the same thing as this one but on
     * another solution instance.
     */
    @Override
    public ChangeFullEventMove rebase(ScoreDirector destinationScoreDirector) {
        return new ChangeFullEventMove(
                (Event) destinationScoreDirector.lookUpWorkingObject(event),
                (Integer) destinationScoreDirector.lookUpWorkingObject(toDay),
                (Integer) destinationScoreDirector.lookUpWorkingObject(toTimeSlot),
                (Integer) destinationScoreDirector.lookUpWorkingObject(toRoom));
    }

    /**
     * Returns list of all entities that are changed in this move.
     */
    @Override
    public Collection<?> getPlanningEntities() {
        return Collections.singletonList(event);
    }

    /**
     * Returns a list of all values that are changed in this move.
     */
    @Override
    public Collection<?> getPlanningValues() {
        return Arrays.asList(toDay, toTimeSlot, toRoom);
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
        } else if (obj instanceof ChangeFullEventMove) {
            ChangeFullEventMove other = (ChangeFullEventMove) obj;
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

    /**
     * Generates a HashCode for this move.
     * @return A HashCode for this move.
     */
    public int hashCode() {
        return new HashCodeBuilder()
                .append(event)
                .append(toDay)
                .append(toTimeSlot)
                .append(toRoom)
                .toHashCode();
    }
}
