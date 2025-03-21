package com.cristhian.gametobeat.database;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "game")
public class Game implements Parcelable {

    @NonNull
    @PrimaryKey
    @SerializedName("id")
    private Long gameId;

    @SerializedName("name")
    private String name;

    @SerializedName("summary")
    private String summary;

    @SerializedName("rating")
    private Double rating;

    private int status;

    @Embedded
    @SerializedName("cover")
    private Cover cover;

    public static final int STATUS_NULL = 0;
    public static final int STATUS_BEAT = 1;
    public static final int STATUS_PLAYING = 2;
    public static final int STATUS_WANT = 3;

    public Game() {}

    protected Game(Parcel in) {
        if (in.readByte() == 0) {
            gameId = null;
        } else {
            gameId = in.readLong();
        }
        name = in.readString();
        summary = in.readString();
        if (in.readByte() == 0) {
            rating = null;
        } else {
            rating = in.readDouble();
        }
        status = in.readInt();
        cover = in.readParcelable(Cover.class.getClassLoader());
    }

    public static final Creator<Game> CREATOR = new Creator<Game>() {
        @Override
        public Game createFromParcel(Parcel in) {
            return new Game(in);
        }

        @Override
        public Game[] newArray(int size) {
            return new Game[size];
        }
    };

    @NonNull
    public Long getGameId() {
        return gameId;
    }

    public void setGameId(@NonNull Long gameId) {
        this.gameId = gameId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Cover getCover() {
        return cover;
    }

    public void setCover(Cover cover) {
        this.cover = cover;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (gameId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(gameId);
        }
        dest.writeString(name);
        dest.writeString(summary);
        if (rating == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(rating);
        }
        dest.writeInt(status);
        dest.writeParcelable(cover, flags);
    }
}
