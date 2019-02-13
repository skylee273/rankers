package com.project.rankers.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import androidx.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Window;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;

import com.project.rankers.R;
import com.project.rankers.databinding.DialogAddressBinding;

import java.util.Objects;

public class LocationDialog extends Dialog {
    private DialogAddressBinding mBinding;
    private Handler handler;
    private String address;
    public LocationDialog(Context context) { super(context); }


    @Override
    protected  void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Objects.requireNonNull(getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        mBinding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.dialog_address, null, false);
        setContentView(mBinding.getRoot());

        handler = new Handler();
        initView();

    }



    @SuppressLint({"SetJavaScriptEnabled", "AddJavascriptInterface"})
    private void initView(){
        mBinding.webView.getSettings().setJavaScriptEnabled(true);
        mBinding.webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        mBinding.webView.addJavascriptInterface(new AndroidBridge(), "LocationDialog");
        mBinding.webView.setWebChromeClient(new WebChromeClient());
        mBinding.webView.loadUrl("http://58.229.208.198/rankers/getAddress.php");

    }

    private class AndroidBridge{
        @JavascriptInterface
        public void setAddress(final String arg1, final String arg2, final String arg3){
            handler.post(new Runnable() {
                @Override
                public void run() {
                    address = String.format("(%s) %s %s",arg1,arg2,arg3);
                    initView();
                    dismiss();
                }
            });
        }
    }

    public String getAddressStr() {
        return address;
    }
}
