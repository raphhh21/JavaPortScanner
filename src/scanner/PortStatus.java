package scanner;

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
        String status = "closed";
        if (isOpen) {
            status = "open";
        }
        return portNum + ":\t" + status;
    }
}
