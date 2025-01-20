package com.lilithsthrone.game.character.attributes;

import com.lilithsthrone.game.PropertyValue;
import com.lilithsthrone.game.character.GameCharacter;
import com.lilithsthrone.game.character.effects.AbstractStatusEffect;
import com.lilithsthrone.game.character.effects.StatusEffect;
import com.lilithsthrone.game.character.fetishes.Fetish;
import com.lilithsthrone.game.character.fetishes.FetishDesire;
import com.lilithsthrone.game.character.npc.NPC;
import com.lilithsthrone.game.character.npc.NPCFlagValue;
import com.lilithsthrone.game.dialogue.utils.UtilText;
import com.lilithsthrone.game.sex.SexPace;
import com.lilithsthrone.main.Main;
import com.lilithsthrone.utils.colours.Colour;
import com.lilithsthrone.utils.colours.PresetColour;

import java.util.ArrayList;
import java.util.List;

/**
 * @since 0.1.97
 * @version 0.3.8.2
 * @author Innoxia
 */
public enum LustLevel {

	ZERO_COLD("холоден", 0, 10, 0.5f, PresetColour.LUST_STAGE_ZERO, SexPace.SUB_RESISTING, SexPace.DOM_GENTLE) {
		@Override
		public AbstractStatusEffect getRelatedStatusEffect() {
			return StatusEffect.LUST_PERK_0;
		}
	},

	ONE_HORNY("возбуждён", 10, 25, 0.75f, PresetColour.LUST_STAGE_ONE, SexPace.SUB_NORMAL, SexPace.DOM_NORMAL) {
		@Override
		public AbstractStatusEffect getRelatedStatusEffect() {
			return StatusEffect.LUST_PERK_1;
		}
	},

	TWO_AMOROUS("чувствителен", 25, 50, 1f, PresetColour.LUST_STAGE_TWO, SexPace.SUB_NORMAL, SexPace.DOM_NORMAL) {
		@Override
		public AbstractStatusEffect getRelatedStatusEffect() {
			return StatusEffect.LUST_PERK_2;
		}
	},

	THREE_LUSTFUL("пылок", 50, 75, 1.25f, PresetColour.LUST_STAGE_THREE, SexPace.SUB_NORMAL, SexPace.DOM_NORMAL) {
		@Override
		public AbstractStatusEffect getRelatedStatusEffect() {
			return StatusEffect.LUST_PERK_3;
		}
	},

	FOUR_IMPASSIONED("похотлив", 75, 90, 1.5f, PresetColour.LUST_STAGE_FOUR, SexPace.SUB_EAGER, SexPace.DOM_ROUGH) {
		@Override
		public AbstractStatusEffect getRelatedStatusEffect() {
			return StatusEffect.LUST_PERK_4;
		}
	},

	FIVE_BURNING("страстен", 90, 100, 1.5f, PresetColour.LUST_STAGE_FIVE, SexPace.SUB_EAGER, SexPace.DOM_ROUGH) {
		@Override
		public AbstractStatusEffect getRelatedStatusEffect() {
			return StatusEffect.LUST_PERK_5;
		}
	};
	
	
	private final String name;
	private final int minimumValue;
    private final int maximumValue;
	private final float arousalModifier;
	private final Colour colour;
	private final SexPace sexPaceSubmissive;
	private final SexPace sexPaceDominant;

	LustLevel(String name, int minimumValue, int maximumValue, float arousalModifier, Colour colour, SexPace sexPaceSubmissive, SexPace sexPaceDominant) {
		this.name = name;
		this.minimumValue = minimumValue;
		this.maximumValue = maximumValue;
		this.arousalModifier = arousalModifier;
		this.colour = colour;
		this.sexPaceSubmissive = sexPaceSubmissive;
		this.sexPaceDominant = sexPaceDominant;
	}

	public abstract AbstractStatusEffect getRelatedStatusEffect();

