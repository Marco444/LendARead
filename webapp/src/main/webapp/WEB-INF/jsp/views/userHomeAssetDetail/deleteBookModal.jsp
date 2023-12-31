<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="modal fade" id="deleteBookModal" tabindex="-1" role="dialog"
     aria-labelledby="${param.modalTitle}" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content rounded-3 border-0 shadow">
            <div class="modal-header border-0" style="text-align: center">
                <div class="icon-box" style="background: #D45235;">
                    <i class="fas fa-trash fa-lg"></i>
                </div>
                <h3 class="modal-title w-100 mt-2">
                    <spring:message code="userHomeView.deleteBookTitle"/>
                </h3>
            </div>
            <div class="modal-body text-center py-0 border-0">
                <p class="mb-4">
                    <spring:message code="userHomeView.deleteBookText"/>
            </div>
            <div class="modal-footer border-0">
                <c:url var="deleteAssetUrl" value="/deleteAsset/${asset.id}"/>
                <form action="${deleteAssetUrl}" method="post">
                    <button type="submit" class="btn-red-outline">
                        <spring:message code="userHomeView.remove.button"/>
                    </button>
                </form>
            </div>
        </div>
    </div>
</div>
<script>
    const deleteBtnHandler = document.getElementById('deleteBtn');
    if (deleteBtnHandler !== null) {
        deleteBtnHandler.addEventListener('click', function () {
            new bootstrap.Modal($('#deleteBookModal')).show();
        });
    }
    const noButton2 = document.getElementById('no_button1');
    if (noButton2 !== null) {
        noButton2.addEventListener('click', function () {
            $('#deleteBookModal').modal('hide')
        });
    }
</script>
