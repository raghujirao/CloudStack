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
package com.cloud.agent.storage;

import java.util.List;

import com.cloud.agent.storage.KVMPhysicalDisk.PhysicalDiskFormat;
import com.cloud.storage.Storage.StoragePoolType;

public interface KVMStoragePool {
	public KVMPhysicalDisk createPhysicalDisk(String name,
			PhysicalDiskFormat format, long size);

	public KVMPhysicalDisk createPhysicalDisk(String name, long size);

	public KVMPhysicalDisk getPhysicalDisk(String volumeUuid);

	public boolean deletePhysicalDisk(String uuid);

	public List<KVMPhysicalDisk> listPhysicalDisks();

	public String getUuid();

	public long getCapacity();

	public long getUsed();

	public boolean refresh();

	public boolean isExternalSnapshot();

	public String getLocalPath();

	public StoragePoolType getType();

	public boolean delete();

	PhysicalDiskFormat getDefaultFormat();

	public boolean createFolder(String path);
}
