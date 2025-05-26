package org.minisecond.project.member;

import java.util.Map;

import org.minisecond.project.util.Util;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
	
	private final MemberService ms;
	
	@PostMapping("/login")
	public Map<String, Object> login(@RequestBody MemberVO member){
		Map<String, Object> map = Util.createMap();
		
		ms.login(member);
		
		map.put("status", "success");
		
		return map;
	}

}
