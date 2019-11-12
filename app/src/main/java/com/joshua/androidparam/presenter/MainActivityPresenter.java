package com.joshua.androidparam.presenter;

import android.content.Context;

import com.joshua.androidparam.MainActivity;
import com.joshua.androidparam.base.BasePresenter;
import com.joshua.androidparam.bean.ParamBean;
import com.joshua.androidparam.contract.MainActivityContract;
import com.joshua.androidparam.model.MainActivityModel;

import java.util.List;

/**
 * Created by joshua on 2019-11-11
 * Describe:
 */
public class MainActivityPresenter extends BasePresenter<MainActivity> implements MainActivityContract.Presenter {

    private MainActivityModel mMainActivityModel = new MainActivityModel();

    @Override
    public List<ParamBean> getParamList() {
        return mMainActivityModel.getParamList();
    }

    @Override
    public void loadParam(Context context) {
        mMainActivityModel.loadParam(context, new MainActivityContract.Model.ModelListener() {
            @Override
            public void onSuccess(List<ParamBean> paramList) {
                mIView.get().refreshParamList();
            }

            @Override
            public void onFail(String msg) {

            }
        });
    }
}
