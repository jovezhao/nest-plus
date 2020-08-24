package com.zhaofujun.nest.demo.adapter.event;

import com.zhaofujun.nest.demo.contract.UserChangedEventData;
import com.zhaofujun.nest.standard.EventArgs;
import com.zhaofujun.nest.standard.EventHandler;
import org.springframework.stereotype.Component;

@Component
public class UserChangedEventHandler implements EventHandler<UserChangedEventData> {
    @Override
    public String getEventCode() {
        return UserChangedEventData.CODE;
    }

    @Override
    public Class<UserChangedEventData> getEventDataClass() {
        return UserChangedEventData.class;
    }

    @Override
    public void handle(UserChangedEventData userChangedEventData, EventArgs eventArgs) {
        System.out.println("handle:" + userChangedEventData.getUserName());
    }
}
