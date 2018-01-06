package cn.sky.mysearch.util;

import java.util.Map;
import java.util.Map.Entry;

public class StringTemplateUtil {

	public static String process(String content_template,Map<String,Object> param) {
		if(param!=null&&param.entrySet().size()>0) {
			for(Entry<String, Object> e:param.entrySet()) {
				content_template = content_template.replace("{"+e.getKey()+"}", e.getValue().toString());
			}
		}
		return content_template;
	}

}
