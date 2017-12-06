//	PROJECT:        NeoCom.Android (NEOC.A)
//	AUTHORS:        Adam Antinoo - adamantinoo.git@gmail.com
//	COPYRIGHT:      (c) 2013-2017 by Dimensinfin Industries, all rights reserved.
//	ENVIRONMENT:		Android API16.
//	DESCRIPTION:		Application to get access to CCP api information and help manage industrial activities
//									for characters and corporations at Eve Online. The set is composed of some projects
//									with implementation for Android and for an AngularJS web interface based on REST
//									services on Sprint Boot Cloud.
package org.dimensinfin.android.mvc.datasource;

import org.dimensinfin.android.mvc.constants.SystemWideConstants;
import org.dimensinfin.android.mvc.core.AbstractAndroidPart;
import org.dimensinfin.android.mvc.interfaces.IPart;
import org.dimensinfin.android.mvc.interfaces.IPartFactory;
import org.dimensinfin.core.datasource.DataSourceLocator;
import org.dimensinfin.core.interfaces.IModelGenerator;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;

// - CLASS IMPLEMENTATION ...................................................................................
public class MVCDataSource extends SpecialDataSource {
	// - S T A T I C - S E C T I O N ..........................................................................
	private static final long serialVersionUID = -33788422150264209L;

	// - F I E L D - S E C T I O N ............................................................................

	// - C O N S T R U C T O R - S E C T I O N ................................................................
	public MVCDataSource(final DataSourceLocator locator, final IPartFactory partFactory,
	                     final IModelGenerator generator) {
		super(locator, partFactory);
		if (null == generator) throw new RuntimeException(
				"RTEX [MVCDataSource.<initialize>]> The Model Generator should not be empty.");
		_modelGenerator = generator;
	}

	// - M E T H O D - S E C T I O N ..........................................................................
//	/**
//	 * Set the RootPart and the sort element for it.
//	 */
//	@Override
//	public void createContentHierarchy() {
//		// Do it only if the RootPart is not already set.
//		if (null == _partModelRoot) {
//			_partModelRoot = new RootPart(_dataModelRoot, _partFactory)
//					.setSorting(NeoComApp.createPartComparator(AppWideConstants.comparators.COMPARATOR_NAME));
//		}
//		super.createContentHierarchy();
//	}

	@Override
	public ArrayList<AbstractAndroidPart> getHeaderParts() {
		return new ArrayList<AbstractAndroidPart>();
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
		if (SystemWideConstants.events
				.valueOf(event.getPropertyName()) == SystemWideConstants.events.EVENTSTRUCTURE_DOWNLOADDATA) {
			this.createContentHierarchy();

			_bodyParts = new ArrayList<IPart>();
			_bodyParts.addAll(_partModelRoot.collaborate2View());
			this.fireStructureChange(SystemWideConstants.events.EVENTADAPTER_REQUESTNOTIFYCHANGES.name(), event.getOldValue(),
					event.getNewValue());
			return;
		}
		if (SystemWideConstants.events
				.valueOf(event.getPropertyName()) == SystemWideConstants.events.EVENTSTRUCTURE_NEWDATA) {
			this.createContentHierarchy();

			_bodyParts = new ArrayList<IPart>();
			_bodyParts.addAll(_partModelRoot.collaborate2View());
			this.fireStructureChange(SystemWideConstants.events.EVENTADAPTER_REQUESTNOTIFYCHANGES.name(), event.getOldValue(),
					event.getNewValue());
			return;
		}
		if (SystemWideConstants.events
				.valueOf(event.getPropertyName()) == SystemWideConstants.events.EVENTADAPTER_REQUESTNOTIFYCHANGES) {
			this.fireStructureChange(SystemWideConstants.events.EVENTADAPTER_REQUESTNOTIFYCHANGES.name(), event.getOldValue(),
					event.getNewValue());
			return;
		}
		super.propertyChange(event);
	}
}

// - UNUSED CODE ............................................................................................
