package com.suglob.pharmacy.service.util;

import com.suglob.pharmacy.constant.RegexConstant;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * The class contains methods validation for compliance with regular expressions.
 */
public class RegularChanges {
	public static boolean passportCheck(String passportId){
		String regexp= RegexConstant.REGEX_PASSPORT;
		Pattern pattern = Pattern.compile(regexp);
		Matcher matcher=pattern.matcher(passportId);
		if(matcher.lookingAt()){
			return true;
		}
		return false;
	}


	public static boolean loginCheck(String login) {
		String regexp= RegexConstant.REGEX_LOGIN;
		Pattern pattern = Pattern.compile(regexp);
		Matcher matcher=pattern.matcher(login);
		if(matcher.lookingAt()){
			return true;
		}
		return false;
	}

	public static boolean passwordCheck(String password) {
		String regexp= RegexConstant.REGEX_PASSWORD;
		Pattern pattern = Pattern.compile(regexp);
		Matcher matcher=pattern.matcher(password);
		if(matcher.lookingAt()){
			return true;
		}
		return false;
	}

	public static boolean emailCheck(String email) {
		String regexp= RegexConstant.REGEX_EMAIL;
		Pattern pattern = Pattern.compile(regexp);
		Matcher matcher=pattern.matcher(email);
		if(matcher.lookingAt()){
			return true;
		}
		return false;
	}

    public static boolean drugCategoryCheck(String drugCategory) {
		String regexp= RegexConstant.REGEX_DRUG_CATEGORY;
		Pattern pattern = Pattern.compile(regexp);
		Matcher matcher=pattern.matcher(drugCategory);
		if(matcher.lookingAt()){
			return true;
		}
		return false;
    }
}
