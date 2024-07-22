package com.blog.mywebsite;

import com.blog.mywebsite.constant.CategoryTestConstant;
import com.blog.mywebsite.dto.CategoryDTO;
import com.blog.mywebsite.exception.EntityExistException;
import com.blog.mywebsite.exception.EntityNotFoundException;
import com.blog.mywebsite.model.Category;
import com.blog.mywebsite.repository.CategoryRepository;
import com.blog.mywebsite.service.impl.CategoryServiceImpl;
import com.blog.mywebsite.util.CategoryTestUtil;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

@ExtendWith({SpringExtension.class})
public class CategoryServiceTest {
}