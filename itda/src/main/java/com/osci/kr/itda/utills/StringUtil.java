package com.osci.kr.itda.utills;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * String 관련 util class
 * @author 박재형
 * @since 2020.12.04
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------

 *
 * </pre>
 */
public final class StringUtil {
	
	/**
	 * blank trime
	 * @param str
	 * @return
	 */
	public static String trim (String str) {
		int idx = 0;
		char[] val = str.toCharArray();
		int count = val.length;
		int len = count;

		while ((idx < len) && (val[idx] <= ' '))   idx++;
		while ((idx < len) && (val[len - 1] <= ' '))  len--;
		//while ((idx < len) && ((val[idx] <= ' ') || (val[idx] == '　') ) )   idx++;
		//while ((idx < len) && ((val[len - 1] <= ' ') || (val[len-1] == '　')))  len--;
		
		return ((idx > 0) || (len < count)) ? str.substring(idx, len) : str;
	}
	
	/**
	 * null -> empty string
	 * @param str
	 * @return
	 */
	public static String fixNull (String str) {
		if(str == null)
			return "";
		else if(str.toLowerCase().equals("null"))
			return "";
		else if(str.toLowerCase().equals("undefined"))
			return "";
		else if(str.trim().length() == 0)
			return "";
		else
			return str;
	}

	/**
	 * null -> empty string
	 * @param str
	 * @return
	 */
	public static String fixNull (Object obj) {
		if(obj == null)
			return "";
		else if(obj.toString().toLowerCase().equals("null"))
			return "";
		else if(obj.toString().toLowerCase().equals("undefined"))
			return "";
		else if(obj.toString().trim().length() == 0)
			return "";
		else
			return obj.toString();
	}
	
	/**
	 * null -> empty string
	 * @param str
	 * @return
	 */
	public static String fixNull (Object obj, String str) {
		
		if(obj == null)
			return str;
		else if(obj.toString().toLowerCase().equals("null"))
			return str;
		else if(obj.toString().toLowerCase().equals("undefined"))
			return str;
		else if(obj.toString().trim().length() == 0)
			return str;
		else
			return obj.toString();
	}
	
	/**
	 * 문자열 좌측에 지정한 길이 내에서 문자 채우기
	 * @param src
	 * @param pad
	 * @param len
	 * @return
	 */
    public static String lPad (String src, String pad, int len) {
        return pad(src, pad, len, -1); 
    }
	
    /**
     * 문자열 우측에 지정한 길이 내에서 문자 채우기
     * @param src
     * @param pad
     * @param len
     * @return
     */
    public static String rPad (String src, String pad, int len) {
        return pad(src, pad, len, 1);
    }
    
    /**
     * 문자열 채우기
     * @param src
     * @param pad
     * @param totLen
     * @param mode
     * @return
     */
    private static String pad (String src, String pad, int totLen, int mode) {
        String paddedString = "";
        
        if (src == null) return "";
        int srcLen = src.length();
        
        if ((totLen<1)||(srcLen>=totLen)) return src;
        
        for (int i=0; i< (totLen-srcLen); i++) {
            paddedString += pad;
        }
        
        if (mode == -1)
            paddedString += src;    // front padding
        else
            paddedString = src + paddedString; //back padding

        return paddedString;        
    }
	
    /**
     * 문자열 좌측에 지정한 길이만큼 문자 채우기
     * @param src
     * @param pad
     * @param rep
     * @return
     */
    public static String lAppend(String src, String pad, int rep) {
    	return append(src, pad, rep, -1);
    }
    
    /**
     * 문자열 우측에 지정한 길이만큼 문자 채우기
     * @param src
     * @param pad
     * @param rep
     * @return
     */
    public static String rAppend(String src, String pad, int rep) {
    	return append(src, pad, rep, 1);
    }
    
