package com.travel.feature.home;

import android.os.Bundle;
import android.widget.TextView;

import com.travel.biz.service.base.Cancelable;
import com.travel.biz.service.base.ResponseCallback;
import com.travel.core.navigation.Navigation;
import com.travel.core.navigation.RouteRequest;
import com.travel.feature.accommodation.api.AccommodationApiData;
import com.travel.feature.accommodation.api.AccommodationRoutes;
import com.travel.feature.accommodation.api.AccommodationProvider;
import com.travel.feature.attraction.api.AttractionData;
import com.travel.feature.attraction.api.AttractionProvider;
import com.travel.feature.attraction.api.AttractionRoutes;
import com.travel.core.ui.base.activity.BaseActivity;
import com.travel.core.model.TravelDetail;
import com.travel.feature.accommodation.api.AccommodationData;
import com.travel.feature.transportation.api.TransportationData;
import com.travel.feature.transportation.api.TransportationProvider;
import com.travel.feature.transportation.api.TransportationRoutes;
import com.travel.biz.service.base.ErrorMessage;

import java.util.ArrayList;

/**
 * App首页
 *
 * @author byron (byron[dot]zhanglei[at]gmail[dot]com)
 */
public class MainActivity extends BaseActivity {

    private final ArrayList<Cancelable> cancelables = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_goto_accommodation).setOnClickListener(view -> startAccommodationActivity());
        findViewById(R.id.btn_goto_transportation).setOnClickListener(view -> startTransportationActivity());
        findViewById(R.id.btn_goto_attraction).setOnClickListener(view -> startAttractionActivity());

        setSupportActionBar(findViewById(R.id.toolbar));
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }

        TextView accommodationTextView = findViewById(R.id.accommodation_text_view);
        AccommodationProvider accommodationProvider = getAccommodationProvider();
        AccommodationData accommodationData = accommodationProvider.fetchAccommodationData();
        accommodationTextView.setText(accommodationData.toString());
        Cancelable cancelable = accommodationProvider.callAccommodationApi(new ResponseCallback<AccommodationApiData>() {
            @Override
            public void onSuccess(AccommodationApiData data) {
                accommodationTextView.setText(String.format("%s\n\n%s", accommodationTextView.getText(), data.toString()));
            }

            @Override
            public void onFailed(ErrorMessage errorMsg) {
                accommodationTextView.setText(String.format("%s\n\n%s", accommodationTextView.getText(), errorMsg));
            }
        });
        cancelables.add(cancelable);

        TransportationProvider transportationProvider = getTransportationProvider();
        TransportationData transportationData = transportationProvider.fetchTransportationData();
        ((TextView) findViewById(R.id.transportation_text_view)).setText(transportationData.toString());

        AttractionProvider attractionProvider = getAttractionProvider();
        AttractionData attractionData = attractionProvider.fetchAttractionData();
        ((TextView) findViewById(R.id.attraction_text_view)).setText(attractionData.toString());
    }

    private AccommodationProvider getAccommodationProvider() {
        return Navigation.navigate(RouteRequest.of(AccommodationRoutes.SERVICE));
    }

    private TransportationProvider getTransportationProvider() {
        return Navigation.navigate(RouteRequest.of(TransportationRoutes.SERVICE));
    }

    private AttractionProvider getAttractionProvider() {
        return Navigation.navigate(RouteRequest.of(AttractionRoutes.SERVICE));
    }

    void startAccommodationActivity() {
        Navigation.navigate(RouteRequest.of(AccommodationRoutes.ACTIVITY_MAIN)
                .withNavigationContext(this)
                .withString(AccommodationRoutes.MainActivityParams.CITY_ID, "110")
                .withParcelable(AccommodationRoutes.MainActivityParams.TRAVEL_DETAIL, new TravelDetail("trip-10000", "杭州西湖", 2)));
    }

    void startTransportationActivity() {

        ArrayList<TravelDetail> travelDetailList = new ArrayList<>();
        travelDetailList.add(new TravelDetail("trip-10001", "上海虹桥", 1));
        travelDetailList.add(new TravelDetail("trip-10002", "苏州平江路", 2));
        travelDetailList.add(new TravelDetail("trip-10004", "南京夫子庙", 3));

        Navigation.navigate(RouteRequest.of(TransportationRoutes.ACTIVITY_MAIN)
                .withNavigationContext(this)
                .withString(TransportationRoutes.MainActivityParams.CITY_ID, "111")
                .withParcelableArrayList(TransportationRoutes.MainActivityParams.TRAVEL_PLAN_LIST, travelDetailList));
    }

    void startAttractionActivity() {
        ArrayList<Integer> attractionIdList = new ArrayList<>();
        attractionIdList.add(20000);
        attractionIdList.add(20001);
        attractionIdList.add(20002);
        Navigation.navigate(RouteRequest.of(AttractionRoutes.ACTIVITY_MAIN)
                .withNavigationContext(this)
                .withString(AttractionRoutes.MainActivityParams.CITY_ID, "112")
                .withIntegerArrayList(AttractionRoutes.MainActivityParams.ATTRACTION_ID_LIST, attractionIdList));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        for (Cancelable cancelable : cancelables) {
            cancelable.cancel();
        }
        cancelables.clear();
    }
}
