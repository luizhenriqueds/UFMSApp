package ufms.br.com.ufmsapp.utils;

import android.os.Build;

public class VersionUtils {

    public static boolean isGraterThanLollipop() {
        return Build.VERSION.SDK_INT >= 21;
    }
}
