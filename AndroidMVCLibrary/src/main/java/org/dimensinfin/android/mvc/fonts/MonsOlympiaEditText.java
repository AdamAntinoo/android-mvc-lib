//	PROJECT:        EVEIndustrialist (EVEI)
//	AUTHORS:        Adam Antinoo - adamantinoo.git@gmail.com
//	COPYRIGHT:      (c) 2013-2014 by Dimensinfin Industries, all rights reserved.
//	ENVIRONMENT:		Android API11.
//	DESCRIPTION:		Application helper for Eve Online Industrialists. Will help on Industry and Manufacture.

package org.dimensinfin.eveonline.neocom.fonts;

// - IMPORT SECTION .........................................................................................

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;

// - CLASS IMPLEMENTATION ...................................................................................
public class MonsOlympiaEditText extends AppCompatEditText {
	// - S T A T I C - S E C T I O N ..........................................................................
	protected static Typeface typefaceName = null;

	// - F I E L D - S E C T I O N ............................................................................

	//- C O N S T R U C T O R - S E C T I O N ................................................................
	public MonsOlympiaEditText (final Context context) {
		super(context);
		this.init(null);
	}

	public MonsOlympiaEditText (final Context context, final AttributeSet attrs) {
		super(context, attrs);
		this.init(attrs);

	}

	public MonsOlympiaEditText (final Context context, final AttributeSet attrs, final int defStyle) {
		super(context, attrs, defStyle);
		this.init(attrs);
	}

	// - M E T H O D - S E C T I O N ..........................................................................
	private void init(final AttributeSet attrs) {
		if (!this.isInEditMode()) {
			MonsOlympiaEditText.typefaceName = Typeface.createFromAsset(this.getContext().getAssets(), "fonts/Mons Olympia.otf");
			this.setTypeface(MonsOlympiaEditText.typefaceName);
		}
	}
}

// - UNUSED CODE ............................................................................................
