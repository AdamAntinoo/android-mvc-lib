package org.dimensinfin.mvc.demo.activity;

public enum PageDefinitions {
	MVCDEMOLIST_ITEMS( "Simple List of Items" ),
	BYCOLOUR( "Items by Colour" ),
	BYSIDES( "Items by Number of Sides" ),
	PAGE_SELECTION( "Test Page Selection" );

	String pageTitle;

	PageDefinitions( final String pageTitle ) {
		this.pageTitle = pageTitle;
	}

	public String getPageTitle() {
		return this.pageTitle;
	}
}
