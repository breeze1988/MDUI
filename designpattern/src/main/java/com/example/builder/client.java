package com.example.builder;


/**
 * Created by Administrator on 2016/2/22.
 */
public class client {
    public static void main(String[] args){
        ConcreteBuilder builder = new ConcreteBuilder();
        Director director = new Director(builder);
        director.construct();
        Product product = builder.getResult();
    }
}
