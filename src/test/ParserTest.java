package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import org.junit.Before;
import org.junit.Test;
import driver.Parser;
import driver.Target;

public class ParserTest {

    private ArrayList<String> userArgs;

    private String[] getUserARgs() {
        return this.userArgs.toArray(new String[this.userArgs.size()]);
    }

    private Target getExpectedTarget(String IP, int portStart, int portEnd) {
        Target expectedTargetStruct = new Target();
        expectedTargetStruct.portRange[0] = portStart;
        expectedTargetStruct.portRange[1] = portEnd;
        expectedTargetStruct.targetIP = IP;
        return expectedTargetStruct;
    }

    @Before
    public void setup() {
        userArgs = new ArrayList<>();
    }

    @Test
    public void testValidCase() {
        Target exp = getExpectedTarget("123.123.123.12", 14, 23);
        Target act = null;
        userArgs.add("123.123.123.12");
        userArgs.add("14-23");

        try {
            act = Parser.run(getUserARgs());
            // assertEquals(exp.portRange, act.portRange);
            assertTrue(Arrays.equals(exp.portRange, act.portRange));
            assertEquals(exp.targetIP, act.targetIP);
        } catch (Exception e) {
            fail("Unexpected exception :(");
        }
    }

}