//	PROJECT:        NeoCom.MVC (NEOC.MVC)
//	AUTHORS:        Adam Antinoo - adamantinoo.git@gmail.com
//	COPYRIGHT:      (c) 2013-2016 by Dimensinfin Industries, all rights reserved.
//	ENVIRONMENT:		Android API16.
//	DESCRIPTION:		Library that defines a generic Model View Controller core classes to be used
//									on Android projects. Defines the Part factory and the Part core methods to manage
//									the extended GEF model into the Android View to be used on ListViews.
package org.dimensinfin.android.mvc.datasource;

//- IMPORT SECTION .........................................................................................
import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;

import org.dimensinfin.android.datasource.DataSourceLocator;
import org.dimensinfin.android.interfaces.IModelGenerator;
import org.dimensinfin.android.mvc.constants.SystemWideConstants;
import org.dimensinfin.android.mvc.core.AbstractAndroidPart;
import org.dimensinfin.android.mvc.core.RootPart;
import org.dimensinfin.android.mvc.interfaces.IDataSource;
import org.dimensinfin.android.mvc.interfaces.IPart;
import org.dimensinfin.android.mvc.interfaces.IPartFactory;
import org.dimensinfin.core.model.RootNode;

// - CLASS IMPLEMENTATION ...................................................................................
/**
 * This class implements the most common code and flow for all DataSources to allow the best code
 * generalization. This class has the common code to make the model transformation to the Part hierarchy and
 * from it to the list of Parts used to renden the view.
 * 
 * @author Adam Antinoo
 */
public class SpecialDataSource extends AbstractDataSource implements IDataSource {
	// - S T A T I C - S E C T I O N ..........................................................................
	private static final long							serialVersionUID	= -9083587546700227219L;
	public static Logger									logger						= Logger.getLogger("SpecialDataSource");

	// - F I E L D - S E C T I O N ............................................................................
	/** The unique identifier of the DataSource */
	private DataSourceLocator							_locator					= null;
	/** Reference to the Model Generator that will be the source point for the model hierarchy. */
	protected IModelGenerator							_modelGenerator		= null;
	private String												_variant					= "DEFAULT_VARIANT";
	private boolean												_cacheable				= true;
	private final HashMap<String, Object>	_parameters				= new HashMap<String, Object>();
	protected IPartFactory								_partFactory			= null;

	/** The initial node where to store the model. Model elements are children of this root. */
	protected RootNode										_dataModelRoot		= null;
	/** The root node for the Part hierarchy that matches the data model hierarchy. */
	protected RootPart										_partModelRoot		= null;
	/** The list of Parts to show on the viewer. This is the body section that is scrollable. */
	protected ArrayList<IPart>						_bodyParts				= new ArrayList<IPart>();
	/** The list of Parts to show on the header. */
	protected ArrayList<IPart>						_headParts				= new ArrayList<IPart>();
	//	private DataSourceManager							_dsManager;

	// - C O N S T R U C T O R - S E C T I O N ................................................................
	public SpecialDataSource(final DataSourceLocator locator, final IPartFactory factory) {
		_locator = locator;
		_partFactory = factory;
	}

	// - M E T H O D - S E C T I O N ..........................................................................
	public SpecialDataSource addParameter(final String name, final int value) {
		_parameters.put(name, Integer.valueOf(value));
		return this;
	}

	public SpecialDataSource addParameter(final String name, final long value) {
		_parameters.put(name, Long.valueOf(value));
		return this;
	}

	public SpecialDataSource addParameter(final String name, final String value) {
		_parameters.put(name, value);
		return this;
	}

	/**
	 * On the new change where we include the usage of Generators, this feature is delegated to the generator
	 * that will provide the model that matches the definition. Is then the Generator the one that will manage
	 * the model elements that are part of the visible presentation.
	 *
	 * This method is called to initialize the model structures. Those structures are copies or transformations
	 * from the main model stored in the <code>store</code> reference. <br>
	 * This is the method to initialize the copy of the model structures on the DataSource. Every time this
	 * method is called, the complete model is recreated. There are two ways to recreate it, comparing with the
	 * old copy and inserting/deleting different nodes or recreating completely the new model copy. Once this
	 * method is called we can create the depending part hierarchy. In the current implementation we always
	 * recreate the model from scratch.<br>
	 * The resulting model always has a RootNode and the contents are stored as children of that node. The model
	 * only deals with the first level so each on the childs will create their own set of the model on call when
	 * required by the model transformation.
	 */
	public RootNode collaborate2Model() {
		SpecialDataSource.logger.info(">< [SpecialDataSource.collaborate2Model]");
		_dataModelRoot = _modelGenerator.collaborate2Model();
		return _dataModelRoot;
	}

	/**
	 * After the model is created we have to transform it into the Part list expected by the DataSourceAdapter.
	 * <br>
	 * The Part creation is performed by the corresponding PartFactory we got at the DataSource creation.<br>
	 * We transform the model recursively and keeping the already available Part elements. We create a
	 * duplicated of the resulting Part model and we move already parts from the current model to the new model
	 * or create new part and finally remove what is left and unused.
	 */