    /**
     * 문자 채우기
     * @param src
     * @param pad
     * @param rep
     * @param mode
     * @return
     */
    private static String append(String src, String pad, int rep, int mode) {
    	StringBuffer sb = new StringBuffer();
    	sb.append(src);
    	
    	if (src == null) return "";
    	
    	if (-1 == mode) {
	    	for (int i=0; i<rep; i++) {
	    		sb.insert(0, pad);
	    	}
    	} else {
	    	for (int i=0; i<rep; i++) {
	    		sb.append(pad);
	    	}
    	}
    	
    	return sb.toString();
    }
    
    /**
     * 지정한 구분자를 기준으로 배열로 변환
     * @param source
     * @param delimiter
     * @return
     */
	public static String[] split (String source, String delimiter) {
		/*
        StringTokenizer token = new StringTokenizer(source, delimiter);
        
        String[] result = new String[token.countTokens()];
        for (int i=0; i<result.length; i++) {
            result[i] = token.nextToken();
        }
        
        return result;
        */
		/*
		String[] result = source.split(delimiter);
		return result;
		*/
		ArrayList result = new ArrayList();
		int begIdx = 0;
		while(true) {
			int endIdx = source.indexOf(delimiter, begIdx);
			if (0 > endIdx) break;
			
			result.add((String)source.substring(begIdx, endIdx));
			begIdx = endIdx + delimiter.length();
		}
		result.add(source.substring(begIdx));
		
		String[] resultS = new String[result.size()];
		for (int i=0; i<result.size(); i++) {
			resultS[i] = (String)result.get(i);
		}
		return resultS;
	}
	
	/**
	 * 지정한 분자열로 문자열 병합
	 * @param arrWords
	 * @param delimiter
	 * @return
	 */
	public static String compose (String[] arrWords, String delimiter) {
        String composedString    = "";

        for (int i=0; i<arrWords.length; i++) {
            if (!arrWords[i].equals("")) {
                if (!composedString.equals("")) composedString    += delimiter;

                composedString    += arrWords[i];
            }
        }

        return composedString;
	}
	
	/**
	 * 회사코드 Validation Check :영문+숫자+8자리 이상
	 *  : (숫자와 영문자가 하나 이상씩 포함되어 있는가 ?) && (숫자영문 8~20 글자로 이루어져 있는가?)
	 *    (^(?=.*[0-9])(?=.*[a-zA-Z])) (^[A-Za-z0-9]{8,20}$)
	 * @param val
	 * @return
	 */
	public static boolean companyCodeCheck(String val) {		
		//Pattern pattern = Pattern.compile("^(?=.*[0-9])(?=.*[a-zA-Z])(?=[\\S]+$).{8,20}$");
		Pattern pattern = Pattern.compile( "(^(?=.*[0-9])(?=.*[a-zA-Z]))(^[A-Za-z0-9]{8,20}$)" );
		Matcher matcher = pattern.matcher(val);

		return matcher.find();
	}
	
	/**
	 * XSS 방지를 위해 변환한 문자열을 다시 원래대로 복귀
	 * @param value
	 * @return
	 */
	public static String revertXSS (String value) {
		value = value.replaceAll("&lt;", "<").replaceAll("&gt;", ">");
		return value;
	}
	
