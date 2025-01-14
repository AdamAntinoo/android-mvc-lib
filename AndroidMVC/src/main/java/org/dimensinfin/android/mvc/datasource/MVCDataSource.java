package org.dimensinfin.android.mvc.datasource;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.dimensinfin.android.mvc.controller.IAndroidController;
import org.dimensinfin.android.mvc.domain.Spacer;
import org.dimensinfin.android.mvc.factory.IControllerFactory;
import org.dimensinfin.logging.LogWrapper;
import org.dimensinfin.core.domain.EEvents;
import org.dimensinfin.core.domain.EventEmitter;
import org.dimensinfin.core.domain.IntercommunicationEvent;
import org.dimensinfin.core.interfaces.ICollaboration;
import org.dimensinfin.core.interfaces.IEventEmitter;
import org.dimensinfin.core.interfaces.IEventReceiver;

/**
 * This class is the abstract implementation for a source of the model elements that should be rendered on an special
 * ListView. The instance connect to the List adapter to generate the views for the contents by a transformation from
 * the root model elements defined on this data source, then converted to a graph of controllers that are able to
 * integrate the model with he android view element.
 * <p>
 * Finally this class has the code to generate the list of controllers to be used on the View List rendering process and
 * all the event processing required to update that list to be rendered when the underlying model changes.
 *
 * @author Adam Antinoo
 * @since 1.0.0
 */
public abstract class MVCDataSource implements IDataSource, IEventEmitter {
	/**
	 * This field contains the list of nodes that were provided by the data source and that are at the first level. From this list of nodes the MVC
	 * will generate the complete model hierarchy and from it the list of views to be rendered.
	 */
	protected final List<ICollaboration> dataModelRoot = new ArrayList<>();
	/**
	 * This field has the same purpose but for the header section that is a linear container without scrolling.
	 */
	protected final List<ICollaboration> headerModelRoot = new ArrayList<>();
//	protected Boolean monitor = Boolean.TRUE; // Synchronization monitor to serialize model generation.
	/**
	 * Unique DataSource string identifier to locate this instance on the <code>DataSourceManager</code> in case the
	 * instances should be cached.
	 */
	protected DataSourceLocator locator;
	/**
	 * Factory to be used on the AndroidController hierarchy generation. Each AndroidController has a connection to this
	 * factory to create its children parts from the model nodes. This is a mandatory field that should be available at
	 * the creation because the DS cannot work without a AndroidController Factory.
	 */
	protected IControllerFactory controllerFactory;
	/**
	 * Copy of the extras bundle received by the Activity.
	 */
	protected Bundle extras = new Bundle();
	/**
	 * This is the Fragment or Activity code used to differentiate between different model generations. It is stored on
	 * the String format to be independent from any of the enumerated structures in the different modules. Anyway it
	 * should be a conversion from closed list of values.
	 */
	protected String variant = "-DEFAULT-VARIANT-";
	/**
	 * Flag to indicate if the model contents generated can be cached and we can avoid running the <code>collaborate2Model
	 * ()</code> method on every fragment instantiation. If the model is suitable for caching we can speed up the turn of
	 * the device because we have not to generate again the DataSource and its model data structure.
	 */
	@Deprecated
	private boolean canBeCached = false;
	/**
	 * This is the delegate to process inter node events. The data source will receive events from the controllers that
	 * are generated by this same data source and will also send events to all the connected listeners that should be
	 * reported of controller changes.
	 * <p>
	 * With that connections any change on the Model should be sent as an event to the Controller. Any action on the
	 * Controller will be also detected and sent along with model changes to the DataSource that will command the
	 * visualization changes by relaying the events to the DataSourceAdapter listener.
	 */
	private IEventEmitter eventController = new EventEmitter();
	private boolean dirty = false; // This is the flag to detect model changes.
	/**
	 * This field now contains the list of controllers resulting from the model list that is also stored into one array
	 * and not inside a specific model node. This simplifies the code for Root nodes than now have disappeared.
	 * This list is the list of controllers to be rendered on the data section list view.
	 */
	private List<IAndroidController> controllerDataSectionRoot = new ArrayList<>();
	/**
	 * This field now contains the list of controllers resulting from the model list that is also stored into one array
	 * and not inside a specific model node. This simplifies the code for Root nodes than now have disappeared.
	 * This list is the list of controllers to be rendered on the header section list view.
	 */
	private List<IAndroidController> controllerHeaderSectionRoot = new ArrayList<>();

