package com.codepath.festy.fragments;


import android.os.Bundle;

import android.util.Log;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.festy.R;
import com.codepath.festy.adapters.ActAdapter;
import com.codepath.festy.models.Act;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    public static final String TAG = "ProfileFragment";
    public static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 54;
    private TextView tvUsername;
    private ImageView ivUserImage;
    private Button btnUpload;
    private TextView tvLabel;
    private RecyclerView rvLineup;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvUsername = view.findViewById(R.id.tvUsername);
        ivUserImage = view.findViewById(R.id.ivUserImage);
        btnUpload = view.findViewById(R.id.btnUpload);
        tvLabel = view.findViewById(R.id.tvLabel);
        rvLineup = view.findViewById(R.id.rvLineup);

        List<Act> actData;

        //TODO: Get all Artists that the user selected and display
        //Step 1: get data from ListFragment
        //Step 2: filter data for only the artists that users selected
        //Step 3: put data into actData List
        //Step 4: display actData into rvLineup
        //Step 5: click button allows user to change profile picture (i.e change ivUserImage)
    }

}
