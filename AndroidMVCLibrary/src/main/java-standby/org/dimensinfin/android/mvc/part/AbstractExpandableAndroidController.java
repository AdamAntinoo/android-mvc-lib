//  PROJECT:     Android.MVC (A.MVC)
//  AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
//  COPYRIGHT:   (c) 2013-2018 by Dimensinfin Industries, all rights reserved.
//  ENVIRONMENT: Android API16.
//  DESCRIPTION: Library that defines a generic Model View Controller core classes to be used
//               on Android projects. Defines the AndroidController factory and the AndroidController core methods to manage
//               a generic converter from a Graph Model to a hierarchical AndroidController model that finally will
//               be converted to a AndroidController list to be used on a BaseAdapter tied to a ListView.
//               The new implementation performs the model to list transformation on the fly each time
//               a model change is detected so the population of the displayed view should be done in
//               real time while processing the model sources. This should allow for search and filtering.
package org.dimensinfin.android.mvc.part;

import android.os.Handler;
import android.view.View;
import org.dimensinfin.android.mvc.constants.SystemWideConstants;
import org.dimensinfin.android.mvc.core.AbstractAndroidAndroidController;
import org.dimensinfin.android.mvc.core.ClickSupporter;
import org.dimensinfin.android.mvc.core.OneShotTask;
import org.dimensinfin.core.interfaces.ICollaboration;
import org.joda.time.Instant;
import org.joda.time.Duration;

import java.util.concurrent.TimeUnit;

/**
 * @author Adam Antinoo
 */

// - CLASS IMPLEMENTATION ...................................................................................
public abstract class AbstractExpandableAndroidController extends AbstractAndroidAndroidController implements View.OnClickListener {
	// - S T A T I C - S E C T I O N ..........................................................................
//	private static Logger logger = LoggerFactory.getLogger("AbstractExpandableAndroidController");

	// - F I E L D - S E C T I O N ............................................................................
	// - C O M P O S I T I O N S
	private ClickSupporter clickSupporter = ClickSupporter.newSupporter(false);

	// - C O N S T R U C T O R - S E C T I O N ................................................................
	public AbstractExpandableAndroidController(final ICollaboration model) {
		super(model);
	}

	// - M E T H O D - S E C T I O N ..........................................................................
	// - C L I C K S U P P O R T E R
	private boolean isClickRunning() {
		return clickSupporter.isClickRunning();
	}

	private void setClickRunning(final boolean clickRunning) {
		clickSupporter.setClickRunning(clickRunning);
	}

	public boolean activateClick() {
		this.setClickRunning(true);
		return this.isClickRunning();
	}

	public boolean completeClick() {
		this.setClickRunning(false);
		return this.isClickRunning();
	}

	public void onClick(final View view) {
		logger.info(">> [AbstractExpandableAndroidController.onClick]");
		// Signal the action may take some time and launch it on the background.
		this.activateClick();
		// Force the update of the view by eliminating it form the AndroidController cache. This forces a redraw and updates the model.
		invalidate();

		new Handler().postDelayed(new OneShotTask<AbstractExpandableAndroidController>(this) {
			public void run() {
				getActivity().runOnUiThread(new Runnable() {
					@Override
					public void run() {
						logger.info(">> [NeoComExpandablePart.onClick.OneShotTask.run]");
						final Instant chrono = Instant.now();
						getTarget().toggleExpanded();
						getTarget().fireStructureChange(SystemWideConstants.events.EVENTCONTENTS_ACTIONEXPANDCOLLAPSE.name(), this, this);
						completeClick();
						getTarget().invalidate();
						logger.info("<< [AbstractExpandableAndroidController.onClick.OneShotTask.run]> Time Elapsed: {}ms" ,
							new	Duration(chrono, Instant.now()).getMillis() + "ms");
					}
				});
			}
		}, TimeUnit.MICROSECONDS.toMillis(500));
		logger.info("<< [NeoComExpandablePart.onClick]");
	}

	private AbstractExpandableAndroidController getTarget() {
		return this;
	}

	//--- G E T T E R S   &   S E T T E R S
	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer("AbstractExpandableAndroidController [ ");
		buffer.append("model: ").append(this.getModel().toString());
		buffer.append("]");
//		buffer.append("->").append(super.toString());
		return buffer.toString();
	}
}
// - UNUSED CODE ............................................................................................
//[01]
