package com.modularization.biz.module.attraction;

import android.os.Bundle;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.modularization.biz.service.attraction.AttractionRouterTable;
import com.modularization.biz.service.accommodation.AccommodationProviderHelper;
import com.modularization.common.base.activity.BaseActivity;

import java.util.ArrayList;

@Route(path = AttractionRouterTable.PATH_ACTIVITY_MAIN)
public class MainActivity extends BaseActivity {

    @Autowired
    String cityId;
    @Autowired
    ArrayList<Integer> attractionIdList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.attraction_activity_main);

        setSupportActionBar(findViewById(R.id.toolbar));
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        ARouter.getInstance().inject(this);

        TextView textView1 = findViewById(R.id.textView1);
        TextView textView2 = findViewById(R.id.textView2);
        TextView textView4 = findViewById(R.id.textView4);

        textView1.setText("城市ID: " + cityId);
        textView2.setText("景点ID列表: " + attractionIdList);

        textView4.setText(AccommodationProviderHelper.getAccommodationProvider().fetchAccommodationData().toString());
    }
}
