package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.AttachGroup;
import com.mycompany.myapp.domain.Post;
import com.mycompany.myapp.service.dto.AttachGroupDTO;
import com.mycompany.myapp.service.dto.PostDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AttachGroup} and its DTO {@link AttachGroupDTO}.
 */
@Mapper(componentModel = "spring")
public interface AttachGroupMapper extends EntityMapper<AttachGroupDTO, AttachGroup> {
    @Mapping(target = "post", source = "post", qualifiedByName = "postId")
    AttachGroupDTO toDto(AttachGroup s);

    @Named("postId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PostDTO toDtoPostId(Post post);
}
