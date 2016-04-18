package com.larditrans.dao;

import com.google.gson.Gson;
import com.larditrans.model.User;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.reflect.Type;
import java.util.*;

/**
 * Created by sergey on 15.04.2016.
 */
@Component
public class UserDaoImplFile implements UserDao {

    public static final int flushDelay = 10000;
    public static Map<String, User> users = new HashMap<>();
    private static final String userDbFilePath = "g:/userdb.json";
    private static final Gson gson = new Gson();
    private static Long currentId;

    public UserDaoImplFile() {
        try
        {
            readFromFile();
            currentId = 0L;
            for (Map.Entry<String, User> e : users.entrySet())
                if (currentId < e.getValue().getId())
                    currentId = e.getValue().getId();
        }
        catch (IOException ex) {ex.printStackTrace();}

        new Flusher(); // run the thread that writes the user db to the file
    }

    private void readFromFile() throws IOException
    {
        RandomAccessFile f = new RandomAccessFile(userDbFilePath, "r");
        byte[] buf = new byte[(int) f.length()];
        f.read(buf, 0, (int) f.length());
        f.close();

        String jsonString = new String(buf, "UTF-8");

        User[] usersArray = gson.fromJson(jsonString, User[].class);
        List<User> userList = new LinkedList<>(Arrays.asList(usersArray));
        for (User u : userList)
            users.put(u.getLogin(), u);
    }

    private void writeToFile() throws IOException
    {
        File f = new File(userDbFilePath);
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

    @Override
    public User add(User user) {
        user.setId(++currentId);
        users.put(user.getLogin(), user);
        return user;
    }

    @Override
    public void delete(User user) {
        users.remove(user.getLogin());
        try
        {
            writeToFile();
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public User update(User user) {
        if (null==user.getLogin() || user.getLogin().isEmpty())
            return null;
        else
        {
            users.put(user.getLogin(), user);
            try
            {
                writeToFile();
            }
            catch (IOException ex)
            {
                ex.printStackTrace();
            }
            return user;
        }

    }

    @Override
    public User getByLogin(String login) {
        return users.get(login);
    }

    private class Flusher implements Runnable
    {
        Thread thread;

        public Flusher() {
            thread = new Thread(this, "File Flusher Thread");
            thread.setDaemon(true);
            thread.start();
        }

        @Override
        public void run() {
//            while (!thread.isInterrupted())
//            {
//                System.out.println("zzz");
//                try
//                {
//                    writeToFile();
//                }
//                catch (IOException ex)
//                {
//                    ex.printStackTrace();
//                }
//                try
//                {
//                    Thread.sleep(flushDelay);
//                }
//                catch (InterruptedException ex)
//                {
//                    ex.printStackTrace();
//                    System.out.println("Interrupted");
//                }
//            }
//            System.out.println("Interrupted");
//            try
//            {
//                writeToFile();
//            }
//            catch (IOException ex)
//            {
//                ex.printStackTrace();
//            }
        }
    }
}
