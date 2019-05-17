package com.hfad.reqresapp.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;
import android.support.annotation.NonNull;

import com.hfad.reqresapp.model.Data;
import com.hfad.reqresapp.repository.UserDataSource;
import com.hfad.reqresapp.repository.UserFactory;
import com.hfad.reqresapp.retrofit.ApiService;
import com.hfad.reqresapp.retrofit.RetrofitClient;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MainViewModel extends AndroidViewModel {

    private Executor executor;
    private LiveData<UserDataSource> sourceLiveData;
    private LiveData<PagedList<Data>> dataPageList;

    public MainViewModel(@NonNull Application application) {
        super(application);

        ApiService service = RetrofitClient.getRetrofitClient().create(ApiService.class);
        UserFactory factory = new UserFactory(service, application);
        sourceLiveData = factory.getMutableLiveData();

        PagedList.Config config = new PagedList.Config.Builder()
                .setEnablePlaceholders(true)
                .setPageSize(10)
                .build();

        //executor = Executors.newFixedThreadPool(5);
        executor = Executors.newSingleThreadExecutor();
        dataPageList = new LivePagedListBuilder<Integer, Data>(factory, config)
                .setFetchExecutor(executor)
                .build();
    }

    public LiveData<PagedList<Data>> getDataPageList() {
        return dataPageList;
    }
}
