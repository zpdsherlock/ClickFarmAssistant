package com.zpdsherlock.clickfarmassistant.store;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class RecordStore {
    private static RecordStore store = new RecordStore();

    public static RecordStore getInstance() {
        return store;
    }

    private LinkedList<RecordNode> nodes = new LinkedList<>();

    private RecordStore() {
    }

    public LinkedList<RecordNode> getNodes() {
        return nodes;
    }
}
