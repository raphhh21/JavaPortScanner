package driver;

import java.util.Comparator;
import scanner.PortScanner;
import scanner.PortScannerResult;
import scanner.PortScannerResult.PortStatus;

/**
 * USAGE: JavaPortScanner [target IP] [port range]
 * <ul>
 * <li>[target IP]: IP address to scan</li>
 * <li>[port range]: range of ports to scan</li>
 * <ul>
 * <li>e.g. 80-100 scans from 80 to 100</li>
 * <li>e.g. 8080 scans only port 8080</li>
 * </ul>
 * </ul>
 */
public class Driver {

    private static final int SCAN_TIMEOUT = 200;

    public static void main(String[] args) {
        // get a Target info from Parser
        Target t = null;
        try {
            t = Parser.run(args);
        } catch (InvalidUserInputException e) {
            // exit with the message
            System.err.println(e.getMessage());
            System.exit(1);
        }

        // run a scan on the target and get a result
        System.out.println("Scanning " + t.targetIP + ":" + t.portRange[0] + "-" + t.portRange[1]);
        // result to populate
        PortScannerResult r = new PortScannerResult();

        // loop through provided port range
        for (int currPort = t.portRange[0]; currPort <= t.portRange[1]; currPort++) {
            PortScanner.isPortOpen(r, t.targetIP, currPort, SCAN_TIMEOUT);
        }

        // sort result list by portNum
        r.getResultList().sort(Comparator.comparing(PortStatus::getPortNum));

        System.out.println(r);
    }
}
