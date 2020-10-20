package de.jensk.optaPlannerHsOsna.CustomMoves;

import de.jensk.optaPlannerHsOsna.CourseSchedule;
import de.jensk.optaPlannerHsOsna.Event;
import org.optaplanner.core.impl.heuristic.move.Move;
import org.optaplanner.core.impl.score.director.ScoreDirector;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.ArrayList;


/**
 * This class iterates over possible DoubleSwapFullEventMoves in a RANDOM order.<br>
 * It should never be instantiated using the new-Keyword.<br>
 * Use the DoubleSwapFullEventMoveIteratorFactory to do so.
 */
public class DoubleSwapFullEventMoveIterator implements Iterator<Move<CourseSchedule>> {

    /**
     * A list of all events which have a minimum of two hours per week.
     * This means every element in this list has at least one other event
     * in the list that has the same id.
     */
    private final List<Event> eventsWithMinTwoHpw;

    /**
     * This HashMap provides a list of all events which have a
     * certain id, providing this id as the key.
     */
    private final HashMap<Integer, List<Event>> allEventsById;

    /**
     * A generator for random numbers.
     */
    private final Random workingRandom;

    /**
     * Constructor for the DoubleSwapFullEventMoveIterator.
     * @param scoreDirector The current ScoreDirector.
     * @param workingRandom An instance of the Random class to generator random numbers.
     */
    public DoubleSwapFullEventMoveIterator(
            ScoreDirector<CourseSchedule> scoreDirector, Random workingRandom) {
        List<Event> events = scoreDirector.getWorkingSolution().getEventList();
        eventsWithMinTwoHpw = new ArrayList<>();
        allEventsById = new HashMap<>();
        events.forEach((event -> {
            if (event.getHpw() >= 2) {
                eventsWithMinTwoHpw.add(event);
                if (!allEventsById.containsKey(event.getId())) {
                    allEventsById.put(event.getId(), new ArrayList<Event>());
                }
                allEventsById.get(event.getId()).add(event);
            }
        }));
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
     * Creates a new DoubleSwapFullEventMove. <br>
     * There are two sets of events, each set containing exactly
     * two events which have the same id. The first event of the first
     * set is swapped with the first event of the second set.<br>
     * The same happens to the second events of each set.<br>
     * A swap means that the day and timeslot values are swapped.<br>
     * Additionally there is a 50% chance that the room is swapped too.
     * @return The created DoubleChangeFullEventMove
     */
    @Override
    public Move<CourseSchedule> next() {
        int eventNr1 = workingRandom.nextInt(eventsWithMinTwoHpw.size());
        Event event1 = eventsWithMinTwoHpw.get(eventNr1);
        Event event2 = null;
        List<Event> eventsWithSameId = allEventsById.get(event1.getId());
        while (event2 == null || event2.getUniqueId().equals(event1.getUniqueId())) {
            int eventNr2 = workingRandom.nextInt(eventsWithSameId.size());
            event2 = eventsWithSameId.get(eventNr2);
        }
        int eventNr3 = workingRandom.nextInt(eventsWithMinTwoHpw.size());
        Event event3 = eventsWithMinTwoHpw.get(eventNr3);
        Event event4 = null;
        eventsWithSameId = allEventsById.get(event3.getId());
        while (event4 == null || event4.getUniqueId().equals(event3.getUniqueId())) {
            int eventNr4 = workingRandom.nextInt(eventsWithSameId.size());
            event4 = eventsWithSameId.get(eventNr4);
        }
        boolean keepRooms = workingRandom.nextBoolean();
        return new DoubleSwapFullEventMove(event1, event2, event3, event4,
                event3.getDay(), event4.getDay(), event1.getDay(), event2.getDay(),
                event3.getTimeSlot(), event4.getTimeSlot(),
                event1.getTimeSlot(), event4.getTimeSlot(),
                keepRooms ? event1.getRoomId() : event3.getRoomId(),
                keepRooms ? event2.getRoomId() : event4.getRoomId(),
                keepRooms ? event3.getRoomId() : event1.getRoomId(),
                keepRooms ? event4.getRoomId() : event2.getRoomId()
        );
    }
}

