package edu.java.controller;

import edu.java.dto.ApiErrorResponse;
import edu.java.exception.ChatNotFoundException;
import edu.java.service.ChatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/tg-chat/{id}")
public class TgChatController {
    private final ChatService chatService;
    @PostMapping
    @Operation(summary = "Зарегистрировать чат")
    @ApiResponse(responseCode = "200", description = "Чат зарегистрирован")
    @ApiResponse(
            responseCode = "400", description = "Некорректные параметры запроса",
            content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))
    )
    public void registerChat(@PathVariable Long id) {
        chatService.register(id);
    }

    @DeleteMapping
    @Operation(summary = "Удалить чат")
    @ApiResponse(responseCode = "200", description = "Чат удален")
    @ApiResponse(
            responseCode = "400", description = "Некорректные параметры запроса",
            content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))
    )
    @ApiResponse(
            responseCode = "404", description = "Чат с заданным id не найден",
            content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))
    )
    public void removeChat(@PathVariable Long id) throws ChatNotFoundException {
        chatService.unregister(id);
    }
}
