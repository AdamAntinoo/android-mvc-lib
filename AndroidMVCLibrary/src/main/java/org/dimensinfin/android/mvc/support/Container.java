package org.dimensinfin.android.mvc.support;

import java.util.ArrayList;
import java.util.List;

import org.dimensinfin.core.interfaces.ICollaboration;

public class Container extends Separator implements IExpandable {
	private static final long serialVersionUID = -957283664928489030L;

	// - F I E L D - S E C T I O N
	private final List<ICollaboration> _contents = new ArrayList<>();
	private boolean _expanded = false;
	private boolean _renderIfEmpty = true;

	// - C O N S T R U C T O R - S E C T I O N
	public Container() {
		super();
	}

	public Container(final String title) {
		super(title);
	}

	// - M E T H O D - S E C T I O N
	public int addContent(final ICollaboration node) {
		this._contents.add(node);
		return _contents.size();
	}

//	public int addContentList(final List<ICollaboration> newcontents) {
//		for (ICollaboration node : newcontents)
//			addContent(node);
//		return _contents.size();
//	}

	public void clean() {
		this._contents.clear();
	}

	public List<ICollaboration> getContents() {
		return _contents;
	}

	public int getContentSize() {
		return this._contents.size();
	}

	// - I C O L L A B O R A T I O N   I N T E R F A C E

	/**
	 * Check if the <code>Container</code> has contents and then add all them to the model.
	 */
	@Override
	public List<ICollaboration> collaborate2Model(final String variant) {
		return this.getContents();
//		ArrayList<ICollaboration> results = new ArrayList<ICollaboration>();
//		results.addAll(this.getContents());
//		return results;
	}

	// - I E X P A N D A B L E   I N T E R F A C E
	public boolean toggleExpand() {
		this._expanded = !this._expanded;
		return this._expanded;
	}

	public boolean collapse() {
		this._expanded = false;
		return _expanded;
	}

	public boolean expand() {
		this._expanded = true;
		return _expanded;
	}

	public boolean isEmpty() {
		if (getContentSize() > 0)
			return true;
		else
			return false;
	}

	public boolean isExpanded() {
		return this._expanded;
	}

	public boolean isRenderWhenEmpty() {
		return this._renderIfEmpty;
	}

	public void setRenderWhenEmpty(final boolean renderWhenEmpty) {
		this._renderIfEmpty = renderWhenEmpty;
//		return this;
	}

	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer("Container [");
		buffer.append(super.toString()).append(" ");
		buffer.append("size: ").append(getContentSize()).append(" ");
		buffer.append(" ]");
		return buffer.toString();
	}
}
