package com.filmfellows.cinemates.domain.main.model.mapper;

import com.filmfellows.cinemates.app.main.dto.boxOfficeDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MainMapper {
    List<boxOfficeDTO> selectBoxOfficeList();
}
