package com.noname.fwt;
import android.app.*;
import android.os.*;
import android.content.*;
import android.view.*;
import android.widget.*;
import android.graphics.*;
import android.widget.FrameLayout.*;
import android.webkit.*;

public class FloatingWindow extends Service
{

	private WindowManager wm;
	private LinearLayout ll;
	private Intent intent;
	private WebView webView;
	
	@Override
	public IBinder onBind(Intent p1)
	{
		// TODO: Implement this method
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId)
	{
		// TODO: Implement this method
		this.intent = intent;
		//loadUrlInWebView(intent.getData().toString());
		return super.onStartCommand(intent, flags, startId);
	}
	
	
	
	@Override
	public void onCreate()
	{
		super.onCreate();
		
		wm = (WindowManager) getSystemService(WINDOW_SERVICE);
		
	
		ll = new LinearLayout(this);
		LinearLayout.LayoutParams llParameters = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT , LinearLayout.LayoutParams.MATCH_PARENT );
		
		ll.setBackgroundColor(Color.argb(150 , 30 , 50 , 20));
		ll.setLayoutParams(llParameters);
		ll.setOrientation(LinearLayout.VERTICAL);
		
		final WindowManager.LayoutParams parametrs = new WindowManager.LayoutParams(560 , 460 , WindowManager.LayoutParams.TYPE_PHONE , WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,PixelFormat.TRANSLUCENT);
		parametrs.x = 0;
		parametrs.y = 0;
		parametrs.gravity = Gravity.CENTER | Gravity.CENTER;
		
		wm.addView(ll , parametrs);
		
		Button button = new Button(this);
		button.setTextSize(4);
		button.setText("x");
		button.setLayoutParams(new TableRow.LayoutParams(100, 80));
		
		//webView = new WebView(this);
		//WebSettings webSettings = webView.getSettings();
        //webSettings.setJavaScriptEnabled(true);
		webView = new WebView(getApplicationContext());
		webView.getSettings().setPluginState(WebSettings.PluginState.ON);
		webView.getSettings().setJavaScriptEnabled(true);
		//webView.loadUrl("http://apiblog.youtube.com/2010/07/new-way-to-embed-youtube-videos.html");
		webView.setWebViewClient(new WebViewClient());
		webView.loadUrl("https://youtu.be/82wvVMu3vj0");
		webView.getSettings().setUserAgentString("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/534.36 (KHTML, like Gecko) Chrome/13.0.766.0 Safari/534.36");
		webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
		//ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(1000 , 1000);
		//webView.setLayoutParams(params);
		String frameVideo = "<html><body>https://youtu.be/82wvVMu3vj0<br> <iframe width=\"320\" height=\"315\" src=\"https://www.youtube.com/\" frameborder=\"0\" allowfullscreen></iframe></body></html>";

        webView.loadData(frameVideo, "text/html", "utf-8");

        webView.loadUrl("http://www.youtube.com/");
		
		
	
		ll.addView(button);
		ll.addView(webView);
	
	
		ll.setOnTouchListener(new View.OnTouchListener(){
			
			private WindowManager.LayoutParams updatedParametrs = parametrs;
			int x , y;
			float touchedX , touchedY;
			
			@Override
			public boolean onTouch(View arg0 , MotionEvent arg1)
			{
				switch(arg1.getAction()){
					case MotionEvent.ACTION_DOWN:
						
						x = updatedParametrs.x;
						y = updatedParametrs.y;
						
						touchedX = arg1.getRawX();
						touchedY = arg1.getRawY();
						break;
					case MotionEvent.ACTION_MOVE: 
						
						updatedParametrs.x = (int)(x+(arg1.getRawX() - touchedX));
						updatedParametrs.y = (int)(y+(arg1.getRawY() - touchedY));
						
						wm.updateViewLayout(ll , updatedParametrs);
						
						break;
					default:
						break;
				}
				
				return false;
			}
		});
		
		
		button.setOnClickListener(
			new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Toast toast = Toast.makeText(getApplicationContext(), "Closed", Toast.LENGTH_SHORT);
					toast.show();
					webView.loadUrl("");
					ll.removeAllViews();
					wm.removeView(ll);
					stopSelf();
				}
			}
		);
		
	}

}

