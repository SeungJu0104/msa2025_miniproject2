package org.minisecond.project.member;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.minisecond.project.util.Util;
import org.minisecond.project.util.Validate;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
	
	private int sizeDefaultVal = 10, pageNoDefaultVal = 1, parserDefaultVal = 5;
	private String idRegex = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,10}$";
	private String pwRegex = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?])[A-Za-z\\d!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]{8,12}$";
	
	private final MemberService ms;
	
	@PostMapping("/login")
	public ResponseEntity<Map<String, String>> login(@RequestBody @Valid LoginForm member){
		
		Map<String, String> map = Util.createMapString();
		
		// 입력받은 아이디, 비밀번호 빈 값 검증은 HttpExceptionHandler에서 수행(BindingResult도 명시할 필요 없다.)
		if(!Validate.isValid(idRegex, member.getId()) || !Validate.isValid(pwRegex, member.getPassword())){
			throw new IllegalArgumentException("아이디 또는 비밀번호를 확인해주세요.");
		}
		
		LoginForm inner = ms.login(member);
		
		if(inner == null || inner.getId().isBlank() || inner.getPassword().isBlank()) {
			throw new IllegalArgumentException("잘못된 접근입니다.");
		}
		
		if(!inner.getId().equals(member.getId()) || !inner.getPassword().equals(member.getPassword())) {
			throw new IllegalArgumentException("아이디 또는 비밀번호를 확인해주세요.");
		}
		
		return ResponseEntity.ok(Util.putMsg("status", "success", map));
		
	}
	
	@PostMapping("/memberList")
	public ResponseEntity<Map<String, Object>> getMemberList(@RequestBody String searchValue, String pageNo, String size){
		
		Map<String, Object> map = Util.createMap();

		map.put("pageResponse", ms.getMemberList(searchValue, Util.parseInt(pageNo, pageNoDefaultVal), Util.parseInt(size, sizeDefaultVal), parserDefaultVal));
		map.put("status", "success");
		return ResponseEntity.ok().body(map);
		
	}

}
