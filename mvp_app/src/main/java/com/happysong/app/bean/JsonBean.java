package com.happysong.app.bean;

import java.io.Serializable;

/**
 * @Package: com.example.relicemxd.happysong.bean
 * @Author: Relice
 * 所有需要用 json，FastJson，
 * 获取gson的 解析的都需要实现这个，
 * 会在混淆文件里面对这个不进行混淆
 */
public class JsonBean implements INotProguard, Serializable {
    public JsonBean() {
        super();
    }
}
