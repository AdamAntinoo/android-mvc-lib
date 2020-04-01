package org.dimensinfin.android.mvc.domain;

import org.dimensinfin.android.mvc.R;
import org.dimensinfin.android.mvc.interfaces.IIconReference;

/**
 * This class manages the reference of Android objects that should be referenced as icon resources from the Application. This is created to comply
 * with the <b>IIconInterface</b> that can describe other icon resources such as external URL references.
 *
 * @author Adam Antinoo
 * @since 3.0.0
 */
public class AndroidIconResource implements IIconReference {
	private int resourceId = R.drawable.defaulticonplaceholder;

	public AndroidIconResource() { }

	public AndroidIconResource( final int resourceIdentifier ) {
		this();
		this.resourceId = resourceIdentifier;
	}

	public int getIdentifier() {
		return this.resourceId;
	}
}