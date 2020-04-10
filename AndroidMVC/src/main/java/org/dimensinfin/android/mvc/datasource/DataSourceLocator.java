package org.dimensinfin.android.mvc.datasource;

import java.io.Serializable;
import java.util.ArrayList;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Allows to set multiple identifiers to generate a single an unique String identifier to locate a single
 * DataSource. The current identifiers are the Character ID and the DataSource type and along the variant to
 * use for this type. For example: for character id 9856634 and the type Assets we can set the variant
 * AssetsByLocation.
 *
 * @author Adam Antinoo
 */
public class DataSourceLocator implements Serializable {
	private static final long serialVersionUID = -9208786809602219002L;
	private static final char SEPARATOR = '/';

	// - F I E L D - S E C T I O N
	private final ArrayList<String> identifiers = new ArrayList<>();

	// - C O N S T R U C T O R S
	public DataSourceLocator() {
	}

	/**
	 * Get the concatenation of the identifiers to generate a single unique identifier.
	 *
	 * @return identifier concatenation
	 */
	public String getIdentity() {
		final StringBuilder buffer = new StringBuilder();
		for (String id : identifiers) {
			buffer.append( id ).append( DataSourceLocator.SEPARATOR );
		}
		return buffer.toString();
	}

	// - M E T H O D - S E C T I O N
	public DataSourceLocator addIdentifier( final int identifier ) {
		identifiers.add( Integer.toString( identifier ) );
		return this;
	}

	public DataSourceLocator addIdentifier( final long identifier ) {
		identifiers.add( Long.toString( identifier ) );
		return this;
	}

	public DataSourceLocator addIdentifier( final String identifier ) {
		identifiers.add( identifier );
		return this;
	}

	@Override
	public String toString() {
		return new ToStringBuilder( this, ToStringStyle.JSON_STYLE )
				       .append( "identifier", this.getIdentity() )
				       .toString();
	}
}
