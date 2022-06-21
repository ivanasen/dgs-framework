/*
 * Copyright 2021 Netflix, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.netflix.graphql.dgs.webflux.autoconfiguration

import com.netflix.graphql.dgs.autoconfig.DgsAutoConfiguration
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.reactive.config.EnableWebFlux

@AutoConfigureWebTestClient
@EnableWebFlux
@SpringBootTest(
    classes = [DgsWebFluxAutoConfiguration::class, DgsAutoConfiguration::class, WebRequestTestWithCustomEndpoint.ExampleImplementation::class],
    properties = ["dgs.graphql.graphiql.title=Custom GraphiQL Title"]
)
class GraphiQlCustomTitle {
    @Autowired
    lateinit var webTestClient: WebTestClient

    @Test
    fun customGraphiQlTitle() {
        webTestClient.get().uri("/graphiql/index.html")
            .exchange()
            .expectStatus()
            .isOk
            .expectBody()
            .toString()
            .contains("Custom GraphiQL Title")
    }
}
