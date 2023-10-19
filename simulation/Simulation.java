package CloudSimThesis.simulation;

import CloudSimThesis.host.*;
import CloudSimThesis.vm.*;
import CloudSimThesis.cloudlet.*;
//import CloudSimThesis.allocationpolicy.*;

import java.util.*;

public class Simulation {

    private double simTime;
    private double simlationInterval = 1;
    
    private List<Host> hostList;
    private List<Vm> vmList;
//    private AllocationPolicy policy;
    
    
    public Simulation(final List<Host> hostlist, final List<Vm> vmlist) {
        this.hostList = hostlist;
        this.vmList = vmlist;
        
    }

    public void start() {
        hostList.forEach(h -> { h.powerOn(); });
        for(Vm vm : vmList) {
            Host h = checkAllocateVmToHost(vm);
            h.vmAllocateToHost(vm);
        }
        while( true ) {
            System.out.print(simTime + ":: VM ::");
            for(Vm vm : vmList ) {
                Cloudlet cl = vm.getCloudlet();
                double utilizationMips = cl.getUtilization() * (double)vm.getmipsCapacity();
                double capacity = vm.getmipsCapacity();
                //System.out.print(String.format(", %3.0f %%", 100 * utilizationMips/capacity));
                System.out.print(String.format(", %3.0f MIPS", utilizationMips));
                cl.decLength((int)utilizationMips);
                vm.getHost().addAllocatedMips((int)utilizationMips);
                //System.out.print((double)cl.getremainingLength()/ (double)cl.getLength() + ", ");
            }
            System.out.println();
            System.out.print(simTime + "::HOST::");
            for(Host h : hostList) {
                //System.out.print(String.format(", %3.0f %%", h.getHostUtilization()));
                System.out.print(String.format(", %3.0f MIPS", h.getallocatedMips()));
                h.resetAllocatedMips();
            }
            System.out.println();
            //1さいくるの終了判定
//            if ( simTime > 2 ) break;
            simTime += simlationInterval;
            if(checkFinish()) break;
        }
    }

    //firstfit
    private Host checkAllocateVmToHost(Vm vm) {
        for ( Host h: this.hostList ) {
            if (h.getInActivePes() > vm.getPes() && h.getHostUtilization() < 90 ){
                return h;
            }
        }

        System.out.println("配置するhostがない");
        System.exit(0);
        return null;
    }

    private boolean checkFinish() {
        for(Vm vm : vmList) {
            int length = vm.getCloudlet().getremainingLength();
            //System.out.println(simTime + " , " + length);
            if( length <= 0 ) continue;
            return false;
        }
        System.out.println("simulation finish");
        return true;
    }
        
}

