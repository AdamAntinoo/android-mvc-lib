package org.dimensinfin.android.mvc.support;

import android.content.Context;

import androidx.annotation.NonNull;

import org.dimensinfin.android.mvc.controller.AndroidController;
import org.dimensinfin.android.mvc.domain.Spacer;
import org.dimensinfin.android.mvc.interfaces.IControllerFactory;
import org.dimensinfin.android.mvc.interfaces.IRender;

public class SpacerController extends AndroidController<Spacer> {
	public SpacerController( @NonNull final Spacer model, @NonNull final IControllerFactory factory) {
		super(model, factory);
	}

	// - I A N D R O I D C O N T R O L L E R   I N T E R F A C E
	@Override
	public IRender buildRender(final Context context) {
		return new SeparatorRender(this, context);
	}
	// - C O R E
//	@Override
//	public int compareTo(@NonNull final Object other) {
//		if (other instanceof SpacerController) {
//			final SpacerController target = (SpacerController) other;
//			return this.getModel().getTitle().compareTo(target.getModel().getTitle());
//		} else return -1;
//	}
}
