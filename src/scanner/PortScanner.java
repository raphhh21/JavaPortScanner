package scanner;

import java.net.InetSocketAddress;
import java.net.Socket;

public final class PortScanner {

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
