package org.dimensinfin.android.mvc.controller;

import org.dimensinfin.android.mvc.factory.ControllerFactory;
import org.dimensinfin.android.mvc.model.DemoLabelCounter;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * @author Adam Antinoo
 */
public class DemoLabelCounterControllerTest {
	private static final ControllerFactory factory = Mockito.mock(ControllerFactory.class);
	private static final DemoLabelCounter counter = new DemoLabelCounter("Counter");

	@Test
	public void getModel() {
		// Given
		final DemoLabelCounterController controller = new DemoLabelCounterController(counter, factory);
		// Asserts
		Assert.assertEquals("The model should match.", counter, controller.getModel());
		Assert.assertEquals("The model class should match.", counter.getClass(), controller.getModel().getClass());
		Assert.assertTrue("Class should be correct.", (controller.getModel() instanceof DemoLabelCounter));
	}

	@Test
	public void getModelId() {
		// Given
		final DemoLabelCounterController controller = new DemoLabelCounterController(counter, factory);
		final long expected = counter.hashCode();
		// Asserts
		Assert.assertEquals("The model unique identifier number should match.", expected, controller.getModelId());
	}

	@Test
	public void compareTo() {
		// Given
		final DemoLabelCounterController controller1 = new DemoLabelCounterController(counter, factory);
		final DemoLabelCounterController controller2 = new DemoLabelCounterController(counter, factory);
		final DemoLabelCounterController controller3 = new DemoLabelCounterController(new DemoLabelCounter("Counter 2"), factory);
		// Asserts
		Assert.assertEquals("Controller 1 orders equal to Controller 2.", 0, controller1.compareTo(controller2));
		Assert.assertEquals("Controller 1 is smaller than Controller 3.", -2, controller1.compareTo(controller3));
		Assert.assertEquals("Controller 3 is grater than Controller 2.", 2, controller3.compareTo(controller2));
	}
}