	/**
	 * 문자열이, 숫자(10진수 실수/정수)인지 아닌지 판단
	 * @param s
	 * @return boolean
	 */
	public static boolean isStringDouble(String s) {
	    try {
		    Double.parseDouble(s);
		    return true;
	    } catch (NumberFormatException e) {
		    return false;
	    }
  	}
	/**
	 * 문자열이, 숫자(10진수 실수/정수)인지 아닌지 판단
	 * @param s
	 * @return boolean
	 */
	public static boolean isStringLong(String s) {
		try {
			Long.parseLong(s);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	
	public static String StringCommaArray(List<HashMap<String, Object>> participantsUser, String string) {
		ArrayList<String> resultList = new ArrayList<String>();
		String rtnStr = "";
		if(participantsUser== null){
			return rtnStr;
		}
		if(participantsUser.size() > 0){
			for (int i = 0; i < participantsUser.size(); i++) {
			    if (!resultList.contains(StringUtil.fixNull(participantsUser.get(i).get(string)))) {
			        resultList.add(StringUtil.fixNull(participantsUser.get(i).get(string)));
			    }
			}

			for(int i= 0; i< resultList.size(); i++){
				if(i>0){
					rtnStr += ",";
				}
				rtnStr += StringUtil.fixNull(resultList.get(i));
			}
		}
		return rtnStr;
	}
	
	public static String substrByByteLength (String str, int byteLength) {
		int length = str.length();
		int retLength = 0;
		int tempSize = 0;
		int asc;

		for (int i = 1; i <= length; i++) {
			asc = (int) str.charAt(i - 1);
			if (asc > 127) {
//				System.out.println("!");
				if (byteLength > tempSize + 1) {
					tempSize += 2;
					retLength++;
				}
			} else {
				if (byteLength > tempSize) {
					tempSize++;
					retLength++;
				}
			}
		}
		str = str.substring(0, retLength);
		return str;
	}
	public static String getStringByByteSizeCut(String s, int n) {
	    byte[] utf8 = s.getBytes();
	    if (utf8.length < n) n = utf8.length;
	    int n16 = 0;
	    int advance = 1;
	    int i = 0;
	    while (i < n) {
	      advance = 1;
	      if ((utf8[i] & 0x80) == 0) i += 1;
	      else if ((utf8[i] & 0xE0) == 0xC0) i += 2;
	      else if ((utf8[i] & 0xF0) == 0xE0) i += 3;
	      else { i += 4; advance = 2; }
	      if (i <= n) n16 += advance;
	    }
	    return s.substring(0,n16);
	  }
	public static String [] getStringArray(String str) {
		String tempStr = StringUtil.fixNull(str).replaceAll(",,", ",").replace(" ","");
		if(str.isEmpty()){
			return null;
		}
		if(tempStr.lastIndexOf(",") == tempStr.length()-1){
			tempStr = tempStr.substring(0, tempStr.length()-1);
		}
		if(tempStr.indexOf(",") == 0){
			tempStr = tempStr.substring(1, tempStr.length());
		}
		String [] tempArr = tempStr.split(",");
		
		if(tempArr.length == 1 && tempArr[0].isEmpty()){
			return null;
		}
		return tempArr;
	}
	public static String [] getStringNumberArray(String str) {
		String tempStr = StringUtil.fixNull(str).replaceAll(",,", ",").replace(" ","");
		if(str.isEmpty()){
			return null;
		}
		Pattern SpecialPattern = Pattern.compile("(^[0-9,]+$)");
		Matcher SpecialMatcher = SpecialPattern.matcher(tempStr);
		if (!SpecialMatcher.find()) {
			return null;
		}
		
		if(tempStr.lastIndexOf(",") == tempStr.length()-1){
			tempStr = tempStr.substring(0, tempStr.length()-1);
		}
		if(tempStr.indexOf(",") == 0){
			tempStr = tempStr.substring(1, tempStr.length());
		}
		String [] tempArr = tempStr.split(",");
		
		if(tempArr.length == 1 && tempArr[0].isEmpty()){
			return null;
		}
		return tempArr;
	}
	/**
	 * XSS 방지를 위해 변환한 문자열을 다시 원래대로 복귀
	 * @param value
	 * @return
	 */
	public static String replaceAll(String value, String str, String replaceStr) {
		value = value.replaceAll(Matcher.quoteReplacement(str), Matcher.quoteReplacement(replaceStr));
		return value;
	}
	public static void main(String[] args) throws ParseException {
		System.out.println(	StringUtil.replaceAll("#aaaa#","#aaaa#", "!@#$%%^^&^*^&*가다라나라 붜ㅔㄹ"));
	}
}
