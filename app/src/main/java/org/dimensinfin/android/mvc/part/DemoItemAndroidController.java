//  PROJECT:     Android.MVC (A.MVC)
//  AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
//  COPYRIGHT:   (c) 2013-2018 by Dimensinfin Industries, all rights reserved.
//  ENVIRONMENT: Android API16.
//  DESCRIPTION: Library that defines a generic Model View Controller core classes to be used
//               on Android projects. Defines the AndroidController factory and the AndroidController core methods to manage
//               a generic converter from a Graph Model to a hierarchical AndroidController model that finally will
//               be converted to a AndroidController list to be used on a BaseAdapter tied to a ListView.
package org.dimensinfin.android.mvc.part;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import org.dimensinfin.android.mvc.controller.AAndroidController;
import org.dimensinfin.android.mvc.demo.R;
import org.dimensinfin.android.mvc.interfaces.IControllerFactory;
import org.dimensinfin.android.mvc.interfaces.IRender;
import org.dimensinfin.android.mvc.model.DemoItem;
import org.dimensinfin.android.mvc.model.DemoLabel;
import org.dimensinfin.android.mvc.render.AbstractRender;

/**
 * @author Adam Antinoo
 */

public class DemoItemAndroidController extends AAndroidController<DemoLabel> {
	// - F I E L D - S E C T I O N

	// - C O N S T R U C T O R - S E C T I O N
	public DemoItemAndroidController(final DemoLabel model, final IControllerFactory factory) {
		super(model, factory);
	}

	// - M E T H O D - S E C T I O N
	public DemoLabel getCastedModel() {
		return (DemoLabel) this.getModel();
	}

	@Override
	public long getModelId() {
		return getCastedModel().getTitle().hashCode();
	}

	// - G E T T E R S   &   S E T T E R S
	public int getIconReference() {
		if (getModel() instanceof DemoItem)
			return ((DemoItem) getModel()).getIconIdentifier();
		return R.drawable.defaulticonplaceholder;
	}

	@Override
	public IRender buildRender(final Context context) {
		if (getRenderMode() == "-LABEL-") return new DemoLabelRender(this, context);
		if (getRenderMode() == "-ITEM-") return new DemoItemRender(this, context);
		return new DemoLabelRender(this, context);
	}

	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer("DemoItemAndroidController [ ");
		buffer.append("name: ").append(0);
		buffer.append("]");
		buffer.append("->").append(super.toString());
		return buffer.toString();
	}

	public static class DemoItemRender extends DemoLabelRender {
		// - S T A T I C - S E C T I O N ..........................................................................

		// - F I E L D - S E C T I O N ............................................................................
		private ImageView nodeIcon = null;
//		private TextView nodeName = null;

		// - C O N S T R U C T O R - S E C T I O N ................................................................
		public DemoItemRender(final AAndroidController target, final Context context) {
			super(target, context);
		}

		// - M E T H O D - S E C T I O N ..........................................................................
		@Override
		public DemoItemAndroidController getController() {
			return (DemoItemAndroidController) super.getController();
		}

		@Override
		public void initializeViews() {
			super.initializeViews();
			nodeIcon = (ImageView) this.getView().findViewById(R.id.nodeIcon);
//			nodeName = (TextView) this.getView().findViewById(R.id.applicationName);
		}

		@Override
		public void updateContent() {
			super.updateContent();
			nodeIcon.setImageResource(getController().getIconReference());
//			nodeName.setText(getController().getCastedModel().getName());
//			nodeName.setVisibility(View.VISIBLE);
		}
	}

	// - CLASS IMPLEMENTATION ...................................................................................
	public static class DemoLabelRender extends AbstractRender<DemoLabel> {
		// - S T A T I C - S E C T I O N ..........................................................................

		// - F I E L D - S E C T I O N ............................................................................
		private TextView nodeName = null;

		// - C O N S T R U C T O R - S E C T I O N ................................................................
		public DemoLabelRender(final AAndroidController target, final Context context) {
			super(target, context);
		}

		// - M E T H O D - S E C T I O N ..........................................................................
		@Override
		public DemoItemAndroidController getController() {
			return (DemoItemAndroidController) super.getController();
		}

		@Override
		public void initializeViews() {
//			super.initializeViews();
			nodeName = (TextView) this.getView().findViewById(R.id.nodeName);
		}

		@Override
		public void updateContent() {
//			super.updateContent();
			nodeName.setText(getController().getCastedModel().getTitle());
			nodeName.setVisibility(View.VISIBLE);
		}

		@Override
		public int accessLayoutReference() {
			return R.layout.label4list;
		}
	}
}
// - UNUSED CODE ............................................................................................
//[01]
