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

package dev.kalenchukov.shortlinks;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Класс конфигурации Swagger.
 *
 * @author Алексей Каленчуков
 */
@Configuration
public class SwaggerConfig {
    /**
     * Конфигурация Swagger.
     *
     * @param info   информация о проекте.
     * @param server данные об основном сервере.
     * @return конфигурацию.
     */
    @Bean
    public OpenAPI openAPI(final Info info,
                           final Server server) {
        return new OpenAPI()
                .info(info)
                .addServersItem(server);
    }

    /**
     * Возвращает контакт по вопросам API проекта.
     *
     * @return контакт.
     */
    @Bean
    public Contact contact() {
        return new Contact()
                .name("Алексей Каленчуков")
                .email("aleksey.kalenchukov@yandex.ru")
                .url("https://github.com/kalenchukov");
    }

    /**
     * Возвращает лицензию проекта.
     *
     * @return лицензия.
     */
    @Bean
    public License license() {
        return new License()
                .name("MIT License")
                .url("https://opensource.org/licenses/MIT");
    }

    /**
     * Возвращает информацию о проекте.
     *
     * @param contact контакт по вопросам API.
     * @param license лицензия проекта.
     * @return информацию о проекте.
     */
    @Bean
    public Info info(final Contact contact, final License license) {
        return new Info()
                .title("ShortLinks API")
                .description("Сервис коротких ссылок")
                .version("1.0.0")
                .contact(contact)
                .license(license);
    }

    /**
     * Возвращает информацию основного сервера.
     *
     * @return информацию основного сервера.
     */
    @Bean
    public Server server() {
        return new Server()
                .url("http://localhost:8080");
    }
}
