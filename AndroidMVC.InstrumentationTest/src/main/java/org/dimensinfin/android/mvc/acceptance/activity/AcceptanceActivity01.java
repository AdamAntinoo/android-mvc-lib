package org.dimensinfin.android.mvc.acceptance.activity;

import android.os.Bundle;

import org.dimensinfin.android.mvc.acceptance.TestIdentifiers;
import org.dimensinfin.android.mvc.activity.MVCMultiPageActivity;

public class AcceptanceActivity01 extends MVCMultiPageActivity {
	private boolean onResumeReached = false;

	public boolean isOnResumeReached() {
		return onResumeReached;
	}

	@Override
	protected void onCreate( final Bundle savedInstanceState ) {
		super.onCreate(savedInstanceState);
		this.addPage(new MVC01Fragment().setVariant(TestIdentifiers.MVC01_01.name()));
		this.addPage(new MVC01Fragment().setVariant(TestIdentifiers.MVC01_02.name()));
	}

	@Override
	protected void onResume() {
		super.onResume();
		this.onResumeReached = true;
	}
}
