// Copyright 2012 Citrix Systems, Inc. Licensed under the
// Apache License, Version 2.0 (the "License"); you may not use this
// file except in compliance with the License.  Citrix Systems, Inc.
// reserves all rights not expressly granted by the License.
// You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
// 
// Automatically generated by addcopyright.py at 04/03/2012
package com.cloud.network.ovs;

import com.cloud.agent.api.Command;

public class OvsCreateTunnelCommand extends Command {
    Integer key;
    String remoteIp;
    Long from;
    Long to;
    long networkId;

    // for debug info
    String fromIp;

    @Override
    public boolean executeInSequence() {
        return true;
    }

    public OvsCreateTunnelCommand(String remoteIp, Integer key, Long from, Long to, long networkId, String fromIp) {
        this.remoteIp = remoteIp;
        this.key = key;
        this.from = from;
        this.to = to;
        this.networkId = networkId;
        this.fromIp = fromIp;
    }

    public Integer getKey() {
        return key;
    }

    public String getRemoteIp() {
        return remoteIp;
    }

    public Long getFrom() {
        return from;
    }

    public Long getTo() {
        return to;
    }

    public long getNetworkId() {
        return networkId;
    }

    public String getFromIp() {
        return fromIp;
    }

}
