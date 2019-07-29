package org.dimensinfin.android.mvc.datasource;

import java.beans.PropertyChangeListener;
import java.util.List;
import java.util.concurrent.Future;

import org.dimensinfin.android.mvc.controller.IAndroidController;

public interface IDataSource extends PropertyChangeListener {
	DataSourceLocator getDataSourceLocator();

	boolean needsCaching();


	void addPropertyChangeListener(final PropertyChangeListener newListener);

	/**
	 * This is the action to signal the data source to start searching for its data to process it and generate
	 * the contents for the root model list. The model generated is still not processed and no controllers are created until
	 * the viewer requires them. This call is to create any internal data source data structures that are to be used when extracting the
	 * header and data section models.
	 */
	void prepareModel();

	/**
	 * With this method the initializer will set a synchronization trap that will force any caller that requires the model contents to
	 * wait until the initialisation is completed and the model root list is populated.
	 */
//	void setSynchronizer(final Future<IDataSource> synchronizer);

	/**
	 * This method will use the data source models to generate the contents for the header section. The model will be transformed to the list of
	 * Controllers so the final header view will have the list of render views to be used on the display.
	 *
	 * @return the list of header controllers to be rendered.
	 */
	List<IAndroidController> getHeaderSectionContents();

	/**
	 * This is the method to generate the list of controllers to be used on the list rendering. The list returned is only for the controllers that are
	 * visible and then that collaborate to the view.
	 *
	 * @return the list of data section controllers to be rendered.
	 */
	List<IAndroidController> getDataSectionContents();
	void collaborate2Model();
}
