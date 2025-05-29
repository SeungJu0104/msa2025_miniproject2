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
	public ResponseEntity<Map<String, Object>> login(@RequestBody @Valid LoginForm member) throws CustomException{

		// 입력받은 아이디, 비밀번호 빈 값 검증은 HttpExceptionHandler에서 수행(BindingResult도 명시할 필요 없다.)
		if(!Validate.isValid(idRegex, member.getId()) || !Validate.isValid(pwRegex, member.getPassword())){
			throw new IllegalArgumentException("아이디 또는 비밀번호를 확인해주세요.");
		}

		LoginForm inner = ms.login(member);
		
		if(inner == null || inner.getId().isBlank()) {
			log.info("id");
			throw new IllegalArgumentException("아이디 또는 비밀번호를 확인해주세요.");
		}
		
		if(inner.getLoginFailure() == 5 && "N".equals(inner.getLockYn())) {
			log.info("lockYn");
			if(ms.updateLockYn(inner) == 1) throw new CustomException("로그인을 5회 이상 실패하셨습니다. 관리자에게 문의하세요.");
			else throw new IllegalStateException("로그인 중 오류가 발생했습니다. 다시 시도해주세요.");
		}
		
		if(inner.getLoginFailure() == 5 || "Y".equals(inner.getLockYn()))	{
			log.info("AlreadyLockYn");
			throw new CustomException("로그인을 5회 이상 실패하셨습니다. 관리자에게 문의하세요.");
		}
		
		if(inner.getPassword().isBlank() || !inner.getPassword().equals(member.getPassword())) { // 아이디는 일치하나 비밀번호가 불일치할 경우 로그인 실패 횟수를 1 증가시킨다.
			log.info("pw");
			if(ms.updateFailure(inner) == 1) throw new IllegalArgumentException("아이디 또는 비밀번호를 확인해주세요.");
			else throw new CustomException("로그인을 5회 이상 실패하셨습니다. 관리자에게 문의하세요.");
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
						Util.parseInt(page.getPageNo(), pageNoDefaultVal), 
						Util.parseInt(page.getSize(), sizeDefaultVal), 
						parserDefaultVal
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
						Util.parseInt(page.getPageNo(), pageNoDefaultVal), 
						Util.parseInt(page.getSize(), pageNoDefaultVal), 
						parserDefaultVal)
		);
	
		return ResponseEntity.ok().body(map);
	}
	
	@PutMapping("/memberUpdate")
	public ResponseEntity<Void> updateMember(@RequestBody @Valid UpdateForm member) {
		
	    if (!Validate.isValid(pwRegex, member.getPassword())) throw new IllegalArgumentException("비밀번호는 8~12자 사이이며, 영문자, 숫자, 특수문자를 각각 1개 이상 포함해야 합니다.");
	    
	    if (ms.updateMember(member) != 1) throw new IllegalStateException("회원 정보 수정 중 오류가 발생했습니다. 다시 시도해주세요.");

		return ResponseEntity.ok().build();
	}
	
	@DeleteMapping("/memberDelete/{id}")
	public ResponseEntity<Void> deleteMember(@PathVariable("id") String id) {
		
		if(id.isBlank()) throw new IllegalArgumentException("잘못된 접근입니다.");
		
		if(ms.deleteMember(id) != 1) throw new IllegalStateException("회원 탈퇴 중 오류가 발생했습니다. 다시 시도해주세요.");
		
		return ResponseEntity.ok().build();
	}

}
