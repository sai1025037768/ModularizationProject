package com.modularization.biz.service.transportation;

import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.facade.template.IProvider;

/**
 * @author baronzhang (baron[dot]zhanglei[at]gmail[dot]com)
 */
public interface TransportationProvider extends IProvider {

    TransportationData fetchTransportationData();

    Fragment getFragment(String routePath);
}
