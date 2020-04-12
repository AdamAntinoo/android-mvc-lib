package org.dimensinfin.android.mvc.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SpinnerTest {
	private static final String NEW_LABEL = "-NEW-LABEL-";

	@Test
	void builderComplete() {
		final Spinner spinner = new Spinner.Builder().build();
		Assertions.assertNotNull( spinner );
	}

	@Test
	void getterContract() {
		final Spinner spinner = new Spinner.Builder().build();
		// Assertions
		Assertions.assertEquals( null, spinner.getLabel() );
		// Assertions
		spinner.setLabel( NEW_LABEL );
		Assertions.assertEquals( NEW_LABEL, spinner.getLabel() );
	}
}