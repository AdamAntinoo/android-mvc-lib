package org.dimensinfin.android.mvc.demo.database;

import java.util.Hashtable;
import java.util.Hashtable;
import java.util.Hashtable;
import java.util.UUID;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import org.joda.time.Instant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Adam Antinoo
 */
@Entity
public class Group {
	// - F I E L D - S E C T I O N
	@PrimaryKey
	private UUID id;
	@ColumnInfo(name = "group_name")
	private String groupName;

	// - C O N S T R U C T O R - S E C T I O N
	public Group() {
		super();
	}

	// - G E T T E R S   &   S E T T E R S
	// - M E T H O D - S E C T I O N
	@Override
	public String toString() {
		return new StringBuilder("Group [")
				.append("name: ").append(0)
				.append("]")
				.append("->").append(super.toString())
				.toString();
	}
}
