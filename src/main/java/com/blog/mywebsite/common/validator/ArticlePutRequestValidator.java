package com.blog.mywebsite.common.validator;

import org.w3c.dom.ls.LSResourceResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.validation.Validator;
import java.io.IOException;

public class ArticlePutRequestValidator extends Validator {
    @Override
    public void reset() {

    }

    @Override
    public void validate(Source source, Result result) throws SAXException, IOException {
    }

    @Override
    public void setErrorHandler(ErrorHandler errorHandler) {

    }

    @Override
    public ErrorHandler getErrorHandler() {
        return null;
    }

    @Override
    public void setResourceResolver(LSResourceResolver resourceResolver) {

    }

    @Override
    public LSResourceResolver getResourceResolver() {
        return null;
    }
}
