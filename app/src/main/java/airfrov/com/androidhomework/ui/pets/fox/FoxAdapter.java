package airfrov.com.androidhomework.ui.pets.fox;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import airfrov.com.androidhomework.R;
import airfrov.com.androidhomework.databinding.ItemPetFoxBinding;
import airfrov.com.androidhomework.model.data.Fox;


public class FoxAdapter extends RecyclerView.Adapter<FoxAdapter.ViewHolder> {
    private List<Fox> fox;
    private final Context context;
    private final FoxView view;
    private static final int VIEW_TYPE_DEFAULT = 0;


    public FoxAdapter(Context context, FoxView view) {
        this.context = context;
        this.view = view;
        fox = new ArrayList<>();
    }


    @Override
    public int getItemViewType(int position) {
        return VIEW_TYPE_DEFAULT;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemPetFoxBinding itemFoxBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.item_pet_fox,
                parent,
                false);

        return new ViewHolder(itemFoxBinding);
    }

    @Override
    public void onBindViewHolder(FoxAdapter.ViewHolder holder, int position) {
        holder.itemFoxBinding.setFox(fox.get(position));
        holder.itemFoxBinding.setView(view);




        Glide.with(holder.itemView.getContext())
                .load(fox.get(position).getFoxImage())
                .centerCrop()
                .error(R.drawable.pet_placeholder)
                .into(holder.itemFoxBinding.foxImage);
    }


    public void clear() {
        fox.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return fox.size();
    }

    public void setAttendeeResult(List<Fox> event) {
        this.fox = event;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemPetFoxBinding itemFoxBinding;

        public ViewHolder(ItemPetFoxBinding itemFoxBinding) {
            super(itemFoxBinding.getRoot());
            this.itemFoxBinding = itemFoxBinding;
        }



    }
}
