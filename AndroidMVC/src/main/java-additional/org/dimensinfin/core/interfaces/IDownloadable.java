//	PROJECT:        corebase.model (CORE.M)
//	AUTHORS:        Adam Antinoo - adamantinoo.git@gmail.com
//	COPYRIGHT:      (c) 2013-2017 by Dimensinfin Industries, all rights reserved.
//	ENVIRONMENT:		Java 1.6.
//	DESCRIPTION:		Library that defines the model classes to implement the core for a GEF based
//									Model-View-Controller. Code is as neutral as possible and made to be reused
//									on all Java development projects.
//                  Added more generic code to develop other Model-View-Controller patterns.
package org.dimensinfin.core.interfaces;

/**
 * Downloadable objects are <code>IExpandable</code> nodes that can load its contents on demand. This requires
 * a different implementation from the generic expandable node because we should provide virtual answers to
 * requests as size or emptiness.
 */
// - INTERFACE IMPLEMENTATION ...............................................................................
public interface IDownloadable {
	public boolean isDownloaded ();

	public boolean isDownloading ();

	public IDownloadable setDownloaded (boolean downloadedstate);

	public IDownloadable setDownloading (final boolean downloading);
}
