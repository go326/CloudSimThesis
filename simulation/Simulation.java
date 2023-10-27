package CloudSimThesis.simulation;

import CloudSimThesis.datacenter.*;
import CloudSimThesis.host.*;
import CloudSimThesis.vm.*;
import CloudSimThesis.cloudlet.*;
//import CloudSimThesis.allocationpolicy.*;

import java.util.*;

public class Simulation {

    private Datacenter dc;
    private double simTime;
    private double simInterval = 1;
    
    private List<Host> hostList;
    private List<Vm> vmList;
//    private AllocationPolicy policy;
    
    
    public Simulation(final Datacenter dc, final List<Host> hostlist, final List<Vm> vmlist) {
        this.dc = dc;
        simTime = 0;
        this.hostList = hostlist;
        this.vmList = vmlist;
        
    }
    public void start() {

        //simlation start
        //host powerOn
        hostList.forEach(h -> { h.powerOn(); });

        // First allocation
        dc.firstAllocate(vmList);

        // simlationInterval の間隔でwhileをまわす
        for ( ; ; simTime += simInterval ) {

            //vmの使用率のupdate
            vmList.forEach(vm -> { vm.simCycle(simTime, simInterval); } );

            //hostの情報を表示
            hostList.forEach(h -> { h.simCycle(simTime); } );


            if( 100 < simTime  &&  simTime <= 101) {
//                dc.migration(vmList.get(1), hostList.get(3));
            }
            
            //1さいくるの終了判定
            if(checkFinish()) break;
        }
    }



    /** simulationの終了を確認する
        すべてのcloudletの、jobの長さが０になったら終了
     **/
    private boolean checkFinish() {
        for(Vm vm : vmList) {
            int length = vm.getCloudlet().getremainingLength();
            if( length <= 0 ) continue;
            return false;
        }
        System.out.println("simulation finish");
        return true;
    }
        
}

