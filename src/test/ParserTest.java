package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import driver.InvalidUserInputException;
import driver.Parser;
import driver.Target;

public class ParserTest {

    private ArrayList<String> userArgs;

    private static final String SAMPLE_IP_ADDR = "123.123.123.123";

    private String[] getUserArgs() {
        return this.userArgs.toArray(new String[this.userArgs.size()]);
    }

    private Target getExpectedTarget(String IP, int portStart, int portEnd) {
        Target expectedTargetStruct = new Target();
        expectedTargetStruct.portRange[0] = portStart;
        expectedTargetStruct.portRange[1] = portEnd;
        expectedTargetStruct.targetIP = IP;
        return expectedTargetStruct;
    }

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Before
    public void setup() {
        userArgs = new ArrayList<>();
    }

    @Test
    public void testValidCaseWithPortRange() {
        Target exp = getExpectedTarget(SAMPLE_IP_ADDR, 14, 23);
        Target act = null;
        userArgs.add(SAMPLE_IP_ADDR);
        userArgs.add("14-23");

        try {
            act = Parser.run(getUserArgs());
            // assertEquals(exp.portRange, act.portRange);
            assertTrue(Arrays.equals(exp.portRange, act.portRange));
            assertEquals(exp.targetIP, act.targetIP);
        } catch (Exception e) {
            fail("Unexpected exception :(");
        }
    }

    @Test
    public void testValidCaseWithSinglePort() {
        Target exp = getExpectedTarget(SAMPLE_IP_ADDR, 22, 22);
        Target act = null;
        userArgs.add(SAMPLE_IP_ADDR);
        userArgs.add("22");

        try {
            act = Parser.run(getUserArgs());
            assertTrue(Arrays.equals(exp.portRange, act.portRange));
            assertEquals(exp.targetIP, act.targetIP);
        } catch (Exception e) {
            fail("Unexpected exception :(");
        }
    }

    @Test
    public void testMissingIPInput() throws InvalidUserInputException {
        // expects exception
        exceptionRule.expect(InvalidUserInputException.class);
        exceptionRule.expectMessage("Invalid user input");

        // user only input port range
        userArgs.add("14-23");
        Parser.run(getUserArgs());
    }

    @Test
    public void testMissingPortInput() throws InvalidUserInputException {
        // expects exception
        exceptionRule.expect(InvalidUserInputException.class);
        exceptionRule.expectMessage("Invalid user input");

        // user only input IP address
        userArgs.add(SAMPLE_IP_ADDR);
        Parser.run(getUserArgs());
    }

    @Test
    public void testInvalidPortRange() throws InvalidUserInputException {
        // expects exception
        exceptionRule.expect(InvalidUserInputException.class);
        exceptionRule.expectMessage("Invalid user input: port");

        // user input alphanumeric port range
        userArgs.add(SAMPLE_IP_ADDR);
        userArgs.add("24a-df");
        Parser.run(getUserArgs());
    }

}