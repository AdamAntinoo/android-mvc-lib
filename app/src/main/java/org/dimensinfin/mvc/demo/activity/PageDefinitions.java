package org.dimensinfin.mvc.demo.activity;

public enum PageDefinitions {
	MVCDEMOLIST_ITEMS("Simple List of Items");

	String pageTitle;
	PageDefinitions ( final String pageTitle){
		this.pageTitle = pageTitle;
	}

	public String getPageTitle() {
		return this.pageTitle;
	}
}
