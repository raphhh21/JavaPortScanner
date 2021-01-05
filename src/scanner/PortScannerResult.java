package scanner;

import java.util.ArrayList;
import java.util.List;

public final class PortScannerResult {

    private List<PortStatus> openList;
    private List<PortStatus> closedList;


    public PortScannerResult(List<PortStatus> resultList) {
        this.openList = new ArrayList<>();
        this.closedList = new ArrayList<>();

        for (PortStatus eachPortStatus : resultList) {
            if (eachPortStatus.getIsOpen()) {
                openList.add(eachPortStatus);
            } else {
                closedList.add(eachPortStatus);
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        int totalCount = openList.size() + closedList.size();
        sb.append(openList.size() + "/" + totalCount + " were open\n");

        for (PortStatus openPortStatus : openList) {
            sb.append(openPortStatus + "\n");
        }
        return sb.toString();
    }
}
