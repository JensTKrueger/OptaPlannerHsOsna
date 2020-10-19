package de.jensk.optaPlannerHsOsna.CustomMoves;

import de.jensk.optaPlannerHsOsna.CourseSchedule;
import de.jensk.optaPlannerHsOsna.Event;
import org.optaplanner.core.impl.heuristic.move.Move;
import org.optaplanner.core.impl.heuristic.selector.move.factory.MoveIteratorFactory;
import org.optaplanner.core.impl.score.director.ScoreDirector;

import java.util.*;

public class DoubleSwapFullEventMoveIteratorFactory implements MoveIteratorFactory<Object> {

    @Override
    public long getSize(ScoreDirector<Object> scoreDirector) {
        long size = ((CourseSchedule)scoreDirector.getWorkingSolution()).getEventList().size() *
                ((CourseSchedule)scoreDirector.getWorkingSolution()).getEventList().size();
        return size;
    }

    @Override
    public Iterator<? extends Move<Object>> createOriginalMoveIterator(ScoreDirector<Object> scoreDirector) {
        return null;
    }

    public Iterator<? extends Move<Object>> createRandomMoveIterator(ScoreDirector<Object> scoreDirector, Random workingRandom) {
        class DoubleSwapFullEventMoveIterator implements Iterator {
            List<Event> eventsWithMinTwoHpw;
            HashMap<Integer, List<Event>> allEventsById;
            int eventsWithMinTwoHpwSize;

            DoubleSwapFullEventMoveIterator() {
                List<Event> events = ((CourseSchedule)scoreDirector.getWorkingSolution()).getEventList();

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
                eventsWithMinTwoHpwSize = eventsWithMinTwoHpw.size();
            }

            @Override
            public boolean hasNext() {
                return true;
            }

            @Override
            public Object next() {
                int eventNr1 = workingRandom.nextInt(eventsWithMinTwoHpwSize);
                Event event1 = eventsWithMinTwoHpw.get(eventNr1);
                Event event2 = null;
                List<Event> eventsWithSameId = allEventsById.get(event1.getId());
                while(event2 == null || event2.getUniqueId().equals(event1.getUniqueId())){
                    int eventNr2 = workingRandom.nextInt(eventsWithSameId.size());
                    event2 = eventsWithSameId.get(eventNr2);
                }
                int eventNr3 = workingRandom.nextInt(eventsWithMinTwoHpwSize);
                Event event3 = eventsWithMinTwoHpw.get(eventNr3);
                Event event4 = null;
                eventsWithSameId = allEventsById.get(event3.getId());
                while(event4 == null || event4.getUniqueId().equals(event3.getUniqueId())){
                    int eventNr4 = workingRandom.nextInt(eventsWithSameId.size());
                    event4 = eventsWithSameId.get(eventNr4);
                }
                boolean keepRooms = workingRandom.nextBoolean();
                DoubleSwapFullEventMove doubleSwapFullEventMove = new DoubleSwapFullEventMove(event1, event2, event3, event4,
                        event3.getDay(), event4.getDay(), event1.getDay(), event2.getDay(),
                        event3.getTimeSlot(), event4.getTimeSlot(), event1.getTimeSlot(), event4.getTimeSlot(),
                        keepRooms ? event1.getRoomId() : event3.getRoomId(), keepRooms ? event2.getRoomId() : event4.getRoomId(),
                        keepRooms ? event3.getRoomId() : event1.getRoomId(), keepRooms ? event4.getRoomId() : event2.getRoomId()
                        );
                return doubleSwapFullEventMove;
            }
        }
        DoubleSwapFullEventMoveIterator iterator = new DoubleSwapFullEventMoveIterator();
        return iterator;
    }

}
