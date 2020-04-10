//  PROJECT:        net.sf.vgap4.assistant.ui
//  FILE NAME:      $Id: InvalidContentException.java 309 2009-05-12 15:33:09Z boneymen $
//  LAST UPDATE:    $Date: 2009-05-12 17:33:09 +0200 (mar, 12 may 2009) $
//  RELEASE:        $Revision: 309 $
//  AUTHORS:        Lima Delta Delta (LDD) - boneymen@netscape.com
//  COPYRIGHT:      (c) 2008 by LDD Game Development Spain, all rights reserved.

package org.dimensinfin.core.exceptions;

//- IMPORT SECTION .........................................................................................
import org.xml.sax.SAXException;

// - CLASS IMPLEMENTATION ...................................................................................
public class InvalidContentException extends SAXException {
	// - S T A T I C - S E C T I O N ..........................................................................
	private static final long serialVersionUID = -8228699753177913838L;

	// - F I E L D - S E C T I O N ............................................................................
	// - C O N S T R U C T O R - S E C T I O N ................................................................
	public InvalidContentException (String message) {
		super(message);
	}

	// - M E T H O D - S E C T I O N ..........................................................................
}
// - UNUSED CODE ............................................................................................
