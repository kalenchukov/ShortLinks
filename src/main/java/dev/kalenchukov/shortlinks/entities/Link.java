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

package dev.kalenchukov.shortlinks.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Positive;
import org.hibernate.validator.constraints.URL;
import org.springframework.validation.annotation.Validated;

import java.sql.Timestamp;
import java.util.Objects;

/**
 * Класс ссылки.
 *
 * @author Алексей Каленчуков.
 */
@Schema(description = "Сущность ссылки")
@Validated
public final class Link {
    /**
     * Идентификатор.
     */
    @Schema(description = "Идентификатор", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    @Positive(message = "Идентификатор должен быть больше 0")
    private Long linkId;

    /**
     * URL-адрес.
     */
    @Schema(description = "URL-адрес", example = "https://kalenchukov.dev/shortlinks")
    @URL(message = "URl-адрес должен быть корректным")
    private String url;

    /**
     * Дата создания.
     */
    @Schema(description = "Дата добавления", example = "2023-06-16T09:59:54.075+00:00", accessMode = Schema.AccessMode.READ_ONLY)
    private Timestamp dateCreate;

    /**
     * Конструктор для {@code Link}.
     */
    public Link() {
    }

    /**
     * Конструктор для {@code Link}.
     *
     * @param linkId     идентификатор.
     * @param url        URL-адрес.
     * @param dateCreate дата создания.
     */
    public Link(final Long linkId, final String url, final Timestamp dateCreate) {
        this.linkId = linkId;
        this.url = url;
        this.dateCreate = dateCreate;
    }

    /**
     * Возвращает идентификатор.
     *
     * @return идентификатор.
     */
    public Long getLinkId() {
        return this.linkId;
    }

    /**
     * Возвращает URL-адрес.
     *
     * @return URL-адрес.
     */
    public String getUrl() {
        return this.url;
    }

    /**
     * Возвращает дату добавления.
     *
     * @return дата добавления.
     */
    public Timestamp getDateCreate() {
        return this.dateCreate;
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public String toString() {
        return "Link{" +
                "linkId=" + this.getLinkId() + ", " +
                "url='" + this.getUrl() + "', " +
                "dateCreate=" + this.getDateCreate() +
                "}";
    }

    /**
     * {@inheritDoc}
     *
     * @param obj {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Link link)) {
            return false;
        }

        if (!Objects.equals(this.getLinkId(), link.getLinkId())) {
            return false;
        }

        return true;
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Long.hashCode(this.getLinkId());
    }
}
