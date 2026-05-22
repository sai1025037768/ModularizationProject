package com.modularization.biz.service.attraction;

import com.alibaba.android.arouter.launcher.ARouter;

/**
 * @author baronzhang (baron[dot]zhanglei[at]gmail[dot]com)
 */
public class AttractionProviderHelper {

    public static AttractionProvider getAttractionProvider() {
        return (AttractionProvider) ARouter.getInstance().build(AttractionRouterTable.PATH_SERVICE_ATTRACTION).navigation();
    }
}
