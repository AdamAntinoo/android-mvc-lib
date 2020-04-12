package org.dimensinfin.android.mvc.render;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class MVCRenderTest {
	//	@Test
	public void constructorContract() {
		final MVCRender render = Mockito.mock( MVCRender.class, Mockito.CALLS_REAL_METHODS );
	}

	@Test
	public void getterContract() {
		// Given
		final MVCRender render = Mockito.mock( MVCRender.class );
		// When
		Mockito.doCallRealMethod().when( render ).getContext();
		Mockito.doCallRealMethod().when( render ).getController();
		// Assertions
		Assert.assertNull( render.getContext() );
		Assert.assertNull( render.getController() );
	}
}