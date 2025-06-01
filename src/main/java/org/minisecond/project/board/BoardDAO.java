package org.minisecond.project.board;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import jakarta.validation.Valid;

@Mapper
public interface BoardDAO {
	
	public List<BoardVO> getBoard(Map <String, Object> map);
	public int getTotalCount(Map<String, Object> map);
	public BoardVO getPost(int postNo);
	public int addViewCnt(int postNo);
	public int updatePost(@Valid PostForm post);
	public int deletePost(@Valid PostForm post);
	public String getPassword(@Valid PostForm post);
	public int registerPost(@Valid RegisterForm post);

}
