package com.zhaofujun.nest.demo.adapter.event;

import com.zhaofujun.nest.context.event.EventArgs;
import com.zhaofujun.nest.core.EventHandler;
import com.zhaofujun.nest.demo.application.DemoEventData;
import org.springframework.stereotype.Component;

@Component
public class DemoEventHandle implements EventHandler<DemoEventData> {
    @Override
    public String getEventCode() {
        return DemoEventData.Code;
    }

    @Override
    public Class<DemoEventData> getEventDataClass() {
        return DemoEventData.class;
    }

    @Override
    public void handle(DemoEventData demoEventData, EventArgs eventArgs) {
        System.out.println(demoEventData.toString());
    }
}
