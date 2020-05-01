package com.example.SpringFiles.service;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.SpringFiles.model.User;
import com.example.SpringFiles.repository.ReadFileRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

@Service
@Transactional
public class SpringReadFileImp implements ReadFileService {

	@Autowired
	private ReadFileRepository repo;

	@Override
	public List<User> findAll() {

		return (List<User>) repo.findAll();
	}

	@Override
	public boolean saveDataFromUploadFile(MultipartFile file) {
		boolean isFlag = false;

		String extension = FilenameUtils.getExtension(file.getOriginalFilename());
		if (extension.equalsIgnoreCase("json")) {
			isFlag = readDataFromJson(file);
		} else if (extension.equalsIgnoreCase("csv")) {
			isFlag = readDataFromCsv(file);
		}
		else if (extension.equalsIgnoreCase("xls") || extension.equalsIgnoreCase("xlsx")) {
			isFlag = readDataFromExcel(file);
		}

		return isFlag;
	}

	private boolean readDataFromExcel(MultipartFile file) {

		Workbook workbook=getWorkBook(file);
		Sheet sheet=workbook.getSheetAt(0);
		Iterator<Row> rows=sheet.iterator();
		rows.next();
		while(rows.hasNext())
		{
			Row row=rows.next();
			User user=new User();
			if(row.getCell(0).getCellType()==Cell.CELL_TYPE_STRING)
			{
				user.setFname(row.getCell(0).getStringCellValue());
			}
			else if(row.getCell(1).getCellType()==Cell.CELL_TYPE_STRING)
			{
				user.setLname(row.getCell(1).getStringCellValue());
			}
			else if(row.getCell(2).getCellType()==Cell.CELL_TYPE_STRING)
			{
				user.setEmail(row.getCell(2).getStringCellValue());
			}
			else if(row.getCell(3).getCellType()==Cell.CELL_TYPE_NUMERIC)
			{
				String phno=NumberToTextConverter.toText(row.getCell(3).getNumericCellValue());
				user.setPhno(phno);
			}
			else if(row.getCell(3).getCellType()==Cell.CELL_TYPE_STRING)
			{
				user.setPhno(row.getCell(3).getStringCellValue());
			}
			user.setFileType(FilenameUtils.getExtension(file.getOriginalFilename()));
			repo.save(user);
		}
		return true;
	}

	private Workbook getWorkBook(MultipartFile file) {

		Workbook workbook=null;
		String extension=FilenameUtils.getExtension(file.getOriginalFilename());
		try
		{
			if(extension.equalsIgnoreCase("xlsx"))
			{
				workbook=new XSSFWorkbook(file.getInputStream());
			}
			else if(extension.equalsIgnoreCase("xls"))
			{
				workbook=new XSSFWorkbook(file.getInputStream());
			}

		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return workbook;
	}

	private boolean readDataFromCsv(MultipartFile file) {

		try {

			InputStreamReader reader=new InputStreamReader(file.getInputStream());
			CSVReader csvReader=new CSVReaderBuilder(reader).withSkipLines(1).build();
			List<String[]> rows=csvReader.readAll();
			for(String[] row:rows)
			{
				repo.save(new User(row[0], row[1], row[2], row[3],FilenameUtils.getExtension(file.getOriginalFilename())));
			}
			return true;

		} catch (Exception e) {
			return false;
		}
	}



	private boolean readDataFromJson(MultipartFile file) {

		try {
			InputStream inputStream = file.getInputStream();
			ObjectMapper mapper = new ObjectMapper();
			List<User> users = Arrays.asList(mapper.readValue(inputStream, User[].class));
			if (users != null && users.size() > 0) {
				for (User user : users) {
					user.setFileType(FilenameUtils.getExtension(file.getOriginalFilename()));
					repo.save(user);
				}
			}
			return true;
		} catch (Exception e) {
			return false;
		}

	}

}
