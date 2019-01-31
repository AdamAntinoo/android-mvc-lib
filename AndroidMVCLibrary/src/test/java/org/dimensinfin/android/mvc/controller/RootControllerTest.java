package org.dimensinfin.android.mvc.controller;

import org.dimensinfin.android.mvc.factory.ControllerFactory;
import org.dimensinfin.android.mvc.interfaces.IAndroidController;
import org.dimensinfin.android.mvc.model.MVCRootNode;
import org.dimensinfin.core.interfaces.ICollaboration;
import org.dimensinfin.core.model.Separator;
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

	public static class TestControllerFactory extends ControllerFactory {

		public TestControllerFactory(final String selectedVariant) {
			super(selectedVariant);
		}

		@Override
		public IAndroidController createController(final ICollaboration node) {
			if (node instanceof TestNode) {
				return new SeparatorController((TestNode) node, this);
			}
			if (node instanceof Separator) {
				return new SeparatorController((Separator) node, this);
			}
			return super.createController(node);
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
//		final ControllerFactory factory = mock(ControllerFactory.class);

		// When
//		when(factory.getVariant()).thenReturn("TEST");
//		when(factory.createController(child)).thenReturn(new SeparatorController(child, factory));
//		when(factory.createController(levelChild)).thenReturn(new RootController(levelChild, factory));

		// Test
		final RootController root = new RootController(node, new TestControllerFactory("TEST"));
		root.refreshChildren();
		final List<ICollaboration> controllers = node.getChildren();

		// Assert
		Assert.assertNotNull(controllers);
		// TODO Should be 6 because the expansion of the TestModel
		Assert.assertEquals(4, controllers.size());
	}
}