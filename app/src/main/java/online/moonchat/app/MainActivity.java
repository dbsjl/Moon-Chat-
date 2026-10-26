package online.moonchat.app;
import android.app.Activity; import android.os.Bundle; import android.webkit.*; import android.content.Intent; import android.net.Uri;
public class MainActivity extends Activity {
 private WebView webView; private ValueCallback<Uri[]> fileCallback; private static final int FILE_PICKER=101;
 @Override public void onCreate(Bundle savedInstanceState){ super.onCreate(savedInstanceState); webView=new WebView(this); setContentView(webView); WebSettings s=webView.getSettings(); s.setJavaScriptEnabled(true); s.setDomStorageEnabled(true); s.setAllowFileAccess(true); s.setAllowContentAccess(true); webView.setWebViewClient(new WebViewClient()); webView.setWebChromeClient(new WebChromeClient(){ @Override public boolean onShowFileChooser(WebView v,ValueCallback<Uri[]> cb,FileChooserParams p){ if(fileCallback!=null)fileCallback.onReceiveValue(null); fileCallback=cb; try{startActivityForResult(p.createIntent(),FILE_PICKER);}catch(Exception e){fileCallback=null;return false;}return true; }}); webView.loadUrl("file:///android_asset/index.html"); }
 @Override protected void onActivityResult(int r,int c,Intent d){super.onActivityResult(r,c,d);if(r==FILE_PICKER&&fileCallback!=null){fileCallback.onReceiveValue(WebChromeClient.FileChooserParams.parseResult(c,d));fileCallback=null;}}
 @Override public void onBackPressed(){if(webView!=null&&webView.canGoBack())webView.goBack();else super.onBackPressed();}
}
