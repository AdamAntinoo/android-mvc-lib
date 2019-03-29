package org.dimensinfin.android.mvc.core;

/**
 * @author Adam Antinoo
 */
public class AppCompatibilityUtils {
    private static AppCompatibilityUtils ourInstance = new AppCompatibilityUtils();

    public static AppCompatibilityUtils getInstance() {
        return ourInstance;
    }

    public static boolean testAssertNotNull(final Object target) {
        if (null == target) throw new NullPointerException("Mandatory parameter is null.");
        return true;
    }

    public static <T> T assertNotNull(final T target) {
        if (null == target) throw new NullPointerException("Target object is not valid. Found to be null.");
        return target;
    }

    private AppCompatibilityUtils() {
    }
}
