package com.larditrans.tokenizer;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by sergey on 21.04.2016.
 */
public class Tokenizer {
    private static Tokenizer ourInstance = new Tokenizer();

    public static Tokenizer getInstance() {
        return ourInstance;
    }

    private Tokenizer() {
    }

    private static Map<String, Integer> tokens = new HashMap<>();
    private static final Random RND = new Random();

    public Integer getToken(String userLogin)
    {
        if (tokens.containsKey(userLogin))
            return tokens.get(userLogin);
        else
        {
            Integer token = RND.nextInt(Integer.MAX_VALUE) + 1;
            tokens.put(userLogin, token);
            return token;
        }

    }

}
