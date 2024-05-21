package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Attach;
import com.mycompany.myapp.domain.AttachGroup;
import com.mycompany.myapp.service.dto.AttachDTO;
import com.mycompany.myapp.service.dto.AttachGroupDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Attach} and its DTO {@link AttachDTO}.
 */
@Mapper(componentModel = "spring")
public interface AttachMapper extends EntityMapper<AttachDTO, Attach> {
    @Mapping(target = "attachGroup", source = "attachGroup", qualifiedByName = "attachGroupId")
    AttachDTO toDto(Attach s);

    @Named("attachGroupId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AttachGroupDTO toDtoAttachGroupId(AttachGroup attachGroup);
}
