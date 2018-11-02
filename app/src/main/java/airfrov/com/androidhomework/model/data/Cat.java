package airfrov.com.androidhomework.model.data;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;



public class Cat extends RealmObject {



    @SerializedName("catId")
    @PrimaryKey
    private int catId;
    @SerializedName("catName")
    private String catName;
    @SerializedName("catImage")
    private String catImage;


    public Cat() {
    }


    public int getCatId() {
        return catId;
    }

    public void setCatId(int catId) {
        this.catId = catId;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public String getCatImage() {
        return catImage;
    }

    public void setCatImage(String catImage) {
        this.catImage = catImage;
    }


}
