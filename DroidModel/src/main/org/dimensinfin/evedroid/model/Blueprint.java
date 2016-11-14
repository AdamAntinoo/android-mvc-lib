//	PROJECT:        EveIndustrialistModel (EVEI-M)
//	AUTHORS:        Adam Antinoo - adamantinoo.git@gmail.com
//	COPYRIGHT:      (c) 2013-2014 by Dimensinfin Industries, all rights reserved.
//	ENVIRONMENT:		JRE 1.7.
//	DESCRIPTION:		Data model to use on EVE related applications. Neutral code to be used in all enwironments.

package org.dimensinfin.evedroid.model;

//- IMPORT SECTION .........................................................................................
import org.dimensinfin.core.model.AbstractGEFNode;
import org.dimensinfin.evedroid.connector.AppConnector;
import org.dimensinfin.evedroid.constant.ModelWideConstants;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

// - CLASS IMPLEMENTATION ...................................................................................
/**
 * Blueprints can be obtained separately from the Assets in a new CREST API call. Use that to speed up access
 * and then use internal queries to link blueprint instances to their corresponding assets. In the database
 * the blueprints are stored stacked to improve blueprint processing. The time to recover blueprints is much
 * more improved and the drawbacks can be circumvented with the use of more data storage.
 * 
 * @author Adam Antinoo
 */

@DatabaseTable(tableName = "Blueprints")
public class Blueprint extends AbstractGEFNode {
	// - S T A T I C - S E C T I O N ..........................................................................
	private static final long	serialVersionUID		= -1284879453130050089L;

	// - D A T A B A S E   F I E L D - S E C T I O N ..........................................................
	/**
	 * This is a generated identifier to allow having duplicated blueprints numbers when processing updates.
	 * This is the primary key identifier and it is generated by an incremental sequence.
	 */
	@DatabaseField(generatedId = true)
	private final long				id									= -2;

	/** This field connect the blueprint with the matching asset. The asset reference is not database enforced. */
	@DatabaseField(index = true)
	protected long						assetID;
	/**
	 * This location id does not match with a real location and fits more with the assetid of the container that
	 * has this blueprint.
	 */
	@DatabaseField(index = true)
	private long							containerID					= -1;
	@DatabaseField(index = true)
	private int								typeID							= -1;
	@DatabaseField
	private String						typeName						= "-";
	@DatabaseField
	private int								flag;
	@DatabaseField
	private int								quantity						= 1;
	@DatabaseField
	private int								timeEfficiency			= 0;
	@DatabaseField
	private int								materialEfficiency	= 0;
	@DatabaseField
	private int								runs								= 0;																	// Exhausted BPC with 0 runs available

	/** Here starts the fields that come from item data but useful for search operations. */
	@DatabaseField
	private long							ownerID							= -1;
	@DatabaseField
	private boolean						bpo									= false;
	@DatabaseField
	private boolean						packaged						= false;
	@DatabaseField
	private String						tech								= ModelWideConstants.eveglobal.TechI;
	/** The type ID of the matching module that can be manufactured with this blueprint. */
	@DatabaseField
	private int								moduleTypeID				= -1;
	@DatabaseField
	private String						stackIDRefences			= "";
	@DatabaseField
	private int						manufactureIndex			= -1;
	@DatabaseField
	private int						inventionIndex			= -1;
	@DatabaseField
	private double						jobProductionCost			= -1.0;
	@DatabaseField
	private int						manufacturableCount			= -1;

	// - F I E L D - S E C T I O N ............................................................................
	/** Memory operation fields not stored into the database but stored on the file store. */
	protected Asset						associatedAsset			= null;
	protected EveLocation			locationCache				= null;
	protected EveItem					blueprintItem				= null;
	protected EveItem					moduleItem					= null;

	// - C O N S T R U C T O R - S E C T I O N ................................................................
	public Blueprint() {
		super();
	}

