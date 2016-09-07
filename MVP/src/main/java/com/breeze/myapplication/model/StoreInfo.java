package com.breeze.myapplication.model;

import io.realm.RealmModel;
import io.realm.RealmObject;
import io.realm.annotations.RealmClass;

/**
 * @version V1.0 <version description>
 * @filename: StoreInfo.java
 * @package: com.dmall.wms.picker.model
 * @description:
 * @author: hao.ni@dmall.com
 * @date: 2015-08-31 17:31
 */
@RealmClass
public class StoreInfo extends RealmObject implements RealmModel{
    public String storeName = "";
    public int storeId = 0;
    public int venderId = 0;

    public StoreInfo(){}

    public StoreInfo(String storeName) {
        this.storeName = storeName;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public int getVenderId() {
        return venderId;
    }

    public void setVenderId(int venderId) {
        this.venderId = venderId;
    }
}
