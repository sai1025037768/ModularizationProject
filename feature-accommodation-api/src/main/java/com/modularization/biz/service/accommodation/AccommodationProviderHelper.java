package com.modularization.biz.service.accommodation;

import com.alibaba.android.arouter.launcher.ARouter;

/**
 * @author baronzhang (baron[dot]zhanglei[at]gmail[dot]com)
 */
public class AccommodationProviderHelper {

    public static AccommodationProvider getAccommodationProvider(){
        return (AccommodationProvider) ARouter.getInstance().build(AccommodationRouterTable.PATH_SERVICE_ACCOMMODATION).navigation();
    }
}
