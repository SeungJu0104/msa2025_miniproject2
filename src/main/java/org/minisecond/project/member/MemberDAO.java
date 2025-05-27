package org.minisecond.project.member;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface MemberDAO {
	
	public LoginForm login(LoginForm member);
	public int updateFailure(MemberVO dbMember);
	public int updateLockYn(MemberVO dbMember);
	public List<MemberVO> getMemberList(Map<String, Object> map);
	public int getTotalCount(Map<String, Object> map);
	public int lockYn(@Param("memberNo") String memberNo, @Param("lockYn") char lockYn);
	public MemberVO getMember(MemberVO member);
	public int register(MemberVO member);
	public MemberVO getInfo(@Param("id") String id);
	public int updateMember(MemberVO member);
	public int deleteMember(MemberVO member);

}
