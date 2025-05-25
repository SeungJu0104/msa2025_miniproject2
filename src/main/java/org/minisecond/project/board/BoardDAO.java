package org.minisecond.project.board;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BoardDAO {
	
	public List<BoardVO> getBoard();

}
