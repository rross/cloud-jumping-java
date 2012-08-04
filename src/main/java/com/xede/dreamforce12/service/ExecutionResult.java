package com.xede.dreamforce12.service;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ExecutionResult 
{
	private Boolean result;
	private String errorMessage;
	private String answer;
	
	public Boolean getResult() {
		return result;
	}
	public void setResult(Boolean result) {
		this.result = result;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	
}