	// - C O N S T R U C T O R S
	protected MVCDataSource() { }

	@Deprecated
	private MVCDataSource( final DataSourceLocator locator, final IControllerFactory controllerFactory ) {
		this.locator = locator;
		this.controllerFactory = controllerFactory;
	}

	// - M E T H O D - S E C T I O N
	private boolean isDirty() {
		return this.dirty;
	}

	private void cleanDirty() {
		this.dirty = false;
	}

	// - G E T T E R S   &   S E T T E R S
//	protected IControllerFactory getControllerFactory() {
//		return this.controllerFactory;
//	}
//
//	public MVCDataSource canBeCached( final boolean shouldBeCached ) {
//		this.canBeCached = shouldBeCached;
//		return this;
//	}

	// - I D A T A S O U R C E   I N T E R F A C E
	@Override
	public DataSourceLocator getDataSourceLocator() {
		return this.locator;
	}

	@Deprecated
	@Override
	public boolean isCacheable() {
		return this.canBeCached;
	}

	@Override
	public IDataSource addHeaderContents( final ICollaboration newModel ) {
		LogWrapper.info( "Adding model >Header: {}", newModel.getClass().getSimpleName() );
		this.headerModelRoot.add( newModel );
		return this;
	}

	/**
	 * This is the single way to add more content to the DataSource internal model representation. Encapsulating this
	 * functionality on this method we make sure that the right events are generated and the model is properly updated and
	 * the render process will work as expected.
	 * <p>
	 * Any change to the model should report the data source on a dirty state. When the updates are completed then we
	 * should generate the new contents with a simple call to the adapter's <code>notifyDataSetChanged</code>. With this
	 * notification we should start the list view content generation from the bottom up.
	 *
	 * @param newModel a new node to be added to the contents of the root point of the model.
	 * @return this IDataSource instance to allow flow coding.
	 */
	public IDataSource addModelContents( final ICollaboration newModel ) {
		LogWrapper.info( "Adding model >Data: {}", newModel.getClass().getSimpleName() );
		this.dataModelRoot.add( newModel );
		this.dirty = true; // Signal the model change
		return this;
	}

	/**
	 * The header contents are the list of different structures and the count for each one. To render the structure we also need the
	 * structure icon identifier that is also managed at the model level.
	 *
	 * @return the model list to be rendered on the header section view.
	 */
	@Override
	public List<IAndroidController> getHeaderSectionContents() {
		LogWrapper.enter();
		final List<IAndroidController> controllers = new ArrayList<>();
		try {
			this.refreshHeaderSection();
			for (IAndroidController controller : this.controllerHeaderSectionRoot) {
				controller.collaborate2View( controllers );
			}
		} finally {
			LogWrapper.exit( "Header Contents count: {}", Integer.toString( controllers.size() ) );
		}
		return controllers;
	}

	/**
	 * This is the point where the adapter connects to get the contents for its list view. When the list view need any
	 * update it will call this point to get a fresh controller list. Most of the times that controller list is ready and
	 * just have to be collected from the full controller hierarchy with the collaboration to view procedure.
	 * <p>
	 * Any new controller or modified controller will not have a view and then when the contents are rendered will get its
	 * view representation created for visualization. For the other calls this just will collect into a new list a set of
	 * already ready controllers.
	 *
	 * @return the list of controllers that collaborate to the view list this time.
	 */
	public List<IAndroidController> getDataSectionContents() {
		LogWrapper.enter();
		final List<IAndroidController> controllers = new ArrayList<>();
		try {
			// Check if the model needs update (dirty flag) or we can jump directly to the view collaboration.
			if (this.isDirty()) {
				LogWrapper.info( "Data contents dirty. Refreshing model." );
				this.refreshDataSection();
				this.cleanDirty();
			}
			for (IAndroidController controller : this.controllerDataSectionRoot) {
				controller.collaborate2View( controllers );
			}
		} finally {
			LogWrapper.exit( "Data Contents count: {}", Integer.toString( controllers.size() ) );
		}
		return controllers;
	}

	// - D E L E G A T E - I E V E N T E M I T T E R
	@Override
	public void addEventListener( final IEventReceiver listener ) {this.eventController.addEventListener( listener );}

	@Override
	public void removeEventListener( final IEventReceiver listener ) {this.eventController.removeEventListener( listener );}

