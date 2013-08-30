package android.stickynotes.httpd;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import java.util.Hashtable;

import android.graphics.Bitmap;
import android.stickynotes.httpd.NanoHTTPD.Response;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.Reader;
import com.google.zxing.ReaderException;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;

/**
 * 二維碼工具類，依賴於Google的zxing項目jar包
 * @author wilson
 *
 */
public class QrCodeUtil {
	

	private final static int QR_WIDTH = 80;
	private final static int QR_HEIGHT = 80;
	// 生成QR图
	public Bitmap createBitmap(String str) {
        try {
            BitMatrix matrix = new MultiFormatWriter().encode(str, BarcodeFormat.QR_CODE, 300, 300);
            int width = matrix.getWidth();
            int height = matrix.getHeight();
            int[] pixels = new int[width*height];
            for(int y = 0; y<width; ++y){
                for(int x = 0; x<height; ++x){
                    if(matrix.get(x, y)){
                        pixels[y*width+x] = 0xff000000; // black pixel
                    } else {
                        pixels[y*width+x] = 0xffffffff; // white pixel
                    }
                }
            }
            Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            bmp.setPixels(pixels, 0, width, 0, 0, width, height);
            return bmp;
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return null;
    }
	
    
}
