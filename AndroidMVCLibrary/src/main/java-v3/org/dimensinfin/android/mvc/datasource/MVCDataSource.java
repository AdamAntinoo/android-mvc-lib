//  PROJECT:     Android.MVC (A.MVC)
//  AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
//  COPYRIGHT:   (c) 2013-2018 by Dimensinfin Industries, all rights reserved.
//  ENVIRONMENT: Android API16.
//  DESCRIPTION: Library that defines a generic Model View Controller core classes to be used
//               on Android projects. Defines the Part factory and the Part core methods to manage
//               a generic converter from a Graph Model to a hierarchical Part model that finally will
//               be converted to a Part list to be used on a BaseAdapter tied to a ListView.
//               The new implementation performs the model to list transformation on the fly each time
//               a model change is detected so the population of the displayed view should be done in
//               real time while processing the model sources. This should allow for search and filtering.
package org.dimensinfin.android.mvc.datasource;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.dimensinfin.android.mvc.R;
import org.dimensinfin.android.mvc.activity.AbstractPagerFragment;
import org.dimensinfin.android.mvc.constants.SystemWideConstants;
import org.dimensinfin.android.mvc.core.AbstractAndroidPart;
import org.dimensinfin.android.mvc.core.AbstractPart;
import org.dimensinfin.android.mvc.core.AbstractRender;
import org.dimensinfin.android.mvc.core.RootPart;
import org.dimensinfin.android.mvc.interfaces.IAndroidPart;
import org.dimensinfin.android.mvc.interfaces.IPartFactory;
import org.dimensinfin.android.mvc.interfaces.IPartsDataSource;
import org.dimensinfin.android.mvc.interfaces.IRootPart;
import org.dimensinfin.core.datasource.DataSourceLocator;
import org.dimensinfin.core.interfaces.ICollaboration;
import org.dimensinfin.core.model.AbstractPropertyChanger;
import org.dimensinfin.core.model.RootNode;
import org.dimensinfin.core.model.Separator;
import org.joda.time.Instant;
import org.joda.time.format.DateTimeFormatterBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.PropertyChangeEvent;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

/**
 * New complete core implementation for the DataSource that should be connected to the extended BaseAdapter to
 * provide the Adapter with the list of Parts to be used for the rendering on the LitView.
 * @author Adam Antinoo
 */

// - CLASS IMPLEMENTATION ...................................................................................
public abstract class MVCDataSource extends AbstractPropertyChanger implements IPartsDataSource {
	// - S T A T I C - S E C T I O N ..........................................................................
	private static final long serialVersionUID = -9128905983909144873L;
	public static Logger logger = LoggerFactory.getLogger("MVCDataSource");

	// - F I E L D - S E C T I O N ............................................................................
	/**
	 * Unique DataSource string identifier to locate this instance on the <code>DataSourceManagerv3</code> in case the
	 * instances should be cached.
	 */
	private final DataSourceLocator _locator;
	/** Copy of the extras bundle received by the Activity. */
	private Bundle _extras = new Bundle();
	/** This is the Fragment or Activity code used to differentiate between different model generations. */
	private String _variant = "-DEFAULT-VARIANT-";
	/**
	 * Factory to be used on the hierarchy generation. Each part has a connection to this factory to create its
	 * children from the model nodes.
	 */
	private IPartFactory _partFactory = null;
	/**
	 * Flag to indicate if the model contents generated can be cached and we can avoid running the <code>collaborate2Model
	 * ()</code> method on every fragment instantiation. If the model is suitable for caching we can speed up the turn of
	 * the device because we have not to generate again the DataSource and its model sta structure.
	 */
	private boolean _shouldBeCached = false;
	/**
	 * The initial node where to store the model. Model elements are children of this root. This version exports this
	 * node to dynamically detect the changes and generate the missing hierarchy elements that are being added during
	 * the Model generation.
	 * There are only a variant for the RootNode so we can lock it to a predefined instance.
	 * If we need to customize the Root nodes (for example to add special filtering) we should remove the
	 * <code>final</code> from this field.
	 */
	private final RootNode _dataModelRoot = new RootNode();
	/**
	 * The root node for the Part hierarchy that matches the data model hierarchy. This field can be changed from the
	 * predefined instance to any developer needs that matches the IRootPart requirements.
	 */
	private IRootPart _partModelRoot = null;
	/**
	 * The list of Parts to show on the viewer. This is the body section that is scrollable. This instance is shared
	 * during the <code>collaboration2View()</code> phase to use less memory and avoid copying references from list to
	 * list during the generation process.
	 */
	private final List<IAndroidPart> _dataSectionParts = new Vector<IAndroidPart>(100);
	/** Flag used to do not launch more update events when there is one pending. */
	private boolean _pending = false;
	protected int refreshTime = -1;

