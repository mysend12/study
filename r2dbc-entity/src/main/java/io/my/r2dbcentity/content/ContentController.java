package io.my.r2dbcentity.content;

import io.my.r2dbcentity.content.entity.Content;
import io.my.r2dbcentity.content.payload.ContentPayload;
import io.my.r2dbcentity.convert.impl.ConvertWithMapstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/content")
public class ContentController {
    private final ContentRepository contentRepository;

    @GetMapping
    public Flux<ContentPayload> findAll() {
        return contentRepository.findAll()
                .map(ConvertWithMapstruct.INSTANCE::contentToContentPayload)
                ;
    }

    @PostMapping
    public Mono<ContentPayload> save(ContentPayload requestBody) {
        Content content = ConvertWithMapstruct.INSTANCE.contentPayloadToContent(requestBody);
        return contentRepository.save(content)
                .map(ConvertWithMapstruct.INSTANCE::contentToContentPayload)
                ;
    }

}
