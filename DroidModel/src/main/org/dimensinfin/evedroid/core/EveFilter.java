//	PROJECT:        EveIndustrialistModel (EVEI-M)
//	AUTHORS:        Adam Antinoo - adamantinoo.git@gmail.com
//	COPYRIGHT:      (c) 2013-2014 by Dimensinfin Industries, all rights reserved.
//	ENVIRONMENT:		JRE 1.7.
//	DESCRIPTION:		Data model to use on EVE related applications. Neutral code to be used in all enwironments.

package org.dimensinfin.evedroid.core;

// - IMPORT SECTION .........................................................................................
import java.util.ArrayList;

import org.dimensinfin.evedroid.model.Asset;

// - CLASS IMPLEMENTATION ...................................................................................
public class EveFilter {
	// - S T A T I C - S E C T I O N ..........................................................................

	// - F I E L D - S E C T I O N ............................................................................
	private ArrayList<Asset>	source;
	private WhereClause				where;

	// - C O N S T R U C T O R - S E C T I O N ................................................................
	public EveFilter() {
	}

	public EveFilter(final ArrayList<Asset> list, final WhereClause clause) {
		this.source = list;
		this.where = clause;
	}

	// - M E T H O D - S E C T I O N ..........................................................................
	public ArrayList<Asset> getResults() {
		final ArrayList<Asset> results = new ArrayList<Asset>();
		// Get an iterator on the source and perform the test for all elements.
		final java.util.Iterator<Asset> it = this.source.iterator();
		while (it.hasNext()) {
			final Asset asset = it.next();
			if (this.where.isFiltered(asset)) results.add(asset);
		}
		return results;
	}

}

// - UNUSED CODE ............................................................................................
