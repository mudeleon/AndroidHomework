package airfrov.com.androidhomework.ui.pets;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import airfrov.com.androidhomework.app.Constants;
import airfrov.com.androidhomework.ui.pets.cat.CatFragment;
import airfrov.com.androidhomework.ui.pets.dog.DogFragment;
import airfrov.com.androidhomework.ui.pets.fox.FoxFragment;

class PetTabAdapter extends FragmentStatePagerAdapter {

    public static final String[] PET_TITLES = {"CATS", "DOGS","FOXES"};
    public PetTabAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new CatFragment();
            case 1:
                return new DogFragment();

            case 2:
            return new FoxFragment();


            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return PET_TITLES.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return PET_TITLES[position];
    }
}
