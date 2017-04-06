package com.happysong.app.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.happysong.app.utils.SToast;
import com.happysong.app.utils.Utils;

import java.util.regex.Pattern;

/**
 * Html5展示页
 */
@SuppressLint("SetJavaScriptEnabled")
public class Html5Activity extends Activity implements OnClickListener {

    private WebView webView;
    /* 标题 */
    private TextView tvView;
    /* 关闭按钮 */
    private ImageView ivClose, ivBack;
    /* Html数据 */
    private String url;
    private final String errorHead = "^(http|https|ftp|www).*";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.happysong.app.R.layout.html_layout);
        initView();
        getDataByBundle();
        addListener();
        showWebView();
    }

    /**
     * 显示Html内容
     */
    private void showWebView() {
        if (url == null || TextUtils.isEmpty(url)) {
            Utils.toast(this, "参数有误");
            return;
        }
        webView.loadUrl(url);
    }


    /**
     * 获取getIntent附带数据
     */
    private void getDataByBundle() {
        Intent intent = getIntent();
        if (intent.hasExtra("title"))
            tvView.setText(intent.getStringExtra("title"));
        if (intent.hasExtra("url")) {
            url = intent.getStringExtra("url");
            Log.e("html5:", url);
        }
    }

    /**
     * 初始化界面元素
     */
    private void initView() {
        // dialog = new ProgressDialog(this);
        ivBack = (ImageView) findViewById(com.happysong.app.R.id.page_title_back_img);
        ivClose = (ImageView) findViewById(com.happysong.app.R.id.page_title_close_img);
        webView = (WebView) findViewById(com.happysong.app.R.id.webview_layout_html);
        tvView = (TextView) findViewById(com.happysong.app.R.id.page_title_tv);
        webView.setSaveEnabled(true);
//        webView.addJavascriptInterface(new JSActionInterface(this, webView), "jsAction");
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setDomStorageEnabled(true);
//        CookieUtil.getInstance().synCookies(this, url, webView);
//        webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ONLY);
    }

    /**
     * 增加监听事件
     */
    private void addListener() {
        ivClose.setOnClickListener(this);
        ivBack.setOnClickListener(this);
//        webView.addJavascriptInterface(new JSActionInterface(this, webView), JSActionInterface.JSACTION_PRFIX);
        webView.setWebViewClient(new HtmlWebViewClient());
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onCloseWindow(WebView window) {
                super.onCloseWindow(window);
            }

            @Override
            public boolean onCreateWindow(WebView view, boolean dialog,
                                          boolean userGesture, Message resultMsg) {
                return super.onCreateWindow(view, dialog, userGesture,
                        resultMsg);
            }

            /**
             * 覆盖默认的window.alert展示界面，避免title里显示为“：来自file:////”
             */
            public boolean onJsAlert(WebView view, String url, String message,
                                     JsResult result) {
                return super.onJsAlert(view, url, message, result);
            }

            public boolean onJsBeforeUnload(WebView view, String url,
                                            String message, JsResult result) {
                return super.onJsBeforeUnload(view, url, message, result);
            }

            /**
             * 覆盖默认的window.confirm展示界面，避免title里显示为“：来自file:////”
             */
            public boolean onJsConfirm(WebView view, String url,
                                       String message, final JsResult result) {
                return super.onJsConfirm(view, url, message, result);
            }

            /**
             * 覆盖默认的window.prompt展示界面，避免title里显示为“：来自file:////”
             * window.prompt('请输入您的域名地址', '618119.com');
             */
            public boolean onJsPrompt(WebView view, String url, String message,
                                      String defaultValue, final JsPromptResult result) {
                return super.onJsPrompt(view, url, message, defaultValue,
                        result);
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress >= 70)
//                    if (dialog != null && dialog.isShowing())
//                        dialog.dismiss();
                    super.onProgressChanged(view, newProgress);
            }

            @Override
            public void onReceivedIcon(WebView view, Bitmap icon) {
                super.onReceivedIcon(view, icon);
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
            }

            @Override
            public void onRequestFocus(WebView view) {
                super.onRequestFocus(view);
            }
        });
    }

    @Override
    protected void onDestroy() {
        if (webView != null) {
            ViewGroup parent = (ViewGroup) webView.getParent();
            if (parent != null) {
                parent.removeView(webView);
            }
            webView.clearCache(true);
            webView.removeAllViews();
            webView.destroy();
        }
        webView = null;
        tvView = null;
        ivClose = null;
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case com.happysong.app.R.id.page_title_back_img:
                if (webView != null && webView.canGoBack()) {
                    webView.goBack();
                    checkShowBackImg();
                }
                break;
            case com.happysong.app.R.id.page_title_close_img:
                finish();
                break;
        }
    }

    /*
     *检测是否需要显示返回按键
     */
    private void checkShowBackImg() {
        if (webView != null && webView.canGoBack()) {
            ivBack.setVisibility(View.VISIBLE);
        } else {
            ivBack.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (webView != null && webView.canGoBack()) {
            webView.goBack();
            checkShowBackImg();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 自定义WebViewClient 防跳转至手机浏览器打开
     */
    class HtmlWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (TextUtils.isEmpty(url)) {
                return true;
            }
            if (url.endsWith(".apk")) {
                startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri
                        .parse(url)));
            } else {
                //已这些 开头的就认为是合法的网页
                if (!Pattern.compile(errorHead).matcher(url).matches()) {
                    //url地址不符合要求
                    SToast.s(Html5Activity.this, "网页地址不符合规则!");
                    return true;
                }
                view.loadUrl(url);
            }
            return false;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            if (!webView.getSettings().getLoadsImagesAutomatically()) {
                webView.getSettings().setLoadsImagesAutomatically(true);
            }
            checkShowBackImg();
        }

        @Override
        public void onReceivedError(WebView view, int errorCode,
                                    String description, String failingUrl) {
            checkShowBackImg();
            super.onReceivedError(view, errorCode, description, failingUrl);
        }
    }
}
