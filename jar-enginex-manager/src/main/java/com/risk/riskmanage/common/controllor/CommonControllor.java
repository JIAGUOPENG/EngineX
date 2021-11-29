package com.risk.riskmanage.common.controllor;

import com.risk.riskmanage.util.SectionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/common/validate")
public class CommonControllor {

	@RequestMapping("/section")
	@ResponseBody
	public Map<String, String> saveEngineNode(@RequestParam("sections[]") List<String> sections) {	
		Map<String, String> resultMap = new HashMap<String, String>();
		// 验证区间完整性
		if (SectionUtils.checkSectionValid(sections)) {
			if (SectionUtils.checkSectionCoincide(sections)) {
				resultMap.put("result", "-1");
				resultMap.put("msg", "区间有重叠,请核准!");
			} else {
				resultMap.put("result", "1");
				resultMap.put("msg", "区间有效!");
			}
		} else {
			resultMap.put("result", "-1");
			resultMap.put("msg", "区间不完整,请核准!");
		}
		return resultMap;
	}
}
