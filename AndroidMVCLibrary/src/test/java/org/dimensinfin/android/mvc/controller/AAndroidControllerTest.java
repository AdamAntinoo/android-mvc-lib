package org.dimensinfin.android.mvc.controller;

import android.content.Context;
import android.support.annotation.NonNull;
import junit.framework.Assert;
import org.dimensinfin.android.mvc.factory.ControllerFactory;
import org.dimensinfin.android.mvc.interfaces.IControllerFactory;
import org.dimensinfin.android.mvc.interfaces.IRender;
import org.dimensinfin.android.mvc.model.EmptyNode;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * @author Adam Antinoo
 */
public class AAndroidControllerTest {
	final static class TestController extends AAndroidController<EmptyNode> {

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

		@Override
		public int compareTo(@NonNull final EmptyNode other) {
			final EmptyNode model = this.getModel();
			return model.getName().compareTo(other.getName());
		}
	}

	private static final ControllerFactory factory = Mockito.mock(ControllerFactory.class);
	private static final TestController controller = new TestController(new EmptyNode("Test"), factory);

	@Before
	public void setUp() throws Exception {

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
	}

	@Test
	public void clean() {
	}

	@Test
	public void buildRender() {
	}

	@Test
	public void collaborate2View() {
	}

	@Test
	public void orderingFeature() {
	}

	@Test
	public void isVisible() {
	}

	@Test
	public void refreshChildren() {
	}
}