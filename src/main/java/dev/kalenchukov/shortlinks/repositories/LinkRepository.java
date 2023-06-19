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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Optional;

/**
 * Класс репозитория ссылок.
 *
 * @author Алексей Каленчуков
 */
@Repository
public class LinkRepository implements LinkRepositories {
    /**
     * Логгер.
     */
    private static final Logger LOG = LogManager.getLogger(LinkRepository.class);

    /**
     * Источник данных.
     */
    private final DataSource dataSource;

    /**
     * Контроллер для {@code LinkRepository}.
     *
     * @param dataSource источник данных.
     */
    @Autowired
    public LinkRepository(final DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * {@inheritDoc}
     *
     * @param linkId {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Override
    public Optional<Link> getById(final long linkId) throws SQLException {
        Optional<Link> link = Optional.empty();
        final String query = "SELECT * FROM links WHERE link_id = ?";

        LOG.debug("Выполнение запроса в базу данных для получения ссылки с ID " + linkId + ".");

        try (Connection connection = this.dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, linkId);
            preparedStatement.execute();

            try (ResultSet resultSet = preparedStatement.getResultSet()) {
                if (resultSet.next()) {
                    final Link linkEntity = new Link(
                            resultSet.getLong("link_id"),
                            resultSet.getString("url"),
                            resultSet.getTimestamp("date_create")
                    );

                    LOG.debug("Найдена ссылка " + linkEntity + ".");

                    link = Optional.of(linkEntity);
                } else {
                    LOG.debug("Ссылки с ID " + linkId + " не существует.");
                }
            }
        }

        LOG.debug("Выполнение запроса в базу данных прошло успешно.");

        return link;
    }

    /**
     * {@inheritDoc}
     *
     * @param link {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Override
    public Link save(final Link link) throws SQLException {
        long linkId = 0L;
        final String query = "INSERT INTO links (url) VALUES (?)";

        LOG.debug("Выполнение запроса в базу данных для добавления ссылки " + link + ".");

        try (Connection connection = this.dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, link.getUrl());
            preparedStatement.executeUpdate();

            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    linkId = resultSet.getLong(1);
                    LOG.debug("Добавлена ссылка с ID " + linkId + ".");
                } else {
                    LOG.debug("Не удалось получить ID новой записи.");
                }
            }
        }

        LOG.debug("Выполнение запроса в базу данных прошло успешно.");

        return this.getById(linkId).orElseThrow(
                () -> new SQLException("Не удалось получить новую запись по ID.")
        );
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public long size() throws SQLException {
        long size = 0L;
        final String query = "SELECT COUNT(*) FROM links";

        LOG.debug("Выполнение запроса в базу данных для получения количества ссылок.");

        try (Connection connection = this.dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.execute();

            try (ResultSet resultSet = preparedStatement.getResultSet()) {
                if (resultSet.next()) {
                    size = resultSet.getLong(1);
                } else {
                    LOG.debug("Не удалось получить количество записей.");
                }
            }
        }

        LOG.debug("Выполнение запроса в базу данных прошло успешно.");

        return size;
    }

    /**
     * {@inheritDoc}
     *
     * @param linkId {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Override
    public boolean deleteById(final long linkId) throws SQLException {
        boolean deleted = false;
        final String query = "DELETE FROM links WHERE link_id = ?";

        LOG.debug("Выполнение запроса в базу данных для удаления ссылки с ID " + linkId + ".");

        try (Connection connection = this.dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, linkId);
            final int countDeleted = preparedStatement.executeUpdate();

            if (countDeleted > 0) {
                deleted = true;
            } else {
                LOG.debug("Ссылки с ID " + linkId + " не существует.");
            }
        }

        LOG.debug("Выполнение запроса в базу данных прошло успешно.");

        return deleted;
    }
}
