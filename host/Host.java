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

    private int capacityMips = 0;     //ホストが持ってるMIPS容量
    private int requestMips = 0;     //すべてのVMが占有しているMPIS
    private int utilizationMips = 0; //今使用しているMPIS
    

    private List<Vm> allocatedVmList = new ArrayList<Vm>();

    
    public Host(final List<Pe> pelist) {
        this.active = false;
        this.peList = pelist;
        peNum = peList.size();
        peList.forEach(pe -> { this.capacityMips += pe.getmipsCapacity(); });
        System.out.println("createHost::" + peNum + ", " + capacityMips + ", " + requestMips);
    }


    public void removeVm(Vm vm){
        vm.setHost(null);
    }

    /** 今のhostの使用率(0~1)を返す
     **   配置されている vmのリストから，VMのMIPS使用率をとってきて，合計する
    **/
    public double nowHostUtilization() {
        int utilizationMips = nowHostUtilizationMIPS();
        double utilization = (double)utilizationMips/(double)capacityMips;
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
        addStateHistoryEntry(time);
    }
     
    //今のhostの情報を記録する
    public void addStateHistoryEntry( final double time) {

        //新しい情報
        final var newState = new HostStateHistoryEntry(time,
                                                       this.capacityMips,
                                                       this.requestMips,
                                                       nowHostUtilizationMIPS(),
                                                       this.active);

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
        requestMips -= vm.getmipsCapacity();
    }
    
    public int getInActivePes() {
        return peNum - activePe;
    }
        
    public boolean getActive () {
        return active;
    }

}     
