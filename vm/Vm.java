package CloudSimThesis.vm;

import CloudSimThesis.host.*;
import CloudSimThesis.cloudlet.*;

public class Vm {

    private int mipsCapacity;
    private int pes;
    private Cloudlet cloudlet;
    private Host allocatedHost;

    public Vm(final int mipsCapacity, final int pes) {
        this.mipsCapacity = mipsCapacity;
        this.pes = pes;
    }

    public void setCloudlet ( final Cloudlet cl) {
        this.cloudlet = cl;
    }

    public Cloudlet getCloudlet() {
        return this.cloudlet;
    }

    public void setHost(Host host){
        this.allocatedHost = host;
    }
    
    public Host getHost() {
        return this.allocatedHost;
    }

    public int getPes(){
        return this.pes;
    }

    public int getmipsCapacity() {
        return mipsCapacity*pes;
    }
        
}
