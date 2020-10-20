package de.jensk.optaPlannerHsOsna.CustomMoves;

import de.jensk.optaPlannerHsOsna.CourseSchedule;
import de.jensk.optaPlannerHsOsna.Event;
import org.optaplanner.core.impl.heuristic.move.Move;
import org.optaplanner.core.impl.score.director.ScoreDirector;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * This class iterates over possible ChangeFullEventMoves in a RANDOM order.<br>
 * It should never be instantiated using the new-Keyword.<br>
 * Use the ChangeFullEventMoveIteratorFactory to do so.
 */
public class ChangeFullEventMoveIterator implements Iterator<Move<CourseSchedule>> {

    /**
     * A list of all existing events.
     */
    private final List<Event> events;

    /**
     * A list of all existing days.
     */
    private final List<Integer> days;

    /**
     * A list of all existing timeslots.
     */
    private final List<Integer> timeSlots;

    /**
     * A list of all existing room.
     */
    private final List<Integer> rooms;

    /**
     * A generator for random numbers.
     */
    private final Random workingRandom;


    /**
     * Constructor for the ChangeFullEventMoveIterator.
     * @param scoreDirector The current ScoreDirector.
     * @param workingRandom An instance of the Random class to generator random numbers.
     */
    ChangeFullEventMoveIterator(
            ScoreDirector<CourseSchedule> scoreDirector, Random workingRandom) {
        events = scoreDirector.getWorkingSolution().getEventList();
        days = scoreDirector.getWorkingSolution().getDayList();
        timeSlots = scoreDirector.getWorkingSolution().getTimeSlotList();
        rooms = scoreDirector.getWorkingSolution().getRoomList();
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
     * Creates a new ChangeFullEventMove. <br>
     * The event and the values are picked randomly out of all existing events and values.
     * @return The created ChangeFullEventMove
     */
    @Override
    public Move<CourseSchedule> next() {
        int eventNr = workingRandom.nextInt(events.size());
        int dayNr = workingRandom.nextInt(days.size());
        int timeSlotNr = workingRandom.nextInt(timeSlots.size());
        int roomNr = workingRandom.nextInt(rooms.size());
        return new ChangeFullEventMove(events.get(eventNr),
                days.get(dayNr),
                timeSlots.get(timeSlotNr),
                rooms.get(roomNr));
    }
}
