package airfrov.com.androidhomework.app;


import airfrov.com.androidhomework.model.response.CatResponse;
import airfrov.com.androidhomework.model.response.DogResponse;
import airfrov.com.androidhomework.model.response.FoxResponse;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {



    @GET(Endpoints.CAT)
    Call<CatResponse> getCat();


    @GET(Endpoints.DOG)
    Call<DogResponse> getDog();

    @GET(Endpoints.FOX)
    Call<FoxResponse> getFox();
}
