package test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.junit.Before;
import org.junit.Test;
import scanner.PortScanner;
import scanner.PortScannerResult;
import scanner.PortScannerResult.PortStatus;

public class PortScannerTest {

    private static final int TIMEOUT = 200;
    private static final int THREAD_COUNT = 1;

    ExecutorService es;
    List<Future<Boolean>> futures;

    private PortScannerResult getResult(String ip, int portStart, int portEnd) {
        PortScannerResult r = new PortScannerResult();

        for (int port = portStart; port <= portEnd; port++) {
            futures.add(PortScanner.isPortOpen(r, es, ip, port, TIMEOUT));
        }
        es.shutdown();
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

    @Before
    public void setup() {
        es = Executors.newFixedThreadPool(THREAD_COUNT);
        futures = new ArrayList<>();
    }

    @Test
    public void testScanGoogle() {
        PortScannerResult exp = new PortScannerResult();
        exp.addPortStatus(80, true);

        PortScannerResult act = getResult("64.233.185.139", 80, 80);

        // ! TBD - print exp and act
        System.out.println("===EXP===");
        System.out.println(exp);
        System.out.println("===ACT===");
        System.out.println(act);



        runCompare(exp, act);
    }
}
