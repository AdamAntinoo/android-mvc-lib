//  PROJECT:     Android.MVC (A.MVC)
//  AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
//  COPYRIGHT:   (c) 2013-2019 by Dimensinfin Industries, all rights reserved.
//  ENVIRONMENT: Android API16.
//  DESCRIPTION: Library that defines a generic Model View Controller core classes to be used
//               on Android projects. Defines the Part factory and the Part core methods to manage
//               a generic converter from a Graph Model to a hierarchical Part model that finally will
//               be converted to a Part list to be used on a BaseAdapter tied to a ListView.
package org.dimensinfin.android.mvc.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author Adam Antinoo
 */
public class UIGlobalExecutor {
	// - S T A T I C - S E C T I O N
	private static final Logger logger = LoggerFactory.getLogger(UIGlobalExecutor.class);
	private static final UIGlobalExecutor singleton = new UIGlobalExecutor();
	private static final ExecutorService uiExecutor = Executors.newFixedThreadPool(1);
	private static final List<Future<?>> runningJobs= new ArrayList<>();

	public static void submit(final Runnable task) {
		final Future<?> future = uiExecutor.submit(task);
		runningJobs.add(future);
	}

	private UIGlobalExecutor() {
	}
}
