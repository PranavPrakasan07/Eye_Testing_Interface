package com.example.eyetestinginterface;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Source;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

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

    TextView username, email, contact, current_address;
    ImageView profile_photo;

    LinearLayout animation_layout;
    ScrollView profile_layout;
    RelativeLayout profile_fragment;

    String email_text = null;
    String phone_text = null;
    String address_text = null;
    String mobile_text = null;
    String photo_url = null;

    Map<String, Object> details = new HashMap<>();

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
            // TODO: Rename and change types of parameters
            String mParam1 = getArguments().getString(ARG_PARAM1);
            String mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        username = view.findViewById(R.id.user_name);
        email = view.findViewById(R.id.email);
        contact = view.findViewById(R.id.contact);
        current_address = view.findViewById(R.id.current_address);

        profile_photo = view.findViewById(R.id.profile_photo);
        profile_layout = view.findViewById(R.id.profile_layout);
        animation_layout = view.findViewById(R.id.animation_layout);
        profile_fragment = view.findViewById(R.id.profile_fragment);

        profile_photo.setClipToOutline(true);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Source source = Source.DEFAULT;

        db.collection("users").document(Home.userid)
                .get(source)
                .addOnSuccessListener(documentSnapshot -> {
                    details = documentSnapshot.getData();

                    assert details != null;
                    email_text = (String) details.get("email");
                    phone_text = (String) details.get("phone");
                    address_text = (String) details.get("address");
                    photo_url = (String) details.get("photo");
                    mobile_text = (String) details.get("mobile");

                    if (email_text != null) {
                        email.setText(email_text);
                    }
                    if (phone_text != null) {
                        contact.setText(phone_text);
                    }
                    if (mobile_text != null) {
                        contact.setText(mobile_text);
                    }
                    if (address_text != null) {
                        current_address.setText(address_text);
                    }
                    if (photo_url != null) {
                        Picasso.get().load(photo_url).into(profile_photo);
                    }
                }).addOnFailureListener(e -> Log.d("TAG", "Failed to read user data"));

        if (email_text != null) {
            email.setText(email_text);
        }
        if (phone_text != null) {
            contact.setText(email_text);
        }
        if (mobile_text != null) {
            contact.setText(email_text);
        }
        if (address_text != null) {
            current_address.setText(email_text);
        }
        if (photo_url != null) {
            Picasso.get().load(photo_url).into(profile_photo);
        }

        profile_photo.setOnClickListener(v ->
        {
            profile_fragment.setBackgroundColor(Color.parseColor("#121212"));

            profile_layout.setVisibility(View.GONE);
            profile_photo.setVisibility(View.INVISIBLE);

            animation_layout.setVisibility(View.VISIBLE);

            new Handler().postDelayed(() -> startActivity(new Intent(getContext(), LoginActivity.class)), 2000);
        });

        // Inflate the layout for this fragment
        return view;
    }
}