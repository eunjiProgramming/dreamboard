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
  <script src="https://cdn.jsdelivr.net/npm/jquery@3.6.4/dist/jquery.slim.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
  <script type="text/javascript">
	  $(document).ready(function(){
	  	if(${!empty msgType}){
	  		$("#myModal").modal("show");
	  	}
	  });
	  
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
		  var memPwd1 = $("#memPwd1").val();
		  var memPwd2 = $("#memPwd2").val();
		  var pwdPattern = /^(?=.*[a-z])[a-z\d*!]{7,20}$/;
		  
		// 비밀번호 유효성 검사
	    if (!memPwd1) {
	        $("#checkMessage").html("비밀번호 변경을 입력해주세요.");
	        $("#checking").modal("show");
	        return false;
	    }
	 	
	    if (!memPwd2) {
	        $("#checkMessage").html("비밀번호 확인을 입력해주세요.");
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
	    
	  document.frm.submit(); // 전송
   	 return true;
		  
	  }
  </script>
</head>
<body>
  <jsp:include page="../common/header.jsp"/> 
  <div class="card">
   <div class="card-header">비밀번호 수정</div>
    <div class="card-body">
	    <form name="frm" action="${contextPath}/member/find/password/change" method="post">
	    <input type="hidden" id="memPwd" name="memPwd" value=""/>
	    <input type="hidden" id="memID" name="memID" value="${param.memID}"/>
		 <div class="form-group">
		    <label for="memPwd1">비밀번호 변경:</label>
		    <input type="password" onkeyup="passwordCheck()" class="form-control" placeholder="비밀번호 입력해주세요" id="memPwd1" name="memPwd1"/>
		</div>
		 <div class="form-group">
		    <label for="memPwd2">비밀번호 확인:</label>
		    <input type="password" onkeyup="passwordCheck()" class="form-control" placeholder="비밀번호 입력해주세요" id="memPwd2" name="memPwd2"/>
		</div>
	   	<td colspan="3" style="text-align: left;">
	        <span id="passMessage" style="color: red"></span>
	        <input type="button" class="btn btn-primary btn-sm float-right" value="비밀번호 변경" onclick="validateForm()"/>
	    </td>
		</form>
		
		
		
		
    </div>
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
