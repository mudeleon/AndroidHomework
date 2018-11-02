package airfrov.com.androidhomework.model.data;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;


public class Dog extends RealmObject {



    @SerializedName("dogId")
    @PrimaryKey
    private int dogId;
    @SerializedName("dogName")
    private String dogName;
    @SerializedName("dogImage")
    private String dogImage;


    public Dog() {
    }


    public int getDogId() {
        return dogId;
    }

    public void setDogId(int dogId) {
        this.dogId = dogId;
    }

    public String getDogName() {
        return dogName;
    }

    public void setDogName(String dogName) {
        this.dogName = dogName;
    }

    public String getDogImage() {
        return dogImage;
    }

    public void setDogImage(String dogImage) {
        this.dogImage = dogImage;
    }


}
