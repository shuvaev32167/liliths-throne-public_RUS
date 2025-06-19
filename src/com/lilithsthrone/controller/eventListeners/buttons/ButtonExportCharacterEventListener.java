package com.lilithsthrone.controller.eventListeners.buttons;

import com.lilithsthrone.controller.eventListeners.tooltips.ClonedEventListener;
import com.lilithsthrone.game.Game;
import com.lilithsthrone.game.character.GameCharacter;
import com.lilithsthrone.main.Main;
import com.lilithsthrone.utils.colours.PresetColour;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.w3c.dom.events.Event;

@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class ButtonExportCharacterEventListener implements ClonedEventListener<ButtonExportCharacterEventListener> {
    @Getter(onMethod = @__(@Override))
    private final ButtonExportCharacterEventListener parent;
    @Setter
    @Accessors(chain = true)
    private GameCharacter exportCharacter;

    @Override
    public void handleEvent(Event evt) {
        if (parent != null) {
            parent.handleEvent(evt);
            return;
        }
        if (exportCharacter == null) {
            return;
        }
        Game.exportCharacter(exportCharacter);
        Main.game.flashMessage(PresetColour.GENERIC_EXCELLENT, "Персонаж экспортирован!");
    }

    @Override
    public ButtonExportCharacterEventListener newInstance() {
        return new ButtonExportCharacterEventListener(tryGetParent());
    }
}
