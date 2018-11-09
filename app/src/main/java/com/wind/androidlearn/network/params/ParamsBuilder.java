package com.wind.androidlearn.network.params;

/**
 * 网络参数构建类
 */
public abstract class ParamsBuilder {

    public Params params;

    public ParamsBuilder(){
        params = convert(new Params());
    }

    protected abstract Params convert(Params params);

    public Params create(){
        return params;
    };
}
