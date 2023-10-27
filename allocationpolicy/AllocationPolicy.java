package CloudSimThesis.allocationpolicy;

import CloudSimThesis.host.*;
import CloudSimThesis.vm.*;

import java.util.*;

public class AllocationPolicy {

    private List<Host> hostList;
    private List<Vm> vmList;

    public AllocationPolicy (List<Host> hostlist, List<Vm> vmlist){
        this.hostList = hostlist;
        this.vmList = vmlist;
    }


    // public void staticPolicy () {
    // }
        
        
    // public void basicPolicy () {
    // }
    

    private boolean hostAleart (){
        // this.hostList.forEach(h -> {
        //         double usagePercent = h.getHostUtilization();

        //         if ( usagePercent > 90 ) {
        //             System.out.println("Aleart90 :: " + h);
        //             return true;
        //         }else if( usagePercent < 35 ) {
        //             System.out.println("Aleart35 :: " + h);
        //         }

//            });
        return false;
    }

    //.    private void allocateVmToHost(Vm vm, Host host){
    //  host.vmAllocateToHost(vm);
//    }
}
