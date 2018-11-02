package airfrov.com.androidhomework.ui.pets.cat;

import com.hannesdorfmann.mosby.mvp.MvpView;

import airfrov.com.androidhomework.model.data.Cat;


public interface CatView extends MvpView {




    void setCatList();

    void showCatDetails(Cat catDetails);

    void stopRefresh();

    void showError(String message);

    void saveCatImages(String CatImage);

    void updateName();


}
