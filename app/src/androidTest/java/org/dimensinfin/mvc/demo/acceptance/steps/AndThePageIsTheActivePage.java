package org.dimensinfin.mvc.demo.acceptance.steps;

import org.junit.Assert;

import org.dimensinfin.logging.LogWrapper;
import org.dimensinfin.mvc.demo.acceptance.support.core.MVCWorld;
import org.dimensinfin.mvc.demo.acceptance.support.ristretto.Ristretto;

import cucumber.api.java.en.When;

public class AndThePageIsTheActivePage extends SupportStepParent {
	public AndThePageIsTheActivePage( final MVCWorld world ) {
		super( world );
	}

	/**
	 * Change the current active page to the requested active page. First check that the destination page is between the page number bounds.
	 * Page numbering starts with 0 to ge the first page.
	 *
	 * @param destinationPageNumber destination page number
	 */
	@When("the page {string} is the active page")
	public void the_page_is_the_active_page( final String destinationPageNumber ) {
		LogWrapper.info( "[WHEN] the page {string} is the active page" );
		final int pageNumber = Integer.parseInt( destinationPageNumber );
		final int numberOfPages = Ristretto.activityPageCount();
		Assert.assertTrue( pageNumber < numberOfPages );
		Assert.assertEquals( pageNumber, Ristretto.setActivePage( pageNumber ) );
	}
}
