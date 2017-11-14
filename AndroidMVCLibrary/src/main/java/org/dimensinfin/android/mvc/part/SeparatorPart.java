//	PROJECT:        NeoCom.Android (NEOC.A)
//	AUTHORS:        Adam Antinoo - adamantinoo.git@gmail.com
//	COPYRIGHT:      (c) 2013-2016 by Dimensinfin Industries, all rights reserved.
//	ENVIRONMENT:		Android API16.
//	DESCRIPTION:		Application to get access to CCP api information and help manage industrial activities
//									for characters and corporations at Eve Online. The set is composed of some projects
//									with implementation for Android and for an AngularJS web interface based on REST
//									services on Sprint Boot Cloud.
package org.dimensinfin.android.mvc.part;

import java.text.DecimalFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.Vector;

import org.dimensinfin.android.model.Separator;
import org.dimensinfin.android.mvc.R;
import org.dimensinfin.android.mvc.core.AbstractAndroidPart;
import org.dimensinfin.android.mvc.core.AbstractRender;
import org.dimensinfin.android.mvc.interfaces.INamedPart;
import org.dimensinfin.android.mvc.interfaces.IPart;
import org.dimensinfin.android.mvc.render.SeparatorRender;

import android.util.Log;

// - CLASS IMPLEMENTATION ...................................................................................
public class SeparatorPart extends AbstractAndroidPart {
	// - S T A T I C - S E C T I O N ..........................................................................
	private static final long				serialVersionUID	= -7108273035430243825L;
	protected static DecimalFormat	qtyFormatter			= new DecimalFormat("###,##0");

	// - F I E L D - S E C T I O N ............................................................................
	private int											priority					= 10;
	private int											iconReference			= R.drawable.defaultitemicon;
	private final String						renderModeName		= "-DEFAULT-RENDER-MODE-";

	// - C O N S T R U C T O R - S E C T I O N ................................................................
	public SeparatorPart(final Separator node) {
		super(node);
		this.getCastedModel().setExpanded(true);
	}

	// - M E T H O D - S E C T I O N ..........................................................................
	public Separator getCastedModel() {
		return (Separator) this.getModel();
	}

	public int getChildrenCount() {
		return this.getChildren().size();
	}

	public int getIconReference() {
		return iconReference;
	}

	@Override
	public long getModelID() {
		return GregorianCalendar.getInstance().getTimeInMillis();
	}

	public String getTitle() {
		return this.getCastedModel().getTitle();
	}

	public String getTransformedCounter() {
		return SeparatorPart.qtyFormatter.format(this.getCastedModel().getContents().size());
	}

	/**
	 * The default actions inside this method usually are the sorting of the children nodes. Sort the container
	 * contents by name.
	 */
	@Override
	public Vector<IPart> runPolicies(final Vector<IPart> targets) {
		// Order the contents by alphabetical name.
		Comparator<IPart> comparator = new Comparator<IPart>() {
			public int compare(final IPart left, final IPart right) {
				String leftField = null;
				String rightField = null;
				if (left instanceof INamedPart) {
					leftField = ((INamedPart) left).getName();
				}
				if (right instanceof INamedPart) {
					rightField = ((INamedPart) right).getName();
				}

				if (null == leftField) return 1;
				if (null == rightField) return -1;
				if ("" == leftField) return 1;
				if ("" == rightField) return -1;
				return leftField.compareTo(rightField);
			}
		};
		Collections.sort(targets, comparator);
		return targets;
	}

	public SeparatorPart setIconReference(final int ref) {
		Log.i("REMOVE", "-- GroupPart.setIconReference - " + this.toString() + " change value to: " + ref);
		iconReference = ref;
		return this;
	}

	public AbstractAndroidPart setPriority(final int pri) {
		priority = pri;
		return this;
	}

	//	@Override
	//	public IPart setRenderMode(int renderMode) {
	//		if (null != renderMode) {
	//			renderModeName = renderMode;
	//		}
	//		return this;
	//	}

	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer("SeparatorPart [");
		buffer.append(this.getTitle()).append(" ");
		buffer.append("pri: ").append(priority).append(" ");
		buffer.append("type: ").append(this.getCastedModel().getType().name()).append(" ");
		buffer.append("chCount: ").append(this.getChildren().size()).append(" ");
		buffer.append("]");
		return buffer.toString();
	}

	//	@Override
	//	protected AbstractHolder selectHolder() {
	//		return this.selectRenderer();
	//	}

	@Override
	protected AbstractRender selectRenderer() {
		return new SeparatorRender(this, _activity);
	}

	//	private String getRenderModeName() {
	//		return renderModeName;
	//	}
}

// - UNUSED CODE ............................................................................................
