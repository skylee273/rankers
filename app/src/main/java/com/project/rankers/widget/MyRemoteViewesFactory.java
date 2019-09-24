package com.project.rankers.widget;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.project.rankers.R;

import java.util.ArrayList;

public class MyRemoteViewesFactory implements RemoteViewsService.RemoteViewsFactory {


    //context 설정하기
    public Context context = null;
    public ArrayList<WidgetItem> arrayList;

    public MyRemoteViewesFactory(Context context) {
        this.context = context;
    }

    public void setData() {
        arrayList = new ArrayList<>();
        arrayList.add(new WidgetItem("한기대 대회", "한기대","2019.09.12 ~ 2019.09.18","ETC"));
        arrayList.add(new WidgetItem("아우내 테니스 대회", "아우니 체육관 옆 테니스코트","2019.09.25 ~ 2019.09.26","LOCAL"));
        arrayList.add(new WidgetItem("2020 윔블던 선수권 대회", "머튼 구 윔블던 테니스장","2020.06.29 ~ 2020.07.12","KTA"));
        arrayList.add(new WidgetItem("NAA 테니스 대회", "서울 신도림역 옆 NAA테니스장","2021.01.01 ~ 2021.02.11","LOCAL"));
    }
    @Override
    public void onCreate() {
        setData();
    }

    @Override
    public void onDataSetChanged() {
        setData();
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {

        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.item_widget_listview);
        remoteViews.setTextViewText(R.id.item_widget_listview_title_textview, arrayList.get(position).title);
        remoteViews.setTextViewText(R.id.item_widget_listview_address_textview, arrayList.get(position).address);
        remoteViews.setTextViewText(R.id.item_widget_listview_period_textview, arrayList.get(position).period);
        remoteViews.setTextViewText(R.id.item_widget_listview_type_textview, arrayList.get(position).flag);

        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
