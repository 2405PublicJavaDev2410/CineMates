package com.filmfellows.cinemates.domain.main.model.service;

import com.filmfellows.cinemates.app.main.dto.boxOfficeDTO;

import java.util.List;

public interface MainService {
    List<boxOfficeDTO> getBoxOfficeList();
}
