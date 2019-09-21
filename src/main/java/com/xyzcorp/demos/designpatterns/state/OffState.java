package com.xyzcorp.demos.designpatterns.state;

public class OffState implements SwitchState {
    private SwitchStateContext context;

    public OffState(SwitchStateContext context) {
        this.context = context;
    }

    @Override
    public void execute() {
        context.setState(new OnState(context));
    }

}
