package org.minisecond.project.board;

import java.util.Map;

import org.minisecond.project.util.PageResponseVO;
import org.minisecond.project.util.Util;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
public class RestBoardController {
	
	private final BoardService bs;
	
	
	@PostMapping("/")
	public Map<String, Object> init(@PathVariable("pageSize") Integer pageSize){
		
		Map<String, Object> map = Util.createMap();
		
		// 데이터베이스에서 자료 가져오기
		PageResponseVO<BoardVO> res = bs.init();
		
		
		
		
		// map에 담기
		
		
		
		
		
		// 화면에 리턴
		return map;
	}

}
