package org.dimensinfin.android.mvc.datasource;

import org.dimensinfin.android.mvc.interfaces.ICollaboration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * This is an special node that is set on the root of a graph or hierarchy. It will collaborate its contents that are
 * being managed by the data source itself. So when the data source wants to show something it adds an element to this
 * root node. The root node does not set any filtering on its contents and will be neutral about the type of its
 * children.
 * @author Adam Antinoo
 */
public class MVCModelRootNode implements ICollaboration {
	// - F I E L D - S E C T I O N
	private List<ICollaboration> modelContents = new ArrayList<>();

	// - C O N S T R U C T O R - S E C T I O N
//	public MVCModelRootNode() {
//		super();
//	}

	// - I C O L L A B O R A T I O N   I N T E R F A C E

	/**
	 * This special node collaborates with their children but not itself.
	 */
	public List<ICollaboration> collaborate2Model(final String variant) {
		return new ArrayList<>(this.getModelContents());
	}

	// - G E T T E R S   &   S E T T E R S

	public List<ICollaboration> getModelContents() {
		return modelContents;
	}

	// - M E T H O D - S E C T I O N
	// - D E L E G A T E S - M O D E L C O N T E N T S
	public void addChild(final ICollaboration child) {
		if (null != child) {
			modelContents.add(child);
		}
	}

	public void addChildren(final List<ICollaboration> modelList) {
		for (ICollaboration node : modelList)
			this.addChild(node);
	}

	public void clean() {
		this.modelContents.clear();
	}

	@Override
	public String toString() {
		return "MVCModelRootNode [count: " + this.getModelContents().size() + " ]";
	}
}
