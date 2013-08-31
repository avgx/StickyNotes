package android.stickynotes;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.stickynotes.httpd.QrCodeUtil;
import android.stickynotes.httpd.TempFilesServer;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class TransportActivity extends Activity {

TextView showLogTextView;
    
    ImageView myImageView;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_transport);
		
		findViewById(R.id.btn_start_serve).setOnClickListener(startServeBtn);
        findViewById(R.id.btn_stop_serve).setOnClickListener(stopServeBtn);
        showLogTextView = (TextView)findViewById(R.id.tv_show_log);
        myImageView = (ImageView)findViewById(R.id.imageview);  
        
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.transport, menu);
		return true;
	}
	
	 private View.OnClickListener startServeBtn = new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				// 获取当前程序路径
				String currentPath = getApplicationContext().getFilesDir()
						.getAbsolutePath();

				// 获取该程序的安装包路径
				String resourcePath = getApplicationContext()
						.getPackageResourcePath();
				String cachePath = System.getProperty("java.io.tmpdir");

				File myFile = new File(resourcePath);
				File targetFile = new File(cachePath, myFile.getName());
				try {
					targetFile.deleteOnExit();
					copyFile(myFile, targetFile);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				TempFilesServer.startInstance(showLogTextView);
				showLogTextView.setText(showLogTextView.getText() + "\n"
						+ "You can use the following adress to visite : " + "\n"
						+ "http://" + getIp() + ":8080");

				showLogTextView.setText(showLogTextView.getText() + "\n"
						+ "currentPath:" + currentPath + "\n" + "resourcePath:"
						+ resourcePath + "\n" + "cachePath:" + cachePath);
				
				QrCodeUtil qcu = new QrCodeUtil();
				String downloadUrl = "http://" + getIp() + ":8080/" + targetFile.getName();
				Bitmap bitmap = qcu.createBitmap(downloadUrl);
				myImageView.setImageBitmap(bitmap);

			}
			
		};
		private View.OnClickListener stopServeBtn = new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				Intent intent = new Intent(MainActivity.this, WebMapActivity.class);
//				startActivity(intent);
				TempFilesServer.stopInstance(showLogTextView);
			}
			
		};
		
		
	    private String getIp(){  
	        WifiManager wm=(WifiManager)getSystemService(Context.WIFI_SERVICE);  
	        //检查Wifi状态    
	        if(!wm.isWifiEnabled())  
	            wm.setWifiEnabled(true);  
	        WifiInfo wi=wm.getConnectionInfo();  
	        //获取32位整型IP地址    
	        int ipAdd=wi.getIpAddress();  
	        //把整型地址转换成“*.*.*.*”地址    
	        String ip=intToIp(ipAdd);  
	        return ip;  
	    }  
	    private String intToIp(int i) {  
	        return (i & 0xFF ) + "." +  
	        ((i >> 8 ) & 0xFF) + "." +  
	        ((i >> 16 ) & 0xFF) + "." +  
	        ( i >> 24 & 0xFF) ;  
	    } 
	    
	    // 复制文件
	    public static void copyFile(File sourceFile, File targetFile) throws IOException {
	        BufferedInputStream inBuff = null;
	        BufferedOutputStream outBuff = null;
	        try {
	            // 新建文件输入流并对它进行缓冲
	            inBuff = new BufferedInputStream(new FileInputStream(sourceFile));

	            // 新建文件输出流并对它进行缓冲
	            outBuff = new BufferedOutputStream(new FileOutputStream(targetFile));

	            // 缓冲数组
	            byte[] b = new byte[1024 * 5];
	            int len;
	            while ((len = inBuff.read(b)) != -1) {
	                outBuff.write(b, 0, len);
	            }
	            // 刷新此缓冲的输出流
	            outBuff.flush();
	        } finally {
	            // 关闭流
	            if (inBuff != null)
	                inBuff.close();
	            if (outBuff != null)
	                outBuff.close();
	        }
	    }

}
