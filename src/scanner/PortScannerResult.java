package scanner;

import java.util.ArrayList;
import java.util.List;

public class PortScannerResult {

    /**
     * List of open ports of the target.
     */
    private List<Integer> openList;

    /**
     * List of closed ports of the target.
     */
    private List<Integer> closeList;

    public PortScannerResult() {
        openList = new ArrayList<>();
        closeList = new ArrayList<>();
    }

    public List<Integer> getOpenList() {
        return this.openList;
    }

    public List<Integer> getCloseList() {
        return this.closeList;
    }

}
