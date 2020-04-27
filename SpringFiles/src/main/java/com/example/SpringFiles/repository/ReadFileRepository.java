package com.example.SpringFiles.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.SpringFiles.model.User;

@Repository
public interface ReadFileRepository extends CrudRepository<User, Long>{

}
