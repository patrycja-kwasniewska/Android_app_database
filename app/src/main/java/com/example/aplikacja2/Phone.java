package com.example.aplikacja2;
//
//import androidx.room.ColumnInfo;
//import androidx.room.Entity;
//import androidx.room.PrimaryKey;
//
//@Entity
//public class Phone {
//    @PrimaryKey(autoGenerate = true)
//    public int id;
//
//    @ColumnInfo(name = "producer")
//    public String producer;
//
//    @ColumnInfo(name = "model")
//    public String model;
//
//    @ColumnInfo(name = "version")
//    public String version;
//
//    @ColumnInfo(name = "website")
//    public String website;
//
//    public Phone(String producer, String model, String  version, String website)
//    {
//        this.producer=producer;
//        this.model=model;
//        this.version=version;
//        this.website=website;
//    }
//
//    public String getProducer() {
//        return producer;
//    }
//
//    public String getModel(){
//        return model;
//    }
//}
//
//



import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Phone implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "producer")
    public String producer;

    @ColumnInfo(name = "model")
    public String model;

    @ColumnInfo(name = "version")
    public String version;

    @ColumnInfo(name = "website")
    public String website;

    public Phone(String producer, String model, String version, String website) {
        this.producer = producer;
        this.model = model;
        this.version = version;
        this.website = website;
    }

    public String getManufacturer() {
        return producer;
    }

    public String getVersion(){
        return version;
    }

    public String getWebsite(){
        return website;
    }

    public String getModel() {
        return model;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    // Metody wymagane przez interfejs Parcelable
    protected Phone(Parcel in) {
        id = in.readInt();
        producer = in.readString();
        model = in.readString();
        version = in.readString();
        website = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(producer);
        dest.writeString(model);
        dest.writeString(version);
        dest.writeString(website);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Phone> CREATOR = new Creator<Phone>() {
        @Override
        public Phone createFromParcel(Parcel in) {
            return new Phone(in);
        }

        @Override
        public Phone[] newArray(int size) {
            return new Phone[size];
        }
    };

    public int getId() {
        return id;
    }
}
