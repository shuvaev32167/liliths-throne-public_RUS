package com.lilithsthrone.game.character.persona;

import com.lilithsthrone.utils.colours.Colour;
import com.lilithsthrone.utils.colours.PresetColour;

/**
 * @since 0.1.79
 * @version 0.3.5
 * @author Innoxia
 */
public enum SexualOrientation {
    ANDROPHILIC("Интерес к мужчинам", false, true, PresetColour.MASCULINE, SexualOrientationPreference.THREE_AVERAGE),

    AMBIPHILIC("Интерес ко всем", true, true, PresetColour.ANDROGYNOUS, SexualOrientationPreference.THREE_AVERAGE),

    GYNEPHILIC("Интерес к женщинам", true, false, PresetColour.FEMININE, SexualOrientationPreference.THREE_AVERAGE);

	private final String name;
	private final Colour colour;
	private final SexualOrientationPreference orientationPreferenceDefault;
	private final boolean attractedToFeminine;
	private final boolean attractedToMasculine;

	SexualOrientation(String name, boolean attractedToFeminine, boolean attractedToMasculine, Colour colour, SexualOrientationPreference orientationPreferenceDefault) {
		this.name = name;
		this.colour = colour;
		this.orientationPreferenceDefault = orientationPreferenceDefault;
		this.attractedToMasculine = attractedToMasculine;
		this.attractedToFeminine = attractedToFeminine;
	}

	public String getName() {
		return name;
	}

	public Colour getColour() {
		return colour;
	}
	
	public SexualOrientationPreference getOrientationPreferenceDefault() {
		return orientationPreferenceDefault;
	}

	public boolean isAttractedToFeminine() {
		return attractedToFeminine;
	}

	public boolean isAttractedToMasculine() {
		return attractedToMasculine;
	}
	
	
}
