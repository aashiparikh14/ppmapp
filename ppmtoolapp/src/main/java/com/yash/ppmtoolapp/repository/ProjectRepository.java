package com.yash.ppmtoolapp.repository;

import org.springframework.data.repository.CrudRepository;

import com.yash.ppmtoolapp.domain.Project;

public interface ProjectRepository extends CrudRepository<Project, Long> {
	
	Iterable<Project> findAll();

	Project findByProjectIdentifier(String projectId);
}

