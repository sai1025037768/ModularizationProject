package com.travel.feature.attraction.presentation;

import android.os.Bundle;
import android.widget.TextView;

import com.travel.core.navigation.Navigation;
import com.travel.core.navigation.RouteRequest;
import com.travel.core.ui.base.activity.BaseActivity;
import com.travel.feature.attraction.R;
import com.travel.feature.attraction.api.AttractionProvider;
import com.travel.feature.attraction.api.AttractionRoutes;

import java.util.ArrayList;

public class AttractionActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.attraction_activity_main);

        setSupportActionBar(findViewById(R.id.toolbar));
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        TextView textView1 = findViewById(R.id.textView1);
        TextView textView2 = findViewById(R.id.textView2);
        TextView textView4 = findViewById(R.id.textView4);

        String cityId = getIntent().getStringExtra(AttractionRoutes.MainActivityParams.CITY_ID);
        ArrayList<Integer> attractionIdList = getIntent().getIntegerArrayListExtra(AttractionRoutes.MainActivityParams.ATTRACTION_ID_LIST);

        textView1.setText("城市ID: " + cityId);
        textView2.setText("景点ID列表: " + attractionIdList);

        AttractionProvider attractionProvider = Navigation.navigate(RouteRequest.of(AttractionRoutes.SERVICE));
        textView4.setText(attractionProvider.fetchAttractionData().toString());
    }
}
