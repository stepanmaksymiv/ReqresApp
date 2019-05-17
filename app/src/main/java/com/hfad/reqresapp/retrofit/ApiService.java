package com.hfad.reqresapp.retrofit;

import com.hfad.reqresapp.model.DataResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("/api/users")
    Call<DataResponse> getListUsers
            (@Query("page")int page);
}
