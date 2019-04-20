//  PROJECT:     Android.MVC (A.MVC)
//  AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
//  COPYRIGHT:   (c) 2013-2019 by Dimensinfin Industries, all rights reserved.
//  ENVIRONMENT: Android API16.
//  DESCRIPTION: Library that defines a generic Model View Controller core classes to be used
//               on Android projects. Defines the AndroidController factory and the AndroidController core methods to manage
//               a generic converter from a Graph Model to a hierarchical AndroidController model that finally will
//               be converted to a AndroidController list to be used on a BaseAdapter tied to a ListView.
package org.dimensinfin.android.mvc.exception;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import org.dimensinfin.android.mvc.R;

/**
 * @author Adam Antinoo
 */

public class ForceCloseActivity extends Activity {
	// - F I E L D - S E C T I O N
	private TextView error;

	// - C O N S T R U C T O R - S E C T I O
	public ForceCloseActivity() {
		super();
	}

	// - M E T H O D - S E C T I O N
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Thread.setDefaultUncaughtExceptionHandler(new MVCExceptionHandler(this));
		setContentView(R.layout.activity_forceclose);

		error = (TextView) findViewById(R.id.error);

		error.setText(getIntent().getStringExtra("error"));
	}
}
