package com.bareksa.news.controller;

import com.bareksa.news.common.dto.*;
import com.bareksa.news.common.utils.PageConverter;
import com.bareksa.news.service.TopicService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@Tag(name = "Topic Api")
public class TopicController {
   TopicService topicService;

   @Autowired
   public TopicController(TopicService topicService) {
      this.topicService = topicService;
   }

   @PostMapping("/topic")
   public BaseResponse<TopicResponseDTO> createTopic(@RequestBody @Valid TopicDTO request) {
      TopicResponseDTO topic = topicService.save(request);
      return BaseResponse.created(topic);
   }

   @GetMapping("/topic/{id}")
   public BaseResponse<TopicResponseDTO> getOne(@PathVariable Integer id) {
      return BaseResponse.ok(topicService.getOne(id));
   }

   @GetMapping("/topic")
   public BaseResponse<MyPage<TopicResponseDTO>> getAll(MyPageable pageable,TopicSearchDTO searchBy) {
      Page<TopicResponseDTO> res = topicService.findAll(MyPageable.convertToPageable(pageable), searchBy);
      PageConverter<TopicResponseDTO> converter = new PageConverter<>();

      URI uri = MvcUriComponentsBuilder.fromMethodCall(
            MvcUriComponentsBuilder.on(getClass()).getAll(null, null))
            .build().toUri();

      MyPage<TopicResponseDTO> response = converter.convert(
            res,
            uri.toString(),
            searchBy
      );

      return BaseResponse.ok(response);
   }


   @DeleteMapping("/topic/{id}")
   public BaseResponse deleteLovCat(@PathVariable Integer id) {
      topicService.delete(id);
      return BaseResponse.ok();
   }

   @PutMapping("/topic/{id}")
   public BaseResponse<TopicResponseDTO> updateTopic(@RequestBody @Valid TopicDTO request, @PathVariable Integer id) {
      TopicResponseDTO topic = topicService.update(id, request);
      return BaseResponse.ok(topic);
   }
}
