package com.example.SpringFiles.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.example.SpringFiles.model.User;

public interface ReadFileService {

public	List<User> findAll();

public boolean saveDataFromUploadFile(MultipartFile file);

}
