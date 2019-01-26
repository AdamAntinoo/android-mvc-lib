package org.dimensinfin.android.mvc.controller;

import android.test.AndroidTestCase;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import org.dimensinfin.android.mvc.R;
import org.dimensinfin.android.mvc.factory.ControllerFactory;
import org.dimensinfin.core.model.Separator;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * @author Adam Antinoo
 */
public class SeparatorRenderTest extends AndroidTestCase {
private View testView ;


	@Before
	public void setUp() throws Exception {
		super.setUp();
		// Given
		final Separator model = new Separator("Test Separator");
		final ControllerFactory factory= Mockito.mock(ControllerFactory.class);
		final SeparatorController controller = new SeparatorController.Builder(model, factory).build();
		final SeparatorRender render = new SeparatorRender.Builder(controller, getContext()).build();
		testView =  LayoutInflater.from(getContext())
				.inflate(render.accessLayoutReference(), null);	}

	@Test
	public void getController() {
	}

	@Test
	public void initializeViews() {
		final TextView expected = testView.findViewById(R.id.title);
		assertNotNull(expected);
	}

	@Test
	public void updateContent() {
	}

	@Test
	public void accessLayoutReference() {
	}
}