package com.xyzcorp.demos.designpatterns.mediator;

public class USInfoWidget implements Component {
    private boolean enabled;

    @Override
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
