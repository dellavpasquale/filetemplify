package it.pdv.tools.filetemplify.util;

import java.util.regex.Pattern;

public class RegexUtil {

	public static String replace(String s, String regex, String replace) {
		String result = null;
		if (s != null) {
			result = new String(s);
			if (regex != null && !regex.trim().isEmpty() && replace != null && !replace.trim().isEmpty()) {
				result = Pattern.compile(regex).matcher(result).replaceFirst(replace);
			}
		}
		return result;
	}

	public static boolean match(String s, String regex) {
		boolean result = false;
		if (s != null && regex != null && !regex.trim().isEmpty()) {
			result = Pattern.compile(regex).matcher(s).find();
		}
		return result;
	}

	public static String wilcardToRegex(String s) {
		String result = null;
		if (s != null) {
			result = new String(s);
			String[] chars = new String[] { "\\", ".", "+", "^", "$", "(", ")", "[", "]", "{", "}", "|" };
			for (String string : chars) {
				result = result.replace(string, "\\" + string);
			}
			result = result.replace("*", ".*");
			result = result.replace("?", ".?");
		}
		return "^" + result + "$";
	}

}
