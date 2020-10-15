package de.jensk.optaPlannerHsOsna.Customs;

import de.jensk.optaPlannerHsOsna.CourseSchedule;
import de.jensk.optaPlannerHsOsna.Event;
import org.optaplanner.core.impl.heuristic.move.Move;
import org.optaplanner.core.impl.heuristic.selector.move.factory.MoveIteratorFactory;
import org.optaplanner.core.impl.score.director.ScoreDirector;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class ChangeDayTimeRoomMoveIteratorFactory implements MoveIteratorFactory<Object> {



    @Override
    public long getSize(ScoreDirector<Object> scoreDirector) {
        long size = ((CourseSchedule)scoreDirector.getWorkingSolution()).getRoomList().size()
                * ((CourseSchedule)scoreDirector.getWorkingSolution()).getDayList().size()
                * ((CourseSchedule)scoreDirector.getWorkingSolution()).getTimeSlotList().size()
                * ((CourseSchedule)scoreDirector.getWorkingSolution()).getEventList().size();
        System.out.println("Size was " + size);
        return size;
    }

    @Override
    public Iterator<? extends Move<Object>> createOriginalMoveIterator(ScoreDirector<Object> scoreDirector) {
        return null;
    }

    @Override
    public Iterator<? extends Move<Object>> createRandomMoveIterator(ScoreDirector<Object> scoreDirector, Random workingRandom) {
        List<Event> events = ((CourseSchedule)scoreDirector.getWorkingSolution()).getEventList();
        List<Integer> days = ((CourseSchedule)scoreDirector.getWorkingSolution()).getDayList();
        List<Integer> timeSlots = ((CourseSchedule)scoreDirector.getWorkingSolution()).getTimeSlotList();
        List<Integer> rooms = ((CourseSchedule)scoreDirector.getWorkingSolution()).getRoomList();
        int eventSize = events.size();
        int daySize = days.size();
        int timeSlotSize = timeSlots.size();
        int roomSize = rooms.size();

        class CustomIterator implements Iterator {
            List<Event> events;
            List<Integer> days;
            List<Integer> timeSlots;
            List<Integer> rooms;

            public void setProblemFacts(List<Event> events, List<Integer> days,  List<Integer> timeSlots, List<Integer> rooms, Random workingRandom ){
                this.events = events;
                this.days = days;
                this.timeSlots = timeSlots;
                this.rooms = rooms;
            }

            @Override
            public boolean hasNext() {
                return true;
            }

            @Override
            public Object next() {
                int eventNr = workingRandom.nextInt(eventSize);
                int dayNr = workingRandom.nextInt(daySize);
                int timeSlotNr = workingRandom.nextInt(timeSlotSize);
                int roomNr = workingRandom.nextInt(roomSize);
                return new ChangeDayTimeRoomMove(events.get(eventNr),
                        days.get(dayNr),
                        timeSlots.get(timeSlotNr),
                        rooms.get(roomNr));
            }
        }
        CustomIterator iterator = new CustomIterator();
        iterator.setProblemFacts(events,days,timeSlots,rooms, workingRandom);
        return iterator;

    }
}
