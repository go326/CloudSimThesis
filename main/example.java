package CloudSimThesis.main;

import CloudSimThesis.simulation.*;
import CloudSimThesis.host.*;
import CloudSimThesis.vm.*;
import CloudSimThesis.cloudlet.*;

import java.util.*;

public class example {
    private Simulation sim;
    private List<Host> HostList;  //= new ArrayList<Host>();
    private List<Vm> VmList;  //= new ArrayList<Host>();
    private List<Cloudlet> CloudletList;

    private int HOSTS = 5;

    private int HOST_PES = 10;
    private int PE_MIPS = 100;// * 1024 * 1024;
    
    private int VMS = 4;
    
    private int VM_PES = 4;
    private int VM_MIPS = 100;

    private int CLOUDLET_LENGTH = 80_000;//_000;
    
    
    
    public static void main(String[] args) {
        new example();
    }
    
    private example() {
        
        //hostの作成
        this.HostList = createHosts();
        this.VmList = createVms();

        System.out.println(HostList);
        System.out.println(VmList);

        sim = new Simulation(this.HostList, this.VmList);
        sim.start();
        
        this.VmList.forEach(v -> { System.out.println(v + " :: " +v.getHost()); });
    }


    private List<Vm> createVms() {
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

