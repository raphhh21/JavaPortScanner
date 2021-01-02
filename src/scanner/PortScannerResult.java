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
    }

    private List<PortStatus> resultList;

    public PortScannerResult() {
        resultList = new ArrayList<>();
    }

    public List<PortStatus> getResultList() {
        return this.resultList;
    }

    public List<PortStatus> addPortStatus(int portNum, boolean isOpen) {
        PortStatus newStatus = new PortStatus(portNum, isOpen);
        this.resultList.add(newStatus);
        return this.resultList;
    }

}
