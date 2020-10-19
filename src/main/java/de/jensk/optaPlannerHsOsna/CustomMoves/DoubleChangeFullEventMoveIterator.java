package de.jensk.optaPlannerHsOsna.CustomMoves;

import de.jensk.optaPlannerHsOsna.CourseSchedule;
import de.jensk.optaPlannerHsOsna.Event;
import org.optaplanner.core.impl.heuristic.move.Move;
import org.optaplanner.core.impl.score.director.ScoreDirector;

import java.util.*;

/**
 * This class iterates over possible DoubleChangeFullEventMoves in a RANDOM order.<br>
 * It should never be instantiated using the new-Keyword.<br>
 * Use the DoubleChangeFullEventMoveIteratorFactory to do so.
 */
public class DoubleChangeFullEventMoveIterator implements Iterator<Move<CourseSchedule>> {
    /**
     * A list of all existing events.
     */
    private List<Event> events;

    /**
     * A list of all existing days.
     */
    private List<Integer> days;

    /**
     * A list of all existing timeslots.
     */
    private List<Integer> timeSlots;

    /**
     * A list of all existing room.
     */
    private List<Integer> rooms;

    /**
     * A generator for random numbers.
     */
    private Random workingRandom;

    /**
     * A list of all events which have a minimum of two hours per week.
     * This means every element in this list has at least one other event
     * in the list that has the same id.
     */
    List<Event> eventsWithMinTwoHpw;

    /**
     * This HashMap provides a list of all events which have a
     * certain id, providing this id as the key.
     */
    HashMap<Integer, List<Event>> allEventsById;


    /**
     * Constructor for the DoubleChangeFullEventMoveIterator.
     * @param scoreDirector The current ScoreDirector.
     * @param workingRandom An instance of the Random class to generator random numbers.
     */
    DoubleChangeFullEventMoveIterator(ScoreDirector<CourseSchedule> scoreDirector, Random workingRandom) {
        events = scoreDirector.getWorkingSolution().getEventList();
        days = scoreDirector.getWorkingSolution().getDayList();
        timeSlots = scoreDirector.getWorkingSolution().getTimeSlotList();
        rooms = scoreDirector.getWorkingSolution().getRoomList();

        eventsWithMinTwoHpw = new ArrayList<>();
        allEventsById = new HashMap<>();
        events.forEach((event -> {
            if(event.getHpw() >= 2) {
                eventsWithMinTwoHpw.add(event);
                if(!allEventsById.containsKey(event.getId())){
                    allEventsById.put(event.getId(), new ArrayList<Event>());
                }
                allEventsById.get(event.getId()).add(event);
            }
        }));
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
     * Creates a new DoubleChangeFullEventMove. <br>
     * The events and the values are picked randomly out of all existing events and values.<br>
     * Both events will have the same id and the move will place them in the same
     * day and room and in adjacent timeslots.
     * @return The created DoubleChangeFullEventMove
     */
    @Override
    public Move<CourseSchedule> next() {
        int dayNr = workingRandom.nextInt(days.size());
        int timeSlotNr = workingRandom.nextInt(days.size());
        int roomNr1 = workingRandom.nextInt(rooms.size());
        int roomNr2 = workingRandom.nextInt(rooms.size());

        int eventNr1 = workingRandom.nextInt(eventsWithMinTwoHpw.size());
        Event event1 = eventsWithMinTwoHpw.get(eventNr1);
        Event event2 = null;
        List<Event> eventsWithSameId = allEventsById.get(event1.getId());
        while(event2 == null || event2.getUniqueId().equals(event1.getUniqueId())){
            int eventNr2 = workingRandom.nextInt(eventsWithSameId.size());
            event2 = eventsWithSameId.get(eventNr2);
        }

        int roomId1 = rooms.get(roomNr1);
        int roomId2 = rooms.get(roomNr2);
        int day = days.get(dayNr);
        int timeSlot1 = timeSlots.get(timeSlotNr);
        int timeSlot2 = timeSlot1 == timeSlots.size() - 1 ? timeSlot1 - 1 : timeSlot1 + 1;
        return new DoubleChangeFullEventMove(event1, event2, day, day, timeSlot1, timeSlot2, roomId1, roomId2);
    }
}