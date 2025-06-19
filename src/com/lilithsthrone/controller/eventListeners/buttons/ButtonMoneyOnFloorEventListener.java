package com.lilithsthrone.controller.eventListeners.buttons;

import com.lilithsthrone.controller.eventListeners.tooltips.ClonedEventListener;
import com.lilithsthrone.main.Main;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.w3c.dom.events.Event;

@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class ButtonMoneyOnFloorEventListener implements ClonedEventListener<ButtonMoneyOnFloorEventListener> {
    @Getter(onMethod = @__(@Override))
    private final ButtonMoneyOnFloorEventListener parent;

    @Override
    public void handleEvent(Event evt) {
        if (parent != null) {
            parent.handleEvent(evt);
            return;
        }
        Main.mainController.openInventory();
    }

    @Override
    public ButtonMoneyOnFloorEventListener newInstance() {
        return new ButtonMoneyOnFloorEventListener(tryGetParent());
    }
}
