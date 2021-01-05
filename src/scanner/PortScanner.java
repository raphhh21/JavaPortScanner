package scanner;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public final class PortScanner {

    public static Future<Boolean> isPortOpen(PortScannerResult r, ExecutorService es, String ip,
            int port, int timeout) {
        return es.submit(() -> {
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
        });
    }
}
