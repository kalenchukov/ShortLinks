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
import org.springframework.http.HttpStatus;

import java.util.List;

/**
 * Класс ошибок.
 *
 * @author Алексей Каленчуков.
 */
@Schema(description = "Сущность ошибок")
public final class Errors {
    /**
     * Код.
     */
    @Schema(description = "Код", example = "123")
    private Integer code;

    /**
     * Сообщение.
     */
    @Schema(description = "Ошибки")
    private List<Error> errors;

    /**
     * Конструктор для {@code Errors}.
     */
    public Errors() {
    }

    /**
     * Конструктор для {@code Errors}.
     *
     * @param httpStatus HTTP-статус.
     * @param errors     коллекция ошибок.
     */
    public Errors(final HttpStatus httpStatus, final List<Error> errors) {
        this.code = httpStatus.value();
        this.errors = errors;
    }

    /**
     * Возвращает код.
     *
     * @return код.
     */
    public Integer getCode() {
        return this.code;
    }

    /**
     * Возвращает ошибки.
     *
     * @return ошибки.
     */
    public List<Error> getErrors() {
        return this.errors;
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public String toString() {
        return "Errors{" +
                "code=" + this.getCode() + ", " +
                "errors=" + this.getErrors() +
                "}";
    }
}
