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
   <div class="card-header">아이디 찾기</div>
    <div class="card-body">
	    <form action="${contextPath}/member/find/id" method="post">
		 <div class="form-group">
		    <label for="memName">이름:</label>
		    <input type="text" class="form-control" placeholder="성함 입력해주세요" id="memName" name="memName" value="${param.memName}">
		</div>
		<div class="form-group">
		    <label for="memEmail">이메일:</label>
		    <input type="email" class="form-control" placeholder="이메일을 입력해주세요" id="memEmail" name="memEmail" value="${param.memEmail}">
		</div>
		  <button type="submit" class="btn btn-primary">아이디 찾기</button>
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
    <div class="card-footer">DreamBoard_권은지</div>
  </div>

</body>
</html>
