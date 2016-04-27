package com.larditrans;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        try {
            String appFile = System.getProperty("lardi.conf");
            if (null == appFile)
                throw new IOException();
            Properties properties = new Properties();
            InputStream in = new FileInputStream(appFile);
            properties.load(in);
            in.close();
            for (String p : properties.stringPropertyNames())
                System.setProperty(p, properties.getProperty(p));
        } catch (IOException ex) {
            throw new IllegalArgumentException("Configuration file is invalid. Run mvn -Dlardi.conf=<conf.file> to start application. Starting application with src/main/resources/application.properties settings.");
        }
        catch (IllegalArgumentException ex) {}
        finally {SpringApplication.run(Main.class, args);}
    }

}
