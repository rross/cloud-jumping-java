package com.xede.dreamforce12.service;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

@XmlRootElement(name="List")
@XmlSeeAlso(Engine.class)
public class JaxbList<T> {	
	protected List<T> list;

    public JaxbList(){}

    public JaxbList(List<T> list){
        this.list=list;
    }

    @XmlElement(name="Item")
    public List<T> getList(){
        return list;
    }
}
