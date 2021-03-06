#!/usr/bin/env bash
# Licensed to the Apache Software Foundation (ASF) under one
# or more contributor license agreements.  See the NOTICE file
# distributed with this work for additional information
# regarding copyright ownership.  The ASF licenses this file
# to you under the Apache License, Version 2.0 (the
# "License"); you may not use this file except in compliance
# the License.  You may obtain a copy of the License at
#
# http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing,
# software distributed under the License is distributed on an
# "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
# KIND, either express or implied.  See the License for the
# specific language governing permissions and limitations
# under the License.


 

# run the usage server

PATHSEP=':'
if [[ $OSTYPE == "cygwin" ]]; then
  PATHSEP=';'
  export CATALINA_HOME=`cygpath -m $CATALINA_HOME`
fi

CP=./$PATHSEP$CATALINA_HOME/webapps/client/WEB-INF/lib/vmops-server.jar
CP=${CP}$PATHSEP$CATALINA_HOME/webapps/client/WEB-INF/lib/vmops-server-extras.jar
CP=${CP}$PATHSEP$CATALINA_HOME/webapps/client/WEB-INF/lib/vmops-utils.jar
CP=${CP}$PATHSEP$CATALINA_HOME/webapps/client/WEB-INF/lib/vmops-core.jar
CP=${CP}$PATHSEP$CATALINA_HOME/webapps/client/WEB-INF/lib/vmops-usage.jar
CP=${CP}$PATHSEP$CATALINA_HOME/conf

for file in $CATALINA_HOME/lib/*.jar; do
  CP=${CP}$PATHSEP$file
done

#echo CP is $CP
DEBUG_OPTS=
#DEBUG_OPTS=-Xrunjdwp:transport=dt_socket,address=$1,server=y,suspend=n

java -cp $CP $DEBUG_OPTS -Dcatalina.home=${CATALINA_HOME} -Dpid=$$ com.vmops.usage.UsageServer $*
