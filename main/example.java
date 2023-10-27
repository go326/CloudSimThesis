package CloudSimThesis.main;

import CloudSimThesis.datacenter.*;
import CloudSimThesis.simulation.*;
import CloudSimThesis.host.*;
import CloudSimThesis.vm.*;
import CloudSimThesis.cloudlet.*;

import java.util.*;

public class example {
    private Datacenter dc;
    private Simulation sim;
    private List<Host> HostList;  //= new ArrayList<Host>();
    private List<Vm> VmList;  //= new ArrayList<Host>();
    private List<Cloudlet> CloudletList;

    private int HOSTS = 4;

    private int HOST_PES = 10;
    private int PE_MIPS = 400;// * 1024 * 1024;
    
    private int VMS = 4;
    
    private int VM_PES = 4;
    private int VM_MIPS = 400;

    private int CLOUDLET_LENGTH = 80_000;//_000;
    
    
    
    public static void main(String[] args) {
        new example();
    }
    
    private example() {

        dc = new Datacenter(sim);
        //hostの作成
        this.HostList = createHosts();
        this.VmList = createVmsAndCloudlets();

        dc.setHostList(HostList);
        
        System.out.println(HostList);
        System.out.println(VmList);
        sim = new Simulation(dc, this.HostList, this.VmList);

        sim.start();

        printHostState();
        
        //this.VmList.forEach(v -> { System.out.println(v + " :: " +v.getHost()); });
    }

    private void printHostState() {
        for (Host h: HostList) {
            System.out.printf("%s History \n", h);
            h.printStateHistoryEntry();
        }
        System.out.println();
    }
    
    private List<Vm> createVmsAndCloudlets() {
        List<Vm> vmlist = new ArrayList<>();
        Vm vm;
        Cloudlet cl;
        for ( int i = 0; i < VMS; i++) {
            vm = new Vm(VM_MIPS, VM_PES);
            vmlist.add(vm);
            cl = new Cloudlet(CLOUDLET_LENGTH);
            cl.setVm(vm);
            cl.setUtilizationModel(new UtilizationModel(i));
        }
        return vmlist;
    }
    

    private List<Host> createHosts() {
        List<Host> hostlist = new ArrayList<>();
        for ( int i = 0; i < HOSTS; i++){
            hostlist.add(new Host(createPes()));         
        }
        return hostlist;
    }

    private List<Pe> createPes() {
        List<Pe> pelist = new ArrayList<Pe>();
        for ( int i = 0; i < HOST_PES; i++) {
            pelist.add(new Pe(PE_MIPS));
        }
        return pelist;
    }
}

