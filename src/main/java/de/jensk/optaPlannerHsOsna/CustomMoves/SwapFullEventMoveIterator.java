package de.jensk.optaPlannerHsOsna.CustomMoves;

import de.jensk.optaPlannerHsOsna.CourseSchedule;
import de.jensk.optaPlannerHsOsna.Event;
import org.optaplanner.core.impl.heuristic.move.Move;
import org.optaplanner.core.impl.score.director.ScoreDirector;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * This class iterates over possible SwapFullEventMoves in a RANDOM order.<br>
 * It should never be instantiated using the new-Keyword.<br>
 * Use the DoubleSwapFullEventMoveIteratorFactory to do so.
 */
public class SwapFullEventMoveIterator implements Iterator<Move<CourseSchedule>> {

    /**
     * A list of all existing events.
     */
    private final List<Event> events;

    /**
     * A generator for random numbers.
     */
    private final Random workingRandom;

    /**
     * Constructor for the SwapFullEventMoveIterator.
     * @param scoreDirector The current ScoreDirector.
     * @param workingRandom An instance of the Random class to generator random numbers.
     */
    SwapFullEventMoveIterator(
            ScoreDirector<CourseSchedule> scoreDirector, Random workingRandom) {
        this.events = scoreDirector.getWorkingSolution().getEventList();
        this.workingRandom = workingRandom;
    }

    /**
     * Return whether there is a next element.<br>
     * Always returns true, because a random element is picked everytime.
     */
    @Override
    public boolean hasNext() {
        return true;
    }

    /**
     * Creates a new SwapFullEventMove. <br>
     * The Move swaps two random events.
     * A swap means that the day and timeslot values are swapped.<br>
     * Additionally there is a 50% chance that the room is swapped too.
     * @return The created DoubleChangeFullEventMove
     */
    @Override
    public Move<CourseSchedule> next() {
        int eventNr1 = workingRandom.nextInt(events.size());
        int eventNr2 = workingRandom.nextInt(events.size());
        boolean keepRooms = workingRandom.nextBoolean();
        Event event1 = events.get(eventNr1);
        Event event2 = events.get(eventNr2);
        return new SwapFullEventMove(event1, event2,
                event2.getDay(), event1.getDay(),
                event2.getTimeSlot(), event1.getTimeSlot(),
                keepRooms ? event1.getRoomId() : event2.getRoomId(),
                keepRooms ? event2.getRoomId() : event1.getRoomId());
    }
}
