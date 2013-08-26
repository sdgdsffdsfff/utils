// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PersonServiceImpl.java

package com.utils.AOPTest;

import java.io.PrintStream;

public class PersonServiceImpl
{

    public PersonServiceImpl()
    {
        user = null;
    }

    public PersonServiceImpl(String user)
    {
        this.user = null;
        this.user = user;
    }

    public String getPersonName(Integer personid)
    {
        System.out.println("\u6211\u662FgetPersonName\u65B9\u6CD5");
        String local = null;
        local = "cn";
        return local;
    }

    public void save(String name)
    {
        String local = "cn";
        System.out.println("\u6211\u662Fsave\u65B9\u6CD5");
    }

    public void update(String name, Integer personid)
    {
        System.out.println("\u6211\u662Fupdate\u65B9\u6CD5");
    }

    public String getUser()
    {
        return user;
    }

    public static String getHead()
    {
        return head;
    }

    public static void setHead(String head)
    {
        head = head;
    }

    protected void aaa()
    {
        System.out.println("protected method");
    }

    private static String head = "PersonServiceImpl has been inited!";
    private String user;

}
