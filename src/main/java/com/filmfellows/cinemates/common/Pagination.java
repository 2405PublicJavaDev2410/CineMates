package com.filmfellows.cinemates.common;

import lombok.*;

@Getter
@Setter
//@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Pagination {
    private int currentPage; // 현재 페이지 번호
    private int totalCount; // 전체 게시물의 갯수

    private int boardLimit; // 한 페이지당 보여줄 게시물의 수
    private int naviLimit = 10; // 한 페이지당 보여줄 페이지번호의 수

    private int maxPage;
    private int startNavi;
    private int endNavi;

    private int prevPage;
    private int nextPage;

    public Pagination() {
    }

    public Pagination(int totalCount, int currentPage, int boardLimit) {
        this.totalCount = totalCount;
        this.currentPage = currentPage;
        this.boardLimit = boardLimit;
        makePagination();
    }

//    public void makePagination() {
//        maxPage = (int) Math.ceil((double) totalCount / boardLimit);
//        startNavi = ((currentPage - 1) / naviLimit) * naviLimit + 1;
//        endNavi = startNavi + naviLimit - 1;
//        endNavi = (endNavi > maxPage) ? maxPage : endNavi;
//
//        prevPage = (startNavi > 1) ? startNavi - 1 : 1;
//        nextPage = (endNavi < maxPage) ? endNavi + 1 : maxPage;
//    }
    public void makePagination() {
        maxPage = (int) Math.ceil((double) totalCount / boardLimit);
        startNavi = ((currentPage - 1) / naviLimit) * naviLimit + 1;
        endNavi = startNavi + naviLimit - 1;
        if (endNavi > maxPage) {
            endNavi = maxPage;
        }
        prevPage = (startNavi > 1) ? startNavi - 1 : 1;
        nextPage = (endNavi < maxPage) ? endNavi + 1 : maxPage;
    }

}
