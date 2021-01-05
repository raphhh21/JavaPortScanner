package driver;

import java.net.InetAddress;
import java.net.UnknownHostException;

public final class Parser {

    /**
     * Get target information from user inputs.
     *
     * @param args arguments from user
     */
    public static Target run(String[] args) throws InvalidUserInputException {
        Target ret = new Target();

        if (args.length != 2 && args.length != 1) {
            throw new InvalidUserInputException("default");
        }

        // set ip
        if (isValidIPv4Address(args[0])) {
            ret.targetIP = args[0];
        } else {
            // invalid ip
            throw new InvalidUserInputException("ip");
        }

        // set port range
        String targetPortStart;
        String targetPortEnd;

        if (args.length == 1) {
            // target all possible port if provided ip is valid
            targetPortStart = "1";
            targetPortEnd = "65535";
        } else if (args[1].contains("-")) {
            // is a range

            // first occurrence of dash
            int index = args[1].indexOf("-");
            targetPortStart = args[1].substring(0, index);
            targetPortEnd = args[1].substring(index + 1, args[1].length());
        } else {
            // a single port
            targetPortStart = args[1];
            targetPortEnd = args[1];
        }

        try {
            ret.portRange[0] = Integer.parseInt(targetPortStart);
            ret.portRange[1] = Integer.parseInt(targetPortEnd);

            // validate range
            if (ret.portRange[0] > ret.portRange[1]) {
                throw new InvalidUserInputException("port");
            }

        } catch (Exception e) {
            // could not convert port num to int
            throw new InvalidUserInputException("port");
        }

        // check if ports are in valid range
        if (!isValidPortNumber(ret.portRange[0]) || !isValidPortNumber(ret.portRange[1])) {
            throw new InvalidUserInputException("port");
        }

        return ret;
    }

    /**
     * Return true iff portNum is in valid range, 1 to 65535.
     *
     * @param portNum port number to check.
     * @return whether portNum is a valid port number.
     */
    private static boolean isValidPortNumber(int portNum) {
        return (1 <= portNum && portNum <= 65535);
    }

    /**
     * Validate provided IPv4 address.
     *
     * @param ip
     * @return whether ip is a valid IPv4 Address
     */
    private static boolean isValidIPv4Address(String ip) {
        try {
            return InetAddress.getByName(ip).getHostAddress().equals(ip);
        } catch (UnknownHostException e) {
            return false;
        }
    }
}
