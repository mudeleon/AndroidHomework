package airfrov.com.androidhomework.ui.pets.fox;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.hannesdorfmann.mosby.mvp.viewstate.RestorableViewState;


class FoxViewState implements RestorableViewState<FoxView> {
    @Override
    public void saveInstanceState(@NonNull Bundle out) {

    }

    @Override
    public RestorableViewState<FoxView> restoreInstanceState(Bundle in) {
        return this;
    }

    @Override
    public void apply(FoxView view, boolean retained) {

    }
}
