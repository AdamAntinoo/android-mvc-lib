//  PROJECT:        net.sf.vorg.vorgautopilot.command
//  FILE NAME:      $Id: ProcessorApp.java,v $
//  LAST UPDATE:    $Date: 2000/06/28 11:52:36 $
//  RELEASE:        $Revision: 1.4 $
//  AUTHORS:        Lima Delta Delta (LDD) - boneymen@netscape.com
//  COPYRIGHT:      (c) 2008 by LDD Game Development Spain, all rights reserved.

package org.dimensinfin.core.parser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;

import org.dimensinfin.core.interfaces.IModelStore;
import org.xml.sax.SAXException;

// - CLASS IMPLEMENTATION ...................................................................................
public abstract class AbstractStoreHandler extends AbstractXMLHandler implements IPersistentHandler {
	// - S T A T I C - S E C T I O N ..........................................................................
	private static final long serialVersionUID = -8829775442031254628L;

	// - F I E L D - S E C T I O N ............................................................................
	protected String inputReference = null;
	protected IModelStore store = null;
	protected byte[] hash;
	protected Exception lastException = null;
	protected Calendar lastUpdate = null;

	// - C O N S T R U C T O R - S E C T I O N ................................................................
	public AbstractStoreHandler (final String targetInput) {
		setInput(targetInput);
	}

	public void clearUpdate () {
		hash = new byte[0];
	}

	// - M E T H O D - S E C T I O N ..........................................................................
	public void endElement (final String name) throws SAXException {
		this.endElement(name, name, name);
	}

	@Override
	public void endElement (final String uri, final String localName, final String name) throws SAXException {
		super.endElement(uri, localName, name);
	}

	public String getLocation () {
		return inputReference;
	}

	//	public InputHandlerStates getState() {
	//		return state;
	//	}

	/**
	 * The byte[] returned by MessageDigest does not have a nice textual representation, so some form of
	 * encoding is usually performed.
	 * 
	 * This implementation follows the example of David Flanagan's book "Java In A Nutshell", and converts a
	 * byte array into a String of hex characters.
	 * 
	 * Another popular alternative is to use a "Base64" encoding.
	 */
	public String hexEncode (final byte[] aInput) {
		if (null == aInput) return "INVALID DATA";
		final StringBuilder result = new StringBuilder();
		final char[] digits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		for (final byte b : aInput) {
			result.append(digits[(b & 0xf0) >> 4]);
			result.append(digits[b & 0x0f]);
		}
		return result.toString().toUpperCase();
	}

	/**
	 * Configures the input to the file path received as the parameter. Checks the input state and updates the
	 * change detection flags and the calculation of the current file hash. During the hash calculation the file
	 * existence is verified.
	 */
	public void setInput (final String fileName) {
		if (null == fileName) {
			//			this.setState(InputHandlerStates.INVALID);
			inputReference = null;
			lastException = null;
			lastUpdate = null;
		} else {
			inputReference = fileName;
			lastException = null;
			lastUpdate = null;
			//			this.setState(InputHandlerStates.LINKED);
			//- Check if we have configured a model destination
			//			if (null != store) {
			//				this.setState(InputHandlerStates.READY);
			//			}
			//			final byte[] localHash = computeHash();
			//			AbstractStoreHandler.logger.fine("Computed hash for file [" + fileName + "] is " + hexEncode(localHash));
			//			if (null != lastException) {
			//				this.setState(InputHandlerStates.ERROR);
			//			} else {
			//- Store the hash, but mark the input as not used to fire a load next time this input is called.
			//				hash = localHash;
			//- Tag this hash with the current time for time refresh.
			lastUpdate = null;
			//			}
		}
	}

	public void setStore (final IModelStore newStore) {
		if (null != newStore) {
			store = newStore;
			//			if (state == InputHandlerStates.LINKED) {
			//				this.setState(InputHandlerStates.READY);
			//			}
		}
	}

	/**
	 * Method called during the XML processing of the lines and tags. Most of the tags pass this call to the
	 * PilotCommand for more processing.
	 */
	//	public void startElement(final String name, final Attributes attributes) throws SAXException {
	//		// - Read and create the list of commands to be processed for the boat.
	//		if (name.toLowerCase().equals("pilotcommand")) {
	//			buildUpCommand = new PilotCommand(validateNotNull(attributes, "type"));
	//			buildUpCommand.startElement(name, attributes);
	//		}
	//		if (name.toLowerCase().equals("pilotlimits")) if (null != buildUpCommand) {
	//			buildUpCommand.startElement(name, attributes);
	//		}
	//		if (name.toLowerCase().equals("pilotlimit")) if (null != buildUpCommand) {
	//			buildUpCommand.startElement(name, attributes);
	//		}
	//		if (name.toLowerCase().equals("waypointlist")) if (null != buildUpCommand) {
	//			buildUpCommand.startElement(name, attributes);
	//		}
	//		if (name.toLowerCase().equals("waypoint")) if (null != buildUpCommand) {
	//			buildUpCommand.startElement(name, attributes);
	//		}
	//	}

	/**
	 * Computes a new file message digest and compares it to the current stored hash key. Returns true is both
	 * keys are the same that means that the file has not changed since the last verification.
	 */
	protected boolean checkHashCode () {
		final byte[] newHash = computeHash();
		return MessageDigest.isEqual(newHash, hash);
	}

	protected byte[] computeHash () {
		try {
			final MessageDigest inputHash = MessageDigest.getInstance("SHA");
			inputHash.update(bufferFileData().getBytes());
			return inputHash.digest();
		} catch (final NoSuchAlgorithmException nsae) {
			//- Store the exception for its use outside this instance
			lastException = nsae;
			return new byte[0];
		} catch (final IOException ioe) {
			//- Store the exception for its use outside this instance
			lastException = ioe;
			return new byte[0];
		}
	}

	protected boolean needsReload () {
		//		if (state != InputHandlerStates.READY) return false;
		if (!checkHashCode())
			return true;
		else if (null == lastUpdate)
			return true;
		else {
			//TODO Check for expiration of at least 30 minutes.
		}
		return false;
	}

	//	protected void setState(final InputHandlerStates newState, final Exception exception) {
	//		this.setState(newState);
	//		lastException = exception;
	//	}

	private String bufferFileData () throws IOException {
		final BufferedReader input = new BufferedReader(new FileReader(inputReference));
		final StringBuffer buffer = new StringBuffer();
		String line = input.readLine();
		while (null != line) {
			buffer.append(line);
			line = input.readLine();
		}
		input.close();
		return buffer.toString();
	}

	//	private void setState(final InputHandlerStates newState) {
	//		state = newState;
	//	}

}
// - UNUSED CODE ............................................................................................