	// - C O N S T R U C T O R - S E C T I O N ................................................................
	public MVCDataSource( final DataSourceLocator locator, final String variant, final IPartFactory factory, final Bundle extras ) {
		super();
		_locator = locator;
		_variant = variant;
		_partFactory = factory;
		this._extras = extras;
	}

	// - M E T H O D - S E C T I O N ..........................................................................

	// --- I D A T A S O U R C E   I N T E R F A C E
	public String getVariant() {
		return _variant;
	}

	public IPartsDataSource setVariant( final String variant ) {
		_variant = variant;
		return this;
	}

	public Bundle getExtras() {
		return _extras;
	}

	@Override
	public DataSourceLocator getDataSourceLocator() {
		return _locator;
	}

	public void cleanup() {
		_dataModelRoot.clean();
		// Clear the listener event link from the discarded Parts.
		//		cleanLinks(_dataSectionParts);
		_dataSectionParts.clear();
		// And add back the initial spinner.
		_dataSectionParts.add(new OnLoadSpinnerPart(new Separator()));
	}

	/**
	 * This method checks if the DataSource is compatible with caching and if this is the case checks if there are
	 * contents already cached so we can avoid to regenerate the model again. Use the counter of the children instead the
	 * <code>isEmpty</code> because that function does not represent the content value for RootNodes. This is a reported
	 * BUG.
	 * @return
	 */
	public boolean isCached() {
		if (_shouldBeCached)
			if (_dataModelRoot.getChildren().size() > 0)
				return true;
		return false;
	}

	/**
	 * Get the current cache selected state. This is used internally to do some checks.
	 * @return
	 */
	public boolean isCacheable() {
		return _shouldBeCached;
	}

	/**
	 * Sets the cacheable state for this DataSource. By default the cache state is <code>false</code> so no sources are
	 * caches. But in some cases the developer can speed up the model generation process and made it suitable for single
	 * initialization and caching. Use this setter to set the right state.
	 * @param cachestate new cache state for this data source. Affects at new source registrations with this same inique
	 *                   identifier.
	 * @return this same instance to allow functional programming.
	 */
	public IPartsDataSource setCacheable( final boolean cachestate ) {
		this._shouldBeCached = cachestate;
		return this;
	}

	public IPartsDataSource setRefreshTime( final int time ) {
		this.refreshTime = time;
		return this;
	}

	/**
	 * This is the single way to add more content to the DataSource internal model representation. Encapsulating this
	 * functionality on this method we make sure that the right events are generated and the model is properly updated and
	 * the renderization process will work as expected.
	 * @param newnode a new node to be added to the contents of the root point of the model.
	 * @return this IDataSource instance to allow functional coding.
	 */
	public IPartsDataSource addModelContents( final ICollaboration newnode ) {
		_dataModelRoot.addChild(newnode);
		// Optimization - If the event is already launched and not processed do not launch it again.
		if (_pending) return this;
		else {
			// Fire the model structure change event. This processing is done on the background and on the UI thread.
			_pending = true;
			AbstractPagerFragment._uiExecutor.submit(() -> {
				// Notify the Adapter that the Root node has been modified to regenerate the collaboration2View.
				propertyChange(new PropertyChangeEvent(this
						, SystemWideConstants.events.EVENTSTRUCTURE_NEWDATA.name(), newnode, _dataModelRoot));
				_pending = false;
			});
			return this;
		}
	}

