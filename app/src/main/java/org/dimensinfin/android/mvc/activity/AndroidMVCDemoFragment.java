//	PROJECT:        NeoCom.Android (NEOC.A)
//	AUTHORS:        Adam Antinoo - adamantinoo.git@gmail.com
//	COPYRIGHT:      (c) 2013-2017 by Dimensinfin Industries, all rights reserved.
//	ENVIRONMENT:		Android API16.
//	DESCRIPTION:		Application to get access to CCP api information and help manage industrial activities
//									for characters and corporations at Eve Online. The set is composed of some projects
//									with implementation for Android and for an AngularJS web interface based on REST
//									services on Sprint Boot Cloud.
package org.dimensinfin.android.mvc.activity;

import org.dimensinfin.android.datasource.AbstractGenerator;
import org.dimensinfin.android.datasource.DataSourceLocator;
import org.dimensinfin.android.interfaces.IModelGenerator;
import org.dimensinfin.android.model.Separator;
import org.dimensinfin.android.mvc.AndroidMVCAppSingleton;
import org.dimensinfin.android.mvc.R;
import org.dimensinfin.android.mvc.core.PartFactory;
import org.dimensinfin.android.mvc.interfaces.IPart;
import org.dimensinfin.android.mvc.interfaces.IPartFactory;
import org.dimensinfin.core.model.AbstractComplexNode;
import org.dimensinfin.core.model.RootNode;

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
		return AndroidMVCAppSingleton.getSingleton().getResourceString(R.string.activity_title_AndroidMVCDemoActivity);
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
		AbstractPagerFragment.logger.info(">> [LoginListFragment.registerDataSource]");
		// Create a unique identifier to locate this Model hierarchy and their matching DataSource.
		DataSourceLocator locator = new DataSourceLocator().addIdentifier(this.getVariant());
		// Create a new Model Generator and register it onto the Fragment to use on request to generate the root model.
		setGenerator(new LoginListGenerator(locator, this.getVariant()));


		//		IModelGenerator generator = ModelGeneratorStore
		//				.registerGenerator(new LoginListGenerator(locator, this.getVariant()));
		//		// Register the datasource. If this same datasource is already at the manager we get it
		//		// instead creating a new one.
		//		SpecialDataSource ds = (SpecialDataSource) DataSourceManager
		//				.registerDataSource(new NeoComDataSource(locator, this.getFactory(), generator));
		//		ds.setVariant(this.getVariant());
		//		ds.setCacheable(true);
		//		this.setDataSource(ds);
		AbstractPagerFragment.logger.info("<< [LoginListFragment.registerDataSource]");
	}

	/**
	 * This methods is used to define the parts that go inside the header section of the page. On this activity
	 * there is no content on the head section.
	 */
	@Override
	protected void setHeaderContents () {
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
	@Override
	public IPart createPart (final AbstractComplexNode node) {
		logger.info("-- [DemoPartFactory.createPart]> Node class: " + node.getClass().getName());
		switch (AndroidMVCDemoActivity.EDemoVariants.valueOf(getVariant())) {
			case DEMO_SEPARATOR_CATALOG:
				if ( node instanceof Separator ) {
					// These special separators can configure an specific icon.
					IPart part = null;
					switch (((Separator) node).getType()) {
						case SHIPSECTION_HIGH:
							part = new SeparatorPart((Separator) node).setIconReference(R.drawable.filtericonhighslot)
							                                          .setRenderMode(AppWideConstants.rendermodes.RENDER_GROUPSHIPFITTING).setFactory(this);
							break;
						case SHIPSECTION_MED:
							part = new SeparatorPart((Separator) node).setIconReference(R.drawable.filtericonmediumslot)
							                                      .setRenderMode(AppWideConstants.rendermodes.RENDER_GROUPSHIPFITTING).setFactory(this);
							break;
						case SHIPSECTION_LOW:
							part = new SeparatorPart((Separator) node).setIconReference(R.drawable.filtericonlowslot)
							                                      .setRenderMode(AppWideConstants.rendermodes.RENDER_GROUPSHIPFITTING).setFactory(this);
							break;
						case SHIPSECTION_RIGS:
							part = new SeparatorPart((Separator) node).setIconReference(R.drawable.filtericonrigslot)
							                                      .setRenderMode(AppWideConstants.rendermodes.RENDER_GROUPSHIPFITTING).setFactory(this);
							break;
						case SHIPSECTION_DRONES:
							part = new SeparatorPart((Separator) node).setIconReference(R.drawable.filtericondrones)
							                                      .setRenderMode(AppWideConstants.rendermodes.RENDER_GROUPSHIPFITTING).setFactory(this);
							break;
						case SHIPSECTION_CARGO:
							part = new SeparatorPart((Separator) node).setIconReference(R.drawable.itemhangar)
							                                      .setRenderMode(AppWideConstants.rendermodes.RENDER_GROUPSHIPFITTING).setFactory(this);
							break;
						default:
							part = new SeparatorPart((Separator) node).setRenderMode(AppWideConstants.rendermodes.RENDER_GROUPSHIPFITTING)
							                                      .setFactory(this);
					}
					return part;
				}
				break;
			case DEMO_SEPARATOR_DETAIL:
				if ( node instanceof Separator ) {
					// These shows the selected Separator but with another rendering.
					IPart		part = new DemoDetailSeparatorPart((Separator) node).setIconReference(R.drawable.filtericonhighslot)
							                                          .setRenderMode(AppWideConstants.rendermodes.RENDER_GROUPSHIPFITTING).setFactory(this);
					return part;
				}
				break;
		}

		// If no part is trapped then call the parent chain until one is found.
		return super.createPart(node);
	}
}

//- CLASS IMPLEMENTATION ...................................................................................
final class LoginListGenerator extends AbstractGenerator implements IModelGenerator {
	// - S T A T I C - S E C T I O N ..........................................................................

	// - F I E L D - S E C T I O N ............................................................................

	// - C O N S T R U C T O R - S E C T I O N ................................................................
	public LoginListGenerator (final DataSourceLocator locator, final String variant) {
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
		AbstractGenerator.logger.info(">> [LoginListGenerator.collaborate2Model]");
		// Initialize the Adapter data structures.
		this.setDataModel(new RootNode());
		// Add each Login to the list. They will expand to show the Character selection.
		for (Login login : NeoComAppConnector.getSingleton().getModelStore().accessLoginList().values()) {
			_dataModelRoot.addChild(login);
			AbstractGenerator.logger
					.info("-- [LoginListGenerator.collaborate2Model]> Adding '" + login.getName() + "' to the _dataModelRoot");
		}
		// Add the node to display the new New Login Button.
		_dataModelRoot.addChild(new NewLoginAction());
		AbstractGenerator.logger.info("<< [LoginListGenerator.collaborate2Model]");
		return _dataModelRoot;
	}
}
// - UNUSED CODE ............................................................................................
