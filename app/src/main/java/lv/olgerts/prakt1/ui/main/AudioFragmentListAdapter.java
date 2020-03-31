package lv.olgerts.prakt1.ui.main;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import java.io.IOException;
import java.util.List;

import lv.olgerts.prakt1.R;

public class AudioFragmentListAdapter extends BaseAdapter {
    List<String> list;
    LayoutInflater inflater;
    Context context;
    List<String> names;
    private MediaPlayer player = null;
    public AudioFragmentListAdapter (Context applicatonContext, List<String> l,List<String> n) {
    this.list = l;
    this.context = applicatonContext;
    inflater = (LayoutInflater.from(applicatonContext));
    this.names = n;
    Log.d("AudioAdapter", "Given name: "+this.names);
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.audio_activity_listview, null);
        final Button btn = convertView.findViewById(R.id.audio_activity_listview_button);
        btn.setText(position +". "+names.get(position)+ " Play");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("", "onClick: "+ btn.getText() );
                if (btn.getText().equals(position +". "+names.get(position)+ " Play")) {
                    player = new MediaPlayer();
                    try {
                        player.setDataSource(list.get(position));
                        player.prepare();
                        player.start();
                        btn.setText(position +". "+names.get(position)+ " Stop");
                    } catch (IOException e) {
                        Log.e("COUGHT", "prepare() failed");
                    }

                }else {
                    if (btn.getText().equals(position +". "+names.get(position)+ " Stop")) {
                        player.release();
                        player = null;
                        btn.setText(position +". "+names.get(position)+ " Play");
                    }
                }

            }
        });


        return convertView;
    }
}
