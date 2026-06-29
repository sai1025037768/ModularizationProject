package com.travel.app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.travel.core.navigation.Navigation;
import com.travel.core.navigation.RouteRequest;
import com.travel.feature.home.api.HomeRoutes;


/**
 * App闪屏页
 *
 * @author byron (byron[dot]zhanglei[at]gmail[dot]com)
 */
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Navigation.navigate(RouteRequest.of(HomeRoutes.ACTIVITY_MAIN)
                .withNavigationContext(this));
        finish();
    }
}
