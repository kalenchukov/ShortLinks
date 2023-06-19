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

package dev.kalenchukov.shortlinks.services;

import dev.kalenchukov.shortlinks.entities.Count;
import dev.kalenchukov.shortlinks.entities.Link;
import dev.kalenchukov.shortlinks.exceptions.LinkNotFoundException;
import dev.kalenchukov.shortlinks.exceptions.ServerErrorException;
import dev.kalenchukov.shortlinks.repositories.LinkRepositories;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.SQLException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

/**
 * Класс проверки методов класса {@link LinkService}.
 *
 * @author Алексей Каленчуков
 */
@ExtendWith(MockitoExtension.class)
public class LinkServiceTest {
    /**
     * Репозиторий для ссылок.
     */
    @Mock
    private LinkRepositories linkRepository;

    /**
     * Сервис для ссылок.
     */
    @InjectMocks
    private LinkService linkService;

    /**
     * Проверка метода {@link LinkService#get(long)} .
     */
    @Test
    public void get() throws SQLException {
        // подготовка
        Link expectedLink = Mockito.mock(Link.class);
        Mockito.when(this.linkRepository.getById(1L)).thenReturn(Optional.of(expectedLink));

        // выполнение
        Link actualLink = this.linkService.get(1L);

        // проверка
        assertThat(actualLink).isEqualTo(expectedLink);
        Mockito.verify(this.linkRepository, Mockito.only()).getById(1L);
    }

    /**
     * Проверка метода {@link LinkService#get(long)} с несуществующим ID ссылки.
     */
    @Test
    public void getNotExistLinkId() throws SQLException {
        // подготовка
        Mockito.when(this.linkRepository.getById(Mockito.anyLong())).thenReturn(Optional.empty());

        // выполнение
        // проверка
        assertThatExceptionOfType(LinkNotFoundException.class).isThrownBy(() -> {
            this.linkService.get(1L);
        });
        Mockito.verify(this.linkRepository, Mockito.only()).getById(Mockito.anyLong());
    }

    /**
     * Проверка метода {@link LinkService#get(long)} при неработоспособной базе данных.
     */
    @Test
    public void getErrorDatabase() throws SQLException {
        // подготовка
        Mockito.when(this.linkRepository.getById(Mockito.anyLong())).thenThrow(SQLException.class);

        // выполнение
        // проверка
        assertThatExceptionOfType(ServerErrorException.class).isThrownBy(() -> {
            this.linkService.get(1L);
        });
        Mockito.verify(this.linkRepository, Mockito.only()).getById(Mockito.anyLong());
    }

    /**
     * Проверка метода {@link LinkService#getUrl(long)}.
     */
    @Test
    public void getUrl() throws SQLException {
        // подготовка
        String expectedUrl = "https://kalenchukov.dev/shortlinks";
        Link link = Mockito.mock(Link.class);
        Mockito.when(link.getUrl()).thenReturn(expectedUrl);
        Mockito.when(this.linkRepository.getById(1L)).thenReturn(Optional.of(link));

        // выполнение
        String actualUrl = this.linkService.getUrl(1L);

        // проверка
        assertThat(actualUrl).isEqualTo(expectedUrl);
        Mockito.verify(link, Mockito.only()).getUrl();
        Mockito.verify(this.linkRepository, Mockito.only()).getById(1L);
    }

    /**
     * Проверка метода {@link LinkService#getUrl(long)} с несуществующим ID ссылки.
     */
    @Test
    public void getUrlNotExistLinkId() throws SQLException {
        // подготовка
        Mockito.when(this.linkRepository.getById(Mockito.anyLong())).thenReturn(Optional.empty());

        // выполнение
        // проверка
        assertThatExceptionOfType(LinkNotFoundException.class).isThrownBy(() -> {
            this.linkService.getUrl(1L);
        });
        Mockito.verify(this.linkRepository, Mockito.only()).getById(Mockito.anyLong());
    }

