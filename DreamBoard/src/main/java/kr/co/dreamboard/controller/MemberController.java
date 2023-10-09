package kr.co.dreamboard.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
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
			
			if (!mvo.isEnabled()) { // enabled가 false인 경우
	            rttr.addFlashAttribute("msgType", "실패 메세지");
	            rttr.addFlashAttribute("msg", "해당 계정은 비활성화 상태입니다.");
	            rttr.addAttribute("memID", m.getMemID());
	            return "redirect:/member/login";
	        }
			
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
	
	// 비밀번호 찾기 화면으로 이동
	@GetMapping("/find/password")
	public String findPwd() {
		return "member/findpwd";
	}
	
	// 비밀번호 찾기 기능 구현
	@PostMapping("/find/pwd")
	public String findPwd(Member m, RedirectAttributes rttr, HttpSession session) {
		
		if (m.getMemName() == null || m.getMemName().trim().equals("") || 
				m.getMemID() == null || m.getMemID().trim().equals("") || 
				m.getMemEmail() == null || m.getMemEmail().trim().equals("")) {
			
			rttr.addFlashAttribute("msgType", "실패 메세지");
			rttr.addFlashAttribute("msg", "모든 내용을 입력해주세요.");
			
			rttr.addAttribute("memName", m.getMemName());
			rttr.addAttribute("memID", m.getMemID());
			rttr.addAttribute("memEmail", m.getMemEmail());
			
			return "redirect:/member/find/password";
		}
		
		
		Member mvo = memberService.findPwd(m);
		
		if (mvo != null) { // 비밀번호 찾기 성공
			rttr.addFlashAttribute("msgType", "성공 메세지");
			rttr.addFlashAttribute("msg", "비밀번호를 변경해주세요");
			rttr.addAttribute("memID", mvo.getMemID());
			session.setAttribute("canChangePwd", true);
			return "redirect:/member/find/password/change";
			
		} else { // 비밀번호 찾기 실패
			rttr.addFlashAttribute("msgType", "실패 메세지");
			rttr.addFlashAttribute("msg", "일치하는 회원이 없습니다.");
			rttr.addAttribute("memName", m.getMemName());
			rttr.addAttribute("memID", m.getMemID());
			rttr.addAttribute("memEmail", m.getMemEmail());
			return "redirect:/member/find/password";
		}
	}
	
	// 비밀번호 찾기 페이지
	@GetMapping("/find/password/change")
	public String pwdChange(HttpSession session) {
		// 직접 URL 접근 허용되지 않음
	    if (session.getAttribute("canChangePwd") == null) {
	        return "redirect:/member/login";
	    }
	    
	    session.removeAttribute("canChangePwd"); // 사용 후 Attribute 제거
	    return "member/pwdChange";
	}
	
	@PostMapping("/find/password/change")
	public String passwordChange(@RequestParam("memPwd") String memPwd, @RequestParam("memID") String memID,
			RedirectAttributes rttr, HttpSession session) {
	    
		// 비밀번호 유효성 검사를 위한 패턴
		Pattern pwdPattern = Pattern.compile("^(?=.*[a-z])[a-z\\d*!]{7,20}$");
	    
	    // 비밀번호 유효성 검사
	    if (memPwd == null || memPwd.trim().equals("")) {
	        rttr.addFlashAttribute("msgType", "실패 메세지");
	        rttr.addFlashAttribute("msg", "비밀번호 변경 / 확인을 입력해주세요.");
	        session.setAttribute("canChangePwd", true);
	        return "redirect:/member/find/password/change";
	    }
	    
	    if (!pwdPattern.matcher(memPwd).matches()) {
	        rttr.addFlashAttribute("msgType", "실패 메세지");
	        rttr.addFlashAttribute("msg", "비밀번호는 영문자(소문자)를 반드시 포함하며, 숫자와 특수문자 *! 만 포함 가능하고, 7~20자여야 합니다.");
	        session.setAttribute("canChangePwd", true);
	        return "redirect:/member/find/password/change";
	    }
	    
	    Map<String, String> params = new HashMap<>();
	    params.put("memPwd", memPwd);
	    params.put("memID", memID);
	    
	    int result = memberService.modifyPasswordByMemID(params);
	    
	    if(result > 0) {
	        // 성공 시 로직
	        rttr.addFlashAttribute("msgType", "성공 메세지");
	        rttr.addFlashAttribute("msg", "비밀번호 변경이 성공했습니다.");
	        return "redirect:/member/login"; // 성공 시 리다이렉트될 페이지
	    } else {
	        // 실패 시 로직
	        rttr.addFlashAttribute("msgType", "실패 메세지");
	        rttr.addFlashAttribute("msg", "비밀번호 변경에 실패했습니다.");
	        return "redirect:/member/login"; // 실패 시 리다이렉트될 페이지 지정
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
	    Pattern idPattern = Pattern.compile("^(?=.*[a-z])[a-z0-9]{3,20}$");
	    Matcher idMatcher = idPattern.matcher(m.getMemID());

	    if (!idMatcher.find()) {
	        rttr.addFlashAttribute("msgType", "실패 메세지");
	        rttr.addFlashAttribute("msg", "아이디는 소문자를 반드시 포함하며, 숫자는 포함 가능한 3자 이상 20자 이하로 입력해주세요.");
	        return "redirect:/member/join";
	    }

	    // 비밀번호 유효성 검사
	    if (!memPwd1.equals(memPwd2)) {
	        rttr.addFlashAttribute("msgType", "실패 메세지");
	        rttr.addFlashAttribute("msg", "비밀번호가 서로 다릅니다.");
	        return "redirect:/member/join";
	    }
	    
	    
	    Pattern pwdPattern = Pattern.compile("^(?=.*[a-z])[a-z0-9*!]{7,20}$");
	    Matcher pwdMatcher = pwdPattern.matcher(memPwd1);

	    if (!pwdMatcher.find()) {
	        rttr.addFlashAttribute("msgType", "실패 메세지");
	        rttr.addFlashAttribute("msg", "비밀번호는 소문자를 반드시 포함하며, 숫자 및 특수문자 *! 는 포함 가능한 7자 이상 20자 이하로 입력해주세요.");
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
		   m = memberService.getMember(m.getMemID());
		   
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
            RedirectAttributes rttr, HttpSession session, HttpServletRequest request)  {
		
		
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

	    Pattern pwdPattern = Pattern.compile("^(?=.*[a-z])[a-z0-9*!]{7,20}$");
	    Matcher pwdMatcher = pwdPattern.matcher(memPwd1);

	    if (!pwdMatcher.find()) {
	        rttr.addFlashAttribute("msgType", "실패 메세지");
	        rttr.addFlashAttribute("msg", "비밀번호는 소문자를 반드시 포함하며, 숫자 및 특수문자 *! 는 포함 가능한 7자 이상 20자 이하로 입력해주세요.");
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
	    
	    String originURL = request.getParameter("originURL");
	    
	    if (originURL == null || originURL.isEmpty()) {
	        originURL = "/";  // 기본 페이지 (홈페이지)로 설정
	    }
	    
	    // 회원을 수정저장하기
	    int result=memberService.modify(m);
	    if(result==1) {
	       rttr.addFlashAttribute("msgType", "성공 메세지");
	       rttr.addFlashAttribute("msg", "회원정보 수정에 성공했습니다.");
	       session.setAttribute("mvo", m);
	       return "redirect:" + originURL;
	    } else {
	       rttr.addFlashAttribute("msgType", "실패 메세지");
	       rttr.addFlashAttribute("msg", "회원정보 수정에 실패했습니다.");
	       return "redirect:" + originURL;
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
		
		// Referer 헤더를 이용하여 이전 페이지의 URL을 가져옵니다.
		String originURL = multi.getParameter("originURL");
	    
	    // 만약 Referer 정보가 없거나 비어있으면 기본 페이지 URL로 설정
	    if (originURL == null || originURL.isEmpty()) {
	        originURL = "/"; 
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
		return "redirect:" + originURL;
	}	
	
	@GetMapping("/deactivate")
    public String deactivateMember(HttpSession session, RedirectAttributes rttr) {
		Member mvo = (Member) session.getAttribute("mvo");

		if (mvo != null) {
		    String memID = mvo.getMemID();
            memberService.deactivateMember(memID);
            session.invalidate(); // 회원 탈퇴 후 세션 무효화
            rttr.addFlashAttribute("msgType", "성공 메세지");
            rttr.addFlashAttribute("msg", "회원 탈퇴가 성공적으로 이루어졌습니다.");
            return "redirect:/"; // 홈페이지나 로그인 페이지로 리다이렉트
        } else {
            rttr.addFlashAttribute("msgType", "오류 메세지");
            rttr.addFlashAttribute("msg", "회원 탈퇴에 실패하였습니다.");
            return "redirect:/"; // 마이 페이지나 다른 페이지로 리다이렉트
        }
    }
	    
}
