package com.example.yejt.olddriver;

/**
 * Created by Yejt on 2017/8/22 0022.
 */
public abstract class SearchFactory
{
    public abstract SearchTask getSearchTask();

    public abstract SearchForDetailsTask getSearchForDetailsTask();
}
