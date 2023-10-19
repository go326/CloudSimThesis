package CloudSimThesis.host;

public record HostStateHistoryEntry(
    double time,
    double allocatedMips,
    double requestedMips,
    boolean active) {
    public double time() {
        return time;
    }

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
        final var msg = "%6.1f, %10.0f, %10.0f, %3.0f %s%n";
        return msg.formatted(time, requestedMips, allocatedMips, percentUsage() * 100 , active);
    }
}