	@Override
	public boolean sendChangeEvent( final IntercommunicationEvent event ) {return this.eventController.sendChangeEvent( event );}

	@Override
	public boolean sendChangeEvent( final String eventName ) {return this.eventController.sendChangeEvent( eventName );}

	@Override
	public boolean sendChangeEvent( final String eventName, final Object origin ) {
		return this.eventController.sendChangeEvent( eventName, origin );
	}

	@Override
	public boolean sendChangeEvent( final String eventName, final Object origin, final Object oldValue, final Object newValue ) {
		return this.eventController.sendChangeEvent( eventName, origin, oldValue, newValue );
	}

	public Bundle getExtras() {
		return extras;
	}

	@Deprecated
	public MVCDataSource setExtras( final Bundle extras ) {
		this.extras = extras;
		return this;
	}

	public String getVariant() {
		return variant;
	}

	@Deprecated
	public MVCDataSource setVariant( final String variant ) {
		this.variant = variant;
		return this;
	}

	public List<ICollaboration> getDataModelRoot() {
		return this.dataModelRoot;
	}

	public List<ICollaboration> getHeaderModelRoot() {
		return this.headerModelRoot;
	}

	private void refreshHeaderSection() {
		LogWrapper.enter();
		this.controllerHeaderSectionRoot.clear();
		synchronized (this.headerModelRoot) {
			for (ICollaboration modelNode : this.headerModelRoot) {
				try {
					final IAndroidController newController = this.controllerFactory.createController( modelNode );
					this.controllerHeaderSectionRoot.add( newController );
					newController.setDataSource( this ); // Connect the controller to the originator data source
					newController.refreshChildren();
				} catch (final ClassCastException cce) {
					this.controllerDataSectionRoot.add(
							this.controllerFactory.createController( new Spacer.Builder()
									                                         .withLabel( cce.getMessage() )
									                                         .build() ) );
				} catch (final RuntimeException rte) {
					this.controllerDataSectionRoot.add(
							this.controllerFactory.createController( new Spacer.Builder()
									                                         .withLabel( rte.getMessage() )
									                                         .build() ) );
				}
			}
		}
		LogWrapper.exit( "Contents: {}", Integer.toString( this.controllerHeaderSectionRoot.size() ) );
	}

	private void refreshDataSection() {
		LogWrapper.enter();
		this.controllerDataSectionRoot.clear();
		synchronized (this.dataModelRoot) {
			for (ICollaboration modelNode : this.dataModelRoot) {
				try {
					final IAndroidController newController = this.controllerFactory.createController( modelNode );
					this.controllerDataSectionRoot.add( newController );
					newController.setDataSource( this ); // Connect the controller to the originator data source
					newController.refreshChildren();
				} catch (final ClassCastException cce) {
					this.controllerDataSectionRoot.add(
							this.controllerFactory.createController( new Spacer.Builder()
									                                         .withLabel( cce.getMessage() )
									                                         .build() ) );
				} catch (final RuntimeException rte) {
					this.controllerDataSectionRoot.add(
							this.controllerFactory.createController( new Spacer.Builder()
									                                         .withLabel( rte.getMessage() )
									                                         .build() ) );
				}
			}
		}
		LogWrapper.exit( "Contents: {}", Integer.toString( this.controllerDataSectionRoot.size() ) );
	}

	/**
	 * Cleans the model and the corresponding controllers. Resets the data source to be empty.
	 */
	public IDataSource cleanModel() {
		this.dataModelRoot.clear();
		this.controllerDataSectionRoot.clear();
		this.dirty = true; // Signal the model change
		return this;
	}

	public void cleanHeaderModel() {
		this.headerModelRoot.clear();
	}

	// - I E V E N T R E C E I V E R   I N T E R F A C E

