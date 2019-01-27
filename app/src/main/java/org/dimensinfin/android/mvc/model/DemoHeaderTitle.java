//  PROJECT:     Android.MVC (A.MVC)
//  AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
//  COPYRIGHT:   (c) 2013-2019 by Dimensinfin Industries, all rights reserved.
//  ENVIRONMENT: Android API16.
//  DESCRIPTION: Library that defines a generic Model View Controller core classes to be used
//               on Android projects. Defines the AndroidController factory and the AndroidController core methods to manage
//               a generic converter from a Graph Model to a hierarchical AndroidController model that finally will
//               be converted to a AndroidController list to be used on a BaseAdapter tied to a ListView.
package org.dimensinfin.android.mvc.model;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import org.dimensinfin.core.interfaces.ICollaboration;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode
public class DemoHeaderTitle implements ICollaboration, Serializable {
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
		StringBuffer buffer = new StringBuffer( "DemoHeaderTitle [" );
		buffer.append( getName() ).append( " - " ).append( getVersion() );
		buffer.append( " ]" );
		return buffer.toString();
	}
}
