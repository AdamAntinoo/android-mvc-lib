package org.dimensinfin.android.mvc.acceptance.support;

public enum ActionBarTypes {
	APP_VERSION( "AppVersion" );

	String actionBarLabel;

	ActionBarTypes( final String actionBarLabel ) {
		this.actionBarLabel = actionBarLabel;
	}

	public String getActionBarLabel() {
		return this.actionBarLabel;
	}
}
