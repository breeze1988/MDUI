package mduicom.breeze.mdui.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
public class GlideRecycleviewFragment extends Fragment {


    @Bind(R.id.glide_recycleview)
    RecyclerView glideRecycleview;
    @Bind(R.id.fab)
    FloatingActionButton fab;

    public GlideRecycleviewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_glide_recycleview, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        glideRecycleview.setLayoutManager(linearLayoutManager);
        glideRecycleview.setAdapter(new GlideAdapter(getActivity()));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private class GlideAdapter extends RecyclerView.Adapter<GlideAdapter.GlideImageHolder> {

        public String[] eatFoodyImages = {
                "http://i.imgur.com/rFLNqWI.jpg",
                "http://i.imgur.com/C9pBVt7.jpg",
                "http://i.imgur.com/rT5vXE1.jpg",
                "http://i.imgur.com/aIy5R2k.jpg",
                "http://i.imgur.com/MoJs9pT.jpg",
                "http://i.imgur.com/S963yEM.jpg",
                "http://i.imgur.com/rLR2cyc.jpg",
                "http://i.imgur.com/SEPdUIx.jpg",
                "http://i.imgur.com/aC9OjaM.jpg",
                "http://i.imgur.com/76Jfv9b.jpg",
                "http://i.imgur.com/fUX7EIB.jpg",
                "http://i.imgur.com/syELajx.jpg",
                "http://i.imgur.com/COzBnru.jpg",
                "http://i.imgur.com/Z3QjilA.jpg",
        };

        public Context context;
//        @Bind(R.id.glide_item_imageview)
//        ImageView glideItemImageview;

        public GlideAdapter(Context context) {
            this.context = context;
        }

        @Override
        public GlideImageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.adapter_glide_imageview_layout, parent, false);
            return new GlideImageHolder(view);
        }

        @Override
        public void onBindViewHolder(GlideImageHolder holder, int position) {
            String imageUrl = eatFoodyImages[position];
            Glide.with(context)
                    .load(imageUrl)
                    .into(holder.imageView);
        }

        @Override
        public int getItemCount() {
            return eatFoodyImages.length;
        }

        public class GlideImageHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            private ImageView imageView;

            public GlideImageHolder(View itemView) {
                super(itemView);
                imageView = (ImageView) itemView.findViewById(R.id.glide_item_imageview);
                imageView.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                if (v == imageView) {

                }
            }
        }
    }
}
