package android.stickynotes.httpd;

import java.io.IOException;

import android.widget.TextView;

public class ServerRunner {
    public static void run(Class serverClass) {
        try {
            executeInstance((NanoHTTPD) serverClass.newInstance());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void executeInstance(NanoHTTPD server) {
        try {
            server.start();
        } catch (IOException ioe) {
            System.err.println("Couldn't start server:\n" + ioe);
            System.exit(-1);
        }

        System.out.println("Server started, Hit Enter to stop.\n");

        try {
            System.in.read();
        } catch (Throwable ignored) {
        }

        server.stop();
        System.out.println("Server stopped.\n");
    }
    
    public static void startInstance(NanoHTTPD server){
    	try {
    		server.start();
    	} catch (IOException ioe) {
    		String logString = "Couldn't start server:\n" + ioe;
    		System.err.println(logString);
    		System.exit(-1);
    	}
    }
    
    public static void startInstance(NanoHTTPD server, TextView tv){
    	try {
            server.start();
        } catch (IOException ioe) {
        	String logString = "Couldn't start server:\n" + ioe;
            System.err.println(logString);
            if(tv != null){
            	tv.setText(tv.getText() + "\n" + logString);
            }
            System.exit(-1);
        }
    	tv.setText(tv.getText() + "\n" + "The server is started!");
    }
    
    public static void stopInstance(NanoHTTPD server){
    	server.stop();
    	String logString = "Server is stopped.\n";
    	System.out.println(logString);
    }
    
    public static void stopInstance(NanoHTTPD server, TextView tv){
    	server.stop();
    	String logString = "Server stopped.\n";
        System.out.println(logString);
        if(tv != null){
        	tv.setText(tv.getText() + "\n" + logString);
        }
    }
}
