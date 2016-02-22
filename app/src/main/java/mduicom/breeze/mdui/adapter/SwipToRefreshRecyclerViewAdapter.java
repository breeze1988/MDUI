package mduicom.breeze.mdui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import mduicom.breeze.mdui.R;

/**
 * Created by breeze on 2016/1/26.
 */
public class SwipToRefreshRecyclerViewAdapter  extends RecyclerView.Adapter<SwipToRefreshRecyclerViewAdapter.CatNamesViewHolder>{

    private Context mContext;
    List<String> mDatas;

    public SwipToRefreshRecyclerViewAdapter(Context context){
        mContext = context;
        randomizeDatas();
    }

    @Override
    public CatNamesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_swip_refresh_view,parent,false);
        return new CatNamesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CatNamesViewHolder holder, int position) {
        String data = mDatas.get(position);
        holder.mCatNameTextView.setText(data);
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public void randomizeDatas(){
        mDatas = Arrays.asList(getDatasFromResouce());
        Collections.shuffle(mDatas);
    }

    private String[] getDatasFromResouce(){
        return mContext.getResources().getStringArray(R.array.swip_refresh_data);
    }

    public class CatNamesViewHolder extends RecyclerView.ViewHolder {
        TextView mCatNameTextView;

        public CatNamesViewHolder(View itemView) {
            super(itemView);
            mCatNameTextView = (TextView) itemView.findViewById(R.id.cat_name_textview);
        }
    }
}
