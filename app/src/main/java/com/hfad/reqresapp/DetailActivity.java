package com.hfad.reqresapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hfad.reqresapp.model.Data;

public class DetailActivity extends AppCompatActivity {

    public static final String KEY_DETAIL = "key_detail";

    private ImageView avatar;
    private TextView first_name, last_name, email;
    private Data data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        avatar = findViewById(R.id.detail_avatar);
        first_name = findViewById(R.id.name_first);
        last_name = findViewById(R.id.name_last);
        email = findViewById(R.id.email);

        data = getIntent().getParcelableExtra(KEY_DETAIL);

        String image = data.getAvatar();
        Glide.with(this).load(image).into(avatar);

        first_name.setText(data.getFirstName());
        last_name.setText(data.getLastName());
        email.setText(data.getEmail());
    }
}
