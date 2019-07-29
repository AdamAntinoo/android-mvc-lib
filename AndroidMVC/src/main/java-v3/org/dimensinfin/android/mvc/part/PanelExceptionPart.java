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
package org.dimensinfin.android.mvc.part;

import android.app.Activity;
import android.widget.TextView;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.dimensinfin.android.mvc.R;
import org.dimensinfin.android.mvc.core.AbstractAndroidPart;
import org.dimensinfin.android.mvc.core.AbstractPart;
import org.dimensinfin.android.mvc.core.AbstractRender;
import org.dimensinfin.android.mvc.model.PanelException;
import org.dimensinfin.core.interfaces.ICollaboration;

/**
 * @author Adam Antinoo
 */

// - CLASS IMPLEMENTATION ...................................................................................
public class PanelExceptionPart extends AbstractAndroidPart {
	// - S T A T I C - S E C T I O N ..........................................................................
	private static Logger logger = LoggerFactory.getLogger("PanelExceptionPart");

	// - F I E L D - S E C T I O N ............................................................................

	// - C O N S T R U C T O R - S E C T I O N ................................................................
	public PanelExceptionPart( final ICollaboration model ) {
		super(model);
	}

	// - M E T H O D - S E C T I O N ..........................................................................
	public PanelException getCastedModel(){
		return (PanelException) this.getModel();
	}
	// --- I P A R T   I N T E R F A C E
	@Override
	public long getModelId() {
		return 0;
	}

	@Override
	public AbstractRender selectRenderer() {
		return new PanelExceptionRender(this,getActivity());
	}

	// - CLASS IMPLEMENTATION ...................................................................................
	public static class PanelExceptionRender extends AbstractRender {
		// - F I E L D - S E C T I O N ............................................................................
private TextView exceptionMessage = null;

		// - C O N S T R U C T O R - S E C T I O N ................................................................
		public PanelExceptionRender( final AbstractPart newPart, final Activity context ) {
			super(newPart, context);
		}

		// - M E T H O D - S E C T I O N ..........................................................................
		public PanelExceptionPart getPart(){
			return this.getPart();
		}
		// --- I R E N D E R   I N T E R F A C E
		@Override
		public void initializeViews() {
			exceptionMessage = (TextView) _convertView.findViewById(R.id.exceptionMessage);
		}

		@Override
		public void updateContent() {
			exceptionMessage.setText(getPart().getCastedModel().getExceptionMessage());
		}

		@Override
		public int accessLayoutReference() {
			return R.layout.panelexception;
		}
	}
}
// - UNUSED CODE ............................................................................................
//[01]
