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
package com.cloud.api.response;

import java.util.Date;

import com.cloud.api.ApiConstants;
import com.cloud.utils.IdentityProxy;
import com.cloud.event.Event;
import com.cloud.serializer.Param;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class EventResponse extends BaseResponse implements ControlledEntityResponse{
    @SerializedName(ApiConstants.ID) @Param(description="the ID of the event")
    private IdentityProxy id = new IdentityProxy("event");

    @SerializedName(ApiConstants.USERNAME) @Param(description="the name of the user who performed the action (can be different from the account if an admin is performing an action for a user, e.g. starting/stopping a user's virtual machine)")
    private String username;

    @SerializedName(ApiConstants.TYPE) @Param(description="the type of the event (see event types)")
    private String eventType;

    @SerializedName(ApiConstants.LEVEL) @Param(description="the event level (INFO, WARN, ERROR)")
    private String level;

    @SerializedName(ApiConstants.DESCRIPTION) @Param(description="a brief description of the event")
    private String description;

    @SerializedName(ApiConstants.ACCOUNT) @Param(description="the account name for the account that owns the object being acted on in the event (e.g. the owner of the virtual machine, ip address, or security group)")
    private String accountName;
    
    @SerializedName(ApiConstants.PROJECT_ID) @Param(description="the project id of the ipaddress")
    private IdentityProxy projectId = new IdentityProxy("projects");
    
    @SerializedName(ApiConstants.PROJECT) @Param(description="the project name of the address")
    private String projectName;

    @SerializedName(ApiConstants.DOMAIN_ID) @Param(description="the id of the account's domain")
    private IdentityProxy domainId = new IdentityProxy("domain");

    @SerializedName(ApiConstants.DOMAIN) @Param(description="the name of the account's domain")
    private String domainName;

    @SerializedName(ApiConstants.CREATED) @Param(description="the date the event was created")
    private Date created;

    @SerializedName(ApiConstants.STATE) @Param(description="the state of the event")
    private Event.State state;

    @SerializedName("parentid") @Param(description="whether the event is parented")
    private IdentityProxy parentId = new IdentityProxy("event");

    public void setId(Long id) {
        this.id.setValue(id);
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    @Override
    public void setDomainId(Long domainId) {
        this.domainId.setValue(domainId);
    }

    @Override
    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public void setState(Event.State state) {
        this.state = state;
    }

    public void setParentId(Long parentId) {
        this.parentId.setValue(parentId);
    }

    @Override
    public void setProjectId(Long projectId) {
        this.projectId.setValue(projectId);
    }

    @Override
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
}
