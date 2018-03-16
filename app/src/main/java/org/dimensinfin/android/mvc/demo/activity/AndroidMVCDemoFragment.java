//  PROJECT:     Android.MVC (A.MVC)
//  AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
//  COPYRIGHT:   (c) 2013-2018 by Dimensinfin Industries, all rights reserved.
//  ENVIRONMENT: Android API16.
//  DESCRIPTION: Library that defines a generic Model View Controller core classes to be used
//               on Android projects. Defines the Part factory and the Part core methods to manage
//               a generic converter from a Graph Model to a hierarchycal Part model that finally will
//               be converted to a Part list to be used on a BaseAdapter tied to a ListView.
package org.dimensinfin.android.mvc.demo.activity;

import android.os.Bundle;

import org.dimensinfin.android.mvc.activity.AbstractPagerFragment;
import org.dimensinfin.android.mvc.core.PartFactory;
import org.dimensinfin.android.mvc.datasource.MVCDataSource;
import org.dimensinfin.android.mvc.demo.DemoAppConnector;
import org.dimensinfin.android.mvc.demo.R;
import org.dimensinfin.android.mvc.interfaces.IDataSource;
import org.dimensinfin.android.mvc.interfaces.IPart;
import org.dimensinfin.android.mvc.interfaces.IPartFactory;
import org.dimensinfin.android.mvc.model.DemoHeaderTitle;
import org.dimensinfin.android.mvc.part.DemoDetailSeparatorPart;
import org.dimensinfin.android.mvc.part.DemoHeaderTitlePart;
import org.dimensinfin.android.mvc.part.SeparatorPart;
import org.dimensinfin.core.constant.CoreConstants;
import org.dimensinfin.core.datasource.AbstractGenerator;
import org.dimensinfin.core.datasource.DataSourceLocator;
import org.dimensinfin.core.interfaces.ICollaboration;
import org.dimensinfin.core.model.Container;
import org.dimensinfin.core.model.RootNode;
import org.dimensinfin.core.model.Separator;

import java.util.ArrayList;
import java.util.List;

//- CLASS IMPLEMENTATION ...................................................................................
public class AndroidMVCDemoFragment extends AbstractPagerFragment {
	// - S T A T I C - S E C T I O N ..........................................................................

	// - C O N S T R U C T O R - S E C T I O N ................................................................

	// - M E T H O D - S E C T I O N ..........................................................................
	@Override
	public String getSubtitle() {
		if (getVariant() == AndroidMVCDemoActivity.EDemoVariants.DEMO_PLANTS.name())
			return getAppContext().getResources().getString(R.string.activity_subtitle_DemoFragment_Plants);
		if (getVariant() == AndroidMVCDemoActivity.EDemoVariants.DEMO_ANIMALS.name())
			return getAppContext().getResources().getString(R.string.activity_subtitle_DemoFragment_Animals);
		return "";
	}

	@Override
	public String getTitle() {
		return getAppContext().getResources().getString(R.string.activity_title_DemoFragment);
	}

	@Override
	public IPartFactory createFactory() {
		return new DemoPartFactory(this.getVariant());
	}

	/**
	 * This should generate the list of model instances that should be rendered on the Header. We only use it for the Animals page
	 * @return list of model data to be rendered on the Header of the page.
	 */
	@Override
	protected List<ICollaboration> registerHeaderSource() {
		if (getVariant() == AndroidMVCDemoActivity.EDemoVariants.DEMO_PLANTS.name()) {
			ArrayList<ICollaboration> plantsHeader = new ArrayList<ICollaboration>();
			plantsHeader.add(new DemoHeaderTitle(getAppContext().getResources().getString(R.string.appname),
					getAppContext().getResources().getString(R.string.appversion)));
			return plantsHeader;
		}
		if (getVariant() == AndroidMVCDemoActivity.EDemoVariants.DEMO_ANIMALS.name()) {
			ArrayList<ICollaboration> animalsHeader = new ArrayList<ICollaboration>();
			animalsHeader.add(new Container("Chordata"));
			animalsHeader.add(new Container("Animalario"));
			return animalsHeader;
		}
		return new ArrayList<ICollaboration>();
	}

