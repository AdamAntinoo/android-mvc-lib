//  PROJECT:        net.sf.sandbox.rcpapplication
//  FILE NAME:      $Id: ProcessorApp.java,v $
//  LAST UPDATE:    $Date: 2000/06/28 11:52:36 $
//  RELEASE:        $Revision: 1.4 $
//  AUTHORS:        Lima Delta Delta (LDD) - boneymen@netscape.com
//  COPYRIGHT:      (c) 2009 by LDD Game Development Spain, all rights reserved.

package org.dimensinfin.core.model;

// - IMPORT SECTION .........................................................................................
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.logging.Logger;

import org.dimensinfin.gef.core.AbstractGEFNode;

// - CLASS IMPLEMENTATION ...................................................................................
public class GenericModelStore extends AbstractGEFNode {

	// - S T A T I C - S E C T I O N ..........................................................................
	private static final long serialVersionUID = 6424836314694502117L;
	private static Logger logger = Logger.getLogger("core");

	//- P R O P E R T I E S   F I R E D
	public static final String MODEL_STRUCTURE_CHANGED = "GenericModelStore.MODEL_STRUCTURE_CHANGED";
	public static final String STATE_CHANGED = "GenericModelStore.STATE_CHANGED";

	// - F I E L D - S E C T I O N ............................................................................
	/**
	 * This array contains the of first level objects to be visible on the Map. Objects on this level may be of
	 * different classes but all have to share a same common interface definitions.
	 */
	//	private final Vector<AbstractGEFNode>	modelContents								= new Vector<AbstractGEFNode>();
	/** Path to the persistent file that is the model repository. */
	private static final String persistentModelStorageName = "SandBoxModel.xml";
	private static final String NEWLINE = "\n\r";
	/** Stores a flag to signal if the model has changed with respect to the persistent stored data. */
	private boolean dirty = false;

	/**
	 * Reference to the last exception occurred during processing. This allows graphical displays to show this
	 * message at a later moment.
	 */
	private Exception lastException = null;

	// - C O N S T R U C T O R - S E C T I O N ................................................................
	public GenericModelStore () {
	}

	// - M E T H O D - S E C T I O N ..........................................................................
	/**
	 * This is a common operation to most <code>ModelStores</code> that is to add a new element to the top level
	 * structure and then update the persistent storage (if defined) and fire upward to the
	 * <code>EditPart</code> and view layers the message that the model structure has changed.
	 */
	//	protected void addChild(AbstractGEFNode childRef) {
	//		if (null != childRef) {
	//			//- Add this to the internal child structure and then update model and fire EditPart update.
	//			super.addChild(childRef);
	//			updatePersistentStorage();
	//			fireStructureChange(MODEL_STRUCTURE_CHANGED, this, childRef);
	//		}
	//	}

	public Exception getLastException () {
		return lastException;
	}

	//	public void setState(AbstractModelStoreState oldState, AbstractModelStoreState newState) {
	//		firePropertyChange(STATE_CHANGED, oldState, newState);
	//	}

	/**
	 * Adds a new top level element of the corresponding type to the model store structure.
	 */
	//	public void addTopElement(HexagonBaseModel baseRef) {
	//		if (null != baseRef) {
	//			//- Check if the element is already present as a reference.
	//			if (modelContents.contains(baseRef)) return;
	//			modelContents.add(baseRef);
	//			//- Use global methods to perform most common operations.
	//			//Add this to the internal child structure and then update model and fire EditPart update.
	//			super.addChild(baseRef);
	//		}
	//	}

	@Override
	public void setDirty (final boolean dirtyState) {
		dirty = dirtyState;
	}

	public void setLastException (Exception lastException) {
		this.lastException = lastException;
	}

	/**
	 * If the flag shows that the model in memory has been changed from the last time it was written to the
	 * persistent storage, then the method start to write down the model data to that persistent file. It there
	 * is any error during this process the flag returns back to show it is dirty and the error message gets
	 * stored for access from outside control classes.
	 */
	public void updatePersistentStorage () {
		if (dirty) {
			try {
				logger.fine(">>>Saving data to persistent store.");
				final PrintWriter persistent = new PrintWriter(persistentModelStorageName);
				final StringBuffer buffer = new StringBuffer();
				buffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>").append(NEWLINE);
				buffer.append("<boatmodel>").append(NEWLINE);
				buffer.append("<configuration");
				//				if (null != inputHelper) {
				//					buffer.append(" location=").append(this.quote(inputHelper.getFilePath()));
				//					buffer.append(" type=").append(this.quote(inputHelper.getType().toString()));
				//				} else {
				//					buffer.append(" location=\"\"  type=\"NONE\"");
				//				}
				//				buffer.append(" refresh=").append(this.quote(refresh));
				//				buffer.append(" time=").append(this.quote(timeDelay));
				buffer.append(" />");
				persistent.println(buffer.toString());
				//				persistent.println("<boatlist>");
				//				final Iterator<PilotBoat> bit = modelContents.iterator();
				//				while (bit.hasNext()) {
				//					final PilotBoat node = bit.next();
				//					persistent.println(node.generatePersistentXML());
				//				}
				//				persistent.println("</boatlist>");
				persistent.println("</boatmodel>");
				persistent.close();
				dirty = false;
			} catch (final FileNotFoundException fnfe) {
				//- Store the exception for later access on the User Interface
				setLastException(fnfe);
				//- Can not create the file. Can not continue.
				throw new RuntimeException(fnfe);
			}
		}
	}
}
// - UNUSED CODE ............................................................................................
