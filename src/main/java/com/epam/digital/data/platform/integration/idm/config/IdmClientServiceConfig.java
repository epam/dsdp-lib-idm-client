/*
 * Copyright 2024-2026 EPAM Systems.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.epam.digital.data.platform.integration.idm.config;

import com.epam.digital.data.platform.integration.idm.factory.IdmServiceFactory;
import com.epam.digital.data.platform.integration.idm.mapper.IdmUsersMapper;
import jakarta.annotation.PostConstruct;
import org.keycloak.common.crypto.CryptoIntegration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ConditionalOnProperty(prefix = "keycloak", name = "url")
@Import({FeignClientsConfiguration.class, IdmComponentScanConfiguration.class})
public class IdmClientServiceConfig {

  @Bean
  public IdmServiceFactory idmServiceFactory(
      @Value("${keycloak.url}") String serverUrl,
      ApplicationContext applicationContext,
      IdmUsersMapper idmUsersMapper) {
    return new IdmServiceFactory(serverUrl, applicationContext, idmUsersMapper);
  }

  @PostConstruct
  public void initDefaultCryptoProvider() {
    CryptoIntegration.init(IdmClientServiceConfig.class.getClassLoader());
  }

}
