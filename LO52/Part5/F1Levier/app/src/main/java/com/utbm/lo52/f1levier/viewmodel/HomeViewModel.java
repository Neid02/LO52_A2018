package com.utbm.lo52.f1levier.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;

import com.utbm.lo52.f1levier.dao.AppRepository;
import com.utbm.lo52.f1levier.model.GroupeIhm;

public class HomeViewModel extends AndroidViewModel {

    private AppRepository appRepository;

    private String selectedItem;
    private GroupeIhm selectedGroup;

    public HomeViewModel(Application application) {
        super(application);
        appRepository = new AppRepository(application);
    }

    public String getSelectedItem() {
        return selectedItem;
    }

    public void setSelectedItem(String selectedItem) {
        this.selectedItem = selectedItem;
    }

    public GroupeIhm getSelectedGroup() {
        return selectedGroup;
    }

    public void setSelectedGroup(GroupeIhm selectedGroup) {
        this.selectedGroup = selectedGroup;
    }

}
