package org.dimensinfin.android.mvc.controller;

import android.content.Context;
import android.support.annotation.NonNull;
import junit.framework.Assert;
import org.dimensinfin.android.mvc.factory.ControllerFactory;
import org.dimensinfin.android.mvc.interfaces.IAndroidController;
import org.dimensinfin.android.mvc.interfaces.ICollaboration;
import org.dimensinfin.android.mvc.interfaces.IControllerFactory;
import org.dimensinfin.android.mvc.interfaces.IRender;
import org.dimensinfin.android.mvc.model.EmptyNode;
import org.dimensinfin.android.mvc.support.TestController;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

/**
 * @author Adam Antinoo
 */
public class AAndroidControllerTest {

	final static class MultipleModelCollaborator extends EmptyNode implements ICollaboration {

		public MultipleModelCollaborator(final String name) {
			super(name);
		}

		@Override
		public List<ICollaboration> collaborate2Model(final String variation) {
			final List<ICollaboration> data = new ArrayList<>();
			data.add(new EmptyNode("Data 1"));
			data.add(new EmptyNode("Data 2"));
			return data;
		}
	}

	private static ControllerFactory factory;
	private static TestController controller;
	private static final  List<IAndroidController> data = new ArrayList<>();

	@Before
	public void setUp() throws Exception {
		factory = Mockito.mock(ControllerFactory.class);
		controller = new TestController(new EmptyNode("Test"), factory);
		data.clear();
		for (int index = 0; index < 9; index++) {
			data.add(new TestController(new EmptyNode("Test" + index), factory));
		}
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
//		final List<IAndroidController> data = new ArrayList<>();
//		for (int index = 0; index < 9; index++) {
//			data.add(new TestController(new EmptyNode("Test" + index), factory));
//		}

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
//		final List<IAndroidController> data = new ArrayList<>();
//		for (int index = 0; index < 9; index++) {
//			data.add(new TestController(new EmptyNode("Test" + index), factory));
//		}

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
//		final List<IAndroidController> data = new ArrayList<>();
//		for (int index = 0; index < 9; index++) {
//			data.add(new TestController(new EmptyNode("Test" + index), factory));
//		}

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
//		final List<IAndroidController> data = new ArrayList<>();
//		for (int index = 0; index < 9; index++) {
//			data.add(new TestController(new EmptyNode("Test" + index), factory));
//		}

		// Test
		controller.addChildren(data);
		controller.setVisible(false);
		controller.collaborate2View(collector);

		// Asserts
		Assert.assertEquals("The collaboration should be 9 elements.", 9, collector.size());
	}

//	@Test
	public void orderingFeature_notordered() {
		// Given
		controller.setOrderedActive(false);
		controller.addChild(new TestController(new EmptyNode("First"), factory));
		controller.addChild(new TestController(new EmptyNode("Last"), factory));

		// Test
		controller.orderingFeature(controller.getChildren());

		// Asserts
		final List c = controller.getChildren();
//		Assert.assertEquals("The first should be First", "First", controller.getChildren().get(0).get().getName());
//		Assert.assertEquals("The first should be Last", "Last", controller.getChildren().get(1).getModel().getName());
	}

//	@Test
	public void orderingFeature_ordered() {
		// Given
		controller.setOrderedActive(true);
		controller.addChild(new TestController(new EmptyNode("Last"), factory));
		controller.addChild(new TestController(new EmptyNode("First"), factory));

		// Test
		controller.orderingFeature(controller.getChildren());

		// Asserts
//		Assert.assertEquals("The first should be First", "First", controller.getChildren().get(0).getModel().getName());
//		Assert.assertEquals("The first should be Last", "Last", controller.getChildren().get(1).getModel().getName());
	}

	@Test
	public void refreshChildren() {
		// Given
		final List<EmptyNode> testModelHierarchy = new ArrayList();
		testModelHierarchy.add(new EmptyNode("Node 1"));
		testModelHierarchy.add(new EmptyNode("Node 2"));
		final TestController testController = new TestController(new MultipleModelCollaborator("Data"),factory);

		// When
		when(factory.createController(any(EmptyNode.class))).thenReturn(new TestController(new EmptyNode("Test"),factory));
		when ( factory.getVariant ( )).thenReturn("TEST");

		// Asserts
		Assert.assertEquals("The initial list is empty.", 0, testController.getChildren().size());

		// Test
		testController.refreshChildren();
		final List<IAndroidController<EmptyNode>> endList = testController.getChildren();

		// Asserts
		Assert.assertEquals("The end list has 2 items.", 2, testController.getChildren().size());
		Mockito.verify(factory , times  (2)).createController(any(EmptyNode.class));
	}
}