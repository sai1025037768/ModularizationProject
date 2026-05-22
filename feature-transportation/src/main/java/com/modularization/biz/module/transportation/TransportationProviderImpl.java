package com.modularization.biz.module.transportation;

import android.content.Context;

import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.modularization.biz.service.transportation.TransportationProvider;
import com.modularization.biz.service.transportation.TransportationData;
import com.modularization.biz.service.transportation.TransportationRouterTable;

/**
 * @author baronzhang (baron[dot]zhanglei[at]gmail[dot]com)
 */
@Route(path = TransportationRouterTable.PATH_SERVICE_TRANSPORTATION)
public class TransportationProviderImpl implements TransportationProvider {

    private Context context;

    @Override
    public void init(Context context) {
        this.context = context;
    }

    @Override
    public TransportationData fetchTransportationData() {
        TransportationData data = new TransportationData();
        data.setId("123123123123342");
        data.setTitle("G7356 高铁");
        data.setAddress("上海虹桥 -> 杭州东");
        data.setPrice("73元起");
        return data;
    }

    @Override
    public Fragment getFragment(String routePath) {
        return (Fragment) ARouter.getInstance().build(TransportationRouterTable.PATH_FRAGMENT_BLANK)
                .withString("param1", "这是一个来自出行模块的Fragment")
                .navigation(context);
    }
}
