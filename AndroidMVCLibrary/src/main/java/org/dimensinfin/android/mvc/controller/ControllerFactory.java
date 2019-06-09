package org.dimensinfin.android.mvc.controller;

import org.dimensinfin.android.mvc.activity.AMVCPagerFragment;
import org.dimensinfin.android.mvc.exception.MVCExceptionHandler;
import org.dimensinfin.android.mvc.support.SeparatorController;
import org.dimensinfin.android.mvc.interfaces.IControllerFactory;
import org.dimensinfin.core.interfaces.ICollaboration;
import org.dimensinfin.core.model.Separator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ControllerFactory implements IControllerFactory {
	protected static Logger logger = LoggerFactory.getLogger(ControllerFactory.class);

	// - F I E L D - S E C T I O N
	private final String variant;

	// - C O N S T R U C T O R - S E C T I O N
	public ControllerFactory(final String selectedVariant) {
		this.variant = selectedVariant;
	}

	// - M E T H O D - S E C T I O N
	public IAndroidController createController(final ICollaboration node) {
		// Associate the default classes defined at the MVC.
		if (node instanceof Separator) {
			return new SeparatorController((Separator) node,this)
					.setRenderMode(this.getVariant());
		}
		if (node instanceof MVCExceptionHandler.ExceptionModel) {
			return new ExceptionController((MVCExceptionHandler.ExceptionModel) node,this);
		}
		// If no part is trapped then result a NOT FOUND mark
		return new SeparatorController(new Separator("-NO Model-Controller match-[" + node.getClass().getSimpleName() + "]-")
				,this);
	}

	// - G E T T E R S   &   S E T T E R S
	public String getVariant() {
		return variant;
	}
}
