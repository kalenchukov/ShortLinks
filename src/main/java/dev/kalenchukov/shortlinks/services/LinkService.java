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
import dev.kalenchukov.shortlinks.exceptions.ServerErrorException;
import dev.kalenchukov.shortlinks.exceptions.LinkNotFoundException;
import dev.kalenchukov.shortlinks.repositories.LinkRepositories;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

/**
 * Класс сервиса ссылок.
 *
 * @author Алексей Каленчуков
 */
@Service
public class LinkService implements LinkServices {
    /**
     * Логгер.
     */
    private static final Logger LOG = LogManager.getLogger(LinkService.class);

    /**
     * Репозиторий для ссылок.
     */
    private final LinkRepositories linkRepository;

    /**
     * Контроллер для {@code LinkService}.
     *
     * @param linkRepository репозиторий ссылок.
     */
    @Autowired
    public LinkService(final LinkRepositories linkRepository) {
        this.linkRepository = linkRepository;
    }

    /**
     * {@inheritDoc}
     *
     * @param linkId {@inheritDoc}
     * @return {@inheritDoc}
     * @throws LinkNotFoundException если ссылка с {@code linkId} не найдена.
     * @throws ServerErrorException  если произошла ошибка при работе с базой данных.
     */
    @Override
    public Link get(final long linkId) {
        LOG.debug("Выполнение логики для получения ссылки с ID " + linkId + ".");

        try {
            return this.linkRepository.getById(linkId).orElseThrow(
                    () -> new LinkNotFoundException("Ссылка не найдена.")
            );
        } catch (SQLException exception) {
            LOG.error("Ошибка при обращении к репозиторию.", exception);
            throw new ServerErrorException("Ошибка при работе с базой данных.");
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param linkId {@inheritDoc}
     * @return {@inheritDoc}
     * @throws LinkNotFoundException если ссылка с {@code linkId} не найдена.
     * @throws ServerErrorException  если произошла ошибка при работе с базой данных.
     */
    @Override
    public String getUrl(final long linkId) {
        LOG.debug("Выполнение логики для получения URL-адреса ссылки с ID " + linkId + ".");

        return this.get(linkId).getUrl();
    }

    /**
     * {@inheritDoc}
     *
     * @param link {@inheritDoc}
     * @return {@inheritDoc}
     * @throws ServerErrorException если произошла ошибка при работе с базой данных.
     */
    @Override
    public Link add(final Link link) {
        LOG.debug("Выполнение логики для добавления ссылки " + link + ".");

        try {
            return this.linkRepository.save(link);
        } catch (SQLException exception) {
            LOG.error("Ошибка при обращении к репозиторию.", exception);
            throw new ServerErrorException("Ошибка при работе с базой данных.");
        }
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     * @throws ServerErrorException если произошла ошибка при работе с базой данных.
     */
    @Override
    public Count count() {
        LOG.debug("Выполнение логики для получения количества ссылок.");

        try {
            return new Count(this.linkRepository.size());
        } catch (SQLException exception) {
            LOG.error("Ошибка при обращении к репозиторию.", exception);
            throw new ServerErrorException("Ошибка при работе с базой данных.");
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param linkId {@inheritDoc}
     * @throws LinkNotFoundException если ссылка с {@code linkId} не найдена.
     * @throws ServerErrorException  если произошла ошибка при работе с базой данных.
     */
    @Override
    public void delete(final long linkId) {
        LOG.debug("Выполнение логики для удаления ссылки с ID " + linkId + ".");

        try {
            if (!this.linkRepository.deleteById(linkId)) {
                throw new LinkNotFoundException("Ссылка не найдена.");
            }
        } catch (SQLException exception) {
            LOG.error("Ошибка при обращении к репозиторию.", exception);
            throw new ServerErrorException("Ошибка при работе с базой данных.");
        }
    }
}
