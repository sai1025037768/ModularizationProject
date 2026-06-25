package com.travel.platform.navigation.arouter;

import android.app.Application;
import android.content.Context;
import android.os.Parcelable;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.launcher.ARouter;
import com.travel.core.navigation.Navigation;
import com.travel.core.navigation.Navigator;
import com.travel.core.navigation.RouteRequest;

import java.util.ArrayList;
import java.util.Map;

/**
 * ARouter-backed navigation adapter.
 */
public final class ArouterNavigator implements Navigator {

    public static void install(Application application) {
        ARouter.init(application);
        Navigation.setNavigator(new ArouterNavigator());
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T navigate(RouteRequest request) {
        Postcard postcard = ARouter.getInstance().build(request.path());
        for (Map.Entry<String, RouteRequest.Parameter> entry : request.parameters().entrySet()) {
            applyParameter(postcard, entry.getKey(), entry.getValue());
        }

        Object navigationContext = request.navigationContext();
        Object result = navigationContext instanceof Context
                ? postcard.navigation((Context) navigationContext)
                : postcard.navigation();
        return (T) result;
    }

    @SuppressWarnings("unchecked")
    private static void applyParameter(Postcard postcard, String key, RouteRequest.Parameter parameter) {
        Object value = parameter.value();
        switch (parameter.type()) {
            case STRING:
                postcard.withString(key, (String) value);
                break;
            case INT:
                postcard.withInt(key, (Integer) value);
                break;
            case INTEGER_ARRAY_LIST:
                postcard.withIntegerArrayList(key, (ArrayList<Integer>) value);
                break;
            case PARCELABLE:
                postcard.withParcelable(key, (Parcelable) value);
                break;
            case PARCELABLE_ARRAY_LIST:
                postcard.withParcelableArrayList(key, (ArrayList<? extends Parcelable>) value);
                break;
            default:
                throw new IllegalArgumentException("Unsupported route parameter type: " + parameter.type());
        }
    }
}