	public String getName() {
		return name;
	}

	public int getMinimumValue() {
		return minimumValue;
	}

	public int getMaximumValue() {
		return maximumValue;
	}
	
	public int getMedianValue() {
		return (minimumValue + maximumValue) / 2;
	}
	
	public float getArousalModifier() {
		return arousalModifier;
	}

	public Colour getColour() {
		return colour;
	}

	public static LustLevel getLustLevelFromValue(float value){
		if(value<0) {
			return ZERO_COLD;
		}
		for(LustLevel al : LustLevel.values()) {
			if(value>=al.getMinimumValue() && value<al.getMaximumValue()) {
				return al;
			}
		}
		return FIVE_BURNING;
	}


	public SexPace getSexPaceSubmissive() {
		return sexPaceSubmissive;
	}

	public SexPace getSexPaceDominant() {
		return sexPaceDominant;
	}
	
	public boolean isResistingFromRapePlay(GameCharacter character) {
		return !Main.sex.isDom(character)
				&& (character.hasFetish(Fetish.FETISH_NON_CON_SUB) && !Main.sex.isCharacterBannedFromRapePlay(character))
				&& !((character instanceof NPC) && ((NPC)character).hasFlag(NPCFlagValue.genericNPCBetrayedByPlayer))
				&& getSexPaceSubmissive()!=SexPace.SUB_RESISTING;
	}
	
	public SexPace getSexPace(boolean consensual, GameCharacter character) {
		SexPace pace;
		if(Main.sex.isDom(character)) {
			pace = getSexPaceDominant();
			
			if((character.hasFetish(Fetish.FETISH_SUBMISSIVE) && !character.hasFetish(Fetish.FETISH_SADIST) && !character.hasFetish(Fetish.FETISH_DOMINANT))
					|| character.getFetishDesire(Fetish.FETISH_SADIST) == FetishDesire.ZERO_HATE) {
				pace = SexPace.DOM_GENTLE;
				
			} else if(character.getFetishDesire(Fetish.FETISH_SADIST).isNegative()) {
				pace = SexPace.DOM_NORMAL;
				
			} else if(character.hasFetish(Fetish.FETISH_SADIST)) {
				return SexPace.DOM_ROUGH;
				
			} else { // Hate sex:
				for(GameCharacter target : Main.sex.getAllParticipants()) {
					if(!Main.sex.isDom(target) && character.getAffection(target)<AffectionLevel.NEGATIVE_TWO_DISLIKE.getMaximumValue()) {
						return SexPace.DOM_ROUGH;
					}
				}
			}
			
		} else {
			pace = getSexPaceSubmissive();
			if((character.hasFetish(Fetish.FETISH_NON_CON_SUB) && !Main.sex.isCharacterBannedFromRapePlay(character))
					|| ((character instanceof NPC) && ((NPC)character).hasFlag(NPCFlagValue.genericNPCBetrayedByPlayer))) {
				pace = SexPace.SUB_RESISTING;
			}
		}
		
		if(pace==SexPace.SUB_RESISTING && !Main.getProperties().hasValue(PropertyValue.nonConContent)) {
			pace = SexPace.SUB_NORMAL;
		}
		
		if(pace==SexPace.DOM_ROUGH
				&& ((!character.hasFetish(Fetish.FETISH_DOMINANT) && !character.hasFetish(Fetish.FETISH_SADIST) && !character.hasFetish(Fetish.FETISH_NON_CON_DOM))
						|| (character.getFetishDesire(Fetish.FETISH_SADIST).isNegative()))) {
			pace = SexPace.DOM_NORMAL;
		}
		
		return pace;
	}
	
