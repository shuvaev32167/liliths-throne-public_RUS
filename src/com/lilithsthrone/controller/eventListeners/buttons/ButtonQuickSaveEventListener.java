package com.lilithsthrone.controller.eventListeners.buttons;

import com.lilithsthrone.controller.eventListeners.tooltips.ClonedEventListener;
import com.lilithsthrone.main.Main;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.w3c.dom.events.Event;

@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class ButtonQuickSaveEventListener implements ClonedEventListener<ButtonQuickSaveEventListener> {
    @Getter(onMethod = @__(@Override))
    private final ButtonQuickSaveEventListener parent;

    @Override
    public void handleEvent(Event evt) {
        if (parent != null) {
            parent.handleEvent(evt);
            return;
        }
        Main.quickSaveGame();
    }

    @Override
    public ButtonQuickSaveEventListener newInstance() {
        return new ButtonQuickSaveEventListener(tryGetParent());
    }
}
