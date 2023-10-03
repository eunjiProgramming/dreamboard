package kr.co.dreamboard.controller;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import kr.co.dreamboard.entity.Member;
import kr.co.dreamboard.service.MemberService;

@Controller
@RequestMapping("/member/*")
public class MemberController {

	@Autowired
	MemberService memberService;

	// 로그인 화면으로 이동
	@GetMapping("/login")
	public String login() {
		return "member/login";
	}

	// 로그인 기능 구현
	@PostMapping("/login")
	public String memLogin(Member m, RedirectAttributes rttr, HttpSession session) {
		
		if (m.getMemID() == null || m.getMemID().trim().equals("") || 
			m.getMemPwd() == null || m.getMemPwd().trim().equals("")) {
				
			rttr.addFlashAttribute("msgType", "실패 메세지");
			rttr.addFlashAttribute("msg", "모든 내용을 입력해주세요.");
			
			rttr.addAttribute("memID", m.getMemID());
			
			return "redirect:/member/login";
		}
		
		Member mvo = memberService.login(m);
		
		if (mvo != null) { // 로그인에 성공
			rttr.addFlashAttribute("msgType", "성공 메세지");
			rttr.addFlashAttribute("msg", "로그인에 성공했습니다.");
			session.setAttribute("mvo", mvo); // ${!empty mvo}
			
			// 원래의 목적지로 리다이렉트
			String dest = (String) session.getAttribute("dest");
			if(dest != null) {
				session.removeAttribute("dest");
				return "redirect:" + dest;
			}
			
			return "redirect:/"; // 메인
			
		} else { // 로그인에 실패
			rttr.addFlashAttribute("msgType", "실패 메세지");
			rttr.addFlashAttribute("msg", "다시 로그인 해주세요.");
			rttr.addAttribute("memID", m.getMemID());
			return "redirect:/member/login";
		}
	}
	
