package net.suntrans.dachu.rx;

import net.suntrans.dachu.api.ApiErrorCode;
import net.suntrans.dachu.api.ApiErrorHelper;
import net.suntrans.dachu.api.ApiException;
import net.suntrans.dachu.utils.UiUtils;

import android.content.Context;

import rx.Subscriber;

/**
 * Created by Looney on 2017/9/6.
 */

public class BaseSubscriber<T> extends Subscriber<T> {

    private Context context;

    public BaseSubscriber(Context context) {
        this.context = context;
    }

    @Override
    public void onStart() {
        if (!UiUtils.isNetworkAvailable()) {
            this.onError(new ApiException(ApiErrorCode.ERROR_NO_INTERNET, "network interrupt"));
            return;
        }
    }

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        ApiErrorHelper.handleCommonError(context,e);
    }

    @Override
    public void onNext(T t) {

    }
}
