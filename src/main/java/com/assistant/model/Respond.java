package com.assistant.model;

import java.io.Serializable;

public class Respond implements Serializable {
    public String message;
    public int code;
    public Object data;
    public Object error;
}
