package airfrov.com.androidhomework.ui.pets.dog;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import java.util.List;

import airfrov.com.androidhomework.app.App;
import airfrov.com.androidhomework.model.data.Dog;
import airfrov.com.androidhomework.model.response.DogResponse;
import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DogPresenter extends MvpBasePresenter<DogView> {

    private Realm realm;

    public void onStart() {

        realm = Realm.getDefaultInstance();
    }

    public void loadDogList(List<String> image) {

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.delete(Dog.class);
            }
        });


        for (int dog=1;dog<=10;dog++) {
            final Dog addDog = new Dog();
            addDog.setDogId(dog);
            addDog.setDogName("Dog " + String.valueOf(dog));
            addDog.setDogImage(image.get(dog-1));




            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.copyToRealmOrUpdate(addDog);
                }
            });

        }

        getView().stopRefresh();
        getView().setDogList();

    }


    public void getDogImages() {

        App.getInstance().getApiInterfaceDog().getDog()
                .enqueue(new Callback<DogResponse>() {
                    @Override
                    public void onResponse(Call<DogResponse> call, final Response<DogResponse> response) {
                        if (isViewAttached()) {
                            getView().stopRefresh();
                        }
                        if (response.isSuccessful()) {
                           getView().saveDogImages(response.body().getFile());

                        } else {
                            if (isViewAttached())
                                getView().showError(response.errorBody().toString());

                        }
                    }

                    @Override
                    public void onFailure(Call<DogResponse> call, Throwable t) {
                        t.printStackTrace();
                        if (isViewAttached()) {
                            getView().stopRefresh();
                            getView().showError(t.getLocalizedMessage());
                        }
                    }
                });


    }

    public void updatePetName(final String name, final int dogId)
    {

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                final Dog dog = realm.where(Dog.class).equalTo("dogId", dogId).findFirst();
                dog.setDogName(name);
            }
        });


        getView().updateName();
    }



    public void onStop() {
        realm.close();
    }
}
