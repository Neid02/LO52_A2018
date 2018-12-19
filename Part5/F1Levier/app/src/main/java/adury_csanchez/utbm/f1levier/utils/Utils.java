package adury_csanchez.utbm.f1levier.utils;

import android.content.Context;
import android.util.TypedValue;

import java.util.List;

public class Utils {
    public static int mapPXtoDP(Context context, int dimensionInPx){
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dimensionInPx, context.getResources().getDisplayMetrics()));
    }
    public static String formatTime(long millis){
        String time = "";
        if(millis>=60000) time = String.format("%d'",millis/60000);
        return time + String.format("%02d\"%03d",(millis-millis/60000*60000)/1000,millis-millis/1000*1000);
    }

    public static Long average(List<Long> values){
        int i = values.size();
        long sum=0;
        for(Long l : values)
            sum+=l;
        return sum/i;
    }
}
