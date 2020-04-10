package org.dimensinfin.android.mvc.controller;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;

import org.dimensinfin.android.mvc.datasource.IDataSource;
import org.dimensinfin.android.mvc.domain.IRender;
import org.dimensinfin.android.mvc.domain.IContainer;
import org.dimensinfin.android.mvc.factory.IControllerFactory;
import org.dimensinfin.android.mvc.interfaces.IUniqueModel;
import org.dimensinfin.core.domain.EEvents;
import org.dimensinfin.core.domain.EventEmitter;
import org.dimensinfin.core.domain.IntercommunicationEvent;
import org.dimensinfin.core.interfaces.ICollaboration;
import org.dimensinfin.core.interfaces.IEventEmitter;
import org.dimensinfin.core.interfaces.IEventReceiver;
import org.dimensinfin.core.interfaces.IExpandable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * This class will implement the core Android interaction controller on the classic pattern Model-View-Controller
 * special and generic implementation into a library for Android native projects. This controller pattern will be the
 * intermediate connection node between the free graph composition of the Model and the final Visual Controller list
 * that will connect the Model and the Render inside the View containers used in the Android interface.
 *
 * On version 4.0 I have replaced the old GEF Part concept by the traditional Controller and started to add unit test
 * code and replaced old style java code practices by the new advanced code techniques and refactorings.
 *
 * @author Adam Antinoo
 * @since 4.0.0
 */
public abstract class AndroidController<M extends ICollaboration> implements IAndroidController<M> {
	/** This is the public logger that should be used by all the Controllers. */
	protected static final Logger logger = LoggerFactory.getLogger(AndroidController.class);

	// - F I E L D - S E C T I O N
	/** This field caches the factory that is set during the construction. */
	private final IControllerFactory factory;
	/** Place to store the model handler delegate so the model class is automatically casted. */
	private ControllerAdapter<M> delegatedController;
	/** List of children of the hierarchy. */
	private List<IAndroidController> children = new ArrayList<>();
	private boolean orderedActive = false; // If the contents should be returned ordered or not

	private String renderMode = "-DEFAULT-"; // Holds the type of the render to be used on this instance.
	private View viewCache; // Caches the render generated view used to the Adapter so it can be reused multiple times.
	private IEventEmitter eventController = new EventEmitter();
	private IDataSource dataSource;

	// - C O N S T R U C T O R S
	private AndroidController( @NonNull final ControllerAdapter<M> delegate, @NonNull final IControllerFactory factory ) {
		this.delegatedController = Objects.requireNonNull(delegate);
		this.factory = Objects.requireNonNull(factory);
	}

	public AndroidController( @NonNull final M model, @NonNull final IControllerFactory factory ) {
		this(new ControllerAdapter<M>(model), factory);
	}

	// - G E T T E R S   &   S E T T E R S
	@Override
	public M getModel() {
		return this.delegatedController.getModel();
	}

	@Override
	public View getViewCache() {
		return viewCache;
	}

	@Override
	public AndroidController setViewCache( final View viewCache ) {
		this.viewCache = viewCache;
		return this;
	}

	@Override
	public boolean isOrderedActive() {
		return this.orderedActive;
	}

	public AndroidController setOrderedActive( final boolean orderedActive ) {
		this.orderedActive = orderedActive;
		return this;
	}

	public String getRenderMode() {
		return renderMode;
	}

	public AndroidController setRenderMode( final String renderMode ) {
		this.renderMode = renderMode;
		return this;
	}

