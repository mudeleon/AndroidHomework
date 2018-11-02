package airfrov.com.androidhomework.ui.pets.dog;

import com.hannesdorfmann.mosby.mvp.MvpView;

import airfrov.com.androidhomework.model.data.Dog;


public interface DogView extends MvpView {




    void setDogList();

    void showDogDetails(Dog dogDetails);

    void stopRefresh();

    void showError(String message);

    void saveDogImages(String DogImage);

    void updateName();


}
