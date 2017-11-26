//	PROJECT:        NeoCom.MVC (NEOC.MVC)
//	AUTHORS:        Adam Antinoo - adamantinoo.git@gmail.com
//	COPYRIGHT:      (c) 2013-2017 by Dimensinfin Industries, all rights reserved.
//	ENVIRONMENT:		Android API16.
//	DESCRIPTION:		Library that defines a generic Model View Controller core classes to be used
//									on Android projects. Defines the Part factory and the Part core methods to manage
//									the extended GEF model into the Android View to be used on ListViews.
package org.dimensinfin.android.mvc.fonts;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

// - CLASS IMPLEMENTATION ...................................................................................
public class MonsOlympiaBoldEditText extends EditText {
	// - S T A T I C - S E C T I O N ..........................................................................
	protected static Typeface typefaceName = null;

	// - F I E L D - S E C T I O N ............................................................................

	//- C O N S T R U C T O R - S E C T I O N ................................................................
	public MonsOlympiaBoldEditText (final Context context) {
		super(context);
		this.init(null);
	}

	public MonsOlympiaBoldEditText (final Context context, final AttributeSet attrs) {
		super(context, attrs);
		this.init(attrs);

	}

	public MonsOlympiaBoldEditText (final Context context, final AttributeSet attrs, final int defStyle) {
		super(context, attrs, defStyle);
		this.init(attrs);
	}

	// - M E T H O D - S E C T I O N ..........................................................................
	private void init(final AttributeSet attrs) {
		if (!this.isInEditMode()) {
			MonsOlympiaBoldEditText.typefaceName = Typeface.createFromAsset(this.getContext().getAssets(),
					"fonts/Mons Olympia Bold.otf");
			this.setTypeface(MonsOlympiaBoldEditText.typefaceName);
		}
	}
}

// - UNUSED CODE ............................................................................................
