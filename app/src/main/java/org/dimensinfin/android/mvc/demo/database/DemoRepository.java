package org.dimensinfin.android.mvc.demo.database;

import android.app.Application;

import java.util.List;

import androidx.lifecycle.LiveData;

/**
 * @author Adam Antinoo
 */
public class DemoRepository {
	// - F I E L D - S E C T I O N
	private GroupDao _groupDao;
	private LabelDao _labelDao;
	private LiveData<List<Group>> allGroups;

	// - C O N S T R U C T O R - S E C T I O N
	public DemoRepository(Application application) {
		DemoRoomDatabase db = DemoRoomDatabase.getDatabase(application);
		this._groupDao = db.groupDao();
		this.allGroups = this._groupDao.getAllGroups();
	}

	// - G E T T E R S   &   S E T T E R S
	public LiveData<List<Group>> getAllGroups() {
		return allGroups;
	}

	public void deleteGroups() {
		this._groupDao.deleteAll();
	}
}
