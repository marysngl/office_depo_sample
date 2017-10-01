package officedepo.mediapark.com.officedepo.Model;

import android.os.Handler;
import android.os.Looper;

import com.squareup.otto.Bus;

/**
 * Created by Mary Songal on 03.11.2016.
 */

public class MainThreadBus extends Bus {

    private final Handler mHandler = new Handler(Looper.getMainLooper());

    @Override
    public void post(final Object event) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            super.post(event);
        } else {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    if(event != null) {
                        MainThreadBus.super.post(event);
                    }
                }
            });
        }
    }
}