	/**
	 * Because on this demo Fragment we use a single fragment class for all the code we should differentiate using the variant to
	 * construct the right <code>IDataSource</code> for each page. We have a page with Plants and a page with Animals that also
	 * has contents on the Header. Both <code>IDataSource</code> classes are also defined on this file as static classes.
	 * @return
	 */
	@Override
	protected IDataSource registerDataSource() {
		AbstractPagerFragment.logger.info(">> [AndroidMVCDemoFragment.registerDataSource]");
		IDataSource ds = null;
		try {
			if (getVariant() == AndroidMVCDemoActivity.EDemoVariants.DEMO_PLANTS.name())
				ds = new PlantDataSource(new DataSourceLocator()
						.addIdentifier(this.getVariant())
						.addIdentifier("DEMO"), getVariant(), getFactory(), getExtras());
			if (getVariant() == AndroidMVCDemoActivity.EDemoVariants.DEMO_ANIMALS.name())
				ds = new AnimalDataSource(new DataSourceLocator()
						.addIdentifier(this.getVariant())
						.addIdentifier("DEMO"), getVariant(), getFactory(), getExtras());
		} finally {
			AbstractPagerFragment.logger.info("<< [AndroidMVCDemoFragment.registerDataSource]");
		}
		return ds;
	}
}

// - CLASS IMPLEMENTATION ...................................................................................
final class DemoPartFactory extends PartFactory implements IPartFactory {
	// - S T A T I C - S E C T I O N ..........................................................................
	//	private static Logger logger = Logger.getLogger("AssetPartFactory");

	// - F I E L D - S E C T I O N ............................................................................

	// - C O N S T R U C T O R - S E C T I O N ................................................................
	public DemoPartFactory(final String variantSelected) {
		super(variantSelected);
	}

	// - M E T H O D - S E C T I O N ..........................................................................

	/**
	 * The method should create the matching part for the model received. We can use the variant to change at creation
	 * time the matching part or to replace parts when required.
	 */
	public IPart createPart(final ICollaboration node) {
		logger.info("-- [DemoPartFactory.createPart]> Node class: " + node.getClass().getName());
		if (node instanceof DemoHeaderTitle) {
			// These shows the selected Separator but with another rendering.
			IPart part = new DemoHeaderTitlePart((DemoHeaderTitle) node).setIconReference(R.drawable.arrowleft)
					.setRenderMode(getVariant())
					.setFactory(this);
			return part;
		}
		switch (AndroidMVCDemoActivity.EDemoVariants.valueOf(getVariant())) {
			case DEMO_SEPARATOR_CATALOG:
				if (node instanceof Separator) {
					// These special separators can configure an specific icon.
					IPart part = new SeparatorPart((Separator) node).setRenderMode(getVariant())
							.setFactory(this);
					return part;
				}
				break;
			case DEMO_SEPARATOR_DETAIL:
				if (node instanceof Separator) {
					// These shows the selected Separator but with another rendering.
					IPart part = new DemoDetailSeparatorPart((Separator) node).setIconReference(R.drawable.keyboard_close)
							.setRenderMode(getVariant())
							.setFactory(this);
					return part;
				}
				break;
		}

		// If no part is trapped then call the parent chain until one is found.
		return super.createPart(node);
	}
}

//- CLASS IMPLEMENTATION ...................................................................................
final class PlantDataSource extends MVCDataSource implements IDataSource {
	// - S T A T I C - S E C T I O N ..........................................................................

	// - F I E L D - S E C T I O N ............................................................................

	// - C O N S T R U C T O R - S E C T I O N ................................................................
	public PlantDataSource(DataSourceLocator locator, String variant, IPartFactory factory, Bundle extras) {
		super(locator,  variant,  factory,  extras);
	}

	// - M E T H O D - S E C T I O N ..........................................................................

