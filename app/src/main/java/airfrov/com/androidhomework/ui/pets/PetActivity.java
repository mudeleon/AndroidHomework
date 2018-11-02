package airfrov.com.androidhomework.ui.pets;

import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import airfrov.com.androidhomework.R;
import airfrov.com.androidhomework.databinding.ActivityPetMainBinding;
import io.realm.Realm;



public class PetActivity extends AppCompatActivity {

    private Realm realm;
    private ActivityPetMainBinding binding;



    @SuppressWarnings("ConstantConditions")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        realm = Realm.getDefaultInstance();


        binding = DataBindingUtil.setContentView(this, R.layout.activity_pet_main);
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setTitle("Random Pets");



        PetTabAdapter mAdapter = new PetTabAdapter(getSupportFragmentManager());

        binding.viewPager.setAdapter(mAdapter);
        binding.tabs.setupWithViewPager(binding.viewPager, true);
        //binding.tabs.setTabMode(TabLayout.MODE_SCROLLABLE);

    }




    @Override
    protected void onDestroy() {
        realm.close();
        super.onDestroy();
    }







}
