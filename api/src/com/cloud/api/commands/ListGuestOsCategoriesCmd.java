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
import com.cloud.api.BaseListCmd;
import com.cloud.api.IdentityMapper;
import com.cloud.api.Implementation;
import com.cloud.api.Parameter;
import com.cloud.api.response.GuestOSCategoryResponse;
import com.cloud.api.response.ListResponse;
import com.cloud.storage.GuestOsCategory;

@Implementation(description="Lists all supported OS categories for this cloud.", responseObject=GuestOSCategoryResponse.class)
public class ListGuestOsCategoriesCmd extends BaseListCmd {
    public static final Logger s_logger = Logger.getLogger(ListIsosCmd.class.getName());

    private static final String s_name = "listoscategoriesresponse";

    /////////////////////////////////////////////////////
    //////////////// API parameters /////////////////////
    /////////////////////////////////////////////////////

    @IdentityMapper(entityTableName="guest_os_category")
    @Parameter(name=ApiConstants.ID, type=CommandType.LONG, description="list Os category by id")
    private Long id;
    
    @Parameter(name=ApiConstants.NAME, type=CommandType.STRING, description="list os category by name", since="3.0.1")
    private String name;


    /////////////////////////////////////////////////////
    /////////////////// Accessors ///////////////////////
    /////////////////////////////////////////////////////

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
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
        List<? extends GuestOsCategory> result = _mgr.listGuestOSCategoriesByCriteria(this);
        ListResponse<GuestOSCategoryResponse> response = new ListResponse<GuestOSCategoryResponse>();
        List<GuestOSCategoryResponse> osCatResponses = new ArrayList<GuestOSCategoryResponse>();
        for (GuestOsCategory osCategory : result) {
            GuestOSCategoryResponse categoryResponse = new GuestOSCategoryResponse();
            categoryResponse.setId(osCategory.getId());
            categoryResponse.setName(osCategory.getName());

            categoryResponse.setObjectName("oscategory");
            osCatResponses.add(categoryResponse);
        }

        response.setResponses(osCatResponses);
        response.setResponseName(getCommandName());
        this.setResponseObject(response);
    }
}
