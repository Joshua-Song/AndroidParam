package com.joshua.androidparam.contract;

import android.content.Context;

import com.joshua.androidparam.bean.ParamBean;

import java.util.List;

/**
 * Created by joshua on 2019-11-11
 * Describe:
 */
public interface MainActivityContract {
    interface View{
        void refreshParamList();
    }

    interface Presenter{
        List<ParamBean> getParamList();
        void loadParam(Context context);
    }

    interface Model{
        void loadParam(Context context, ModelListener modelListener);

        interface ModelListener{
            void onSuccess(List<ParamBean> paramList);
            void onFail(String msg);
        }
    }
}
