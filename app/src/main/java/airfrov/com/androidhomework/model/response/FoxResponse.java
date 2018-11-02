package airfrov.com.androidhomework.model.response;


import com.google.gson.annotations.SerializedName;

public class FoxResponse {


    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    @SerializedName("image")
    private String file;



}
