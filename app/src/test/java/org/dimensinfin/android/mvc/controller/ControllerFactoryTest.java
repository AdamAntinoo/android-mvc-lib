package org.dimensinfin.android.mvc.controller;

import android.content.Context;

import org.dimensinfin.mvc.demo.acceptance.activity.AcceptanceActivity01;
import org.dimensinfin.android.mvc.domain.Spacer;
import org.dimensinfin.android.mvc.exception.ExceptionReport;
import org.dimensinfin.android.mvc.support.Container;
import org.dimensinfin.core.interfaces.ICollaboration;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

public class ControllerFactoryTest {
	@Test
	public void verifyControllerGeneration() {
		final ControllerFactory factory = new ControllerFactory("-UNIT-TEST-");

		Assert.assertEquals("SpacerController",
		                    factory.createController(new Spacer.Builder().build()).getClass().getSimpleName());
		Assert.assertEquals("ExceptionController",
		                    factory.createController(new ExceptionReport(new NullPointerException("Test exception")))
				                    .getClass().getSimpleName());
		Assert.assertEquals("SpacerController",
		                    factory.createController(new Container("Empty")).getClass().getSimpleName());
		Assert.assertEquals("SpacerController",
		                    factory.createController(new TestModel()).getClass().getSimpleName());
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

		factory.cleanRegistry();
		factory.prepareActivity("AcceptanceActivity01", context);
	}

	@Test
	public void registerActivity() {
		final Context context = Mockito.mock(Context.class);
		final ControllerFactory factory = new ControllerFactory("-UNIT-TEST-");

		factory.registerActivity("AcceptanceActivity01", AcceptanceActivity01.class);
		Assert.assertNotNull(factory.prepareActivity("AcceptanceActivity01", context));
	}
	public static class TestModel implements ICollaboration{
		@Override
		public List<ICollaboration> collaborate2Model( final String variation ) {
			return new ArrayList<>();
		}

		@Override
		public int compareTo( final Object o ) {
			return 0;
		}
	}
}