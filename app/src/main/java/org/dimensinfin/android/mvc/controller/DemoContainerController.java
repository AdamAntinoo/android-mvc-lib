//  PROJECT:     Android.MVC (A.MVC)
//  AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
//  COPYRIGHT:   (c) 2013-2019 by Dimensinfin Industries, all rights reserved.
//  ENVIRONMENT: Android API16.
//  DESCRIPTION: Library that defines a generic Model View Controller core classes to be used
//               on Android projects. Defines the AndroidController factory and the AndroidController core methods to manage
//               a generic converter from a Graph Model to a hierarchical AndroidController model that finally will
//               be converted to a AndroidController list to be used on a BaseAdapter tied to a ListView.
package org.dimensinfin.android.mvc.controller;

import android.content.Context;
import android.support.annotation.NonNull;
import org.dimensinfin.android.mvc.interfaces.IControllerFactory;
import org.dimensinfin.android.mvc.interfaces.IRender;
import org.dimensinfin.android.mvc.model.DemoContainer;
import org.dimensinfin.android.mvc.render.AbstractRender;

import java.text.DecimalFormat;

/**
 * @author Adam Antinoo
 */
public class DemoContainerController extends AAndroidController<DemoContainer> {
	private static DecimalFormat itemCountFormatter = new DecimalFormat("###,##0");

	// - F I E L D - S E C T I O N

	// - C O N S T R U C T O R - S E C T I O N
	public DemoContainerController(final DemoContainer model, final IControllerFactory factory) {
		super(model, factory);
	}

	// - M E T H O D - S E C T I O N
	@Override
	public long getModelId() {
		return this.getModel().getTitle().hashCode();
	}

	@Override
	public IRender buildRender(final Context context) {
		return new DemoContainerRender(this, context);
	}

	// - G E T T E R S   &   S E T T E R S
	public String getTContentCount() {
		return itemCountFormatter.format(this.getModel().getContentSize());
	}

	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer("DemoItemAndroidController [ ");
		buffer.append("name: ").append(0);
		buffer.append("]");
		buffer.append("->").append(super.toString());
		return buffer.toString();
	}

	@Override
	public int compareTo(@NonNull final Object o) {
		if (o instanceof DemoContainerController) {
			final DemoContainerController target = (DemoContainerController) o;
			return this.getModel().compareTo(target.getModel());
		} else return -1;
	}

	public static class DemoContainerRender extends AbstractRender<DemoContainer> {
		// - F I E L D - S E C T I O N
		// - C O N S T R U C T O R - S E C T I O N
		public DemoContainerRender(final AAndroidController<DemoContainer> controller, final Context context) {
			super(controller, context);
		}

		// - M E T H O D - S E C T I O N
		@Override
		public void initializeViews() {

		}

		@Override
		public void updateContent() {
//			super.updateContent();
//			name.setText(getController().getCastedModel().getTitle());
//			name.setVisibility(View.VISIBLE);
//			childCount.setText(this.getController().getTContentCount());
		}

		@Override
		protected int accessLayoutReference() {
			return 0;
		}
	}
}
