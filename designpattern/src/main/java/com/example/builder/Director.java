package com.example.builder;

/**
 * Created by Administrator on 2016/2/22.
 * 将部件最后组装成成品
 */
public class Director {
    private Builder builder;
    public  Director(Builder builder){
        this.builder = builder;
    }

    //组装
    public void construct(){
        builder.buildPartA();
        builder.buildPartB();
        builder.buildPartC();
    }
}
