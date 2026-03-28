/*
 * Copyright 2021 EPAM Systems.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.epam.digital.data.platform.integration.idm.service;

import com.epam.digital.data.platform.integration.idm.client.PublicKeycloakAuthClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.keycloak.common.crypto.CryptoIntegration;
import org.keycloak.representations.idm.PublishedRealmRepresentation;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.util.Base64;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PublicKeycloakIdmServiceTest {

  @Mock
  private PublicKeycloakAuthClient publicKeycloakAuthClient;

  private PublicKeycloakIdmService idmService;

  @BeforeEach
  void init() {
    CryptoIntegration.init(this.getClass().getClassLoader());
    idmService = new PublicKeycloakIdmService(publicKeycloakAuthClient);
  }

  @Test
  void getRealmRepresentation() throws NoSuchAlgorithmException {
    // Generate RSA key pair
    KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
    keyPairGenerator.initialize(2048);
    KeyPair keyPair = keyPairGenerator.generateKeyPair();
    PublicKey expectedPublicKey = keyPair.getPublic();

    // Convert to PEM format
    String publicKeyPem = Base64.getEncoder().encodeToString(expectedPublicKey.getEncoded());

    // Mock the response
    var realmRepresentation = mock(PublishedRealmRepresentation.class);
    when(realmRepresentation.getPublicKeyPem()).thenReturn(publicKeyPem);
    String realmName = "realmTest";
    when(publicKeycloakAuthClient.getRealmRepresentation(realmName)).thenReturn(realmRepresentation);

    // Execute
    var realm = idmService.getRealm(realmName);

    // Verify
    assertThat(realm.getPublicKey()).isNotNull();
    assertThat(realm.getPublicKey().getAlgorithm()).isEqualTo("RSA");
    assertThat(realm.getPublicKey().getEncoded()).isEqualTo(expectedPublicKey.getEncoded());
  }

}