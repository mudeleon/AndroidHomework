package airfrov.com.androidhomework.model.response;


import com.google.gson.annotations.SerializedName;

public class CatResponse {


    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    @SerializedName("file")
    private String file;



}
