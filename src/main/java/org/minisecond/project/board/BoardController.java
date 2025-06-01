package org.minisecond.project.board;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.minisecond.project.board.RegisterForm;
import org.minisecond.project.util.GetPageVO;
import org.minisecond.project.util.PageResponseVO;
import org.minisecond.project.util.Util;
import org.minisecond.project.util.Validate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
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
	
	@GetMapping("/detail/{postNo}")
	public ResponseEntity<Map<String, Object>> getPost(@PathVariable("postNo") String postNum){
		
		try {
			int postNo = Util.parseInt(postNum, "잘못된 접근입니다.");

			BoardVO post = bs.getPost(postNo);

			if (post == null) throw new IllegalArgumentException("다시 시도해주세요.");

			return ResponseEntity.ok().body(Map.of("post", post));

		} catch (Exception e) {
			// 400 Bad Request + 메시지
			return ResponseEntity.badRequest().body(Map.of("msg", "처리 중 오류가 발생했습니다. 다시 시도해주세요."));
		}
		
	}
	
	@GetMapping("/view/{postNo}")
	public ResponseEntity<Map<String, Object>> addViewCnt(@PathVariable("postNo") String postNum){
		
		try {
			int postNo = Util.parseInt(postNum, "잘못된 접근입니다.");

			if (bs.addViewCnt(postNo) != 1) throw new IllegalStateException("처리 중 오류가 발생했습니다. 다시 시도해주세요.");
			
			return ResponseEntity.ok().build();

		} catch (Exception e) {
			
			return ResponseEntity.badRequest().body(Map.of("msg", "처리 중 오류가 발생했습니다. 다시 시도해주세요."));
		
		}
		
	}
	
	
	
	
	@PutMapping("/update")
	public ResponseEntity<Map<String, Object>> updatePost(@RequestBody @Valid PostForm post) {
		
		if(bs.updatePost(post) != 1) throw new IllegalStateException("처리 중 오류가 발생했습니다. 다시 시도해주세요.");
		
		return ResponseEntity.ok().body(Map.of("post", post));
		
	}

	
	@PostMapping("/delete")
	public ResponseEntity<Map<String, Object>> deletePost(@RequestBody @Valid PostForm post) {
		
		if(post.getPassword() == null || post.getPassword().isBlank()) throw new IllegalArgumentException("비밀번호를 입력해주세요.");
		
		if(Validate.isValid(Validate.POSTPWREGEX, post.getPassword())) throw new IllegalArgumentException(Validate.POSTPWERRMSG);
		
		if(!bs.getPassword(post).equals(post.getPassword())) throw new IllegalArgumentException("비밀번호를 다시 확인해주세요.");

		if (bs.deletePost(post) != 1) throw new IllegalStateException("처리 중 오류가 발생했습니다. 다시 시도해주세요.");
			
		return ResponseEntity.ok().body(Map.of("msg", "게시글이 삭제됐습니다."));

	}
	
	@PostMapping("/register")
	public ResponseEntity<Map<String, Object>> registerPost(@RequestBody @Valid RegisterForm post) {
		
		if(!Validate.isValid(Validate.IDREGEX, post.getId())) throw new IllegalArgumentException(Validate.IDREGEXERRMSG);
		
		if(Validate.isValid(Validate.POSTPWREGEX, post.getPassword())) throw new IllegalArgumentException(Validate.POSTPWERRMSG);
		
		if(bs.registerPost(post) != 1) throw new IllegalStateException("처리 중 오류가 발생했습니다. 다시 시도해주세요.");
		
		return ResponseEntity.ok().body(Map.of("msg", "게시글이 등록됐습니다."));
	}
	
	
	public Map<String, String> boardMapper(List<String> boardList, List<String> mapperList){
		
		Map<String, String> boardNameMap = new HashMap<String, String>();
		
		if(boardList.size() != mapperList.size()) throw new IllegalStateException("다시 시도해주세요.");
		
		for(int i = 0; i < boardList.size(); i++) boardNameMap.put(boardList.get(i), mapperList.get(i));
		
		return boardNameMap;
		
	}

}
