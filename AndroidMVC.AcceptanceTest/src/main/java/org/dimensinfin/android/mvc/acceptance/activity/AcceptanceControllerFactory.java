package org.dimensinfin.android.mvc.activity;

import org.dimensinfin.android.mvc.TitlePanel;
import org.dimensinfin.android.mvc.controller.ControllerFactory;
import org.dimensinfin.android.mvc.controller.IAndroidController;
import org.dimensinfin.android.mvc.controller.TitlePanelController;
import org.dimensinfin.core.interfaces.ICollaboration;

public class AcceptanceControllerFactory extends ControllerFactory {
	public AcceptanceControllerFactory( final String selectedVariant ) {
		super(selectedVariant);
	}

	@Override
	public IAndroidController createController( final ICollaboration node ) {
		if (node instanceof TitlePanel)
			return new TitlePanelController((TitlePanel) node, this);

		return super.createController(node);
	}
}