	/**
	 * Optimized process to generate the list of Controllers that should end on the render graphical process. While we are
	 * collecting the data we are feeding it on the final collection list so at the end we have the data required by the
	 * adapter to be render on the device screen.
	 *
	 * There are configuration operations over the result of the children nodes of a controller. The first one is the
	 * ordering of the results that can be controlled by the use of the <code>Comparable</code> interface to set a default
	 * ordering. Ordering then can be activated with a flag on each of the controllers and the code is kept simple and
	 * with no dependencies.
	 *
	 * The second configurable operation is the filtering. This comes from the UI requirement and should trim out any node
	 * not matching the filter restrictions. Filtering is a such complex task that will require a complete
	 * reimplementation for this method and the search for a pattern algorithm suitable for empty node trimming while not
	 * removing intermediate nodes not matching the filter.
	 *
	 * @param contentCollector the list where we are collecting the Controllers visible for rendering.
	 */
	@Override
	public void collaborate2View( final List<IAndroidController> contentCollector ) {
		logger.info(">< [AndroidController.collaborate2View]> Collaborator: {}", this.getClass().getSimpleName());
		// If the node is expanded then give the children the opportunity to also be added.
		if (this.getModel() instanceof IExpandable) {
			// --- This is the section that is different for any AndroidController.
			List<IAndroidController> ch = this.orderingFeature(this.getChildren());
			logger.info("-- [AndroidController.collaborate2View]> Collaborator children: {}", ch.size());
			// --- End of policies
			// Add this node to the list of controllers only if it should be visible.
			if (this.isVisible()) contentCollector.add(this);
			if (((IExpandable) this.getModel()).isExpanded())
				for (IAndroidController controller : ch) {
					controller.collaborate2View(contentCollector);
				}
		} else if (this instanceof IContainer) {
			if (this.isVisible()) contentCollector.add(this);
			if (((IContainer) this).wants2Collaborate()) {
				List<IAndroidController> ch = this.orderingFeature(this.getChildren());
				for (IAndroidController controller : ch) {
					controller.collaborate2View(contentCollector);
				}
			}
		} else if (this.isVisible()) contentCollector.add(this);
	}

	/**
	 * If the ordering flag is set then order the children and use the final ordered list to continue the rendering of the
	 * nodes to the list container.
	 *
	 * @param childrenList the list of elements to be sorted if the flag is set to true.
	 */
	public List<IAndroidController> orderingFeature( final List<IAndroidController> childrenList ) {
		if (this.isOrderedActive()) Collections.sort(childrenList);
		return childrenList;
	}

	/**
	 * This is the ain switch to be implemented on each controller to decide if the node and its contents are visible at a
	 * determinate point in time. It can happen that a node will have to be rendered on a particular time but should not
	 * be visible at others and this is determined by an specific logic and not by some tests done over some set of
	 * fields.
	 *
	 * With this implementation most of the render decision is set for each controller and not generically and controlled
	 * by fields.
	 */
	@Override
	public boolean isVisible() {
		return true;
	}

	/**
	 * The refresh process should optimize the reuse of the already available Parts. We should check for model identity on
	 * the already available parts to be able to reuse one of them. So once we have a model item we search on the list of
	 * available parts for one of them containing a model being that same instance. If found we reuse the
	 * AndroidController. Otherwise we create a new AndroidController for this model node and continue the transformation
	 * process.
	 *
	 * It is a recursive process that it is repeated for each one of the nodes added to the already processing structure
	 * while the nodes have any number of children.
	 *
	 * The process gets the Model attached to the AndroidController we are working with. Then finds the Model children to
	 * reconstruct their parts if they do not have them already and match them to the current list of Parts already
	 * connected to the AndroidController in the work bench. Any discrepancies create or delete the required Parts. Then
	 * we start again this process with the first of the children until all the model nodes have been processed.
	 *
	 * During the transformation process we use the <code>collaborate2Model(final String variant)</code> to generate a
	 * fresh list of nodes from the model instance. We use the <b>Variant</b> to allow the model to generate different
	 * sets of new model instances depending on that parameter. The flexibility of this approach allows a single model
	 * object to generate different outputs for each variant received and this value is set for each different Activity
	 * Page.
	 */
	public void refreshChildren() {
		// Get the new list of children for this model node. Use the Variant for generation discrimination.
		final List<ICollaboration> firstLevelNodes = this.getModel().collaborate2Model(this.getControllerFactory().getVariant());
		if (firstLevelNodes.isEmpty()) return;
		// Create the model-controller current map to check the elements missing.
		final HashMap<ICollaboration, IAndroidController> currentMap = new HashMap<>(firstLevelNodes.size());
		for (IAndroidController control : this.getChildren())
			currentMap.put((ICollaboration) control.getModel(), control);
		// Create the new children list ot be able to delete nodes.
		final List<IAndroidController> newChildrenList = new ArrayList<>(firstLevelNodes.size());
		// Check all the model instances have a matching AndroidController instance.
		for (ICollaboration modelNode : firstLevelNodes) {
			// Search for the model instance on the current controller map.
			if (currentMap.containsKey(modelNode)) {
				currentMap.get(modelNode).refreshChildren();
				newChildrenList.add(currentMap.get(modelNode));
			} else {
				// The controller is non existent for this model node. Create a new one from the factory.
				logger.info("-- [AndroidController.refreshChildren]> New AndroidController for Model: {}",
				            modelNode.getClass().getSimpleName());
				final IAndroidController newController = this.getControllerFactory().createController(modelNode);
				newController.refreshChildren();
				newChildrenList.add(newController);
			}
		}
		// Replace the new children list.
		this.clean();
		this.addChildren(newChildrenList);
	}

