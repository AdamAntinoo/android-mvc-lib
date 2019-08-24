package org.dimensinfin.android.mvc.controller;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;

import org.dimensinfin.android.mvc.domain.IContainer;
import org.dimensinfin.android.mvc.domain.Spacer;
import org.dimensinfin.android.mvc.domain.IControllerFactory;
import org.dimensinfin.android.mvc.interfaces.IRender;
import org.dimensinfin.android.mvc.support.Container;
import org.dimensinfin.android.mvc.support.TestContainerNode;
import org.dimensinfin.android.mvc.support.TestContainerNodeController;
import org.dimensinfin.android.mvc.support.TestControllerFactory;
import org.dimensinfin.android.mvc.support.TestNode;
import org.dimensinfin.android.mvc.support.TestNodeController;
import org.dimensinfin.core.interfaces.ICollaboration;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

/**
 * This is the JUnit test class to check all the features available to controllers and specifically to the main
 * controller implementation represented by the abstract AndroidController class. There is a need for a test additional
 * class because the class under test is abstract and cannot be instantiated.
 *
 * @author Adam Antinoo
 */
public class AndroidControllerTest {
	private static final String TEST_VARIANT = "-TEST-VARIANT-";

	public static <T> T giveNull() {
		return null;
	}

	private final List<IAndroidController> data = new ArrayList<>();
	private ControllerFactory factory;
	private TestNode model;
	private TestNodeController controller;

	@Before
	public void setUp() {
		this.factory = Mockito.mock(ControllerFactory.class);
		this.model = new TestNode("TEST NODE");
		this.controller = new TestNodeController(this.model, this.factory);
		this.data.clear();
		for (int index = 0; index < 9; index++) {
			this.data.add(new TestNodeController(new TestNode("Test" + index), this.factory));
		}
	}

	@Test
	public void collaborate2ViewSimple() {
		final TestNodeController controller = new TestNodeController(model, factory);
		final List<IAndroidController> collector = new ArrayList<>();
		controller.collaborate2View(collector);
		Assert.assertEquals("The number of elements should be 1.", 1, collector.size());
		Assert.assertEquals("The contents should be the controller.", controller, collector.get(0));
	}

	@Test
	public void collaborate2ViewExpandable() {
		// COMPRESSED
		final Container expandableModel = new Container("Title");
		expandableModel.addContent(new TestNode("TEST"));
		final MockExpandableController controller = new MockExpandableController(expandableModel, factory);
		final List<IAndroidController> collector = new ArrayList<>();
		controller.collaborate2View(collector);
		Assert.assertEquals("The number of elements should be 1.", 1, collector.size());
		Assert.assertEquals("The contents should be the controller.", controller, collector.get(0));
		// EXPANDED
		expandableModel.expand();
		controller.collaborate2View(collector);
		Assert.assertEquals("The number of elements should be 2.", 2, collector.size());
		Assert.assertEquals("The contents should be the controller.", controller, collector.get(0));
	}

	@Test
	public void collaborate2ViewContainer() {
		final MockContainerModel containerModel = new MockContainerModel();
		final MockContainerController controller = new MockContainerController(containerModel, factory);
		final List<IAndroidController> collector = new ArrayList<>();
		controller.collaborate2View(collector);
		Assert.assertEquals("The number of elements should be 1.", 1, collector.size());
		Assert.assertEquals("The contents should be the controller.", controller, collector.get(0));
	}

	@Test(expected = NullPointerException.class)
	public void createWithNulls() {
		// Given a null creation call
		final TestNodeController controller = new TestNodeController((TestNode) giveNull(), giveNull());
	}

	@Test
	public void createWithValidParameters() {
		// Given
		final String expected = "Create Test";
		final TestNodeController controller = new TestNodeController(new TestNode(expected), factory);
		// Tests
		final String actual = controller.getModel().getName();
		// Asserts
		Assert.assertEquals("Model label should match.", expected, actual);
		Assert.assertEquals("Model class should be the one declared.", "TestNode",
		                    controller.getModel().getClass().getSimpleName());
		Assert.assertEquals("Factory should be the same.", factory, controller.getControllerFactory());
	}

	@Test
	public void renderField() {
		// Given
		final String expected = "-TEST-RENDER-";
		// Tests
		controller.setRenderMode(expected);
		// Asserts
		Assert.assertEquals("The render mode should match.", expected, controller.getRenderMode());
	}

	@Test
	public void orderActiveField() {
		// Given
		final boolean expected = true;
		// Tests
		controller.setOrderedActive(true);
		// Asserts
		Assert.assertEquals("The render mode should match.", expected, controller.isOrderedActive());
	}

	@Test
	public void viewCacheField() {
		// Given
		final View expected = Mockito.mock(View.class);
		// Tests
		controller.setViewCache(expected);
		// Asserts
		Assert.assertEquals("The render mode should match.", expected, controller.getViewCache());
	}

	@Test
	public void addChild() {
		// Given
		final int initial = controller.getChildren().size();
		// Test
		controller.addChild(new TestNodeController(new TestNode("Test"), factory));
		// Assert
		Assert.assertEquals("The number of initial children is 0.", 0, initial);
		Assert.assertEquals("The number of child is 1.", 1, controller.getChildren().size());
	}

	@Test
	public void addChildren() {
		// Given
		final int initial = controller.getChildren().size();
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
		// Test
		controller.addChildren(data);
		// Assert
		Assert.assertEquals("The number of child is 1.", 9, controller.getChildren().size());
		controller.clean();
		Assert.assertEquals("The number of initial children is 0.", 0, controller.getChildren().size());
	}

	//	@Test
	public void collaborate2View_allVisibleElements() {
		// Given
		final List collector = new ArrayList();
		// Test
		controller.addChildren(data);
		controller.collaborate2View(collector);
		// Asserts
		Assert.assertEquals("The collaboration should be 9+1 elements.", 10, collector.size());
	}

