package com.larditrans.fileDb;

import com.google.gson.Gson;
import com.larditrans.model.User;
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
    private static FileDb ourInstance = new FileDb();

    public static FileDb getInstance() {
        return ourInstance;
    }

    private FileDb() {
    }

    public static Map<String, User> users = new HashMap<>();
    private static final String userDbFilePath = "g:/userdb%s.json";
    private static final Gson gson = new Gson();

    private void readFromFile(String userLogin) throws IOException {
        RandomAccessFile f = new RandomAccessFile(String.format(userDbFilePath, userLogin), "r");
        byte[] buf = new byte[(int) f.length()];
        f.read(buf, 0, (int) f.length());
        f.close();

        String jsonString = new String(buf, "UTF-8");

        User[] usersArray = gson.fromJson(jsonString, User[].class);
        List<User> userList = new LinkedList<>(Arrays.asList(usersArray));
        for (User u : userList)
            users.put(u.getLogin(), u);
    }

    private void writeToFile(String userLogin) throws IOException {
        File f = new File(String.format(userDbFilePath, userLogin));
        if (f.exists())
            f.delete();

        RandomAccessFile raf = new RandomAccessFile(f, "rw");
        List<User> userList = new LinkedList<>(users.values());
        User[] usersArray = new User[userList.size()];
        usersArray = userList.toArray(usersArray);

        String jsonString = gson.toJson(usersArray);
        byte[] buf = jsonString.getBytes("UTF-8");
        raf.write(buf);
        raf.close();
    }

    public User getUserByLogin(String userLogin)
    {
        return users.get(userLogin);
    }
}
