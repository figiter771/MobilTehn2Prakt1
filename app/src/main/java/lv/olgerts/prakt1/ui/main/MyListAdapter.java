package lv.olgerts.prakt1.ui.main;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.List;

import lv.olgerts.prakt1.R;

public class MyListAdapter extends BaseAdapter {
    Context context;
//    int logos[];
    List<Uri> uriList;
    LayoutInflater inflter;
//    public MyListAdapter(Context applicationContext, int[] logos) {
public MyListAdapter(Context applicationContext, List<Uri> uriList) {
        this.context = applicationContext;
//        this.logos = logos;
        this.uriList = uriList;
        inflter = (LayoutInflater.from(applicationContext));
    }
    @Override
    public int getCount() {
//        return logos.length;
        return  uriList.size();
    }
    @Override
    public Object getItem(int i) {
        return null;
    }
    @Override
    public long getItemId(int i) {
        return 0;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.activity_gridview, null); // inflate the layout
        ImageView icon = (ImageView) view.findViewById(R.id.gridView_icon); // get the reference of ImageView
//        icon.setImageResource(logos[i]); // set logo images
        icon.setImageURI(uriList.get(i));
        return view;
    }
}
