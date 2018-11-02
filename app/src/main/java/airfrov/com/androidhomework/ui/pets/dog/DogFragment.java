package airfrov.com.androidhomework.ui.pets.dog;

import android.app.Dialog;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.hannesdorfmann.mosby.mvp.viewstate.MvpViewStateFragment;
import com.hannesdorfmann.mosby.mvp.viewstate.ViewState;

import java.util.ArrayList;
import java.util.List;

import airfrov.com.androidhomework.R;
import airfrov.com.androidhomework.databinding.ActivityPetDogBinding;
import airfrov.com.androidhomework.databinding.DialogPetDogBinding;
import airfrov.com.androidhomework.model.data.Dog;
import io.realm.Realm;
import io.realm.RealmResults;


public class DogFragment
        extends MvpViewStateFragment<DogView, DogPresenter>
        implements SwipeRefreshLayout.OnRefreshListener, DogView {

    private static final String TAG = DogFragment.class.getSimpleName();
    private ActivityPetDogBinding binding;
    private Realm realm;
    private RealmResults<Dog> dogRealmResults;
    public String id;
    private DogAdapter dogListAdapter;
    private List<String> dogImages;
    private DialogPetDogBinding dialogBinding;
    private Dialog dialog;


    public DogFragment(){

    }





    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

    }




    @NonNull
    @Override
    public ViewState<DogView> createViewState() {
        setRetainInstance(true);
        return new DogViewState();
    }

    @Override
    public void onNewViewStateInstance() {

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        binding = DataBindingUtil.inflate(inflater, R.layout.activity_pet_dog, container, false);
        return binding.getRoot();
    }



    @Override
    public void onStart() {
        super.onStart();

        realm = Realm.getDefaultInstance();

        //Initializing Dog List
        presenter.onStart();

        //Initializing Dog Adapter and RecyclerView
        dogListAdapter = new DogAdapter(getActivity(), getMvpView());
        binding.recyclerView.setAdapter(dogListAdapter);


        //Initilizing Swipe Refresh
        binding.swipeRefreshLayout.setOnRefreshListener(this);

        binding.recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        binding.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int topRowVerticalPosition = (recyclerView == null || recyclerView.getChildCount() == 0)
                        ? 0 : recyclerView.getChildAt(0).getTop();
                binding.swipeRefreshLayout.setEnabled(topRowVerticalPosition >= 0);
            }
        });


        dogImages = new ArrayList<>();


        }


    @NonNull
    @Override
    public DogPresenter createPresenter() {
        return new DogPresenter();
    }


    @Override
    public void onResume() {
        super.onResume();
        dogRealmResults = realm.where(Dog.class).findAll();
        if(dogRealmResults.isEmpty())
            loadInitialData();
        else
            setDogList();
    }


    @Override
    public void onDestroy() {
        presenter.onStop();
        dogRealmResults.removeChangeListeners();
        realm.close();
        super.onDestroy();
    }


    @Override
    public void onRefresh() {
       loadInitialData();
    }



    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }


    public void loadInitialData()
    {
        dogImages.clear();
        presenter.getDogImages();
    }

    @Override
    public void saveDogImages(String dogImage) {

        dogImages.add(dogImage);

        if(dogImages.size()==10)
            presenter.loadDogList(dogImages);
        else
            presenter.getDogImages();
    }



    @Override
    public void setDogList(){

       dogListAdapter.setAttendeeResult(realm.copyToRealmOrUpdate(dogRealmResults.where()
               .findAll()));//Sorted("eventDateFrom", Sort.ASCENDING)));
        dogListAdapter.notifyDataSetChanged();
    }




    @Override
    public void stopRefresh() {
        binding.swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showError(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }





    @Override
    public void showDogDetails(final Dog dog) {



        dialog = new Dialog(getContext(),R.style.AppTheme_NoActionBar);


        dialogBinding = DataBindingUtil.inflate(
                getLayoutInflater(),
                R.layout.dialog_pet_dog,
                null,
                false);
        dialogBinding.setDog(dog);
        dialogBinding.setView(getMvpView());

        dialogBinding.toolbar.setNavigationIcon(R.drawable.ic_arrow_back_gray);

        dialogBinding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        Glide.with(this)
                .load(dog.getDogImage())
                .centerCrop()
                .error(R.drawable.pet_placeholder)
                .into(dialogBinding.dogEditImage);



        dialogBinding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                presenter.updatePetName(dialogBinding.etPetName.getText().toString(), dog.getDogId());

            }
        });






        dialog.setContentView(dialogBinding.getRoot());
        dialog.setCancelable(false);
        dialog.show();
    }


    @Override
    public void updateName(){

        dialog.dismiss();
        setDogList();
    }


}





