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

import com.cloud.utils.IdentityProxy;
import com.cloud.serializer.Param;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

public class ExceptionResponse extends BaseResponse {
	@SerializedName("uuidList") @Param(description="List of uuids associated with this error")
	private ArrayList<IdentityProxy> idList = new ArrayList<IdentityProxy>();
	
    @SerializedName("errorcode") @Param(description="numeric code associated with this error")
    private Integer errorCode;

    @SerializedName("cserrorcode") @Param(description="cloudstack exception error code associated with this error")
    private Integer csErrorCode;    
    
    @SerializedName("errortext") @Param(description="the text associated with this error")
    private String errorText = "Command failed due to Internal Server Error";

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorText() {
        return errorText;
    }

    public void setErrorText(String errorText) {
        this.errorText = errorText;
    }
	
	public void addProxyObject(String tableName, Long id, String idFieldName) {
		idList.add(new IdentityProxy(tableName, id, idFieldName));
		return;
	}
	
	public ArrayList<IdentityProxy> getIdProxyList() {
		return idList;
	}
	
	public void setCSErrorCode(int cserrcode) {
		this.csErrorCode = cserrcode;
	}
}
