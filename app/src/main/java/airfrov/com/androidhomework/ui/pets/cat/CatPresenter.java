package airfrov.com.androidhomework.ui.pets.cat;

import android.util.Log;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import java.util.List;

import airfrov.com.androidhomework.app.ApiInterface;
import airfrov.com.androidhomework.app.App;
import airfrov.com.androidhomework.model.data.Cat;
import airfrov.com.androidhomework.model.response.CatResponse;
import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class CatPresenter extends MvpBasePresenter<CatView> {

    private Realm realm;
    private String catReturn;

    public void onStart() {

        realm = Realm.getDefaultInstance();
        catReturn = "";
    }

    public void loadCatList(List<String> image) {

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.delete(Cat.class);
            }
        });


        for (int cat=1;cat<=10;cat++) {
            final Cat addCat = new Cat();
            addCat.setCatId(cat);
            addCat.setCatName("Cat " + String.valueOf(cat));
            addCat.setCatImage(image.get(cat-1));




            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.copyToRealmOrUpdate(addCat);
                }
            });

        }

        getView().stopRefresh();
        getView().setCatList();

    }


    public void getCatImages() {

        App.getInstance().getApiInterfaceCat().getCat()
                .enqueue(new Callback<CatResponse>() {
                    @Override
                    public void onResponse(Call<CatResponse> call, final Response<CatResponse> response) {
                        if (isViewAttached()) {
                            getView().stopRefresh();
                        }
                        if (response.isSuccessful()) {
                         //   Log.d(">>>>",response.body().getFile()+"");
                           getView().saveCatImages(response.body().getFile());

                        } else {
                            if (isViewAttached())
                                getView().showError(response.errorBody().toString());

                        }
                    }

                    @Override
                    public void onFailure(Call<CatResponse> call, Throwable t) {
                        t.printStackTrace();
                        if (isViewAttached()) {
                            getView().stopRefresh();
                            getView().showError(t.getLocalizedMessage());
                        }
                    }
                });


    }

    public void updatePetName(final String name, final int catId)
    {

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                final Cat cat = realm.where(Cat.class).equalTo("catId", catId).findFirst();
                cat.setCatName(name);
            }
        });


        getView().updateName();
    }




    public void onStop() {
        realm.close();
    }
}
