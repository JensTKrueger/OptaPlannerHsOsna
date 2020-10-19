package de.jensk.optaPlannerHsOsna.CustomMoves;

import de.jensk.optaPlannerHsOsna.CourseSchedule;
import de.jensk.optaPlannerHsOsna.Event;
import org.optaplanner.core.impl.heuristic.move.Move;
import org.optaplanner.core.impl.heuristic.selector.move.factory.MoveIteratorFactory;
import org.optaplanner.core.impl.score.director.ScoreDirector;

import java.util.*;

public class DoubleChangeFullEventMoveIteratorFactory implements MoveIteratorFactory<Object>{

    @Override
    public long getSize(ScoreDirector<Object> scoreDirector) {
        long size = ((CourseSchedule)scoreDirector.getWorkingSolution()).getRoomList().size()
                * ((CourseSchedule)scoreDirector.getWorkingSolution()).getRoomList().size()
                * ((CourseSchedule)scoreDirector.getWorkingSolution()).getDayList().size()
                * ((CourseSchedule)scoreDirector.getWorkingSolution()).getTimeSlotList().size()
                * ((CourseSchedule)scoreDirector.getWorkingSolution()).getEventList().size()
                * ((CourseSchedule)scoreDirector.getWorkingSolution()).getEventList().size();
        return size;
    }

    @Override
    public Iterator<? extends Move<Object>> createOriginalMoveIterator(ScoreDirector<Object> scoreDirector) {
        return null;
    }

    @Override
    public Iterator<? extends Move<Object>> createRandomMoveIterator(ScoreDirector<Object> scoreDirector, Random workingRandom) {
        class DoubleChangeFullEventMoveInterator implements Iterator {
            List<Event> eventsWithMinTwoHpw;
            List<Event> events;
            List<Integer> days;
            List<Integer> timeSlots;
            List<Integer> rooms;
            HashMap<Integer, List<Event>> allEventsById;
            int daySize;
            int timeSlotSize;
            int roomSize;
            int eventsWithMinTwoHpwSize;


            DoubleChangeFullEventMoveInterator() {
                events = ((CourseSchedule)scoreDirector.getWorkingSolution()).getEventList();
                days = ((CourseSchedule)scoreDirector.getWorkingSolution()).getDayList();
                timeSlots = ((CourseSchedule)scoreDirector.getWorkingSolution()).getTimeSlotList();
                rooms = ((CourseSchedule)scoreDirector.getWorkingSolution()).getRoomList();

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
                daySize = days.size();
                timeSlotSize = timeSlots.size();
                roomSize = rooms.size();
                eventsWithMinTwoHpwSize = eventsWithMinTwoHpw.size();

            }

            @Override
            public boolean hasNext() {
                return true;
            }

            @Override
            public Object next() {
                int dayNr = workingRandom.nextInt(daySize);
                int timeSlotNr = workingRandom.nextInt(timeSlotSize);
                int roomNr1 = workingRandom.nextInt(roomSize);
                int roomNr2 = workingRandom.nextInt(roomSize);

                int eventNr1 = workingRandom.nextInt(eventsWithMinTwoHpwSize);
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
        DoubleChangeFullEventMoveInterator iterator = new DoubleChangeFullEventMoveInterator();
        return iterator;
    }



}
