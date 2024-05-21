package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Board;
import com.mycompany.myapp.domain.Post;
import com.mycompany.myapp.service.dto.BoardDTO;
import com.mycompany.myapp.service.dto.PostDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Post} and its DTO {@link PostDTO}.
 */
@Mapper(componentModel = "spring")
public interface PostMapper extends EntityMapper<PostDTO, Post> {
    @Mapping(target = "board", source = "board", qualifiedByName = "boardId")
    PostDTO toDto(Post s);

    @Named("boardId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    BoardDTO toDtoBoardId(Board board);
}
