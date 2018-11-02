package airfrov.com.androidhomework.ui.pets.fox;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import java.util.List;

import airfrov.com.androidhomework.app.App;
import airfrov.com.androidhomework.model.data.Fox;
import airfrov.com.androidhomework.model.response.FoxResponse;
import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FoxPresenter extends MvpBasePresenter<FoxView> {

    private Realm realm;

    public void onStart() {

        realm = Realm.getDefaultInstance();
    }

    public void loadFoxList(List<String> image) {

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.delete(Fox.class);
            }
        });


        for (int fox=1;fox<=10;fox++) {
            final Fox addFox = new Fox();
            addFox.setFoxId(fox);
            addFox.setFoxName("Fox " + String.valueOf(fox));
            addFox.setFoxImage(image.get(fox-1));




            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.copyToRealmOrUpdate(addFox);
                }
            });

        }

        getView().stopRefresh();
        getView().setFoxList();

    }


    public void updatePetName(final String name, final int foxId)
    {

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                final Fox fox = realm.where(Fox.class).equalTo("foxId", foxId).findFirst();
                fox.setFoxName(name);
            }
        });


        getView().updateName();
    }


    public void getFoxImages() {

        App.getInstance().getApiInterfaceFox().getFox()
                .enqueue(new Callback<FoxResponse>() {
                    @Override
                    public void onResponse(Call<FoxResponse> call, final Response<FoxResponse> response) {
                        if (isViewAttached()) {
                            getView().stopRefresh();
                        }
                        if (response.isSuccessful()) {
                         //   Log.d(">>>>",response.body().getFile()+"");
                           getView().saveFoxImages(response.body().getFile());

                        } else {
                            if (isViewAttached())
                                getView().showError(response.errorBody().toString());

                        }
                    }

                    @Override
                    public void onFailure(Call<FoxResponse> call, Throwable t) {
                        t.printStackTrace();
                        if (isViewAttached()) {
                            getView().stopRefresh();
                            getView().showError(t.getLocalizedMessage());
                        }
                    }
                });


    }



    public void onStop() {
        realm.close();
    }
}
