package com.osci.kr.itda.utills;

import com.osci.kr.itda.common.model.ItDaResult;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;


/**
 * Validation Util class
 * 값 체크 유틸 클래스
 * 
 * @author 박재형
 * @since 2020.12.19
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 *
 *
 * </pre>
 */

public class ValidationUtil {
	
	/**
	 * 파라미터 단 필수값 체크 
	 * @param requirementKey
	 * @param param
	 * @return
	 */
	public static boolean checkContainsKey(String requirementKey, HashMap param) {
		
		boolean flag = false;
		if(param == null) {
			return false;
		}
		if (param.containsKey(requirementKey) && !StringUtil.fixNull(param.get(requirementKey)).equals("")) {
			flag = true;
		}				
		if (!flag) {
			return false;
		}
		return true;
		
	}
	
	/**
	 * 파라미터 다중 필수값 체크 
	 * @param requirementKey
	 * @param param
	 * @return
	 */
	public static boolean checkContainsKeys(String[] requirementKeys, HashMap param) {
		
		if(param == null) {
			return false;
		}
		
		for (String requirementKey : requirementKeys) {
			boolean flag = false;
			
			for (Object key : param.keySet()){
				String stringKey = StringUtil.fixNull(key.toString());
				if (requirementKey.equals(stringKey) && !StringUtil.fixNull(param.get(key)).equals("")) {
					flag = true;
					break;
				}				
			}
			if (!flag) {
				return false;
			}
		}
		return true;
		
	}
	
	/**
	 * 파라미터 포함 체크 
	 */
	public static boolean checkAnyContainsKeys(String[] keys, HashMap<String, Object> param) {
		if(param == null) {
			return false;
		}
		for (String key: keys) {
			if (param.containsKey(key)) return true;
		}
		return false;
	}
	
	/**
	 * 파라미터 다중 필수값 체크 
	 * @param requirementKey
	 * @param param
	 * @return
	 */
	public static ItDaResult checkContainsKeysValue(String[] requirementKeys, HashMap param) {

		ItDaResult luluResult = new ItDaResult();
    	String emptyList = "";
		if(param == null) {
			luluResult.setResultCode(400);
			luluResult.setResultMsg("Parameter Is Not Valid. Parameter is null ]");
		}
		for (String requirementKey : requirementKeys) {
			boolean flag = false;
			
			for (Object key : param.keySet()){
				String stringKey = StringUtil.fixNull(key.toString());
				if (requirementKey.equals(stringKey) && !StringUtil.fixNull(param.get(key)).equals("")) {
					flag = true;
					break;
				}				
			}
			if (!flag) {
				if(emptyList.isEmpty()) {
					emptyList = "["+requirementKey;
				}else {
					emptyList += ", "+requirementKey;
				}
			}
		}
		if(!emptyList.isEmpty()) {
			luluResult.setResultCode(400);
			luluResult.setResultMsg("Parameter Is Not Valid. "+emptyList+"]");
		}
		return luluResult;
		
	}

	/**
	 * 중복된 3자 이상의 문자 또는 숫자 사용불가
	 */
	public static boolean checkDuplicate3Character(String d) {
		int p = d.length();
		byte[] b = d.getBytes();
		for (int i = 0; i < ((p * 2) / 3); i++) {
			int b1 = b[i + 1];
			int b2 = b[i + 2];

			if ((b[i] == b1) && (b[i] == b2)) {
				return true;
			} else {
				continue;
			}
		}
		return false;
	}
	/**
	 * 필수값이비어 있으면 빈값으로 채워서 리턴
	 * @param requirementKey
	 * @param param
	 * @return
	 */
	public static HashMap<String, Object> checkContainKeyFillValue(String requirementKey, HashMap<String, Object> param) {
		if(param == null) {
			return null;
		}
		if (!param.containsKey(requirementKey)) {
			param.put(requirementKey, "");
		}				
		return param;
	}
	
	/**
	 * 필수값이비어 있으면 빈값으로 채워서 리턴
	 * @param requirementKey
	 * @param param
	 * @return
	 */
	public static HashMap<String, Object> checkContainKeysFillValue(String[] requirementKeys, HashMap<String, Object> param) {
		if(param == null) {
			return null;
		}
		for (String requirementKey : requirementKeys) {
			if (!param.containsKey(requirementKey)) {
				param.put(requirementKey, "");
			}
		}
		return param;
	}
	/**
	 * 필수값이비어 있으면 빈값으로 채워서 리턴
	 * @param requirementKey
	 * @param param
	 * @return
	 */
	public static HashMap<String, Object> checkContainKeysCopyMap(String[] requirementKeys, HashMap<String, Object> param) {
		if(param == null) {
			return null;
		}
		HashMap<String, Object> result = new HashMap<String, Object>(); 
		for (String requirementKey : requirementKeys) {
			if (!param.containsKey(requirementKey)) {
				result.put(requirementKey, "");
			} else {
				result.put(requirementKey, param.get(requirementKey));
			}
		}
		return result;
	}
	
	/**
	 * 파일 확장자 체크
	 * @param fileName 파일 이름
	 * @param possible_ext 가능 확자
	 * @param impossible_ext 불가능 확장자
	 * @return
	 */
	public static boolean checkFileExtension(String fileName, String[] possible_ext, String[] impossible_ext) {
		boolean isSuccess = true;
		String fileExtension = FileUtil.getFileExtension(StringUtil.fixNull(fileName));
		//빈값이면 false
		if(fileExtension.isEmpty()){
			return false;
		}
		//한개라도 맞으면 true
		for (String possibleExt : possible_ext) {
			if(fileExtension.equalsIgnoreCase(possibleExt)){
				return true;
			}
		}
		//한개라도 맞으면 false
		for (String impossibleExt :impossible_ext) {
			if(fileExtension.equalsIgnoreCase(impossibleExt)){
				return false;
			}
		}
		return isSuccess;
	}

	/**
	 * 리스트 데이터 존재 여부
	 */
	public static boolean checkListData(List list) {
		boolean isCheck = false;
		if(list != null && list.size() >0){
			isCheck = true;
		}
		return isCheck;
		
	}
	/**
	 * 허용 서비스 코드 체크
	 */
	public static boolean checkPermissionServiceCode(HttpServletRequest request,String [] service_code_list) {
		String service_code = "";
		try{
			service_code = StringUtil.fixNull(request.getHeader("service-code"));
		}catch(Exception e){
			return false;
		}
		if("".equals(service_code)){
			return false;
		}
		for(String serviceCode:service_code_list){
			if(service_code.equals(serviceCode)){
				return true;
			}
		}
		return false;
	}

	public static Map convertObjectToMap(Object obj){
		Map map = new HashMap();
		Field[] fields = obj.getClass().getDeclaredFields();
		for(int i=0; i <fields.length; i++){
			fields[i].setAccessible(true);
			try{
				map.put(fields[i].getName(), fields[i].get(obj));
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return map;
	}
	
    public static void main(String[] args) throws Exception{

    	HashMap<String, Object> param = new HashMap<>();
    	param.put("user_no","1");
//    	param.put("png","1");
//    	param.put("bmp","1");
    	param.put("exe","1");
		ItDaResult checkParamResult = checkContainsKeysValue("member_id,member_no".split(","), param);
    	if(checkParamResult.getResultCode() != 200) {
    		System.out.println(checkParamResult.getResultCode());
    		System.out.println(checkParamResult.getResultMsg());
    	}
    	
    }
    
}

