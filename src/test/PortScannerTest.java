package test;

import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import org.junit.Before;
import org.junit.Test;
import scanner.PortScanner;
import scanner.PortStatus;

public class PortScannerTest {

    private static final int TIMEOUT = 200;
    private static final int THREAD_COUNT = 16;
    private static final String GOOGLE_IP = "64.233.185.139";

    private ExecutorService es;
    private List<PortStatus> expected;
    private List<PortStatus> actual;

    @Before
    public void setup() {
        es = Executors.newFixedThreadPool(THREAD_COUNT);
        expected = new ArrayList<>();
        actual = new ArrayList<>();
    }

    /**
     * This simulates how driver will scan but this will return PortScannerResult obj for testing.
     *
     * @param ip        target IP address
     * @param portStart start of port range
     * @param portEnd   end of port range
     */
    private void getResults(String ip, int portStart, int portEnd) {
        List<Future<PortStatus>> futures = new ArrayList<>();
        for (int port = portStart; port <= portEnd; port++) {
            futures.add(PortScanner.isPortOpen(es, ip, port, TIMEOUT));
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

        List<PortStatus> resultList = new ArrayList<>();

        for (Future<PortStatus> eachFuture : futures) {
            try {
                resultList.add(eachFuture.get(TIMEOUT, TimeUnit.MILLISECONDS));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        actual = resultList;
    }

    private void runCompare() {
        assertEquals(expected.size(), actual.size());
        for (int i = 0; i < expected.size(); i++) {
            PortStatus expStatus = expected.get(i);
            PortStatus actStatus = actual.get(i);
            assertEquals(expStatus.getPortNum(), actStatus.getPortNum());
            assertEquals(expStatus.getIsOpen(), actStatus.getIsOpen());
        }
    }

    @Test
    public void testScanGoogleSinglePort() {
        expected.add(new PortStatus(80, true));
        getResults(GOOGLE_IP, 80, 80);
        runCompare();
    }

    @Test
    public void testScanGoogleMultiplePorts() {
        for (int i = 80; i <= 443; i++) {
            expected.add(new PortStatus(i, (i == 80 || i == 443)));
        }
        getResults(GOOGLE_IP, 80, 443);
        runCompare();
    }
}
