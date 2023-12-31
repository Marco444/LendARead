package ar.edu.itba.paw.webapp.form.annotations.implementations;

import ar.edu.itba.paw.webapp.form.annotations.interfaces.Image;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ImageValidatorImpl implements ConstraintValidator<Image, MultipartFile> {
    private static final long MAX_SIZE = 3000 * 3000;
    private static final String ACCEPTED_MIME_TYPES = "image/";


    @Override
    public void initialize(Image image) {

    }

    @Override
    public boolean isValid(MultipartFile multipartFile, ConstraintValidatorContext constraintValidatorContext) {
        if (multipartFile == null || multipartFile.isEmpty()) {
            return true;
        }
        return multipartFile.getSize() < MAX_SIZE && multipartFile.getContentType().contains(ACCEPTED_MIME_TYPES);
    }
}