package com.project.rankers.widget;

public class WidgetItem {
    String title;
    String address;
    String period;
    String flag;

    public WidgetItem(String title, String address, String period, String flag) {
        this.title = title;
        this.address = address;
        this.period = period;
        this.flag = flag;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }
}
