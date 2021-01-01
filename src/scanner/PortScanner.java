package scanner;

import java.net.InetSocketAddress;
import java.net.Socket;

public class PortScanner {

    public boolean isPortOpen(String ip, int port, int timeout) {
        try (Socket sfd = new Socket()) {
            sfd.connect(new InetSocketAddress(ip, port), timeout);
            // connection ok
            return true;
        } catch (Exception e) {
            // connection failed
            return false;
        }
    }
}
