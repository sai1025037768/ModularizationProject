package com.travel.feature.accommodation.presentation;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import com.travel.core.model.TravelDetail;
import com.travel.core.ui.base.activity.BaseActivity;
import com.travel.feature.accommodation.R;
import com.travel.feature.accommodation.api.AccommodationRoutes;

import java.util.ArrayList;

public class AccommodationActivity extends BaseActivity {

    private final AccommodationNavigator navigator = new AccommodationNavigator();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accommodation_activity_main);
        findViewById(R.id.button).setOnClickListener(view -> navigator.openAttractions("112", createAttractionIds()));

        setSupportActionBar(findViewById(R.id.toolbar));
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        TextView textView1 = findViewById(R.id.textView1);
        TextView textView2 = findViewById(R.id.textView2);

        String cityId = getIntent().getStringExtra(AccommodationRoutes.MainActivityParams.CITY_ID);
        TravelDetail travelDetail = getTravelDetail(getIntent());

        textView1.setText("城市ID: " + cityId);
        textView2.setText("住宿行程: \n" + travelDetail);
    }

    private ArrayList<Integer> createAttractionIds() {
        ArrayList<Integer> attractionIdList = new ArrayList<>();
        attractionIdList.add(20000);
        attractionIdList.add(20001);
        attractionIdList.add(20002);
        return attractionIdList;
    }

    @SuppressWarnings("deprecation")
    private TravelDetail getTravelDetail(Intent intent) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            return intent.getParcelableExtra(AccommodationRoutes.MainActivityParams.TRAVEL_DETAIL, TravelDetail.class);
        }
        return intent.getParcelableExtra(AccommodationRoutes.MainActivityParams.TRAVEL_DETAIL);
    }
}
