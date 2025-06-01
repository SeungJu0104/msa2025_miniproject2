package org.minisecond.project.board;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.minisecond.project.util.GetPageVO;
import org.minisecond.project.util.PageResponseVO;
import org.minisecond.project.util.Util;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {
	
	private final BoardService bs;
//	private Set <String> boardList = new HashSet<String>(Arrays.asList(boards));
//	private String parserPage = "5"; // 페이징 개수 크기는 일단 컨트롤러에서 하드 코딩으로 전달.
//	private int sizeDefaultVal = 10, pageNoDefaultVal = 1, parserDefaultVal = 5;	
	
	private String [] boards = {"kleague", "bundesliga", "laliga", "free", "epl"}; // 게시판 종류
	private String [] mappers = {"K리그", "분데스리가", "라리가", "자유게시판", "EPL"}; // 종류 mapper
	private List<String> boardList = new ArrayList<String>(Arrays.asList(boards));
	private List <String> mapperList = new ArrayList<String>(Arrays.asList(mappers));
	private Map<String, String> boardMapper = boardMapper(boardList, mapperList);
	
	@PostMapping("/{board}/search")
	public ResponseEntity<Map<String, Object>> getBoardList(@PathVariable("board") String board, @RequestParam("searchValue") String searchValue, @RequestBody GetPageVO page){

		if(!boardMapper.containsKey(board)) throw new IllegalArgumentException("잘못된 접근입니다.");
		
		PageResponseVO<BoardVO> boardList = bs.getBoard(boardMapper.get(board), searchValue, Util.parseInt(page.getPageNo(), Util.PAGENODEFAULTVAL), Util.parseInt(page.getSize(), Util.SIZEDEFAULTVAL), Util.PARSERDEFAULTVAL);
		
		return ResponseEntity.ok().body(Map.of("pageResponse", boardList));
		
	}
	
	public Map<String, String> boardMapper(List<String> boardList, List<String> mapperList){
		
		Map<String, String> boardNameMap = new HashMap<String, String>();
		
		if(boardList.size() != mapperList.size()) return null;
		
		for(int i = 0; i < boardList.size(); i++) boardNameMap.put(boardList.get(i), mapperList.get(i));
		
		return boardNameMap;
	}

}
