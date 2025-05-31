package org.minisecond.project.member;


import java.util.Map;


import org.minisecond.project.util.CustomException;
import org.minisecond.project.util.GetPageVO;
import org.minisecond.project.util.Util;
import org.minisecond.project.util.Validate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
	
	private final MemberService ms;
	
	@PostMapping("/login")
	public ResponseEntity<Map<String, Object>> login(@RequestBody @Valid LoginForm member) throws CustomException{

		// 입력받은 아이디, 비밀번호 빈 값 검증은 HttpExceptionHandler에서 수행(BindingResult도 명시할 필요 없다.)
		if(!Validate.isValid(Validate.IDREGEX, member.getId())) throw new IllegalArgumentException(Validate.IDREGEXERRMSG);
		
		if(!Validate.isValid(Validate.PWREGEX, member.getPassword())) throw new IllegalArgumentException(Validate.PWREGEXERRMSG);

		LoginForm inner = ms.login(member);
		
		if(inner == null || inner.getId().isBlank()) {
			throw new IllegalArgumentException(Validate.IDPWEMPTYERRMSG);
		}
		
		if(inner.getLoginFailure() == 5 && "N".equals(inner.getLockYn())) {
			
			if(ms.updateLockYn(inner) == 1) throw new CustomException(Validate.LOGINCNTERRMSG);
			else throw new IllegalStateException("로그인 중 오류가 발생했습니다. 다시 시도해주세요.");
		
		}
		
		if(inner.getLoginFailure() == 5 || "Y".equals(inner.getLockYn()))	{
			throw new CustomException(Validate.LOGINCNTERRMSG);
		}
		
		if(inner.getPassword().isBlank() || !inner.getPassword().equals(member.getPassword())) { // 아이디는 일치하나 비밀번호가 불일치할 경우 로그인 실패 횟수를 1 증가시킨다.
			if(ms.updateFailure(inner) == 1) throw new IllegalArgumentException(Validate.IDPWEMPTYERRMSG);
			else throw new CustomException(Validate.LOGINCNTERRMSG);
		}
		
		return ResponseEntity.ok(
				Map.of(
				"userId", inner.getId(),
				"boardAuth", inner.getBoardAuth(),
				"lockYn", inner.getLockYn()
				)
		);
		
	}
	
	@PostMapping("/memberList")
	public ResponseEntity<Map<String, Object>> getMemberList(@RequestBody GetPageVO page){

		Map<String, Object> map = Map.of(
				"pageResponse", 
				ms.getMemberList(
						page.getSearchValue(), 
						Util.parseInt(page.getPageNo(), Util.PAGENODEFAULTVAL), 
						Util.parseInt(page.getSize(), Util.SIZEDEFAULTVAL), 
						Util.PARSERDEFAULTVAL
				)				
		);

		return ResponseEntity.ok().body(map);
		
	}
	
	@GetMapping("/memberDetail/{id}")
	public ResponseEntity<Map<String, Object>> getMember(@PathVariable("id") String id){
		
		if(id == null || id.isBlank()) throw new IllegalArgumentException("잘못된 접근입니다.");
		
		MemberVO dbMember = ms.getInfo(id);
		
		if(dbMember == null) throw new IllegalStateException("회원 정보를 가져오지 못했습니다. 다시 시도해주세요.");
		
		return ResponseEntity.ok().body(Map.of("item", dbMember));
		
	}
	
	@PutMapping("/lockYn")
	public ResponseEntity<Void> lockYn(@RequestBody MemberVO member){
		
		if(member.getMemberNo() <= 0) throw new IllegalArgumentException("잘못된 접근입니다.");
		
		if(ms.lockYn(member.getMemberNo(), member.getLockYn()) != 1)  throw new IllegalStateException("회원 잠금 또는 해제에 실패했습니다.");
		
		return ResponseEntity.ok().build();
	}
	
	@PostMapping("/search")
	public ResponseEntity<Map<String, Object>> search(@RequestBody GetPageVO page) {
		
		Map<String, Object> map = Map.of(
				"pageResponse", 
				ms.getMemberList(
						page.getSearchValue(), 
						Util.parseInt(page.getPageNo(), Util.PAGENODEFAULTVAL), 
						Util.parseInt(page.getSize(), Util.SIZEDEFAULTVAL), 
						Util.PARSERDEFAULTVAL)
		);
	
		return ResponseEntity.ok().body(map);
	}
	
	@PutMapping("/memberUpdate")
	public ResponseEntity<Void> updateMember(@RequestBody @Valid UpdateForm member) {
		
	    if (!Validate.isValid(Validate.PWREGEX, member.getPassword())) throw new IllegalArgumentException(Validate.PWREGEXERRMSG);
	    
	    if(!Validate.isValid(Validate.PHONEREGEX, member.getPhoneNumber())) throw new IllegalArgumentException(Validate.PWREGEXERRMSG);
	    
	    if (ms.updateMember(member) != 1) throw new IllegalStateException("회원 정보 수정 중 오류가 발생했습니다. 다시 시도해주세요.");

		return ResponseEntity.ok().build();
	}
	
	@DeleteMapping("/memberDelete/{id}")
	public ResponseEntity<Void> deleteMember(@PathVariable("id") String id) {
		
		if(id == null || id.isBlank()) throw new IllegalArgumentException("잘못된 접근입니다.");
		
		if(ms.deleteMember(id) != 1) throw new IllegalStateException("회원 탈퇴 중 오류가 발생했습니다. 다시 시도해주세요.");
		
		return ResponseEntity.ok().build();
	}
	
	@PostMapping("/memberRegister")
	public ResponseEntity<Void> registerMember(@RequestBody @Valid RegisterForm member) {
		log.info(member.toString());
		log.info("a");
		if(!Validate.isValid(Validate.IDREGEX, member.getId())) throw new IllegalArgumentException(Validate.IDREGEXERRMSG);
		log.info("b");
	    if (!Validate.isValid(Validate.PWREGEX, member.getPassword())) throw new IllegalArgumentException(Validate.PWREGEXERRMSG);
	    log.info("c");
	    if(!Validate.isValid(Validate.PHONEREGEX, member.getPhoneNumber())) throw new IllegalArgumentException(Validate.PHONEREGEXERRMSG);
	    log.info("d");
	    if(!Validate.isValidBirthDate(member.getBirthDate())) throw new IllegalArgumentException(Validate.BIRTHDATEERRMSG);
	    log.info("e");
	    if(ms.register(member) != 1) throw new IllegalStateException("회원가입 중 오류가 발생했습니다. 다시 시도해주세요.");
	    log.info("f");
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/idDupChk/{id}")
	public ResponseEntity<Map<String, String>> idDupChk(@PathVariable("id") String id) {
		
		if(id == null || id.isBlank() || !Validate.isValid(Validate.IDREGEX, id)) throw new IllegalArgumentException(Validate.IDREGEXERRMSG);

		if(ms.getId(id) != null) throw new IllegalArgumentException(id + "은(는) 사용 불가합니다.");

		return ResponseEntity.ok().body(Map.of("msg", id + "은(는) 사용 가능합니다."));
	}

}
