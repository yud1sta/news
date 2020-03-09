package com.bareksa.news.controller;

import com.bareksa.news.common.dto.*;
import com.bareksa.news.common.utils.PageConverter;
import com.bareksa.news.service.TagsService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@Tag(name = "Tags Api")
public class TagsController {
   TagsService tagsService;

   @Autowired
   public TagsController(TagsService tagsService) {
      this.tagsService = tagsService;
   }

   @PostMapping("/tags")
   public BaseResponse<TagsResponseDTO> createTags(@RequestBody @Valid TagsDTO request) {
      TagsResponseDTO tags = tagsService.save(request);

      return BaseResponse.created(tags);
   }

   @GetMapping("/tags/{id}")
   public BaseResponse<TagsResponseDTO> getOne(@PathVariable Integer id) {
      return BaseResponse.ok(tagsService.getOne(id));
   }

   @GetMapping("/tags")
   public BaseResponse<MyPage<TagsResponseDTO>> getAll(@Parameter(hidden = true) MyPageable pageable, @Parameter(hidden = true) TagsSearchDTO searchBy) {
      Page<TagsResponseDTO> res = tagsService.findAll(MyPageable.convertToPageable(pageable), searchBy);
      PageConverter<TagsResponseDTO> converter = new PageConverter<>();

      URI uri = MvcUriComponentsBuilder.fromMethodCall(
            MvcUriComponentsBuilder.on(getClass()).getAll(null, null))
            .build().toUri();

      MyPage<TagsResponseDTO> response = converter.convert(
            res,
            uri.toString(),
            searchBy
      );

      return BaseResponse.ok(response);
   }


   @DeleteMapping("/tags/{id}")
   public BaseResponse deleteTags(@PathVariable Integer id) {
      tagsService.delete(id);
      return BaseResponse.ok();
   }

   @PutMapping("/tags/{id}")
   public BaseResponse<TagsResponseDTO> updateTags(@RequestBody @Valid TagsDTO request, @PathVariable Integer id) {
      TagsResponseDTO tags = tagsService.update(id, request);
      return BaseResponse.ok(tags);
   }
}
