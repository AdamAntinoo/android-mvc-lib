//  PROJECT:        net.sf.vorg.vorgautopilot.command
//  FILE NAME:      $Id: IconGeneratorTest.java 174 2008-06-26 12:59:47Z boneymen $
//  LAST UPDATE:    $Date: 2008-06-26 14:59:47 +0200 (jue, 26 jun 2008) $
//  RELEASE:        $Revision: 174 $
//  AUTHORS:        Lima Delta Delta (LDD) - boneymen@netscape.com
//  COPYRIGHT:      (c) 2009 by LDD Game Development Spain, all rights reserved.

package org.dimensinfin.core.parser;

// - IMPORT SECTION .........................................................................................
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.logging.Logger;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.dimensinfin.core.interfaces.IModelStore;
import org.xml.sax.SAXException;

// - CLASS IMPLEMENTATION ...................................................................................
public class XMLFileParser extends AbstractStoreHandler {
	// - S T A T I C - S E C T I O N ..........................................................................
	private static final long serialVersionUID = -1593676021663133194L;
	private static Logger logger = Logger.getLogger("net.sf.vorg.vorgautopilot.parsers");

	// - F I E L D - S E C T I O N ............................................................................
	// - C O N S T R U C T O R - S E C T I O N ................................................................
	public XMLFileParser (final String targetInput) {
		super(targetInput);
	}

	// - M E T H O D - S E C T I O N ..........................................................................
	//	public InputTypes getType() {
	//		return InputTypes.XML;
	//	}

	public IModelStore getStore () {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Process the data contents of the configured VRTool file and parse the contents to load and update the
	 * data inside the Pilot Model.
	 */
	public boolean loadContents () {
		if (needsReload()) {
			try {
				System.out
						.println("------------------------------------------------------------------------------------------");
				System.out.println(Calendar.getInstance().getTime() + " - new scan run for configuration: " + getLocation());
				logger.fine("Model needs reload. Parsing again XML data.");
				final InputStream stream = openInput(inputReference);
				final SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
				parser.parse(stream, this);
				final byte[] localHash = computeHash();
				logger.fine("Computed hash for file [" + inputReference + "] is " + hexEncode(localHash));
				hash = localHash;
				lastUpdate = Calendar.getInstance();
				return true;
			} catch (final FileNotFoundException fnfe) {
				System.out.println("EEE - " + fnfe.getLocalizedMessage());
				//				setState(InputHandlerStates.ERROR, fnfe);
				return false;
				//			} catch (final DataLoadingException dle) {
				//				System.out.println("EEE - " + dle.getLocalizedMessage());
				//				setState(InputHandlerStates.ERROR, dle);
				//				return false;
			} catch (final ParserConfigurationException pce) {
				System.out.println("EEE - " + pce.getLocalizedMessage());
				//				setState(InputHandlerStates.ERROR, pce);
				return false;
			} catch (final SAXException saxe) {
				System.out.println("EEE - " + saxe.getLocalizedMessage());
				//				setState(InputHandlerStates.ERROR, saxe);
				return false;
			} catch (final IOException ioe) {
				System.out.println("EEE - " + ioe.getLocalizedMessage());
				//				setState(InputHandlerStates.ERROR, ioe);
				return false;
			}
		} else {
			System.out.println("------------------------------------------------------------------------------------------");
			System.out.println(Calendar.getInstance().getTime() + " - input route configuration not reloaded.");
			return false;
		}
	}

	//	@Override
	//	public ObjectInput prepareStorageInput() {
	//		// TODO Auto-generated method stub
	//		return null;
	//	}
	//
	//	@Override
	//	public ObjectOutput prepareStorageOutput() {
	//		// TODO Auto-generated method stub
	//		return null;
	//	}

	public boolean restore () {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean save () {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean uploadContents () {
		// TODO Auto-generated method stub
		return false;
	}

	protected InputStream openInput (final String ref) throws FileNotFoundException, IOException {
		InputStream input = null;
		//		if (getType() == InputTypes.XML) {
		if (true) {
			input = new BufferedInputStream(new FileInputStream(ref));
		}
		return input;
		//		throw new IOException("The input type is not specified or is not supported.");
	}
}
// - UNUSED CODE ............................................................................................