	//	@Test
	public void collaborate2View_containerNotVisible() {
		// Given
		final List collector = new ArrayList();
		// Test
		controller.addChildren(data);
		//		controller.setVisible(false);
		controller.collaborate2View(collector);
		// Asserts
		Assert.assertEquals("The collaboration should be 9 elements.", 9, collector.size());
	}

	@Test
	public void orderingFeature_notordered() {
		// Given
		controller.setOrderedActive(false);
		controller.addChild(new TestNodeController(new TestNode("First"), factory));
		controller.addChild(new TestNodeController(new TestNode("Last"), factory));
		// Test
		controller.orderingFeature(controller.getChildren());
		// Asserts
		final List c = controller.getChildren();
		Assert.assertEquals("The first should be First", "First", ((TestNodeController) controller.getChildren().get(0))
				                                                          .getModel().getName());
		Assert.assertEquals("The first should be Last", "Last", ((TestNodeController) controller.getChildren().get(1)).getModel()
				                                                        .getName());
	}

	@Test
	public void orderingFeature_ordered() {
		// Given
		controller.setOrderedActive(true);
		controller.addChild(new TestNodeController(new TestNode("Last"), factory));
		controller.addChild(new TestNodeController(new TestNode("First"), factory));

		// Test
		controller.orderingFeature(controller.getChildren());

		// Asserts
		//		Assert.assertEquals("The first should be First", "First", controller.getChildren().get(0).getModel().getName());
		//		Assert.assertEquals("The first should be Last", "Last", controller.getChildren().get(1).getModel().getName());
	}

	@Test
	public void refreshChildren() {
		// Given
		final List<TestNode> testModelHierarchy = new ArrayList();
		testModelHierarchy.add(new TestNode("Node 1"));
		testModelHierarchy.add(new TestNode("Node 2"));
		final TestNodeController mockController = new TestNodeController(new MultipleModelCollaborator("Data"), factory);

		// When
		when(factory.createController(any(TestNode.class))).thenReturn(new TestNodeController(new TestNode("Test"), factory));
		when(factory.getVariant()).thenReturn("TEST");

		// Asserts
		Assert.assertEquals("The initial list is empty.", 0, mockController.getChildren().size());

		// Test
		mockController.refreshChildren();
		final List<IAndroidController> endList = mockController.getChildren();

		// Asserts
		Assert.assertEquals("The end list has 2 items.", 2, mockController.getChildren().size());
		Mockito.verify(factory, times(2)).createController(any(TestNode.class));
	}

	@Test
	public void refreshChildrenWithContents() {
		final IControllerFactory factory = new TestControllerFactory(TEST_VARIANT);
		final TestContainerNode container = new TestContainerNode();
		container.addContent(new TestNode("Node 1"));
		final TestContainerNodeController controller = new TestContainerNodeController(container, factory);
		controller.refreshChildren();

		final int expected = controller.getChildren().size();
		Assert.assertFalse(controller.getChildren().isEmpty());
		controller.refreshChildren(); // Run the generation again but this time with a list already generated.
		Assert.assertFalse(controller.getChildren().isEmpty());
		Assert.assertEquals(expected, controller.getChildren().size());
	}

	@Test
	public void getModel() {
		Assert.assertNotNull("Check that the model exists.", controller.getModel());
		Assert.assertEquals(model, controller.getModel());
	}

	@Test
	public void getRenderMode_default() {
		final String obtained = controller.getRenderMode();
		Assert.assertEquals("Check the value for the default render mode.", "-DEFAULT-", obtained);
	}

	@Test
	public void getRenderMode_set() {
		controller.setRenderMode("TEST MODE");
		final String obtained = controller.getRenderMode();
		Assert.assertEquals("Check the value for the default render mode.", "TEST MODE", obtained);
	}

	@Test
	public void getModelId() {
		final int expected = model.hashCode();
		final long obtained = controller.getModelId();
		Assert.assertEquals("Check that the default id matches.", expected, obtained);
	}

	@Test
	public void getModelIdUnique() {
		final TestControllerFactory factory = new TestControllerFactory(TEST_VARIANT);
		final TestNodeController controller = new TestNodeController(new TestNode("-UNIQUE-TEST-"), factory);
		final int expected = 100;
		final long obtained = controller.getModelId();
		Assert.assertEquals("Check that the default id matches.", expected, obtained);
	}
}

final class MultipleModelCollaborator extends TestNode implements ICollaboration {

	public MultipleModelCollaborator( final String name ) {
		super(name);
	}

	@Override
	public List<ICollaboration> collaborate2Model( final String variation ) {
		final List<ICollaboration> data = new ArrayList<>();
		data.add(new TestNode("Data 1"));
		data.add(new TestNode("Data 2"));
		return data;
	}
}

final class MockExpandableController extends AndroidController<Container> {

	public MockExpandableController( @NonNull final Container model, @NonNull final IControllerFactory factory ) {
		super(model, factory);
	}

	@Override
	public IRender buildRender( final Context context ) {
		return null;
	}
}

final class MockContainerController extends AndroidController<MockContainerModel> {

	public MockContainerController( @NonNull final MockContainerModel model, @NonNull final IControllerFactory factory ) {
		super(model, factory);
	}

	@Override
	public IRender buildRender( final Context context ) {
		return null;
	}
}

final class MockContainerModel extends Spacer implements IContainer {
	@Override
	public List<ICollaboration> collaborate2Model( final String variant ) {
		final List<ICollaboration> results = new ArrayList<>();
		results.add(new Spacer.Builder().build());
		return results;
	}

	@Override
	public boolean wants2Collaborate() {
		return true;
	}
}
