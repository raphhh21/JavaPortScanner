package scanner;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class PortScanner {

    public boolean scan(String ip, int port, int timeout) {
        try {
            Socket sfd = new Socket();
            sfd.connect(new InetSocketAddress(ip, port), timeout);
            sfd.close();
            // connection ok
            return true;
        } catch (IOException e) {
            // connection failed
            return false;
        }
    }
}
