package android.stickynotes.httpd;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Map;

import android.stickynotes.StickyNotesActivity;
import android.stickynotes.httpd.NanoHTTPD.Response.Status;

public class DebugServer extends NanoHTTPD {
    public DebugServer() {
        super(8080);
    }

    public static void main(String[] args) {
        ServerRunner.run(DebugServer.class);
    }

    @Override
    public Response serve(String uri, Method method, Map<String, String> header, Map<String, String> parms, Map<String, String> files) {
        StringBuilder sb = new StringBuilder();
        sb.append("<html>");
        sb.append("<head><title>Debug Server</title></head>");
        sb.append("<body>");
        sb.append("<h1>Response</h1>");
        sb.append("<p><blockquote><b>URI -</b> ").append(String.valueOf(uri)).append("<br />");
        sb.append("<b>Method -</b> ").append(String.valueOf(method)).append("</blockquote></p>");
        sb.append("<h3>Headers</h3><p><blockquote>").append(String.valueOf(header)).append("</blockquote></p>");
        sb.append("<h3>Parms</h3><p><blockquote>").append(String.valueOf(parms)).append("</blockquote></p>");
        sb.append("<h3>Files</h3><p><blockquote>").append(String.valueOf(files)).append("</blockquote></p>");
        sb.append("</body>");
        sb.append("</html>");
        
        if(uri != null && uri.indexOf(".apk") != -1){
            String tmpdir = System.getProperty("java.io.tmpdir");
        	File myFile = new File( tmpdir + uri );
        	InputStream is = null;
			try {
				is = new FileInputStream(myFile);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return new Response(Status.NOT_FOUND, MIME_HTML, is);
			}
        	return new Response(Status.PARTIAL_CONTENT, MIME_DEFAULT_BINARY, is);
        }else{
        	return new Response(sb.toString());
        }
        
        
    }
}
