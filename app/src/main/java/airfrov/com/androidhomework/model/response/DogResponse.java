package airfrov.com.androidhomework.model.response;


import com.google.gson.annotations.SerializedName;

public class DogResponse {


    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    @SerializedName("url")
    private String file;



}
