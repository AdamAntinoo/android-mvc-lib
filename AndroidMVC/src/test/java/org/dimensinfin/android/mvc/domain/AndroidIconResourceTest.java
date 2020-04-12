package org.dimensinfin.android.mvc.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import org.dimensinfin.android.mvc.R;

class AndroidIconResourceTest {
	private static final Integer ANY_DEFAULT_ICON_ID = 321654;

	@Test
	void constructorContract() {
		AndroidIconResource iconResource = new AndroidIconResource();
		Assertions.assertNotNull( iconResource );
		Assertions.assertEquals( R.drawable.defaulticonplaceholder, iconResource.getIdentifier() );
		iconResource = new AndroidIconResource( ANY_DEFAULT_ICON_ID );
		Assertions.assertNotNull( iconResource );
		Assertions.assertEquals( ANY_DEFAULT_ICON_ID, iconResource.getIdentifier() );
	}

	@Test
	void builderComplete() {
		final AndroidIconResource iconResource = new AndroidIconResource.Builder()
				                                         .withResourceId( ANY_DEFAULT_ICON_ID ).build();
		Assertions.assertNotNull( iconResource );
		Assertions.assertEquals( ANY_DEFAULT_ICON_ID, iconResource.getIdentifier() );
	}

	@Test
	void builderFailure() {
		Assertions.assertThrows( NullPointerException.class, new AndroidIconResource.Builder().withResourceId( null )::build );
	}
}