	public List<String> getStatusEffectModifierDescription(boolean consensual, GameCharacter character) {
		List<String> modifiersList = new ArrayList<>();

		Colour levelColour = LustLevel.getLustLevelFromValue(character.getRestingLust()).getColour();
		modifiersList.add("Минимальная похоть: <b style='color:" + levelColour.toWebHexString() + ";'>" + character.getRestingLust() + "</b>");
		
		if(Main.game.isInSex()) {
			switch(this.getSexPace(consensual, character)) {
				case DOM_GENTLE:
					if(!character.isPlayer()) {
						modifiersList.add("Prefers <b style='color: " + SexPace.DOM_GENTLE.getColour().toWebHexString() + "'>gentle</b> pace");
					}
					break;
				case DOM_NORMAL:
					if(!character.isPlayer()) {
						modifiersList.add("Prefers <b style='color: " + SexPace.DOM_NORMAL.getColour().toWebHexString() + "'>normal</b> pace");
					}
					break;
				case DOM_ROUGH:
					if(!character.isPlayer()) {
						if(!character.hasFetish(Fetish.FETISH_DOMINANT) && !character.hasFetish(Fetish.FETISH_SADIST)) {
							modifiersList.add("Prefers <b style='color: " + SexPace.DOM_NORMAL.getColour().toWebHexString() + "'>normal</b> pace");
							modifiersList.add("(<b style='color: " + SexPace.DOM_ROUGH.getColour().toWebHexString() + "'>Rough</b> pace requires "+Fetish.FETISH_DOMINANT.getName(character)
													+", "+Fetish.FETISH_NON_CON_DOM.getName(character)+", or "+Fetish.FETISH_SADIST.getName(character)+" fetish)");
						} else {
							modifiersList.add("Prefers <b style='color: " + SexPace.DOM_ROUGH.getColour().toWebHexString() + "'>rough</b> pace");
						}
					}
					break;
				case SUB_EAGER:
					if(!character.isPlayer()) {
						modifiersList.add("Prefers <b style='color: " + SexPace.SUB_EAGER.getColour().toWebHexString() + "'>eager</b> pace");
					}
					break;
				case SUB_NORMAL:
					if(!character.isPlayer()) {
						modifiersList.add("Prefers <b style='color: " + SexPace.SUB_NORMAL.getColour().toWebHexString() + "'>normal</b> pace");
					}
					break;
				case SUB_RESISTING:
					if(!character.isPlayer()) {
						if(character.hasFetish(Fetish.FETISH_NON_CON_SUB)) {
							modifiersList.add("Always prefers <b style='color: " + SexPace.SUB_RESISTING.getColour().toWebHexString() + "'>resisting</b> pace due to "+Fetish.FETISH_NON_CON_SUB.getName(character)+" fetish");
						} else {
							modifiersList.add("Prefers <b style='color: " + SexPace.SUB_RESISTING.getColour().toWebHexString() + "'>resisting</b> pace");
						}
					}
					break;
			}
		
			int gains = (int)(this.getArousalModifier()*100);
			modifiersList.add((gains>=100?"[style.boldArousal("+gains+"%)]":"[style.boldBad("+gains+"%)]")+" arousal gains");
			
		}
		
		return modifiersList;
	}
	
