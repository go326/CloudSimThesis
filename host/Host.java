package CloudSimThesis.host;

import CloudSimThesis.vm.*;
//import CloudSimThesis.host.Pe;
//import CloudSimThesis.PowerModel;

import java.util.*;

public class Host {

    private List<Pe> peList;
    private final int peNum;
    private int activePe;
    private List<HostStateHistoryEntry> stateHistory;
    private PowerModelHost powermodel;
    private boolean active;

    private int allMips;
    private int requestMips = 0;
    private int allocatedMips = 0; 
    

    private List<Vm> allocatedVmList = new ArrayList<Vm>();

    
    public Host(final List<Pe> pelist) {
        this.active = false;
        this.peList = pelist;
        peNum = peList.size();
        pelist.forEach(p -> { this.allMips += p.getmipsCapacity(); });
        this.allocatedMips = 0;
        System.out.println("createHost::" + peNum + ", " + allMips + "," + allocatedMips);
    }

    public void powerOn() {
        this.active = true;
        System.out.println("powerOn :: " + this);
    }

    public void powerOff() {
        this.active = false;
        System.out.println("powerOff :: " + this);
    }
    
    public void vmAllocateToHost(final Vm vm) {
        allocatedVmList.add(vm);
        activePe += vm.getPes();
        requestMips += vm.getmipsCapacity();
        vm.setHost(this);
    }

    public int getInActivePes() {
        return peNum - activePe;
    }
        
    public boolean getActive () {
        return active;
    }

    public void addAllocatedMips(int mips) {
        this.allocatedMips += mips;
        //System.out.println(this + ", " + allocatedMips);
    }

    public void resetAllocatedMips() {
        this.allocatedMips = 0;
    }

    public double getallocatedMips() {
        return (double) allocatedMips;
    }
    
    public double getHostUtilization() {
//        System.out.println("hostUtilization::" + allocatedMips + ", " + allMips + "::");
        return ((double)allocatedMips / (double)allMips) * 100;
    }

    //hostの情報を記録する
    private void addStateHistoryEntry(
        final double time,
        final double allocatedMips,
        final double requestedMips,
        final boolean isActive) {
        final var newState = new HostStateHistoryEntry(time, allocatedMips, requestedMips, isActive);
        if (!stateHistory.isEmpty()) {
            final var previousState = stateHistory.get(stateHistory.size() - 1);
            if (previousState.time() == time) {
                stateHistory.set(stateHistory.size() - 1, newState);
                return;
            }
        }
        stateHistory.add(newState);
    }

}     
