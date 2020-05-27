package com.yash.ppmtoolapp.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yash.ppmtoolapp.domain.Backlog;
import com.yash.ppmtoolapp.domain.Project;
import com.yash.ppmtoolapp.domain.ProjectTask;

import com.yash.ppmtoolapp.repository.BacklogRepository;
import com.yash.ppmtoolapp.repository.ProjectRepository;
import com.yash.ppmtoolapp.repository.ProjectTaskRepository;



@Service
public class ProjectTaskService { 
	
	@Autowired
	private BacklogRepository backlogRepository;
	
	@Autowired
	private ProjectTaskRepository projectTaskRepository;
	
	
	
	public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask) {
		
		
			//ProjectTasks to be added to specific project, project!=null, Backlog Exists
			Backlog backlog =  backlogRepository.findByProjectIdentifier(projectIdentifier);
			
			//Set the Backlog to project task
			projectTask.setBacklog(backlog);
			
			//We want our project Sequence to be like this. IDPRO-1  IDPRO-2  ...100 101
			Integer backLogSequence = backlog.getPTSequence();
			//Update the BacklogSequence
			backLogSequence++;
			backlog.setPTSequence(backLogSequence);
			//Add backlogSequence to ProjectTask
			projectTask.setProjectSequence(projectIdentifier+"-"+backLogSequence);
			projectTask.setProjectIdentifer(projectIdentifier);
			
			//Initial priority when priority is null
			if(projectTask.getPriority()==null) {
				projectTask.setPriority(3);
			}			
			//INITIAL Status when status is null
			if(projectTask.getStatus()=="" || projectTask.getStatus()==null) {
				projectTask.setStatus("TO_DO");
			}
			return projectTaskRepository.save(projectTask);
		
		
	}
	
	

}