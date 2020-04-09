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
import org.dimensinfin.android.mvc.core.IAndroidController;
import org.dimensinfin.android.mvc.core.IRender;
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
	}

	public View getExceptionView( final Exception exception ) {
		return this.generateRenderController(exception).buildRender(this.activity).getView();
	}

	private void initialiseViews() {
		this.exceptionContainer = this.activity.findViewById(R.id.exceptionContainer);
	}

	private void generateExceptionViews( final List<Throwable> exceptions ) {
		for (Throwable exception : exceptions) {
			this.addView2Container(this.generateRenderController(exceptions.get(0)));
		}
	}

	protected IAndroidController generateRenderController( final Exception exception ) {
		final ControllerFactory factory = new ControllerFactory("EXCEPTION-VARIANT");
		return factory.createController(new ExceptionReport.Builder(exception).build());
	}

	private IAndroidController generateRenderController( final Throwable exceptionToShow ) {
		final String exceptionClass = exceptionToShow.getClass().getSimpleName();
		final String exceptionMessage = exceptionToShow.getMessage();
		final ControllerFactory factory = new ControllerFactory("EXCEPTION-VARIANT");
		return factory.createController(new ExceptionReport.Builder((Exception) exceptionToShow).build());
	}

	private void addView2Container( final IAndroidController target ) {
		logger.info(">> [MVCPagerFragment.addView2Container]");
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
			logger.info("RTEX [MVCPagerFragment.addView2Container]> Problem generating view for: {}", target.getClass().getCanonicalName());
			logger.info("RTEX [MVCPagerFragment.addView2Container]> RuntimeException. {}", rtex.getMessage());
			rtex.printStackTrace();
			Toast.makeText(this.activity
					, "RTEX [MVCPagerFragment.addView2Container]> RuntimeException. " + rtex.getMessage()
					, Toast.LENGTH_LONG).show();
		}
		logger.info("<< [MVCPagerFragment.addView2Container]");
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
