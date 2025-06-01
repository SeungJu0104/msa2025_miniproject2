package org.minisecond.project.board;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.minisecond.project.util.PageResponseVO;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardService {
	
	private final BoardDAO bd;

	public PageResponseVO<BoardVO> getBoard(String board, String searchValue, int pageNo, int size, int parserPage) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("board", board);
		map.put("start", (pageNo-1) * size + 1);
		map.put("end", pageNo * size);
		map.put("searchValue", searchValue);
		return new PageResponseVO<BoardVO>(board, pageNo, bd.getBoard(map), bd.getTotalCount(map), size, parserPage);
	}

	public BoardVO getPost(BoardVO post) {
		return bd.getPost(post);
	}
	
	@Transactional
	public int addViewCnt(int postNo) {
		return bd.addViewCnt(postNo);
	}
	
	@Transactional
	public int updatePost(BoardVO post) {
		return bd.updatePost(post);
	}
	
	@Transactional
	public int deletePost(BoardVO post) {
		return bd.deletePost(post);
	}

	public String getPassword(BoardVO post) {
		return bd.getPassword(post);
	}
	
	@Transactional
	public int registerPost(BoardVO post) {
		return bd.registerPost(post);
	}

}