	public IPartsDataSource addModelContents( final ICollaboration newnode, final boolean forceEvent ) {
		_dataModelRoot.addChild(newnode);
		if (forceEvent)
			//			if(_pending)
			AbstractPagerFragment._uiExecutor.submit(() -> {
				// Notify the Adapter that the Root node has been modified to regenerate the collaboration2View.
				propertyChange(new PropertyChangeEvent(this
						, SystemWideConstants.events.EVENTSTRUCTURE_NEWDATA.name(), newnode, _dataModelRoot));
				_pending = false;
			});
		return this;
	}

	public abstract void collaborate2Model();

	public List<IAndroidPart> getDataSectionContents() {
		return _dataSectionParts;
	}

	/**
	 * This method can be customized by developers to change the features implemented by the <code>IRootPart</code>. The
	 * library provides an implementation but the code is open to make replacements at the key points to enhance
	 * flexibility on the use of the library. This method is called whenever the root part container is still undefined
	 * and calls any inherited implementation that defines a new instance for this nose. The internal creation method
	 * will generate a <code>@link{RootAndroidPart}</code> instance that is suitable for most of developments.
	 * @return a new instance of a <code>IRootPart</code> interface to be used as the root for the part hierarchy.
	 */
	public IRootPart createRootPart() {
		return new RootPart(_dataModelRoot, _partFactory);
	}

	public RootNode getRootModel() {
		return this._dataModelRoot;
	}

	public IPartFactory getFactory() {
		return this._partFactory;
	}

	/**
	 * Add an spinner at the first position on the Part list to signal that we are doing som processing. If the
	 * datasource is cached it should not have any effect since the model is already generated and we should not have
	 * to wait for it.
	 */
	public void startOnLoadProcess() {
		if (!isCached()) {
			_dataSectionParts.add(new OnLoadSpinnerPart(new Separator()));
		}
	}

