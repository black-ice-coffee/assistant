package com.assistant.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;


public class GoldRate implements Serializable{

    private Root root;

    public GoldRate(){}

    public GoldRate(Root root){
        this.root = root;
    }

    public Root getRoot ()
    {
        return root;
    }

    public void setRoot (Root root)
    {
        this.root = root;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [root = "+root+"]";
    }


}
