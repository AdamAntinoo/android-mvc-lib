package org.dimensinfin.mvc.demo.acceptance.support.core;

import android.app.Activity;

public class World {
	private Activity activeActivity;
	private Integer selectedPage = 0;

	public Activity getActiveActivity() {
		return this.activeActivity;
	}

	public World setActiveActivity( final Activity activeActivity ) {
		this.activeActivity = activeActivity;
		return this;
	}

	public Integer getSelectedPage() {
		return this.selectedPage;
	}

	public World setSelectedPage( final Integer selectedPage ) {
		this.selectedPage = selectedPage;
		return this;
	}
}