	/**
	 * After the model is created we have to transform it into the Part list expected by the DataSourceAdapter.
	 * The Part creation is performed by the corresponding PartFactory we got at the DataSource creation.
	 * <p>
	 * We transform the model recursively and keeping the already available Part elements. We create a
	 * duplicated of the resulting Part model and we move already available parts from the current model to the new model
	 * or create new part and finally remove what is left and unused.
	 * This new implementation will use partial generation to split and speed up this phase.
	 */
	private void transformModel2Parts() {
		logger.info(">> [MVCDataSource.transformModel2Parts]");
		// Check if we have already a Part model.
		// But do not forget to associate the new Data model even if the old exists.
		if (null == _partModelRoot) {
			_partModelRoot = createRootPart();
		}
		_partModelRoot.setRootModel(_dataModelRoot);

		logger.info("-- [MVCDataSource.transformModel2Parts]> Initiating the refreshChildren() for the Model Root");
		// Intercept any exception on the creation of the model but do not cut the progress of the already added items.
		try {
			//			_dataSectionParts.clear();
			_partModelRoot.refreshChildren();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		logger.info("<< [MVCDataSource.transformModel2Parts]> _dataSectionParts.size: {}", _dataSectionParts.size());
	}

	// --- P R O P E R T Y C H A N G E R   I N T E R F A C E

	/**
	 * This method is called whenever there is an event from any model change or any Part interaction. There are two
	 * groups of events, <b>structural</b> that change the model structure and contents and that require a full
	 * regeneration of all the transformations and <b>content</b> that can change the list of elements to be visible at
	 * this point in time but that do not change the initial structure. The contents can happen from changes on the model
	 * data or by interactions on the Parts that have some graphical impact.
	 * <p>
	 * If the model structure changes we should recreate the Model -> Part transformation and generate another Part tree
	 * with Parts matching the current model graph. At this transformation we can transform any data connected structure
	 * real or virtual to a hierarchy graph with the standard parent-child structure. We use the
	 * <code>collaborate2Model()</code> as a way to convert internal data structures to a hierarchy representation on a
	 * point in time. We isolate internal model ways to deal with data and we can optimize for the Part hierarchy without
	 * compromising thet model flexibility.
	 * <p>
	 * If the contents change we only should run over the Part tree to make the transformation to generate a new Part list
	 * for all the new visible and renderable items. This is performed with the <code>collaborate2View()</code> method for
	 * any Part that will then decide which of its internal children are going to be referenced for the collaborating list
	 * of Parts. This is the right place where to set up programmatic filtering or sorting because at this point we can
	 * influence the output representation for the model instance. We can also decorate the resulting Part list breaking
	 * the one to one relationship between a model instance and a Part instance.
	 * <p>
	 * After the models changes we should send a message to the <code>DataSourceAdapter</code> to refresh the graphical
	 * elements and change the display. <code>DataSource</code> instances do not have a reference to the Adapter nor to
	 * the Fragment that created them but during the creation process the Adapter installed a listener to get a copy of
	 * the events sent by all its datasources. So we can sent that message by sending again another type of message
	 * related to the need for the display for update, the <code>EVENTADAPTER_REQUESTNOTIFYCHANGES</code>
	 * @param event the event to be processed. Event have a property name that is used as a selector.
	 */
	@Override
	public synchronized void propertyChange( final PropertyChangeEvent event ) {
		logger.info(">> [MVCDataSource.propertyChange]> Processing Event: {}", event.getPropertyName());

		//--- C O N T E N T   E V E N T S
		// The expand/collapse state has changed.
		if (SystemWideConstants.events.valueOf(event.getPropertyName()) ==
				SystemWideConstants.events.EVENTCONTENTS_ACTIONEXPANDCOLLAPSE) {
			//			cleanLinks(_dataSectionParts);
			synchronized (_dataSectionParts) {
				_dataSectionParts.clear();
				_partModelRoot.collaborate2View(_dataSectionParts);
			}
		}

		//--- S T R U C T U R E   E V E N T S
		if (SystemWideConstants.events.valueOf(event.getPropertyName()) ==
				SystemWideConstants.events.EVENTSTRUCTURE_NEWDATA) {
			this.transformModel2Parts();
			//			cleanLinks(_dataSectionParts);
			synchronized (_dataSectionParts) {
				_dataSectionParts.clear();
				_partModelRoot.collaborate2View(_dataSectionParts);
			}
			// TODO - I think there is missing the action to update the listview. Trying with this messsage.
			firePropertyChange(SystemWideConstants.events.EVENTADAPTER_REQUESTNOTIFYCHANGES.name(), null, null);
		}
		if (SystemWideConstants.events.valueOf(event.getPropertyName()) ==
				SystemWideConstants.events.EVENTSTRUCTURE_DOWNLOADDATA) {
			this.transformModel2Parts();
			//			cleanLinks(_dataSectionParts);
			synchronized (_dataSectionParts) {
				_dataSectionParts.clear();
				_partModelRoot.collaborate2View(_dataSectionParts);
			}
		}

		//--- R E F R E S H   E V E N T S
		if (SystemWideConstants.events.valueOf(event.getPropertyName()) ==
				SystemWideConstants.events.EVENTSTRUCTURE_REFRESHDATA) {
			collaborate2Model();
			this.transformModel2Parts();
			//			cleanLinks(_dataSectionParts);
			synchronized (_dataSectionParts) {
				_dataSectionParts.clear();
				_partModelRoot.collaborate2View(_dataSectionParts);
			}
		}

		//--- A D A P T E R   E V E N T S
		//		if (SystemWideConstants.events
		//				.valueOf(event.getPropertyName()) == SystemWideConstants.events.EVENTADAPTER_REQUESTNOTIFYCHANGES) {
		////			this.fireStructureChange(SystemWideConstants.events.EVENTADAPTER_REQUESTNOTIFYCHANGES.name(), event.getOldValue(),
		////					event.getNewValue());
		//			logger.info("<< [MVCDataSource.propertyChange]");
		//			return;
		//		}
		// Send up the event to the DataSourceAdapter but be sure to run any display changes on the UI main thread.
		this.fireStructureChange(SystemWideConstants.events.EVENTADAPTER_REQUESTNOTIFYCHANGES.name(), event.getOldValue(),
				event.getNewValue());
	}

	protected void cleanLinks( final List<IAndroidPart> partList ) {
		for (IAndroidPart part : partList) {
			if (part.getModel() instanceof AbstractPropertyChanger)
				((AbstractPropertyChanger) part.getModel()).removePropertyChangeListener(part);
		}
		partList.clear();
	}

	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer("MVCDataSource [");
		buffer.append("Identifier: ").append(getDataSourceLocator().getIdentity());
		buffer.append("]");
		return buffer.toString();
	}


