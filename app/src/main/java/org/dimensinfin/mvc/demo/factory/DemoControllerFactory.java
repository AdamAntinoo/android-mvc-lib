package org.dimensinfin.mvc.demo.factory;

import androidx.annotation.NonNull;

import org.dimensinfin.android.mvc.factory.ControllerFactory;
import org.dimensinfin.android.mvc.controller.IAndroidController;
import org.dimensinfin.android.mvc.factory.IControllerFactory;
import org.dimensinfin.core.interfaces.ICollaboration;
import org.dimensinfin.mvc.demo.controller.ApplicationHeaderTitleController;
import org.dimensinfin.mvc.demo.controller.DemoItemController;
import org.dimensinfin.mvc.demo.controller.PageButtonController;
import org.dimensinfin.mvc.demo.controller.TitleLabelController;
import org.dimensinfin.mvc.demo.controller.TitledActionBarController;
import org.dimensinfin.mvc.demo.domain.ApplicationHeaderTitle;
import org.dimensinfin.mvc.demo.domain.ColorfulFigure;
import org.dimensinfin.mvc.demo.domain.ColorfulFigureController$Base;
import org.dimensinfin.mvc.demo.domain.DemoLabel;
import org.dimensinfin.mvc.demo.domain.PageButton;
import org.dimensinfin.mvc.demo.domain.TitleLabel;
import org.dimensinfin.mvc.demo.ui.TitledActionBar;

/**
 * @author Adam Antinoo
 */
public class DemoControllerFactory extends ControllerFactory implements IControllerFactory {
	// - C O N S T R U C T O R S
	public DemoControllerFactory( @NonNull final String selectedVariant ) {
		super( selectedVariant );
	}

	/**
	 * The method should create the matching controller for the model received. We can use the variant to change at
	 * creation time the controller or to replace controllers when required.
	 */
	@Override
	public IAndroidController createController( final ICollaboration node ) {
		if (node instanceof PageButton) {
			// These shows the selected Separator but with another rendering.
			return new PageButtonController( (PageButton) node, this ).setRenderMode( this.getVariant() );
		}
		if (node instanceof TitledActionBar) {
			// These shows the selected Separator but with another rendering.
			return new TitledActionBarController( (TitledActionBar) node, this ).setRenderMode( this.getVariant() );
		}
		if (node instanceof DemoLabel) {
			// These shows the selected Separator but with another rendering.
			return new DemoItemController( (DemoLabel) node, this ).setRenderMode( this.getVariant() );
		}
		if (node instanceof ApplicationHeaderTitle) {
			// These shows the selected Separator but with another rendering.
			return new ApplicationHeaderTitleController( (ApplicationHeaderTitle) node, this ).setRenderMode( this.getVariant() );
		}
		if (node instanceof TitleLabel) {
			// These shows the selected Separator but with another rendering.
			return new TitleLabelController( (TitleLabel) node, this ).setRenderMode( this.getVariant() );
		}
		// If no part is trapped then call the parent chain until one is found.
		return super.createController( node );
	}
}
