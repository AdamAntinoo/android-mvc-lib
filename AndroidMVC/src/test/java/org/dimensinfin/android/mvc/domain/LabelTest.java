package org.dimensinfin.android.mvc.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class LabelTest {
	private static final String NEW_LABEL = "-NEW-LABEL-";

	@Test
	void builderComplete() {
		final Label label = new Label.Builder().withLabel( NEW_LABEL ).build();
		Assertions.assertNotNull( label );
	}

	@Test
	void builderFailure() {
		Assertions.assertThrows( NullPointerException.class, () -> {
			new Label.Builder().withLabel( null ).build();
		} );
		Assertions.assertThrows( NullPointerException.class, () -> {
			new Label.Builder().build();
		} );
	}

	@Test
	void getterContract() {
		final Label label = new Label.Builder().withLabel( NEW_LABEL ).build();
		// Assertions
		Assertions.assertEquals( NEW_LABEL, label.getLabel() );
	}

	@Test
	void setterContract() {
		final Label label = new Label.Builder().withLabel( NEW_LABEL ).build();
		// Assertions
		label.setLabel( "-ANOTHER-LABEL-" );
		Assertions.assertNotEquals( NEW_LABEL, label.getLabel() );
		Assertions.assertEquals( "-ANOTHER-LABEL-", label.getLabel() );
	}
}