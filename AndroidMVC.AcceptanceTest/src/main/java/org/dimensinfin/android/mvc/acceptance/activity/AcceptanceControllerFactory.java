package org.dimensinfin.android.mvc.acceptance.activity;

import org.dimensinfin.android.mvc.TitlePanel;
import org.dimensinfin.android.mvc.acceptance.controller.TitlePanelController;
import org.dimensinfin.android.mvc.acceptance.domain.MVC04Test;
import org.dimensinfin.android.mvc.controller.ControllerFactory;
import org.dimensinfin.android.mvc.controller.ExceptionController;
import org.dimensinfin.android.mvc.controller.IAndroidController;
import org.dimensinfin.android.mvc.exception.ExceptionReport;
import org.dimensinfin.core.interfaces.ICollaboration;

public class AcceptanceControllerFactory extends ControllerFactory {
	public AcceptanceControllerFactory( final String selectedVariant ) {
		super(selectedVariant);
	}

	@Override
	public IAndroidController createController( final ICollaboration node ) {
		if (node instanceof TitlePanel)
			return new TitlePanelController((TitlePanel) node, this).setRenderMode(this.getVariant());
		if (node instanceof ExceptionReport)
			return new ExceptionController((ExceptionReport) node, this);
		if (node instanceof MVC04Test)
			return new ExceptionController((ExceptionReport) node, this);

		return super.createController(node);
	}
}
