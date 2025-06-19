package com.lilithsthrone.controller.eventListeners.buttons;

import com.lilithsthrone.controller.eventListeners.tooltips.ClonedEventListener;
import com.lilithsthrone.main.Main;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.w3c.dom.events.Event;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

/**
 * @since 0.1.69.9
 * @version 0.1.69.9
 * @author Innoxia
 */
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class ButtonCopyDialogueEventListener implements ClonedEventListener<ButtonCopyDialogueEventListener> {
	@Getter(onMethod = @__(@Override))
	private final ButtonCopyDialogueEventListener parent;

	@Override
	public void handleEvent(Event event) {
		if (parent != null) {
			parent.handleEvent(event);
			return;
		}
		StringSelection selection = new StringSelection(Main.game.getContentForClipboard());
	    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
	    clipboard.setContents(selection, selection);
	}

    @Override
	public ButtonCopyDialogueEventListener newInstance() {
		return new ButtonCopyDialogueEventListener(tryGetParent());
    }
}