package gudwns999.com.dialorcallapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by user on 2016-06-06.
 */
public class More extends Activity {
    private WebView webView;
    @Override
    public void onCreate(Bundle savedInstancesState){
        super.onCreate(savedInstancesState);
        setContentView(R.layout.webview);

        webView = (WebView)findViewById(R.id.webView_view);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("http://gudwns999.com");
        webView.setWebViewClient(new WishWebViewClient());
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()){
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    private  class WishWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url){
            view.loadUrl(url);
            return true;
        }
    }
}
