package airfrov.com.androidhomework.ui.pets.dog;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.hannesdorfmann.mosby.mvp.viewstate.RestorableViewState;


class DogViewState implements RestorableViewState<DogView> {
    @Override
    public void saveInstanceState(@NonNull Bundle out) {

    }

    @Override
    public RestorableViewState<DogView> restoreInstanceState(Bundle in) {
        return this;
    }

    @Override
    public void apply(DogView view, boolean retained) {

    }
}
