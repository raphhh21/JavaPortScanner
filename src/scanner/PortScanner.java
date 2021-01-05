package scanner;

import java.net.InetSocketAddress;
import java.net.Socket;

public final class PortScanner {

    private PortScannerResult r;
    private String targetIP;
    private int targetPort;
    private int timeout;

    public PortScanner(PortScannerResult r, String targetIP, int targetPort, int timeout) {
        this.r = r;
        this.targetIP = targetIP;
        this.targetPort = targetPort;
        this.timeout = timeout;
    }

    public void runScan() {

        // ! TBD !!!!!!!!!!!!
        System.out.println("RUN: " + targetIP + ":" + targetPort);

        boolean stat;
        try (Socket sfd = new Socket()) {
            sfd.connect(new InetSocketAddress(targetIP, targetPort), timeout);
            stat = true;
        } catch (Exception e) {
            stat = false;
        }
        if (r != null) {
            r.addPortStatus(targetPort, stat);
        }
    }

    public static void isPortOpen(PortScannerResult r, String ip, int port, int timeout) {
        boolean stat;
        try (Socket sfd = new Socket()) {
            sfd.connect(new InetSocketAddress(ip, port), timeout);
            // connection ok
            // r.getOpenList().add(port);
            stat = true;
        } catch (Exception e) {
            // connection failed
            // r.getCloseList().add(port);
            stat= false;
        }
        if (r != null) {
            r.addPortStatus(port, stat);
        }
    }
}
