package de.jensk.optaPlannerHsOsna.CustomMoves;

import de.jensk.optaPlannerHsOsna.CourseSchedule;
import de.jensk.optaPlannerHsOsna.Event;
import org.optaplanner.core.impl.heuristic.move.Move;
import org.optaplanner.core.impl.heuristic.selector.move.factory.MoveIteratorFactory;
import org.optaplanner.core.impl.score.director.ScoreDirector;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class SwapFullEventMoveIteratorFactory implements MoveIteratorFactory<Object> {

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

    @Override
    public Iterator<? extends Move<Object>> createRandomMoveIterator(ScoreDirector<Object> scoreDirector, Random workingRandom) {
        class SwapFullEventMoveIterator implements Iterator {
            List<Event> events = ((CourseSchedule)scoreDirector.getWorkingSolution()).getEventList();
            int eventSize;

            SwapFullEventMoveIterator(){
                this.events = events;
                eventSize = events.size();
            }

            @Override
            public boolean hasNext() {
                return true;
            }

            @Override
            public Object next() {
                int eventNr1 = workingRandom.nextInt(eventSize);
                int eventNr2 = workingRandom.nextInt(eventSize);
                boolean keepRooms = workingRandom.nextBoolean();
                Event event1 = events.get(eventNr1);
                Event event2 = events.get(eventNr2);
                SwapFullEventMove swapFullEventMove = new SwapFullEventMove(event1, event2,
                        event2.getDay(),event1.getDay(),event2.getTimeSlot(),event1.getTimeSlot(),
                        keepRooms ? event1.getRoomId() : event2.getRoomId(), keepRooms ? event2.getRoomId() : event1.getRoomId());
                return swapFullEventMove;
            }
        }

        SwapFullEventMoveIterator iterator = new SwapFullEventMoveIterator();
        return iterator;
    }
}
