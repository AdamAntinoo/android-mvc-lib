package org.dimensinfin.mvc.demo.acceptance.steps;

import java.util.concurrent.TimeUnit;

import org.junit.Assert;

import org.dimensinfin.mvc.demo.acceptance.support.ristretto.Ristretto;
import org.dimensinfin.mvc.demo.acceptance.support.core.AcceptanceNeoComLogger;
import org.dimensinfin.mvc.demo.acceptance.support.core.MVCWorld;

import cucumber.api.java.en.Then;

public class MVC00CommonSteps {
	private MVCWorld world;

	public MVC00CommonSteps( final MVCWorld world ) {
		this.world = world;
		Ristretto.setWorld( this.world );
	}
	@Then("{string} panels on the {string}")
	public void panels_on_the( final String numberOfPanels, final String section ) {
		AcceptanceNeoComLogger.info( "[THEN] {string} panels on the {string}" );
		switch (section) {
			case "HeaderSection":
				Assert.assertEquals( Integer.parseInt( numberOfPanels ), Ristretto.headerContentsCount( this.world.getSelectedPage() ) );
				break;
			case "DataSection":
				Assert.assertEquals( Integer.parseInt( numberOfPanels ), Ristretto.dataContentsCount( this.world.getSelectedPage() ) );
				break;
		}
	}

	@Then("stop on debug point")
	public void stop_on_debug_point() throws InterruptedException {
		Ristretto.waitForBackground( () -> {
			Ristretto.waitForCompletion( () -> {
				AcceptanceNeoComLogger.info( "Invalidate all the display and wait termination..." );
				Ristretto.updateDisplay();
			} );
		} );
		Thread.sleep( TimeUnit.SECONDS.toMillis( 10 ) );
		final int dummy = 0;
	}
}