	/**
	 * This method is called whenever there is an event from any model change or any AndroidController interaction. There
	 * are two groups of events, <b>structural</b> that change the model structure and contents and that require a full
	 * regeneration of all the transformations and <b>content</b> that can change the list of elements to be visible at
	 * this point in time but that do not change the initial structure. The contents can happen from changes on the model
	 * data or by interactions on the Parts that have some graphical impact.
	 *
	 * If the model structure changes we should recreate the Model -> AndroidController transformation and generate
	 * another AndroidController tree with Parts matching the current model graph. At this transformation we can transform
	 * any data connected structure real or virtual to a hierarchy graph with the standard parent-child structure. We use
	 * the
	 * <code>collaborate2Model()</code> as a way to convert internal data structures to a hierarchy representation on a
	 * point in time. We isolate internal model ways to deal with data and we can optimize for the AndroidController
	 * hierarchy without compromising the model flexibility.
	 *
	 * If the contents change we only should run over the AndroidController tree to make the transformation to generate a
	 * new AndroidController list for all the new visible and render items. This is performed with the
	 * <code>collaborate2View()</code> method for any AndroidController that will then decide which of its internal
	 * children are going to be referenced for the collaborating list of Parts. This is the right place where to set up
	 * programmatic filtering or sorting because at this point we can influence the output representation for the model
	 * instance. We can also decorate the resulting AndroidController list breaking the one to one relationship between a
	 * model instance and a AndroidController instance.
	 *
	 * After the models changes we should send a message to the <code>DataSourceAdapter</code> to refresh the graphical
	 * elements and change the display. <code>DataSource</code> instances do not have a reference to the Adapter nor to
	 * the Fragment that created them but during the creation process the Adapter installed a listener to get a copy of
	 * the events sent by all its data sources. So we can sent that message by sending again another type of message
	 * related to the need for the display for update, the <code>EVENTADAPTER_REQUESTNOTIFYCHANGES</code>
	 *
	 * @param event the event to be processed. Event have a property name that is used as a selector.
	 */
	@Override
	public synchronized void receiveEvent( final IntercommunicationEvent event ) {
		LogWrapper.info( "Processing Event: {}", event.getPropertyName() );
		// - C O N T E N T   E V E N T S
		// The expand/collapse state has changed.
		if (event.getPropertyName().equalsIgnoreCase( EEvents.EVENT_NEWDATA.name() )) {
			LogWrapper.info( "Event: {} processed.", event.getPropertyName() );
			this.sendChangeEvent( event.getPropertyName() );
			return;
		}
		if (event.getPropertyName().equalsIgnoreCase( EEvents.EVENT_REFRESHDATA.name() )) {
			LogWrapper.info( "Event: {} processed.", event.getPropertyName() );
			this.sendChangeEvent( event.getPropertyName() );
			return;
		}
		this.sendChangeEvent( event.getPropertyName() ); // Pass the event to the Adapter
	}

	// - C O R E
	@Override
	public String toString() {
		return new ToStringBuilder( this, ToStringStyle.JSON_STYLE )
				       .append( "locator", this.locator )
				       .append( "variant", this.variant )
				       .append( "canBeCached", this.canBeCached )
				       .append( "dirty", this.dirty )
				       .toString();
	}

	// - B U I L D E R
	protected static abstract class Builder<T extends MVCDataSource, B extends MVCDataSource.Builder> {
		private final DataSourceLocator identifier;
		protected B actualClassBuilder;

		public Builder() {
			this.identifier = new DataSourceLocator();
			this.actualClassBuilder = getActualBuilder();
		}

		protected abstract T getActual();

		protected abstract B getActualBuilder();

		public B addIdentifier( final int identifier ) {
			this.identifier.addIdentifier( Integer.valueOf( identifier ).toString() );
			return this.actualClassBuilder;
		}

		public B addIdentifier( final long identifier ) {
			this.identifier.addIdentifier( Long.valueOf( identifier ).toString() );
			return this.actualClassBuilder;
		}

		public B addIdentifier( final String identifier ) {
			if (null != identifier) this.identifier.addIdentifier( identifier );
			return this.actualClassBuilder;
		}

		public B withFactory( final IControllerFactory factory ) {
			this.getActual().controllerFactory = Objects.requireNonNull( factory );
			return this.actualClassBuilder;
		}

		public B withVariant( final String variant ) {
			this.getActual().variant = Objects.requireNonNull( variant );
			return this.actualClassBuilder;
		}

		public B withExtras( final Bundle extras ) {
			if (null != extras) this.getActual().extras = extras;
			else this.getActual().extras = new Bundle();
			return this.actualClassBuilder;
		}

		public T build() {
			// Register the identifier and create the data source.
			this.getActual().locator = this.identifier;
			// Do any other validations. If failed then launch an exception.
			Objects.requireNonNull( this.getActual().locator );
			Objects.requireNonNull( this.getActual().controllerFactory );
			return this.getActual();
		}
	}
}
