package org.dimensinfin.android.mvc.core;

/**
 * @author Adam Antinoo
 */
public class AppCompatibilityUtils {
    private static AppCompatibilityUtils ourInstance = new AppCompatibilityUtils();

    public static AppCompatibilityUtils getInstance() {
        return ourInstance;
    }

    public static boolean assertNotNull(final Object target) {
        if (null == target) throw new NullPointerException("Mandatory parameter is null.");
        return true;
    }

    private AppCompatibilityUtils() {
    }
}
