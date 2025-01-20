package com.lilithsthrone.game.character.body.valueEnums;

import com.lilithsthrone.game.character.GameCharacter;
import com.lilithsthrone.game.character.body.Body;
import com.lilithsthrone.utils.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * @since 0.1.83
 * @version 0.3.7
 * @author Innoxia
 */
public enum HairStyle {

//	- parted down the middle
//	- side parted
//	- shaved (different from bald)
//	- punk (hair draped over face)

	NONE("натуральные", Femininity.ANDROGYNOUS, HairLength.ZERO_BALD) {
		@Override
		public String getName(Body body) {
			if(body!=null && body.isFeral()) {
				if(body.getLegConfiguration()==LegConfiguration.AVIAN) {
					return "оперенные";
				} else {
					return "гривастые";
				}
			}
			return super.getName(body);
		}
	},
	MESSY("неаккуратные", Femininity.ANDROGYNOUS, HairLength.ONE_VERY_SHORT),
	LOOSE("распущенные", Femininity.ANDROGYNOUS, HairLength.ONE_VERY_SHORT),
	CURLY("кудрявые", Femininity.ANDROGYNOUS, HairLength.ONE_VERY_SHORT),
	STRAIGHT("прямые", Femininity.ANDROGYNOUS, HairLength.ONE_VERY_SHORT),
	SLICKED_BACK("зачесанные назад", Femininity.ANDROGYNOUS, HairLength.ONE_VERY_SHORT),
	SIDE_PARTED("с боковым пробором", Femininity.ANDROGYNOUS, HairLength.ONE_VERY_SHORT),

	SIDECUT("с боковым андеркатом", Femininity.ANDROGYNOUS, HairLength.TWO_SHORT),
	MOHAWK("ирокезом", Femininity.ANDROGYNOUS, HairLength.TWO_SHORT),
	DREADLOCKS("дреды", Femininity.ANDROGYNOUS, HairLength.TWO_SHORT),
	
	AFRO("афро", Femininity.MASCULINE, HairLength.ONE_VERY_SHORT),
	TOPKNOT("топ-узел.", Femininity.MASCULINE, HairLength.THREE_SHOULDER_LENGTH),
	
	PIXIE("пикси", Femininity.FEMININE, HairLength.TWO_SHORT),
	BUN("пучоком", Femininity.FEMININE, HairLength.THREE_SHOULDER_LENGTH),
	BOB_CUT("каре", Femininity.FEMININE, HairLength.THREE_SHOULDER_LENGTH),
	CHONMAGE("чонмаге", Femininity.FEMININE, HairLength.THREE_SHOULDER_LENGTH),
	WAVY("волнистые", Femininity.FEMININE, HairLength.THREE_SHOULDER_LENGTH),
	PONYTAIL("конский хвост", Femininity.FEMININE, HairLength.THREE_SHOULDER_LENGTH),
	LOW_PONYTAIL("низким конским хвостом", Femininity.FEMININE, HairLength.THREE_SHOULDER_LENGTH),
	TWIN_TAILS("двойными хвостиками", Femininity.FEMININE, HairLength.THREE_SHOULDER_LENGTH),
	SIDE_BRAIDS("боковыми косами", Femininity.FEMININE, HairLength.THREE_SHOULDER_LENGTH),
	CHIGNON("шиньон", Femininity.FEMININE, HairLength.FOUR_MID_BACK),
	BRAIDED("косичками", Femininity.FEMININE, HairLength.FOUR_MID_BACK),
	TWIN_BRAIDS("двумя косами", Femininity.FEMININE, HairLength.FOUR_MID_BACK),
	CROWN_BRAID("косой вокруг головы", Femininity.FEMININE, HairLength.FOUR_MID_BACK),
	DRILLS("кудрявые локоны ожо", Femininity.FEMININE, HairLength.FOUR_MID_BACK),
	HIME_CUT("принцесса (Японская Химе)", Femininity.FEMININE, HairLength.FOUR_MID_BACK),
	BIRD_CAGE("птичьей клеткой", Femininity.FEMININE, HairLength.SEVEN_TO_FLOOR);
	
	private final String descriptor;
	private final Femininity femininity;
	private final int minimumLengthRequired;

	HairStyle(String descriptor, Femininity femininity, HairLength minimumLengthRequired) {
		this.descriptor = descriptor;
		this.femininity = femininity;
		this.minimumLengthRequired = minimumLengthRequired.getMinimumValue();
	}

	public String getName(GameCharacter owner) {
		if(owner==null) {
			return descriptor;
		}
		return getName(owner.getBody());
	}

	public String getName(Body body) {
		return descriptor;
	}
	
	/** This should just be used for random character hair style generation. */
	public Femininity getFemininity() {
		return femininity;
	}

	public int getMinimumLengthRequired() {
		return minimumLengthRequired;
	}
	
	/**
	 * @return A random hair style, filtered by femininity and length limitations.
	 */
	public static HairStyle getRandomHairStyle(boolean feminine, int hairLength) {
		List<HairStyle> availableStyles = new ArrayList<>();
		
		for(HairStyle hs : HairStyle.values()) {
			if((hs.getFemininity()==Femininity.ANDROGYNOUS || hs.getFemininity().isFeminine()==feminine) && hs.getMinimumLengthRequired() <= hairLength) {
				availableStyles.add(hs);
			}
		}
		
		// Most likely to have a "normal" hair style:
		if(Math.random()>0.10f) {
			availableStyles.remove(HairStyle.AFRO);
			availableStyles.remove(HairStyle.SIDECUT);
			availableStyles.remove(HairStyle.MOHAWK);
			availableStyles.remove(HairStyle.HIME_CUT);
			availableStyles.remove(HairStyle.CHONMAGE);
			availableStyles.remove(HairStyle.DREADLOCKS);
			availableStyles.remove(HairStyle.BIRD_CAGE);
			availableStyles.remove(HairStyle.DRILLS);
		}
		
		return availableStyles.get(Util.random.nextInt(availableStyles.size()));
	}
}
