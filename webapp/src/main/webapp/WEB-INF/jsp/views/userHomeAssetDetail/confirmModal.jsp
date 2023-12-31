<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="modal fade" id="confirmAssetModal"  tabindex="-1" role="dialog" aria-labelledby="${param.modalTitle}" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content rounded-3 border-0 shadow">
            <div class="modal-header border-0" style="text-align: center">
                <div class="icon-box">
                    <i class="fas fa-check-circle fa-lg"></i>
                </div>
                <h3 class="modal-title w-100 mt-2">
                    <spring:message code="userHomeView.confirmAssetTitle" />
                </h3>
            </div>
            <div class="modal-body text-center py-0 border-0">
                <p class="mb-4">
                    <spring:message code="userHomeView.confirmAssetText" />
            </div>
            <div class="modal-footer border-0">
                <c:url var="confirmAssetUrl" value="/confirmAsset/${lending.id}"/>
                <form action="${confirmAssetUrl}" method="post">
                    <button type="submit" class="btn btn-primary rounded-pill px-4 py-2" style="background-color: #2B3B2B; border-color: #00B4A0;">
                        <spring:message code="confirm" />
                    </button>
                </form>
            </div>
        </div>
    </div>
</div>

<script>
    const confirmAssetBtnHandler = document.getElementById('confirmAssetBtn');
    confirmAssetBtnHandler.addEventListener('click', function() {
        new bootstrap.Modal($('#confirmAssetModal')).show();
    });
</script>