package CloudSimThesis.host;

public record HostStateHistoryEntry(
    double time,
    double capacityMips,    //ホストが持っているMIPS容量
    double requestedMips,   //全てのホストが占有しているMIPS
    double utilizationMips,	//今使用しているMIPS
    boolean active) {

    //simulation時間
    public double time() {
        return time;
    }

    public double capacityMips() {
        return capacityMips;
    }

    public double requestedMips() {
        return requestedMips;
    }

    public double utilizationMips() {
        return utilizationMips;
    }
    
    public double percentUsage() {
        return utilizationMips / capacityMips;
    }

    public boolean active() {
        return active;
    }

    public String toString() {
//        final var msg = "%6.1f s | %5.0f MIPS | %5.0f MIPS | %3.0f %% | %3.0f%% | %s";
        final var msg = "%6.1f s | %5.0f MIPS | %5.0f MIPS | %5.0f MIPS | %3.0f %% | %s";
        return msg.formatted(time, capacityMips, requestedMips, utilizationMips,
                             percentUsage() * 100 , //utilizationPercent * 100,
                             active);
    }
}
