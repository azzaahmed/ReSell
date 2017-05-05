package com.app.resell.Widget;

import android.app.IntentService;
import android.content.Intent;

/**
 * Created by azza ahmed on 5/5/2017.
 */
public class WidgetRemoteViewsService extends IntentService {

    public static final String ACTION_Data_Update =
            " com.app.resell.ACTION_DATA_UPDATED";
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public WidgetRemoteViewsService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

    }
}
