package com.blog.mywebsite.util;

import com.blog.mywebsite.dto.CommentDTO;
import com.blog.mywebsite.model.Comment;

import java.util.*;

import static com.blog.mywebsite.constant.CommentTestConstant.*;

public class CommentTestUtil {
    private CommentTestUtil(){
        throw new AssertionError();
    }

    public static Comment createComment(){
        Comment comment = new Comment();
        comment.setId(ID);
        comment.setContent(CONTENT);
        comment.setCreatedAt(CREATED_AT);
        comment.setUpdatedAt(UPDATED_AT);
        return comment;
    }

    public static Comment createParentComment(){
        Comment comment = new Comment();
        comment.setId(PARENT_ID);
        comment.setContent(PARENT_CONTENT);
        comment.setCreatedAt(PARENT_CREATED_AT);
        comment.setUpdatedAt(PARENT_UPDATED_AT);
        return comment;
    }

    public static Comment createSubComment(){
        Comment comment = new Comment();
        comment.setId(SUB_ID);
        comment.setContent(SUB_CONTENT);
        comment.setCreatedAt(SUB_CREATED_AT);
        comment.setUpdatedAt(SUB_UPDATED_AT);
        return comment;
    }

    public static List<Comment> createCommentList(){
        List<Comment> commentList = new ArrayList<>();
        commentList.add(createComment());
        return commentList;
    }

    public static CommentDTO createCommentDTO(){
        return new CommentDTO(ID, null, CONTENT);
    }

    public static List<CommentDTO> createCommentDTOList(){
        List<CommentDTO> commentDTOList = new ArrayList<>();
        commentDTOList.add(createCommentDTO());
        return commentDTOList;
    }
}