	/**
	 * Instantiate a new blueprint but for an id. This blueprint has no associated asset and it is not present
	 * on the list of assets for the character so it has some limited features.
	 * 
	 * @param blueprintID
	 */
	public Blueprint(final int blueprintID) {
		super();
		typeID = blueprintID;
		blueprintItem = AppConnector.getDBConnector().searchItembyID(blueprintID);
		typeName = blueprintItem.getName();
		moduleTypeID = AppConnector.getDBConnector().searchModule4Blueprint(typeID);
		moduleItem = AppConnector.getDBConnector().searchItembyID(moduleTypeID);
		tech = obtainTech();
		associatedAsset = null;
	}

	public double getJobProductionCost() {
		return jobProductionCost;
	}

	public void setJobProductionCost(double jobProductionCost) {
		this.jobProductionCost = jobProductionCost;
	}

	public int getManufactureIndex() {
		return manufactureIndex;
	}

	public int getManufacturableCount() {
		return manufacturableCount;
	}

	public void setManufacturableCount(int manufacturableCount) {
		this.manufacturableCount = manufacturableCount;
	}

	public void setManufactureIndex(int manufactureIndex) {
		this.manufactureIndex = manufactureIndex;
	}

	/**
	 * The creation method is called during the parsing conversion from eveapi objects to Android model objects.
	 * 
	 * @param newAsseID
	 *          the resource identifier for an existing resource on the database. If the resource on the
	 *          database does not exist the blueprint is not created.
	 */
	public Blueprint(final long newAsseID) {
		assetID = newAsseID;
		// Load the asset and set the reference.
		try {
			accessAssociatedAsset();
			// Create the processor depending on the blueprint technology
			blueprintItem = associatedAsset.getItem();
			typeID = blueprintItem.getItemID();
			typeName = blueprintItem.getName();
			moduleTypeID = AppConnector.getDBConnector().searchModule4Blueprint(typeID);
			moduleItem = AppConnector.getDBConnector().searchItembyID(moduleTypeID);
			tech = obtainTech();
		} catch (final Exception ex) {
			//			Log.w("W> Blueprint.<init>. Asset <" + newAsseID + "> not found.");
			throw new RuntimeException("W> Blueprint.<init> - Asset <" + newAsseID + "> not found.");
		}
	}

	// - M E T H O D - S E C T I O N ..........................................................................
	public long getAssetID() {
		return assetID;
	}

	public String getCategory() {
		return getAssociatedAsset().getCategory();
	}

	public int getFlag() {
		return flag;
	}

	public String getGroupName() {
		return getAssociatedAsset().getGroupName();
	}

	public EveItem getItem() {
		return getAssociatedAsset().getItem();
	}

	/**
	 * Get access to the real location reference for the blueprint. It can be a default for prototypes and the
	 * location of the associated asset for normal blueprints. Check the access to null elements and cache the
	 * result.
	 */
	public EveLocation getLocation() {
		if (null == locationCache) {
			if (null == getAssociatedAsset())
				return new EveLocation();
			else
				locationCache = getAssociatedAsset().getLocation();
		}
		return locationCache;
	}

	public long getLocationID() {
		return containerID;
	}

	public int getMaterialEfficiency() {
		return materialEfficiency;
	}

	public String getModuleGroup() {
		if (null == moduleItem) moduleItem = AppConnector.getDBConnector().searchItembyID(moduleTypeID);
		return moduleItem.getGroupName();
	}

	public String getModuleGroupCategory() {
		if (null == moduleItem) moduleItem = AppConnector.getDBConnector().searchItembyID(moduleTypeID);
		return moduleItem.getGroupName() + "/" + moduleItem.getCategory();
	}

	public EveItem getModuleItem() {
		if (null == moduleItem) moduleItem = AppConnector.getDBConnector().searchItembyID(moduleTypeID);
		return moduleItem;
	}

	public String getModuleName() {
		return getAssociatedAsset().getItemName();
	}

	public int getModuleTypeID() {
		return moduleTypeID;
	}

	public String getName() {
		return getTypeName();
	}

	public Asset getParentContainer() {
		return getAssociatedAsset().getParentContainer();
	}

	public int getQuantity() {
		return quantity;
	}

	public int getRuns() {
		return runs;
	}

