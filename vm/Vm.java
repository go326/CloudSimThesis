package CloudSimThesis.vm;

import CloudSimThesis.host.*;
import CloudSimThesis.cloudlet.*;

public class Vm {

    private int mipsCapacity;
    private int pes;
    private Cloudlet cloudlet;
    private Host allocatedHost;
    private int nowUtilizateMIPS;

    public Vm(final int mipsCapacity, final int pes) {
        this.mipsCapacity = mipsCapacity * pes;
        this.pes = pes;
    }

    public void simCycle(double time, double interval) {
        computeUsageMips(interval);
        
    }

    private void computeUsageMips(double interval){
        //cloudletのcpu使用modelを取得
        double utilization = this.cloudlet.getUtilization();
        //intervalあたりのMIPS
        double intervalCapa = (double)mipsCapacity * interval;
        this.nowUtilizateMIPS = (int)Math.round(intervalCapa * utilization);
        this.cloudlet.decLength(nowUtilizateMIPS);
        this.allocatedHost.addAllocatedMips(nowUtilizateMIPS);
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

    public int getNowUtilizateMIPS() {
        return nowUtilizateMIPS;
    }
}

