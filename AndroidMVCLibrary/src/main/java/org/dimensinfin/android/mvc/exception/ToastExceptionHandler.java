//  PROJECT:     Android.MVC (A.MVC)
//  AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
//  COPYRIGHT:   (c) 2013-2018 by Dimensinfin Industries, all rights reserved.
//  ENVIRONMENT: Android API16.
//  DESCRIPTION: Library that defines a generic Model View Controller core classes to be used
//               on Android projects. Defines the AndroidController factory and the AndroidController core methods to manage
//               a generic converter from a Graph Model to a hierarchical AndroidController model that finally will
//               be converted to a AndroidController list to be used on a BaseAdapter tied to a ListView.
package org.dimensinfin.android.mvc.exception;

import android.content.Context;
import android.widget.Toast;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;

/**
 * @author Adam Antinoo
 */

public class ToastExceptionHandler implements Thread.UncaughtExceptionHandler {
	private static Logger logger = LoggerFactory.getLogger(ToastExceptionHandler.class);

	// - F I E L D - S E C T I O N
	private final Context localContext;
//	private final String LINE_SEPARATOR = "\n";

	// - C O N S T R U C T O R - S E C T I O N
	public ToastExceptionHandler(Context context) {
		this.localContext = context;
	}

	// - M E T H O D - S E C T I O N
	public void uncaughtException(Thread thread, Throwable exception) {
		final String message = MessageFormat.format("RTEX [ToastExceptionHandler.uncaughtException]> {0}.", exception.getMessage());

		logger.error(message);
		exception.printStackTrace();
		Toast.makeText(this.localContext
				, message
				, Toast.LENGTH_LONG).show();
	}
}
