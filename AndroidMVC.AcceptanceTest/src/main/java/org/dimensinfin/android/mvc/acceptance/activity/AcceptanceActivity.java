package org.dimensinfin.android.mvc.acceptance.activity;

import android.os.Bundle;

import org.dimensinfin.android.mvc.acceptance.TestIdentifiers;
import org.dimensinfin.android.mvc.activity.MVCMultiPageActivity;

public class AcceptanceActivity extends MVCMultiPageActivity {
	@Override
	protected void onCreate( final Bundle savedInstanceState ) {
		super.onCreate(savedInstanceState);
		this.addPage(new MVC01Fragment().setVariant(TestIdentifiers.MVC02_01.name()));
	}
}
