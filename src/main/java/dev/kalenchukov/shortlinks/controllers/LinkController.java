/*
 * Copyright © 2023 Алексей Каленчуков
 * GitHub: https://github.com/kalenchukov
 * E-mail: mailto:aleksey.kalenchukov@yandex.ru
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package dev.kalenchukov.shortlinks.controllers;

import dev.kalenchukov.shortlinks.entities.Count;
import dev.kalenchukov.shortlinks.entities.Link;
import dev.kalenchukov.shortlinks.services.LinkServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

/**
 * Класс контроллера ссылок.
 *
 * @author Алексей Каленчуков
 */
@RestController()
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Ссылки")
public class LinkController {
    /**
     * Логгер.
     */
    private static final Logger LOG = LogManager.getLogger(LinkController.class);

    /**
     * Сервис для ссылок.
     */
    private final LinkServices linkService;

    /**
     * Контроллер для {@code LinkController}.
     *
     * @param linkService сервис для ссылок.
     */
    @Autowired
    public LinkController(final LinkServices linkService) {
        this.linkService = linkService;
    }

    /**
     * Возвращает количество ссылок.
     *
     * @return количество
     */
    @Operation(
            summary = "Получение количества ссылок",
            description = "Позволяет получить количество ссылок"
    )
    @ApiResponse(responseCode = "200")
    @GetMapping(path = "/count")
    public ResponseEntity<Count> count() {
        LOG.debug("Получен запрос на получение количества ссылок");

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(this.linkService.count());
    }

    /**
     * Переадресовывает по URL-адресу ссылки.
     *
     * @param linkId идентификатор ссылки.
     * @return пустой ответ.
     */
    @Operation(
            summary = "Перенаправление на URL-адреса ссылки",
            description = "Позволяет перенаправить запрос на URL-адрес ссылки"
    )
    @ApiResponse(responseCode = "302")
    @GetMapping(path = "/{linkId}")
    public ResponseEntity<Void> redirect(
            @Parameter(description = "Идентификатор ссылки", required = true)
            @PathVariable("linkId") final long linkId) {
        LOG.debug("Получен запрос на получение URL-адреса ссылки с ID " + linkId + ".");

        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(this.linkService.getUrl(linkId)))
                .build();
    }

    /**
     * Возвращает информацию о ссылке.
     *
     * @param linkId идентификатор ссылки.
     * @return ссылку.
     */
    @Operation(
            summary = "Получение информации о ссылке",
            description = "Позволяет получить информацию о ссылке"
    )
    @ApiResponse(responseCode = "200")
    @GetMapping(path = "/{linkId}/info")
    public ResponseEntity<Link> info(
            @Parameter(description = "Идентификатор ссылки", required = true)
            @PathVariable("linkId") final long linkId) {
        LOG.debug("Получен запрос на получение информации по ссылке с ID " + linkId + ".");

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(this.linkService.get(linkId));
    }

    /**
     * Добавляет ссылку.
     *
     * @param link ссылка.
     * @return ссылку.
     */
    @Operation(
            summary = "Добавление ссылки",
            description = "Позволяет добавить ссылку"
    )
    @ApiResponse(responseCode = "201")
    @PostMapping(path = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Link> add(
            @Parameter(description = "URL-адрес ссылки", required = true)
            @RequestBody final Link link) {
        LOG.debug("Получен запрос на добавление ссылки " + link + ".");

        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(this.linkService.add(link));
    }

    /**
     * Удаляет ссылку.
     *
     * @param linkId идентификатор ссылки.
     * @return пустой ответ.
     */
    @Operation(
            summary = "Удаление ссылки",
            description = "Позволяет удалить ссылку"
    )
    @ApiResponse(responseCode = "204")
    @DeleteMapping(path = "/{linkId}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "Идентификатор ссылки", required = true)
            @PathVariable("linkId") final long linkId) {
        LOG.debug("Получен запрос на удаление ссылки с ID " + linkId + ".");

        this.linkService.delete(linkId);

        return ResponseEntity.noContent()
                .build();
    }
}
