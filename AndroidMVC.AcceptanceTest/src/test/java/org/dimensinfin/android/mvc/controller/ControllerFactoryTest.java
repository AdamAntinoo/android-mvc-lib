package org.dimensinfin.android.mvc.controller;

import android.content.Context;

import org.dimensinfin.android.mvc.acceptance.activity.AcceptanceActivity01;
import org.dimensinfin.android.mvc.exception.ExceptionReport;
import org.dimensinfin.core.model.Container;
import org.dimensinfin.core.model.Separator;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class ControllerFactoryTest {
	@Test
	public void verifyControllerGeneration() {
		final ControllerFactory factory = new ControllerFactory("-UNIT-TEST-");

		Assert.assertEquals("SeparatorController",
		                    factory.createController(new Separator()).getClass().getSimpleName());
		Assert.assertEquals("ExceptionController",
		                    factory.createController(new ExceptionReport(new NullPointerException("Test exception")))
				                    .getClass().getSimpleName());
		Assert.assertEquals("SeparatorController",
		                    factory.createController(new Container()).getClass().getSimpleName());
	}

	@Test(expected = NullPointerException.class)
	public void registerActivityNullCheck() {
		final ControllerFactory factory = new ControllerFactory("-UNIT-TEST-");

		factory.prepareActivity(null, null);
	}

	@Test(expected = NullPointerException.class)
	public void registerActivityNotFound() {
		final Context context = Mockito.mock(Context.class);
		final ControllerFactory factory = new ControllerFactory("-UNIT-TEST-");

		factory.prepareActivity("AcceptanceActivity01", context);
	}

	@Test
	public void registerActivity() {
		final Context context = Mockito.mock(Context.class);
		final ControllerFactory factory = new ControllerFactory("-UNIT-TEST-");

		factory.registerActivity("AcceptanceActivity01", AcceptanceActivity01.class);
		Assert.assertNotNull(factory.prepareActivity("AcceptanceActivity01", context));
	}
}