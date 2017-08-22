package com.example.yejt.olddriver;

/**
 * Created by Yejt on 2017/8/15 0015.
 */
public class SearchContract
{
    public static int SOURCE_SOBT8 = 0;
    public static int SOURCE_TORRENT_KITTY = 1;

    public class Sobt8Contract
    {
        public static final String PREFIX = "http://www.sobt8.com/q/";
        public static final String PREFIX_DETAIL = "http://www.sobt8.com";
        public static final String INFIX = "_rel_";
        public static final String POSTFIX = ".html";
    }

    public class TorrentKittyContract
    {
        public static final String PREFIX = "https://www.torrentkitty.tv/search/";
        public static final String PREFIX_DETAIL = "https://www.torrentkitty.tv";
    }

}
