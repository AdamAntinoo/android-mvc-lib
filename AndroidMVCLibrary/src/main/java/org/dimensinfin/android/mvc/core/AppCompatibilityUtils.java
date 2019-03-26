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
		assert (target != null);
		return true;
	}

	private AppCompatibilityUtils() {
	}
}
