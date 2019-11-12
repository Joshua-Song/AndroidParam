package com.joshua.androidparam;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.joshua.androidparam.adapter.ParamListViewAdapter;
import com.joshua.androidparam.base.BaseActivity;
import com.joshua.androidparam.contract.MainActivityContract;
import com.joshua.androidparam.presenter.MainActivityPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by joshua on 2019-11-11
 * Describe:
 */
public class MainActivity extends BaseActivity<MainActivityPresenter> implements MainActivityContract.View {

    @BindView(R.id.lv_param_list_view)
    public ListView listView;
    private ParamListViewAdapter mParamListViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

//        try {
//            Method[] methods = Class.forName("android.telephony.TelephonyManager").getDeclaredMethods();
//            for (Method method:methods
//                 ) {
//                System.out.println(">>>>>>>" + method.getName());
//            }
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
        onPrepare();
    }

    private void onPrepare() {
        mParamListViewAdapter = new ParamListViewAdapter(this, mIPresenter.getParamList());
        listView.setAdapter(mParamListViewAdapter);
        mIPresenter.loadParam(this);
    }

    @Override
    public void refreshParamList() {
        mParamListViewAdapter.notifyDataSetChanged();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.refresh:
                mIPresenter.loadParam(this);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}