package com.hfad.reqresapp.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.paging.PageKeyedDataSource;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.hfad.reqresapp.db.UserDatabase;
import com.hfad.reqresapp.model.Data;
import com.hfad.reqresapp.model.DataResponse;
import com.hfad.reqresapp.retrofit.ApiService;
import com.hfad.reqresapp.retrofit.RetrofitClient;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserDataSource extends PageKeyedDataSource<Integer, Data> {

    private ApiService service;
    private Application application;
    public List<Data> dataList = new ArrayList<>();
    private Executor executor = Executors.newSingleThreadExecutor();
    private UserDatabase db;
    public LiveData<List<Data>>liveData;

    public UserDataSource(ApiService service, Application application) {
        this.service = service;
        this.application = application;
        db = UserDatabase.getInstance(application.getApplicationContext());
        liveData = getAllUsers();
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull final LoadInitialCallback<Integer, Data> callback) {
        service = RetrofitClient.getRetrofitClient().create(ApiService.class);
        Call<DataResponse>call = service.getListUsers(1);
        call.enqueue(new Callback<DataResponse>() {
            @Override
            public void onResponse(Call<DataResponse> call, Response<DataResponse> response) {
                DataResponse dataResponse = response.body();
                if (dataResponse != null && dataResponse.getData() != null && response.isSuccessful()){
                    dataList = dataResponse.getData();
                    callback.onResult(dataList, null, 2);

                    insertData();

                } else {

                    getAllUsers();
                }
            }

            @Override
            public void onFailure(Call<DataResponse> call, Throwable t) {
                Toast.makeText(application.getApplicationContext(), "Фатальна помилка, неможливо завантажити дані", Toast.LENGTH_LONG).show();

            }
        });
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Data> callback) {

    }

    @Override
    public void loadAfter(@NonNull final LoadParams<Integer> params, @NonNull final LoadCallback<Integer, Data> callback) {

        service = RetrofitClient.getRetrofitClient().create(ApiService.class);
        Call<DataResponse>call = service.getListUsers(params.key);
        call.enqueue(new Callback<DataResponse>() {
            @Override
            public void onResponse(Call<DataResponse> call, Response<DataResponse> response) {
                DataResponse dataResponse = response.body();
                if (dataResponse != null && dataResponse.getData() != null && response.isSuccessful()){
                    dataList = dataResponse.getData();
                    callback.onResult(dataList,  params.key + 1);

                    insertData();
                } else {

                    getAllUsers();
                }
            }

            @Override
            public void onFailure(Call<DataResponse> call, Throwable t) {
                Toast.makeText(application.getApplicationContext(), "Фатальна помилка, неможливо завантажити дані", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void insertData(){
        Toast.makeText(application.getApplicationContext(), "Дані збережено", Toast.LENGTH_SHORT).show();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                db.userDao().insertAll(dataList);
            }
        });
    }

    private LiveData<List<Data>> getAllUsers(){
        return db.userDao().getAllUsers();
    }
}
