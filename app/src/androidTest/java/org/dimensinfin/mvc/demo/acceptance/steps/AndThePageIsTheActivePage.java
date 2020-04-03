package org.dimensinfin.mvc.demo.acceptance.steps;

import org.junit.Assert;

import org.dimensinfin.mvc.demo.acceptance.support.core.MVCWorld;

import cucumber.api.java.en.When;

public class AndThePageIsTheActivePage extends SupportStepParent {
	public AndThePageIsTheActivePage( final MVCWorld world ) {
		super( world );
	}

	@When("the page {string} is the active page")
	public void the_page_is_the_active_page( final String pageNumber ) {
		Assert.assertEquals( Integer.parseInt( pageNumber ), this.world.getSelectedPage().intValue() );
	}
}
