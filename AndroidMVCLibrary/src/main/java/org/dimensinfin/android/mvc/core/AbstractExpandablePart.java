//  PROJECT:     Android.MVC (A.MVC)
//  AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
//  COPYRIGHT:   (c) 2013-2018 by Dimensinfin Industries, all rights reserved.
//  ENVIRONMENT: Android API16.
//  DESCRIPTION: Library that defines a generic Model View Controller core classes to be used
//               on Android projects. Defines the Part factory and the Part core methods to manage
//               a generic converter from a Graph Model to a hierarchical Part model that finally will
//               be converted to a Part list to be used on a BaseAdapter tied to a ListView.
//               The new implementation performs the model to list transformation on the fly each time
//               a model change is detected so the population of the displayed view should be done in
//               real time while processing the model sources. This should allow for search and filtering.
package org.dimensinfin.android.mvc.core;

import android.os.Handler;
import android.view.View;

import org.dimensinfin.android.mvc.constants.SystemWideConstants;
import org.dimensinfin.core.interfaces.ICollaboration;
import org.dimensinfin.core.util.Chrono;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * @author Adam Antinoo
 */

// - CLASS IMPLEMENTATION ...................................................................................
public abstract class AbstractExpandablePart extends AbstractAndroidPart implements View.OnClickListener {
	// - S T A T I C - S E C T I O N ..........................................................................
	private static Logger logger = LoggerFactory.getLogger("AbstractExpandablePart");

	// - F I E L D - S E C T I O N ............................................................................

	// - C O N S T R U C T O R - S E C T I O N ................................................................
	public AbstractExpandablePart ( final ICollaboration model ) {
		super(model);
	}

	// - M E T H O D - S E C T I O N ..........................................................................
	public void onClick ( final View view ) {
		logger.info(">> [AbstractExpandablePart.onClick]");
		// Signal the action may take some time and launch it on the background.
		activateClick();
		// Force the update of the view by eliminating it form the Part cache. This forces a redraw and updates the model.
		invalidate();

		new Handler().postDelayed(new OneShotTask<AbstractExpandablePart>(this) {
			public void run () {
				getActivity().runOnUiThread(new Runnable() {
					@Override
					public void run () {
						logger.info(">> [NeoComExpandablePart.onClick.OneShotTask.run]");
						Chrono chrono = new Chrono();
						getTarget().toggleExpanded();
						getTarget().fireStructureChange(SystemWideConstants.events.EVENTCONTENTS_ACTIONEXPANDCOLLAPSE.name(), this, this);
						completeClick();
						getTarget().invalidate();
						logger.info("<< [NeoComExpandablePart.onClick.OneShotTask.run]> Time Elapsed: " + chrono.printElapsed
								(Chrono.ChronoOptions.SHOWMILLIS));
					}
				});
			}
		}, TimeUnit.MICROSECONDS.toMillis(500));
		logger.info("<< [NeoComExpandablePart.onClick]");
	}

	private AbstractExpandablePart getTarget () {
		return this;
	}

	//--- G E T T E R S   &   S E T T E R S
	@Override
	public String toString () {
		StringBuffer buffer = new StringBuffer("AbstractExpandablePart [ ");
		buffer.append("model: ").append(this.getModel().toString());
		buffer.append("]");
//		buffer.append("->").append(super.toString());
		return buffer.toString();
	}
}
// - UNUSED CODE ............................................................................................
//[01]
