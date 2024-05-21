package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Board;
import com.mycompany.myapp.repository.BoardRepository;
import com.mycompany.myapp.service.BoardService;
import com.mycompany.myapp.service.dto.BoardDTO;
import com.mycompany.myapp.service.mapper.BoardMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.mycompany.myapp.domain.Board}.
 */
@Service
@Transactional
public class BoardServiceImpl implements BoardService {

    private final Logger log = LoggerFactory.getLogger(BoardServiceImpl.class);

    private final BoardRepository boardRepository;

    private final BoardMapper boardMapper;

    public BoardServiceImpl(BoardRepository boardRepository, BoardMapper boardMapper) {
        this.boardRepository = boardRepository;
        this.boardMapper = boardMapper;
    }

    @Override
    public BoardDTO save(BoardDTO boardDTO) {
        log.debug("Request to save Board : {}", boardDTO);
        Board board = boardMapper.toEntity(boardDTO);
        board = boardRepository.save(board);
        return boardMapper.toDto(board);
    }

    @Override
    public BoardDTO update(BoardDTO boardDTO) {
        log.debug("Request to update Board : {}", boardDTO);
        Board board = boardMapper.toEntity(boardDTO);
        board = boardRepository.save(board);
        return boardMapper.toDto(board);
    }

    @Override
    public Optional<BoardDTO> partialUpdate(BoardDTO boardDTO) {
        log.debug("Request to partially update Board : {}", boardDTO);

        return boardRepository
            .findById(boardDTO.getId())
            .map(existingBoard -> {
                boardMapper.partialUpdate(existingBoard, boardDTO);

                return existingBoard;
            })
            .map(boardRepository::save)
            .map(boardMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BoardDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Boards");
        return boardRepository.findAll(pageable).map(boardMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<BoardDTO> findOne(Long id) {
        log.debug("Request to get Board : {}", id);
        return boardRepository.findById(id).map(boardMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Board : {}", id);
        boardRepository.deleteById(id);
    }
}
