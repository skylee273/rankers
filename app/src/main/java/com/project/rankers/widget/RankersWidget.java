package com.project.rankers.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.RemoteViews;

import androidx.recyclerview.widget.RecyclerView;

import com.project.rankers.R;
import com.project.rankers.ui.main.MainActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Calendar;

public class RankersWidget extends AppWidgetProvider {

    private static final String REFRESH = "REFRESH Clicked";

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        RemoteViews widget = new RemoteViews(context.getPackageName(), R.layout.rankers_widget);
        Intent serviceIntent = new Intent(context, MyRemoteViewsService.class);
        widget.setRemoteAdapter(R.id.rankers_widget_listview, serviceIntent);

        Intent intent = new Intent(context, RankersWidget.class).setAction(REFRESH);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,0, intent, 0);
        widget.setOnClickPendingIntent(R.id.dining_widget_date_textview, pendingIntent);

        setDate(widget);

        appWidgetManager.updateAppWidget(appWidgetId, widget);
    }
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        String action = intent.getAction();
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.rankers_widget);
        ComponentName componentName = new ComponentName(context, RankersWidget.class);

        if(action.equals(REFRESH)) {
            AppWidgetManager manager = AppWidgetManager.getInstance(context);
            manager.updateAppWidget(new ComponentName(context, RankersWidget.class), remoteViews);
            appWidgetManager.updateAppWidget(componentName, remoteViews);
        }
    }

    static void setDate(RemoteViews remoteViews){
        Calendar calendar = Calendar.getInstance();
        int dayWeek = calendar.get(Calendar.DAY_OF_WEEK);
        String dayWeekStr = null;
        switch (dayWeek){
            case 1:
                dayWeekStr = "일";
                break;
            case 2:
                dayWeekStr = "월";
                break;
            case 3:
                dayWeekStr = "화";
                break;
            case 4:
                dayWeekStr = "수";
                break;
            case 5:
                dayWeekStr = "목";
                break;
            case 6:
                dayWeekStr = "금";
                break;
            case 7:
                dayWeekStr = "토";
                break;
        }
        remoteViews.setTextViewText(R.id.dining_widget_date_textview, (calendar.get(Calendar.MONTH)+1) + "/"+ calendar.get(Calendar.DAY_OF_MONTH) +" ("+dayWeekStr+")");
    }

}

