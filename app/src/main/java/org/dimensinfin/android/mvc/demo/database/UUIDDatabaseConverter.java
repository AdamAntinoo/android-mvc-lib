package org.dimensinfin.android.mvc.demo.database;

import androidx.room.TypeConverter;

import java.util.UUID;

/**
 * @author Adam Antinoo
 */
public class UUIDDatabaseConverter {
	// - F I E L D - S E C T I O N

	// - C O N S T R U C T O R - S E C T I O N
	public UUIDDatabaseConverter() {
		super();
	}

	// - G E T T E R S   &   S E T T E R S
	@TypeConverter
	public static UUID fromString(String value) {
		return UUID.fromString(value);
	}

	@TypeConverter
	public static String fromUUID(final UUID identifier) {
		return identifier.toString();
	}
}
