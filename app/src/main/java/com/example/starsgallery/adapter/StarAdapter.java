package com.example.starsgallery.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.starsgallery.R;
import com.example.starsgallery.beans.Star;
import com.example.starsgallery.service.StarService;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class StarAdapter extends RecyclerView.Adapter<StarAdapter.StarViewHolder> implements Filterable {

    private final Context context;
    private final List<Star> stars;
    private List<Star> starsFilter;
    private NewFilter mFilter;

    public StarAdapter(Context context, List<Star> stars) {
        this.context = context;
        this.stars = stars;
        this.starsFilter = new ArrayList<>(stars);
        this.mFilter = new NewFilter();
    }

    @NonNull
    @Override
    public StarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_start, parent, false);
        return new StarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StarViewHolder holder, int position) {
        Star star = starsFilter.get(position);
        holder.tvName.setText(star.getName().toUpperCase());
        holder.ratingBar.setRating(star.getRating());
        
        Glide.with(context)
                .load(star.getImg())
                .placeholder(R.mipmap.ic_launcher)
                .centerCrop()
                .into(holder.imgStar);

        holder.itemView.setOnClickListener(v -> showEditDialog(star, position));
    }

    private void showEditDialog(Star star, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.star_edit_dialog, null);
        RatingBar ratingBar = view.findViewById(R.id.dialog_rating);
        TextView title = view.findViewById(R.id.dialog_title);
        
        title.setText("Modifier la note de " + star.getName());
        ratingBar.setRating(star.getRating());

        new AlertDialog.Builder(context)
                .setView(view)
                .setPositiveButton("Valider", (dialog, which) -> {
                    star.setRating(ratingBar.getRating());
                    StarService.getInstance().update(star);
                    notifyItemChanged(position);
                })
                .setNegativeButton("Annuler", null)
                .show();
    }

    @Override
    public int getItemCount() { return starsFilter.size(); }

    @Override
    public Filter getFilter() {
        return mFilter;
    }

    public static class StarViewHolder extends RecyclerView.ViewHolder {
        CircleImageView imgStar;
        TextView tvName;
        RatingBar ratingBar;

        public StarViewHolder(@NonNull View itemView) {
            super(itemView);
            imgStar   = itemView.findViewById(R.id.imgStar);
            tvName    = itemView.findViewById(R.id.tvName);
            ratingBar = itemView.findViewById(R.id.rating);
        }
    }

    public class NewFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<Star> filtered = new ArrayList<>();
            if (charSequence == null || charSequence.length() == 0) {
                filtered.addAll(stars);
            } else {
                String filterPattern = charSequence.toString().toLowerCase().trim();
                for (Star s : stars) {
                    if (s.getName().toLowerCase().contains(filterPattern)) {
                        filtered.add(s);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filtered;
            results.count = filtered.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            starsFilter = (List<Star>) filterResults.values;
            notifyDataSetChanged();
        }
    }
}