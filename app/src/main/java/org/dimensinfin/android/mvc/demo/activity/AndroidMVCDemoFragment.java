//  PROJECT:     Android.MVC (A.MVC)
//  AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
//  COPYRIGHT:   (c) 2013-2018 by Dimensinfin Industries, all rights reserved.
//  ENVIRONMENT: Android API16.
//  DESCRIPTION: Library that defines a generic Model View Controller core classes to be used
//               on Android projects. Defines the AndroidController factory and the AndroidController core methods to manage
//               a generic converter from a Graph Model to a hierarchical AndroidController model that finally will
//               be converted to a AndroidController list to be used on a BaseAdapter tied to a ListView.
package org.dimensinfin.android.mvc.demo.activity;

import android.os.Bundle;

import org.dimensinfin.android.mvc.activity.AbstractPagerFragment;
import org.dimensinfin.android.mvc.core.AndroidController;
import org.dimensinfin.android.mvc.factory.ControllerFactory;
import org.dimensinfin.android.mvc.datasource.MVCDataSource;
import org.dimensinfin.android.mvc.demo.R;
import org.dimensinfin.android.mvc.interfaces.IAndroidController;
import org.dimensinfin.android.mvc.interfaces.IDataSource;
import org.dimensinfin.android.mvc.interfaces.IControllerFactory;
import org.dimensinfin.android.mvc.model.DemoContainer;
import org.dimensinfin.android.mvc.model.DemoHeaderTitle;
import org.dimensinfin.android.mvc.model.DemoItem;
import org.dimensinfin.android.mvc.model.DemoLabel;
import org.dimensinfin.android.mvc.part.DemoContainerAndroidController;
import org.dimensinfin.android.mvc.part.DemoHeaderTitleAndroidController;
import org.dimensinfin.android.mvc.part.DemoItemAndroidController;
import org.dimensinfin.core.datasource.AbstractGenerator;
import org.dimensinfin.core.datasource.DataSourceLocator;
import org.dimensinfin.core.interfaces.ICollaboration;
import org.dimensinfin.core.interfaces.IModelGenerator;
import org.dimensinfin.core.model.RootNode;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class AndroidMVCDemoFragment extends AbstractPagerFragment {
	// - M E T H O D - S E C T I O N
	@Override
	public String getSubtitle() {
		if (getVariant() == AndroidMVCDemoActivity.EDemoVariants.NON_EXPANDABLE_SECTION.name())
			return getAppContext().getResources().getString(R.string.activity_subtitle_DemoFragment_NonExpandable);
		if (getVariant() == AndroidMVCDemoActivity.EDemoVariants.EXPANDABLE_SECTION.name())
			return getAppContext().getResources().getString(R.string.activity_subtitle_DemoFragment_Expandable);
		return "";
	}

	@Override
	public String getTitle() {
		return getAppContext().getResources().getString(R.string.activity_title_DemoFragment);
	}

	@Override
	public IControllerFactory createFactory() {
		return new DemoControllerFactory(this.getVariant());
	}

	/**
	 * This should generate the list of model instances that should be rendered on the Header. We only use it for the
	 * Non Expandable page section.
	 *
	 * @return list of model data to be rendered on the Header of the page.
	 */
	@Override
	protected List<ICollaboration> registerHeaderSource() {
		List<ICollaboration> headerContents = new ArrayList<>();
		if (getVariant() == AndroidMVCDemoActivity.EDemoVariants.NON_EXPANDABLE_SECTION.name()) {
			headerContents.add(new DemoHeaderTitle(getAppContext().getResources().getString(R.string.appname),
					getAppContext().getResources().getString(R.string.appversion)));
		}
		return headerContents;
	}

	/**
	 * Because on this demo Fragment we use a single fragment class for all the code we should differentiate using the
	 * variant to construct the right <code>IDataSource</code> for each page. We have a page with <b>DemoItems</b> but
	 * one page can expand items where the other not. We are going to use the <b>variant</b> at all the levels to
	 * reduce the code. On this version we have removed the need for the ModelGenerators.
	 * @return a DataSource instance able to generate the required model for each page variant.
	 */
	@Override
	protected IDataSource registerDataSource() {
		AbstractPagerFragment.logger.info(">> [AndroidMVCDemoFragment.registerDataSource]");
		IDataSource ds = null;
		try {
//			if (getVariant() == AndroidMVCDemoActivity.EDemoVariants.NON_EXPANDABLE_SECTION.name())
				ds = new DemoDataSource(new DataSourceLocator()
						.addIdentifier(this.getVariant())
						.addIdentifier("DEMO"), getVariant(), getFactory(), getExtras())
						.setCacheable(true);
//			if (getVariant() == AndroidMVCDemoActivity.EDemoVariants.EXPANDABLE_SECTION.name())
//				ds = new DemoDataSource(new DataSourceLocator()
//						.addIdentifier(this.getVariant())
//						.addIdentifier("DEMO"), getVariant(), getFactory(), getExtras())
//						.setCacheable(false);
			return ds;
		} finally {
			AbstractPagerFragment.logger.info("<< [AndroidMVCDemoFragment.registerDataSource]");
		}
//
//
//
//
//
//		AbstractPagerFragment.logger.info(">> [AndroidMVCDemoFragment.registerDataSource]");
//		// Create a unique identifier to locate this Model hierarchy and their matching DataSource.
//		DataSourceLocator locator = new DataSourceLocator().addIdentifier(this.getVariant());
//		// Create a new Model Generator and register it onto the Fragment to use on request to generate the root model.
//		final IDataSource ds = setGenerator(new DemoSeparatorGenerator(locator, this.getVariant()));
//		AbstractPagerFragment.logger.info("<< [AndroidMVCDemoFragment.registerDataSource]");
//		return ds;
	}

}

