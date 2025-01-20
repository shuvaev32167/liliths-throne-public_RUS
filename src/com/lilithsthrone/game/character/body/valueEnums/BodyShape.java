package com.lilithsthrone.game.character.body.valueEnums;

import com.lilithsthrone.game.dialogue.utils.UtilText;
import com.lilithsthrone.utils.Util;
import javafx.scene.paint.Color;

import java.util.List;

/**
 * @since 0.1.83
 * @version 0.2.8
 * @author Innoxia
 */
public enum BodyShape {
	
	/*
	 * Ectomorph: Lean and long, no muscle
	 * Endomorph: Big, high body fat
	 * Mesomorph: Muscular and well-built
	 */
	
	// BodySize == ZERO_SKINNY
	SKINNY_SOFT("тощий", BodySize.ZERO_SKINNY, Muscle.ZERO_SOFT, Util.newArrayListOfValues("худой", "тощий")),
	SKINNY_LIGHTLY_MUSCLED("миниатюрный", BodySize.ZERO_SKINNY, Muscle.ONE_LIGHTLY_MUSCLED, Util.newArrayListOfValues("худой", "тощий")),
	SKINNY_TONED("гибкий", BodySize.ZERO_SKINNY, Muscle.TWO_TONED, Util.newArrayListOfValues("худой")),
	SKINNY_MUSCULAR("исхудавший", BodySize.ZERO_SKINNY, Muscle.THREE_MUSCULAR, Util.newArrayListOfValues("худой", "в тонусе")),
	SKINNY_RIPPED("занимается гимнастикой", BodySize.ZERO_SKINNY, Muscle.FOUR_RIPPED, Util.newArrayListOfValues("худой", "в тонусе")),

	// BodySize == ONE_SLENDER
	SLENDER_SOFT("тонкий", BodySize.ONE_SLENDER, Muscle.ZERO_SOFT, Util.newArrayListOfValues("тонкий", "стройный")),
	SLENDER_LIGHTLY_MUSCLED("худой", BodySize.ONE_SLENDER, Muscle.ONE_LIGHTLY_MUSCLED, Util.newArrayListOfValues("тонкий", "стройный")),
	SLENDER_TONED("проворный", BodySize.ONE_SLENDER, Muscle.TWO_TONED, Util.newArrayListOfValues("тонкий", "стройный", "в тонусе")),
	SLENDER_MUSCULAR("гибкий", BodySize.ONE_SLENDER, Muscle.THREE_MUSCULAR, Util.newArrayListOfValues("тонкий", "в тонусе")),
	SLENDER_RIPPED("занимается аэробикой", BodySize.ONE_SLENDER, Muscle.FOUR_RIPPED, Util.newArrayListOfValues("тонкий", "в тонусе")),
	
	// BodySize == TWO_AVERAGE
	AVERAGE_SOFT("упитанный", BodySize.TWO_AVERAGE, Muscle.ZERO_SOFT, Util.newArrayListOfValues("упитанный", "мягкий")),
	AVERAGE_LIGHTLY_MUSCLED("средний", BodySize.TWO_AVERAGE, Muscle.ONE_LIGHTLY_MUSCLED, Util.newArrayListOfValues("немного в тонусе")),
	AVERAGE_TONED("здоровый", BodySize.TWO_AVERAGE, Muscle.TWO_TONED, Util.newArrayListOfValues("в тонусе")),
	AVERAGE_MUSCULAR("спортивный", BodySize.TWO_AVERAGE, Muscle.THREE_MUSCULAR, Util.newArrayListOfValues("в тонусе", "мускулистый")),
	AVERAGE_RIPPED("атлет", BodySize.TWO_AVERAGE, Muscle.FOUR_RIPPED, Util.newArrayListOfValues("в тонусе", "мускулистый", "сильный")),
	
	// BodySize == THREE_LARGE
	LARGE_SOFT("толстый", BodySize.THREE_LARGE, Muscle.ZERO_SOFT, Util.newArrayListOfValues("упитанный", "мягкий", "толстый")),
	LARGE_LIGHTLY_MUSCLED("полный", BodySize.THREE_LARGE, Muscle.ONE_LIGHTLY_MUSCLED, Util.newArrayListOfValues("упитанный", "мягкий", "полный")),
	LARGE_TONED("грузный", BodySize.THREE_LARGE, Muscle.TWO_TONED, Util.newArrayListOfValues("большой", "сильный")),
	LARGE_MUSCULAR("мощный", BodySize.THREE_LARGE, Muscle.THREE_MUSCULAR, Util.newArrayListOfValues("большой", "мускулистый", "сильный")),
	LARGE_RIPPED("крепкий", BodySize.THREE_LARGE, Muscle.FOUR_RIPPED, Util.newArrayListOfValues("огромный", "мускулистый", "сильный")),
	
	// BodySize == FOUR_HUGE
	HUGE_SOFT("жирный", BodySize.FOUR_HUGE, Muscle.ZERO_SOFT, Util.newArrayListOfValues("упитанный", "мягкий", "толстый")),
	HUGE_LIGHTLY_MUSCLED("коренастый", BodySize.FOUR_HUGE, Muscle.ONE_LIGHTLY_MUSCLED, Util.newArrayListOfValues("упитанный", "мягкий", "полный")),
	HUGE_TONED("крепкий", BodySize.FOUR_HUGE, Muscle.TWO_TONED, Util.newArrayListOfValues("большой", "сильный")),
	HUGE_MUSCULAR("плотный", BodySize.FOUR_HUGE, Muscle.THREE_MUSCULAR, Util.newArrayListOfValues("большой", "мускулистый", "сильный")),
	HUGE_RIPPED("здоровенный", BodySize.FOUR_HUGE, Muscle.FOUR_RIPPED, Util.newArrayListOfValues("огромный", "мускулистый", "сильный"));
	
	private final String name;
	private final List<String> limbDescriptors;
	private final BodySize relatedBodySize;
	private final Muscle relatedMuscle;
	
	BodyShape(String name, BodySize relatedBodySize, Muscle relatedMuscle, List<String> limbDescriptors) {
		this.name = name;
		this.relatedBodySize = relatedBodySize;
		this.relatedMuscle = relatedMuscle;
		this.limbDescriptors = limbDescriptors;
	}

	public String getName(boolean withDeterminer) {
		if(withDeterminer) {
			return UtilText.generateSingularDeterminer(name) + " " + name;
		} else {
			return name;
		}
	}

	public BodySize getRelatedBodySize() {
		return relatedBodySize;
	}

	public Muscle getRelatedMuscle() {
		return relatedMuscle;
	}
	
	public static BodyShape valueOf(Muscle muscle, BodySize bodySize) {
		for(BodyShape bs : BodyShape.values()) {
			if(muscle == bs.getRelatedMuscle() && bodySize == bs.getRelatedBodySize()) {
				return bs;
			}
		}
		return AVERAGE_LIGHTLY_MUSCLED;
	}
	
	public Color getDerivedColor() {
		return Util.midpointColor(relatedBodySize.getColour().getColor(), relatedMuscle.getColour().getColor());
	}
	
	public String toWebHexStringColour() {
		return Util.toWebHexString(getDerivedColor());
	}

	public List<String> getLimbDescriptors() {
		return limbDescriptors;
	}
}
