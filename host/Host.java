package CloudSimThesis.host;

import CloudSimThesis.vm.*;
//import CloudSimThesis.host.Pe;
//import CloudSimThesis.PowerModel;

import java.util.*;

public class Host {

    private List<Pe> peList;
    private final int peNum;
    private int activePe;
    private List<HostStateHistoryEntry> stateHistory = new ArrayList<HostStateHistoryEntry>();
    private PowerModelHost powermodel;
    private boolean active;

//    private int utilizationMips = 0; //今使用しているMPIS
    private int requestMips = 0;     //すべてのVMが占有しているMPIS
    private int allocatedMips = 0;   //
    

    private List<Vm> allocatedVmList = new ArrayList<Vm>();

    
    public Host(final List<Pe> pelist) {
        this.active = false;
        this.peList = pelist;
        peNum = peList.size();
        this.allocatedMips = 0;
        System.out.println("createHost::" + peNum + ", " + getHostMips() + ", " + allocatedMips);
    }


    public void addAllocatedMips(double mips) {
        this.allocatedMips += mips;
    }

    public void resetAllocatedMips() {
        this.allocatedMips = 0;
    }

    public double getallocatedMips() {
        return (double) allocatedMips;
    }

    public double getHostUtilization() {
        return ((double)allocatedMips / (double)getHostMips()) * 100;
    }

    public void removeVm(Vm vm){
        vm.setHost(null);
    }

    private int getHostMips() {
        int mips = 0;
        for (Pe p : peList) {
            mips += p.getmipsCapacity();
        }
        return mips;
    }

    /** 今のhostの使用率(0~1)を返す
     **/
    private double nowHostUtilization() {
        int utilizationMips = nowHostUtilizationMIPS();
        double utilization = (double)utilizationMips/(double)getHostMips();
        return utilization;
    }


    /** 今のhostの使用しているMIPS数を返す
     **/
    private int nowHostUtilizationMIPS() {
        int MIPS = 0;
        for (Vm vm: allocatedVmList) {
            MIPS += vm.getNowUtilizateMIPS();
        }
        return MIPS;
    }

    //hostの情報を表示する
    public void printStateHistoryEntry(){
        this.stateHistory.forEach(sh -> {System.out.println(sh.toString()); });
    }

    public void simCycle(double time) {
        double aa = nowHostUtilization();
        addStateHistoryEntry(time, allocatedMips, getHostMips(), active);
        resetAllocatedMips();
    }
    
    //hostの情報を記録する
    public void addStateHistoryEntry(
        final double time,
        final int allocatedMips,
        final int requestedMips,
        final boolean isActive) {

        //新しい情報
        final var newState = new HostStateHistoryEntry(time, allocatedMips, requestedMips, this.requestMips, isActive);

        //初回の追加の場合
        if (this.stateHistory == null || !this.stateHistory.isEmpty()) {
            final var previousState = stateHistory.get(stateHistory.size() - 1);
            if (previousState.time() == time) {
                stateHistory.set(stateHistory.size() - 1, newState);
                return;
            }
        }
        
        stateHistory.add(newState);
    }

    public void powerOn() {
        this.active = true;
        System.out.println("powerOn :: " + this);
    }

    public void powerOff() {
        this.active = false;
        System.out.println("powerOff :: " + this);
    }

    //vmを配置する
    public void vmAllocate(final Vm vm) {
        allocatedVmList.add(vm);
        activePe += vm.getPes();
        requestMips += vm.getmipsCapacity();
    }

    public void vmDeallocate(final Vm vm) {
        allocatedVmList.remove(allocatedVmList.indexOf(vm));
        activePe -= vm.getPes();
        allocatedMips -= vm.getmipsCapacity();
    }
    
    public int getInActivePes() {
        return peNum - activePe;
    }
        
    public boolean getActive () {
        return active;
    }

}     
