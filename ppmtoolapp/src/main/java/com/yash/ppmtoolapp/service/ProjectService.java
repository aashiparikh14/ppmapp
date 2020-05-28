package com.yash.ppmtoolapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yash.ppmtoolapp.domain.Backlog;
import com.yash.ppmtoolapp.domain.Project;
import com.yash.ppmtoolapp.exception.ProjectIdException;
import com.yash.ppmtoolapp.repository.BacklogRepository;
import com.yash.ppmtoolapp.repository.ProjectRepository;

@Service
public class ProjectService {
	@Autowired
	private ProjectRepository projectRepository;
	
	@Autowired
	private BacklogRepository backlogRepository;
	
		public Project saveOrUpdateProject(Project project) {
			
			try {
				project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
				//while saving project ,backlog should be saved.
				//When project is not having Id,it is a new project-create Backlog.
				if(project.getId()==null)
					{
						Backlog backlog=new Backlog();
						project.setBacklog(backlog);//one to one relationship
						backlog.setProject(project);//one to one relationship
						backlog.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
					}
				if(project.getId()!=null)
					{
						project.setBacklog(backlogRepository.findByProjectIdentifier(project.getProjectIdentifier().toUpperCase()));
					}
				return projectRepository.save(project);
				} 
			catch(Exception ex) {
				throw new ProjectIdException("Project Id '"+project.getProjectIdentifier().toUpperCase()+"' already exists");
				}
		}
		
		
		public Iterable<Project> findAllProjects(){
			return projectRepository.findAll();
		}
		
		public Project findProjectByIdentifier(String projectId) {
			Project project =  projectRepository.findByProjectIdentifier(projectId.toUpperCase());
			if(project==null) {
				throw new ProjectIdException("Project Id '"+projectId.toUpperCase()+"' does not exists");
			}
			return project;
		}
		
		public void deleteProjectByIdentifier(String projectId){
	        Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());

	        if(project == null){
	            throw  new  ProjectIdException("Cannot delete Project with ID '"+projectId+"'. This project does not exist");
	        }

	        projectRepository.delete(project);
	    }
}
