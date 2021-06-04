package com.berete.realestatemanager.ui.edit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.berete.realestatemanager.R;
import com.berete.realestatemanager.domain.models.RealEstateAgent;
import com.bumptech.glide.Glide;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AgentSpinnerAdapter extends ArrayAdapter<RealEstateAgent> {

  public AgentSpinnerAdapter(@NonNull Context context, @NonNull List<RealEstateAgent> agentList) {
    super(context, 0, agentList);
  }

  @NonNull
  @Override
  public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
    if (convertView == null) {
      convertView =
          LayoutInflater.from(getContext())
              .inflate(R.layout.agent_spinner_item_layout, parent, false);
    }
    final ImageView agentPhoto = convertView.findViewById(R.id.agentPhoto);
    final TextView agentName = convertView.findViewById(R.id.agentName);
    final RealEstateAgent agent = getItem(position);
    agentName.setText(agent.getName());
    Glide.with(convertView).load(agent.getPhotoUrl()).centerCrop().into(agentPhoto);

    return convertView;
  }

  @Override
  public View getDropDownView(
      int position,
      @Nullable @org.jetbrains.annotations.Nullable View convertView,
      @NonNull @NotNull ViewGroup parent) {
    return getView(position, convertView, parent);
  }
}
