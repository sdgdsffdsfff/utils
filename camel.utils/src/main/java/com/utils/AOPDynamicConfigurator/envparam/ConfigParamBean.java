// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ConfigParamBean.java

package com.utils.AOPDynamicConfigurator.envparam;


public class ConfigParamBean
{

    public ConfigParamBean()
    {
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getValue()
    {
        return value;
    }

    public void setValue(String value)
    {
        this.value = value;
    }

    public String getRemark()
    {
        return remark;
    }

    public void setRemark(String remark)
    {
        this.remark = remark;
    }

    public String getActive()
    {
        return active;
    }

    public void setActive(String active)
    {
        this.active = active;
    }

    public String getSystem()
    {
        return system;
    }

    public void setSystem(String system)
    {
        this.system = system;
    }
    
    public String toString(){
    	StringBuffer sb = new StringBuffer();
    	sb.append("id:"+id + " ## ")
    		.append("name:"+name+ " ## ")
    		.append("value:"+value+ " ## ")
    		.append("remark:"+remark+ " ## ")
    		.append("active:"+active+ " ## ")
    		.append("system:"+system+ " ## ");
    	return sb.toString();
    }

    private Integer id;
    private String name;
    private String value;
    private String remark;
    private String active;
    private String system;
}