//- CLASS IMPLEMENTATION ...................................................................................
final class DemoDataSource extends MVCDataSource implements IDataSource {
	// - S T A T I C - S E C T I O N ..........................................................................

	// - F I E L D - S E C T I O N ............................................................................

	// - C O N S T R U C T O R - S E C T I O N ................................................................
	public DemoDataSource(DataSourceLocator locator, String variant, IControllerFactory factory, Bundle extras) {
		super(locator, variant, factory, extras);
	}

	// - M E T H O D - S E C T I O N ..........................................................................

	/**
	 * This is the core methods all DataSources should implement to generate the model to be used. This demo will
	 * generate a <b>DemoLabel</b> item followed by a set of icon items. For the expandable page the dataSource will
	 * generate a DemoLabel and a container with some more items.
	 * @return
	 */
	public void collaborate2Model() {
		MVCDataSource.logger.info(">> [DemoDataSource.collaborate2Model]");
		// Check if we should use the cached version.
		if (!isCached()) {
			if (getVariant() == AndroidMVCDemoActivity.EDemoVariants.NON_EXPANDABLE_SECTION.name()) {
				// Initialize the Adapter data structures.
//				this.setDataModel(new RootNode());

				// Wait a delay of 6 seconds to allow to watch the counter.
				try {
					Thread.sleep(TimeUnit.SECONDS.toMillis(2));
				} catch (InterruptedException ex) {
				}
				// Add manually each of the demo model nodes.
				addModelContents(new DemoLabel("NON EXPANDABLE SECTION"));
				addModelContents(new DemoItem()
						.setIcon(R.drawable.criticalstate)
						.setTitle("STOP"));
				addModelContents(new DemoItem()
						.setIcon(R.drawable.corpmap)
						.setTitle("Maps"));
				addModelContents(new DemoItem()
						.setIcon(R.drawable.industry)
						.setTitle("Industrial"));
			}
			if (getVariant() == AndroidMVCDemoActivity.EDemoVariants.EXPANDABLE_SECTION.name()) {
				// Initialize the Adapter data structures.
//				this.setDataModel(new RootNode());

				// Wait a delay of 6 seconds to allow to watch the counter.
				try {
					Thread.sleep(TimeUnit.SECONDS.toMillis(4));
				} catch (InterruptedException ex) {
				}
				addModelContents(new DemoLabel("EXPANDABLE SECTION"));
				final DemoContainer container = new DemoContainer()
						.setName("STATES");
				container.addContent(new DemoItem()
						.setIcon(R.drawable.info)
						.setTitle("Critical"));
				container.addContent(new DemoItem()
						.setIcon(R.drawable.info)
						.setTitle("Danger"));
				container.addContent(new DemoItem()
						.setIcon(R.drawable.info)
						.setTitle("Warning"));
				container.addContent(new DemoItem()
						.setIcon(R.drawable.info)
						.setTitle("Normal"));
				container.addContent(new DemoItem()
						.setIcon(R.drawable.info)
						.setTitle("Nominal"));
				container.addContent(new DemoItem()
						.setIcon(R.drawable.info)
						.setTitle("Stop"));
				addModelContents(container);
			}
		}
		logger.info("<< [PlantDataSource.collaborate2Model]");
	}

	private void threadWait(final long milliseconds) {
		try {
			Thread.sleep(milliseconds);
		} catch (InterruptedException ex) {
		}
	}
}

// - CLASS IMPLEMENTATION ...................................................................................
final class DemoControllerFactory extends ControllerFactory implements IControllerFactory {
	public DemoControllerFactory(final String variantSelected) {
		super(variantSelected);
	}

	// - M E T H O D - S E C T I O N

