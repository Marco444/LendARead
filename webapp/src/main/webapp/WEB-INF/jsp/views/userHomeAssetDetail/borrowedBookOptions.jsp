<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">
<link rel="stylesheet" href="<c:url value="/static/css/modal.css"/>">
<c:if test="${lending.active.getIsActive()}">
    <div class="options-menu">
        <spring:message code="borrowedBookOption.pendingInfo"/>
        <button id="cancelAssetBtn" class="btn btn-red mt-3" type="submit">Cancel Lending</button>
    </div>
    <jsp:include page="cancelModal.jsp">
        <jsp:param name="lending" value="${lending}"/>
    </jsp:include>
</c:if>
<c:if test="${canReview}">
    <a class="btn btn-green mt-3" href="<c:out value="/review/borrower/${lending.id}"/>"><spring:message code="makeReview"/></a>
</c:if>