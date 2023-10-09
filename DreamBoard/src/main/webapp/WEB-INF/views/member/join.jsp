<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="en">
<head>
  <title>Dream Board</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
  <script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
  <script type="text/javascript">
	  $(document).ready(function(){
	  	if(${!empty msgType}){
	  		$("#myModal").modal("show");
	  	}
	  });
	  
	  function registerCheck() {
		    var memID = $("#memID").val();
		    var idPattern = /^(?=.*[a-z])[a-z\d]{3,20}$/;

		    // 아이디 유효성 검사
		    if (!idPattern.test(memID)) {
		        $("#idcheck").html("아이디는 영문자(소문자)를 반드시 포함해야 하며, 숫자는 포함 가능하고, 3~20자여야 합니다.");
		        $("#myModal").modal("show");
		        return;
		    }

		    // 아이디가 유효한 경우에만 중복검사를 실행
		    $.ajax({
		        url: "${contextPath}/member/checkDuplicate",
		        type: "get",
		        data: { "memID": memID },
		        success: function(result) {
		            if (result != 1) {
		                $("#idcheck").html("사용할 수 없는 아이디입니다.");
		            } else {
		                $("#idcheck").html("사용할 수 있는 아이디입니다.");
		            }
		            $("#myModal").modal("show");
		        },
		        error: function() {
		            alert("error");
		        }
		    });
		}

	  
	  function passwordCheck(){
	    	var memPwd1=$("#memPwd1").val();
	    	var memPwd2=$("#memPwd2").val();
	    	if(memPwd1 != memPwd2){
	    		$("#passMessage").html("비밀번호가 서로 일치하지 않습니다.");
	    	}else{
	    		$("#passMessage").html("");
	    		$("#memPwd").val(memPwd1);
	    	}   	
	    }
	  
	  function validateForm() {
		  	var memID = $("#memID").val();
		    var memPwd1 = $("#memPwd1").val();
		    var memPwd2 = $("#memPwd2").val();
		    var memName = $("#memName").val();
		    var memAge = $("#memAge").val();
		    var memEmail = $("#memEmail").val();
		    var memAddr = $("#memAddr").val();
		    var idPattern = /^(?=.*[a-z])[a-z\d]{3,20}$/;
		    var pwdPattern = /^(?=.*[a-z])[a-z\d*!]{7,20}$/;


		    // 아이디 유효성 검사
		    if (!memID) {
		        $("#checkMessage").html("아이디를 입력해주세요.");
		        $("#checking").modal("show");
		        return false;
		    }

		    if (!idPattern.test(memID)) {
		        $("#checkMessage").html("아이디는 영문자(소문자)를 반드시 포함해야 하며, 숫자는 포함 가능하고, 3~20자여야 합니다.");
		        $("#checking").modal("show");
		        return false;
		    }
		 	
		    if (memID.length < 3 || memID.length > 20) {
		        $("#checkMessage").html("아이디는 3자 이상 20자 이하로 입력해주세요.");
		        $("#checking").modal("show");
		        return false;
		    }
		    
		 	// 비밀번호 유효성 검사
		    if (!memPwd1) {
		        $("#checkMessage").html("비밀번호를 입력해주세요.");
		        $("#checking").modal("show");
		        return false;
		    }
		 	
		    if (!memPwd2) {
		        $("#checkMessage").html("비밀번호를 입력해주세요.");
		        $("#checking").modal("show");
		        return false;
		    }
		    
		    if (!pwdPattern.test(memPwd1)) {
		        $("#checkMessage").html("비밀번호는 영문자(소문자)를 반드시 포함하며, 숫자와 특수문자 *! 만 포함 가능하고, 7~20자여야 합니다.");
		        $("#checking").modal("show");
		        return false;
		    }
		    
		    
		    if (memPwd1 !== memPwd2) {
		        $("#checkMessage").html("비밀번호가 서로 일치하지 않습니다.");
		        $("#checking").modal("show");
		        return false;
		    }

		 	// 이름 유효성 검사
		    if (!memName) {
		        $("#checkMessage").html("이름을 입력해주세요.");
		        $("#checking").modal("show");
		        return false;
		    }
		    
		    var namePattern = /^[가-힣]+$/;  // 한글만 허용하는 정규표현식
		    if (!namePattern.test(memName) || memName.length < 2 || memName.length > 20) {
		        $("#checkMessage").html("이름은 한글 2자 이상 20자 이하로 입력해주세요.");
		        $("#checking").modal("show");
		        return false;
		    }

		    // 나이 유효성 검사
		    if (!memAge) {
		        $("#checkMessage").html("나이를 입력해주세요.");
		        $("#checking").modal("show");
		        return false;
		    }
		    
		    if (memAge < 0 || memAge > 120) {
		        $("#checkMessage").html("나이는 0 ~ 120까지만 입력 가능합니다.");
		        $("#checking").modal("show");
		        return false;
		    }

		  // 성별 유효성 검사
		    var genderChecked = $('input[name="memGender"]:checked').length > 0;
		    if (!genderChecked) {
		        $("#checkMessage").html("성별을 선택해주세요.");
		        $("#checking").modal("show");
		        return false;
		    }
		    
		  // 이메일 유효성 검사
		    if (!memEmail) {
		        $("#checkMessage").html("이메일을 입력해주세요.");
		        $("#checking").modal("show");
		        return false;
		    }
		    
		    var emailPattern = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;
		    if (!emailPattern.test(memEmail) || memEmail.length < 10 || memEmail.length > 50) {
		        $("#checkMessage").html("올바른 이메일 양식을 입력해주세요. 10자 이상 50자 이하로 입력해주세요.");
		        $("#checking").modal("show");
		        return false;
		    }

		    // 주소 유효성 검사
		    if (!memAddr) {
		        $("#checkMessage").html("주소를 입력해주세요.");
		        $("#checking").modal("show");
		        return false;
		    }
		    
		    if (memAddr.length < 5 || memAddr.length > 50) {
		        $("#checkMessage").html("주소는 5자 이상 50자 이하로 입력해주세요.");
		        $("#checking").modal("show");
		        return false;
		    }
		    
		    
		 // 아이디 중복 검사
		    var isDuplicated = false;

		    $.ajax({
		        url: "${contextPath}/member/checkDuplicate",
		        type: "get",
		        data: { "memID": memID },
		        async: false,  // 동기식으로 처리
		        success: function(result) {
		            if (result == 0) {
		                isDuplicated = true;
		            }
		        }
		    });

		    if (isDuplicated) {
		        $("#checkMessage").html("사용할 수 없는 아이디입니다.");
		        $("#checking").modal("show");
		        return false; // 중복되면 폼 전송 중지
		    }

		    
		    document.frm.submit(); // 전송
	    	 return true;
		}
	  
  </script>
