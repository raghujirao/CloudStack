package com.cloud.network.element;

import com.cloud.utils.component.PluggableService;

public interface DhcpElementService extends PluggableService{
    boolean configure();
}
