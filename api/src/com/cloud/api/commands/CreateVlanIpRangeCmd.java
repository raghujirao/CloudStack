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

import org.apache.log4j.Logger;

import com.cloud.api.ApiConstants;
import com.cloud.api.BaseCmd;
import com.cloud.api.IdentityMapper;
import com.cloud.api.Implementation;
import com.cloud.api.Parameter;
import com.cloud.api.ServerApiException;
import com.cloud.api.response.VlanIpRangeResponse;
import com.cloud.dc.Vlan;
import com.cloud.exception.ConcurrentOperationException;
import com.cloud.exception.InsufficientCapacityException;
import com.cloud.exception.ResourceAllocationException;
import com.cloud.exception.ResourceUnavailableException;
import com.cloud.user.Account;

@Implementation(description="Creates a VLAN IP range.", responseObject=VlanIpRangeResponse.class)
public class CreateVlanIpRangeCmd extends BaseCmd {
	public static final Logger s_logger = Logger.getLogger(CreateVlanIpRangeCmd.class.getName());

    private static final String s_name = "createvlaniprangeresponse";

    /////////////////////////////////////////////////////
    //////////////// API parameters /////////////////////
    /////////////////////////////////////////////////////

    @Parameter(name=ApiConstants.ACCOUNT, type=CommandType.STRING, description="account who will own the VLAN. If VLAN is Zone wide, this parameter should be ommited")
    private String accountName;
    
    @IdentityMapper(entityTableName="projects")
    @Parameter(name=ApiConstants.PROJECT_ID, type=CommandType.LONG, description="project who will own the VLAN. If VLAN is Zone wide, this parameter should be ommited")
    private Long projectId;

    @IdentityMapper(entityTableName="domain")
    @Parameter(name=ApiConstants.DOMAIN_ID, type=CommandType.LONG, description="domain ID of the account owning a VLAN")
    private Long domainId;

    @Parameter(name=ApiConstants.END_IP, type=CommandType.STRING, description="the ending IP address in the VLAN IP range")
    private String endIp;

    @Parameter(name=ApiConstants.FOR_VIRTUAL_NETWORK, type=CommandType.BOOLEAN, description="true if VLAN is of Virtual type, false if Direct")
    private Boolean forVirtualNetwork;

    @Parameter(name=ApiConstants.GATEWAY, type=CommandType.STRING, description="the gateway of the VLAN IP range")
    private String gateway;

    @Parameter(name=ApiConstants.NETMASK, type=CommandType.STRING, description="the netmask of the VLAN IP range")
    private String netmask;

    @IdentityMapper(entityTableName="host_pod_ref")
    @Parameter(name=ApiConstants.POD_ID, type=CommandType.LONG, description="optional parameter. Have to be specified for Direct Untagged vlan only.")
    private Long podId;

    @Parameter(name=ApiConstants.START_IP, type=CommandType.STRING, required=true, description="the beginning IP address in the VLAN IP range")
    private String startIp;

    @Parameter(name=ApiConstants.VLAN, type=CommandType.STRING, description="the ID or VID of the VLAN. If not specified," +
    		" will be defaulted to the vlan of the network or if vlan of the network is null - to Untagged")
    private String vlan;

    @IdentityMapper(entityTableName="data_center")
    @Parameter(name=ApiConstants.ZONE_ID, type=CommandType.LONG, description="the Zone ID of the VLAN IP range")
    private Long zoneId;
    
    @IdentityMapper(entityTableName="networks")
    @Parameter(name=ApiConstants.NETWORK_ID, type=CommandType.LONG, description="the network id")
    private Long networkID;

    @IdentityMapper(entityTableName="physical_network")
    @Parameter(name=ApiConstants.PHYSICAL_NETWORK_ID, type=CommandType.LONG, description="the physical network id")
    private Long physicalNetworkId;
    
    /////////////////////////////////////////////////////
    /////////////////// Accessors ///////////////////////
    /////////////////////////////////////////////////////

    public String getAccountName() {
        return accountName;
    }

    public Long getDomainId() {
        return domainId;
    }

    public String getEndIp() {
        return endIp;
    }

    public Boolean isForVirtualNetwork() {
        return forVirtualNetwork == null ? true : forVirtualNetwork;
    }

    public String getGateway() {
        return gateway;
    }

    public String getNetmask() {
        return netmask;
    }

    public Long getPodId() {
        return podId;
    }

    public String getStartIp() {
        return startIp;
    }

    public String getVlan() {
        return vlan;
    }

    public Long getZoneId() {
        return zoneId;
    }

    public Long getProjectId() {
        return projectId;
    }
    /////////////////////////////////////////////////////
    /////////////// API Implementation///////////////////
    /////////////////////////////////////////////////////

    public Long getNetworkID() {
        return networkID;
    }

    public Long getPhysicalNetworkId() {
        return physicalNetworkId;
    }

    
    @Override
    public String getCommandName() {
        return s_name;
    }
    
    @Override
    public long getEntityOwnerId() {
        return Account.ACCOUNT_ID_SYSTEM;
    }
    
    @Override
    public void execute() throws ResourceUnavailableException, ResourceAllocationException{
        try {
            Vlan result = _configService.createVlanAndPublicIpRange(this);
            if (result != null) {
                VlanIpRangeResponse response = _responseGenerator.createVlanIpRangeResponse(result);
                response.setResponseName(getCommandName());
                this.setResponseObject(response);
            }else {
                throw new ServerApiException(BaseCmd.INTERNAL_ERROR, "Failed to create vlan ip range");
            }
        } catch (ConcurrentOperationException ex) {
            s_logger.warn("Exception: ", ex);
            throw new ServerApiException(BaseCmd.INTERNAL_ERROR, ex.getMessage());
        } catch (InsufficientCapacityException ex) {
            s_logger.info(ex);
            throw new ServerApiException(BaseCmd.INSUFFICIENT_CAPACITY_ERROR, ex.getMessage());
        } 
    }
}
