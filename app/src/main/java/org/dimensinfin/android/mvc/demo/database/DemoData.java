package org.dimensinfin.android.mvc.demo.database;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

/**
 * @author Adam Antinoo
 */
@Database(entities = {Group.class, Label.class}, version = 1)
public abstract class DemoData extends RoomDatabase {
	public static  DemoData appDatabase ;
	static {
		final DemoData appDatabase = Room.databaseBuilder(this.ge,
				DemoData.class, "database-name").build();
	}

	public abstract GroupDao groupDao();
	public abstract LabelDao labelDao();
}
