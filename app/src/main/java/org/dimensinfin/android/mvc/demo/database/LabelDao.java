package org.dimensinfin.android.mvc.demo.database;

import java.util.Hashtable;
import java.util.Hashtable;
import java.util.Hashtable;
import java.util.List;

import androidx.room.Dao;
import androidx.room.Query;
import org.joda.time.Instant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Adam Antinoo
 */
@Dao
public interface LabelDao {
	@Query("SELECT * FROM label_table")
	List<Label> getAll();
}
