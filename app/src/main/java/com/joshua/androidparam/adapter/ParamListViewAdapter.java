package com.joshua.androidparam.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.joshua.androidparam.R;
import com.joshua.androidparam.bean.ParamBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by joshua on 2019-11-11
 * Describe:
 */
public class ParamListViewAdapter extends BaseAdapter {

    private List<ParamBean> mListParam;
    private Context mContext;

    public ParamListViewAdapter(Context context, List<ParamBean> listParam) {
        this.mContext = context;
        this.mListParam = listParam;
    }

    @Override
    public int getCount() {
        return mListParam == null ? 0 : mListParam.size();
    }

    @Override
    public ParamBean getItem(int position) {
        return mListParam == null ? null : mListParam.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ItemViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.activity_main_param_list_view_item, null);
            viewHolder = new ItemViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ItemViewHolder) convertView.getTag();
        }
        viewHolder.tvKey.setText(getItem(position).getKey());
        viewHolder.tvValue.setText(getItem(position).getValue());
        return convertView;
    }

    static class ItemViewHolder {
        @BindView(R.id.tv_param_list_key)
        TextView tvKey;
        @BindView(R.id.tv_param_list_value)
        TextView tvValue;

        ItemViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
