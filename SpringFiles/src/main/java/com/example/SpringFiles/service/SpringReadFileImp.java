package com.example.SpringFiles.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.SpringFiles.repository.ReadFileRepository;

@Service
@Transactional
public class SpringReadFileImp implements ReadFileService{

	@Autowired
	private ReadFileRepository repo;
}
