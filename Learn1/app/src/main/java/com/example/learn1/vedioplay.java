package com.example.learn1;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class vedioplay extends AppCompatActivity {
    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vedioplayer);

        mWebView = findViewById(R.id.web_view);

        // 配置WebView
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setMediaPlaybackRequiresUserGesture(false);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        //webSettings.setUserAgentString("Mozilla/5.0 (Linux; Android 10; Mobile) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.120 Mobile Safari/537.36");

//        mWebView.setWebViewClient(new WebViewClient(){
//        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
//            super.onReceivedError(view, request, error);
//            Log.e("WebViewError", "Error: " + error.getDescription());}
//        });

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                String url = request.getUrl().toString();
                return handleUrlLoading(url);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return handleUrlLoading(url);
            }

            private boolean handleUrlLoading(String url) {
                if (url.startsWith("http://") || url.startsWith("https://")) {
                    // 允许 WebView 加载 HTTP/HTTPS 链接
                    return false;
                } else if (url.startsWith("intent://")) {
                    // 处理 Intent 跳转（如 B 站 App 跳转）
                    try {
                        Intent intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
                        if (intent.resolveActivity(getPackageManager()) != null) {
                            startActivity(intent);
                        } else {
                            // 如果目标 App 未安装，跳转到应用市场
                            String marketUrl = "market://details?id=" + intent.getPackage();
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(marketUrl)));
                        }
                        return true;
                    } catch (Exception e) {
                        e.printStackTrace();
                        return true;
                    }
                } else if (url.startsWith("tel:") || url.startsWith("mailto:") || url.startsWith("sms:")) {
                    // 处理电话、邮件、短信等
                    try {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        startActivity(intent);
                        return true;
                    } catch (Exception e) {
                        e.printStackTrace();
                        return true;
                    }
                } else {
                    // 其他未知 Scheme，可以尝试用浏览器打开
                    try {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        startActivity(intent);
                        return true;
                    } catch (Exception e) {
                        e.printStackTrace();
                        return true;
                    }
                }
            }
        });

        Intent v=getIntent();
        String s=v.getStringExtra("网页URI");
        // 加载B站视频页面，替换为你要播放的视频BV号
        //String bvid = "BV1GJ411x7h7";
        //if(s.length()!=0)
        assert s != null;
        mWebView.loadUrl(s);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mWebView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mWebView.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mWebView.destroy();
    }
}
