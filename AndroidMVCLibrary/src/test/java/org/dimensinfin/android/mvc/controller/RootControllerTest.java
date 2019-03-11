package org.dimensinfin.android.mvc.controller;

import org.dimensinfin.android.mvc.factory.ControllerFactory;
import org.dimensinfin.android.mvc.interfaces.IAndroidController;
import org.dimensinfin.android.mvc.interfaces.ICollaboration;
import org.dimensinfin.android.mvc.model.MVCRootNode;
import org.dimensinfin.android.mvc.model.Separator;
import org.dimensinfin.android.mvc.support.TestControllerFactory;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Adam Antinoo
 */
public class RootControllerTest {
	public static class TestNode extends Separator {
		private List<ICollaboration> children = new ArrayList<>();

		public void addChild(final ICollaboration child) {
			this.children.add(child);
		}

		@Override
		public List<ICollaboration> collaborate2Model(final String variant) {
			return new ArrayList<ICollaboration>(this.children);
		}
	}

	@Test(expected = NullPointerException.class)
	public void refreshChildren_unprobableNotModelConnected() {
		// Test
		final RootController root = new RootController(null, null);
	}

	@Test
	public void refreshChildren_rootEmpty() {
		// Given
		final MVCRootNode node = mock(MVCRootNode.class);
		final ControllerFactory factory = mock(ControllerFactory.class);

		// When
		when(node.collaborate2Model("TEST")).thenReturn(new ArrayList<>());
		when(factory.getVariant()).thenReturn("TEST");

		// Test
		final RootController root = new RootController(node, factory);
		root.refreshChildren();

		// Assert
		verify(node, times(1)).collaborate2Model("TEST");
	}

	@Test
	public void refreshChildren_root1SimpleNode() {
		// Given
		final MVCRootNode node = new MVCRootNode();
		final Separator child = new Separator("SEPARATOR-TEST");
		node.addChild(child);
		final ControllerFactory factory = mock(ControllerFactory.class);

		// When
		when(factory.getVariant()).thenReturn("TEST");
		when(factory.createController(child)).thenReturn(new SeparatorController(child, factory));

		// Test
		final RootController root = new RootController(node, factory);
		root.refreshChildren();
		final List<ICollaboration> controllers = node.getChildren();

		// Assert
		Assert.assertNotNull(controllers);
		Assert.assertEquals(1, controllers.size());
	}

	@Test
	public void refreshChildren_root5MultiLevelNode() {
		// Given
		final MVCRootNode node = new MVCRootNode();
		final Separator child = new Separator("SEPARATOR-TEST");
		node.addChild(child);
		node.addChild(child);
		node.addChild(child);
		final TestNode levelChild = new TestNode();
		levelChild.addChild(child);
		levelChild.addChild(child);
		node.addChild(levelChild);

		// Test
		final RootController root = new RootController(node, new TestControllerFactory("TEST"));
		root.refreshChildren();
		final List<ICollaboration> controllers = node.getChildren();

		// Assert
		Assert.assertNotNull(controllers);
		Assert.assertEquals(4, controllers.size());
	}

	@Test
	public void refreshChildren_root5WithPreGeneration() {
		// Given
		final MVCRootNode node = new MVCRootNode();
		final Separator child = new Separator("SEPARATOR-TEST");
		node.addChild(child);
		node.addChild(child);
		node.addChild(child);
		final TestNode levelChild = new TestNode();
		levelChild.addChild(child);
		levelChild.addChild(child);

		// Test
		final RootController root = new RootController(node, new TestControllerFactory("TEST"));
		root.refreshChildren();
		List<ICollaboration> controllers = node.getChildren();

		// Assert
		Assert.assertNotNull(controllers);
		Assert.assertEquals(3, controllers.size());

		// Add another node and check again
		node.addChild(levelChild);
		final ICollaboration expected = controllers.get(2);

		// Test
		root.refreshChildren();
		controllers = node.getChildren();
		final ICollaboration obtained = controllers.get(2);

		// Assert
		Assert.assertNotNull(controllers);
		Assert.assertEquals(4, controllers.size());
		Assert.assertTrue(expected == obtained);
	}

	@Test
	public void refreshChildren_root5WithNodeReduction() {
		// Given
		final MVCRootNode node = new MVCRootNode();
		final Separator child = new Separator("SEPARATOR-TEST");
		node.addChild(child);
		node.addChild(child);
		node.addChild(child);
		final TestNode levelChild = new TestNode();
		levelChild.addChild(child);
		levelChild.addChild(child);
		node.addChild(levelChild);

		// Test
		final RootController root = new RootController(node, new TestControllerFactory("TEST"));
		root.refreshChildren();
		List<ICollaboration> controllers = node.getChildren();

		// Assert
		Assert.assertNotNull(controllers);
		Assert.assertEquals(4, controllers.size());

		// Remove one of the nodes.
		final List<ICollaboration> modelList = node.getChildren();
		final List<ICollaboration> newList = new ArrayList<>();
		newList.add(modelList.get(0));
		newList.add(modelList.get(1));
		newList.add(modelList.get(3));
		node.clean();
		node.addChildren(newList);

		// Test
		root.refreshChildren();
		controllers = node.getChildren();
		final ICollaboration obtained = controllers.get(2);

		// Assert
		Assert.assertNotNull(controllers);
		Assert.assertEquals(3, controllers.size());
	}

	@Test
	public void collaborate2View() {
		// Given
		final MVCRootNode node = new MVCRootNode();
		final Separator child = new Separator("SEPARATOR-TEST");
		node.addChild(child);
		node.addChild(child);
		node.addChild(child);
		final TestNode levelChild = new TestNode();
		levelChild.addChild(child);
		levelChild.addChild(child);
		node.addChild(levelChild);

		// Test
		final RootController root = new RootController(node, new TestControllerFactory("TEST"));
		root.refreshChildren();
		final List<IAndroidController> contentCollector = new ArrayList<>();
		root.collaborate2View(contentCollector);

		// Assert
		Assert.assertEquals(6, contentCollector.size());
	}

	@Test
	public void collaborate2View_nodeReduction() {
		// Given
		final MVCRootNode node = new MVCRootNode();
		final Separator child = new Separator("SEPARATOR-TEST");
		node.addChild(child);
		node.addChild(child);
		node.addChild(child);
		final TestNode levelChild = new TestNode();
		levelChild.addChild(child);
		levelChild.addChild(child);
		node.addChild(levelChild);

		// Test
		final RootController root = new RootController(node, new TestControllerFactory("TEST"));
		root.refreshChildren();
		final List<IAndroidController> contentCollector = new ArrayList<>();
		root.collaborate2View(contentCollector);

		// Assert
		Assert.assertEquals(6, contentCollector.size());

		// Remove one of the nodes.
		final List<ICollaboration> modelList = node.getChildren();
		final List<ICollaboration> newList = new ArrayList<>();
		newList.add(modelList.get(0));
		newList.add(modelList.get(1));
		newList.add(modelList.get(3));
		node.clean();
		node.addChildren(newList);

		// Test
		root.refreshChildren();
		contentCollector.clear();
		root.collaborate2View(contentCollector);

		// Assert
		Assert.assertEquals(5, contentCollector.size());
	}
}