import de.jensk.optaPlannerHsOsna.MinMaxAccumulator;
import org.junit.Test;
import static org.junit.Assert.assertEquals;


public class SpecialTests {

    @Test
    public void testHoursAccumulationMapMax(){
        MinMaxAccumulator map = new MinMaxAccumulator(1);
        map.addHour(1,1);
        map.addHour(2,2);
        map.addHour(3,3);
        assertEquals(3, map.getMaxHours());
        map.addHour(3,3);
        assertEquals(4, map.getMaxHours());
        map.addHour(3,4);
        assertEquals(4, map.getMaxHours());
        map.addHour(3,4);
        assertEquals(4, map.getMaxHours());
        map.addHour(1,6);
        assertEquals(4, map.getMaxHours());
        map.addHour(1,6);
        assertEquals(5, map.getMaxHours());
    }

    @Test
    public void testHoursAccumulationMapMin(){
        MinMaxAccumulator map = new MinMaxAccumulator(1);
        map.addHour(1,1);
        map.addHour(2,2);
        map.addHour(3,3);
        assertEquals(3, map.getMinHours());
        map.addHour(1,1);
        assertEquals(4, map.getMinHours());
        map.addHour(1,4);
        assertEquals(3, map.getMinHours());
        map.addHour(1,4);
        assertEquals(4, map.getMinHours());
        map.addHour(3,3);
        map.addHour(3,3);
        map.addHour(3,3);
        assertEquals(7, map.getMinHours());
        map.addHour(3,5);
        assertEquals(4, map.getMinHours());
        map.addHour(3,5);
        assertEquals(5, map.getMinHours());
    }

}
