package com.joshua.androidparam.interfaces;

import android.app.Activity;

/**
 * Created by joshua on 2019-11-11
 * Describe:
 */
public interface IView {

    // 绑定主持
    void bindPresenter();

    // 解绑主持
    void unbindPresenter();

    // 此方法是为了当Presenter中需要获取上下文对象时，传递上下文对象，而不是让Presenter直接持有上下文对象
    Activity getSelfActivity();
}
