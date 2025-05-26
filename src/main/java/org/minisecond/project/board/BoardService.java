package org.minisecond.project.board;

import java.util.List;

import org.minisecond.project.util.PageResponseVO;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardService {
	
	private final BoardDAO bd;

	public PageResponseVO<BoardVO> getBoard(Integer pageSize) {
		
		List<BoardVO> res = bd.getBoard();
		
		return new PageResponseVO<BoardVO>(pageSize);
	}

}
