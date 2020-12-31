package driver;

public class Parser {

    /**
     * Get target information from user inputs.
     *
     * @param args arguments from user
     */
    public static Target run(String[] args) throws InvalidUserInputException {
        Target ret = new Target();

        if (args.length != 2) {
            throw new InvalidUserInputException("default");
        }

        // set ip
        ret.targetIP = args[0];
        // set port range
        String targetPortStart;
        String targetPortEnd;
        if (args[1].contains("-")) {
            // is a range
            String[] tmp = args[1].split("-");
            targetPortStart = tmp[0];
            targetPortEnd = tmp[1];
        } else {
            // a single port
            targetPortStart = args[1];
            targetPortEnd = args[1];
        }
        try {
            ret.portRange[0] = Integer.parseInt(targetPortStart);
            ret.portRange[1] = Integer.parseInt(targetPortEnd);
        } catch (Exception e) {
            throw new InvalidUserInputException("port");
        }

        return ret;
    }
}
