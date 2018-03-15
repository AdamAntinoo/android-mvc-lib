//	PROJECT:        Android.MVC (A.MVC)
//	AUTHORS:        Adam Antinoo - adamantinoo.git@gmail.com
//	COPYRIGHT:      (c) 2013-2017 by Dimensinfin Industries, all rights reserved.
//	ENVIRONMENT:		Android API22.
//	DESCRIPTION:		Library that defines a generic Model View Controller core classes to be used
//									on Android projects. Defines the Part factory and the Part core methods to manage
//									a generic data graph into a Part hierarchy and finally on the Android View to be
//                  used on ListViews.
package org.dimensinfin.android.mvc.demo.activity;

import org.dimensinfin.android.mvc.activity.AbstractPagerFragment;
import org.dimensinfin.android.mvc.core.PartFactory;
import org.dimensinfin.android.mvc.demo.DemoAppConnector;
import org.dimensinfin.android.mvc.demo.R;
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
import org.dimensinfin.core.interfaces.IModelGenerator;
import org.dimensinfin.core.model.Container;
import org.dimensinfin.core.model.RootNode;
import org.dimensinfin.core.model.Separator;

//- CLASS IMPLEMENTATION ...................................................................................
public class AndroidMVCDemoFragment extends AbstractPagerFragment {
	// - S T A T I C - S E C T I O N ..........................................................................

	// - C O N S T R U C T O R - S E C T I O N ................................................................

	// - M E T H O D - S E C T I O N ..........................................................................
	@Override
	public void createFactory () {
		this.setFactory(new DemoPartFactory(this.getVariant()));
	}

	@Override
	public String getSubtitle () {
		return "";
	}

	@Override
	public String getTitle () {
		return DemoAppConnector.getSingleton().getResourceString(R.string.activity_title_AndroidMVCDemoActivity);
	}

	/**
	 * This is the method that any new fragment should override to implement the right DataSource association
	 * that corresponds to this activity contents. The new implementation divides the creation into two steps.
	 * One creates the custom model generator that will be used on Android and on the Web platform. With this
	 * model generator we then instantiate the DataSource that is only used on the Android platform because the
	 * Web uses javascript to manage the model and the MVC presentation layer.
	 */
	@Override
	protected void registerDataSource () {
		AbstractPagerFragment.logger.info(">> [AndroidMVCDemoFragment.registerDataSource]");
		// Create a unique identifier to locate this Model hierarchy and their matching DataSource.
		DataSourceLocator locator = new DataSourceLocator().addIdentifier(this.getVariant());
		// Create a new Model Generator and register it onto the Fragment to use on request to generate the root model.
//		setGenerator(new DemoSeparatorGenerator(locator, this.getVariant()));
		AbstractPagerFragment.logger.info("<< [AndroidMVCDemoFragment.registerDataSource]");
	}

	/**
	 * This methods is used to define the parts that go inside the header section of the page. On this activity
	 * there is no content on the head section.
	 */
	@Override
	protected void setHeaderContents () {
		addHeaderModel(new DemoHeaderTitle(DemoAppConnector.getSingleton().getResourceString(R.string.appname),
				DemoAppConnector.getSingleton().getResourceString(R.string.appversion)));
	}
}

// - CLASS IMPLEMENTATION ...................................................................................
final class DemoPartFactory extends PartFactory implements IPartFactory {
	// - S T A T I C - S E C T I O N ..........................................................................
	//	private static Logger logger = Logger.getLogger("AssetPartFactory");

	// - F I E L D - S E C T I O N ............................................................................

	// - C O N S T R U C T O R - S E C T I O N ................................................................
	public DemoPartFactory (final String variantSelected) {
		super(variantSelected);
	}

	// - M E T H O D - S E C T I O N ..........................................................................

	/**
	 * The method should create the matching part for the model received. We can use the variant to change at creation
	 * time the matching part or to replace parts when required.
	 */
	public IPart createPart (final ICollaboration node) {
		logger.info("-- [DemoPartFactory.createPart]> Node class: " + node.getClass().getName());
		if ( node instanceof DemoHeaderTitle ) {
			// These shows the selected Separator but with another rendering.
			IPart part = new DemoHeaderTitlePart((DemoHeaderTitle) node).setIconReference(R.drawable.arrowleft)
			                                                            .setRenderMode(getVariant())
			                                                            .setFactory(this);
			return part;
		}
		switch (AndroidMVCDemoActivity.EDemoVariants.valueOf(getVariant())) {
			case DEMO_SEPARATOR_CATALOG:
				if ( node instanceof Separator ) {
					// These special separators can configure an specific icon.
					IPart part = new SeparatorPart((Separator) node).setRenderMode(getVariant())
					                                                .setFactory(this);
					return part;
				}
				break;
			case DEMO_SEPARATOR_DETAIL:
				if ( node instanceof Separator ) {
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
final class DemoSeparatorGenerator extends AbstractGenerator implements IModelGenerator {
	// - S T A T I C - S E C T I O N ..........................................................................

	// - F I E L D - S E C T I O N ............................................................................

	// - C O N S T R U C T O R - S E C T I O N ................................................................
	public DemoSeparatorGenerator (final DataSourceLocator locator, final String variant) {
		super(locator, variant);
	}

	// - M E T H O D - S E C T I O N ..........................................................................

	/**
	 * This adapter should generate the model for all the EVE characters associated to a set of api keys. The
	 * set is selected on the value of the login identifier that should already have stores that identifier
	 * along the api key on the Neocom database for retrieval. So from the unique login we get access to the set
	 * of keys and from there to the set of characters.
	 */
	public RootNode collaborate2Model () {
		AbstractGenerator.logger.info(">> [DemoSeparatorGenerator.collaborate2Model]");
		// Initialize the Adapter data structures.
		this.setDataModel(new RootNode());

		// Wait a delay of 6 seconds to allow to watch the counter.
		try {
			Thread.sleep(CoreConstants.ONESECOND*6);
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