	public static class OnLoadSpinnerPart extends AbstractAndroidPart {

		public OnLoadSpinnerPart( final ICollaboration model ) {
			super(model);
		}

		@Override
		public long getModelId() {
			return 0;
		}

		@Override
		public AbstractRender selectRenderer() {
			return new OnLoadSpinnerRender(this, getActivity());
		}
	}

	public static class OnLoadSpinnerRender extends AbstractRender {
		private ProgressBar progress = null;
		private TextView progressCounter = null;

		private Instant _elapsedTimer = null;
		private CountDownTimer _timer = null;

		public OnLoadSpinnerRender( final AbstractPart newPart, final Activity context ) {
			super(newPart, context);
		}

		// --- I R E N D E R   I N T E R F A C E
		@Override
		public void initializeViews() {
			//			super.initializeViews();
			progress = (ProgressBar) _convertView.findViewById(R.id.progress);
			//			progress.
			progressCounter = (TextView) _convertView.findViewById(R.id.progressCounter);
			_elapsedTimer = Instant.now();
			_timer = new CountDownTimer(TimeUnit.DAYS.toMillis(1), TimeUnit.MILLISECONDS.toMillis(10)) {
				@Override
				public void onFinish() {
					progressCounter.setText(generateTimeString(_elapsedTimer.getMillis()));
					progressCounter.invalidate();
				}

				@Override
				public void onTick( final long millisUntilFinished ) {
					progressCounter.setText(generateTimeString(_elapsedTimer.getMillis()));
					progressCounter.invalidate();
				}
			}.start();
		}

		@Override
		public void updateContent() {
			//			super.updateContent();
			//			progressCounter.setText("10 sec");
			Animation rotation = AnimationUtils.loadAnimation(getContext(), R.anim.clockwise_rotation);
			rotation.setRepeatCount(Animation.INFINITE);
			progress.startAnimation(rotation);
		}

		@Override
		public int accessLayoutReference() {
			return R.layout.onload_spinner;
		}

		/**
		 * Displays an string in the format "nh nm ns" that is the number of seconds from the start point that is the value
		 * received as the parameter and the current instant on time.
		 */
		protected String generateTimeString( final long millis ) {
			try {
				final long elapsed = Instant.now().getMillis() - millis;
				final DateTimeFormatterBuilder timeFormatter = new DateTimeFormatterBuilder();
				if (elapsed > TimeUnit.HOURS.toMillis(1)) {
					timeFormatter.appendHourOfDay(2).appendLiteral("h ");
				}
				if (elapsed > TimeUnit.MINUTES.toMillis(1)) {
					timeFormatter.appendMinuteOfHour(2).appendLiteral("m ").appendSecondOfMinute(2).appendLiteral("s");
				} else timeFormatter.appendSecondOfMinute(2).appendLiteral("s");
				return timeFormatter.toFormatter().print(new Instant(elapsed));
			} catch (final RuntimeException rtex) {
				return "0m 00s";
			}
		}
	}
}

// - UNUSED CODE ............................................................................................
//[01]
