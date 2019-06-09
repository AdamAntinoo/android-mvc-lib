//  PROJECT:     Android.MVC (A.MVC)
//  AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
//  COPYRIGHT:   (c) 2013-2018 by Dimensinfin Industries, all rights reserved.
//  ENVIRONMENT: Android API16.
//  DESCRIPTION: Library that defines a generic Model View Controller core classes to be used
//               on Android projects. Defines the AndroidController factory and the AndroidController core methods to manage
//               a generic converter from a Graph Model to a hierarchical AndroidController model that finally will
//               be converted to a AndroidController list to be used on a BaseAdapter tied to a ListView.
package org.dimensinfin.android.mvc.exception;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.dimensinfin.android.mvc.R;
import org.dimensinfin.android.mvc.controller.ControllerFactory;
import org.dimensinfin.android.mvc.controller.IAndroidController;
import org.dimensinfin.android.mvc.interfaces.IRender;
import org.dimensinfin.core.interfaces.ICollaboration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Adam Antinoo
 */

public class MVCExceptionHandler implements Thread.UncaughtExceptionHandler {
	private static Logger logger = LoggerFactory.getLogger(MVCExceptionHandler.class);

	// - F I E L D - S E C T I O N
	private final Activity activity;
	private ViewGroup exceptionContainer;
	//	private final String LINE_SEPARATOR = "\n";

	// - C O N S T R U C T O R - S E C T I O N
	public MVCExceptionHandler( Activity activity ) {
		this.activity = activity;
	}

	// - M E T H O D - S E C T I O N
	public void uncaughtException( Thread thread, Throwable exception ) {
		this.initialiseViews();
		final List<Throwable> exceptions = new ArrayList<>();
		exceptions.add(exception);
		this.generateExceptionViews(exceptions);
//		this.updateViewContents();


		//		StringWriter stackTrace = new StringWriter();
		//		exception.printStackTrace(new PrintWriter(stackTrace));
		//		StringBuilder errorReport = new StringBuilder();
		//		errorReport.append("************ CAUSE OF ERROR ************\n\n");
		//		errorReport.append(stackTrace.toString());
		//
		//		errorReport.append("\n************ DEVICE INFORMATION ***********\n");
		//		errorReport.append("Brand: ");
		//		errorReport.append(Build.BRAND);
		//		errorReport.append(LINE_SEPARATOR);
		//		errorReport.append("Device: ");
		//		errorReport.append(Build.DEVICE);
		//		errorReport.append(LINE_SEPARATOR);
		//		errorReport.append("Model: ");
		//		errorReport.append(Build.MODEL);
		//		errorReport.append(LINE_SEPARATOR);
		//		errorReport.append("Id: ");
		//		errorReport.append(Build.ID);
		//		errorReport.append(LINE_SEPARATOR);
		//		errorReport.append("Product: ");
		//		errorReport.append(Build.PRODUCT);
		//		errorReport.append(LINE_SEPARATOR);
		//		errorReport.append("\n************ FIRMWARE ************\n");
		//		errorReport.append("SDK: ");
		//		errorReport.append(Build.VERSION.SDK);
		//		errorReport.append(LINE_SEPARATOR);
		//		errorReport.append("Release: ");
		//		errorReport.append(Build.VERSION.RELEASE);
		//		errorReport.append(LINE_SEPARATOR);
		//		errorReport.append("Incremental: ");
		//		errorReport.append(Build.VERSION.INCREMENTAL);
		//		errorReport.append(LINE_SEPARATOR);

		//		Intent intent = new Intent(localContext, ForceCloseActivity.class);
		//		intent.putExtra("error", errorReport.toString());
		//		localContext.startActivity(intent);

		//		android.os.Process.killProcess(android.os.Process.myPid());
		//		System.exit(10);
	}

	private void initialiseViews() {
		this.exceptionContainer = this.activity.findViewById(R.id.exceptionContainer);
	}

	private void generateExceptionViews( final List<Throwable> exceptions ) {
		for (Throwable exception : exceptions) {
			this.addView2Container(this.generateRenderController(exceptions.get(0)));
		}
	}

	private IAndroidController generateRenderController( final Throwable exceptionToShow ) {
		final String exceptionClass = exceptionToShow.getClass().getSimpleName();
		final String exceptionMessage = exceptionToShow.getMessage();
		final ControllerFactory factory = new ControllerFactory("EXCEPTION-VARIANT");
		return factory.createController(new ExceptionModel(exceptionToShow));
	}

	private void addView2Container( final IAndroidController target ) {
		logger.info(">> [AMVCPagerFragment.addView2Container]");
		try {
			final IRender holder = target.buildRender(this.activity);
			final View hv = holder.getView();
			holder.updateContent();
			this.exceptionContainer.addView(hv);
			// Add the connection to the click listener
			//			if (target instanceof View.OnClickListener) {
			//				hv.setClickable(true);
			//				hv.setOnClickListener((View.OnClickListener) target);
			//			}
			this.exceptionContainer.setVisibility(View.VISIBLE);
		} catch (final RuntimeException rtex) {
			logger.info("RTEX [AMVCPagerFragment.addView2Container]> Problem generating view for: {}", target.getClass().getCanonicalName());
			logger.info("RTEX [AMVCPagerFragment.addView2Container]> RuntimeException. {}", rtex.getMessage());
			rtex.printStackTrace();
			Toast.makeText(this.activity
					, "RTEX [AMVCPagerFragment.addView2Container]> RuntimeException. " + rtex.getMessage()
					, Toast.LENGTH_LONG).show();
		}
		logger.info("<< [AMVCPagerFragment.addView2Container]");
	}

//	private void updateViewContents() {
//		exceptionContainer.setVisibility(View.VISIBLE);
//
//	}

	public static class ExceptionModel implements ICollaboration {
		private String type;
		private String message;

		public ExceptionModel( final Throwable exceptionToShow ) {
			this.type = exceptionToShow.getClass().getSimpleName();
			this.message = exceptionToShow.getMessage();
		}

		public String getTypeName() {
			return this.type;
		}

		public String getMessage() {
			return this.message;
		}

		@Override
		public List<ICollaboration> collaborate2Model( final String variation ) {
			return new ArrayList<>();
		}

		@Override
		public int compareTo( final Object o ) {
			return 0;
		}
	}
}
