package org.dimensinfin.android.mvc.activity;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.dimensinfin.android.mvc.controller.IAndroidController;
import org.dimensinfin.android.mvc.datasource.IDataSource;
import org.dimensinfin.android.mvc.datasource.MVCDataSource;
import org.dimensinfin.android.mvc.interfaces.IRender;

import androidx.annotation.NonNull;
import androidx.annotation.UiThread;

import org.dimensinfin.core.domain.IntercommunicationEvent;
import org.dimensinfin.core.interfaces.IEventReceiver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MapContentAdapter implements IEventReceiver {
	private static Logger logger = LoggerFactory.getLogger(MapContentAdapter.class);
	protected static final Handler _handler = new Handler(Looper.getMainLooper());

	// - F I E L D - S E C T I O N
	/** The Activity where all this structures belong and that is used as the core display context. */
	private Context context = null;
	/** An instance for a source of data that will provide the list of <b>Parts</b> to be used to construct the Views. */
	private MVCDataSource datasource = null;
	/** The container where the contents should be rendered. Adapter does the rendering of the views directly to the layout container. */
	private ViewGroup mapContainer;

	// - C O N S T R U C T O R S
	public MapContentAdapter(@NonNull  final IPagerFragment fragment, @NonNull final IDataSource datasource, @NonNull final ViewGroup container) {
		this.context = fragment.getActivityContext();
		this.datasource = (MVCDataSource) datasource;
		this.datasource.addEventListener(this); // Connect the listener to the data source events.
		this.mapContainer = container;
	}

	public void collaborateData() {
		if (null != this.datasource) this.datasource.collaborate2Model();
	}

	public void notifyDataSetChanged() {
		this.generateMapContents(this.datasource.getDataSectionContents());
	}

	@UiThread
	protected void generateMapContents(final List<IAndroidController> mapControllers) {
		logger.info(">> [CanvasPagerFragment.generateHeaderContents]");
		// Now create the views and add them to the map container.
		_handler.post(() -> {
			this.mapContainer.removeAllViews();
			for (IAndroidController controller : mapControllers) {
				if (controller instanceof IAndroidController) addView2Map(controller);
			}
			this.mapContainer.setVisibility(View.VISIBLE);
		});
		logger.info("<< [CanvasPagerFragment.generateHeaderContents]");
	}

	private void addView2Map(final IAndroidController target) {
		logger.info(">> [CanvasPagerFragment.addViewtoHeader]");
		try {
			final IRender holder = target.buildRender(context);
//			holder.initializeViews();
			holder.getView(); // Force view creation and initialisation.
			holder.updateContent();
			final View hv = holder.getView();
			this.mapContainer.addView(hv);
		} catch (final RuntimeException rtex) {
			logger.info("RTEX [CanvasPagerFragment.addView2Header]> Problem generating view for: {}", target.getClass().getCanonicalName());
			logger.info("RTEX [CanvasPagerFragment.addView2Header]> RuntimeException. {}", rtex.getMessage());
			rtex.printStackTrace();
			Toast.makeText(context
					, "RTEX [CanvasPagerFragment.addView2Header]> RuntimeException. " + rtex.getMessage()
					, Toast.LENGTH_LONG).show();
		}
		logger.info("<< CanvasPagerFragment.addViewtoHeader");
	}

	// - P R O P E R T Y C H A N G E L I S T E N E R   I N T E R F A C E
	@Override
	public void receiveEvent(@NonNull final IntercommunicationEvent event) {
		logger.info(">< [MapContentAdapter.receiveEvent]> Processing Event: {}", event.getPropertyName());
		this.notifyDataSetChanged();
	}
}
