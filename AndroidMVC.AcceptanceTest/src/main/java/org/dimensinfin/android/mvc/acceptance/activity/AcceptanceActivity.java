package org.dimensinfin.android.mvc.activity;

import android.os.Bundle;

public class AcceptanceActivity extends MVCMultiPageActivity{
	public enum TestIdentifiers{
		MVC01_01;
	}
	@Override
	protected void onCreate( final Bundle savedInstanceState ) {
		super.onCreate(savedInstanceState);
		this.addPage(new MVC01Fragment().setVariant(TestIdentifiers.MVC01_01.name()));
	}
}
