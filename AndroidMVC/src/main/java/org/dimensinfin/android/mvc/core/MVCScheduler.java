package org.dimensinfin.android.mvc.core;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * This class creates and makes public a background thread to run sequentially any code that should be run outside the
 * main UI thread.
 * @author Adam Antinoo
 */
public class MVCScheduler {
	public static final ExecutorService backgroundExecutor = Executors.newFixedThreadPool(1);
}
