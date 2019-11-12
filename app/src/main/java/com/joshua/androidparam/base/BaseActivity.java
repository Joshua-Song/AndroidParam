package com.joshua.androidparam.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.joshua.androidparam.interfaces.IPresenter;
import com.joshua.androidparam.interfaces.IView;

import java.lang.reflect.ParameterizedType;

/**
 * Created by joshua on 2019-11-11
 * Describe:
 */
public abstract class BaseActivity<P extends IPresenter> extends AppCompatActivity implements IView {

    protected P mIPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindPresenter();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindPresenter();
    }

    @Override
    public void bindPresenter() {
        ParameterizedType pType = (ParameterizedType) this.getClass().getGenericSuperclass();
        Class clazz = (Class<P>) pType.getActualTypeArguments()[0];
        try {
            mIPresenter = (P) clazz.newInstance();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        mIPresenter.attachView(this);
    }

    @Override
    public void unbindPresenter() {
        if (mIPresenter != null) {
            mIPresenter.detachView();
        }
    }

    @Override
    public Activity getSelfActivity() {
        return this;
    }
}

