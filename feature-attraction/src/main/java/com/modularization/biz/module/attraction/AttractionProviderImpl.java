package com.modularization.biz.module.attraction;

import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.modularization.biz.service.attraction.AttractionProvider;
import com.modularization.biz.service.attraction.AttractionData;
import com.modularization.biz.service.attraction.AttractionRouterTable;

/**
 * @author baronzhang (baron[dot]zhanglei[at]gmail[dot]com)
 */
@Route(path = AttractionRouterTable.PATH_SERVICE_ATTRACTION)
public class AttractionProviderImpl implements AttractionProvider {

    @Override
    public void init(Context context) {

    }

    @Override
    public AttractionData fetchAttractionData() {
        AttractionData data = new AttractionData();
        data.setId("123123123");
        data.setName("西湖风景名胜区");
        data.setTicketPrice("免费");
        data.setAddress("杭州市西湖区龙井路");
        return data;
    }
}
