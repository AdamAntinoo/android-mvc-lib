package org.dimensinfin.mvc.demo.acceptance.support;

import java.util.Arrays;

import org.apache.commons.lang3.NotImplementedException;

public enum PanelTypes {
	PAGE_BUTTON_PANEL( "PageButton" ),
	APPLICATION_HEADER_TITLE( "ApplicationHeaderTitle" ),
	TITLE_LABEL( "TitleLabel" ),
	DEMO_LABEL( "DemoLabel" );

	String panelLabel;

	PanelTypes( final String panelLabel ) {
		this.panelLabel = panelLabel;
	}

	public static PanelTypes from( final String code ) {
		return Arrays.stream( PanelTypes.values() )
				       .filter( requestType -> requestType.getPanelLabel().equals( code ) )
				       .findFirst()
				       .orElseThrow( () -> new NotImplementedException( "Request type not implemented." ) );
	}

	public String getPanelLabel() {
		return this.panelLabel;
	}
}
