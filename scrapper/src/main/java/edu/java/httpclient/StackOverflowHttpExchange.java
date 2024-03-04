package edu.java.httpclient;

import edu.java.dto.httpclient.StackExchangeResponse;
import java.util.Map;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange(url = "/{apiVersion}/questions/{id}")
public interface StackOverflowHttpExchange {
    @GetExchange
    StackExchangeResponse fetchQuestion(@PathVariable Long id,
                                        @PathVariable String apiVersion,
                                        @RequestParam Map<String, String> params);
}
