package com.travel.feature.transportation.presentation;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import com.travel.core.model.TravelDetail;
import com.travel.core.ui.base.activity.BaseActivity;
import com.travel.feature.transportation.R;
import com.travel.feature.transportation.api.TransportationRoutes;

import java.util.ArrayList;

public class TransportationActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transportation_activity_main);

        setSupportActionBar(findViewById(R.id.toolbar));
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        TextView textView1 = findViewById(R.id.textView1);
        TextView textView2 = findViewById(R.id.textView2);

        String cityId = getIntent().getStringExtra(TransportationRoutes.MainActivityParams.CITY_ID);
        ArrayList<TravelDetail> travelPlanList = getTravelPlanList(getIntent());

        textView1.setText("城市ID: " + cityId);
        textView2.setText("出行计划列表: \n" + formatTravelPlans(travelPlanList));
    }

    private String formatTravelPlans(ArrayList<TravelDetail> travelPlanList) {
        if (travelPlanList == null || travelPlanList.isEmpty()) {
            return "";
        }

        StringBuilder builder = new StringBuilder();
        for (TravelDetail travelDetail : travelPlanList) {
            builder.append(travelDetail.toString()).append("\n");
        }
        return builder.substring(0, builder.length() - 1);
    }

    @SuppressWarnings("deprecation")
    private ArrayList<TravelDetail> getTravelPlanList(Intent intent) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            return intent.getParcelableArrayListExtra(TransportationRoutes.MainActivityParams.TRAVEL_PLAN_LIST, TravelDetail.class);
        }
        return intent.getParcelableArrayListExtra(TransportationRoutes.MainActivityParams.TRAVEL_PLAN_LIST);
    }
}
