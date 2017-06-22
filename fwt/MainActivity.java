package com.noname.fwt;

import android.app.*;
import android.os.*;
import android.widget.*;
import android.view.View.*;
import android.view.*;
import android.content.*;
import android.webkit.*;

public class MainActivity extends Activity 
{
	Button buttonStartWindow;
	WebView mainWebView;
	
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		
		buttonStartWindow = (Button) findViewById(R.id.button1);
		buttonStartWindow.setOnClickListener( new View.OnClickListener(){
			@Override
			public void onClick(View v){
				ComponentName name = startService(new Intent(MainActivity.this , FloatingWindow.class));
			}
		});
		
		mainWebView = (WebView)findViewById(R.id.webview1);
		WebSettings webSettings = mainWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
		mainWebView.setWebViewClient(new WebViewClient());
		mainWebView.loadUrl("https://google.com");
		
    }

	@Override
	protected void onDestroy()
	{
		// TODO: Implement this method
		stopService(new Intent(MainActivity.this , FloatingWindow.class));
		
		super.onDestroy();
	}
	
}
