package org.dimensinfin.android.mvc.core;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Null detection and exception generation. If I want to fire an exception when something is null I just select the
 * right method.
 * Also it will collect some generic and used methods that can be made global.
 * @author Adam Antinoo
 */
public class AppCompatibilityUtils {
	public static final ExecutorService backgroundExecutor = Executors.newFixedThreadPool(1);

//	public static boolean testAssertNotNull(final Object target) {
//		if (null == target) throw new NullPointerException("Mandatory parameter is null.");
//		return true;
//	}
//
//	public static boolean parameterNotNull(final Object target) {
//		if (null == target) throw new NullPointerException("Mandatory parameter is null.");
//		return true;
//	}

//	public static <T> T assertNotNull(final T target) {
//		if (null == target) throw new NullPointerException("Target object is not valid. Found to be null.");
//		return target;
//	}
}
