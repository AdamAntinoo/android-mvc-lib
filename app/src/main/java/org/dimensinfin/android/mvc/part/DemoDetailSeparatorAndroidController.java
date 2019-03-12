package org.dimensinfin.android.mvc.part;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import org.dimensinfin.android.mvc.controller.AAndroidController;
import org.dimensinfin.android.mvc.demo.R;
import org.dimensinfin.android.mvc.interfaces.IControllerFactory;
import org.dimensinfin.android.mvc.interfaces.IRender;
import org.dimensinfin.android.mvc.model.Separator;
import org.dimensinfin.android.mvc.render.AbstractRender;

import java.util.GregorianCalendar;

// - CLASS IMPLEMENTATION
public class DemoDetailSeparatorAndroidController extends AAndroidController<Separator> {
	private static final long serialVersionUID = -7103273035430243825L;

	// - F I E L D - S E C T I O N
	private int iconReference = R.drawable.defaulticonplaceholder;

	// - C O N S T R U C T O R - S E C T I O N
	public DemoDetailSeparatorAndroidController(final Separator model, final IControllerFactory factory) {
		super(model, factory);
	}

	// - M E T H O D - S E C T I O N
	public Separator getCastedModel() {
		return (Separator) this.getModel();
	}

	public int getIconReference() {
		return iconReference;
	}

	public DemoDetailSeparatorAndroidController setIconReference(final int resourceIdentifier) {
		logger.info("-- [DemoDetailSeparatorAndroidController.setIconReference]> setting icon ref: " + resourceIdentifier);
		iconReference = resourceIdentifier;
		return this;
	}

	@Override
	public long getModelId() {
		return GregorianCalendar.getInstance().getTimeInMillis();
	}

	@Override
	public IRender buildRender(final Context context) {
		return new DemoDetailSeparatorRender(this, context);
	}

	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer("DemoDetailSeparatorAndroidController [");
		buffer.append("icon ref id: ").append(iconReference);
		buffer.append("]");
		return buffer.toString();
	}

	@Override
	public int compareTo(@NonNull final Object o) {
		if (o instanceof DemoDetailSeparatorAndroidController) {
			final DemoDetailSeparatorAndroidController target = (DemoDetailSeparatorAndroidController) o;
			return this.getModel().compareTo(target.getModel());
		} else return -1;
	}
}

// - CLASS IMPLEMENTATION ...................................................................................
final class DemoDetailSeparatorRender extends AbstractRender<Separator> {
	// - S T A T I C - S E C T I O N ..........................................................................

	// - F I E L D - S E C T I O N ............................................................................
	private ImageView nodeIcon = null;
	private TextView title = null;

	// - C O N S T R U C T O R - S E C T I O N ................................................................
	public DemoDetailSeparatorRender(final AAndroidController<Separator> target, final Context context) {
		super(target, context);
	}

	// - M E T H O D - S E C T I O N ..........................................................................
	@Override
	public DemoDetailSeparatorAndroidController getController() {
		return (DemoDetailSeparatorAndroidController) super.getController();
	}

	@Override
	public void initializeViews() {
//		super.initializeViews();
		nodeIcon = (ImageView) this.getView().findViewById(R.id.nodeIcon);
		title = (TextView) this.getView().findViewById(R.id.title);
	}

	@Override
	public void updateContent() {
//		super.updateContent();
		nodeIcon.setImageResource(this.getController().getIconReference());
		title.setText(this.getController().getCastedModel().getTitle());
		title.setVisibility(View.GONE);
	}

	@Override
	public int accessLayoutReference() {
		return R.layout.demodetailseparator4activity;
	}
}

// - UNUSED CODE ............................................................................................
