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
import org.dimensinfin.android.mvc.interfaces.IControllerFactory;
import org.dimensinfin.android.mvc.interfaces.IRender;
import org.dimensinfin.android.mvc.render.SeparatorRender;
import org.dimensinfin.core.model.Separator;
import org.joda.time.Instant;

public class SeparatorController extends AAndroidController<Separator> {
	// - F I E L D - S E C T I O N
	// - C O N S T R U C T O R - S E C T I O N
	protected SeparatorController(final SeparatorController.Builder builder) {
		super(builder);
	}

	// - M E T H O D - S E C T I O N
	public String getTitle() {
		return this.getModel().getTitle();
	}

	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer("SeparatorController [");
		buffer.append(getModel().toString()).append(" ");
		buffer.append("]");
		return buffer.toString();
	}

	// - I A N D R O I D C O N T R O L L E R   I N T E R F A C E

	/**
	 * This method is required by the Adapter to get a unique identifier for each node to be render on a Viewer.
	 * @return a unique number identifier.
	 */
	@Override
	public long getModelId() {
		return Instant.now().getMillis();
	}

	@Override
	public IRender getRenderer(final Context context) {
		return new SeparatorRender.Builder(this, context)
				.controller(this)
				.build();
	}

	// - B U I L D E R
	public static class Builder extends AAndroidController.Builder<Separator> {
		public Builder(final Separator model, final IControllerFactory factory) {
			super(model, factory);
		}

		public SeparatorController build() {
			return new SeparatorController(this);
		}
	}
}
