//	PROJECT:        corebase.model (CORE.M)
//	AUTHORS:        Adam Antinoo - adamantinoo.git@gmail.com
//	COPYRIGHT:      (c) 2013-2017 by Dimensinfin Industries, all rights reserved.
//	ENVIRONMENT:		Java 1.6.
//	DESCRIPTION:		Library that defines the model classes to implement the core for a GEF based
//									Model-View-Controller. Code is as neutral as possible and made to be reused
//									on all Java development projects.
//                  Added more generic code to develop other Model-View-Controller patterns.
package org.dimensinfin.core.model;

import org.dimensinfin.core.interfaces.ICollaboration;
import org.dimensinfin.core.interfaces.IExpandable;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

// - CLASS IMPLEMENTATION ...................................................................................
public class Container extends Separator implements IExpandable {
	// - S T A T I C - S E C T I O N ..........................................................................
	private static final long serialVersionUID = -957283664928489030L;

	// - F I E L D - S E C T I O N ............................................................................
	private final Vector<ICollaboration> _contents = new Vector<ICollaboration>();
	private boolean _expanded = false;
	private boolean _renderIfEmpty = true;

	// - C O N S T R U C T O R - S E C T I O N ................................................................
	public Container () {
		super();
		jsonClass = "Container";
	}

	public Container (final String title) {
		super(title);
		jsonClass = "Container";
	}

	//- M E T H O D - S E C T I O N ..........................................................................
	public int addContent (final ICollaboration node) {
		this._contents.add(node);
		return _contents.size();
	}
	public int addContentList (final List<ICollaboration> newcontents) {
		for(ICollaboration node: newcontents)
			addContent(node);
		return _contents.size();
	}

	public void clean () {
		_contents.clear();
	}

	/**
	 * Check if the <code>Container</code> has contents and then add all them to the model.
	 */
	@Override
	public List<ICollaboration> collaborate2Model (final String variant) {
		ArrayList<ICollaboration> results = new ArrayList<ICollaboration>();
		results.addAll(this.getContents());
		return results;
	}

	public boolean collapse () {
		_expanded = false;
		return _expanded;
	}

	public boolean expand () {
		_expanded = true;
		return _expanded;
	}

	public Vector<ICollaboration> getContents () {
		return _contents;
	}

	public int getContentSize () {
		return this._contents.size();
	}

	public boolean isEmpty () {
		if (getContentSize() > 0)
			return true;
		else
			return false;
	}

	public boolean isExpanded () {
		return _expanded;
	}

	public boolean isRenderWhenEmpty () {
		return _renderIfEmpty;
	}

	public IExpandable setRenderWhenEmpty (final boolean renderWhenEmpty) {
		_renderIfEmpty = renderWhenEmpty;
		return this;
	}

	@Override
	public String toString () {
		StringBuffer buffer = new StringBuffer("Container [");
		buffer.append(super.toString()).append(" ");
		buffer.append("size: ").append(getContentSize()).append(" ");
		buffer.append(" ]");
		return buffer.toString();
	}
}

// - UNUSED CODE ............................................................................................
