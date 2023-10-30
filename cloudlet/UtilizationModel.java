package CloudSimThesis.cloudlet;

import java.util.*;



public class UtilizationModel {

    private Random r;
    private double util;
    
    public UtilizationModel(final long seed){
        r = new Random(seed);
        util = 0.4;
    }

    public double getUtilization(){
        // 0~1の間の使用率を返却
        // 一回前の使用率から
        double tmp;
        tmp = r.nextGaussian()/15;
        util =  Math.floor((util + tmp)*100)/100 ;
        while ( util <= 0.05 || 1 <= util) {
            tmp = r.nextGaussian()/15 + 0.05;
            util =  Math.floor((util + tmp)*100)/100 ;
        }
        return util;
    }

}
