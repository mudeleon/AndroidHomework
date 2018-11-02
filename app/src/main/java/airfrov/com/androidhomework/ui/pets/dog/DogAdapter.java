package airfrov.com.androidhomework.ui.pets.dog;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import airfrov.com.androidhomework.R;
import airfrov.com.androidhomework.databinding.ItemPetDogBinding;
import airfrov.com.androidhomework.model.data.Dog;


public class DogAdapter extends RecyclerView.Adapter<DogAdapter.ViewHolder> {
    private List<Dog> dog;
    private final Context context;
    private final DogView view;
    private static final int VIEW_TYPE_DEFAULT = 0;


    public DogAdapter(Context context, DogView view) {
        this.context = context;
        this.view = view;
        dog = new ArrayList<>();
    }


    @Override
    public int getItemViewType(int position) {
        return VIEW_TYPE_DEFAULT;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemPetDogBinding itemDogBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.item_pet_dog,
                parent,
                false);

        return new ViewHolder(itemDogBinding);
    }

    @Override
    public void onBindViewHolder(DogAdapter.ViewHolder holder, int position) {
        holder.itemDogBinding.setDog(dog.get(position));
        holder.itemDogBinding.setView(view);




        Glide.with(holder.itemView.getContext())
                .load(dog.get(position).getDogImage())
                .centerCrop()
                .error(R.drawable.pet_placeholder)
                .into(holder.itemDogBinding.dogImage);
    }


    public void clear() {
        dog.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return dog.size();
    }

    public void setAttendeeResult(List<Dog> event) {
        this.dog = event;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemPetDogBinding  itemDogBinding;

        public ViewHolder(ItemPetDogBinding  itemDogBinding) {
            super(itemDogBinding.getRoot());
            this.itemDogBinding = itemDogBinding;
        }



    }
}
