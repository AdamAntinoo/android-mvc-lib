package org.dimensinfin.android.mvc.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class SpacerTest {
	private static final String SPACER_LABEL = "-SPACER-LABEL-";

	@Test
	void constructorContract() {
		final Spacer spacer = new Spacer();
		Assertions.assertNotNull( spacer );
	}

	@Test
	void builderComplete() {
		final Spacer spacer = new Spacer.Builder()
				                      .withLabel( SPACER_LABEL )
				                      .withType( SpacerType.LINE_DARKBLUE )
				                      .build();
		Assertions.assertNotNull( spacer );
	}

	@Test
	void builderFailure() {
		assertThrows( NullPointerException.class, () -> {
			new Spacer.Builder()
					.withLabel( null )
					.withType( SpacerType.LINE_DARKBLUE )
					.build();
		} );
		assertThrows( NullPointerException.class, () -> {
			new Spacer.Builder()
					.withLabel( SPACER_LABEL )
					.withType( null )
					.build();
		} );
	}

	@Test
	void getterContractEmpty() {
		final Spacer spacer = new Spacer.Builder().build();
		// Assertions
		Assertions.assertEquals( SpacerType.LINE_WHITE, spacer.getType() );
		Assertions.assertNull( spacer.getLabel() );
	}

	@Test
	void getterContract() {
		final Spacer spacer = new Spacer.Builder().withType( SpacerType.LINE_GREEN ).withLabel( SPACER_LABEL ).build();
		// Assertions
		Assertions.assertEquals( SpacerType.LINE_GREEN, spacer.getType() );
		Assertions.assertEquals( SPACER_LABEL, spacer.getLabel() );
	}

	@Deprecated
	@Test
	void setterContract() {
		final String expected = "-NEW-LABEL-";
		final Spacer spacer = new Spacer.Builder().withType( SpacerType.LINE_GREEN ).withLabel( SPACER_LABEL ).build();
		// Assertions
		Assertions.assertEquals( SPACER_LABEL, spacer.getLabel() );

		spacer.setLabel( expected );
		Assertions.assertNotEquals( SPACER_LABEL, spacer.getLabel() );
		Assertions.assertEquals( expected, spacer.getLabel() );
	}
}