package com.utils.AOPDynamicConfigurator.envparam;

import java.util.Map;

public interface IConfigParamReader
{

    public abstract Map<String,ConfigParamBean> getAllConfigParam(EnvConfigBean envconfigbean);
}
