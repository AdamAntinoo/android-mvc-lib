package org.dimensinfin.mvc.demo.fonts;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatTextView;

public class ProtomoleculeBoldTextView extends AppCompatTextView {
	private static final String ELEMENTAL_FONTPATH = "fonts/Protomolecule-Bold.ttf";
	protected Typeface typefaceName = null;

	// - C O N S T R U C T O R S
	public ProtomoleculeBoldTextView( final Context context ) {
		super( context );
		this.init( null );
	}

	public ProtomoleculeBoldTextView( final Context context, final AttributeSet attrs ) {
		super( context, attrs );
		this.init( attrs );

	}

	public ProtomoleculeBoldTextView( final Context context, final AttributeSet attrs, final int defStyle ) {
		super( context, attrs, defStyle );
		this.init( attrs );
	}

	private void init( final AttributeSet attrs ) {
		if (!this.isInEditMode()) {
			this.typefaceName = Typeface.createFromAsset( this.getContext().getAssets(), ELEMENTAL_FONTPATH );
			this.setTypeface( this.typefaceName );
		}
	}
}