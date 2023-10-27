package CloudSimThesis.cloudlet;

import CloudSimThesis.vm.*;

public class Cloudlet {

    private int id;
    private double length;
    private UtilizationModel um;
    private Vm vm;
    private int remainingLength;

    public Cloudlet (final int length){
        this.length = length;
        this.remainingLength = length;
    }

    public void setId(final int id){
        this.id = id;
    }

    public void setVm(final Vm vm) {
        this.vm = vm;
        vm.setCloudlet(this);
    }

    public void decLength(double len){
        remainingLength -= len;
    }
    
    public int getremainingLength() {
        return remainingLength;
    }

    public void setUtilizationModel(UtilizationModel um) {
        this.um = um;
    }

    public double getUtilization() {
        return um.getUtilization();
    }

    public double getLength(){
        return length;
    }
}
