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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Класс проверки методов класса {@link LinkController}.
 *
 * @author Алексей Каленчуков
 */
@ExtendWith(MockitoExtension.class)
public class LinkControllerTest {
    /**
     * Сервис для ссылок.
     */
    @Mock
    private LinkServices linkService;

    /**
     * Контроллер для ссылок.
     */
    @InjectMocks
    private LinkController linkController;

    /**
     * Проверка метода {@link LinkController#add(Link)}.
     */
    @Test
    public void add() {
        // подготовка
        Link link = Mockito.mock(Link.class);
        Link expectedLink = Mockito.mock(Link.class);
        Mockito.when(this.linkService.add(link)).thenReturn(expectedLink);

        // выполнение
        ResponseEntity<Link> responseEntity = this.linkController.add(link);
        HttpStatusCode actualHttpStatusCode = responseEntity.getStatusCode();
        MediaType actualMediaType = responseEntity.getHeaders().getContentType();
        Link actualLink = responseEntity.getBody();

        // проверка
        assertThat(actualHttpStatusCode).isEqualTo(HttpStatus.CREATED);
        assertThat(actualMediaType).isEqualTo(MediaType.APPLICATION_JSON);
        assertThat(actualLink).isEqualTo(expectedLink);
        Mockito.verify(this.linkService, Mockito.only()).add(link);
    }

    /**
     * Проверка метода {@link LinkController#count()}.
     */
    @Test
    public void count() {
        // подготовка
        Count expectedCount = Mockito.mock(Count.class);
        Mockito.when(this.linkService.count()).thenReturn(expectedCount);

        // выполнение
        ResponseEntity<Count> responseEntity = this.linkController.count();
        HttpStatusCode actualHttpStatusCode = responseEntity.getStatusCode();
        MediaType actualMediaType = responseEntity.getHeaders().getContentType();
        Count actualCount = responseEntity.getBody();

        // проверка
        assertThat(actualHttpStatusCode).isEqualTo(HttpStatus.OK);
        assertThat(actualMediaType).isEqualTo(MediaType.APPLICATION_JSON);
        assertThat(actualCount).isEqualTo(expectedCount);
        Mockito.verify(this.linkService, Mockito.only()).count();
    }

    /**
     * Проверка метода {@link LinkController#redirect(long)}.
     */
    @Test
    public void redirect() {
        // подготовка
        String url = "https://kalenchukov.dev/shortlinks";
        Mockito.when(this.linkService.getUrl(1L)).thenReturn(url);

        // выполнение
        ResponseEntity<Void> responseEntity = this.linkController.redirect(1L);
        HttpStatusCode actualHttpStatusCode = responseEntity.getStatusCode();
        URI actualLocation = responseEntity.getHeaders().getLocation();

        // проверка
        assertThat(actualHttpStatusCode).isEqualTo(HttpStatus.FOUND);
        assertThat(actualLocation).isEqualTo(URI.create(url));
        Mockito.verify(this.linkService, Mockito.only()).getUrl(1L);
    }

    /**
     * Проверка метода {@link LinkController#delete(long)}.
     */
    @Test
    public void delete() {
        // подготовка
        Mockito.doNothing().when(this.linkService).delete(1L);

        // выполнение
        ResponseEntity<Void> responseEntity = this.linkController.delete(1L);
        HttpStatusCode actualHttpStatusCode = responseEntity.getStatusCode();

        // проверка
        assertThat(actualHttpStatusCode).isEqualTo(HttpStatus.NO_CONTENT);
        Mockito.verify(this.linkService, Mockito.only()).delete(1L);
    }

    /**
     * Проверка метода {@link LinkController#info(long)}.
     */
    @Test
    public void info() {
        // подготовка
        Link expectedLink = Mockito.mock(Link.class);
        Mockito.when(this.linkService.get(1L)).thenReturn(expectedLink);

        // выполнение
        ResponseEntity<Link> responseEntity = this.linkController.info(1L);
        HttpStatusCode actualHttpStatusCode = responseEntity.getStatusCode();
        MediaType actualMediaType = responseEntity.getHeaders().getContentType();
        Link actualLink = responseEntity.getBody();

        // проверка
        assertThat(actualHttpStatusCode).isEqualTo(HttpStatus.OK);
        assertThat(actualMediaType).isEqualTo(MediaType.APPLICATION_JSON);
        assertThat(actualLink).isEqualTo(expectedLink);
        Mockito.verify(this.linkService, Mockito.only()).get(1L);
    }
}