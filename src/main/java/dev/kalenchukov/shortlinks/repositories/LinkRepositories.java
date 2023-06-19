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

import dev.kalenchukov.shortlinks.entities.Link;

import java.sql.SQLException;
import java.util.Optional;

/**
 * Интерфейс для реализации репозитория ссылок.
 *
 * @author Алексей Каленчуков
 */
public interface LinkRepositories {
    /**
     * Возвращает ссылку по идентификатору.
     *
     * @param linkId идентификатор ссылки.
     * @return ссылка.
     * @throws SQLException при возникновении проблем с базой данных.
     */
    Optional<Link> getById(long linkId) throws SQLException;

    /**
     * Сохраняет ссылку.
     *
     * @param link ссылка.
     * @return ссылку.
     * @throws SQLException при возникновении проблем с базой данных.
     */
    Link save(Link link) throws SQLException;

    /**
     * Возвращает количество ссылок.
     *
     * @return количество ссылок.
     * @throws SQLException при возникновении проблем с базой данных.
     */
    long size() throws SQLException;

    /**
     * Удаляет ссылку по идентификатору.
     *
     * @param linkId идентификатор ссылки.
     * @return {@code true} если ссылка удалена, иначе {@code false}.
     * @throws SQLException при возникновении проблем с базой данных.
     */
    boolean deleteById(long linkId) throws SQLException;
}
