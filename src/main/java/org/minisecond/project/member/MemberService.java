package org.minisecond.project.member;

import java.util.HashMap;
import java.util.Map;

import org.minisecond.project.util.PageResponseVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {
	
	private final MemberDAO mDao;
	
	public LoginForm login(LoginForm member) {
		return mDao.login(member);
	}
	
	@Transactional
	public int updateFailure(LoginForm dbMember) {
		return mDao.updateFailure(dbMember);
	}
	
	@Transactional
	public int updateLockYn(LoginForm dbMember) {
		return mDao.updateLockYn(dbMember);
	}
	
	@Transactional
	public int lockYn(int memberNo, String lockYn) {
		return mDao.lockYn(memberNo, lockYn.charAt(0));
	}

	public PageResponseVO<MemberVO> getMemberList(String searchValue, int pageNo, int size, int parserPage) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", (pageNo-1) * size + 1);
		map.put("end", pageNo * size);
		map.put("searchValue", searchValue);
		return new PageResponseVO<MemberVO>(pageNo, mDao.getMemberList(map), mDao.getTotalCount(map), size, parserPage);
	}

	public MemberVO getMember(String id) {
		return mDao.getMember(id);
	}
	
	@Transactional
	public int register(RegisterForm member) {
		return mDao.register(member);
	}

	public MemberVO getInfo(String id) {
		return mDao.getInfo(id);
	}
	
	@Transactional
	public int updateMember(UpdateForm member) {
		return mDao.updateMember(member);
	}
	
	@Transactional
	public int deleteMember(String id) {
		return mDao.deleteMember(id);
	}

	public String getId(String id) {
		return mDao.getId(id);
	}
	

}
