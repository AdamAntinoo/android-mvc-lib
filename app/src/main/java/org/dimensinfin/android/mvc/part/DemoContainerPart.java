//  PROJECT:     Android.MVC (A.MVC)
//  AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
//  COPYRIGHT:   (c) 2013-2018 by Dimensinfin Industries, all rights reserved.
//  ENVIRONMENT: Android API16.
//  DESCRIPTION: Library that defines a generic Model View Controller core classes to be used
//               on Android projects. Defines the Part factory and the Part core methods to manage
//               a generic converter from a Graph Model to a hierarchical Part model that finally will
//               be converted to a Part list to be used on a BaseAdapter tied to a ListView.
package org.dimensinfin.android.mvc.part;

import android.app.Activity;
import android.view.View;

import org.dimensinfin.android.mvc.core.AbstractAndroidPart;
import org.dimensinfin.android.mvc.core.AbstractExpandablePart;
import org.dimensinfin.android.mvc.core.AbstractExpandableRender;
import org.dimensinfin.android.mvc.core.AbstractRender;
import org.dimensinfin.android.mvc.model.DemoContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DecimalFormat;

/**
 * @author Adam Antinoo
 */

// - CLASS IMPLEMENTATION ...................................................................................
public class DemoContainerPart extends AbstractExpandablePart {
	// - S T A T I C - S E C T I O N ..........................................................................
	private static Logger logger = LoggerFactory.getLogger("DemoItemPart");
	private static DecimalFormat itemCountFormatter = new DecimalFormat("###,##0");

	// - F I E L D - S E C T I O N ............................................................................

	// - C O N S T R U C T O R - S E C T I O N ................................................................
	public DemoContainerPart(final DemoContainer node) {
		super(node);
	}

	// - M E T H O D - S E C T I O N ..........................................................................
	public DemoContainer getCastedModel() {
		return (DemoContainer) this.getModel();
	}

	@Override
	public long getModelId() {
		return getCastedModel().getTitle().hashCode();
	}

	@Override
	protected AbstractRender selectRenderer() {
		return new DemoContainerRender(this, _activity);
	}

	//--- G E T T E R S   &   S E T T E R S
	public String getTContentCount() {
		return itemCountFormatter.format(this.getCastedModel().getContentSize());
	}

	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer("DemoItemPart [ ");
		buffer.append("name: ").append(0);
		buffer.append("]");
		buffer.append("->").append(super.toString());
		return buffer.toString();
	}

	// - CLASS IMPLEMENTATION ...................................................................................
	public static class DemoContainerRender extends AbstractExpandableRender {
		// - S T A T I C - S E C T I O N ..........................................................................

		// - F I E L D - S E C T I O N ............................................................................
		//		private TextView nodeName = null;

		// - C O N S T R U C T O R - S E C T I O N ................................................................
		public DemoContainerRender(final AbstractAndroidPart target, final Activity context) {
			super(target, context);
		}

		// - M E T H O D - S E C T I O N ..........................................................................
		@Override
		public DemoContainerPart getPart() {
			return (DemoContainerPart) super.getPart();
		}

		@Override
		public void updateContent() {
			super.updateContent();
			name.setText(getPart().getCastedModel().getTitle());
			name.setVisibility(View.VISIBLE);
			childCount.setText(this.getPart().getTContentCount());
		}
	}
}
// - UNUSED CODE ............................................................................................
//[01]
