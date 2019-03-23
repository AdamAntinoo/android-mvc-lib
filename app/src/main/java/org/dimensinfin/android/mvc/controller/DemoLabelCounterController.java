package org.dimensinfin.android.mvc.controller;

import java.util.Hashtable;
import java.util.Hashtable;
import java.util.Hashtable;

import org.joda.time.Instant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Adam Antinoo
 */

public class DemoLabelCounterController extends DemoItemAndroidController {
	private static Logger logger = LoggerFactory.getLogger(DemoLabelCounterController.class);

	// - F I E L D - S E C T I O N

	// - C O N S T R U C T O R - S E C T I O N
	public DemoLabelCounterController() {
		super();
	}

	// - G E T T E R S   &   S E T T E R S
	// - M E T H O D - S E C T I O N
	@Override
	public String toString() {
		return new StringBuilder("DemoLabelCounterController [")
				.append("name: ").append(0)
				.append("]")
				.append("->").append(super.toString())
				.toString();
	}
}
