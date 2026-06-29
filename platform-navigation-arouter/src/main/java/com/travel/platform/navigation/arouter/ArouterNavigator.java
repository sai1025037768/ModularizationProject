package com.travel.platform.navigation.arouter;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.os.Parcelable;

import com.alibaba.android.arouter.core.LogisticsCenter;
import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.enums.RouteType;
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

    private final Context applicationContext;

    private ArouterNavigator(Context applicationContext) {
        this.applicationContext = applicationContext.getApplicationContext();
    }

    public static void install(Application application) {
        if ((application.getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0) {
            ARouter.openLog();
            ARouter.openDebug();
        }
        ARouter.init(application);
        Navigation.setNavigator(new ArouterNavigator(application));
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T navigate(RouteRequest request) {
        Postcard postcard = ARouter.getInstance().build(request.path());
        for (Map.Entry<String, RouteRequest.Parameter> entry : request.parameters().entrySet()) {
            applyParameter(postcard, entry.getKey(), entry.getValue());
        }

        LogisticsCenter.completion(postcard);

        if (postcard.getType() == RouteType.PROVIDER) {
            return (T) postcard.getProvider();
        }

        if (postcard.getType() == RouteType.ACTIVITY) {
            Context context = request.navigationContext() instanceof Context
                    ? (Context) request.navigationContext()
                    : applicationContext;
            startActivity(context, postcard);
            return null;
        }

        throw new IllegalArgumentException("Unsupported ARouter route type: " + postcard.getType());
    }

    private void startActivity(Context context, Postcard postcard) {
        Intent intent = new Intent(context, postcard.getDestination());
        intent.putExtras(postcard.getExtras());
        if (postcard.getFlags() != 0) {
            intent.setFlags(postcard.getFlags());
        }
        if (!(context instanceof Activity)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        if (postcard.getAction() != null && !postcard.getAction().isEmpty()) {
            intent.setAction(postcard.getAction());
        }
        context.startActivity(intent, postcard.getOptionsBundle());
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
