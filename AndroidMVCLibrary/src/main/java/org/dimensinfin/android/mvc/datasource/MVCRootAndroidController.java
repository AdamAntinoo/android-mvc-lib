//  PROJECT:     Android.MVC (A.MVC)
//  AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
//  COPYRIGHT:   (c) 2013-2018 by Dimensinfin Industries, all rights reserved.
//  ENVIRONMENT: Android API16.
//  DESCRIPTION: Library that defines a generic Model View Controller core classes to be used
//               on Android projects. Defines the AndroidController factory and the AndroidController core methods to manage
//               a generic converter from a Graph Model to a hierarchical AndroidController model that finally will
//               be converted to a AndroidController list to be used on a BaseAdapter tied to a ListView.
package org.dimensinfin.android.mvc.datasource;

import android.content.Context;
import org.dimensinfin.android.mvc.controller.AAndroidController;
import org.dimensinfin.android.mvc.interfaces.IControllerFactory;
import org.dimensinfin.android.mvc.interfaces.IDataSource;
import org.dimensinfin.android.mvc.interfaces.IRender;
import org.dimensinfin.android.mvc.interfaces.IRootPart;
import org.dimensinfin.android.mvc.render.AbstractRender;
import org.dimensinfin.core.model.RootNode;

/**
 * @author Adam Antinoo
 */

public class MVCRootAndroidController extends AAndroidController<RootNode> implements IRootPart {
	// - F I E L D - S E C T I O N
	private IDataSource ds;

	// - C O N S T R U C T O R - S E C T I O N

	/**
	 * This constructor connect the root part to the DS and then top the other initialization elements that define the DS
	 * functionality but at a time that is not the creation time. Then the Factory and other data structures become
	 * available to the part hierarchy without affecting any other AndroidController implementation.
	 */
	protected MVCRootAndroidController(final MVCRootAndroidController.Builder builder) {
		super(builder);
		this.ds = builder.dataSource;
	}

	// - M E T H O D - S E C T I O N
	public IControllerFactory getPartFactory() {
		return this.ds.getControllerFactory();
	}

	// - O V E R R I D E N
//	@Override
//	public boolean isRoot() {
//		return true;
//	}

	@Override
	public IRender buildRender(final Context context) {
		return null;
	}

	@Override
	public long getModelId() {
		return 0;
	}

	// - B U I L D E R
	public static class Builder extends AAndroidController.Builder<RootNode> {
		private IDataSource dataSource;

		public Builder(final RootNode model, final IControllerFactory factory) {
			super(model, factory);
		}

		public Builder dataSource(final IDataSource dataSource) {
			this.dataSource = dataSource;
			return this;
		}

		public MVCRootAndroidController build() {
			return new MVCRootAndroidController(this);
		}
	}
}

final class RootNodeRender extends AbstractRender<RootNode> {

	protected RootNodeRender(final Builder<RootNode> builder) {
		super(builder);
	}

	@Override
	protected void initializeViews() {

	}

	@Override
	protected int accessLayoutReference() {
		return 0;
	}

	@Override
	public void updateContent() {

	}
}