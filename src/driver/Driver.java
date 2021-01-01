package driver;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import scanner.PortScanner;
import scanner.PortScannerResult;

/**
 * USAGE: JavaPortScanner [target IP] [port range]
 * <ul>
 * <li><target IP>: IP address to scan</li>
 * <li><port range>: range of ports to scan</li>
 * <ul>
 * <li>e.g. 80-100 scans from 80 to 100</li>
 * <li>e.g. 8080 scans only port 8080</li>
 * </ul>
 */
public class Driver {

    private static final int SCAN_TIMEOUT = 200;

    public static void main(String[] args) {
        // get a Target info
        Target t = null;
        try {
            t = Parser.run(args);
        } catch (InvalidUserInputException e) {
            // exit with the message
            System.err.println(e.getMessage());
            System.exit(1);
        }

        // run a scan on the target and get a result
        // 16 threads
        ExecutorService es = Executors.newFixedThreadPool(16);
        List<Future<Boolean>> futures = new ArrayList<>();
        // result to populate
        PortScannerResult r = new PortScannerResult();

        // loop through provided port range
        for (int currPort = t.portRange[0]; currPort <= t.portRange[1]; currPort++) {
            futures.add(PortScanner.isPortOpen(r, es, t.targetIP, currPort, SCAN_TIMEOUT));
        }
        es.shutdown();

        // TODO: display the results

    }
}
