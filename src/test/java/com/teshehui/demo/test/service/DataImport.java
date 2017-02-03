package com.teshehui.demo.test.service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.wwwfred.framework.util.charset.PinyinFormat;
import net.wwwfred.framework.util.charset.PinyinUtil;
import net.wwwfred.framework.util.code.CodeUtil;
import net.wwwfred.framework.util.io.IOUtil;
import net.wwwfred.framework.util.log.LogUtil;
import net.wwwfred.yw.alumni.model.SchoolModel;
import net.wwwfred.yw.alumni.model.YearModel;
import net.wwwfred.yw.alumni.model.YearStudentModel;
import net.wwwfred.yw.alumni.service.SchoolService;
import net.wwwfred.yw.alumni.service.StudentService;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

//@RunWith(SpringJUnit4ClassRunner.class) 
//@ContextConfiguration`(locations = { "config/spring/spring-config-framework.xml" })  
public class DataImport {

	private static AbstractApplicationContext context;
	
	public static void main(String[] args) throws Exception  {
		
		 context = new ClassPathXmlApplicationContext("config/spring/spring-config-framework.xml");
		 
		 yearStudentImport();
		 
		 yearStudentCacheUpdate();
		 
		context.close();
	}

	private static void yearStudentCacheUpdate() {
		
		SchoolService schoolService = context.getBean(SchoolService.class);
		StudentService studentService = context.getBean(StudentService.class);
		
		schoolService.querySchoolList(true, null, null, Arrays.asList("42_20_0001"));
		Set<YearModel> yearModelSet = studentService.queryYearStudentBySchoolID(true, 1L).keySet();
		for (YearModel yearModel : yearModelSet) {
			studentService.queryYearStudentByYearCode(true, yearModel.getCode());
		}
	}

	private static void yearStudentImport() throws Exception {

//		schoolData		
//		1		2017-01-23 03:22:49		42	湖北	高中	20	42_20_0001	麻城一中	http://image.wwwfred.net/alumni/42/20/0001/mcyz.jpg	麻城一中校友联谊会
		
		String schoolCode = "42_20_0001";
		String file = "D:/fred/app/alumni/temp.xlsx";
//		Map<String, List<String>> yearStudentMap = xlsParse(file);
		Map<String, List<String>> yearStudentMap = xlsxParse(file);
		
		SchoolService schoolService = context.getBean(SchoolService.class);
		StudentService studentService = context.getBean(StudentService.class);
		
		SchoolModel schoolModel = schoolService.querySchoolList(null, null, null, Arrays.asList(schoolCode)).get(0);
		
		Map<YearModel,List<YearStudentModel>> yearStudentModelMap = new LinkedHashMap<YearModel, List<YearStudentModel>>();
		for (String year : yearStudentMap.keySet()) {
			yearStudentModelMap = new LinkedHashMap<YearModel, List<YearStudentModel>>();
			
			YearModel yearModel = new YearModel();
			yearModel.setCode(schoolCode+"_"+year);
			yearModel.setName(year);
			yearModel.setSchoolID(schoolModel.getId());
			yearStudentModelMap.put(yearModel, new ArrayList<YearStudentModel>());
			
			List<String> studentNameList = yearStudentMap.get(year);
			for (int i = 0; i < studentNameList.size(); i++) {
				String studentName = studentNameList.get(i);
				YearStudentModel yearStudentModel = new YearStudentModel();
				yearStudentModel.setName(studentName);
				yearStudentModel.setPinyinName(PinyinUtil.convertToPinyinString(studentName, " ", PinyinFormat.WITHOUT_TONE));
				yearStudentModel.setShortPinyinName(PinyinUtil.getShortPinyin(studentName));
				
				String code = (i+1)+"";
				while(code.length()<4)
				{
					code = "0"+code;
				}
				yearStudentModel.setCode(schoolCode+"_"+year+"_"+code);
				yearStudentModelMap.get(yearModel).add(yearStudentModel);
			}
			
			long startTime = System.currentTimeMillis();
			LogUtil.i("start insertYearStudent,year="+yearStudentModelMap.keySet()+",studentSize="+yearStudentMap.values().iterator().next().size());
			studentService.insertYearStudent(yearStudentModelMap);
			LogUtil.i("useTime="+(System.currentTimeMillis()-startTime)+",insertYearStudent,year="+yearStudentModelMap.keySet()+",studentSize="+yearStudentMap.values().iterator().next().size());
		}
	}

	private static Map<String, List<String>> xlsxParse(String file) throws Exception  {
		InputStream is = IOUtil.readLocal(null, file);
		XSSFWorkbook xssfworkbook = new XSSFWorkbook(is);
		
		Map<String, List<String>> yearStudentMap = new LinkedHashMap<String, List<String>>();
		
		for (int i = 0; i < xssfworkbook.getNumberOfSheets(); i++) {
			
			XSSFSheet xssfSheet = xssfworkbook.getSheetAt(i);
			String sheetName = xssfSheet.getSheetName();
			
			if(CodeUtil.isEmpty(sheetName))
				continue;
			yearStudentMap.put(sheetName, new ArrayList<String>());
			
			if(xssfSheet!=null)
			{
				for (int j = 0; j <= xssfSheet.getLastRowNum(); j++) {
					
					XSSFRow xssfRow = xssfSheet.getRow(j);
					
					if(xssfRow!=null)
					{
//						for (int k = hssfRow.getFirstCellNum(); k < hssfRow.getLastCellNum(); k++) {
//							HSSFCell hssfCell = hssfRow.getCell(k);
//							
//						}
						
						XSSFCell xssfCell = xssfRow.getCell(0);
						
						String cellValue = xssfCell.getStringCellValue();
						
						if(!CodeUtil.isEmpty(cellValue))
						{
							yearStudentMap.get(sheetName).add(cellValue);
						}
					}
				}
			}
			
		}
		
		is.close();
		
		return yearStudentMap;
	}

//	private static Map<String, List<String>> xlsParse(String file) throws Exception {
//		InputStream is = IOUtil.readLocal(null, file);
//		HSSFWorkbook hssfworkbook = new HSSFWorkbook(is);
//		
//		Map<String, List<String>> yearStudentMap = new LinkedHashMap<String, List<String>>();
//		
//		for (int i = 0; i < hssfworkbook.getNumberOfSheets(); i++) {
//			
//			HSSFSheet hssfSheet = hssfworkbook.getSheetAt(i);
//			String sheetName = hssfSheet.getSheetName();
//			
//			if(CodeUtil.isEmpty(sheetName))
//				continue;
//			yearStudentMap.put(sheetName, new ArrayList<String>());
//			
//			if(hssfSheet!=null)
//			{
//				for (int j = 1; j <= hssfSheet.getLastRowNum(); j++) {
//					
//					HSSFRow hssfRow = hssfSheet.getRow(j);
//					
//					if(hssfRow!=null)
//					{
////						for (int k = hssfRow.getFirstCellNum(); k < hssfRow.getLastCellNum(); k++) {
////							HSSFCell hssfCell = hssfRow.getCell(k);
////							
////						}
//						
//						HSSFCell hssfCell = hssfRow.getCell(0);
//						
//						String cellValue = hssfCell.getStringCellValue();
//						
//						if(!CodeUtil.isEmpty(cellValue))
//						{
//							yearStudentMap.get(sheetName).add(cellValue);
//						}
//					}
//				}
//			}
//			
//		}
//		
//		is.close();
//		
//		return yearStudentMap;
//	}
	
}
