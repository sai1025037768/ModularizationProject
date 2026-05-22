package com.modularization.biz.module.main;

import android.os.Bundle;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.launcher.ARouter;
import com.modularization.biz.service.base.ResponseCallback;
import com.modularization.biz.service.accommodation.AccommodationApiData;
import com.modularization.biz.service.accommodation.AccommodationProviderHelper;
import com.modularization.biz.service.accommodation.AccommodationRouterTable;
import com.modularization.biz.service.attraction.AttractionData;
import com.modularization.biz.service.attraction.AttractionProviderHelper;
import com.modularization.biz.service.attraction.AttractionRouterTable;
import com.modularization.common.base.activity.BaseActivity;
import com.modularization.common.base.util.ActivityUtils;
import com.modularization.common.model.TravelDetail;
import com.modularization.biz.service.accommodation.AccommodationData;
import com.modularization.biz.service.transportation.TransportationData;
import com.modularization.biz.service.transportation.TransportationRouterTable;
import com.modularization.biz.service.transportation.TransportationProviderHelper;
import com.modularization.biz.service.base.ErrorMessage;

import java.util.ArrayList;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * App首页
 *
 * @author baronzhang (baron[dot]zhanglei[at]gmail[dot]com)
 */
public class MainActivity extends BaseActivity {

    private CompositeSubscription subscriptions;
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

        subscriptions = new CompositeSubscription();

        TextView accommodationTextView = findViewById(R.id.accommodation_text_view);
        AccommodationData accommodationData = AccommodationProviderHelper.getAccommodationProvider().fetchAccommodationData();
        accommodationTextView.setText(accommodationData.toString());
        Subscription subscription = AccommodationProviderHelper.getAccommodationProvider().callAccommodationApi(new ResponseCallback<AccommodationApiData>() {
            @Override
            public void onSuccess(AccommodationApiData data) {
                accommodationTextView.setText(String.format("%s\n\n%s", accommodationTextView.getText(), data.toString()));
            }

            @Override
            public void onFailed(ErrorMessage errorMsg) {
                accommodationTextView.setText(String.format("%s\n\n%s", accommodationTextView.getText(), errorMsg));
            }
        });
        subscriptions.add(subscription);

        TransportationData transportationData = TransportationProviderHelper.getTransportationProvider().fetchTransportationData();
        ((TextView) findViewById(R.id.transportation_text_view)).setText(transportationData.toString());

        AttractionData attractionData = AttractionProviderHelper.getAttractionProvider().fetchAttractionData();
        ((TextView) findViewById(R.id.attraction_text_view)).setText(attractionData.toString());

        Fragment fragment = TransportationProviderHelper.getTransportationProvider().getFragment(TransportationRouterTable.PATH_FRAGMENT_BLANK);
        ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), fragment, R.id.fragment_container);
    }

    void startAccommodationActivity() {
        ARouter.getInstance().build(AccommodationRouterTable.PATH_ACTIVITY_MAIN)
                .withString("cityId", "110")
                .withParcelable("travelDetail", new TravelDetail("trip-10000", "杭州西湖", 2))
                .navigation();
    }

    void startTransportationActivity() {

        ArrayList<TravelDetail> travelDetailList = new ArrayList<>();
        travelDetailList.add(new TravelDetail("trip-10001", "上海虹桥", 1));
        travelDetailList.add(new TravelDetail("trip-10002", "苏州平江路", 2));
        travelDetailList.add(new TravelDetail("trip-10004", "南京夫子庙", 3));

        ARouter.getInstance().build(TransportationRouterTable.PATH_ACTIVITY_MAIN)
                .withString("cityId", "111")
                .withParcelableArrayList("travelPlanList", travelDetailList)
                .navigation();
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (subscriptions != null)
            subscriptions.unsubscribe();
    }
}
