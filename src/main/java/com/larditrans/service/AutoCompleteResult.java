package com.larditrans.service;

/**
 * Created by sergey on 24.04.2016.
 */

/* This class is user for bootstrap autocomplete requests.
 * It represents a single found autocomplete entry: it's id and name */

public class AutoCompleteResult {
    String id;
    String name;

    public AutoCompleteResult(String id, String name) {
        this.id = id;
        this.name = name;
    }
}

