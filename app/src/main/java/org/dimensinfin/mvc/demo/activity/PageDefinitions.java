package org.dimensinfin.mvc.demo.activity;

public enum PageDefinitions {
	MVCDEMOLIST_ITEMS( "Simple List of Items" ),
	BYGROUP( "Items classified by Group" ),
	BYCATEGORY( "Items classified by Category" ),
	PAGE_SELECTION( "Select the Page to Test");

	String pageTitle;

	PageDefinitions( final String pageTitle ) {
		this.pageTitle = pageTitle;
	}

	public String getPageTitle() {
		return this.pageTitle;
	}
}
