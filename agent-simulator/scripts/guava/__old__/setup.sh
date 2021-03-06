#!/usr/bin/env bash
# Licensed to the Apache Software Foundation (ASF) under one
# or more contributor license agreements.  See the NOTICE file
# distributed with this work for additional information
# regarding copyright ownership.  The ASF licenses this file
# to you under the Apache License, Version 2.0 (the
# "License"); you may not use this file except in compliance
# with the License.  You may obtain a copy of the License at
# 
#   http://www.apache.org/licenses/LICENSE-2.0
# 
# Unless required by applicable law or agreed to in writing,
# software distributed under the License is distributed on an
# "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
# KIND, either express or implied.  See the License for the
# specific language governing permissions and limitations
# under the License.


 


#1. 1 host per cluster

zone_query="GET  http://10.91.30.219/client/?command=createZone&networktype=Advanced&securitygroupenabled=false&name=Go&dns1=4.2.2.2&internaldns1=4.2.2.2&vlan=500-1000&guestcidraddress=10.1.1.0%2F24	HTTP/1.0\n\n"
echo -e $zone_query | nc -v -q 120 10.91.30.219 8096

pod_query="GET  http://10.91.30.219/client/?command=createPod&zoneId=1&name=Guava&netmask=255.255.0.0&startIp=172.1.2.2&endIp=172.1.255.252&gateway=172.1.2.1	HTTP/1.0\n\n"
echo -e $pod_query | nc -v -q 120 10.91.30.219 8096

vlan_query="GET http://10.91.30.219/client/?command=createVlanIpRange&forVirtualNetwork=true&zoneId=1&vlan=untagged&gateway=172.2.1.1&netmask=255.255.0.0&startip=172.2.1.2&endip=172.2.255.254	HTTP/1.0\n\n"
echo -e $vlan_query | nc -v -q 120 10.91.30.219 8096

for name in `seq 1 100`
do
	cluster_query="GET	http://10.91.30.219/client/?command=addCluster&hypervisor=Simulator&clustertype=CloudManaged&zoneId=1&podId=1&clustername=CS$name	HTTP/1.0\n\n"
	echo -e $cluster_query | nc -v -q 120 10.91.30.219 8096

	host_query="GET	http://10.91.30.219/client/api?_=1302625706202&command=addHost&zoneId=1&podId=1&clusterid=$name&hypervisor=Simulator&clustertype=CloudManaged&hosttags=&username=sim&password=sim&url=http%3A%2F%2Fsim	HTTP/1.0\n\n"
	echo -e $host_query | nc -v -q 60 10.91.30.219 8096

	spool_query="GET	http://10.91.30.219/client/?command=createStoragePool&zoneId=1&podId=1&clusterid=$name&name=SPOOL$name&url=nfs://172.1.25.$name/export/share/$name   HTTP/1.0\n\n"
	echo -e $spool_query | nc -v -q 60 10.91.30.219 8096
done