    /**
     * Проверка метода {@link LinkService#getUrl(long)} при неработоспособной базе данных.
     */
    @Test
    public void getUrlErrorDatabase() throws SQLException {
        // подготовка
        Mockito.when(this.linkRepository.getById(Mockito.anyLong())).thenThrow(SQLException.class);

        // выполнение
        // проверка
        assertThatExceptionOfType(ServerErrorException.class).isThrownBy(() -> {
            this.linkService.getUrl(1L);
        });
        Mockito.verify(this.linkRepository, Mockito.only()).getById(Mockito.anyLong());
    }

    /**
     * Проверка метода {@link LinkService#add(Link)}.
     */
    @Test
    public void add() throws SQLException {
        // подготовка
        Link link = Mockito.mock(Link.class);
        Link expectedLink = Mockito.mock(Link.class);
        Mockito.when(this.linkRepository.save(link)).thenReturn(expectedLink);

        // выполнение
        Link actualLink = this.linkService.add(link);

        // проверка
        assertThat(actualLink).isEqualTo(expectedLink);
        Mockito.verify(this.linkRepository, Mockito.only()).save(link);
    }

    /**
     * Проверка метода {@link LinkService#add(Link)} при неработоспособной базе данных.
     */
    @Test
    public void addErrorDatabase() throws SQLException {
        // подготовка
        Link link = Mockito.mock(Link.class);
//		Mockito.when(link.getUrl()).thenReturn("https://kalenchukov.dev/shortlinks");
        Mockito.when(this.linkRepository.save(link)).thenThrow(SQLException.class);

        // выполнение
        // проверка
        assertThatExceptionOfType(ServerErrorException.class).isThrownBy(() -> {
            this.linkService.add(link);
        });
        Mockito.verify(this.linkRepository, Mockito.only()).save(link);
    }

    /**
     * Проверка метода {@link LinkService#count()}.
     */
    @Test
    public void count() throws SQLException {
        // подготовка
        Count expectedCount = Mockito.mock(Count.class);
        Mockito.when(expectedCount.getCount()).thenReturn(13L);
        Mockito.when(this.linkRepository.size()).thenReturn(13L);

        // выполнение
        Count actualCount = this.linkService.count();

        // проверка
        assertThat(actualCount).isEqualTo(expectedCount);
        Mockito.verify(this.linkRepository, Mockito.only()).size();
    }

    /**
     * Проверка метода {@link LinkService#count()} при неработоспособной базе данных.
     */
    @Test
    public void countErrorDatabase() throws SQLException {
        // подготовка
        Mockito.when(this.linkRepository.size()).thenThrow(SQLException.class);

        // выполнение
        // проверка
        assertThatExceptionOfType(ServerErrorException.class).isThrownBy(() -> {
            this.linkService.count();
        });
        Mockito.verify(this.linkRepository, Mockito.only()).size();
    }

    /**
     * Проверка метода {@link LinkService#delete(long)}.
     */
    @Test
    public void delete() throws SQLException {
        // подготовка
        Mockito.when(this.linkRepository.deleteById(1L)).thenReturn(true);

        // выполнение
        this.linkService.delete(1L);

        // проверка
        Mockito.verify(this.linkRepository, Mockito.only()).deleteById(1L);
    }

    /**
     * Проверка метода {@link LinkService#delete(long)} с несуществующим ID ссылки.
     */
    @Test
    public void deleteNotExistLinkId() throws SQLException {
        // подготовка
        Mockito.when(this.linkRepository.deleteById(Mockito.anyLong())).thenReturn(false);

        // выполнение
        // проверка
        assertThatExceptionOfType(LinkNotFoundException.class).isThrownBy(() -> {
            this.linkService.delete(1L);
        });
        Mockito.verify(this.linkRepository, Mockito.only()).deleteById(Mockito.anyLong());
    }

    /**
     * Проверка метода {@link LinkService#delete(long)} при неработоспособной базе данных.
     */
    @Test
    public void deleteErrorDatabase() throws SQLException {
        // подготовка
        Mockito.when(this.linkRepository.deleteById(Mockito.anyLong())).thenThrow(SQLException.class);

        // выполнение
        // проверка
        assertThatExceptionOfType(ServerErrorException.class).isThrownBy(() -> {
            this.linkService.delete(1L);
        });
        Mockito.verify(this.linkRepository, Mockito.only()).deleteById(Mockito.anyLong());
    }
}