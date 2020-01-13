package com.zhaofujun.nest.demo.adapter.event;

import com.zhaofujun.nest.CustomException;
import com.zhaofujun.nest.SystemException;
import com.zhaofujun.nest.context.event.EventArgs;
import com.zhaofujun.nest.core.EventHandler;
import com.zhaofujun.nest.demo.contract.TestEventData;
import org.springframework.stereotype.Component;

@Component
public class DemoEventHandle implements EventHandler<TestEventData> {
    @Override
    public String getEventCode() {
        return TestEventData.Code;
    }

    @Override
    public Class<TestEventData> getEventDataClass() {
        return TestEventData.class;
    }

    @Override
    public void handle(TestEventData testEventData, EventArgs eventArgs) {
        System.out.println(testEventData.toString());
        throw new CustomException(1, "11") {
        };
    }
}
