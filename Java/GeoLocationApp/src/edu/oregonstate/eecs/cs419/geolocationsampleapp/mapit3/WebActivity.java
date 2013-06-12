package edu.oregonstate.eecs.cs419.geolocationsampleapp.mapit3;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

@SuppressLint("SetJavaScriptEnabled")
public class WebActivity extends Activity{

	public static final String THE_URL = "item_url";
	Button rate;
	Button upload;
	
	@SuppressLint("JavascriptInterface")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_web);
		Intent intent = getIntent();
		String tempUrl = intent.getStringExtra(THE_URL);
		WebView myWebView = (WebView) findViewById(R.id.webview);
		WebSettings webSettings = myWebView.getSettings();
		webSettings.setJavaScriptEnabled(true);
		myWebView.setWebViewClient(new WebViewClient());
		myWebView.setWebChromeClient(new MyWebChromeClient());
		myWebView.addJavascriptInterface(new JavaScriptInterface(this), "Android");
		myWebView.loadUrl(tempUrl);
		
	}
	
	private ValueCallback<Uri> mUploadMessage;
	private final static int FILECHOOSER_RESULTCODE = 1;

	@Override
	protected void onActivityResult(int requestCode, int resultCode,
	        Intent intent) {
	    if (requestCode == FILECHOOSER_RESULTCODE) {
	        if (null == mUploadMessage)
	            return;
	        Uri result = intent == null || resultCode != RESULT_OK ? null
	                : intent.getData();
	        mUploadMessage.onReceiveValue(result);
	        mUploadMessage = null;

	    }
	}
	
	
	
	 protected class MyWebChromeClient extends WebChromeClient {
	      		  
		 public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {

		        mUploadMessage = uploadMsg;
		        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
		        i.addCategory(Intent.CATEGORY_OPENABLE);
		        i.setType("image/*");
		        WebActivity.this.startActivityForResult(
		                Intent.createChooser(i, "Image Browser"),
		                FILECHOOSER_RESULTCODE);
		    }
		 public void openFileChooser( ValueCallback<Uri> uploadMsg, String acceptType ){
			 
		        mUploadMessage = uploadMsg;
		        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
		        i.addCategory(Intent.CATEGORY_OPENABLE);
		        i.setType("image/*");
		        WebActivity.this.startActivityForResult(
		                Intent.createChooser(i, "Image Browser"),
		                FILECHOOSER_RESULTCODE);
		 }
		 
	 }
	 
	 public class JavaScriptInterface {
	     Context mContext;
	 
	     // Instantiate the interface and set the context
	     JavaScriptInterface(Context c) {
	         mContext = c;
	     }
	      
	     //using Javascript to call the finish activity
	     public void closeMyActivity() {
	         finish();
	     }
	      
	 }

}
