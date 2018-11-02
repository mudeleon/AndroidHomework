package airfrov.com.androidhomework.app;

import android.app.Application;
import android.support.v7.app.AppCompatDelegate;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class App extends Application {

    private static App sInstance;
    private OkHttpClient.Builder httpClient;
    private Retrofit retrofitFox,retrofitCat,retrofitDog;
    private ApiInterface apiInterfaceFox,apiInterfaceCat,apiInterfaceDog;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        Realm.init(this);
        RealmConfiguration realmConfig = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfig);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

    }

    public synchronized static App getInstance() {
        return sInstance;
    }


    private OkHttpClient.Builder getOkHttpClient() {
        if (httpClient == null) {
            // setup logs for debugging of http request
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            // set your desired log level
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            httpClient = new OkHttpClient.Builder();
            // add your other interceptors …

            // add logging as last interceptor
            httpClient.addInterceptor(logging);  // <-- this is the important line!
        }
        return httpClient;
    }

    //Fox API
    private Retrofit getClientFox() {
        if (retrofitFox == null) {

            Gson gson = new GsonBuilder()
                    .setDateFormat(Constants.YYYY_MM_DD_T_HH_MM_SS)
                    .create();

            String url = Endpoints.API_URL_FOX;
            retrofitFox = new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(getOkHttpClient().build())
                    .build();
        }
        return retrofitFox;
    }

    public ApiInterface getApiInterfaceFox() {
        if (apiInterfaceFox == null) {
            apiInterfaceFox = getClientFox().create(ApiInterface.class);
        }
        return apiInterfaceFox;
    }


    //Dog API
    private Retrofit getClientDog() {
        if (retrofitDog == null) {

            Gson gson = new GsonBuilder()
                    .setDateFormat(Constants.YYYY_MM_DD_T_HH_MM_SS)
                    .create();

            String url = Endpoints.API_URL_DOG;
            retrofitDog = new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(getOkHttpClient().build())
                    .build();
        }
        return retrofitDog;
    }

    public ApiInterface getApiInterfaceDog() {
        if (apiInterfaceDog == null) {
            apiInterfaceDog = getClientDog().create(ApiInterface.class);
        }
        return apiInterfaceDog;
    }

    //Cat API
    private Retrofit getClientCat() {
        if (retrofitCat == null) {

            Gson gson = new GsonBuilder()
                    .setDateFormat(Constants.YYYY_MM_DD_T_HH_MM_SS)
                    .create();

            String url = Endpoints.API_URL_CAT;
            retrofitCat = new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(getOkHttpClient().build())
                    .build();
        }
        return retrofitCat;
    }

    public ApiInterface getApiInterfaceCat() {
        if (apiInterfaceCat == null) {
            apiInterfaceCat = getClientCat().create(ApiInterface.class);
        }
        return apiInterfaceCat;
    }

}
