package com.osci.kr.itda.utills;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

/**
 * Convert Util class 포멧 변경 유틸 클래스
 * 
 * @author
 * @since 2020 .12.24
 * @version 1.0
 * @see
 *
 *      <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 *
 *
 *      </pre>
 */
public class FileUtil{

	/**
	 * 디렉터리 생성
	 * 
	 * @param Object
	 * @return HashMap
	 */
	public static void makeDir(String path){
		File uploadDir = new File(path);
		if(!uploadDir.exists())
			uploadDir.mkdir();
	}
	public static void writeFileLog(String filePath, String FileName, String logText){

		try{
			// 폴더 없을경우 폴더 생성
			FileUtil.makeDir(filePath);
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String date = dateFormat.format(new Date());

			// BufferedWriter 와 FileWriter를 조합하여 사용 (속도 향상)
			BufferedWriter fw = new BufferedWriter(new FileWriter(filePath + FileName + "." + date + ".log", true));
			StringBuffer sb = new StringBuffer(); // 본문내용
			sb.append(logText);
			// 파일안에 문자열 쓰기
			fw.write(sb.toString() + "\n");
			fw.flush();

			// 객체 닫기
			fw.close();

		}catch(Exception e){
			System.out.println(e);
		}

	}
	/**
	 * MultipartFile 를 FILE 로 변경해준다.
	 * 
	 * @param MultipartFile
	 * @return File
	 */
	public static File multipartToFile(MultipartFile multipart){

		File convFile = new File(multipart.getOriginalFilename());
		try{
			multipart.transferTo(convFile);
		}catch(IllegalStateException | IOException e){
			System.out.println(e);
		}
		return convFile;
	}
	// 확장자 자르기
	public static String getFileExtension(String fileName){

		String result = "";

		if(!fileName.equals("") || fileName != null){
			int cnt = fileName.lastIndexOf(".");
			if(cnt > 0){
				result = fileName.substring(cnt + 1, fileName.length()).toLowerCase();
			}else{
				result = "";
			}
		}

		return result;
	}
	public static void main(String[] args){
		List<HashMap<String, Object>> resultList = getCSVFile("/Users/LeeYongSang/Downloads/2017.03~2018.03_small.txt", "euc-kr", "\\|", 1);
//		List<HashMap<String, Object>> resultList = getCSVFile("/Users/LeeYongSang/Downloads/2017.03~2018.03.txt", "euc-kr", "\\|", 1);
		System.out.println(resultList.size());
	}

	@SuppressWarnings("finally")
	public static List<HashMap<String, Object>> getCSVFile(String path, String encoding, String delimiter, int startReadLine){
		BufferedReader br = null;
		String line;
		List<HashMap<String, Object>> resultList = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> result = new HashMap<>();
		try{
			br = new BufferedReader(new InputStreamReader(new FileInputStream(path), encoding));
			int cnt = 0;
			String [] header = {"business_no","company_sub_reg_no","company_name","ceo_name","address1","business_format","business_type","is_member"};
			while((line = br.readLine()) != null){
				if(cnt < startReadLine){
					cnt++;
					continue;
				}
				String[] field = line.split(delimiter);
				result = new HashMap<>();
				for(int i=0;i<field.length;i++){
					if(i==7){
						result.put(header[i], StringUtil.fixNull(field[i]).equals("1")?"T":"F");
					}else{
						result.put(header[i], StringUtil.fixNull(field[i]));
					}
				}
				resultList.add(result);
				cnt++;
			}
		}catch(FileNotFoundException e){
			System.out.println(e);
		}catch(IOException e){
			System.out.println(e);
		}finally{
			if(br != null){
				try{
					br.close();
				}catch(IOException e){
					System.out.println(e);
				}
			}
			return resultList;
		}
	}
}
