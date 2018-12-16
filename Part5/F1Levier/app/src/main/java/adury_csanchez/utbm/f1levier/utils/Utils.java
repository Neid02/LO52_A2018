package adury_csanchez.utbm.f1levier.utils;

import android.content.Context;
import android.util.TypedValue;

public class Utils {
    public static int mapPXtoDP(Context context, int dimensionInPx){
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dimensionInPx, context.getResources().getDisplayMetrics()));
    }
}
