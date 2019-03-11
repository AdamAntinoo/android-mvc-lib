package org.dimensinfin.android.mvc.controller;

import android.content.Context;
import android.support.annotation.NonNull;
import junit.framework.Assert;
import org.dimensinfin.android.mvc.factory.ControllerFactory;
import org.dimensinfin.android.mvc.interfaces.IAndroidController;
import org.dimensinfin.android.mvc.interfaces.IControllerFactory;
import org.dimensinfin.android.mvc.interfaces.IRender;
import org.dimensinfin.android.mvc.model.EmptyNode;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Adam Antinoo
 */
public class AAndroidControllerTest {
	final static class TestController extends AAndroidController<EmptyNode> implements IAndroidController<EmptyNode> {
		private boolean visible = true;

		public TestController(final @NonNull EmptyNode model, final @NonNull IControllerFactory factory) {
			super(model, factory);
		}

		@Override
		public IRender buildRender(final Context context) {
			return null;
		}

		@Override
		public long getModelId() {
			return 0;
		}

//		@Override
//		public int compareTo(@NonNull final EmptyNode other) {
//			final EmptyNode model = this.getModel();
//			return model.getName().compareTo(other.getName());
//		}

		@Override
		public boolean isVisible() {
			return visible;
		}

		public TestController setVisible(final boolean visible) {
			this.visible = visible;
			return this;
		}

		@Override
		public int compareTo(@NonNull final Object o) {
			final TestController target = (TestController) o;
			return this.getModel().getName().compareTo(((TestController) o).getModel().getName());
		}

	}

	private static ControllerFactory factory;
	private static TestController controller;

	@Before
	public void setUp() throws Exception {
		factory = Mockito.mock(ControllerFactory.class);
		controller = new TestController(new EmptyNode("Test"), factory);
	}

	@Test
	public void addChild() {
		// Given
		final int initial = controller.getChildren().size();

		// Test
		controller.addChild(new TestController(new EmptyNode("Test"), factory));

		// Assert
		Assert.assertEquals("The number of initial children is 0.", 0, initial);
		Assert.assertEquals("The number of child is 1.", 1, controller.getChildren().size());
	}

	@Test
	public void addChildren() {
		// Given
		final int initial = controller.getChildren().size();
		final List<IAndroidController<EmptyNode>> data = new ArrayList<>();
		for (int index = 0; index < 9; index++) {
			data.add(new TestController(new EmptyNode("Test" + index), factory));
		}

		// Test
		controller.addChildren(data);

		// Assert
		Assert.assertEquals("The number of initial children is 0.", 0, initial);
		Assert.assertEquals("The number of child is 1.", 9, controller.getChildren().size());
	}

	@Test
	public void clean() {
		// Given
		final int initial = controller.getChildren().size();
		final List<IAndroidController<EmptyNode>> data = new ArrayList<>();
		for (int index = 0; index < 9; index++) {
			data.add(new TestController(new EmptyNode("Test" + index), factory));
		}

		// Test
		controller.addChildren(data);

		// Assert
		Assert.assertEquals("The number of child is 1.", 9, controller.getChildren().size());
		controller.clean();
		Assert.assertEquals("The number of initial children is 0.", 0, controller.getChildren().size());
	}

	@Test
	public void collaborate2View_allvisibleelements() {
		// Given
		final List collector = new ArrayList();
		final List<IAndroidController<EmptyNode>> data = new ArrayList<>();
		for (int index = 0; index < 9; index++) {
			data.add(new TestController(new EmptyNode("Test" + index), factory));
		}

		// Test
		controller.addChildren(data);
		controller.collaborate2View(collector);

		// Asserts
		Assert.assertEquals("The collaboration should be 9+1 elements.", 10, collector.size());
	}

	@Test
	public void collaborate2View_containernotvisible() {
		// Given
		final List collector = new ArrayList();
		final List<IAndroidController<EmptyNode>> data = new ArrayList<>();
		for (int index = 0; index < 9; index++) {
			data.add(new TestController(new EmptyNode("Test" + index), factory));
		}

		// Test
		controller.addChildren(data);
		controller.setVisible(false);
		controller.collaborate2View(collector);

		// Asserts
		Assert.assertEquals("The collaboration should be 9 elements.", 9, collector.size());
	}

	@Test
	public void orderingFeature_notordered() {
		// Given
		controller.setOrderedActive(false);
		controller.addChild(new TestController(new EmptyNode("First"), factory));
		controller.addChild(new TestController(new EmptyNode("Last"), factory));

		// Test
		controller.orderingFeature(controller.getChildren());

		// Asserts
		Assert.assertEquals("The first should be First", "First", controller.getChildren().get(0).getModel().getName());
		Assert.assertEquals("The first should be Last", "Last", controller.getChildren().get(1).getModel().getName());
	}

	@Test
	public void orderingFeature_ordered() {
		// Given
		controller.setOrderedActive(true);
		controller.addChild(new TestController(new EmptyNode("Last"), factory));
		controller.addChild(new TestController(new EmptyNode("First"), factory));

		// Test
		controller.orderingFeature(controller.getChildren());

		// Asserts
		Assert.assertEquals("The first should be First", "First", controller.getChildren().get(0).getModel().getName());
		Assert.assertEquals("The first should be Last", "Last", controller.getChildren().get(1).getModel().getName());
	}

	@Test
	public void buildRender() {
	}


	@Test
	public void isVisible() {
	}

	@Test
	public void refreshChildren() {
	}
}