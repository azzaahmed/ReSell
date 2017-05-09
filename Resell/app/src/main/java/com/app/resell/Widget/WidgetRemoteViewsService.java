package com.app.resell.Widget;

import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.app.resell.R;

/**
 * Created by azza ahmed on 5/5/2017.
 */
public class WidgetRemoteViewsService  extends RemoteViewsService {


    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new RemoteViewsFactory() {
            @Override
            public void onCreate() {

            }

            @Override
            public void onDataSetChanged() {

            }

            @Override
            public void onDestroy() {

            }

            @Override
            public int getCount() {
                return 0;
            }

            @Override
            public RemoteViews getViewAt(int position) {
                RemoteViews views = new RemoteViews(getPackageName(),
                        R.layout.widget_list_item);


                final Intent fillInIntent = new Intent();

                fillInIntent.putExtra("history",history);
                views.setOnClickFillInIntent(R.id.card_list_item, fillInIntent);

                return views;
            }

            @Override
            public RemoteViews getLoadingView() {
                   return new RemoteViews(getPackageName(), R.layout.widget_list_item);
            }

            @Override
            public int getViewTypeCount() {
                return 0;
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public boolean hasStableIds() {
                return false;
            }
        };
    }
}
