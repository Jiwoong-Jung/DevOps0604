package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Board;
import com.mycompany.myapp.service.dto.BoardDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Board} and its DTO {@link BoardDTO}.
 */
@Mapper(componentModel = "spring")
public interface BoardMapper extends EntityMapper<BoardDTO, Board> {}
