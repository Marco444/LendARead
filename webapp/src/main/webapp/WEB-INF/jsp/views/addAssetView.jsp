<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<head>
    <title>Prestar Libro</title>
    <script src="<c:url value="/static/javaScript/topbar.js"/>"></script>
    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.12.9/dist/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-aFq/bzH65dt+w6FI2ooMVUpc+21e0SRygnTpmBvdBgSdnuTN7QbdgL+OapgHtvPp" crossorigin="anonymous">
    <link href="<c:url value="/static/css/main.css"/>" rel="stylesheet"/>
    <link href="<c:url value="/static/css/addAssetView.css"/>" rel="stylesheet"/>

</head>

<body data-path="${path}" class = "body-class">

<jsp:include page="../components/navBar.jsp"/>
<div class="main-class">
    <div class="container my-5">

        <jsp:include page="../components/snackbarComponent.jsp" />

        <h1 class="text-center mb-5">¿Quieres prestar un libro?</h1>
        <div class="p-4 rounded" >
            <div class="flex-container">

                <div class="image-wrapper">
                    <div class="image-container position-relative">
                        <img src="<c:url value="/static/images/no_image_placeholder.jpg"/>" alt="Book Cover" class="img-fluid" id="bookImage" style="width: 400px; height: 600px; object-fit: cover">
                        <label for="uploadImage" class="position-absolute bottom-0 end-0 btn btn-primary" id="uploadLabel">
                            <i class="bi bi-cloud-upload"></i> Subir foto
                        </label>
                    </div>
                </div>

                <div class="form-wrapper">
                    <c:url var="addAssetUrl" value="/addAsset"/>
                    <form:form modelAttribute="addAssetForm" method="post" action="${addAssetUrl}" enctype="multipart/form-data" id="form" accept-charset="utf-9">
                        <div class="info-container">
                            <h2>Libro:</h2>
                            <div class="field-group">
                                <div class="field">
                                    <label for="title" class="form-label">Titulo:</label>
                                    <form:input path="title" id="title" placeholder="Titulo" class="form-control" readonly="true" />
                                    <form:errors path="title" cssClass="text-danger small" element="small"/>
                                </div>
                                <div class="field">
                                    <label for="physicalCondition" class="form-label">Estado:</label>
                                    <form:select path="physicalCondition" id="physicalCondition" class="form-control" accept-charset="utf-9">
                                        <form:option value="asnew">Nuevo</form:option>
                                        <form:option value="fine">Casi nuevo</form:option>
                                        <form:option value="verygood">Muy bien</form:option>
                                        <form:option value="good">Bien</form:option>
                                        <form:option value="fair">Aceptable</form:option>
                                        <form:option value="poor">Pobre</form:option>
                                        <form:option value="exlibrary">Ex-biblioteca</form:option>
                                        <form:option value="bookclub">Book club</form:option>
                                        <form:option value="bindingcopy">Dorso dañado</form:option>
                                    </form:select>
                                </div>
                            </div>
                            <div class="field-group">
                            <div class="field">
                                    <label for="isbn" class="form-label">ISBN:</label>
                                    <form:input path="isbn" id="isbn" placeholder="ISBN" class="form-control"/>
                                    <form:errors path="isbn" cssClass="text-danger small" element="small"/>
                                </div>
                                <div class="field">
                                    <label for="author" class="form-label">Autor:</label>
                                    <form:input path="author" id="author" placeholder="Autor" class="form-control" readonly="true" />
                                    <form:errors path="author" cssClass="text-danger small" element="small"/>
                                </div>
                            <div class="field-group">

                                <div class="field">
                                    <label for="language" class="form-label">Idioma:</label>
                                    <form:input path="language" id="language" placeholder="Idioma" class="form-control" readonly="true" />
                                    <form:errors path="language" cssClass="text-danger small" element="small"/>
                                </div>
                            </div>
                            </div>
                        </div>
                        <div class="info-container">
                            <h2>Ubicacion: </h2>
                            <div class="field-group">
                                <div class="field">
                                    <label for="locality" class="form-label">Localidad:</label>
                                    <form:input path="locality" id="locality" placeholder="Localidad" class="form-control"/>
                                    <form:errors path="locality" cssClass="text-danger small" element="small"/>
                                </div>
                                <div class="field">
                                    <label for="province" class="form-label">Provincia:</label>
                                    <form:input path="province" id="province" placeholder="Provincia" class="form-control"/>
                                    <form:errors path="province" cssClass="text-danger small" element="small"/>
                                </div>
                            </div>
                            <div class="field-group">

                            <div class="field">
                                    <label for="country" class="form-label">Pais:</label>
                                    <form:input path="country" id="country" placeholder="Pais" class="form-control"/>
                                    <form:errors path="country" cssClass="text-danger small" element="small"/>
                                </div>
                                <div class="field">
                                    <label for="zipcode" class="form-label">Codigo postal:</label>
                                    <form:input path="zipcode" id="zipcode" placeholder="Codigo postal" class="form-control"/>
                                    <form:errors path="zipcode" cssClass="text-danger small" element="small"/>
                                </div>
                            </div>
                        </div>
                        <div class="info-container">
                            <h2>Contacto: </h2>
                            <div class="field-group">
                                <div class="field">
                                    <label for="name" class="form-label">Nombre:</label>
                                    <form:input path="name" id="name" placeholder="Nombre" class="form-control"/>
                                    <form:errors path="name" cssClass="text-danger small" element="small"/>
                                </div>
                                <div class="field">
                                    <label for="email" class="form-label">Email:</label>
                                    <form:input path="email" id="email" placeholder="Email" class="form-control"/>
                                    <form:errors path="email" cssClass="text-danger small" element="small"/>
                                </div>
                            </div>
                        </div>
                        <input type="file" accept="image/*" name="file" id="uploadImage" style="display:none;" onchange="previewImage()" />
                        <div style="display: flex; justify-content: center;">
                            <button type="submit" class="btn btn-primary" style="padding: 9px 20px;">Agregarlo!</button>
                        </div>
                    </form:form>
                </div>
            </div>
        </div>
    </div>
