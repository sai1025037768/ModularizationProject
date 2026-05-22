package com.modularization.biz.service.attraction;

import com.alibaba.android.arouter.facade.template.IProvider;

/**
 * @author baronzhang (baron[dot]zhanglei[at]gmail[dot]com)
 */
public interface AttractionProvider extends IProvider {

    AttractionData fetchAttractionData();
}
