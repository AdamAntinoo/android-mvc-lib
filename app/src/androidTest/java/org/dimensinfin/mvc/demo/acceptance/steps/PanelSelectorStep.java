package org.dimensinfin.mvc.demo.acceptance.steps;

import java.util.List;
import java.util.Map;

import org.junit.Assert;

import org.dimensinfin.android.mvc.controller.IAndroidController;
import org.dimensinfin.mvc.demo.acceptance.support.MVCValidators;
import org.dimensinfin.mvc.demo.acceptance.support.PanelTypes;
import org.dimensinfin.mvc.demo.acceptance.support.core.MVCWorld;
import org.dimensinfin.mvc.demo.acceptance.support.ristretto.Ristretto;
import org.dimensinfin.mvc.demo.controller.ApplicationHeaderTitleController;
import org.dimensinfin.mvc.demo.controller.DemoItemController;
import org.dimensinfin.mvc.demo.controller.TitleLabelController;

import cucumber.api.java.en.Then;

public class PanelSelectorStep {
	private MVCWorld world;

	public PanelSelectorStep( final MVCWorld world ) {
		this.world = world;
		Ristretto.setWorld( this.world );
		MVCValidators.setWorld( this.world );
	}

	@Then("validate a {string} panel with the next contents")
	public void validate_a_panel_with_the_next_contents( final String panelTypeName, final List<Map<String, String>> dataTable ) {
		List<IAndroidController> panelsMatching;
		try {
			final PanelTypes panelType = PanelTypes.from( panelTypeName );
			switch (panelType) {
				case APPLICATION_HEADER_TITLE:
					panelsMatching = Ristretto.withType( Ristretto.accessHeaderPanels( this.world.getSelectedPage() ),
							ApplicationHeaderTitleController.class );
					Assert.assertNotNull( panelsMatching );
					Assert.assertTrue( panelsMatching.size() > 0 );
					Assert.assertTrue(
							MVCValidators.validateApplicationHeaderTitlePanel( dataTable.get( 0 ), panelsMatching.get( 0 ).getViewCache() ) );
					break;
				case TITLE_LABEL:
					panelsMatching = Ristretto.withType( Ristretto.accessHeaderPanels( this.world.getSelectedPage() ),
							TitleLabelController.class );
					Assert.assertNotNull( panelsMatching );
					Assert.assertTrue( panelsMatching.size() > 0 );
					Assert.assertTrue( MVCValidators.validateTitleLabelPanel( dataTable.get( 0 ), panelsMatching.get( 0 ).getViewCache() ) );
					break;
				case DEMO_LABEL:
					panelsMatching = Ristretto.withType( Ristretto.accessDataPanels( this.world.getSelectedPage() ),
							DemoItemController.class );
					Assert.assertNotNull( panelsMatching );
					Assert.assertTrue( panelsMatching.size() > 0 );
					Assert.assertTrue( MVCValidators.validateDemoLabelPanel( dataTable.get( 0 ), panelsMatching.get( 0 ).getViewCache() ) );
					break;
				default:
					throw new NullPointerException( "The panel type " + panelTypeName + " is not defined on the list of registered panels." );
			}
		} catch (final IllegalArgumentException iae) {
			throw new NullPointerException( "The panel type " + panelTypeName + " is not defined on the list of registered panels." );
		}
	}
}
