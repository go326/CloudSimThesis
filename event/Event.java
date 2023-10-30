package CloudSimThesis.event;


public class Event {

    private double delay;

    public Event(double delay) {
        this.delay = delay;
    }

    public boolean occurreEvent(){
        return delay < 0;
    }

    public void subDelay(double sub){
        delay -= sub;
    }

    public boolean event(double interval) {
        subDelay(interval);
        return occurreEvent();
    }
     
}
