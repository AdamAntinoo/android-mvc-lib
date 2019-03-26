package org.dimensinfin.android.mvc.demo.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

/**
 * @author Adam Antinoo
 */
@Dao
public interface GroupDao {
	@Query("SELECT * FROM group_table ORDER BY group_name ASC")
	LiveData<List<Group>> getAllGroups();

	@Query("DELETE FROM group_table")
	void deleteAll();
}
