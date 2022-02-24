package id.go.beacukai.scswriter.infrastructure.adapter.web.controller;

import id.go.beacukai.scswriter.application.port.incoming.HeaderCommandService;
import id.go.beacukai.scswriter.domain.entity.Header;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequestMapping("/v2/headers")
public class HeaderCommandController {

    private final HeaderCommandService headerCommandService;

    public HeaderCommandController(HeaderCommandService headerCommandService) {
        this.headerCommandService = headerCommandService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Mono<Header> createNewHeader(@RequestBody @Valid Header header) {
        return headerCommandService.createDocumentHeader(header);
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<Header>> updateHeader(@RequestBody @Valid Header header, @PathVariable String id) {
        return Mono.empty();
    }
}
