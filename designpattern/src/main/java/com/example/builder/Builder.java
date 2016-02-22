package com.example.builder;

/**
 * Created by Administrator on 2016/2/22.
 */
public interface Builder {
    //创建部件
    void buildPartA();
    void buildPartB();
    void buildPartC();
    //返回最后组装成品结果，组装过程在Director类进行
    Product getResult();
}
