package airfrov.com.androidhomework.model.data;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;


public class Fox extends RealmObject {



    @SerializedName("foxId")
    @PrimaryKey
    private int foxId;
    @SerializedName("foxName")
    private String foxName;
    @SerializedName("foxImage")
    private String foxImage;


    public Fox() {
    }


    public int getFoxId() {
        return foxId;
    }

    public void setFoxId(int foxId) {
        this.foxId = foxId;
    }

    public String getFoxName() {
        return foxName;
    }

    public void setFoxName(String foxName) {
        this.foxName = foxName;
    }

    public String getFoxImage() {
        return foxImage;
    }

    public void setFoxImage(String foxImage) {
        this.foxImage = foxImage;
    }

}
