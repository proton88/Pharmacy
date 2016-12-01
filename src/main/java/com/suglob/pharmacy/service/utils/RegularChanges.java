package com.suglob.pharmacy.service.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegularChanges {
	public static boolean passportChange(String passportId){
		String regexp="[A-Z]{2}[0-9]{7}";
		Pattern pattern = Pattern.compile(regexp);
		Matcher matcher=pattern.matcher(passportId);
		if(matcher.lookingAt()){
			return true;
		}
		return false;
	}
}