	public String getStatusEffectDescription(boolean consensual, GameCharacter character) {
		StringBuilder sb = new StringBuilder();

		if(Main.game.isInSex()) {
			switch(this.getSexPace(consensual, character)) {
				case DOM_GENTLE:
					switch(this) {
						case ZERO_COLD:
                            sb.append("[npc.NameIsFull] not really interested in having sex at all right now, and as a result, [npc.she] want to take things slow and gentle.");
							break;
						case ONE_HORNY:
							sb.append("[npc.NameIsFull] currently quite horny, but [npc.is] still in control of [npc.her] lust, allowing [npc.herHim] to keep a cool head and concentrate on taking things slow and gentle.");
							break;
						case TWO_AMOROUS:
							sb.append("[npc.NameIsFull] currently feeling more than a little lustful, but [npc.is] still able to concentrate on taking things slow and gentle.");
							break;
						case THREE_LUSTFUL:
							sb.append("[npc.NameIsFull] currently burning with lust, but [npc.is] still able to concentrate on taking things slow and gentle.");
							break;
						case FOUR_IMPASSIONED:
							sb.append("[npc.NameIsFull] completely burning with lust, but [npc.is] still able to concentrate on taking things slow and gentle.");
							break;
						case FIVE_BURNING:
							sb.append("[npc.NameIsFull] completely overwhelmed with lust, but, somehow, [npc.is] still able to concentrate on taking things slow and gentle.");
							break;
					}
					break;
				case DOM_NORMAL:
				case SUB_NORMAL:
					switch(this) {
						case ZERO_COLD:
							sb.append("Although [npc.nameIsFull]n't really interested in having sex at all right now, [npc.she] [npc.is] still able to force [npc.herself] to act as though [npc.sheIs] turned on and horny.");
							break;
						case ONE_HORNY:
							sb.append("[npc.NameIsFull] currently quite horny, and [npc.is] more than happy to have sex at the moment.");
							break;
						case TWO_AMOROUS:
							sb.append("[npc.NameIsFull] currently feeling more than a little lustful, and [npc.is] very happy to be having sex right at this moment.");
							break;
						case THREE_LUSTFUL:
							sb.append("[npc.NameIsFull] currently burning with lust, and [npc.is] ecstatic to be having sex right at this moment.");
							break;
						case FOUR_IMPASSIONED:
							sb.append("[npc.NameIsFull] completely burning with lust, but [npc.is] still able to prevent [npc.herself] from getting too carried away with things.");
							break;
						case FIVE_BURNING:
							sb.append("[npc.NameIsFull] completely overwhelmed with lust, but, somehow, [npc.is] still able to prevent [npc.herself] from getting too carried away with things.");
							break;
					}
					break;
				case DOM_ROUGH:
				case SUB_EAGER:
					switch(this) {
						case ZERO_COLD:
							sb.append("Although [npc.nameIsFull]n't really interested in having sex at all right now, [npc.she] [npc.is] still able to force [npc.herself] to act as though [npc.sheIs] extremely turned on.");
							break;
						case ONE_HORNY:
							sb.append("[npc.NameIsFull] currently quite horny, and [npc.is] more than happy to have sex at the moment.");
							break;
						case TWO_AMOROUS:
							sb.append("[npc.NameIsFull] currently feeling more than a little lustful, and [npc.is] very happy to be having sex right at this moment.");
							break;
						case THREE_LUSTFUL:
							sb.append("[npc.NameIsFull] currently burning with lust, and [npc.is] ecstatic to be having sex right at this moment.");
							break;
						case FOUR_IMPASSIONED:
							sb.append("[npc.NameIsFull] completely burning with lust, and [npc.is] really starting to get carried away with things.");
							break;
						case FIVE_BURNING:
							sb.append("[npc.NameIsFull] completely overwhelmed with lust, and [npc.has] totally lost [npc.herself] to the pleasure of having sex.");
							break;
					}
					break;
				case SUB_RESISTING:
					switch(this) {
						case ZERO_COLD:
							sb.append("[npc.NameIsFull] not at all interested in having sex right now, and [npc.is] desperately trying to resist what's currently happening to [npc.herHim].");
							break;
						case ONE_HORNY:
							sb.append("[npc.NameIsFull] currently quite horny, but, despite this, [npc.sheIs] not at all happy with [npc.her] current situation, and [npc.is] desperately resisting sex.");
							break;
						case TWO_AMOROUS:
							sb.append("[npc.NameIsFull] currently feeling more than a little lustful, but, despite this, [npc.sheIs] not at all happy with [npc.her] current situation, and [npc.is] desperately resisting sex.");
							break;
						case THREE_LUSTFUL:
							sb.append("[npc.NameIsFull] currently burning with lust, but, despite this, [npc.sheIs] not at all happy with [npc.her] current situation, and [npc.is] desperately resisting sex.");
							break;
						case FOUR_IMPASSIONED:
							sb.append("[npc.NameIsFull] completely burning with lust, but, despite this, [npc.sheIs] not at all happy with [npc.her] current situation, and [npc.is] desperately resisting sex.");
							break;
						case FIVE_BURNING:
							sb.append("[npc.NameIsFull] completely overwhelmed with lust, but, despite this, [npc.sheIs] not at all happy with [npc.her] current situation, and [npc.is] desperately resisting sex.");
							break;
					}
					break;
			}
			
		} else {
			switch(this) {
				case ZERO_COLD:
					sb.append("[npc.NameIsFull] не очень [npc.genderBasedWord(заинтересован, заинтересована)] в сексе в данный момент.");
					break;
				case ONE_HORNY:
					sb.append("[npc.NameIsFull] в данный момент очень [npc.genderBasedWord(возбуждён, возбуждена)], но всё ещё [npc.targetBasedWord(контролируешь свою, контролирует [npc.her])] похоть.");
					if(Main.game.isOpportunisticAttackersEnabled() && character.isPlayer())
						sb.append("<br><b style='color:" + PresetColour.BASE_GREY.toWebHexString() + ";'>Возможность нападения</b><br>Кажется, ты начинаешь притягивать к себе неприятности.");
					break;
				case TWO_AMOROUS:
					sb.append("[npc.NameIsFull] в настоящее время [npc.targetBasedWord(чувствуешь, чувствует)] себя более чем [npc.genderBasedWord(похотливым, похотливой)], и довольно часто [npc.targetBasedWord(думаешь, думает)] о сексе.");
					if(Main.game.isOpportunisticAttackersEnabled() && character.isPlayer())
						sb.append("<br><b style='color:" + PresetColour.BASE_GREY.toWebHexString() + ";'>Возможность нападения</b><br>Ты чувствуешь на себе всё больше и больше пристальных взглядов.");
					break;
				case THREE_LUSTFUL:
					sb.append("[npc.NameIsFull] в данный момент [npc.targetBasedWord(сгораешь, сгорает)] от похоти, и изо всех сил [npc.targetBasedWord(пытаешься, пытается)] думать о чем-нибудь, кроме секса.");
					if(Main.game.isOpportunisticAttackersEnabled() && character.isPlayer())
						sb.append("<br><b style='color:" + PresetColour.BASE_GREY.toWebHexString() + ";'>Возможность нападения</b><br>Твою ауру, наполненную похотью, больше нельзя скрывать.");
					break;
				case FOUR_IMPASSIONED:
					sb.append("[npc.NameIsFull] полностью [npc.targetBasedWord(сгораешь, сгорает)] от похоти, и изо всех сил [npc.targetBasedWord(пытаешься, пытается)] думать о чем-нибудь, кроме секса.");
					if(Main.game.isOpportunisticAttackersEnabled() && character.isPlayer())
						sb.append("<br><b style='color:" + PresetColour.BASE_GREY.toWebHexString() + ";'>Возможность нападения</b><br>Почти каждый прохожий обращает на тебя похотливые взгляды.");
					break;
				case FIVE_BURNING:
					sb.append("[npc.NameIsFull] полностью [npc.genderBasedWord(поглощён, поглощена)] похотью, и не [npc.genderBasedWord(способен, способна)] думать ни о чём, кроме секса.");
					if(Main.game.isOpportunisticAttackersEnabled() && character.isPlayer())
						sb.append("<br><b style='color:" + PresetColour.BASE_GREY.toWebHexString() + ";'>Возможность нападения</b><br>Каждый может сказать, что ты полностью [npc.genderBasedWord(поглощён, поглощена)] похотью. Некоторые наверняка попытаются воспользоваться этим.");
					break;
			}
		}
		
		return UtilText.parse(character, sb.toString());
	
	}
}
