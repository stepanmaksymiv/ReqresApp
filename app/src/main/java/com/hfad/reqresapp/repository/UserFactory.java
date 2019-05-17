package com.hfad.reqresapp.repository;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.DataSource;

import com.hfad.reqresapp.retrofit.ApiService;

public class UserFactory extends DataSource.Factory {

    private UserDataSource dataSource;
    private ApiService service;
    private Application application;
    private MutableLiveData<UserDataSource>mutableLiveData;

    public UserFactory(ApiService service, Application application) {
        this.service = service;
        this.application = application;
        mutableLiveData = new MutableLiveData<>();
    }

    @Override
    public DataSource create() {
        dataSource = new UserDataSource(service, application);
        mutableLiveData.postValue(dataSource);
        return dataSource;
    }

    public MutableLiveData<UserDataSource> getMutableLiveData() {
        return mutableLiveData;
    }
}
