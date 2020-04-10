//  PROJECT:        net.sf.gef.core
//  FILE NAME:      $Id: AbstractXMLHandler.java 309 2009-05-12 15:33:09Z boneymen $
//  LAST UPDATE:    $Date: 2009-05-12 17:33:09 +0200 (mar, 12 may 2009) $
//  RELEASE:        $Revision: 309 $
//  AUTHORS:        Lima Delta Delta (LDD) - boneymen@netscape.com
//  COPYRIGHT:      (c) 2008 by LDD Game Development Spain, all rights reserved.

package org.dimensinfin.core.parser;

// - IMPORT SECTION .........................................................................................
import org.dimensinfin.core.exceptions.InvalidContentException;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

// - CLASS IMPLEMENTATION ...................................................................................
public abstract class AbstractXMLHandler extends DefaultHandler {
	// - S T A T I C - S E C T I O N ..........................................................................
	//	private static Logger	logger	= Logger.getLogger("net.sf");

	// - F I E L D - S E C T I O N ............................................................................

	// - C O N S T R U C T O R - S E C T I O N ................................................................
	public AbstractXMLHandler () {
	}

	// - M E T H O D - S E C T I O N ..........................................................................
	protected String validateNotNull (Attributes attributes, String attrName) throws InvalidContentException {
		return svalidateNotNull(attributes, attrName);
	}

	protected String validateNull (Attributes attributes, String attrName) throws InvalidContentException {
		return svalidateNull(attributes, attrName);
	}

	public static String svalidateNotNull (Attributes attributes, String attrName) throws InvalidContentException {
		String attr = attributes.getValue(attrName);
		String attrUp = attributes.getValue(attrName.toUpperCase());
		String attrLow = attributes.getValue(attrName.toLowerCase());
		if (null != attr) return attr;
		if (null != attrUp) return attrUp;
		if (null != attrLow) return attrLow;
		throw new InvalidContentException("The attribute [" + attrName + "] can not be empty or not defined.");
	}

	public static String svalidateNull (Attributes attributes, String attrName) throws InvalidContentException {
		String attr = attributes.getValue(attrName);
		String attrUp = attributes.getValue(attrName.toUpperCase());
		String attrLow = attributes.getValue(attrName.toLowerCase());
		if (null != attr) return attr;
		if (null != attrUp) return attrUp;
		if (null != attrLow) return attrLow;
		return attr;
	}
}

// - UNUSED CODE ............................................................................................
