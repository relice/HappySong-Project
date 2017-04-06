package com.happysong.app.selectState;

/**
 * @Package: com.happysong.app.selectState
 * @Author: Relice
 * @Date: 2017/2/27
 * @Des: TODO
 */

public class ClickContext {

    public ClickContext() {
    }

    public void setState(State state) {
        state.handleState();
    }
}
