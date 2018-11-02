package airfrov.com.androidhomework.ui.pets.fox;

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
import airfrov.com.androidhomework.databinding.ActivityPetFoxBinding;
import airfrov.com.androidhomework.databinding.DialogPetFoxBinding;
import airfrov.com.androidhomework.model.data.Fox;
import io.realm.Realm;
import io.realm.RealmResults;


public class FoxFragment
        extends MvpViewStateFragment<FoxView, FoxPresenter>
        implements SwipeRefreshLayout.OnRefreshListener, FoxView {

    private static final String TAG = FoxFragment.class.getSimpleName();
    private ActivityPetFoxBinding binding;
    private Realm realm;
    private RealmResults<Fox> foxRealmResults;
    public String id;
    private FoxAdapter foxListAdapter;
    private List<String> foxImages;
    private DialogPetFoxBinding dialogBinding;
    private Dialog dialog;



    public FoxFragment(){

    }





    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

    }




    @NonNull
    @Override
    public ViewState<FoxView> createViewState() {
        setRetainInstance(true);
        return new FoxViewState();
    }

    @Override
    public void onNewViewStateInstance() {

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        binding = DataBindingUtil.inflate(inflater, R.layout.activity_pet_fox, container, false);
        return binding.getRoot();
    }



    @Override
    public void onStart() {
        super.onStart();

        realm = Realm.getDefaultInstance();

        //Initializing Fox List
        presenter.onStart();

        //Initializing Fox Adapter and RecyclerView
        foxListAdapter = new FoxAdapter(getActivity(), getMvpView());
        binding.recyclerView.setAdapter(foxListAdapter);


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


        foxImages = new ArrayList<>();


        }


    @NonNull
    @Override
    public FoxPresenter createPresenter() {
        return new FoxPresenter();
    }


    @Override
    public void onResume() {
        super.onResume();
        foxRealmResults = realm.where(Fox.class).findAll();
        if(foxRealmResults.isEmpty())
            loadInitialData();
        else
            setFoxList();
    }


    @Override
    public void onDestroy() {
        presenter.onStop();
        foxRealmResults.removeChangeListeners();
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
        foxImages.clear();
        presenter.getFoxImages();
    }

    @Override
    public void saveFoxImages(String foxImage) {

        foxImages.add(foxImage);

        if(foxImages.size()==10)
            presenter.loadFoxList(foxImages);
        else
            presenter.getFoxImages();
    }



    @Override
    public void setFoxList(){

       foxListAdapter.setAttendeeResult(realm.copyToRealmOrUpdate(foxRealmResults.where()
               .findAll()));//Sorted("eventDateFrom", Sort.ASCENDING)));
        foxListAdapter.notifyDataSetChanged();
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
    public void showFoxDetails(final Fox fox) {


        dialog = new Dialog(getContext(),R.style.AppTheme_NoActionBar);


        dialogBinding = DataBindingUtil.inflate(
                getLayoutInflater(),
                R.layout.dialog_pet_fox,
                null,
                false);
        dialogBinding.setFox(fox);
        dialogBinding.setView(getMvpView());

        dialogBinding.toolbar.setNavigationIcon(R.drawable.ic_arrow_back_gray);

        dialogBinding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        Glide.with(this)
                .load(fox.getFoxImage())
                .centerCrop()
                .error(R.drawable.pet_placeholder)
                .into(dialogBinding.foxEditImage);



        dialogBinding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                presenter.updatePetName(dialogBinding.etPetName.getText().toString(), fox.getFoxId());

            }
        });






        dialog.setContentView(dialogBinding.getRoot());
        dialog.setCancelable(false);
        dialog.show();
    }


    @Override
    public void updateName(){

        dialog.dismiss();
        setFoxList();
    }


}