	/**
	 * The model identifier can be implemented by default by getting the hashcode for the model or if the model implements the
	 * additional interface to provide a unique adapter identifier then the implementation may call that additional method.
	 */
	public long getModelId() {
		if (this.getModel() instanceof IUniqueModel)
			return ((IUniqueModel) this.getModel()).getUniqueModelIdentifier();
		return this.getModel().hashCode();
	}

	public IDataSource getDataSource() {
		return this.dataSource;
	}

	public AndroidController setDataSource( final IDataSource dataSource ) {
		this.dataSource = dataSource;
		return this;
	}

	// - I A N D R O I D C O N T R O L L E R   I N T E R F A C E

	/**
	 * This is the call that should create and inflate the render UI view. During all other processes the Context is not
	 * needed not used but this is the right moment to get to the Android system and instantiate a new UI element. The
	 * Context can be discarded after this moment since the view is going to be cached and if needed to be constructed
	 * again this call will be issued another time.
	 *
	 * @param context the Activity UI context to use to locate the inflater and do the action.
	 */
	public abstract IRender buildRender( final Context context );

	/**
	 * The factory is set on all the Controllers during the creation time by the factory itself. This allows to construct
	 * any Model supported by the factory from any Controller created by that Factory.
	 */
	public IControllerFactory getControllerFactory() {
		return this.factory;
	}

	public List<IAndroidController> getChildren() {
		if (children == null) this.children = new ArrayList<>(2);
		return children;
	}

	// - D E L E G A T E S - C H I L D R E N
	public void addChild( final IAndroidController child ) {
		this.getChildren().add(child);
	}

	public void addChildren( final List<IAndroidController> modelList ) {
		for (IAndroidController node : modelList)
			this.addChild(node);
	}

	public void clean() {
		this.getChildren().clear();
	}

	@Override
	public int compareTo( @NonNull final Object target ) {
		if (target instanceof IAndroidController) {
			final IAndroidController castedTarget = (IAndroidController) target;
			return this.getModel().compareTo(castedTarget.getModel());
		} else return -1;
	}

	// - I E V E N T E M I T T E R   I N T E R F A C E

	@Override
	public void addEventListener( final IEventReceiver listener ) {this.eventController.addEventListener(listener);}

	@Override
	public void removeEventListener( final IEventReceiver listener ) {this.eventController.removeEventListener(listener);}

	@Override
	public boolean sendChangeEvent( final IntercommunicationEvent event ) {return this.eventController.sendChangeEvent(event);}

	@Override
	public boolean sendChangeEvent( final String eventName ) {return this.eventController.sendChangeEvent(eventName);}

	@Override
	public boolean sendChangeEvent( final String eventName, final Object origin ) {
		return this.eventController.sendChangeEvent(eventName, origin);
	}

	@Override
	public boolean sendChangeEvent( final String eventName, final Object origin, final Object oldValue, final Object newValue ) {
		return this.eventController.sendChangeEvent(eventName, origin, oldValue, newValue);
	}

	public void notifyDataModelChange() {
		this.viewCache = null; // Clean the view cache to force recreation.
		this.sendChangeEvent(EEvents.EVENT_REFRESHDATA.name());
	}

	protected void notifyDataModelChange( final EEvents event ) {
		this.viewCache = null; // Clean the view cache to force recreation.
		this.sendChangeEvent(event.name());
	}

	// - M E T H O D - S E C T I O N
	protected void invalidate() {
		this.setViewCache(null);
		this.notifyDataModelChange();
	}
}
