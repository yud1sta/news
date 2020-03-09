package com.bareksa.news.common.utils;

import com.bareksa.news.common.dto.MyPage;
import org.springframework.data.domain.Page;


public class PageConverter<T> {
    public MyPage<T> convert(Page<T> page, String baseUrl, Object searchBy) {
        MyPage<T> myPage = new MyPage<>();

        int currentPage = page.getPageable().getPageNumber() + 1;
        int perPage = page.getPageable().getPageSize();
        int lastPage = page.getTotalPages();
        long total = page.getTotalElements();

        long from = ((perPage * currentPage) - perPage) + 1;
        long to = perPage * currentPage;
        if (currentPage == lastPage) {
            to = total;
        }

        if (currentPage > lastPage) {
            from = 0;
            to = 0;
        }

        String prev = null;
        if (currentPage > 1 && currentPage <= lastPage) {
            int prevI = currentPage - 1;
            prev = baseUrl + "?page=" + prevI + "&size=" + page.getPageable().getPageSize()+searchBy.toString();
        }


        String next = null;
        if (currentPage < lastPage) {
            int nextI = currentPage + 1;
            next = baseUrl + "?page=" + nextI + "&size=" + page.getPageable().getPageSize()+searchBy.toString();
        }

        myPage.setCurrentPage(currentPage);
        myPage.setTotal(total);
        myPage.setPerPage(perPage);
        myPage.setLastPage(page.getTotalPages());
        myPage.setData(page.getContent());
        myPage.setFrom(from);
        myPage.setTo(to);
        myPage.setNextPageUrl(next);
        myPage.setPrevPageUrl(prev);

        return myPage;

    }

}