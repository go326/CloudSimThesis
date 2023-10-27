package CloudSimThesis.cloudlet;

import java.util.*;



public class UtilizationModel {

    private Random r;
    private double util;
    
    public UtilizationModel(final long seed){
        r = new Random(seed);
    }

    public double getUtilization(){
        // 0~1の間の使用率を返却
        do {
            util =  Math.floor(util + r.nextGaussian()*100)/100 ;
        } while ( util <= 0 || 1 <= util);
        return util;
    }

}
