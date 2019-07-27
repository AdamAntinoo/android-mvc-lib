package org.dimensinfin.android.mvc.controller;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.View;
import androidx.annotation.NonNull;

import org.dimensinfin.android.mvc.domain.IContainer;
import org.dimensinfin.android.mvc.interfaces.IControllerFactory;
import org.dimensinfin.android.mvc.interfaces.IRender;
import org.dimensinfin.android.mvc.support.Container;
import org.dimensinfin.android.mvc.support.EmptyNode;
import org.dimensinfin.android.mvc.support.MockController;
import org.dimensinfin.core.interfaces.ICollaboration;
import org.dimensinfin.core.model.Separator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

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
	private static final List<IAndroidController> data = new ArrayList<>();
	private static ControllerFactory factory;
	private static EmptyNode model;
	private static MockController controller;

	public static <T> T giveNull() {
		return null;
	}

	@Before
	public void setUp() {
		factory = Mockito.mock(ControllerFactory.class);
		model = new EmptyNode("TEST NODE");
		controller = new MockController(model, factory);
		data.clear();
		for (int index = 0; index < 9; index++) {
			data.add(new MockController(new EmptyNode("Test" + index), factory));
		}
	}

	@Test
	public void collaborate2View_simple() {
		//		final Separator model = new Separator();
		final MockController controller = new MockController(model, factory);
		final List<IAndroidController> collector = new ArrayList<>();
		controller.collaborate2View(collector);
		Assert.assertEquals("The number of elements should be 1.", 1, collector.size());
		Assert.assertEquals("The contents should be the controller.", controller, collector.get(0));
	}

	@Test
	public void collaborate2View_expandable() {
		// COMPRESSED
		final Container expandableModel = new Container();
		expandableModel.addContent(new EmptyNode("TEST"));
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
	public void collaborate2View_container() {
		final MockContainerModel containerModel = new MockContainerModel();
		final MockContainerController controller = new MockContainerController(containerModel, factory);
		final List<IAndroidController> collector = new ArrayList<>();
		controller.collaborate2View(collector);
		Assert.assertEquals("The number of elements should be 1.", 1, collector.size());
		Assert.assertEquals("The contents should be the controller.", controller, collector.get(0));

//		final List<IAndroidController> collector = new ArrayList<>();
//		final Separator model = new Separator();
	}

	@Test(expected = NullPointerException.class)
	public void createWithNulls() {
		// Given a null creation call
		final MockController controller = new MockController((EmptyNode) giveNull(), giveNull());
	}

	@Test
	public void createWithValidParameters() {
		// Given
		final String expected = "Create Test";
		final MockController controller = new MockController(new EmptyNode(expected), factory);
		// Tests
		final String actual = controller.getModel().getName();
		// Asserts
		Assert.assertEquals("Model label should match.", expected, actual);
		Assert.assertEquals("Model class should be the one declared.", "EmptyNode", controller.getModel().getClass().getSimpleName());
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
		controller.addChild(new MockController(new EmptyNode("Test"), factory));
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
		controller.addChild(new MockController(new EmptyNode("First"), factory));
		controller.addChild(new MockController(new EmptyNode("Last"), factory));
		// Test
		controller.orderingFeature(controller.getChildren());
		// Asserts
		final List c = controller.getChildren();
		Assert.assertEquals("The first should be First", "First", ((MockController) controller.getChildren().get(0)).getModel().getName());
		Assert.assertEquals("The first should be Last", "Last", ((MockController) controller.getChildren().get(1)).getModel().getName());
	}

	@Test
	public void orderingFeature_ordered() {
		// Given
		controller.setOrderedActive(true);
		controller.addChild(new MockController(new EmptyNode("Last"), factory));
		controller.addChild(new MockController(new EmptyNode("First"), factory));

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
		final MockController mockController = new MockController(new MultipleModelCollaborator("Data"), factory);

		// When
		when(factory.createController(any(EmptyNode.class))).thenReturn(new MockController(new EmptyNode("Test"), factory));
		when(factory.getVariant()).thenReturn("TEST");

		// Asserts
		Assert.assertEquals("The initial list is empty.", 0, mockController.getChildren().size());

		// Test
		mockController.refreshChildren();
		final List<IAndroidController> endList = mockController.getChildren();

		// Asserts
		Assert.assertEquals("The end list has 2 items.", 2, mockController.getChildren().size());
		Mockito.verify(factory, times(2)).createController(any(EmptyNode.class));
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

	//	@Test
	//	public void buildRender() {
	//		final Context context = Mockito.mock(Context.class);
	//		final MVCRender coreRender = Mockito.mock(MVCRender.class);
	////		Mockito.doAnswer((call)->{
	////			return null;
	////		}).when(coreRender.createView());
	//		final IRender render = controller.buildRender(context);
	//		Assert.assertNotNull(render);
	//		Assert.assertTrue(render instanceof MockRender);
	//	}

}

final class MultipleModelCollaborator extends EmptyNode implements ICollaboration {

	public MultipleModelCollaborator( final String name ) {
		super(name);
	}

	@Override
	public List<ICollaboration> collaborate2Model( final String variation ) {
		final List<ICollaboration> data = new ArrayList<>();
		data.add(new EmptyNode("Data 1"));
		data.add(new EmptyNode("Data 2"));
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

//final class MockTestControllerFactory extends ControllerFactory {
//
//	public MockTestControllerFactory( final String selectedVariant ) {
//		super(selectedVariant);
//	}
//
//	@Override
//	public IAndroidController createController( final ICollaboration node ) {
//		if (node instanceof Separator)
//			return new SeparatorController((Separator) node, this);
//		return super.createController(node);
//	}
//}
final class MockContainerController extends AndroidController<MockContainerModel> {

	public MockContainerController( @NonNull final MockContainerModel model, @NonNull final IControllerFactory factory ) {
		super(model, factory);
	}

	@Override
	public IRender buildRender( final Context context ) {
		return null;
	}
}

final class MockContainerModel extends Separator implements IContainer{
	@Override
	public List<ICollaboration> collaborate2Model( final String variant ) {
		final List<ICollaboration> results = new ArrayList<>();
		results.add(new Separator());
		return results;
	}

	@Override
	public boolean wants2Collaborate() {
		return true;
	}
}
