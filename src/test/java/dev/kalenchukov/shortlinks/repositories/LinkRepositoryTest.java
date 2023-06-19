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

package dev.kalenchukov.shortlinks.repositories;

import dev.kalenchukov.shortlinks.AppConfigTest;
import dev.kalenchukov.shortlinks.entities.Link;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlMergeMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.sql.DataSource;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Класс проверки методов класса {@link LinkRepository}.
 *
 * @author Алексей Каленчуков
 */
@SpringBootTest
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AppConfigTest.class)
@Sql(value = "/database/create-tables.sql")
@Sql(value = "/database/insert-table-links.sql")
@Sql(value = "/database/drop-tables.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@SqlMergeMode(SqlMergeMode.MergeMode.MERGE)
public class LinkRepositoryTest {
    /**
     * Источник данных.
     */
    @Autowired
    public DataSource dataSource;

    /**
     * Репозиторий для ссылок.
     */
    @Autowired
    public LinkRepository linkRepository;

    /**
     * Проверка метода {@link LinkRepository#getById(long)}.
     */
    @Test
    public void getById() throws SQLException {
        // подготовка
        Link expectedLink = new Link(
                1L,
                "https://kalenchukov.dev/shortlinks",
                Timestamp.from(Instant.now())
        );

        // выполнение
        Optional<Link> actualLink = this.linkRepository.getById(1L);

        // проверка
        assertThat(actualLink).isPresent().get().isEqualTo(expectedLink);
    }

    /**
     * Проверка метода {@link LinkRepository#getById(long)} с несуществующим ID ссылки.
     */
    @Test
    public void getByIdNotExistLinkId() throws SQLException {
        // подготовка
        // выполнение
        Optional<Link> actualLink = this.linkRepository.getById(0L);

        // проверка
        assertThat(actualLink).isNotPresent();
    }

    /**
     * Проверка метода {@link LinkRepository#save(Link)}.
     */
    @Test
    @Sql(value = "/database/drop-tables.sql")
    @Sql(value = "/database/create-tables.sql")
    public void save() throws SQLException {
        // подготовка
        Link link = Mockito.mock(Link.class);
        Mockito.when(link.getUrl()).thenReturn("https://kalenchukov.dev/shortlinks");
        Link expectedLink = new Link(
                1L,
                "https://kalenchukov.dev/shortlinks/help",
                Timestamp.from(Instant.now())
        );

        // выполнение
        Link actualLink = this.linkRepository.save(link);

        // проверка
        assertThat(actualLink).isEqualTo(expectedLink);
    }

    /**
     * Проверка метода {@link LinkRepository#save(Link)} с дублирующим URL.
     */
    @Test
    @Sql(value = "/database/drop-tables.sql")
    @Sql(value = "/database/create-tables.sql")
    public void saveDuplicateUrl() throws SQLException {
        // подготовка
        Link link1 = Mockito.mock(Link.class);
        Mockito.when(link1.getUrl()).thenReturn("https://kalenchukov.dev/shortlinks/help");
        Link link2 = Mockito.mock(Link.class);
        Mockito.when(link2.getUrl()).thenReturn("https://kalenchukov.dev/shortlinks/help");
        Link expectedLink1 = new Link(
                1L,
                "https://kalenchukov.dev/shortlinks/help",
                Timestamp.from(Instant.now())
        );
        Link expectedLink2 = new Link(
                2L,
                "https://kalenchukov.dev/shortlinks/help",
                Timestamp.from(Instant.now())
        );

        // выполнение
        Link actualLink1 = this.linkRepository.save(link1);
        Link actualLink2 = this.linkRepository.save(link2);

        // проверка
        assertThat(actualLink1).isEqualTo(expectedLink1);
        assertThat(actualLink2).isEqualTo(expectedLink2);
    }

    /**
     * Проверка метода {@link LinkRepository#size()}.
     */
    @Test
    public void size() throws SQLException {
        // подготовка
        long expectedSize = 3L;

        // выполнение
        long actualSize = this.linkRepository.size();

        // проверка
        assertThat(actualSize).isEqualTo(expectedSize);
    }

    /**
     * Проверка метода {@link LinkRepository#size()} с отсутствием ссылок.
     */
    @Test
    @Sql(value = "/database/truncate-table-links.sql")
    public void sizeIsEmpty() throws SQLException {
        // подготовка
        long expectedSize = 0L;

        // выполнение
        long actualSize = this.linkRepository.size();

        // проверка
        assertThat(actualSize).isEqualTo(expectedSize);
    }

    /**
     * Проверка метода {@link LinkRepository#deleteById(long)}.
     */
    @Test
    public void deleteById() throws SQLException {
        // подготовка
        // выполнение
        boolean actualDeleted = this.linkRepository.deleteById(3L);

        // проверка
        assertThat(actualDeleted).isTrue();
    }

    /**
     * Проверка метода {@link LinkRepository#deleteById(long)} с несуществующим ID ссылки.
     */
    @Test
    public void deleteByIdNotExist() throws SQLException {
        // подготовка
        // выполнение
        boolean actualDeleted = this.linkRepository.deleteById(0L);

        // проверка
        assertThat(actualDeleted).isFalse();
    }
}