	/**
	 * The method should create the matching part for the model received. We can use the variant to change at creation
	 * time the matching part or to replace parts when required.
	 */
	public IAndroidController createController(final ICollaboration node) {
		logger.info("-- [DemoControllerFactory.createPart]> Node class: " + node.getClass().getSimpleName());
		if (node instanceof DemoHeaderTitle) {
			// These shows the selected Separator but with another rendering.
			prt= AndroidController.<DemoHeaderTitle>builder().model((DemoHeaderTitle) node).build();



			final AndroidController newpart =  DemoHeaderTitleAndroidController.builder().build();    .Builder<DemoHeaderTitle>((DemoHeaderTitle) node, this.getRootPart())
					.build();
			IAndroidController part = new DemoHeaderTitleAndroidController((DemoHeaderTitle) node).setIconReference(R.drawable.arrowleft)
			                                                            .setRenderMode(getVariant())
			                                                            .setFactory(this);
			return part;
		}
		if (node instanceof DemoContainer) {
			// These shows the selected Separator but with another rendering.
			IAndroidController part = new DemoContainerAndroidController((DemoContainer) node).setRenderMode(getVariant())
			                                                        .setFactory(this);
			return part;
		}

		// Demo classes and models
		if (node instanceof DemoItem) {
			// These shows the selected Separator but with another rendering.
			IAndroidController part = new DemoItemAndroidController((DemoItem) node).setRenderMode("-ITEM-")
			                                              .setFactory(this);
			return part;
		}
		// WARNING - When node classes have direct inheritance put the parent below their children.
		if (node instanceof DemoLabel) {
			// These shows the selected Separator but with another rendering.
			IAndroidController part = new DemoItemAndroidController((DemoLabel) node).setRenderMode("-LABEL-")
			                                               .setFactory(this);
			return part;
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
	public DemoSeparatorGenerator(final DataSourceLocator locator, final String variant) {
		super(locator, variant);
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
		// Generate the contents depending on the variant.
		if (getVariant() == AndroidMVCDemoActivity.EDemoVariants.NON_EXPANDABLE_SECTION.name()) {
			// Initialize the Adapter data structures.
			this.setDataModel(new RootNode());

			// Wait a delay of 6 seconds to allow to watch the counter.
			try {
				Thread.sleep(TimeUnit.SECONDS.toMillis(6));
			} catch (InterruptedException ex) {
			}
			// Add manually each of the demo model nodes.
			_dataModelRoot.addChild(new DemoLabel("NON EXPANDABLE SECTION"));
			_dataModelRoot.addChild(new DemoItem()
					.setIcon(R.drawable.criticalstate)
					.setTitle("STOP"));
			_dataModelRoot.addChild(new DemoItem()
					.setIcon(R.drawable.corpmap)
					.setTitle("Maps"));
		}
		if (getVariant() == AndroidMVCDemoActivity.EDemoVariants.EXPANDABLE_SECTION.name()) {
			// Initialize the Adapter data structures.
			this.setDataModel(new RootNode());

			// Wait a delay of 6 seconds to allow to watch the counter.
			try {
				Thread.sleep(TimeUnit.SECONDS.toMillis(4));
			} catch (InterruptedException ex) {
			}
			_dataModelRoot.addChild(new DemoLabel("EXPANDABLE SECTION"));
			final DemoContainer container = new DemoContainer()
					.setName("STATES");
			container.addContent(new DemoItem()
					.setIcon(R.drawable.info)
					.setTitle("Critical"));
			container.addContent(new DemoItem()
					.setIcon(R.drawable.info)
					.setTitle("Danger"));
			container.addContent(new DemoItem()
					.setIcon(R.drawable.info)
					.setTitle("Warning"));
			container.addContent(new DemoItem()
					.setIcon(R.drawable.info)
					.setTitle("Normal"));
			container.addContent(new DemoItem()
					.setIcon(R.drawable.info)
					.setTitle("Nominal"));
			container.addContent(new DemoItem()
					.setIcon(R.drawable.info)
					.setTitle("Stop"));
			_dataModelRoot.addChild(container);
		}
		//
		//		Separator node = new Separator("RED-EMPTY").setType(Separator.ESeparatorType.EMPTY_SIGNAL);
		//		_dataModelRoot.addChild(node);
		//		node = new Separator("RED-Line").setType(Separator.ESeparatorType.LINE_RED);
		//		_dataModelRoot.addChild(node);
		//		node = new Separator("ORANGE-Line").setType(Separator.ESeparatorType.LINE_ORANGE);
		//		_dataModelRoot.addChild(node);
		//		node = new Separator("YELLOW-Line").setType(Separator.ESeparatorType.LINE_YELLOW);
		//		_dataModelRoot.addChild(node);
		//		node = new Separator("GREEN-Line").setType(Separator.ESeparatorType.LINE_GREEN);
		//		_dataModelRoot.addChild(node);
		//
		//		final Separator expandable = new Container("-CONTAINER-").setType(Separator.ESeparatorType.LINE_WHITE);
		//		_dataModelRoot.addChild(expandable);

		AbstractGenerator.logger.info("<< [DemoSeparatorGenerator.collaborate2Model]");
		return _dataModelRoot;
	}
}
// - UNUSED CODE ............................................................................................
