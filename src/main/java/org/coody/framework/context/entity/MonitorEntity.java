package org.coody.framework.context.entity;

import java.util.Date;

import org.coody.framework.context.base.BaseModel;

@SuppressWarnings("serial")
public class MonitorEntity extends BaseModel{
	
	private Date runTime;
	
	private Date resultTime;

	private String input;
	
	private String output;

	public String getInput() {
		return input;
	}

	public Date getRunTime() {
		return runTime;
	}

	public void setRunTime(Date runTime) {
		this.runTime = runTime;
	}

	public Date getResultTime() {
		return resultTime;
	}

	public void setResultTime(Date resultTime) {
		this.resultTime = resultTime;
	}

	public void setInput(String input) {
		this.input = input;
	}

	public String getOutput() {
		return output;
	}

	public void setOutput(String output) {
		this.output = output;
	}
	
}
