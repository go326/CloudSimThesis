package CloudSimThesis.host;


public class PowerModelHost {

    private Host host;
    private double startupPower;
    private double shutdownPower;
    private double maxPower;
    private double staticPower;
    private double total;

    PowerModelHost(
        final double startupPower,
        final double shutdownPower,
        final double maxPower,
        final double staticPower) {

        this.startupPower = startupPower;
        this.shutdownPower = shutdownPower;
        this.maxPower = maxPower;
        this.staticPower = staticPower;
    }
        
    
}

