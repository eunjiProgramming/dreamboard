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
  </script>
</head>
<body>
 
  <jsp:include page="../common/header.jsp"/> 
  <div class="card">
  	<div class="card-header">로그인</div>
    <div class="card-body">
	    <form action="${contextPath}/member/login" method="post">
		 <div class="form-group">
		    <label for="memID">아이디</label>
		    <input type="text" class="form-control" placeholder="아이디를 입력해주세요" id="memID" name="memID" value="${param.memID}">
		</div>
		<div class="form-group">
		    <label for="memPwd">비밀번호:</label>
		    <input type="password" class="form-control" placeholder="비밀번호를 입력해주세요" id="memPwd" name="memPwd">
		</div>
		  <button type="submit" class="btn btn-primary">로그인</button>
		</form>
		<div class="mt-3">
        <a href="${contextPath}/member/find/id" class="text-primary">아이디 찾기</a> | 
        <a href="${contextPath}/member/find/password" class="text-primary">비밀번호 찾기</a>
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
    <div class="card-footer">DreamBoard_권은지</div>
  </div>

</body>
</html>
