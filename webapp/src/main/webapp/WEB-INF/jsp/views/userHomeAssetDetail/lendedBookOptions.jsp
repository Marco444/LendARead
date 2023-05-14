<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">
<link rel="stylesheet" href="<c:url value="/static/css/modal.css"/>">

<div>
    <c:if test="${asset.assetState.isPending()}">
    <button id="confirmAssetBtn" class="btn-green" type="submit">

        <h6>The book is currently:
            <spring:message code="userHomeView.pending"/>
        </h6>
            <spring:message code="userHomeView.verifyBook"/>
    </button>
    </c:if>


    <c:if test="${asset.assetState.isBorrowed()}">
    <button id="returnAssetBtn" class="btn-green" type="submit">
        <h6>The book is currently:
            <spring:message code="userHomeView.inProgress"/>
        </h6>
            <spring:message code="userHomeView.return"/>
    </button>
    </c:if>
</div>

<jsp:include page="returnModal.jsp">
    <jsp:param name="asset" value="${asset}"/>
</jsp:include>
<jsp:include page="confirmModal.jsp">
    <jsp:param name="asset" value="${asset}"/>
</jsp:include>