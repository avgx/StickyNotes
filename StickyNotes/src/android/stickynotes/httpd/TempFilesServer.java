package android.stickynotes.httpd;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.widget.TextView;

/**
 * @author Paul S. Hawke (paul.hawke@gmail.com)
 *         On: 3/9/13 at 12:47 AM
 */
public class TempFilesServer extends DebugServer {
	
	public static TempFilesServer server = null;
    public static void main(String[] args) {
    	executeInstance();
    }
    
    public static void executeInstance(){
    	server = new TempFilesServer();
        server.setTempFileManagerFactory(new ExampleManagerFactory());
        ServerRunner.executeInstance(server);
    }
    
    public static void startInstance(TextView tv){
    	server = new TempFilesServer();
    	server.setTempFileManagerFactory(new ExampleManagerFactory());
    	ServerRunner.startInstance(server, tv);
    }
    
    public static void stopInstance(TextView tv){
//    	server = new TempFilesServer();
//    	server.setTempFileManagerFactory(new ExampleManagerFactory());
    	if(server != null){
    		ServerRunner.stopInstance(server, tv);
    	}
    }
    

    private static class ExampleManagerFactory implements TempFileManagerFactory {
        @Override
        public TempFileManager create() {
            return new ExampleManager();
        }
    }

    private static class ExampleManager implements TempFileManager {
        private final String tmpdir;
        private final List<TempFile> tempFiles;

        private ExampleManager() {
            tmpdir = System.getProperty("java.io.tmpdir");
            tempFiles = new ArrayList<TempFile>();
        }

        @Override
        public TempFile createTempFile() throws Exception {
            DefaultTempFile tempFile = new DefaultTempFile(tmpdir);
            tempFiles.add(tempFile);
            System.out.println("Created tempFile: " + tempFile.getName());
            return tempFile;
        }

        @Override
        public void clear() {
            if (!tempFiles.isEmpty()) {
                System.out.println("Cleaning up:");
            }
            for (TempFile file : tempFiles) {
                try {
                    System.out.println("   "+file.getName());
                    file.delete();
                } catch (Exception ignored) {}
            }
            tempFiles.clear();
        }
    }
}
