package org.dimensinfin.android.mvc.controller;

import android.app.Application;
import org.dimensinfin.android.mvc.demo.database.DemoRepository;
import org.dimensinfin.android.mvc.demo.database.Group;

import java.util.List;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

/**
 * @author Adam Antinoo
 */
public class GroupViewModel extends AndroidViewModel {
	// - F I E L D - S E C T I O N
	private DemoRepository mRepository;
	private LiveData<List<Group>> _allGroups;

	// - C O N S T R U C T O R - S E C T I O N
	public GroupViewModel (Application application) {
		super(application);
		mRepository = new DemoRepository(application);
		_allGroups = mRepository.getAllGroups();
	}

	// - G E T T E R S   &   S E T T E R S
	public LiveData<List<Group>> getAllWords() { return this._allGroups; }

	public void storeGroup(final Group group) {
		this.mRepository.storeGroup(group);
	}

	// - M E T H O D - S E C T I O N
}
