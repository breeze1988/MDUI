package mduicom.breeze.mdui.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import mduicom.breeze.mdui.R;
import util.HttpUtil;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OkHttpTestFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OkHttpTestFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    @Bind(R.id.btn_okHttpGet)Button mOkHttpGetBtn;
    @Bind(R.id.btn_okHttpPost)Button mOkHttpPostBtn;
    @Bind(R.id.tx_okHttpGetResponse)TextView mOkHttpGetResponseTxt;
    @Bind(R.id.tx_okHttpPosteResponse)TextView mOkHttpPostResponseTxt;

    public OkHttpTestFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OkHttpTestFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OkHttpTestFragment newInstance(String param1, String param2) {
        OkHttpTestFragment fragment = new OkHttpTestFragment();
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
        View view = inflater.inflate(R.layout.fragment_ok_http_test, container, false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onClick(View v) {
        if(v == mOkHttpGetBtn){
            mOkHttpGetResponseTxt.setText(HttpUtil.getRun("https://raw.github.com/square/okhttp/master/README.md"));
        }else if( v == mOkHttpPostBtn){

        }
    }
}