	// 로그아웃 처리
	@RequestMapping("/logout")
	public String memLogout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}
	
	// 아이디 찾기 화면으로 이동
	@GetMapping("/find/id")
	public String findId() {
		return "member/findid";
	}
	
	// 아이디 찾기 기능 구현
	@PostMapping("/find/id")
	public String findId(Member m, RedirectAttributes rttr) {
		
		if (m.getMemName() == null || m.getMemName().trim().equals("") || m.getMemEmail() == null
				|| m.getMemEmail().trim().equals("")) {
			rttr.addFlashAttribute("msgType", "실패 메세지");
			rttr.addFlashAttribute("msg", "모든 내용을 입력해주세요.");
			
			rttr.addAttribute("memName", m.getMemName());
			rttr.addAttribute("memEmail", m.getMemEmail());
			
			return "redirect:/member/find/id";
		}
		
		
		Member mvo = memberService.findID(m);
		
		if (mvo != null) { // 아이디 찾기 성공
			rttr.addFlashAttribute("msgType", "성공 메세지");
			rttr.addFlashAttribute("msg", "로그인 창입니다. 귀하의 아이디 " + mvo.getMemID() + " 가 입력되어 있습니다.");
			rttr.addAttribute("memID", mvo.getMemID());
			return "redirect:/member/login";
			
		} else { // 아이디 찾기 실패
			rttr.addFlashAttribute("msgType", "실패 메세지");
			rttr.addFlashAttribute("msg", "일치하는 아이디가 없습니다.");
			rttr.addAttribute("memName", m.getMemName());
			rttr.addAttribute("memEmail", m.getMemEmail());
			return "redirect:/member/find/id";
		}
	}
	
	// 회원가입 화면으로 이동
	@GetMapping("/join")
	public String memJoin() {
		return "member/join"; 
	}
	
	// 회원가입 처리
	@PostMapping("/join")
	public String memRegister(Member m, String memPwd1, String memPwd2,
				                  RedirectAttributes rttr, HttpSession session) {
		
		if(m.getMemID()==null || m.getMemID().trim().equals("") ||
			memPwd1==null || memPwd1.trim().equals("") ||
			memPwd2==null || memPwd2.trim().equals("") ||
		   m.getMemName()==null || m.getMemName().trim().equals("") ||	
		   m.getMemAge()==0 ||
		   m.getMemGender()==null || m.getMemGender().equals("") ||
		   m.getMemEmail()==null || m.getMemEmail().equals("") || 
			m.getMemAddr()==null || m.getMemAddr().trim().equals(""))
		{
		   // 누락메세지를 가지고 가기? =>객체바인딩(Model, HttpServletRequest, HttpSession)
		   rttr.addFlashAttribute("msgType", "실패 메세지");
		   rttr.addFlashAttribute("msg", "모든 내용을 입력하세요.");
		   return "redirect:/member/join";  // ${msgType} , ${msg}
		}
		
		
		m.setMemProfile(""); // 사진이미는 없다는 의미 ""
		
		 // 아이디 유효성 검사
	    if (m.getMemID() == null || m.getMemID().trim().length() < 3 || m.getMemID().trim().length() > 20) {
	        rttr.addFlashAttribute("msgType", "실패 메세지");
	        rttr.addFlashAttribute("msg", "아이디는 3자 이상 20자 이하로 입력해주세요.");
	        return "redirect:/member/join";
	    }

	    // 비밀번호 유효성 검사
	    if (!memPwd1.equals(memPwd2)) {
	        rttr.addFlashAttribute("msgType", "실패 메세지");
	        rttr.addFlashAttribute("msg", "비밀번호가 서로 다릅니다.");
	        return "redirect:/member/join";
	    }

	    if (memPwd1 == null || memPwd1.trim().length() < 7 || memPwd1.trim().length() > 20) {
	        rttr.addFlashAttribute("msgType", "실패 메세지");
	        rttr.addFlashAttribute("msg", "비밀번호는 7자 이상 20자 이하로 입력해주세요.");
	        return "redirect:/member/join";
	    }
	    
	    // 이름 유효성 검사 (한글만)
	    Pattern pattern = Pattern.compile("^[가-힣]+$");  // 한글만 허용하는 정규 표현식
	    Matcher matcher = pattern.matcher(m.getMemName());

	    if (!matcher.find() || m.getMemName().trim().length() < 2 || m.getMemName().trim().length() > 20) {
	        rttr.addFlashAttribute("msgType", "실패 메세지");
	        rttr.addFlashAttribute("msg", "이름은 2자 이상 20자 이하의 한글로만 입력해주세요.");
	        return "redirect:/member/join";
	    }

	    // 나이 유효성 검사
	    if (m.getMemAge() <= 0 || m.getMemAge() > 120) {
	        rttr.addFlashAttribute("msgType", "실패 메세지");
	        rttr.addFlashAttribute("msg", "나이는 0 ~ 120까지만 입력 가능합니다.");
	        return "redirect:/member/join";
	    }

	    // 이메일 유효성 검사
	    if (m.getMemEmail() == null || m.getMemEmail().trim().length() < 10 || m.getMemEmail().trim().length() > 50) {
	        rttr.addFlashAttribute("msgType", "실패 메세지");
	        rttr.addFlashAttribute("msg", "이메일은 10자 이상 50자 이하로 입력해주세요.");
	        return "redirect:/member/join";
	    }

	    // 주소 유효성 검사
	    if (m.getMemAddr() == null || m.getMemAddr().trim().length() < 5 || m.getMemAddr().trim().length() > 50) {
	        rttr.addFlashAttribute("msgType", "실패 메세지");
	        rttr.addFlashAttribute("msg", "주소는 5자 이상 50자 이하로 입력해주세요.");
	        return "redirect:/member/join";
	    }
		
		// 회원을 테이블에 저장하기
		int result=memberService.join(m);
		
		if(result==1) { // 회원가입 성공 메세지
		   rttr.addFlashAttribute("msgType", "성공 메세지");
		   rttr.addFlashAttribute("msg", "회원가입에 성공했습니다.");
		   // 회원가입이 성공하면=>로그인처리하기
		   session.setAttribute("mvo", m); // ${!empty mvo}
		   return "redirect:/";
		   
		}else {
		   rttr.addFlashAttribute("msgType", "실패 메세지");
		   rttr.addFlashAttribute("msg", "이미 존재하는 회원입니다.");
		   return "redirect:/memJoin.do";
		}	
		
		
	}

	// 아이디 중복 체크
	@RequestMapping("/member/checkDuplicate")
	public @ResponseBody int checkDuplicate(@RequestParam("memID") String memID) {
		Member m=memberService.checkDuplicate(memID);
		if(m!=null || memID.trim().equals("")) {
			return 0; //이미 존재하는 회원, 입력불가 // 입력 안됨
		}
		return 1; //사용가능한 아이디
	}

	// 회원정보수정화면
	@GetMapping("/modify")
	public String memUpdateForm(HttpSession session) {
		// 로그인 체크
		Member mvo = (Member) session.getAttribute("mvo");
		if(mvo == null) {
			session.setAttribute("dest", "/member/modify");
			return "redirect:/member/login";
		}
		return "member/modify";
	}
		
	
	// 회원정보수정
	@PostMapping("/modify")
	public String memUpdate(Member m, String memPwd1, String memPwd2,
            RedirectAttributes rttr, HttpSession session)  {
		
		
		if(m.getMemID()==null || m.getMemID().trim().equals("") ||
				memPwd1==null || memPwd1.trim().equals("") ||
				memPwd2==null || memPwd2.trim().equals("") ||
			   m.getMemName()==null || m.getMemName().trim().equals("") ||	
			   m.getMemAge()==0 ||
			   m.getMemGender()==null || m.getMemGender().equals("") ||
			   m.getMemEmail()==null || m.getMemEmail().equals("") || 
				m.getMemAddr()==null || m.getMemAddr().trim().equals(""))
			{
			   // 누락메세지를 가지고 가기? =>객체바인딩(Model, HttpServletRequest, HttpSession)
			   rttr.addFlashAttribute("msgType", "실패 메세지");
			   rttr.addFlashAttribute("msg", "모든 내용을 입력하세요.");
			   return "redirect:/member/modify";  // ${msgType} , ${msg}
			}
		
		
		  // 비밀번호 유효성 검사
	    if (!memPwd1.equals(memPwd2)) {
	        rttr.addFlashAttribute("msgType", "실패 메세지");
	        rttr.addFlashAttribute("msg", "비밀번호가 서로 다릅니다.");
	        return "redirect:/member/modify";
	    }

	    if (memPwd1 == null || memPwd1.trim().length() < 7 || memPwd1.trim().length() > 20) {
	        rttr.addFlashAttribute("msgType", "실패 메세지");
	        rttr.addFlashAttribute("msg", "비밀번호는 7자 이상 20자 이하로 입력해주세요.");
	        return "redirect:/member/modify";
	    }
	    
	    // 이름 유효성 검사 (한글만)
	    Pattern pattern = Pattern.compile("^[가-힣]+$");  // 한글만 허용하는 정규 표현식
	    Matcher matcher = pattern.matcher(m.getMemName());

	    if (!matcher.find() || m.getMemName().trim().length() < 2 || m.getMemName().trim().length() > 20) {
	        rttr.addFlashAttribute("msgType", "실패 메세지");
	        rttr.addFlashAttribute("msg", "이름은 2자 이상 20자 이하의 한글로만 입력해주세요.");
	        return "redirect:/member/modify";
	    }

	    // 나이 유효성 검사
	    if (m.getMemAge() <= 0 || m.getMemAge() > 120) {
	        rttr.addFlashAttribute("msgType", "실패 메세지");
	        rttr.addFlashAttribute("msg", "나이는 0 ~ 120까지만 입력 가능합니다.");
	        return "redirect:/member/modify";
	    }

	    // 이메일 유효성 검사
	    if (m.getMemEmail() == null || m.getMemEmail().trim().length() < 10 || m.getMemEmail().trim().length() > 50) {
	        rttr.addFlashAttribute("msgType", "실패 메세지");
	        rttr.addFlashAttribute("msg", "이메일은 10자 이상 50자 이하로 입력해주세요.");
	        return "redirect:/member/modify";
	    }

	    // 주소 유효성 검사
	    if (m.getMemAddr() == null || m.getMemAddr().trim().length() < 5 || m.getMemAddr().trim().length() > 50) {
	        rttr.addFlashAttribute("msgType", "실패 메세지");
	        rttr.addFlashAttribute("msg", "주소는 5자 이상 50자 이하로 입력해주세요.");
	        return "redirect:/member/modify";
	    }
	    
		// 회원을 수정저장하기
		int result=memberService.modify(m);
		if(result==1) { // 수정성공 메세지
		   rttr.addFlashAttribute("msgType", "성공 메세지");
		   rttr.addFlashAttribute("msg", "회원정보 수정에 성공했습니다.");
		   // 회원수정이 성공하면=>로그인처리하기
		   session.setAttribute("mvo", m); // ${!empty mvo}
		   return "redirect:/";
		}else {
		   rttr.addFlashAttribute("msgType", "실패 메세지");
		   rttr.addFlashAttribute("msg", "회원정보 수정에 실패했습니다.");
		   return "redirect:/member/modify";
		}
	}
	
	// 회원의 사진등록 화면
	@GetMapping("/image")
	public String memImageForm(HttpSession session) {
		// 로그인 체크
		Member mvo = (Member) session.getAttribute("mvo");
		if(mvo == null) {
			session.setAttribute("dest", "/member/image");
			return "redirect:/member/login";
		}
		return "member/memImageForm";  // memImageForm.jsp
	}
	
	// 회원사진 이미지 업로드(upload, DB저장)
	@PostMapping("/image")
	public String memImageUpdate(HttpServletRequest request,HttpSession session, RedirectAttributes rttr) throws IOException {
		// 파일업로드 API(cos.jar, 3가지)
		MultipartRequest multi=null;
		int fileMaxSize=40*1024*1024; // 40MB		
		String savePath=request.getRealPath("resources/upload"); // 1.png
		
		try {                                                                        // 1_1.png
			// 이미지 업로드
			multi=new MultipartRequest(request, savePath, fileMaxSize, "UTF-8",new DefaultFileRenamePolicy());
		
		} catch (Exception e) {
			e.printStackTrace();
			rttr.addFlashAttribute("msgType", "실패 메세지");
			rttr.addFlashAttribute("msg", "파일의 크기는 40MB를 넘을 수 없습니다.");			
			return "redirect:/member/image";
		}
		
		// 데이터베이스 테이블에 회원이미지를 업데이트
		String memID=multi.getParameter("memID");
		String newProfile="";
		File file=multi.getFile("memProfile");
		
		if(file !=null) { // 업로드가 된상태(.png, .jpg, .gif)
			// 이미지파일 여부를 체크->이미지 파일이 아니면 삭제(1.png)
			String ext=file.getName().substring(file.getName().lastIndexOf(".")+1);
			ext=ext.toUpperCase(); // PNG, GIF, JPG
			if(ext.equals("PNG") || ext.equals("GIF") || ext.equals("JPG")){
				// 새로 업로드된이미지(new->1.PNG), 현재DB에 있는 이미지(old->4.PNG)
				String oldProfile=memberService.getMember(memID).getMemProfile();
				File oldFile=new File(savePath+"/"+oldProfile);
				if(oldFile.exists()) {
					oldFile.delete();
				}
				newProfile=file.getName();
			}else { // 이미지 파일이 아니면
				if(file.exists()) {
					file.delete(); //삭제
				}
				rttr.addFlashAttribute("msgType", "실패 메세지");
				rttr.addFlashAttribute("msg", "이미지 파일만 업로드 가능합니다.");			
				return "redirect:/member/image";
			}
		}  else {
	        // 사용자가 사진을 선택하지 않았을 때의 처리
	        String oldProfile = memberService.getMember(memID).getMemProfile();
	        File oldFile = new File(savePath + "/" + oldProfile);
	        if (oldFile.exists()) {
	            oldFile.delete(); // 기존 사진 삭제
	        }
	        Member mvo = new Member();
	        mvo.setMemID(memID);
	        mvo.setMemProfile(""); // DB에 저장된 사진 경로를 빈 문자열로 설정
	        memberService.memProfileModify(mvo);

	        Member m = memberService.getMember(memID);
	        session.setAttribute("mvo", m);
	        rttr.addFlashAttribute("msgType", "알림 메세지");
	        rttr.addFlashAttribute("msg", "기존 이미지가 삭제되었습니다.");
	    }
		
		// 새로운 이미지를 테이블에 업데이트
		Member mvo=new Member();
		mvo.setMemID(memID);
		mvo.setMemProfile(newProfile);
		memberService.memProfileModify(mvo); // 이미지 업데이트 성공
		
		Member m=memberService.getMember(memID);
		
		// 세션을 새롭게 생성한다.
		session.setAttribute("mvo", m);
		rttr.addFlashAttribute("msgType", "성공 메세지");
		rttr.addFlashAttribute("msg", "이미지 변경이 성공했습니다.");	
		return "redirect:/";
	}	
	    
}
