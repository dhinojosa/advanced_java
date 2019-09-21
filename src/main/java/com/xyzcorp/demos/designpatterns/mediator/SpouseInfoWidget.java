package com.xyzcorp.demos.designpatterns.mediator;

public class SpouseInfoWidget implements Component {
    private boolean enabled;

    @Override
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
