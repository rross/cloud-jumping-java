package com.rickross.dreamforce12.service;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class Engine 
{
	private String fullName;
	private String version;
	private JaxbList<String> shortNames;
	
	public Engine()
	{
		// required for JaxB support
	}
	
	public Engine(String fullName, String version, List<String> shortNames) 
	{		
		this.fullName = fullName;
		this.version = version;
		this.shortNames = new JaxbList<String>(shortNames);
	}
	
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	@XmlElement(name="ShortNames")
	public JaxbList<String> getShortNames() {
		return shortNames;
	}
	public void setShortNames(JaxbList<String> shortNames) {
		this.shortNames = shortNames;
	} 

	
}
