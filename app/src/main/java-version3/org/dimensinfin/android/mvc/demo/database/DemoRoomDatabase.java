package org.dimensinfin.android.mvc.demo.database;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import java.util.UUID;

/**
 * @author Adam Antinoo
 */
@TypeConverters({UUIDDatabaseConverter.class})
@Database(entities = {Group.class, Label.class}, version = 1)
public abstract class DemoRoomDatabase extends RoomDatabase {
	// - D A O S
	public abstract GroupDao groupDao();
	public abstract LabelDao labelDao();

	// - D A T A B A S E   S I N G L E T O N
	public static volatile DemoRoomDatabase appDatabase;

	static DemoRoomDatabase getDatabase(final Context context) {
		if (appDatabase == null) {
			synchronized (DemoRoomDatabase.class) {
				if (appDatabase == null) {
					appDatabase = Room.databaseBuilder(context.getApplicationContext(),
							DemoRoomDatabase.class, "demo_database")
							.build();
				}
			}
		}
		return appDatabase;
	}
}
