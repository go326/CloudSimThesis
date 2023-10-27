package CloudSimThesis.datacenter;

import CloudSimThesis.host.*;
import CloudSimThesis.vm.*;
import CloudSimThesis.simulation.*;

import java.util.*;

public class Datacenter {

    private Simulation sim;
    private List<Host> hostList;
    
    public Datacenter (Simulation sim, List<Host> hostlist) {
        this.sim = sim;
        this.hostList = hostlist;
    }

    public Datacenter (Simulation sim) {
        this.sim = sim;
    }

    public void setHostList(List<Host> hostlist){
        this.hostList = hostlist;
    }


    public void firstAllocate(final List<Vm> vmlist) {
        for (Vm vm: vmlist) {
            Host h = checkAllocateVmToHost(vm);
            h.vmAllocate(vm);
            vm.setHost(h);
        }
    }

    public void migration(final Vm vm) {
        Host h = vm.getHost();
        h.vmDeallocate(vm);
        checkAllocateVmToHost(vm);
    }        
    
    public void migration(final Vm vm, final Host h) {
        Host deHost = vm.getHost();
        System.out.printf("%s\n", deHost);
        deHost.vmDeallocate(vm);
        h.vmAllocate(vm);
        vm.setHost(h);
    }
            
    //firstfit
    private Host checkAllocateVmToHost(Vm vm) {
        for ( Host h: this.hostList ) {
            if (h.getInActivePes() >= vm.getPes() && h.getHostUtilization() < 90 ){
                return h;
            }
        }
        System.out.println("配置するhostがない");
        System.exit(0);
        return null;
    }
    
    
    public void migrationVmToHost(Vm vm, Host host){
    }

    // private Host findToHost(Vm vm) {
    //     return ;
    // }    
}
