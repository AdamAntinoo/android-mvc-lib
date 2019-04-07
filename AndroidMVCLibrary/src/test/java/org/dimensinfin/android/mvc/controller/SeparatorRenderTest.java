package org.dimensinfin.android.mvc.controller;

import android.view.View;
import android.widget.TextView;

import org.dimensinfin.android.mvc.R;
import org.dimensinfin.android.mvc.factory.ControllerFactory;
import org.dimensinfin.android.mvc.model.Separator;

import org.junit.Test;
import org.mockito.Mockito;

/**
 * @author Adam Antinoo
 */
public class SeparatorRenderTest {
	private View testView;

	//	@Before
	public void setUp() throws Exception {
		//		super.setUp();
		// Given
		final Separator model = new Separator("Test Separator");
		final ControllerFactory factory = Mockito.mock(ControllerFactory.class);
		final SeparatorController controller = new SeparatorController(model, factory);
		// Test cases that use the Android framework still do not work
		//		final SeparatorRender render = new SeparatorRender(controller, getContext());
		//		testView = LayoutInflater.from(getContext())
		//				.inflate(R.layout.exception4list, null);
	}

	//	@Test
	public void getController() {
	}

	//	@Test
	public void initializeViews() {
		final TextView expected = testView.findViewById(R.id.title);
//		assertNotNull(expected);
	}

	@Test
	public void updateContent() {
	}

	@Test
	public void accessLayoutReference() {
	}
}