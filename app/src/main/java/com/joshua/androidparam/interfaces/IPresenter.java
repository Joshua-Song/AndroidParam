package com.joshua.androidparam.interfaces;

/**
 * Created by joshua on 2019-11-11
 * Describe:
 */
public interface IPresenter<V> {

    // 附加视图
    void attachView(V view);

    // 分离视图
    void detachView();
}
