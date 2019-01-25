//  PROJECT:     Android.MVC (A.MVC)
//  AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
//  COPYRIGHT:   (c) 2013-2018 by Dimensinfin Industries, all rights reserved.
//  ENVIRONMENT: Android API16.
//  DESCRIPTION: Library that defines a generic Model View Controller core classes to be used
//               on Android projects. Defines the AndroidController factory and the AndroidController core methods to manage
//               a generic converter from a Graph Model to a hierarchical AndroidController model that finally will
//               be converted to a AndroidController list to be used on a BaseAdapter tied to a ListView.
package org.dimensinfin.android.mvc.part;

import android.app.Activity;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import lombok.Builder;
import org.dimensinfin.android.mvc.core.AbstractAndroidAndroidController;
import org.dimensinfin.android.mvc.core.AbstractRender;
import org.dimensinfin.android.mvc.core.AndroidController;
import org.dimensinfin.android.mvc.demo.R;
import org.dimensinfin.android.mvc.interfaces.IMenuActionTarget;
import org.dimensinfin.android.mvc.model.DemoHeaderTitle;

import java.util.GregorianCalendar;
@Builder
public class DemoHeaderTitleAndroidController extends AndroidController<DemoHeaderTitle> implements IMenuActionTarget {
	// - S T A T I C - S E C T I O N ..........................................................................
	private static final long serialVersionUID = -7103273035430243825L;

	// - F I E L D - S E C T I O N ............................................................................
	private int iconReference = R.drawable.defaulticonplaceholder;
private Activity _activity;
	// - C O N S T R U C T O R - S E C T I O N ................................................................
//	public DemoHeaderTitleAndroidController (final DemoHeaderTitle node) {
//		super(node);
//	}

	// - M E T H O D - S E C T I O N ..........................................................................
	public DemoHeaderTitle getCastedModel() {
		return (DemoHeaderTitle) this.getModel();
	}

	public int getIconReference() {
		return iconReference;
	}

	public DemoHeaderTitleAndroidController setIconReference(final int resourceIdentifier) {
		logger.info("-- [DemoHeaderTitleAndroidController.setIconReference]> setting icon ref: " + resourceIdentifier);
		iconReference = resourceIdentifier;
		return this;
	}

//	@Override
	public long getModelId() {
		return GregorianCalendar.getInstance().getTimeInMillis();
	}

	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer("DemoHeaderTitleAndroidController [");
		buffer.append("icon ref id: ").append(iconReference);
		buffer.append("]");
		return buffer.toString();
	}

//	@Override
	public AbstractRender selectRenderer() {
		return null;
//		return new DemoHeaderTitleRender(this, _activity);
	}

	@Override
	public boolean onContextItemSelected(final MenuItem item) {
		logger.info(">< [DemoHeaderTitleAndroidController.onContextItemSelected]");
		Toast.makeText(this.getActivity()
				, item.getTitle()
				, Toast.LENGTH_LONG).show();
		return true;
	}
public Activity getActivity(){
		return null;
}
	@Override
	public void onCreateContextMenu(final ContextMenu menu, final View v, final ContextMenu.ContextMenuInfo menuInfo) {
		logger.info(">> [DemoHeaderTitleAndroidController.onCreateContextMenu]");
		menu.setHeaderTitle("Context Menu");
		menu.add(0, v.getId(), 0, "Action 1");
		menu.add(0, v.getId(), 0, "Action 2");
		logger.info("<< [DemoHeaderTitleAndroidController.onCreateContextMenu]");
	}

//	public static class Builder<DemoHeaderTitle> extends AndroidController.Builder<Builder<DemoHeaderTitle>> {
//		//		public Builder<DemoHeaderTitle>(final DemoHeaderTitle model, final IPartFactory factory) {
////			super(model, factory);
////		}
//		@Override
//		public DemoHeaderTitleAndroidController build() {
//			return new DemoHeaderTitleAndroidController(this);
//		}
//
//		@Override
//		protected Builder self() {
//			return this;
//		}
//	}

//	private DemoHeaderTitleAndroidController(final Builder builder) {
//		super(builder);
//
//	}
}

//public abstract class Pizza {
//	public enum Topping {HAM, MUSHROOM, ONION, PEPPER, SAUSAGE}
//
//	final Set<Topping> toppings;
//
//	abstract static class Builder<T extends Builder<T>> {
//		EnumSet<Topping> toppings = EnumSet.noneOf(Topping.class);
//
//		public T addTopping(Topping topping) {
//			toppings.add(Objects.requireNonNull(topping));
//			return self();
//		}
//
//		abstract Pizza build();
//
//		// Subclasses must override this method to return "this"
//		protected abstract T self();
//	}
//
//	Pizza(Builder<?> builder) {
//		toppings = builder.toppings.clone(); // See Item 50
//	}
//}

//public class Calzone extends Pizza {
//	private final boolean sauceInside;
//
//	public static class Builder extends Pizza.Builder<Builder> {
//
//		@Override
//		public Calzone build() {
//			return new Calzone(this);
//		}
//
//		@Override
//		protected Builder self() {
//			return this;
//		}
//	}
//
//	private Calzone(Builder builder) {
//		super(builder);
//		sauceInside = builder.sauceInside;
//	}
//}

// - CLASS IMPLEMENTATION ...................................................................................
final class DemoHeaderTitleRender /*extends AbstractRender */ {
	// - S T A T I C - S E C T I O N ..........................................................................

	// - F I E L D - S E C T I O N ............................................................................
//	private ImageView nodeIcon = null;
	private TextView applicationName = null;
	private TextView applicationVersion = null;

	// - C O N S T R U C T O R - S E C T I O N ................................................................
	public DemoHeaderTitleRender(final AbstractAndroidAndroidController target, final Activity context) {
		super(target, context);
	}

	// - M E T H O D - S E C T I O N ..........................................................................
	@Override
	public DemoHeaderTitleAndroidController getPart() {
		return (DemoHeaderTitleAndroidController) super.getPart();
	}

	@Override
	public void initializeViews() {
//		super.initializeViews();
//		nodeIcon = (ImageView) _convertView.findViewById(R.id.nodeIcon);
		applicationName = (TextView) _convertView.findViewById(R.id.applicationName);
		applicationVersion = (TextView) _convertView.findViewById(R.id.applicationVersion);
	}

	@Override
	public void updateContent() {
//		super.updateContent();
//		nodeIcon.setImageResource(getPart().getIconReference());
		applicationName.setText(getPart().getCastedModel().getName());
		applicationName.setVisibility(View.VISIBLE);
		applicationVersion.setText(getPart().getCastedModel().getVersion());
		applicationVersion.setVisibility(View.VISIBLE);
	}

	@Override
	public int accessLayoutReference() {
		return R.layout.demoheadertitle4header;
	}
}

// - UNUSED CODE ............................................................................................
