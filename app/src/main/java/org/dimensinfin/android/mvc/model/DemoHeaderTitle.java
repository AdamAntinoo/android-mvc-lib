package org.dimensinfin.android.mvc.model;

import android.support.annotation.NonNull;
import org.dimensinfin.android.mvc.interfaces.ICollaboration;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DemoHeaderTitle implements ICollaboration, Serializable, Comparable<DemoHeaderTitle> {
	private static final long serialVersionUID = 7084637836405461264L;

	// - F I E L D - S E C T I O N
	private String name = "-APPLICATION NAME-";
	private String version = "-VERSION-";

	// - C O N S T R U C T O R - S E C T I O N
	public DemoHeaderTitle() {
	}

	public DemoHeaderTitle(@NonNull final String name, @NonNull final String version) {
		this.name = name;
		this.version = version;
	}

	//- M E T H O D - S E C T I O N
	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(final String version) {
		this.version = version;
	}

	// - I C O L L A B O R A T I O N   I N T E R F A C E
	@Override
	public List<ICollaboration> collaborate2Model(final String variant) {
		return new ArrayList<ICollaboration>();
	}

	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer("DemoHeaderTitle [");
		buffer.append(getName()).append(" - ").append(getVersion());
		buffer.append(" ]");
		return buffer.toString();
	}

	@Override
	public int compareTo(@NonNull final DemoHeaderTitle o) {
		return this.name.compareTo(o.name);
	}

}
