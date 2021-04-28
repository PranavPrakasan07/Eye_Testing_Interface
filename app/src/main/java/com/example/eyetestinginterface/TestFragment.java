package com.example.eyetestinginterface;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.ramotion.fluidslider.FluidSlider;

import kotlin.Unit;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TestFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TestFragment extends Fragment {

    final static String[] distance = {"20"};

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public TestFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TestFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TestFragment newInstance(String param1, String param2) {
        TestFragment fragment = new TestFragment();
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
        View view = inflater.inflate(R.layout.fragment_test, container, false);

        TextView distance_text = view.findViewById(R.id.distance_text);
        ImageButton take_test = view.findViewById(R.id.take_test_button);
        FluidSlider slider = view.findViewById(R.id.slider);

        slider.setPositionListener(pos -> {
            final String value = String.valueOf((int) (pos * 20));
            slider.setBubbleText(value);
            distance_text.setText(value);

            distance[0] = value;

            return Unit.INSTANCE;
        });

        take_test.setOnClickListener(v -> {

            Bundle bundle = new Bundle();
            Intent intent = new Intent(getActivity(), TestEvaluate.class);

            bundle.putString("distance", distance[0]);

            intent.putExtras(bundle);
            startActivity(intent);
        });


        return view;
    }
}