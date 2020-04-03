package org.dimensinfin.android.mvc.render;

import android.content.Context;

import androidx.test.runner.AndroidJUnitRunner;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;

import org.dimensinfin.android.mvc.controller.AndroidController;
import org.dimensinfin.android.mvc.support.TestRender4Render;
//@RunWith(JUnit4.class)
public class MVCRenderTest {
	@Test
	public void constructorContract() {
		final AndroidController controller = Mockito.mock( AndroidController.class );
		final Context context = Mockito.mock( Context.class );
		final MVCRender render = new TestRender4Render( controller, context );
		Assertions.assertNotNull( render );
	}
}