package org.dimensinfin.android.mvc.demo.controller;

import android.content.Context;
import androidx.annotation.NonNull;

import org.dimensinfin.android.mvc.R;
import org.dimensinfin.android.mvc.controller.AndroidController;
import org.dimensinfin.android.mvc.demo.domain.DemoItem;
import org.dimensinfin.android.mvc.demo.domain.DemoLabel;
import org.dimensinfin.android.mvc.demo.render.DemoItemRender;
import org.dimensinfin.android.mvc.demo.render.DemoLabelRender;
import org.dimensinfin.android.mvc.domain.IControllerFactory;
import org.dimensinfin.android.mvc.domain.IRender;

/**
 * @author Adam Antinoo
 */
public class DemoItemController extends AndroidController<DemoLabel> {
	public DemoItemController( @NonNull final DemoLabel model, @NonNull final IControllerFactory factory ) {
		super( model, factory );
	}

	// - A N D R O I D C O N T R O L L E R
	@Override
	public IRender buildRender( final Context context ) {
		if (getRenderMode() == "-LABEL-") return new DemoLabelRender( this, context );
		if (getRenderMode() == "-ITEM-") return new DemoItemRender( this, context );
		return new DemoLabelRender( this, context );
	}

	// - C O M P A R A B L E   I N T E R F A C E
	@Override
	public int compareTo( @NonNull final Object o ) {
		if (o instanceof DemoItemController) {
			final DemoItemController target = (DemoItemController) o;
			final DemoLabel m = this.getModel();
			final DemoLabel t = target.getModel();
			return m.getTitle().compareTo( t.getTitle() );
		} else return -1;
	}

	@Override
	public boolean isVisible() {
		return true;
	}

	// - G E T T E R S   &   S E T T E R S
	public int getIconReference() {
		if (this.getModel() instanceof DemoItem)
			return ((DemoItem) getModel()).getIconIdentifier();
		return R.drawable.defaulticonplaceholder;
	}
}
