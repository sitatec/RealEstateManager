package com.berete.realestatemanager.ui.list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.berete.realestatemanager.R;
import com.berete.realestatemanager.domain.models.Photo;
import com.bumptech.glide.Glide;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PhotoListAdapter extends RecyclerView.Adapter<PhotoListAdapter.PhotoViewHolder> {

  private List<Photo> photoList;

  public PhotoListAdapter(List<Photo> photoList) {
    this.photoList = photoList;
  }

  @NotNull
  @Override
  public PhotoViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
    final View photoView =
        LayoutInflater.from(parent.getContext()).inflate(R.layout.photo_list_item, parent, false);

    return new PhotoViewHolder(photoView);
  }

  @Override
  public void onBindViewHolder(@NotNull PhotoViewHolder holder, int position) {
    holder.updateView(photoList.get(position));
  }

  @Override
  public int getItemCount() {
    return photoList.size();
  }

  public void updateList(List<Photo> photos){
    photoList = photos;
    notifyDataSetChanged();
  }

  public static class PhotoViewHolder extends RecyclerView.ViewHolder {

    private final ImageView photoView;
    private final TextView descriptionText;

    public PhotoViewHolder(@NotNull View itemView) {
      super(itemView);
      photoView = itemView.findViewById(R.id.photo);
      descriptionText = itemView.findViewById(R.id.description);
    }

    public void updateView(Photo photo){
      Glide.with(photoView.getContext()).load(photo.getUrl()).centerCrop().into(photoView);
      descriptionText.setText(photo.getDescription());
    }
  }
}
