package com.larditrans.fileDb;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.larditrans.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.*;

/**
 * Created by sergey on 19.04.2016.
 */
@Service
public class FileDb {

    @Value("${filedb.path}")
    private String fileDbPath;
    private static final String userDbFilePath = "/user_%s.json";
    private static final Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

    public boolean exists(String userLogin) {
        return new File(String.format(fileDbPath + userDbFilePath, userLogin)).exists();
    }

    public synchronized User readFromFile(String userLogin) throws IOException {

        if (exists((userLogin))) {
            RandomAccessFile raf = new RandomAccessFile(String.format(fileDbPath + userDbFilePath, userLogin), "r");
            byte[] buf = new byte[(int) raf.length()];
            raf.read(buf, 0, (int) raf.length());
            raf.close();
            String jsonString = new String(buf, "UTF-8");

            User user = gson.fromJson(jsonString, User.class);
            user.setId(0);
            return user;
        } else
            return null;
    }

    public synchronized void writeToFile(User user) throws IOException {
        File f = new File(fileDbPath);
        if (!f.exists())
            f.mkdirs();

        f = new File(String.format(fileDbPath + userDbFilePath, user.getLogin()));
        if (f.exists())
            f.delete();

        RandomAccessFile raf = new RandomAccessFile(f, "rw");

        user.setId(0);
        String jsonString = gson.toJson(user, user.getClass());
        byte[] buf = jsonString.getBytes("UTF-8");
        raf.write(buf);
        raf.close();
    }

    public synchronized void deleteFile(User user)
    {
        File f = new File(String.format(fileDbPath + userDbFilePath, user.getLogin()));
        if (!f.exists())
            throw new IllegalArgumentException("User not found. = " + user.getLogin());
        f.delete();
    }
}
