package id.go.beacukai.scswriter.infrastructure.adapter.web.controller;

import id.go.beacukai.scs.sharedkernel.domain.event.HeaderCreatedEvent;
import id.go.beacukai.scs.sharedkernel.domain.event.HeaderUpdatedEvent;
import id.go.beacukai.scswriter.application.port.incoming.HeaderCommandService;
import id.go.beacukai.scswriter.domain.entity.Header;
import id.go.beacukai.scswriter.infrastructure.adapter.web.dto.HeaderCreatedEventResponseModel;
import id.go.beacukai.scswriter.infrastructure.adapter.web.dto.HeaderUpdatedEventResponseModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/v2/headers")
public class HeaderCommandController {

    private final HeaderCommandService headerCommandService;

    public HeaderCommandController(HeaderCommandService headerCommandService) {
        this.headerCommandService = headerCommandService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Mono<HeaderCreatedEventResponseModel> createNewHeader(@RequestBody @Valid Header header) {
        var createdEvent = headerCommandService.createDocumentHeader(header);
        return createdEvent.map(event -> new HeaderCreatedEventResponseModel(event));
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<HeaderUpdatedEventResponseModel>> updateHeader(@RequestBody Header updatedHeader, @PathVariable String id) {
        var updatedEvent = headerCommandService.updateDocumentHeader(updatedHeader, id);
        return updatedEvent.map(event -> ResponseEntity.ok(new HeaderUpdatedEventResponseModel(event)))
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }
}
