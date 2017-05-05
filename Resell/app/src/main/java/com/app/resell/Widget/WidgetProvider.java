package com.app.resell.Widget;

/**
 * Created by azza ahmed on 5/5/2017.
 */
public class WidgetProvider{
//
//} extends AppWidgetProvider {
//    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
//        // Perform this loop procedure for each App Widget that belongs to this provider
//        for (int appWidgetId : appWidgetIds) {
//            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget);
//
//            // Create an Intent to launch MainActivity
//            Intent intent = new Intent(context, MainActivity.class);
//            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
//            views.setOnClickPendingIntent(R.id.widgetFrame, pendingIntent);
//
//            // Set up the collection
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
//                setRemoteAdapter(context, views);
//            } else {
//                setRemoteAdapterV11(context, views);
//            }
//            boolean useDetailActivity = context.getResources()
//                    .getBoolean(R.bool.use_activity);
//            Intent clickIntentTemplate = useDetailActivity
//                    ? new Intent(context, itemDetails.class)
//                    : new Intent(context, MainActivity.class);
//
//            PendingIntent clickPendingIntentTemplate = TaskStackBuilder.create(context)
//                    .addNextIntentWithParentStack(clickIntentTemplate)
//                    .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
//            views.setPendingIntentTemplate(R.id.widget_list, clickPendingIntentTemplate);
//            views.setEmptyView(R.id.widget_list, R.id.widget_empty);
//
//            // Tell the AppWidgetManager to perform an update on the current app widget
//            appWidgetManager.updateAppWidget(appWidgetId, views);
//        }
//    }
//
//    @Override
//    public void onReceive(@NonNull Context context, @NonNull Intent intent) {
//        super.onReceive(context, intent);
//        if (QuoteSyncJob.ACTION_DATA_UPDATED.equals(intent.getAction())) {
//            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
//            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(
//                    new ComponentName(context, getClass()));
//            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_list);
//        }
//    }
//
//    /**
//     * Sets the remote adapter used to fill in the list items
//     *
//     * @param views RemoteViews to set the RemoteAdapter
//     */
//    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
//    private void setRemoteAdapter(Context context, @NonNull final RemoteViews views) {
//        views.setRemoteAdapter(R.id.widget_list,
//                new Intent(context, WidgetRemoteViewsService.class));
//    }
//
//    /**
//     * Sets the remote adapter used to fill in the list items
//     *
//     * @param views RemoteViews to set the RemoteAdapter
//     */
//    @SuppressWarnings("deprecation")
//    private void setRemoteAdapterV11(Context context, @NonNull final RemoteViews views) {
//        views.setRemoteAdapter(0, R.id.widget_list,
//                new Intent(context, WidgetRemoteViewsService.class));
//    }
}