package com.abderrahmane.webappexec.helpers;

import java.io.File;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.Wrapper;
import org.apache.catalina.startup.Tomcat;

import jakarta.servlet.Servlet;

public class TomcatAppLauncher {
    private final String baseDir = "./target/tomcat";
    private String docBase = "src/main/webapp";
    private String host = "localhost";
    private int port = 8080;
    private final Tomcat tomcat;
    private Context context;

    public TomcatAppLauncher () {
        this.tomcat = new Tomcat();
        this.tomcat.setBaseDir(baseDir);
        this.tomcat.getConnector();
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setPort(String port) {
        this.port = Integer.parseInt(port);
    }

    public void setDocBase (String docBase) {
        this.docBase = docBase;
    } 

    public Context createWebapp () {
        return this.createWebapp("");
    }

    public Context createWebapp (String contextPath) {
        this.setupDirectory();
        this.tomcat.setPort(this.port);
        this.tomcat.setHostname(this.host);

        this.context = this.tomcat.addWebapp(contextPath, new File(this.baseDir, "ROOT").getAbsolutePath());

        return this.context;
    }

    public Wrapper addServlet (String servletName, String servletClass, String path) throws IllegalStateException {
        if (this.context != null) throw new IllegalStateException("Web App is not created Yet run : tomcatLauncher.createWebapp()");

        Wrapper wrapper = Tomcat.addServlet(this.context, servletName, servletClass);
        this.context.addServletMappingDecoded(path, servletName);

        return wrapper;
    }

    public Wrapper addServlet (String servletName, Servlet servlet, String path) throws IllegalStateException {
        if (this.context != null) throw new IllegalStateException("Web App is not created Yet run : tomcatLauncher.createWebapp()");

        Wrapper wrapper = Tomcat.addServlet(this.context, servletName, servlet);
        this.context.addServletMappingDecoded(path, servletName);

        return wrapper;
    }

    public void start () throws LifecycleException {
        this.tomcat.start();
        this.tomcat.getServer().await();
    }

    private void setupDirectory () {
        File appConfigDir = new File(this.baseDir, "ROOT");
        File webappDir = new File(this.docBase);

        if (!appConfigDir.exists()) appConfigDir.mkdirs();

        FileSystem.copyDirectoryContent(webappDir, appConfigDir);
    }
}
