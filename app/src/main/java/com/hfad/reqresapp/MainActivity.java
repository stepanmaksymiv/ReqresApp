package com.hfad.reqresapp;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.paging.PagedList;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.hfad.reqresapp.model.Data;
import com.hfad.reqresapp.model.DataResponse;
import com.hfad.reqresapp.retrofit.ApiService;
import com.hfad.reqresapp.retrofit.RetrofitClient;
import com.hfad.reqresapp.viewmodel.MainViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private UserAdapter adapter;
    private RecyclerView recyclerView;
    private MainViewModel viewModel;
    private PagedList<Data> dataPagedList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initRecyclerView();
        getUserData();
    }

    private void getUserData(){
        Observer<PagedList<Data>>pagedListObserver = new Observer<PagedList<Data>>() {
            @Override
            public void onChanged(@Nullable PagedList<Data> dataResult) {
                if (dataResult != null){
                    dataPagedList = dataResult;
                }
                if (adapter == null){
                    adapter = new UserAdapter(MainActivity.this);
                    adapter.submitList(dataPagedList);
                    recyclerView.setAdapter(adapter);
                } else {
                    adapter.notifyDataSetChanged();
                }
                adapter.setListener(new UserAdapter.Listener() {
                    @Override
                    public void onItemClick(int position) {
                        Data data = dataPagedList.get(position);
                        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                        intent.putExtra(DetailActivity.KEY_DETAIL, data);
                        startActivity(intent);
                    }
                });
            }
        };
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        viewModel.getDataPageList().observe(this, pagedListObserver);
    }

    private void initRecyclerView(){
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
    }
}
