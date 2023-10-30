package CloudSimThesis.event;

import CloudSimThesis.host.*;

public class PowerOnEvent extends Event {

    private final Host targetHost;
    
    public PowerOnEvent(double delay, Host h){
        super(delay);
        this.targetHost = h;
    }

    /** Eventクラスのeventメソッドを継承
     ** 最初に super.event(interval)を行う必要がある
     ** 
     **/
    @Override
    public boolean event(double interval){
        if(super.event(interval)){
            targetHost.powerOn();
            return true;
        } else { return false; }
    }
}
