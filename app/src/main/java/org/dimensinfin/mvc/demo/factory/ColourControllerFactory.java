package org.dimensinfin.mvc.demo.factory;

import org.dimensinfin.android.mvc.controller.IAndroidController;
import org.dimensinfin.android.mvc.domain.Container;
import org.dimensinfin.android.mvc.factory.ControllerFactory;
import org.dimensinfin.core.interfaces.ICollaboration;
import org.dimensinfin.logging.LogWrapper;
import org.dimensinfin.mvc.demo.controller.FigureGroupController;
import org.dimensinfin.mvc.demo.domain.ColorfulFigure;
import org.dimensinfin.mvc.demo.domain.ColorfulFigureController$Base;

public class ColourControllerFactory extends ControllerFactory {

	public ColourControllerFactory( final String selectedVariant ) {
		super( selectedVariant );
	}

	@Override
	public IAndroidController createController( final ICollaboration node ) {
		LogWrapper.info( "Searching for controller: {}", node.getClass().getSimpleName() );
		if (node instanceof Container) {
			// These shows the selected Separator but with another rendering.
			return new FigureGroupController( (Container) node, this ).setRenderMode( this.getVariant() );
		}
		if (node instanceof ColorfulFigure) {
			// These shows the selected Separator but with another rendering.
			return new ColorfulFigureController$Base( (ColorfulFigure) node, this ).setRenderMode( this.getVariant() );
		}

		return super.createController( node );
	}
}
