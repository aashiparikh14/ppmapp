package com.yash.ppmtoolapp.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.yash.ppmtoolapp.domain.Project;

@Repository
public interface ProjectRepository extends CrudRepository<Project, Long> {
	
	Iterable<Project> findAll();

	Project findByProjectIdentifier(String projectId);
}

