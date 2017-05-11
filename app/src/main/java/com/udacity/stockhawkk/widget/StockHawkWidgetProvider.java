package com.udacity.stockhawkk.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;
import android.support.v4.app.TaskStackBuilder;

import com.udacity.stockhawkk.R;
import com.udacity.stockhawkk.ui.MainActivity;
import com.udacity.stockhawkk.ui.StockDetailActivity;

/**
 * Created by shallak on 09/05/2017.
 */

public class StockHawkWidgetProvider extends AppWidgetProvider {

    public static final String EXTRA_POSITION = "com.udacity.stockhawk.widget.extra_position";
    public static final String EXTRA_SYMBOL = "com.udacity.stockhawk.widget.extra_symbol";
    public static final String ACTION_OPEN_STOCK_HAWK = "com.udacity.stockhawk.widget.open_stock_hawk";

    @Override
    public void onReceive(Context context, Intent intent) {

        String action = intent.getAction();
        if (action.equals(ACTION_OPEN_STOCK_HAWK)) {
            String stockSymbol = intent.getStringExtra(EXTRA_SYMBOL);
            Intent mainAppIntent = new Intent(context, StockDetailActivity.class);
            mainAppIntent.putExtra("symbol", stockSymbol);
            mainAppIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(context);
            taskStackBuilder.addParentStack(MainActivity.class);
            taskStackBuilder.addNextIntent(mainAppIntent);
            context.startActivity(mainAppIntent);
        }
        super.onReceive(context, intent);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        for (int i = 0; i < appWidgetIds.length; i++) {
            Intent intent = new Intent(context, StockHawkWidgetService.class);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds[i]);
            intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

            RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.widget_stock_hawk);

            rv.setRemoteAdapter(R.id.widget_list_view, intent);
            rv.setEmptyView(R.id.widget_list_view, R.id.widget_empty_view);

            Intent stockIntent = new Intent(context, StockHawkWidgetProvider.class);
            stockIntent.setAction(ACTION_OPEN_STOCK_HAWK);
            stockIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds[i]);
            stockIntent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

            PendingIntent stockPendingIntent = PendingIntent.getBroadcast(context, appWidgetIds[i], stockIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT);

            rv.setPendingIntentTemplate(R.id.widget_list_view, stockPendingIntent);

            appWidgetManager.updateAppWidget(appWidgetIds[i], rv);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }


}