package com.modularization.biz.module.transportation;

import android.os.Bundle;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.modularization.biz.service.transportation.TransportationRouterTable;
import com.modularization.common.base.activity.BaseActivity;
import com.modularization.common.model.TravelDetail;

import java.util.ArrayList;

@Route(path = TransportationRouterTable.PATH_ACTIVITY_MAIN)
public class MainActivity extends BaseActivity {

    @Autowired
    String cityId;
    @Autowired
    ArrayList<TravelDetail> travelPlanList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transportation_activity_main);

        setSupportActionBar(findViewById(R.id.toolbar));
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        ARouter.getInstance().inject(this);

        TextView textView1 = findViewById(R.id.textView1);
        TextView textView2 = findViewById(R.id.textView2);

        textView1.setText("城市ID: " + cityId);
        StringBuilder travelPlanListStr = new StringBuilder();
        for(TravelDetail travelDetail : travelPlanList){
            travelPlanListStr.append(travelDetail.toString()).append("\n");
        }
        textView2.setText("出行计划列表: \n" + travelPlanListStr.substring(0, travelPlanListStr.length()-1));
    }
}
