package org.dimensinfin.android.mvc.fonts;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatTextView;

public class ProximaNovaBoldTextView extends AppCompatTextView {
	private static final String PROXIMA_BOLD_FONTPATH = "fonts/Proxima Nova Bold.ttf";
	protected Typeface typefaceName = null;

	// - C O N S T R U C T O R S
	public ProximaNovaBoldTextView( final Context context ) {
		super( context );
		this.init();
	}

	public ProximaNovaBoldTextView( final Context context, final AttributeSet attrs ) {
		super( context, attrs );
		this.init();

	}

	public ProximaNovaBoldTextView( final Context context, final AttributeSet attrs, final int defStyle ) {
		super( context, attrs, defStyle );
		this.init();
	}

	private void init() {
		if (!this.isInEditMode()) {
			this.typefaceName = Typeface.createFromAsset( this.getContext().getAssets(), PROXIMA_BOLD_FONTPATH );
			this.setTypeface( this.typefaceName );
		}
	}
}
