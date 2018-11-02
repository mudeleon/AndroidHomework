package airfrov.com.androidhomework.ui.pets.fox;

import com.hannesdorfmann.mosby.mvp.MvpView;

import airfrov.com.androidhomework.model.data.Fox;


public interface FoxView extends MvpView {




    void setFoxList();

    void showFoxDetails(Fox foxDetails);

    void stopRefresh();

    void showError(String message);

    void saveFoxImages(String FoxImage);

    void updateName();


}
