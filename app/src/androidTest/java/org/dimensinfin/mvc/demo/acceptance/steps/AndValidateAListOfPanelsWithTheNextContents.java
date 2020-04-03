package org.dimensinfin.mvc.demo.acceptance.steps;

import java.util.List;
import java.util.Map;

import org.junit.Assert;

import org.dimensinfin.android.mvc.controller.IAndroidController;
import org.dimensinfin.mvc.demo.acceptance.support.PanelTypes;
import org.dimensinfin.mvc.demo.acceptance.support.core.MVCWorld;
import org.dimensinfin.mvc.demo.acceptance.support.ristretto.Ristretto;
import org.dimensinfin.mvc.demo.acceptance.validators.ValidatePageButtonPanels;
import org.dimensinfin.mvc.demo.controller.PageButtonController;

import cucumber.api.java.en.Then;

public class AndValidateAListOfPanelsWithTheNextContents extends SupportStepParent {
	public AndValidateAListOfPanelsWithTheNextContents( final MVCWorld world ) {
		super( world );
	}

	@Then("validate a list of {string} panels with the next contents")
	public void andValidateAListOfPanelsWithTheNextContents( final String panelTypeName, final List<Map<String, String>> dataTable ) {
		List<IAndroidController> panelsMatching;
		try {
			final PanelTypes panelType = PanelTypes.from( panelTypeName );
			switch (panelType) {
				case PAGE_BUTTON_PANEL:
					final List<IAndroidController> pageButtonPanelsMatching = Ristretto.withType(
							Ristretto.accessHeaderPanels( this.world.getSelectedPage() ),
							PageButtonController.class );
					Assert.assertNotNull( pageButtonPanelsMatching );
					Assert.assertTrue( pageButtonPanelsMatching.size() > 0 );
					Assert.assertTrue( new ValidatePageButtonPanels<PageButtonController>( this.world )
							                   .validatePageButtonPanels(
									                   dataTable,
									                   pageButtonPanelsMatching
							                   ) );
					break;
				default:
					throw new NullPointerException( "The panel type " + panelTypeName + " is not defined on the list of registered panels." );
			}
		} catch (final IllegalArgumentException iae) {
			throw new NullPointerException( "The panel type " + panelTypeName + " is not defined on the list of registered panels." );
		}
	}
}
