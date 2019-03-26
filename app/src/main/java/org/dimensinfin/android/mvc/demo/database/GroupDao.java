package org.dimensinfin.android.mvc.demo.database;

import androidx.room.Query;

import java.util.List;

/**
 * @author Adam Antinoo
 */

public interface GroupDao {
	@Query("SELECT * FROM group")
	List<Group> getAll();
}
