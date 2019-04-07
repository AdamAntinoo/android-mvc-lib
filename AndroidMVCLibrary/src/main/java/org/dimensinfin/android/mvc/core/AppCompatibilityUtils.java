package org.dimensinfin.android.mvc.core;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Adam Antinoo
 */
public class AppCompatibilityUtils {
	public static final ExecutorService backgroundExecutor = Executors.newFixedThreadPool(1);
	private static AppCompatibilityUtils ourInstance = new AppCompatibilityUtils();

	public static AppCompatibilityUtils getInstance() {
		return ourInstance;
	}

	public static boolean testAssertNotNull(final Object target) {
		if (null == target) throw new NullPointerException("Mandatory parameter is null.");
		return true;
	}

	public static boolean parameterNotNull(final Object target) {
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
