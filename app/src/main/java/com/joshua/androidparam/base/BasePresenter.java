package com.joshua.androidparam.base;

import com.joshua.androidparam.interfaces.IPresenter;
import com.joshua.androidparam.interfaces.IView;

import java.lang.ref.WeakReference;

/**
 * Created by joshua on 2019-11-11
 * Describe:
 */
public abstract class BasePresenter<V extends IView> implements IPresenter<V> {

    public static final String TAG = "BasePresenter";

    protected WeakReference<V> mIView;

    @Override
    public void attachView(V view) {
        mIView = new WeakReference<V>(view);
    }

    @Override
    public void detachView() {
        if (mIView != null) {
            mIView.clear();
            mIView = null;
        }
    }

    protected V getView() {
        if (mIView != null) {
            return mIView.get();
        }
        return null;
    }

    /**
     * 主要用于判断IView的生命周期是否结束，防止出现内存泄露状况
     *
     * @return
     */
    protected boolean isViewAttach() {
        return mIView != null && mIView.get() != null;
    }
}

