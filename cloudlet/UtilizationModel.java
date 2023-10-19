package CloudSimThesis.cloudlet;

import java.util.*;



public class UtilizationModel {

    private Random r;
    
    public UtilizationModel(final long seed){
        r = new Random(seed);
    }

    public double getUtilization(){
        return Math.floor(r.nextDouble() * 100)/100 ;
    }

}
