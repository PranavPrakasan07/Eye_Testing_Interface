package com.example.eyetestinginterface;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    TextView logout, username, email;
    ImageView profile_photo;

    LinearLayout animation_layout;
    ScrollView profile_layout;
    RelativeLayout profile_fragment;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        username = view.findViewById(R.id.user_name);
        email = view.findViewById(R.id.email);
        profile_photo = view.findViewById(R.id.profile_photo);

        profile_layout = view.findViewById(R.id.profile_layout);
        animation_layout = view.findViewById(R.id.animation_layout);

        profile_fragment = view.findViewById(R.id.profile_fragment);

//        username.setText(Objects.requireNonNull(LoginActivity.auth.getCurrentUser()).getDisplayName());
//        email.setText(LoginActivity.auth.getCurrentUser().getEmail());

        profile_photo.setClipToOutline(true);

        try {
            Picasso.get().load(Objects.requireNonNull(LoginActivity.auth.getCurrentUser()).getPhotoUrl())
                    .into(profile_photo);
        } catch (Exception e) {
            e.printStackTrace();
        }

        email.setText(Objects.requireNonNull(LoginActivity.auth.getCurrentUser().getEmail()));
        username.setText(LoginActivity.auth.getCurrentUser().getDisplayName());

        profile_photo.setOnClickListener(v -> {

            profile_fragment.setBackgroundColor(Color.parseColor("#121212"));

            profile_layout.setVisibility(View.GONE);
            profile_photo.setVisibility(View.INVISIBLE);

            animation_layout.setVisibility(View.VISIBLE);

            Handler handler = new Handler();

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(getContext(), LoginActivity.class));
                }
            }, 2000);

//                LoginActivity.auth.signOut();
//                Toast.makeText(getActivity(), "Clicked", Toast.LENGTH_SHORT).show();
//                startActivity(new Intent(getContext(), LoginActivity.class));
        });



        // Inflate the layout for this fragment
        return view;
    }
}