	/**
	 * This adapter should generate the model for all the EVE characters associated to a set of api keys. The
	 * set is selected on the value of the login identifier that should already have stores that identifier
	 * along the api key on the Neocom database for retrieval. So from the unique login we get access to the set
	 * of keys and from there to the set of characters.
	 */
	public RootNode collaborate2Model() {
		AbstractGenerator.logger.info(">> [DemoSeparatorGenerator.collaborate2Model]");
		// Initialize the Adapter data structures.
		this.setDataModel(new RootNode());

		// Wait a delay of 6 seconds to allow to watch the counter.
		try {
			Thread.sleep(CoreConstants.ONESECOND * 6);
		} catch (InterruptedException ex) {
		}
		// Add manually each of the demo model nodes.
		Separator node = new Separator("RED-EMPTY").setType(Separator.ESeparatorType.EMPTY_SIGNAL);
		_dataModelRoot.addChild(node);
		node = new Separator("RED-Line").setType(Separator.ESeparatorType.LINE_RED);
		_dataModelRoot.addChild(node);
		node = new Separator("ORANGE-Line").setType(Separator.ESeparatorType.LINE_ORANGE);
		_dataModelRoot.addChild(node);
		node = new Separator("YELLOW-Line").setType(Separator.ESeparatorType.LINE_YELLOW);
		_dataModelRoot.addChild(node);
		node = new Separator("GREEN-Line").setType(Separator.ESeparatorType.LINE_GREEN);
		_dataModelRoot.addChild(node);

		final Separator expandable = new Container("-CONTAINER-").setType(Separator.ESeparatorType.LINE_WHITE);
		_dataModelRoot.addChild(expandable);

		AbstractGenerator.logger.info("<< [DemoSeparatorGenerator.collaborate2Model]");
		return _dataModelRoot;
	}
}

//- CLASS IMPLEMENTATION ...................................................................................
final class AnimalDataSource extends MVCDataSource implements IDataSource {
	// - S T A T I C - S E C T I O N ..........................................................................

	// - F I E L D - S E C T I O N ............................................................................

	// - C O N S T R U C T O R - S E C T I O N ................................................................
	public AnimalDataSource(DataSourceLocator locator, String variant, IPartFactory factory, Bundle extras) {
		super(locator,  variant,  factory,  extras);
	}

	// - M E T H O D - S E C T I O N ..........................................................................

	/**
	 * This adapter should generate the model for all the EVE characters associated to a set of api keys. The
	 * set is selected on the value of the login identifier that should already have stores that identifier
	 * along the api key on the Neocom database for retrieval. So from the unique login we get access to the set
	 * of keys and from there to the set of characters.
	 */
	public RootNode collaborate2Model() {
		AbstractGenerator.logger.info(">> [DemoSeparatorGenerator.collaborate2Model]");
		// Initialize the Adapter data structures.
		this.setDataModel(new RootNode());

		// Wait a delay of 6 seconds to allow to watch the counter.
		try {
			Thread.sleep(CoreConstants.ONESECOND * 6);
		} catch (InterruptedException ex) {
		}
		// Add manually each of the demo model nodes.
		Separator node = new Separator("RED-EMPTY").setType(Separator.ESeparatorType.EMPTY_SIGNAL);
		_dataModelRoot.addChild(node);
		node = new Separator("RED-Line").setType(Separator.ESeparatorType.LINE_RED);
		_dataModelRoot.addChild(node);
		node = new Separator("ORANGE-Line").setType(Separator.ESeparatorType.LINE_ORANGE);
		_dataModelRoot.addChild(node);
		node = new Separator("YELLOW-Line").setType(Separator.ESeparatorType.LINE_YELLOW);
		_dataModelRoot.addChild(node);
		node = new Separator("GREEN-Line").setType(Separator.ESeparatorType.LINE_GREEN);
		_dataModelRoot.addChild(node);

		final Separator expandable = new Container("-CONTAINER-").setType(Separator.ESeparatorType.LINE_WHITE);
		_dataModelRoot.addChild(expandable);

		AbstractGenerator.logger.info("<< [DemoSeparatorGenerator.collaborate2Model]");
		return _dataModelRoot;
	}
}

// - UNUSED CODE ............................................................................................
