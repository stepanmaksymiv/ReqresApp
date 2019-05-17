package com.hfad.reqresapp.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "users")
public class Data implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    @SerializedName("id")
    @Expose
    private int id;
    @ColumnInfo
    @SerializedName("email")
    @Expose
    private String email;
    @ColumnInfo
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @ColumnInfo
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @ColumnInfo
    @SerializedName("avatar")
    @Expose
    private String avatar;

    public Data(int id, String email, String firstName, String lastName, String avatar) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.avatar = avatar;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }


    public static final DiffUtil.ItemCallback callBack = new DiffUtil.ItemCallback<Data>() {

        @Override
        public boolean areItemsTheSame(@NonNull Data oldData, @NonNull Data newData) {
            return oldData.getId() == newData.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Data oldData, @NonNull Data newData) {
            return true;
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.email);
        dest.writeString(this.firstName);
        dest.writeString(this.lastName);
        dest.writeString(this.avatar);
    }

    protected Data(Parcel in) {
        this.id = in.readInt();
        this.email = in.readString();
        this.firstName = in.readString();
        this.lastName = in.readString();
        this.avatar = in.readString();
    }

    public static final Parcelable.Creator<Data> CREATOR = new Parcelable.Creator<Data>() {
        @Override
        public Data createFromParcel(Parcel source) {
            return new Data(source);
        }

        @Override
        public Data[] newArray(int size) {
            return new Data[size];
        }
    };
}
