package com.travel.feature.transportation.presentation;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.travel.feature.transportation.R;

public class TransportationBlankFragment extends Fragment {

    private static final String ARG_MESSAGE = "message";

    String mParam1;

    public TransportationBlankFragment() {
    }

    public static TransportationBlankFragment newInstance(String param1) {
        TransportationBlankFragment fragment = new TransportationBlankFragment();
        Bundle args = new Bundle();
        args.putString(ARG_MESSAGE, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (TextUtils.isEmpty(mParam1) && getArguments() != null) {
            mParam1 = getArguments().getString(ARG_MESSAGE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_blank, container, false);
        TextView textView = rootView.findViewById(R.id.textView);
        if (!TextUtils.isEmpty(mParam1)) {
            textView.setText(mParam1);
        }
        return rootView;
    }
}
