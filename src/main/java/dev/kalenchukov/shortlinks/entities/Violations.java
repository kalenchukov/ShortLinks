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
 * Класс нарушений.
 *
 * @author Алексей Каленчуков.
 */
@Schema(description = "Сущность нарушений")
public final class Violations {
    /**
     * Код.
     */
    @Schema(description = "Код", example = "123")
    private Integer code;

    /**
     * Нарушения.
     */
    @Schema(description = "Нарушения")
    private List<Violation> violations;

    /**
     * Конструктор для {@code Violations}.
     */
    public Violations() {
    }

    /**
     * Конструктор для {@code Violations}.
     *
     * @param httpStatus HTTP-статус.
     * @param violations коллекция нарушений.
     */
    public Violations(final HttpStatus httpStatus, final List<Violation> violations) {
        this.code = httpStatus.value();
        this.violations = violations;
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
     * Возвращает нарушения.
     *
     * @return нарушения.
     */
    public List<Violation> getViolations() {
        return this.violations;
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public String toString() {
        return "Violations{" +
                "code=" + this.getCode() + ", " +
                "violations=" + this.getViolations() +
                "}";
    }
}
