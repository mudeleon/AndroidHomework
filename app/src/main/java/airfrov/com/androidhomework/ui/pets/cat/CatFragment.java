package airfrov.com.androidhomework.ui.pets.cat;

import android.app.Dialog;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import airfrov.com.androidhomework.databinding.ActivityPetCatBinding;
import airfrov.com.androidhomework.databinding.DialogPetCatBinding;
import airfrov.com.androidhomework.model.data.Cat;
import airfrov.com.androidhomework.util.GridSpacingItemDecoration;
import io.realm.Realm;
import io.realm.RealmResults;


public class CatFragment
        extends MvpViewStateFragment<CatView, CatPresenter>
        implements SwipeRefreshLayout.OnRefreshListener, CatView {

    private static final String TAG = CatFragment.class.getSimpleName();
    private ActivityPetCatBinding binding;
    private Realm realm;
    private RealmResults<Cat> catRealmResults;
    public String id;
    private CatAdapter catListAdapter;
    private List<String> catImages;
    private DialogPetCatBinding dialogBinding;
    private Dialog dialog;



    public CatFragment(){

    }





    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

    }




    @NonNull
    @Override
    public ViewState<CatView> createViewState() {
        setRetainInstance(true);
        return new CatViewState();
    }

    @Override
    public void onNewViewStateInstance() {

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        binding = DataBindingUtil.inflate(inflater, R.layout.activity_pet_cat, container, false);



        return binding.getRoot();
    }



    @Override
    public void onStart() {
        super.onStart();

        realm = Realm.getDefaultInstance();

        //Initializing Cat List
        presenter.onStart();

        //Initializing Cat Adapter and RecyclerView
        catListAdapter = new CatAdapter(getActivity(), getMvpView());
        binding.recyclerView.setAdapter(catListAdapter);


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


        catImages = new ArrayList<>();




        }


    @NonNull
    @Override
    public CatPresenter createPresenter() {
        return new CatPresenter();
    }


    @Override
    public void onResume() {
        super.onResume();
        catRealmResults = realm.where(Cat.class).findAll();
        if(catRealmResults.isEmpty())
            loadInitialData();
        else
            setCatList();
    }


    @Override
    public void onDestroy() {
        presenter.onStop();
        catRealmResults.removeChangeListeners();
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
        catImages.clear();
        presenter.getCatImages();
    }

    @Override
    public void saveCatImages(String catImage) {

        catImages.add(catImage);

        if(catImages.size()==10)
            presenter.loadCatList(catImages);
        else
            presenter.getCatImages();
    }



    @Override
    public void setCatList(){

       catListAdapter.setAttendeeResult(realm.copyToRealmOrUpdate(catRealmResults.where()
               .findAll()));//Sorted("eventDateFrom", Sort.ASCENDING)));
        catListAdapter.notifyDataSetChanged();
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
    public void showCatDetails(final Cat cat) {




      dialog = new Dialog(getContext(),R.style.AppTheme_NoActionBar);



        dialogBinding = DataBindingUtil.inflate(
                getLayoutInflater(),
                R.layout.dialog_pet_cat,
                null,
                false);
        dialogBinding.setCat(cat);
        dialogBinding.setView(getMvpView());

        dialogBinding.toolbar.setNavigationIcon(R.drawable.ic_arrow_back_gray);

        dialogBinding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        Glide.with(this)
                .load(cat.getCatImage())
                .centerCrop()
                .error(R.drawable.pet_placeholder)
                .into(dialogBinding.catEditImage);



        dialogBinding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                presenter.updatePetName(dialogBinding.etPetName.getText().toString(), cat.getCatId());

            }
        });






        dialog.setContentView(dialogBinding.getRoot());
        dialog.setCancelable(false);
        dialog.show();
    }


    @Override
    public void updateName(){

        dialog.dismiss();
        setCatList();
    }


}
