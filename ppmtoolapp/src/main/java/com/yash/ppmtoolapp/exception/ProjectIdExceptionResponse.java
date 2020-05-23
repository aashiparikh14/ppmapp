package com.yash.ppmtoolapp.exception;

public class ProjectIdExceptionResponse {

	private String projectIdentifier;

	public String getProjectIdentifier() {
		return projectIdentifier;
	}

	public ProjectIdExceptionResponse(String projectIdentifier) {
		super();
		this.projectIdentifier = projectIdentifier;
	}

	public void setProjectIdentifier(String projectIdentifier) {
		this.projectIdentifier = projectIdentifier;
	}
}
