package com.xingye.netzoo.zooapplication.main;

import java.util.ArrayList;

/**
 * Created by yx on 17/5/30.
 */

public class ZhihuModel {
    public String date;
    public ArrayList<ZHStory> stories;
    public ArrayList<ZHStory> top_stories;

    public class ZHStory
    {
        public String title;
        public String ga_prefix;
        public boolean multipic;
        public String[] images;
        public int     type;
        public int     id;
    }
}
