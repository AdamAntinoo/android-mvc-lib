package org.dimensinfin.android.mvc.parts;

import org.dimensinfin.android.mvc.core.AndroidController;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;

/**
 * @author Adam Antinoo
 */
public class PartTest {
	private TestModel model;

	@Before
	public void setup() {
		// Given
		model = new TestModel.Builder()
				.withTestField("test")
				.build();
	}

	@Test
	public void isRoot() {
		// Test
		final AndroidController target = new AndroidController(model);
		final boolean obtained = target.isRoot();

		// Assert
		assertFalse(obtained);
	}

	@Test
	public void getChildren() {
		// Given
		final AndroidController root = new AndroidController(model);
		root.addChild(new AndroidController(model);
		root.addChild(new AndroidController(model);
		root.addChild(new AndroidController(model);

		// Assert
		assertEquals(3,root.getChildren().size());
	}

	@Test
	public void getParentPart() {
		// Given
		final AndroidController root = new AndroidController(model);
		root.addChild(new AndroidController(model);
		root.addChild(new AndroidController(model);
		root.addChild(new AndroidController(model);

		// Assert
		assertEquals(root, ((AndroidController) root.getChildren().get(1)).getParentPart());
	}

	@Test
	public void getModel() {
	}

	@Test
	public void setParent() {
	}

	@Test
	public void addChild() {
	}

	@Test
	public void refreshChildren() {
	}
}

final class TestModel {
	private String test;

	private TestModel(final Builder builder) {
		this.test = builder.test;
	}

	public static class Builder {
		private String test;

		public TestModel.Builder withTestField(final String test) {
			this.test = test;
			return this;
		}

		public TestModel build() {
			return new TestModel(this);
		}
	}
}
