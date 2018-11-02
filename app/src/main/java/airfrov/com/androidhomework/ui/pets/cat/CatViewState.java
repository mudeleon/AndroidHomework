package airfrov.com.androidhomework.ui.pets.cat;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.hannesdorfmann.mosby.mvp.viewstate.RestorableViewState;


class CatViewState implements RestorableViewState<CatView> {
    @Override
    public void saveInstanceState(@NonNull Bundle out) {

    }

    @Override
    public RestorableViewState<CatView> restoreInstanceState(Bundle in) {
        return this;
    }

    @Override
    public void apply(CatView view, boolean retained) {

    }
}
