package scanner;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public final class PortScanner {

    public static Future<PortStatus> isPortOpen(ExecutorService es, String ip, int port,
            int timeout) {
        return es.submit(new Callable<PortStatus>() {

            @Override
            public PortStatus call() {

                boolean stat;
                try (Socket sfd = new Socket()) {
                    sfd.connect(new InetSocketAddress(ip, port), timeout);
                    // connection ok
                    // r.getOpenList().add(port);
                    stat = true;
                } catch (Exception e) {
                    // connection failed
                    // r.getCloseList().add(port);
                    stat = false;
                }
                return new PortStatus(port, stat);
            }
        });
    }
}
