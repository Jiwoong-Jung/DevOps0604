package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Comment;
import com.mycompany.myapp.domain.Post;
import com.mycompany.myapp.service.dto.CommentDTO;
import com.mycompany.myapp.service.dto.PostDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Comment} and its DTO {@link CommentDTO}.
 */
@Mapper(componentModel = "spring")
public interface CommentMapper extends EntityMapper<CommentDTO, Comment> {
    @Mapping(target = "post", source = "post", qualifiedByName = "postId")
    @Mapping(target = "parent", source = "parent", qualifiedByName = "commentId")
    CommentDTO toDto(Comment s);

    @Named("commentId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CommentDTO toDtoCommentId(Comment comment);

    @Named("postId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PostDTO toDtoPostId(Post post);
}