</div>
<% request.setCharacterEncoding("utf-8"); %>
<jsp:include page="../components/modal.jsp">
    <jsp:param name="modalTitle" value="Libro subido"/>
    <jsp:param name="text" value="El libro se ha añadido a nuestra base de datos. Te enviaremos un mail cuando otro usuario solicite prestamo de este libro."/>
</jsp:include>

</body>
<script>
    function previewImage() {
        const fileInput = document.getElementById('uploadImage');
        const file = fileInput.files[0];
        const img = document.getElementById('bookImage');
        const reader = new FileReader();

        reader.addEventListener('load', function () {
            img.src = reader.result;
        }, false);

        if (file) {
            reader.readAsDataURL(file);
        }
    }
</script>
<script>
    const isbnInput = document.getElementById('isbn');
    const titleInput = document.getElementById('title');
    const authorInput = document.getElementById('author');
    const languageInput = document.getElementById('language');
    const form = document.getElementById('form')

    isbnInput.addEventListener('input', async (event) => {
        const isbn = event.target.value;

        const inputFields = [titleInput, authorInput, languageInput];

        if (isbn.length === 13) {
            try {
                inputFields.forEach(field => field.classList.add('loading'))
                let url = `<c:url value="/book"><c:param name="isbn" value="${isbn}" /></c:url>`;
                const response = await fetch(url + isbn); //No te dejan usar fetch para tu back end
                const book = await response.json();
                titleInput.value = book.name || '';
                authorInput.value = book.author || '';
                languageInput.value = book.language || '';
                console.log(book);

                inputFields.forEach(field => field.classList.remove('loading'))

                // enable inputs for missing information
                if (!book.name) titleInput.readOnly = false;
                if (!book.author) authorInput.readOnly = false;
                if (!book.language) languageInput.readOnly = false;
            } catch (error) {
                console.error(error);
            }
        } else {
            // clear inputs and disable
            titleInput.value = '';
            authorInput.value = '';
            languageInput.value = '';
            titleInput.readOnly = true;
            authorInput.readOnly = true;
            languageInput.readOnly = true;
        }
    });
</script>