package CloudSimThesis.host;

public record HostStateHistoryEntry(
    double time,
    double utilizationMips,	
    double allocatedMips,
    double requestedMips,
    boolean active) {

    //simulation時間
    public double time() {
        return time;
    }

    //hostの初期配置
    public double allocatedMips() {
        return allocatedMips;
    }

    public double requestedMips() {
        return requestedMips;
    }

    public double percentUsage() {
        return requestedMips > 0 ? allocatedMips / requestedMips : 0;
    }

    public boolean active() {
        return active;
    }

    public String toString() {
//        final var msg = "%6.1f s | %5.0f MIPS | %5.0f MIPS | %3.0f %% | %3.0f%% | %s";
        final var msg = "%6.1f s | %5.0f MIPS | %5.0f MIPS | %3.0f %% | %s";
        return msg.formatted(time, allocatedMips, requestedMips,
                             percentUsage() * 100 , //utilizationPercent * 100,
                             active);
    }
}
