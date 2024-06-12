package com.kimhyunwook.mybatiscommunity.service;

import com.kimhyunwook.mybatiscommunity.dto.BoardDTO;
import com.kimhyunwook.mybatiscommunity.dto.BoardFileDTO;
import com.kimhyunwook.mybatiscommunity.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    public void save(BoardDTO boardDTO) throws IOException {
        // 파일이 없다면
        if (boardDTO.getBoardFile().get(0).isEmpty()) {
            boardDTO.setFileAttached(0);
            boardRepository.save(boardDTO);
        } else {
            // 파일이 있다면
            boardDTO.setFileAttached(1);
            // 게시글 저장 후, id값을 활용하기 위해 리턴 받는다.
            BoardDTO savedBoard = boardRepository.save(boardDTO);
            // 파일만 따로 가져온다.
            for (MultipartFile boardFile : boardDTO.getBoardFile()) {
                // 파일 이름 가져오기
                String originalFilename = boardFile.getOriginalFilename();
                // 저장용 이름 생성
                String storedFileName = System.currentTimeMillis() + "-" + originalFilename;

                // BoardFileDTO 세팅
                BoardFileDTO boardFileDTO = new BoardFileDTO();
                boardFileDTO.setOriginalFileName(originalFilename);
                boardFileDTO.setStoredFileName(storedFileName);
                boardFileDTO.setBoardId(savedBoard.getId());

                // 파일 저장용 폴더에 파일 저장 처리
                String filePath = "/Users/kimhyunwook/IdeaProjects/mybatis-community/upload_files/" + storedFileName;
                boardFile.transferTo(new File(filePath));

                // board_file_table에 저장 처리
                boardRepository.saveFile(boardFileDTO);
            }
        }
    }

    public List<BoardDTO> findAll() {
        return boardRepository.findAll();
    }

    public void updateHits(Long id) {
        boardRepository.updateHits(id);
    }

    public BoardDTO findById(Long id) {
        return boardRepository.findById(id);
    }

    public void update(BoardDTO boardDTO) {
        boardRepository.update(boardDTO);
    }

    public void delete(Long id) {
        boardRepository.delete(id);
    }

    public List<BoardFileDTO> findFile(Long id) {
        return boardRepository.findFile(id);
    }
}
