package CloudSimThesis.datacenter;

import CloudSimThesis.host.*;
import CloudSimThesis.vm.*;
import CloudSimThesis.simulation.*;
import CloudSimThesis.event.*;

import java.util.*;

public class Datacenter {

    private Simulation sim;
    private List<Host> hostList;
    private List<Event> eventList = new ArrayList<Event>();

    private double UTILIZATION_THRESHHOLD = 0.9;
    
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
            if ( vm.getHost() != null ) return ;            
            Host h = checkAllocateVmToHost(vm);
            if ( h == null ) {
                return ;
            }
            h.vmAllocate(vm);
            vm.setHost(h);
            System.out.println(vm.getmipsCapacity());
        }
    }

    public void AddEventList(final Event e){
        this.eventList.add(e);
    }
    
    public void checkEvent(double interval){
        if ( eventList.size() == 0) return;
        List<Event> removeList = new ArrayList<Event>();
        for ( Event e: eventList) {
            if( e.event(interval) ) removeList.add(e);
        }
        removeEvent(removeList);
    }

    /**ホストのCPU使用率の
     **/
    public void checkAleart(){
        for (Host h : hostList) {
            if ( UTILIZATION_THRESHHOLD < h.nowHostUtilization()){
                System.out.println("alert");
                requestMigration(h);
            }
        }
    }

    private void requestMigration(Host h){
        Vm vm = h.findVm();
        Host target = checkAllocateVmToHost(vm);
        migration(vm, target);
    }
    
    public void removeEvent(List<Event> list) {
        list.forEach ( this.eventList::remove );
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
            if ( h.nowHostUtilization() < 90 && h.getActive() && vm.getHost() != h){
                System.out.println(vm.getHost() + ", " + h);
                return h;
            }
        }

        System.out.println("配置するhostがない");
//        System.exit(0);
        return null;
    }
    
    
    public void migrationVmToHost(Vm vm, Host host){
    }

    // private Host findToHost(Vm vm) {
    //     return ;
    // }    
}
