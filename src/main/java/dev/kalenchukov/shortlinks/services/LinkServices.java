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
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.validation.annotation.Validated;

/**
 * Интерфейс для реализации сервиса ссылок.
 *
 * @author Алексей Каленчуков
 */
@Validated
public interface LinkServices {
    /**
     * Возвращает ссылку.
     *
     * @param linkId идентификатор ссылки.
     * @return ссылку.
     */
    Link get(@Positive(message = "Идентификатор должен быть больше 0") long linkId);

    /**
     * Возвращает URL ссылки.
     *
     * @param linkId идентификатор ссылки.
     * @return URL ссылки.
     */
    String getUrl(@Positive(message = "Идентификатор должен быть больше 0") long linkId);

    /**
     * Добавляет ссылку.
     *
     * @param link ссылка.
     * @return ссылку.
     */
    Link add(@Valid Link link);

    /**
     * Возвращает количество ссылок.
     *
     * @return количество ссылок.
     */
    Count count();

    /**
     * Удаляет ссылку.
     *
     * @param linkId идентификатор ссылки.
     */
    void delete(@Positive(message = "Идентификатор должен быть больше 0") long linkId);
}
