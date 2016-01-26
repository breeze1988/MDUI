package mduicom.breeze.mdui.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.Bind;
import butterknife.ButterKnife;
import mduicom.breeze.mdui.R;
import mduicom.breeze.mdui.adapter.SwipToRefreshRecyclerViewAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SwipToRefrshFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SwipToRefrshFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @Bind(R.id.swiperefreshlayout_recyclerview)
    RecyclerView swiperefreshlayoutRecyclerview;
    @Bind(R.id.swiperefreshlayout)
    SwipeRefreshLayout swiperefreshlayout;

    private SwipToRefreshRecyclerViewAdapter mSwipToRefreshRecyclerViewAdapter;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public SwipToRefrshFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SwipToRefrshFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SwipToRefrshFragment newInstance(String param1, String param2) {
        SwipToRefrshFragment fragment = new SwipToRefrshFragment();
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
        View view = inflater.inflate(R.layout.fragment_swip_to_refrsh, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        swiperefreshlayoutRecyclerview.setLayoutManager(layoutManager);
        setupAdapter();
        swiperefreshlayout.setColorSchemeResources(R.color.orange, R.color.green, R.color.blue);
        swiperefreshlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        setupAdapter();
                        swiperefreshlayout.setRefreshing(false);
                    }
                }, 2500);
            }
        });
    }

    private void setupAdapter() {
        mSwipToRefreshRecyclerViewAdapter = new SwipToRefreshRecyclerViewAdapter(getActivity());
        swiperefreshlayoutRecyclerview.setAdapter(mSwipToRefreshRecyclerViewAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
