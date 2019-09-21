package com.xyzcorp.demos.designpatterns.state;

public class SwitchStateContext {
    private SwitchState switchState;

    public void setState(SwitchState switchState) {
        this.switchState = switchState;
    }

    public SwitchState getSwitchState() {
        return switchState;
    }

    public void next() {
        switchState.execute();
    }
}
