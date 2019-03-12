package org.dimensinfin.android.mvc.model;

import android.support.annotation.NonNull;
import org.dimensinfin.android.mvc.interfaces.ICollaboration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Adam Antinoo
 */

// - CLASS IMPLEMENTATION ...................................................................................
public class DemoNode implements ICollaboration, Comparable<DemoNode> {
	// - S T A T I C - S E C T I O N ..........................................................................
	private static Logger logger = LoggerFactory.getLogger("DemoNode");

	// - F I E L D - S E C T I O N ............................................................................
	public String jsonClass = "DemoNode";

	// - C O N S T R U C T O R - S E C T I O N ................................................................
	public DemoNode() {
		super();
	}

	// - M E T H O D - S E C T I O N ..........................................................................
	//--- I C O L L A B O R A T I O N   I N T E R F A C E
	@Override
	public List<ICollaboration> collaborate2Model(final String variant) {
		return new ArrayList<>();
	}

	//--- G E T T E R S   &   S E T T E R S
	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer("DemoLabel [ ");
		buffer.append("jsonClass: ").append(jsonClass).append(" ");
		buffer.append("]");
		return buffer.toString();
	}
	@Override
	public int compareTo(@NonNull final DemoNode o) {
		return this.jsonClass.compareTo(o.jsonClass);
	}

}
// - UNUSED CODE ............................................................................................
//[01]
