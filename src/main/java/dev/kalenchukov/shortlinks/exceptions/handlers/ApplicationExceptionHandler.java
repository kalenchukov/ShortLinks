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

package dev.kalenchukov.shortlinks.exceptions.handlers;

import dev.kalenchukov.shortlinks.entities.Error;
import dev.kalenchukov.shortlinks.entities.Errors;
import dev.kalenchukov.shortlinks.entities.Violation;
import dev.kalenchukov.shortlinks.entities.Violations;
import dev.kalenchukov.shortlinks.exceptions.EntityNotFoundException;
import dev.kalenchukov.shortlinks.exceptions.ServerErrorException;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Path;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.Objects;

/**
 * Класс обработчика исключений приложения.
 *
 * @author Алексей Каленчуков
 */
@RestControllerAdvice
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {
    /**
     * Логгер.
     */
    private static final Logger LOG = LogManager.getLogger(ApplicationExceptionHandler.class);

    /**
     * Контроллер для {@code ApplicationExceptionHandler}.
     */
    public ApplicationExceptionHandler() {
        super();
    }

    /**
     * Возвращает ошибку не найденной сущности.
     *
     * @param exception исключение.
     * @return ошибки.
     */
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Errors> handleNotFoundException(final EntityNotFoundException exception) {
        final Error error = new Error(exception.getLocalizedMessage());
        final Errors errors = new Errors(
                HttpStatus.NOT_FOUND,
                List.of(error)
        );

        LOG.debug("Возврат HTTP-ответа: " + errors);

        return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);
    }

    /**
     * Возвращает ошибку сервера.
     *
     * @param exception исключение.
     * @return ошибки.
     */
    @ExceptionHandler(ServerErrorException.class)
    public ResponseEntity<Errors> handleServerErrorException(final ServerErrorException exception) {
        final Error error = new Error(exception.getLocalizedMessage());
        final Errors errors = new Errors(
                HttpStatus.INTERNAL_SERVER_ERROR,
                List.of(error)
        );

        LOG.debug("Возврат HTTP-ответа: " + errors);

        return new ResponseEntity<>(errors, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Возвращает ошибку параметра.
     *
     * @param exception исключение.
     * @return нарушения.
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Violations> handleConstraintViolationException(final ConstraintViolationException exception) {
        final List<Violation> violationResponse = exception.getConstraintViolations().stream()
                .map(violation -> new Violation(this.getNameFieldFromPath(violation.getPropertyPath()), violation.getMessage()))
                .toList();

        final Violations violations = new Violations(
                HttpStatus.BAD_REQUEST,
                violationResponse
        );

        LOG.debug("Возврат HTTP-ответа: " + violations);

        return new ResponseEntity<>(violations, HttpStatus.BAD_REQUEST);
    }

    /**
     * Возвращает ошибку параметра.
     *
     * @param exception исключение.
     * @param headers   заголовки.
     * @param status    статус.
     * @param request   запрос.
     * @return нарушения.
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException exception,
                                                                  final HttpHeaders headers,
                                                                  final HttpStatusCode status,
                                                                  final WebRequest request) {
        final Violation violation = new Violation(
                Objects.requireNonNull(exception.getFieldError()).getField(),
                Objects.requireNonNull(exception.getFieldError()).getDefaultMessage()
        );

        final Violations violations = new Violations(
                HttpStatus.BAD_REQUEST,
                List.of(violation)
        );

        LOG.debug("Возврат HTTP-ответа: " + violations);

        return new ResponseEntity<>(violations, HttpStatus.BAD_REQUEST);
    }

    /**
     * Возвращает название поля класса из пути.
     *
     * @param path путь.
     * @return название поля класса.
     */
    private String getNameFieldFromPath(final Path path) {
        String nameField = null;

        for (Path.Node node : path) {
            nameField = node.getName();
        }

        return nameField;
    }
}
