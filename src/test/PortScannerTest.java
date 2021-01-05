package test;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.junit.Before;
import org.junit.Test;
import scanner.PortScanner;
import scanner.PortScannerResult;
import scanner.PortScannerResult.PortStatus;

public class PortScannerTest {

    private static final int TIMEOUT = 200;
    private static final int THREAD_COUNT = 16;
    private static final String GOOGLE_IP = "64.233.185.139";

    private ExecutorService es;

    @Before
    public void setup() {
        es = Executors.newFixedThreadPool(THREAD_COUNT);
    }

    /**
     * This simulates how driver will scan but this will return PortScannerResult obj for testing.
     *
     * @param ip        target IP address
     * @param portStart start of port range
     * @param portEnd   end of port range
     * @return populated PortScannerResult
     */
    private PortScannerResult getResult(String ip, int portStart, int portEnd) {
        PortScannerResult r = new PortScannerResult();

        for (int port = portStart; port <= portEnd; port++) {
            PortScanner sc = new PortScanner(r, ip, port, TIMEOUT);

            Runnable scanRunnable = () -> {
                // ! TBD
                System.out.println("RUN:\t" + sc.getThreadName());

                sc.runScan();
            };

            es.execute(scanRunnable);
        }

        es.shutdown();
        try {
            if (!es.awaitTermination(1, TimeUnit.MINUTES)) {
                // timeout elapsed before termination
                es.shutdownNow();
            }
        } catch (InterruptedException e) {
            es.shutdownNow();
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
    public void testScanGoogleSinglePort() {
        PortScannerResult exp = new PortScannerResult();
        exp.addPortStatus(80, true);

        PortScannerResult act = getResult(GOOGLE_IP, 80, 80);

        // ! TBD
        System.out.println("===EXP===");
        System.out.println(exp);
        System.out.println("===ACT===");
        System.out.println(act);

        runCompare(exp, act);
    }

    @Test
    public void testScanGoogleMultiplePorts() {
        PortScannerResult exp = new PortScannerResult();
        for (int i = 80; i <= 443; i++) {
            exp.addPortStatus(i, i == 80 || i == 443);
        }

        PortScannerResult act = getResult(GOOGLE_IP, 80, 443);

        // ! TBD
        System.out.println("===EXP===");
        System.out.println(exp);
        System.out.println("===ACT===");
        System.out.println(act);

        runCompare(exp, act);
    }
}
