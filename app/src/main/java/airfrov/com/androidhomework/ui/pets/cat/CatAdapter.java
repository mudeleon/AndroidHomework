package airfrov.com.androidhomework.ui.pets.cat;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import airfrov.com.androidhomework.R;
import airfrov.com.androidhomework.databinding.ItemPetCatBinding;
import airfrov.com.androidhomework.model.data.Cat;


public class CatAdapter extends RecyclerView.Adapter<CatAdapter.ViewHolder> {
    private List<Cat> cat;
    private final Context context;
    private final CatView view;
    private static final int VIEW_TYPE_DEFAULT = 0;


    public CatAdapter(Context context, CatView view) {
        this.context = context;
        this.view = view;
        cat = new ArrayList<>();
    }


    @Override
    public int getItemViewType(int position) {
        return VIEW_TYPE_DEFAULT;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemPetCatBinding itemCatBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.item_pet_cat,
                parent,
                false);

        return new ViewHolder(itemCatBinding);
    }

    @Override
    public void onBindViewHolder(CatAdapter.ViewHolder holder, int position) {
        holder.itemCatBinding.setCat(cat.get(position));
        holder.itemCatBinding.setView(view);




        Glide.with(holder.itemView.getContext())
                .load(cat.get(position).getCatImage())
                .centerCrop()
                .error(R.drawable.pet_placeholder)
                .into(holder.itemCatBinding.catImage);
    }


    public void clear() {
        cat.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return cat.size();
    }

    public void setAttendeeResult(List<Cat> event) {
        this.cat = event;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemPetCatBinding itemCatBinding;

        public ViewHolder(ItemPetCatBinding itemCatBinding) {
            super(itemCatBinding.getRoot());
            this.itemCatBinding = itemCatBinding;
        }



    }
}
