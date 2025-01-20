package com.lilithsthrone.controller.eventListeners.buttons;

import com.lilithsthrone.controller.eventListeners.tooltips.ClonedEventListener;
import com.lilithsthrone.main.Main;
import org.w3c.dom.events.Event;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

/**
 * @since 0.1.69.9
 * @version 0.1.69.9
 * @author Innoxia
 */
public class ButtonCopyDialogueEventListener implements ClonedEventListener {

	@Override
	public void handleEvent(Event event) {
		StringSelection selection = new StringSelection(Main.game.getContentForClipboard());
	    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
	    clipboard.setContents(selection, selection);
	}

    @Override
    public ClonedEventListener newInstance() {
        return new ButtonCopyDialogueEventListener();
    }
}