/*
 * Copyright 2023 EPAM Systems.
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

package com.epam.digital.data.platform.integration.idm.resource;

import com.epam.digital.data.platform.integration.idm.model.SearchUsersByAttributesRequestDto;
import com.epam.digital.data.platform.integration.idm.model.SearchUsersByAttributesResponseDto;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.MediaType;

@Path("/realms/{realm}/users")
@Consumes(MediaType.APPLICATION_JSON)
public interface UsersExtendedResource {

  @POST
  @Path("/v2/search-by-attributes")
  @Consumes(MediaType.APPLICATION_JSON)
  SearchUsersByAttributesResponseDto searchUsersByAttributes(@PathParam("realm") String realm,
                                                             SearchUsersByAttributesRequestDto requestDto);
}