package io.dang;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

import javax.servlet.ServletException;
import java.io.File;
import java.net.MalformedURLException;

public class RunTomcat {
  public static void main(String[] args) throws ServletException, LifecycleException, MalformedURLException {
    final int TOMCAT_PORT = 8080;
    String webAppPath = "src/main/webapp";
    String contextPath = webAppPath + "/META-INF/context.xml";
    final Tomcat tomcat = new Tomcat();
    tomcat.enableNaming();
    tomcat.setPort(TOMCAT_PORT);
    tomcat.setSilent(true);
    tomcat.getServer().setParentClassLoader(Thread.currentThread().getContextClassLoader());
    Context ctx = tomcat.addWebapp("/auth", new File(webAppPath).getAbsolutePath());
    ctx.setConfigFile(new File(contextPath).toURI().toURL());
    tomcat.start();
    tomcat.getServer().await();

    Runtime.getRuntime().addShutdownHook(new Thread() {
      @Override
      public void run() {
        try {
          tomcat.stop();
        } catch (LifecycleException ignored) {
        }
      }
    });
  }
}
