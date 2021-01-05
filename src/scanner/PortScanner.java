package scanner;

import java.net.InetSocketAddress;
import java.net.Socket;

public final class PortScanner {

    public static boolean isPortOpen(PortScannerResult r, String ip, int port, int timeout) {
        try (Socket sfd = new Socket()) {
            sfd.connect(new InetSocketAddress(ip, port), timeout);
            // connection ok
            // r.getOpenList().add(port);
            r.addPortStatus(port, true);
            return true;
        } catch (Exception e) {
            // connection failed
            // r.getCloseList().add(port);
            r.addPortStatus(port, false);
            return false;
        }
    }
}
