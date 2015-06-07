package com.mygdx.game;

import aima.core.search.csp.Assignment;

/**
 * Created by tin on 6/8/15.
 */
public class Message {
    private Assignment msg;

    public Message(Assignment assignment){
        this.msg=assignment;
    }

    public Assignment getMsg() {
        return msg;
    }

}
