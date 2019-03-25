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
import org.dimensinfin.android.mvc.model.Separator;
import org.dimensinfin.android.mvc.render.SeparatorRender;
import org.joda.time.Instant;

public class SeparatorController extends AAndroidController {
	// - F I E L D - S E C T I O N
	private GenericController<Separator> delegatedController;

	// - C O N S T R U C T O R - S E C T I O N
	public SeparatorController(@NonNull final Separator model, @NonNull final IControllerFactory factory) {
		super(factory);
		// Connect the delegate.
		this.delegatedController = new GenericController<Separator>(model);
	}

	// - D E L E G A T E D - A A N D R O I D C O N T R O L L E R
	public Separator getModel() {
		return delegatedController.getModel();
	}

	// - M E T H O D - S E C T I O N
	public String getTitle() {
		return this.getModel().getTitle();
	}
//	@Override
//	public long getModelId() {
//		return this.getModel().hashCode();
//	}

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
	public IRender buildRender(final Context context) {
		return new SeparatorRender(this, context);
	}

//	@Override
	public int compareTo(@NonNull final Object o) {
		final SeparatorController target = (SeparatorController) o;
		return this.getModel().getTitle().compareTo(((SeparatorController) o).getModel().getTitle());
	}

//	@Override
//	public int compareTo(@NonNull final Separator other) {
//		return this.getTitle().compareTo(other.getTitle());
//	}
// - B U I L D E R
//	public static class Builder extends AAndroidController.Builder<Separator> {
//		public Builder(final Separator model, final IControllerFactory factory) {
//			super(model, factory);
//		}
//
//		public SeparatorController build() {
//			return new SeparatorController(this);
//		}
//	}
}
