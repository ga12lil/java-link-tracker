package edu.java.controller;

import edu.java.dto.ApiErrorResponse;
import edu.java.dto.links.AddLinkRequest;
import edu.java.dto.links.LinkResponse;
import edu.java.dto.links.ListLinksResponse;
import edu.java.dto.links.RemoveLinkRequest;
import java.util.ArrayList;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/links")
public class LinksController {
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
        return new ListLinksResponse(new ArrayList<>(), 0);
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
        return new LinkResponse(0L, request.link());
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
            @RequestBody RemoveLinkRequest request) {
        return new LinkResponse(0L, request.link());
    }
}
