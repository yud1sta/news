package com.bareksa.news.controller;

import com.bareksa.news.common.dto.*;
import com.bareksa.news.common.utils.PageConverter;
import com.bareksa.news.service.NewsService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@Tag(name = "News Api")
public class NewsController {
   NewsService newsService;

   @Autowired
   public NewsController(NewsService newsService) {
      this.newsService = newsService;
   }

   @PostMapping("/news")
   public BaseResponse<NewsResponseDTO> createNews(@RequestBody @Valid NewsDTO request) {
      NewsResponseDTO news = newsService.save(request);
      return BaseResponse.created(news);
   }

   @GetMapping("/news/{id}")
   public BaseResponse<NewsResponseDTO> getOne(@PathVariable Integer id) {
      return BaseResponse.ok(newsService.getOne(id));
   }

   @GetMapping("/news")
   public BaseResponse<MyPage<NewsResponseDTO>> getAll(@Parameter(hidden = true) MyPageable pageable, @Parameter(hidden = true) NewsSearchDTO searchBy) {
      Page<NewsResponseDTO> res = newsService.findAll(MyPageable.convertToPageable(pageable), searchBy);
      PageConverter<NewsResponseDTO> converter = new PageConverter<>();

      URI uri = MvcUriComponentsBuilder.fromMethodCall(
            MvcUriComponentsBuilder.on(getClass()).getAll(null, null))
            .build().toUri();

      MyPage<NewsResponseDTO> response = converter.convert(
            res,
            uri.toString(),
            searchBy
      );

      return BaseResponse.ok(response);
   }


   @DeleteMapping("/news/{id}")
   public BaseResponse deleteNews(@PathVariable Integer id) {
      newsService.delete(id);
      return BaseResponse.ok();
   }

   @PutMapping("/news/{id}")
   public BaseResponse<NewsResponseDTO> updateNews(@RequestBody @Valid NewsDTO request, @PathVariable Integer id) {
      NewsResponseDTO news = newsService.update(id, request);
      return BaseResponse.ok(news);
   }
}
