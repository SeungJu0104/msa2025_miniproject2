package org.minisecond.project.member;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface MemberDAO {
	
	public LoginForm login(LoginForm member);
	public int updateFailure(LoginForm dbMember);
	public int updateLockYn(LoginForm dbMember);
	public List<MemberVO> getMemberList(Map<String, Object> map);
	public int getTotalCount(Map<String, Object> map);
	public int lockYn(@Param("memberNo") int memberNo, @Param("lockYn") char lockYn);
	public MemberVO getMember(String id);
	public int register(MemberVO member);
	public MemberVO getInfo(@Param("id") String id);
	public int updateMember(UpdateForm member);
	public int deleteMember(String id);

}
