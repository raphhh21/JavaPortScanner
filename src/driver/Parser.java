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
            throw new InvalidUserInputException("port");
        }

        return ret;
    }
}
