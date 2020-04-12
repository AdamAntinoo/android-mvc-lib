package org.dimensinfin.android.mvc.render;

import android.content.Context;
import androidx.test.filters.SmallTest;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import org.dimensinfin.android.mvc.controller.AndroidController;
import org.dimensinfin.android.mvc.support.TestModel4RenderRender;

@RunWith(AndroidJUnit4ClassRunner.class)
@SmallTest
public class MVCRenderTest {
	@Test
	public void constructorContract() {
//		final TestModel4Render
		final AndroidController controller = Mockito.mock( AndroidController.class );
		final Context context = Mockito.mock( Context.class );
		final MVCRender render = new TestModel4RenderRender( controller, context );
		Assert.assertNotNull( render );
	}
}