<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>


<script>
    var memName = "${mvo.memName}";
</script>

<div class="card" style="min-height: 500px; max-height: 100px;">
    <div class="row">
        <div class="col-lg-12">
            <div class="card-body">
                <c:if test="${!empty mvo}">
                    <h4 class="card-title">${mvo.memName}</h4>
                    <p class="card-text">회원님 Welcome!</p>
                </c:if>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-lg-12">
            <div class="card-body">
                <p class="card-text">MAP VIEW</p>
                <div class="input-group mb-3">
                    <input type="text" class="form-control" id="address" placeholder="Search" value="${mvo.memAddr}" />
                    <div class="input-group-append">
                        <button type="button" class="btn btn-secondary" id="mapBtn">GO</button>
                    </div>
                </div>
                <div id="map" style="width:100%;height:150px;"></div>
            </div>
        </div>
    </div>
</div>

<script>
$(document).ready(function() {
    // 주소 입력란에 값이 있을 경우 mapViewBtn 함수를 호출하여 지도를 로드
    if($("#address").val()) {
        mapViewBtn();
    }

    $("#mapBtn").click(mapViewBtn);
});
</script>
