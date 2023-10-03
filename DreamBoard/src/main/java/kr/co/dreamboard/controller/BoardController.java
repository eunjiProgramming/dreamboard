package kr.co.dreamboard.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.dreamboard.entity.Board;
import kr.co.dreamboard.entity.Criteria;
import kr.co.dreamboard.entity.Member;
import kr.co.dreamboard.entity.PageMaker;
import kr.co.dreamboard.service.BoardService;



@Controller
@RequestMapping("/board/*")
public class BoardController {
	
	@Autowired
	BoardService boardService;
	
	@RequestMapping("/list")
	public String getList(Criteria cri, Model model, HttpSession session) { // type, keyword
		
		// 로그인 체크
		Member mvo = (Member) session.getAttribute("mvo");
		if(mvo == null) {
			session.setAttribute("dest", "/board/list");
			return "redirect:/member/login";
		}
		
		List<Board> list=boardService.getList(cri);
		// 객체바인딩
		model.addAttribute("list", list); // Model
		
		// 페이징 처리에 필요한 부분
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(cri);
		pageMaker.setTotalCount(boardService.totalCount(cri));
		
		model.addAttribute("pageMaker", pageMaker);
		
		return "board/list"; // View
 	}

}
