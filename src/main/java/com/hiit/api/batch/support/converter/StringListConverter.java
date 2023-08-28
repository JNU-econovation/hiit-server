package com.hiit.api.batch.support.converter;

import java.util.ArrayList;
import java.util.List;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;

/** String List를 변환하는 유틸리티 클래스 */
@UtilityClass
public class StringListConverter {

	/**
	 * String List를 String 토큰으로 변환한다.
	 *
	 * @param list String List
	 * @return String 토큰
	 */
	public static String[] toTokens(String list) {
		return StringUtils.splitPreserveAllTokens(list, "[,]");
	}

	/**
	 * String List를 String 타입 List로 변환한다.
	 *
	 * @param list String List
	 * @return String 타입 List
	 */
	public static List<String> toStringList(String list) {
		List<String> rtn = new ArrayList<>();
		String[] tokens = toTokens(list);
		for (int i = 0; i < tokens.length; i++) {
			if (tokens[i].equals("")) {
				continue;
			}
			rtn.add(tokens[i].trim());
		}
		return rtn;
	}
}
