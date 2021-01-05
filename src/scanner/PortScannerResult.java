package scanner;

import java.util.ArrayList;
import java.util.List;

public final class PortScannerResult {

    public final class PortStatus {
        private int portNum;
        private boolean isOpen;

        public PortStatus(int portNum, boolean isOpen) {
            this.portNum = portNum;
            this.isOpen = isOpen;
        }

        public int getPortNum() {
            return this.portNum;
        }

        public boolean getIsOpen() {
            return this.isOpen;
        }

        @Override
        public String toString() {
            String status;
            if (this.isOpen) {
                status = "open";
            } else {
                status = "closed";
            }
            return this.portNum + ":\t" + status;
        }
    }

    private List<PortStatus> resultList;
    private int openCount;
    private int closeCount;

    public PortScannerResult() {
        resultList = new ArrayList<>();
    }

    /**
     * Add a status of a port whether it is open or closed.
     *
     * @param portNum valid port number to add
     * @param isOpen  boolean value to indicate if the port is open
     */
    public void addPortStatus(int portNum, boolean isOpen) {
        PortStatus newStatus = new PortStatus(portNum, isOpen);
        this.resultList.add(newStatus);

        // count
        if (isOpen) {
            this.openCount++;
        } else {
            this.closeCount++;
        }
    }

    public List<PortStatus> getResultList() {
        return this.resultList;
    }

    public int getOpenCount() {
        return this.openCount;
    }

    public int getCloseCount() {
        return this.closeCount;
    }

    @Override
    public String toString() {
        int totalCount = closeCount + openCount;
        StringBuilder sb = new StringBuilder();
        sb.append(openCount + "/" + totalCount + " are open\n");
        for (PortStatus ps : resultList) {
            if (ps.isOpen) {
                sb.append(ps.toString() + "\n");
            }
        }
        return sb.toString();
    }

}
