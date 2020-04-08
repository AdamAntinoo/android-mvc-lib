package org.dimensinfin.mvc.demo.activity;

import android.os.Bundle;

import org.dimensinfin.android.mvc.activity.MVCMultiPageActivity;

public class DemoMultiPageActivity extends MVCMultiPageActivity {
	@Override
	protected void onCreate( final Bundle savedInstanceState ) {
		super.onCreate( savedInstanceState );
		this.addPage( new GroupFragment() );
	}
}
