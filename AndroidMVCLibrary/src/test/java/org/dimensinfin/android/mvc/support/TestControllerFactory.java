package org.dimensinfin.android.mvc.support;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.dimensinfin.android.mvc.controller.AAndroidController;
import org.dimensinfin.android.mvc.controller.RootController;
import org.dimensinfin.android.mvc.controller.RootControllerTest;
import org.dimensinfin.android.mvc.controller.SeparatorController;
import org.dimensinfin.android.mvc.datasource.AMVCDataSource;
import org.dimensinfin.android.mvc.datasource.DataSourceLocator;
import org.dimensinfin.android.mvc.factory.ControllerFactory;
import org.dimensinfin.android.mvc.interfaces.IAndroidController;
import org.dimensinfin.android.mvc.interfaces.ICollaboration;
import org.dimensinfin.android.mvc.model.MVCRootNode;
import org.dimensinfin.android.mvc.model.Separator;
import org.junit.Assert;
import org.junit.Test;

public class TestControllerFactory extends ControllerFactory {

	public TestControllerFactory(final String selectedVariant) {
		super(selectedVariant);
	}

	@Override
	public IAndroidController createController(final ICollaboration node) {
		if (node instanceof RootControllerTest.TestNode) {
			return new SeparatorController((RootControllerTest.TestNode) node, this);
		}
		if (node instanceof Separator) {
			return new SeparatorController((Separator) node, this);
		}
		return super.createController(node);
	}
}