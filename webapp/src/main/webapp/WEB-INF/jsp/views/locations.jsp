<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
  <link rel="shortcut icon" href="<c:url value='/static/images/favicon-claro-bg.ico'/>" type="image/x-icon">
  <script src="<c:url value="/static/javaScript/topbar.js"/>"></script>
  <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"
          integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN"
          crossorigin="anonymous"></script>
  <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"
          integrity="sha384-IQsoLXl5PILFhosVNubq5LC7Qb9DXgDA9i+tQ8Zj3iwWAwPtgFTxbJ8NT4GN1R8p"
          crossorigin="anonymous"></script>
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.min.js"
          integrity="sha384-cVKIPhGWiC2Al4u+LWgxfKTRIcfu0JTxR+EQDz/bgldoEyl4H0zUF0QKbrJ0EcQF"
          crossorigin="anonymous"></script>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha2/dist/css/bootstrap.min.css" rel="stylesheet"
        integrity="sha384-aFq/bzH65dt+w6FI2ooMVUpc+21e0SRygnTpmBvdBgSdnuTN7QbdgL+OapgHtvPp" crossorigin="anonymous">
  <link href="<c:url value="/static/css/main.css"/>" rel="stylesheet"/>
  <script src="<c:url value="/static/javaScript/addAssetView.js"/>" defer></script>
  <script src="<c:url value="/static/javaScript/utils.js"/>" defer></script>
  <link href="<c:url value="/static/css/addAssetView.css"/>" rel="stylesheet"/>
  <script src="<c:url value="/static/javaScript/addAssetForm.js"/>" defer></script>
  <title><spring:message code="addAssetView.locationInfo"/></title>
</head>

<jsp:include page="../components/navBar.jsp"/>
<body class="body-class">
<div class="main-class" style="display: flex; justify-content: center;align-items: center;flex-direction: column;">
  <div class="container">
    <div class="d-flex flex-wrap">
      <c:forEach var="location" items="${locations}">
        <div class="info-container m-3" style="max-width: 300px">
          <form action="location/edit" method="post">
            <div class="d-flex justify-content-end">
              <button type="button" class="btn btn-danger delete-location" data-location-id="${location.getId()}">
                <i class="fas fa-trash-alt"></i>
              </button>
            </div>
            <div class="field-group">
              <div class="field">
                <spring:message code="addAssetView.localityLabel" var="localityLabel"/>
                <spring:message code="addAssetView.placeholders.city" var="localityPH"/>
                <label for="locality${location.getId()}" class="form-label">${localityLabel}</label>
                <input type="text" name="locality" id="locality${location.getId()}" placeholder="${localityPH}"
                       class="form-control" value="${location.locality}" disabled/>
              </div>
              <div class="field">
                <spring:message code="addAssetView.provinceLabel" var="provinceLabel"/>
                <spring:message code="addAssetView.placeholders.province" var="provincePH"/>
                <label for="province${location.getId()}" class="form-label">${provinceLabel}</label>
                <input type="text" name="province" id="province${location.getId()}" placeholder="${provincePH}"
                       class="form-control" value="${location.province}" disabled/>
              </div>
              <div class="field">
                <spring:message code="addAssetView.countryLabel" var="countryLabel"/>
                <spring:message code="addAssetView.placeholders.country" var="countryPH"/>
                <label for="country${location.getId()}" class="form-label">${countryLabel}</label>
                <input type="text" name="country" id="country${location.getId()}" placeholder="${countryPH}"
                       class="form-control" value="${location.country}" disabled/>
              </div>
              <div class="field">
                <spring:message code="addAssetView.zipcodeLabel" var="zipcodeLabel"/>
                <spring:message code="addAssetView.placeholders.zipcode" var="zipcodePH"/>
                <label for="zipcode${location.getId()}" class="form-label">${zipcodeLabel}</label>
                <input type="text" name="zipcode" id="zipcode${location.getId()}" placeholder="${zipcodePH}"
                       class="form-control" value="${location.zipcode}" disabled/>
              </div>
              <input type="hidden" name="id" value="${location.getId()}">
            </div>
            <div class="mt-3 form-button-container">
              <button type="button" class="edit-button btn btn-outline-success mx-1">Edit</button>
              <input type="submit" class="save-button btn btn-green mx-1 d-none" value="Save"/>
            </div>
          </form>
        </div>
      </c:forEach>
      <div class="info-container m-3 add-new-location">
        <div class="d-flex justify-content-center align-items-center" style="height: 100%;">
          <button type="button" class="btn btn-primary btn-lg">+</button>
        </div>
      </div>
    </div>
  </div>
</div>
</body>
</html>


<script>
  $(function() {
    $('.edit-button').on('click', function() {
      $(this).closest('form').find('input[type="text"]').prop('disabled', false);
      $(this).addClass('d-none');
      $(this).siblings('.save-button').removeClass('d-none');
    });

    $('.add-new-location button').on('click', function() {
      // logic to add a new location
    });
  });

</script>