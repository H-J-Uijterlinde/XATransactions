package com.semafoor.xa.web;

import com.semafoor.xa.services.XaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    private XaService xaService;

    public TestController(XaService xaService) {
        this.xaService = xaService;
    }

    @GetMapping("/1")
    public ResponseEntity<String> getExampleMessage() {
        return ResponseEntity.ok().body(xaService.returnString());
    }
}
