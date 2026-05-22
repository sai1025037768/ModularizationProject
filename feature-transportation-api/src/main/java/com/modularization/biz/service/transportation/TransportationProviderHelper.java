package com.modularization.biz.service.transportation;

import com.alibaba.android.arouter.launcher.ARouter;

/**
 * @author baronzhang (baron[dot]zhanglei[at]gmail[dot]com)
 */
public class TransportationProviderHelper {

    public static TransportationProvider getTransportationProvider(){
        return (TransportationProvider) ARouter.getInstance().build(TransportationRouterTable.PATH_SERVICE_TRANSPORTATION).navigation();
    }
}
