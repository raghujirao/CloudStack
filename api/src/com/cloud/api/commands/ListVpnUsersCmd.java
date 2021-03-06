// Licensed to the Apache Software Foundation (ASF) under one
// or more contributor license agreements.  See the NOTICE file
// distributed with this work for additional information
// regarding copyright ownership.  The ASF licenses this file
// to you under the Apache License, Version 2.0 (the
// "License"); you may not use this file except in compliance
// with the License.  You may obtain a copy of the License at
//
//   http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing,
// software distributed under the License is distributed on an
// "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
// KIND, either express or implied.  See the License for the
// specific language governing permissions and limitations
// under the License.
package com.cloud.api.commands;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.cloud.api.ApiConstants;
import com.cloud.api.BaseListProjectAndAccountResourcesCmd;
import com.cloud.api.IdentityMapper;
import com.cloud.api.Implementation;
import com.cloud.api.Parameter;
import com.cloud.api.response.ListResponse;
import com.cloud.api.response.VpnUsersResponse;
import com.cloud.network.VpnUser;

@Implementation(description="Lists vpn users", responseObject=VpnUsersResponse.class)
public class ListVpnUsersCmd extends BaseListProjectAndAccountResourcesCmd {
    public static final Logger s_logger = Logger.getLogger (ListVpnUsersCmd.class.getName());

    private static final String s_name = "listvpnusersresponse";

    /////////////////////////////////////////////////////
    //////////////// API parameters /////////////////////
    /////////////////////////////////////////////////////
    @IdentityMapper(entityTableName="vpn_users")
    @Parameter(name=ApiConstants.ID, type=CommandType.LONG, description="the ID of the vpn user")
    private Long id;

    @Parameter(name=ApiConstants.USERNAME, type=CommandType.STRING, description="the username of the vpn user.")
    private String userName;
    
    /////////////////////////////////////////////////////
    /////////////////// Accessors ///////////////////////
    /////////////////////////////////////////////////////

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return userName;
    }

    /////////////////////////////////////////////////////
    /////////////// API Implementation///////////////////
    /////////////////////////////////////////////////////

    @Override
    public String getCommandName() {
        return s_name;
    }
    
    @Override
    public void execute(){
        List<? extends VpnUser> vpnUsers = _ravService.searchForVpnUsers(this);

        ListResponse<VpnUsersResponse> response = new ListResponse<VpnUsersResponse>();
        List<VpnUsersResponse> vpnResponses = new ArrayList<VpnUsersResponse>();
        for (VpnUser vpnUser : vpnUsers) {
            vpnResponses.add(_responseGenerator.createVpnUserResponse(vpnUser));
        }

        response.setResponses(vpnResponses);
        response.setResponseName(getCommandName());
        this.setResponseObject(response);
    }
}
