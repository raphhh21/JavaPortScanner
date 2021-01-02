package scanner;

import java.util.ArrayList;
import java.util.List;

public final class PortScannerResult {

    private final class PortStatus {
        @SuppressWarnings("unused")
        public int portNum;
        @SuppressWarnings("unused")
        public boolean isOpen;

        public PortStatus(int portNum, boolean isOpen) {
            this.portNum = portNum;
            this.isOpen = isOpen;
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
