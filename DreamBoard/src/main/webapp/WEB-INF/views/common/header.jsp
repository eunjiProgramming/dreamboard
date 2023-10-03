<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
<nav class="navbar navbar-expand-sm navbar-light bg-light">

  <a class="navbar-brand" href="${contextPath}/">Dream Board</a>
  
  <ul class="navbar-nav">
  	<li class="nav-item">
      <a class="nav-link" href="${contextPath}/">Home</a>
    </li>
    <li class="nav-item">
      <a class="nav-link" href="${contextPath}/board/list">게시판</a>
    </li>
  </ul>
  <c:if test="${empty mvo}">
	   <ul class="navbar-nav ml-auto">
	    <li class="nav-item">
	      <a class="nav-link" href="${contextPath}/member/login"><i class="fas fa-sign-in-alt"></i> 로그인</a>
	    </li>
	    <li class="nav-item">
	      <a class="nav-link" href="${contextPath}/member/join"><i class="fas fa-user-plus"></i> 회원가입</a>
	    </li>
	  </ul>
  </c:if>
	<c:if test="${!empty mvo}">
	    <ul class="navbar-nav ml-auto">
	        <li class="nav-item">
	            <a class="nav-link" href="${contextPath}/member/modify">
	                <i class="fas fa-wrench"></i> 회원정보수정
	            </a>
	        </li>
	        <li class="nav-item">
	            <a class="nav-link" href="${contextPath}/member/image">
	                <i class="fas fa-image"></i> 사진등록
	            </a>
	        </li>
	        <li class="nav-item">
	            <a class="nav-link" href="${contextPath}/member/logout">
	                <i class="fas fa-sign-out-alt"></i> 로그아웃
	            </a>
	        </li>
	        <c:if test="${!empty mvo}">
	            <c:if test="${empty mvo.memProfile}">
	                <li class="nav-item">
	                    <img src="${contextPath}/resources/images/person.png" class="rounded-circle" style="width: 50px; height: 50px">${mvo.memName} 님 welcome
	                </li>
	            </c:if>
	            <c:if test="${!empty mvo.memProfile}">
	                <li class="nav-item">
	                    <img src="${contextPath}/resources/upload/${mvo.memProfile}" class="rounded-circle" style="width: 50px; height: 50px">${mvo.memName} 님 welcome
	                </li>
	            </c:if>
	        </c:if>
	    </ul>
	</c:if>

</nav>