	@Override
	public void createContentHierarchy() {
		try {
			SpecialDataSource.logger.info(">> [SpecialDataSource.createContentHierarchy]");
			// Check if we have already a Part model.
			// But do not forget to associate the new Data model even of the old exists.
			if (null == _partModelRoot) {
				_partModelRoot = new RootPart(_dataModelRoot, _partFactory);
			} else {
				_partModelRoot.setModel(_dataModelRoot);
			}

			SpecialDataSource.logger.info(
					"-- [SpecialDataSource.createContentHierarchy]> Initiating the refreshChildren() for the _partModelRoot");
			// Intercept any exception on the creation of the model but do not cut the progress of the already added items
			try {
				_partModelRoot.refreshChildren();
			} catch (Exception e) {
				e.printStackTrace();
			}
			// Get the list of Parts that will be used for the ListView
			_bodyParts = new ArrayList<IPart>();
			// Select for the body contents only the viewable Parts from the Part model. Make it a list.
			_bodyParts.addAll(_partModelRoot.collaborate2View());
		} catch (Exception e) {
			e.printStackTrace();
		}
		SpecialDataSource.logger
				.info("-- [SpecialDataSource.createContentHierarchy]> _bodyParts.size: " + _bodyParts.size());
		SpecialDataSource.logger.info("<< [SpecialDataSource.createContentHierarchy]");
	}

	/**
	 * Return just the list of viewable Parts. During the composition of the list we transform it of class
	 * because we should change the final class level returned to the higher level possible and now for
	 * compatibility we keep the <code>AbstractAndroidPart</code>.
	 */
	@Override
	public ArrayList<AbstractAndroidPart> getBodyParts() {
		SpecialDataSource.logger.info(">> [SpecialDataSource.getBodyParts]");
		// Get the list of Parts that will be used for the ListView
		ArrayList<AbstractAndroidPart> result = new ArrayList<AbstractAndroidPart>();
		if (null != _bodyParts) {
			for (IPart part : _bodyParts)
				if (part instanceof AbstractAndroidPart) {
					result.add((AbstractAndroidPart) part);
				}
		}
		SpecialDataSource.logger.info("<< [SpecialDataSource.getBodyParts]> result.size: " + result.size());
		return result;
	}

	public DataSourceLocator getDataSourceLocator() {
		return _locator;
	}

	/**
	 * This method is also deprecated because the Part generation for the header is kept outside the DataSource.
	 * The management of the header is now performed at the Fragment level and once it is changed to another
	 * level we can check this code. This method is kept for backward compatibility.
	 */
	@Deprecated
	public ArrayList<AbstractAndroidPart> getHeaderParts() {
		ArrayList<AbstractAndroidPart> result = new ArrayList<AbstractAndroidPart>();
		for (IPart node : _headParts) {
			result.add((AbstractAndroidPart) node);
		}
		return result;
	}

	public String getVariant() {
		return _variant;
	}

	/**
	 * This method is called whenever there is an event on any Part related to this DataSource. We just process
	 * structure changes that need the DataSource to reconstruct the Part model from the new Model state. Wrong.
	 * The model does not change but the result from the collaborate2View transformation does.
	 */
	@Override
	public void propertyChange(final PropertyChangeEvent event) {
		// The expand/collapse state has changed.
		if (SystemWideConstants.events
				.valueOf(event.getPropertyName()) == SystemWideConstants.events.EVENTSTRUCTURE_ACTIONEXPANDCOLLAPSE) {
			_bodyParts = new ArrayList<IPart>();
			_bodyParts.addAll(_partModelRoot.collaborate2View());
			this.fireStructureChange(SystemWideConstants.events.EVENTADAPTER_REQUESTNOTIFYCHANGES.name(), event.getOldValue(),
					event.getNewValue());
			return;
		}
		// TODO Check if we should get this event and fire it again.
		if (SystemWideConstants.events
				.valueOf(event.getPropertyName()) == SystemWideConstants.events.EVENTADAPTER_REQUESTNOTIFYCHANGES) {
			this.fireStructureChange(SystemWideConstants.events.EVENTADAPTER_REQUESTNOTIFYCHANGES.name(), event.getOldValue(),
					event.getNewValue());
			return;
		}
		super.propertyChange(event);
	}

	public void setCacheable(final boolean cacheState) {
		_cacheable = cacheState;
	}

	//[01]
	public void setDataModel(final RootNode root) {
		_dataModelRoot = root;
	}

	public SpecialDataSource setVariant(final String variant) {
		_variant = variant;
		return this;
	}

	/**
	 * When we receive update events we should optimize the tasks that should be performed again and simplify
	 * the tasks to run.
	 */
	@Override
	public void updateContentHierarchy() {
		try {
			SpecialDataSource.logger.info(">> [SpecialDataSource.updateContentHierarchy]");
			// Check if we have already a Part model.
			if (null == _partModelRoot) {
				_partModelRoot = new RootPart(_dataModelRoot, _partFactory);
				try {
					_partModelRoot.refreshChildren();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			} else {
				_partModelRoot.setModel(_dataModelRoot);
			}

			// Get the list of Parts that will be used for the ListView
			_bodyParts = new ArrayList<IPart>();
			// Select for the body contents only the viewable Parts from the Part model. Make it a list.
			_bodyParts.addAll(_partModelRoot.collaborate2View());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		SpecialDataSource.logger
				.info("-- [SpecialDataSource.updateContentHierarchy]> _bodyParts.size: " + _bodyParts.size());
		SpecialDataSource.logger.info("<< [SpecialDataSource.updateContentHierarchy]");
	}

	protected int getParameterInteger(final String name) {
		Object param = _parameters.get(name);
		if (null != param) if (param instanceof Integer) return ((Integer) param).intValue();
		return 0;
	}

	protected long getParameterLong(final String name) {
		Object param = _parameters.get(name);
		if (null != param) if (param instanceof Long) return ((Long) param).longValue();
		return 0;
	}

	protected String getParameterString(final String name) {
		Object param = _parameters.get(name);
		if (null != param) if (param instanceof String) return (String) param;
		return "";
	}
}

// - UNUSED CODE ............................................................................................
