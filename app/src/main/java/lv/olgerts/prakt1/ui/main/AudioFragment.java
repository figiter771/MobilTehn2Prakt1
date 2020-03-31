package lv.olgerts.prakt1.ui.main;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lv.olgerts.prakt1.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AudioFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AudioFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AudioFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    private FirebaseAnalytics mFirebaseAnalytics;

    private static final String LOG_TAG = "AudioRecordTest";
    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;
    private static String fileName = null;
    private List<String> fileNameList =new ArrayList<>();
    private List<String> givenButtonNames = new ArrayList<>();

    private EditText textField = null;
    private MediaRecorder recorder = null;
    private MediaPlayer   player = null;
    private boolean permissionToRecordAccepted = false;
    private String [] permissions = {Manifest.permission.RECORD_AUDIO};

    private boolean startedRecording = false;
    private boolean startedPlaying = false;

    AudioFragmentListAdapter audioListAdapter;
    ListView listV;


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case REQUEST_RECORD_AUDIO_PERMISSION:
                permissionToRecordAccepted  = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                break;
        }
        if (!permissionToRecordAccepted ){
            Toast.makeText(getActivity(),"No premmision to record audio!",Toast.LENGTH_LONG).show();
        }

    }

    public AudioFragment() {
        // Required empty public constructor
    }

    public static AudioFragment newInstance() {
        AudioFragment fragment = new AudioFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(getContext());

        ActivityCompat.requestPermissions(getActivity(), permissions, REQUEST_RECORD_AUDIO_PERMISSION);



        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_audio, container, false);
        final Button recordButton = view.findViewById(R.id.startRecord);
//        final Button playButton = view.findViewById(R.id.startPlayback);
        textField = view.findViewById(R.id.audioNameInput);
        listV  = view.findViewById(R.id.recording_list);
        audioListAdapter = new AudioFragmentListAdapter(getContext(),fileNameList, givenButtonNames);
        listV.setAdapter(audioListAdapter);

        recordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (startedRecording == false) {
                    startRecording();
                    recordButton.setText("Stop Record");
                    startedRecording = true;
                }
                else {
                    stopRecording();
                    recordButton.setText("Record");
                    startedRecording = false;
                }

            }
        });

//        playButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (startedPlaying == false) {
//                    startPlaying();
//                    playButton.setText("Stop Play");
//                    startedPlaying = true;
//                }
//                else {
//                    stopPlaying();
//                    playButton.setText("Play");
//                    startedPlaying = false;
//                }
//            }
//        });




        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }


    private void startPlaying() {
//        player = new MediaPlayer();
//        try {
//            player.setDataSource(fileName);
//            player.prepare();
//            player.start();
//        } catch (IOException e) {
//            Log.e(LOG_TAG, "prepare() failed");
//        }
    }

    private void stopPlaying() {
//        player.release();
//        player = null;
    }

    private void startRecording() {
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);

        fileName = getActivity().getExternalCacheDir().getAbsolutePath();
        Date date = new Date();
        fileName += "/audiorecordtest"+ date.getTime()+  ".3gp";
        recorder.setOutputFile(fileName);
        fileNameList.add(fileName);

        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            recorder.prepare();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }

        recorder.start();
    }

    private void stopRecording() {
        recorder.stop();
        recorder.release();
        recorder = null;
        updateListView(fileNameList);
    }
    @Override
    public void onStop() {
        super.onStop();
        if (recorder != null) {
            recorder.release();
            recorder = null;
        }

        if (player != null) {
            player.release();
            player = null;
        }
    }
    private void updateListView(List<String> list) {
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, fileName);
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, textField.getText().toString());
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "String");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
//        mFirebaseAnalytics.logEvent("select_content", bundle);
        givenButtonNames.add(textField.getText().toString());
        Log.d("AudioFragment", "givenButtonName"+ givenButtonNames);
        audioListAdapter.notifyDataSetChanged();
        listV.setAdapter(audioListAdapter);
    }
}
