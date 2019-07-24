package com.cristhian.gametobeat.database;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Cover implements Parcelable {

    @SerializedName("id")
    private Long id;

    @SerializedName("url")
    private String url;

    @SerializedName("image_id")
    private String imageId;

    public Cover() {
    }

    protected Cover(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readLong();
        }
        url = in.readString();
        imageId = in.readString();
    }

    public static final Creator<Cover> CREATOR = new Creator<Cover>() {
        @Override
        public Cover createFromParcel(Parcel in) {
            return new Cover(in);
        }

        @Override
        public Cover[] newArray(int size) {
            return new Cover[size];
        }
    };

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(id);
        }
        dest.writeString(url);
        dest.writeString(imageId);
    }
}
