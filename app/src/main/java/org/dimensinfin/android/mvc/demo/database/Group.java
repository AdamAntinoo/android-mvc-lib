package org.dimensinfin.android.mvc.demo.database;

import org.dimensinfin.android.mvc.model.DemoNode;

import java.util.UUID;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * @author Adam Antinoo
 */
@Entity(tableName = "group_table")
public class Group extends DemoNode {
	// - F I E L D - S E C T I O N
	@PrimaryKey
	@NonNull
	public UUID id;
	@ColumnInfo(name = "group_name")
	@NonNull
	public String groupName;

	// - C O N S T R U C T O R - S E C T I O N
	public Group() {
		super();
	}

	// - G E T T E R S   &   S E T T E R S
	@NonNull
	public UUID getId() {
		return id;
	}

	@NonNull
	public String getGroupName() {
		return groupName;
	}

	public Group setId(@NonNull final UUID id) {
		this.id = id;
		return this;
	}

	public Group setGroupName(@NonNull final String groupName) {
		this.groupName = groupName;
		return this;
	}

	// - M E T H O D - S E C T I O N
//	@Override
//	public String toString() {
//		return new StringBuilder("Group [")
//				.append("name: ").append(0)
//				.append("]")
//				.append("->").append(super.toString())
//				.toString();
//	}
}
