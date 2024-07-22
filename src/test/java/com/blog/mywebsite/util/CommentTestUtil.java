package com.blog.mywebsite.util;

import com.blog.mywebsite.constant.CommentTestConstant;
import com.blog.mywebsite.dto.CommentDTO;
import com.blog.mywebsite.model.Comment;

import java.util.*;

public class CommentTestUtil {
    private CommentTestUtil(){
        throw new AssertionError();
    }

    public static Comment createComment(){
        Comment comment = new Comment();
        comment.setId(UUID.randomUUID().toString());
        comment.setContent(CommentTestConstant.CONTENT);
        comment.setSubComments(createSubComment());
        comment.setCreatedAt(CommentTestConstant.CREATED_AT);
        comment.setParent(null);
        return comment;
    }

    public static CommentDTO createCommentDTO(){
        return new CommentDTO(
                UUID.randomUUID().toString(),
                null,
                CommentTestConstant.CONTENT
        );
    }

    public static List<Comment> createCommentList(){
        List<Comment> commentList = new ArrayList<>();
        commentList.add(createComment());
        return commentList;
    }

    public static List<CommentDTO> createCommentDTOList(){
        List<CommentDTO> commentDTOList = new ArrayList<>();
        commentDTOList.add(createCommentDTO());
        commentDTOList.add(createCommentDTO());
        return commentDTOList;
    }

    private static Comment createParentComment(){
        Comment comment = createComment();
        comment.setId(CommentTestConstant.PARENT_ID);
        comment.setSubComments(createSubComment());
        return comment;
    }

    private static Set<Comment> createSubComment(){
        Set<Comment> subComments = new HashSet<>();
        Comment subComment1 = new Comment();
        subComment1.setId(UUID.randomUUID().toString());
        subComment1.setContent("Sub Content1 Content");
        subComment1.setParent(createParentComment());
        subComment1.setCreatedAt(CommentTestConstant.CREATED_AT);
        subComment1.setSubComments(null);

        Comment subComment2 = new Comment();
        subComment1.setId(UUID.randomUUID().toString());
        subComment1.setContent("Sub Content2 Content");
        subComment1.setParent(createParentComment());
        subComment1.setCreatedAt(CommentTestConstant.CREATED_AT);
        subComment1.setSubComments(null);

        subComments.add(subComment1);
        subComments.add(subComment2);
        return subComments;
    }
}
