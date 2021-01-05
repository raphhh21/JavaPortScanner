package test;

import static org.junit.Assert.assertEquals;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.junit.Before;
import org.junit.Test;
import scanner.PortScanner;
import scanner.PortScannerResult;
import scanner.PortScannerResult.PortStatus;

public class PortScannerTest {

    private static final int TIMEOUT = 200;
    private static final int THREAD_COUNT = 32;

    private ExecutorService es;

    @Before
    public void setup() {
        es = Executors.newFixedThreadPool(THREAD_COUNT);
    }

    /**
     * This simulates how driver will scan but this will return PortScannerResult obj for testing.
     *
     * @param ip target IP address
     * @param portStart start of port range
     * @param portEnd end of port range
     * @return populated PortScannerResult
     */
    private PortScannerResult getResult(String ip, int portStart, int portEnd) {
        PortScannerResult r = new PortScannerResult();

        for (int port = portStart; port <= portEnd; port++) {
            PortScanner.isPortOpen(r, ip, port, TIMEOUT);
        }
        return r;
    }

    private void runCompare(PortScannerResult exp, PortScannerResult act) {
        assertEquals(exp.getCloseCount(), act.getCloseCount());
        assertEquals(exp.getOpenCount(), act.getOpenCount());

        // list of expected and actual have same size here
        for (int i = 0; i < exp.getResultList().size(); i++) {
            PortStatus expStatus = exp.getResultList().get(i);
            PortStatus actStatus = act.getResultList().get(i);
            assertEquals(expStatus.getPortNum(), actStatus.getPortNum());
            assertEquals(expStatus.getIsOpen(), actStatus.getIsOpen());
        }

    }

    @Test
    public void testScanGoogle() {
        PortScannerResult exp = new PortScannerResult();
        exp.addPortStatus(80, true);

        PortScannerResult act = getResult("64.233.185.139", 80, 80);

        runCompare(exp, act);
    }
}
