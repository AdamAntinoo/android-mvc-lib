package org.dimensinfin.mvc.demo.acceptance.support;

import java.util.Arrays;

import org.apache.commons.lang3.NotImplementedException;

public enum ActionBarTypes {
	TITLED_ACTIONBAR( "TitledActionBar" ),
	APP_VERSION( "AppVersion" );

	String actionBarLabel;

	ActionBarTypes( final String actionBarLabel ) {
		this.actionBarLabel = actionBarLabel;
	}

	public static ActionBarTypes from( final String code ) {
		return Arrays.stream( ActionBarTypes.values() )
				       .filter( requestType -> requestType.getActionBarLabel().equals( code ) )
				       .findFirst()
				       .orElseThrow( () -> new NotImplementedException( "Request type not implemented." ) );
	}

	public String getActionBarLabel() {
		return this.actionBarLabel;
	}
}
