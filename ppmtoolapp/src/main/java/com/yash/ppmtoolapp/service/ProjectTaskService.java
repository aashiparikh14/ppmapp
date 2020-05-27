package com.yash.ppmtoolapp.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yash.ppmtoolapp.domain.Backlog;
import com.yash.ppmtoolapp.domain.Project;
import com.yash.ppmtoolapp.domain.ProjectTask;
import com.yash.ppmtoolapp.exception.ProjectNotFoundException;
import com.yash.ppmtoolapp.repository.BacklogRepository;
import com.yash.ppmtoolapp.repository.ProjectRepository;
import com.yash.ppmtoolapp.repository.ProjectTaskRepository;



@Service
public class ProjectTaskService { 
	
	@Autowired
	private BacklogRepository backlogRepository;
	
	@Autowired
	private ProjectTaskRepository projectTaskRepository;
	
	@Autowired
	private ProjectRepository projectRepository;
	
	public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask) {
		
		try {
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
			projectTask.setProjectIdentifier(projectIdentifier);
			
			//Initial priority when priority is null
			if(projectTask.getPriority()==null) {
				projectTask.setPriority(3);
			}			
			//INITIAL Status when status is null
			if(projectTask.getStatus()=="" || projectTask.getStatus()==null) {
				projectTask.setStatus("TO_DO");
			}
			return projectTaskRepository.save(projectTask);
		} catch(Exception ex) {
			throw new ProjectNotFoundException("Project Not Found");
		}
		
	}
	
	public Iterable<ProjectTask> findBacklogById(String id){
		
		Project project =projectRepository.findByProjectIdentifier(id);
		if(project==null) {
			throw new ProjectNotFoundException("Project with id: '"+id+"' does not exist");
		}
		return projectTaskRepository.findByProjectIdentifierOrderByPriority(id);
	}
	
	public ProjectTask findPTByProjectSequence(String backlog_id, String pt_id) {
		
		return projectTaskRepository.findByProjectSequence(pt_id);
		
	}

}