</head>
<body>
 
  <jsp:include page="../common/header.jsp"/> 
  <div class="card">
    <div class="card-header">회원가입</div>
    <div class="card-body">
    
    <form name="frm" action="${contextPath}/member/join" method="post">
    	<input type="hidden" id="memPwd" name="memPwd" value=""/>
        <div class="form-group">
	        <label for="memID">아이디</label>
	        <div class="input-group">
	            <input type="text" class="form-control" id="memID" name="memID" maxlength="20" required>
	            <div class="input-group-append">
	                <button type="button" class="btn btn-outline-secondary" onclick="registerCheck()">중복 확인</button>
	            </div>
	        </div>
    	</div>
        <div class="form-group">
            <label for="memPwd">비밀번호</label>
            <input type="password" onkeyup="passwordCheck()" class="form-control" id="memPwd1" name="memPwd1" maxlength="20" required>
        </div>
         <div class="form-group">
            <label for="memPwd">비밀번호확인</label>
            <input type="password" onkeyup="passwordCheck()" class="form-control" id="memPwd2" name="memPwd2" maxlength="20" required>
        </div>
        <div class="form-group">
            <label for="memName">이름</label>
            <input type="text" class="form-control" id="memName" maxlength="20" name="memName" required>
        </div>
        <div class="form-group">
            <label for="memAge">나이</label>
            <input type="number" class="form-control" id="memAge" name="memAge" required>
        </div>
        <div class="form-group">
            <label>성별</label><br>
            <div class="form-check form-check-inline">
                <input class="form-check-input" type="radio" name="memGender" id="memGender" value="남">
                <label class="form-check-label" for="male">남자</label>
            </div>
            <div class="form-check form-check-inline">
                <input class="form-check-input" type="radio" name="memGender" id="memGender" value="여">
                <label class="form-check-label" for="female">여자</label>
            </div>
        </div>
        <div class="form-group">
            <label for="memEmail">이메일</label>
            <input type="email" class="form-control" id="memEmail" name="memEmail" maxlength="50" required>
        </div>
        <div class="form-group">
            <label for="memAddr">주소</label>
            <input type="text" class="form-control" id="memAddr" name="memAddr" maxlength="50" required>
        </div>
        <tr>
		    <td colspan="3" style="text-align: left;">
		        <span id="passMessage" style="color: red"></span>
		        <input type="button" class="btn btn-primary btn-sm float-right" value="회원가입" onclick="validateForm()"/>
		    </td>
		</tr>
        
    </form>
    </div> 
     <!-- The Modal -->
	<div class="modal" id="myModal">
	  <div class="modal-dialog">
	    <div class="modal-content">
	
	      <!-- Modal Header -->
	      <div class="modal-header">
	        <h4 class="modal-title">${msgType}</h4>
	        <button type="button" class="close" data-dismiss="modal">&times;</button>
	      </div>
	
	      <!-- Modal body -->
	      <div class="modal-body">
	       <p>${msg}</p>
	       <p id="idcheck"></p>
	      </div>
	
	      <!-- Modal footer -->
	      <div class="modal-footer">
	        <button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>
	      </div>
	
	    </div>
	  </div>
	</div>
	<!-- The Modal End -->
	     <!-- The Modal -->
	<div class="modal" id="checking">
	  <div class="modal-dialog">
	    <div class="modal-content">
	
	      <!-- Modal Header -->
	      <div class="modal-header">
	        <h4 class="modal-title">입력폼 오류</h4>
	        <button type="button" class="close" data-dismiss="modal">&times;</button>
	      </div>
	
	      <!-- Modal body -->
	      <div class="modal-body">
	       <p id="checkMessage"></p>
	      </div>
	
	      <!-- Modal footer -->
	      <div class="modal-footer">
	        <button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>
	      </div>
	
	    </div>
	  </div>
	</div>
	<!-- The Modal End -->
    <div class="card-footer">DreamBoard_권은지</div>
  </div>

</body>
</html>
