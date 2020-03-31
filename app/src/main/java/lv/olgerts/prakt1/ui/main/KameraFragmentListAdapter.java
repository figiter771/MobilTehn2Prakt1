package lv.olgerts.prakt1.ui.main;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import lv.olgerts.prakt1.R;

public class KameraFragmentListAdapter extends RecyclerView.Adapter<KameraFragmentListAdapter.KameraFragmentViewHolder> {
    List<Uri> uriList;
    Context context;
    LayoutInflater inflter;

    public KameraFragmentListAdapter (Context context, List<Uri> uriList) {
    this.uriList = uriList;
    this.context = context;
    inflter = (LayoutInflater.from(context));

    }

    @NonNull
    @Override
    public KameraFragmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = inflter.inflate(R.layout.activity_gridview, null);
        v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_gridview,parent, false);
        return new KameraFragmentViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull KameraFragmentViewHolder holder, int position) {
        holder.img.setImageURI(uriList.get(position));
        Log.d("Adapter", ""+uriList.get(position));
    }

    @Override
    public int getItemCount() {
        return uriList.size();
    }

    public class KameraFragmentViewHolder extends RecyclerView.ViewHolder {
        public ImageView img;
        public KameraFragmentViewHolder(@NonNull View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.gridView_icon);

        }
    }
}
