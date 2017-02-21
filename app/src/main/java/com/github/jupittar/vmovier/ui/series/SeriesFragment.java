package com.github.jupittar.vmovier.ui.series;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.jupittar.vmovier.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SeriesFragment extends Fragment {


  public SeriesFragment() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_series, container, false);
  }

}
