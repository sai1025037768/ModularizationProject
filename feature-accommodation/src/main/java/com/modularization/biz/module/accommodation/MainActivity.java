package com.modularization.biz.module.accommodation;

import android.os.Bundle;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.modularization.biz.service.attraction.AttractionRouterTable;
import com.modularization.biz.service.accommodation.AccommodationRouterTable;
import com.modularization.common.base.activity.BaseActivity;
import com.modularization.common.model.TravelDetail;

import java.util.ArrayList;

@Route(path = AccommodationRouterTable.PATH_ACTIVITY_MAIN)
public class MainActivity extends BaseActivity {

    @Autowired
    String cityId;

    @Autowired
    TravelDetail travelDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accommodation_activity_main);
        findViewById(R.id.button).setOnClickListener(view -> startAttractionActivity());

        setSupportActionBar(findViewById(R.id.toolbar));
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        ARouter.getInstance().inject(this);

        TextView textView1 = findViewById(R.id.textView1);
        TextView textView2 = findViewById(R.id.textView2);

        textView1.setText("城市ID: " + cityId);
        textView2.setText("住宿行程: \n" + travelDetail);
    }

    void startAttractionActivity() {
        ArrayList<Integer> attractionIdList = new ArrayList<>();
        attractionIdList.add(20000);
        attractionIdList.add(20001);
        attractionIdList.add(20002);
        ARouter.getInstance().build(AttractionRouterTable.PATH_ACTIVITY_MAIN)
                .withString("cityId", "112")
                .withIntegerArrayList("attractionIdList", attractionIdList)
                .navigation();
    }
}
