package de.jensk.optaPlannerHsOsna.CustomMoves;

import de.jensk.optaPlannerHsOsna.CourseSchedule;
import org.optaplanner.core.impl.heuristic.move.Move;
import org.optaplanner.core.impl.heuristic.selector.move.factory.MoveIteratorFactory;
import org.optaplanner.core.impl.score.director.ScoreDirector;
import java.util.Iterator;
import java.util.Random;

/**
 * This factory creates the SwapFullEventMoveIterator.
 */
public class SwapFullEventMoveIteratorFactory
        implements MoveIteratorFactory<CourseSchedule> {

    /**
     * This method calculate an approximation of the amount of possible
     * moves of the type DoubleSwapFullEventMove. It doesnt have to be accurate.
     * @param scoreDirector The current ScoreDirector.
     * @return The size approximation.
     */
    @Override
    public long getSize(ScoreDirector<CourseSchedule> scoreDirector) {
        return scoreDirector.getWorkingSolution().getEventList().size()
                   * scoreDirector.getWorkingSolution().getEventList().size();
    }

    /**
     * This method is only overridden, because the interface demands it. <br>
     * Do not use this method.<br>
     * This method is not implemented because the original order is not useful. <br>
     * Use the random order only.
     * @param scoreDirector The current Score director.
     * @return Always null.
     */
    @Override
    public Iterator<? extends Move<CourseSchedule>> createOriginalMoveIterator(
            ScoreDirector<CourseSchedule> scoreDirector) {
        return null;
    }


    /**
     * This method instantiates an Iterator, that can iterate over
     * all possible SwapFullEvents in a random order.
     * @param scoreDirector The current ScoreDirector.
     * @param workingRandom A generator for Random numbers.
     * @return The instantiated Iterator.
     */
    @Override
    public Iterator<? extends Move<CourseSchedule>> createRandomMoveIterator(
            ScoreDirector<CourseSchedule> scoreDirector, Random workingRandom) {
        return new SwapFullEventMoveIterator(scoreDirector, workingRandom);
    }
}