	public String getStackID() {
		StringBuffer stackid = new StringBuffer();
		stackid.append(typeID).append(".").append(getLocationID()).append(getRuns());
		return stackid.toString();
	}

	public String getStackIDRefences() {
		return stackIDRefences;
	}

	public String getTech() {
		return tech;
	}

	public int getTimeEfficiency() {
		return timeEfficiency;
	}

	public int getTypeID() {
		return typeID;
	}

	public String getTypeName() {
		return typeName;
	}

	public boolean isBpo() {
		return bpo;
	}

	public boolean isPackaged() {
		return packaged;
	}

	public boolean isPrototype() {
		if (null == associatedAsset)
			return true;
		else
			return false;
	}

	/**
	 * Add the assetID reference to the string with the list of current references to keep track of stack
	 * contents.
	 * 
	 * @param refid
	 *          the asset reference to store.
	 */
	public void registerReference(final long refid) {
		if (stackIDRefences == "")
			stackIDRefences = Long.valueOf(refid).toString();
		else
			stackIDRefences = stackIDRefences + ModelWideConstants.STACKID_SEPARATOR + Long.valueOf(refid).toString();
	}

	public void resetOwner() {
		ownerID = -1;
	}

	public void setBpo(final boolean bpo) {
		this.bpo = bpo;
	}

	public void setFlag(final int flag) {
		this.flag = flag;
	}

	public void setLocationID(final long locationID) {
		containerID = locationID;
	}

	public void setMaterialEfficiency(final int materialEfficiency) {
		this.materialEfficiency = materialEfficiency;
	}

	public void setModuleTypeID(final int moduleID) {
		moduleTypeID = moduleID;
	}

	public void setOwnerID(final long ownerID) {
		this.ownerID = ownerID;
	}

	public void setPackaged(final boolean packaged) {
		this.packaged = packaged;
	}

	public void setQuantity(final int quantity) {
		this.quantity = quantity;
	}

	public void setRuns(final int runs) {
		this.runs = runs;
	}

	public void setTech(final String tech) {
		this.tech = tech;
	}

	public void setTimeEfficiency(final int timeEfficiency) {
		this.timeEfficiency = timeEfficiency;
	}

	public void setTypeID(final int typeID) {
		this.typeID = typeID;
	}

	public void setTypeName(final String typeName) {
		this.typeName = typeName;
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer("Blueprint [");
		if (associatedAsset == null)
			buffer.append("[PROTO]").append(" ");
		else
			buffer.append("[").append(getAssociatedAsset().getAssetID()).append("]").append(" ").append(getStackIDRefences())
					.append(" ");
		buffer.append(typeName).append(" ");
		buffer.append("[").append(typeID).append("/").append(moduleTypeID).append("] ");
		buffer.append(tech).append(" ");
		buffer.append("Runs:").append(getRuns()).append(" ");
		buffer.append("MT:").append(getMaterialEfficiency()).append("/").append(getTimeEfficiency()).append(" ");
		buffer.append("Qty:").append(quantity).append(" ");
		if (null != locationCache) buffer.append(locationCache.toString()).append(" ");
		buffer.append("]");
		return buffer.toString();
	}

	private void accessAssociatedAsset() {
		try {
			Dao<Asset, String> dao = AppConnector.getDBConnector().getAssetDAO();
			associatedAsset = dao.queryForEq("assetID", new Long(assetID).toString()).get(0);
		} catch (final Exception ex) {
//						logger.warning("W> Blueprint.<init>. Asset <" + assetID + "> not found.");
		//	throw new RuntimeException("W> Blueprint.<init> - Asset <" + assetID + "> not found.");
		}
	}

	private Asset getAssociatedAsset() {
		if (null == associatedAsset) accessAssociatedAsset();
		return associatedAsset;
	}

	/**
	 * Go to the database and get from the SDE the tech of the product manufactured with this blueprint.
	 * 
	 * @return
	 */
	private String obtainTech() {
		return AppConnector.getDBConnector().searchTech4Blueprint(typeID);
	}
}

// - UNUSED CODE ............................................................................................
