package edu.java.controller;

import edu.java.dto.ApiErrorResponse;
import edu.java.dto.domain.LinkEntity;
import edu.java.dto.links.AddLinkRequest;
import edu.java.dto.links.LinkResponse;
import edu.java.dto.links.ListLinksResponse;
import edu.java.dto.links.RemoveLinkRequest;
import edu.java.dto.mapper.LinkMapper;
import edu.java.exception.LinkNotFoundException;
import edu.java.service.LinkService;
import io.micrometer.core.instrument.Counter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/links")
public class LinksController {
    private final LinkService linkService;
    private final LinkMapper linkMapper;
    private final Counter requestsCounter;

    @GetMapping
    @Operation(summary = "Получить отслеживаемые ссылки")
    @ApiResponse(
            responseCode = "200", description = "Ссылки получены",
            content = @Content(schema = @Schema(implementation = ListLinksResponse.class))
    )
    @ApiResponse(
            responseCode = "400", description = "Некорректные параметры запроса",
            content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))
    )
    public ListLinksResponse getAllLinks(@RequestHeader(value = "Tg-Chat-Id") Long tgChatId) {
        List<LinkEntity> listLinks = linkService.listAll(tgChatId);
        requestsCounter.increment();
        return linkMapper.toListLinksResponse(listLinks);
    }

    @PostMapping
    @Operation(summary = "Добавить ссылку в отслеживаемые")
    @ApiResponse(
            responseCode = "200", description = "Ссылка добавлена",
            content = @Content(schema = @Schema(implementation = LinkResponse.class))
    )
    @ApiResponse(
            responseCode = "400", description = "Некорректные параметры запроса",
            content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))
    )
    public LinkResponse addLink(
            @RequestHeader(value = "Tg-Chat-Id") Long tgChatId,
            @RequestBody AddLinkRequest request) {
        LinkEntity link = linkService.add(tgChatId, request.link());
        requestsCounter.increment();
        return linkMapper.toLinkResponse(link);
    }

    @DeleteMapping
    @Operation(summary = "Удалить ссылку из отслеживаемых")
    @ApiResponse(
            responseCode = "200", description = "Ссылка удалена",
            content = @Content(schema = @Schema(implementation = LinkResponse.class))
    )
    @ApiResponse(
            responseCode = "400", description = "Некорректные параметры запроса",
            content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))
    )
    @ApiResponse(
            responseCode = "404", description = "Ссылка ненайдена",
            content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))
    )
    public LinkResponse removeLink(
            @RequestHeader(value = "Tg-Chat-Id") Long tgChatId,
            @RequestBody RemoveLinkRequest request) throws LinkNotFoundException {
        LinkEntity linkEntity = linkService.remove(tgChatId, request.link());
        requestsCounter.increment();
        return linkMapper.toLinkResponse(linkEntity);
    }
}
