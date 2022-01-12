package com.abderrahmane.webappexec;

import com.abderrahmane.webappexec.helpers.TomcatAppLauncher;

public class App {
    public static void main(String[] args) throws Exception {
        TomcatAppLauncher appLauncher = new TomcatAppLauncher();

        appLauncher.setPort(8080);
        appLauncher.setHost("192.168.1.11"); // For testing
        appLauncher.createWebapp();
        appLauncher.start();
    }
}
