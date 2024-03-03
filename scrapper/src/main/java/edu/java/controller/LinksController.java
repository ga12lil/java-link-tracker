package edu.java.controller;

import edu.java.dto.links.AddLinkRequest;
import edu.java.dto.links.LinkResponse;
import edu.java.dto.links.ListLinksResponse;
import edu.java.dto.links.RemoveLinkRequest;
import java.util.ArrayList;
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
    public ListLinksResponse getAllLinks(@RequestHeader(value = "Tg-Chat-Id") Long tgChatId) {
        return new ListLinksResponse(new ArrayList<>(), 0);
    }

    @PostMapping
    public LinkResponse addLink(
            @RequestHeader(value = "Tg-Chat-Id") Long tgChatId,
            @RequestBody AddLinkRequest request) {
        return new LinkResponse(0L, request.link());
    }

    @DeleteMapping
    public LinkResponse removeLink(
            @RequestHeader(value = "Tg-Chat-Id") Long tgChatId,
            @RequestBody RemoveLinkRequest request) {
        return new LinkResponse(0L, request.link());
    }
}
