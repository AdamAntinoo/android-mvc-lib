package org.dimensinfin.android.mvc.support;

import android.content.Context;

import org.dimensinfin.android.mvc.controller.AAndroidController;
import org.dimensinfin.android.mvc.interfaces.IControllerFactory;
import org.dimensinfin.android.mvc.interfaces.IRender;
import org.dimensinfin.core.model.Separator;

import androidx.annotation.NonNull;

public class SeparatorController extends AAndroidController<Separator> {
	// - F I E L D - S E C T I O N
//	private ControllerAdapter<Separator> delegatedController;

	// - C O N S T R U C T O R - S E C T I O N
	public SeparatorController(@NonNull final Separator model, @NonNull final IControllerFactory factory) {
		super(model, factory);
	}

	// - D E L E G A T E D - A A N D R O I D C O N T R O L L E R
//	public Separator getModel() {
//		return delegatedController.getModel();
//	}

	// - M E T H O D - S E C T I O N
//	public String getTitle() {
//		return this.getModel().getTitle();
//	}
//	@Override
//	public long getModelId() {
//		return this.getModel().hashCode();
//	}

//	@Override
//	public String toString() {
//		StringBuffer buffer = new StringBuffer("SeparatorController [");
//		buffer.append(getModel().toString()).append(" ");
//		buffer.append("]");
//		return buffer.toString();
//	}

	// - I A N D R O I D C O N T R O L L E R   I N T E R F A C E

	/**
	 * This method is required by the Adapter to get a unique identifier for each node to be render on a Viewer.
	 * @return a unique number identifier.
	 */
	@Override
	public long getModelId() {
		return this.getModel().hashCode();
	}

	@Override
	public IRender buildRender(final Context context) {
		return new SeparatorRender(this, context);
	}

	@Override
	public boolean isVisible() {
		return true;
	}

	// - C O R E
	@Override
	public int compareTo(@NonNull final Object other) {
		if (other instanceof SeparatorController) {
			final SeparatorController target = (SeparatorController) other;
			return this.getModel().getTitle().compareTo(target.getModel().getTitle());
		} else return -1;
	}
}
