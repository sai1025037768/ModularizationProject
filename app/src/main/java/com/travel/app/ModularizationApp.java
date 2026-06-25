package com.travel.app;

import android.app.Application;

import com.travel.core.storage.preferences.Preferences;
import com.travel.core.network.ApiClient;
import com.travel.platform.navigation.arouter.ArouterNavigator;

/**
 * @author byron (byron[dot]zhanglei[at]gmail[dot]com)
 */
public class ModularizationApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ArouterNavigator.install(this);
        ApiClient.getInstance().init(null);

        //初始化SP配置
        Preferences.getInstance(this).loadDefaults();
    }
}
