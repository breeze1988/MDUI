package mduicom.breeze.mdui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import butterknife.Bind;
import butterknife.ButterKnife;
import mduicom.breeze.mdui.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class GlideSigleImageFragment extends Fragment {


    @Bind(R.id.glide_imageview)
    ImageView glideImageview;

    public GlideSigleImageFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_glide_sigle_image, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        String internetUrl = "http://i.imgur.com/DvpvklR.png";

        Glide
                .with(getActivity())
                .load(internetUrl)
                .into(glideImageview);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
