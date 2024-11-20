package com.example.grampanchayatkouthaliapk;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class SchemeAdapter extends RecyclerView.Adapter<SchemeAdapter.SchemeViewHolder> {

    private final List<Scheme> schemeList;
    private final Context context;

    public SchemeAdapter(List<Scheme> schemeList, Context context) {
        this.schemeList = schemeList;
        this.context = context;
    }

    @NonNull
    @Override
    public SchemeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_scheme, parent, false);
        return new SchemeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SchemeViewHolder holder, int position) {
        Scheme scheme = schemeList.get(position);
        holder.schemeTitle.setText(scheme.getTitle());
        holder.schemeDescription.setText(scheme.getDescription());
        holder.schemeImage.setImageResource(scheme.getImageResId());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, SchemeDetailActivity.class);
            intent.putExtra("title", scheme.getTitle());
            intent.putExtra("image", scheme.getImageResId());
            intent.putExtra("info", scheme.getDescription());
            intent.putExtra("eligibility", scheme.getEligibility());
            intent.putExtra("requiredDocs", scheme.getRequiredDocs());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return schemeList.size();
    }

    public static class SchemeViewHolder extends RecyclerView.ViewHolder {

        TextView schemeTitle;
        TextView schemeDescription;
        ImageView schemeImage;

        public SchemeViewHolder(@NonNull View itemView) {
            super(itemView);
            schemeTitle = itemView.findViewById(R.id.schemeTitle);
            schemeDescription = itemView.findViewById(R.id.schemeDescription);
            schemeImage = itemView.findViewById(R.id.schemeImage);
        }
    }
}
