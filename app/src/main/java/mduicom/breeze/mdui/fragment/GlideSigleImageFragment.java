package mduicom.breeze.mdui.fragment;


import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;
import mduicom.breeze.mdui.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class GlideSigleImageFragment extends Fragment {


    @Bind(R.id.glide_imageview)
    ImageView glideImageview;
    @Bind(R.id.glide_resource_imageview)
    ImageView glideResourceImageview;
    @Bind(R.id.glide_file_imageview)
    ImageView glideFileImageview;
    @Bind(R.id.glide_uri_imageview)
    ImageView glideUriImageview;
    @Bind(R.id.glide_gif_imageview)
    ImageView glideGifImageview;

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

        Glide.with(getActivity())
                .load(internetUrl)
//                .placeholder(R.drawable.london_flat)
//                .error(R.drawable.ic_menu_gallery)
                .into(glideImageview);


        Glide.with(getActivity())
                .load(R.drawable.london_flat)
                .error(R.drawable.ic_menu_gallery)
                .into(glideResourceImageview);

        File file = new File("/storage/emulated/0/军人/file_346326346.jpg");
        Glide.with(getActivity())
                .load(file)
                .error(R.drawable.ic_menu_gallery)
                .into(glideFileImageview);

        Uri uri = resourceIdToUri(getActivity(), R.drawable.london_flat);

        Glide
                .with(getActivity())
                .load(uri)
                .into(glideUriImageview);

        String gifUrl = "http://i.kinja-img.com/gawker-media/image/upload/s--B7tUiM5l--/gf2r69yorbdesguga10i.gif";
        Glide
                .with(getActivity())
                .load(gifUrl)
                .into(glideGifImageview );


    }

    public static final String ANDROID_RESOURCE = "android.resource://";
    public static final String FOREWARD_SLASH = "/";

    private static Uri resourceIdToUri(Context context, int resourceId) {
        return Uri.parse(ANDROID_RESOURCE + context.getPackageName() + FOREWARD_SLASH + resourceId);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
