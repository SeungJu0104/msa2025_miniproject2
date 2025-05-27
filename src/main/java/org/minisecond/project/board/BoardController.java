package org.minisecond.project.board;

import java.util.Map;

import org.minisecond.project.util.PageResponseVO;
import org.minisecond.project.util.Util;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {
	
	private final BoardService bs;
//	private Set <String> boardList = new HashSet<String>(Arrays.asList(boards));
	private String parserPage = "5"; // 페이징 개수 크기는 일단 컨트롤러에서 하드 코딩으로 전달.
	private int sizeDefaultVal = 10, pageNoDefaultVal = 1, parserDefaultVal = 5;	
	
	
	@GetMapping("/getBoard/{league}")
	public Map<String, Object> getBoard(@PathVariable("league") String league){
		
		Map<String, Object> map = Util.createMap();
		
//		if(league )
		
		// 데이터베이스에서 자료 가져오기
//		PageResponseVO<BoardVO> res = bs.getBoard(pageSize);
		
		
		
		
		// map에 담기
		map.put("status", "ok");
//		map.put("pageResponse", res);
		
		
		
		
		// 화면에 리턴
		return map;
	}

}
