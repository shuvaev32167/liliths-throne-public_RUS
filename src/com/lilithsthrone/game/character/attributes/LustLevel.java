package com.lilithsthrone.game.character.attributes;

import java.util.ArrayList;
import java.util.List;

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
				&& !((character instanceof NPC) && ((NPC)character).hasFlag(NPCFlagValue.genericNPCBetrayedByPlayer) && !character.isSlave() && !Main.game.getPlayer().getFriendlyOccupants().contains(character.getId()))
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
					|| ((character instanceof NPC) && ((NPC)character).hasFlag(NPCFlagValue.genericNPCBetrayedByPlayer) && !character.isSlave() && !Main.game.getPlayer().getFriendlyOccupants().contains(character.getId()))) {
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
						modifiersList.add("Предпочитает <b style='color: " + SexPace.DOM_GENTLE.getColour().toWebHexString() + "'>мягкий</b> темп");
					}
					break;
				case DOM_NORMAL:
					if(!character.isPlayer()) {
						modifiersList.add("Предпочитает <b style='color: " + SexPace.DOM_NORMAL.getColour().toWebHexString() + "'>нормальный</b> темп");
					}
					break;
				case DOM_ROUGH:
					if(!character.isPlayer()) {
						if(!character.hasFetish(Fetish.FETISH_DOMINANT) && !character.hasFetish(Fetish.FETISH_SADIST)) {
							modifiersList.add("Предпочитает <b style='color: " + SexPace.DOM_NORMAL.getColour().toWebHexString() + "'>нормальный</b> темп");
							modifiersList.add("(<b style='color: " + SexPace.DOM_ROUGH.getColour().toWebHexString() + "'>Грубый</b> темп требует " + Fetish.FETISH_DOMINANT.getName(character)
									+ ", " + Fetish.FETISH_NON_CON_DOM.getName(character) + ", or " + Fetish.FETISH_SADIST.getName(character) + " фетиш)");
						} else {
							modifiersList.add("Предпочитает <b style='color: " + SexPace.DOM_ROUGH.getColour().toWebHexString() + "'>грубый</b> темп");
						}
					}
					break;
				case SUB_EAGER:
					if(!character.isPlayer()) {
						modifiersList.add("Предпочитает <b style='color: " + SexPace.SUB_EAGER.getColour().toWebHexString() + "'>стремительный</b> темп");
					}
					break;
				case SUB_NORMAL:
					if(!character.isPlayer()) {
						modifiersList.add("Предпочитает <b style='color: " + SexPace.SUB_NORMAL.getColour().toWebHexString() + "'>нормальный</b> темп");
					}
					break;
				case SUB_RESISTING:
					if(!character.isPlayer()) {
						if(character.hasFetish(Fetish.FETISH_NON_CON_SUB)) {
							modifiersList.add("Always prefers <b style='color: " + SexPace.SUB_RESISTING.getColour().toWebHexString() + "'>сопротивляющийся</b> темп из-за " + Fetish.FETISH_NON_CON_SUB.getName(character) + " фетиша");
						} else {
							modifiersList.add("Предпочитает <b style='color: " + SexPace.SUB_RESISTING.getColour().toWebHexString() + "'>сопротивляющийся</b> темп");
						}
					}
					break;
			}
		
			int gains = (int)(this.getArousalModifier()*100);
			modifiersList.add((gains >= 100 ? "[style.boldArousal(" + gains + "%)]" : "[style.boldBad(" + gains + "%)]") + " роста возбуждения");
			
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
							sb.append("[npc.NameIsFull] сейчас совсем не [npc.genderBasedWord(заинтересован, заинтересована)] в сексе, и в результате [npc.she] [npc.targetBasedWord(хочешь, хочет)], чтобы всё происходило медленно и нежно.");
							break;
						case ONE_HORNY:
							sb.append("[npc.NameIsFull] сейчас довольно [npc.genderBasedWord(возбуждён, возбуждена)], но всё ещё [npc.targetBasedWord(контролируешь, контролирует)] [npc.targetBasedWord(твою, [npc.her])] похоть, позволяя сохранять [npc.targetBasedWord(твоё, [npc.herHim])] хладнокровие и сосредоточиться на том, чтобы действовать медленно и нежно.");
							break;
						case TWO_AMOROUS:
							sb.append("[npc.NameIsFull] в настоящее время [npc.targetBasedWord(испытываешь, испытывает)] более чем легкое влечение, но всё ещё [npc.genderBasedWord(способен, способна)] сосредоточиться на том, чтобы действовать медленно и осторожно.");
							break;
						case THREE_LUSTFUL:
							sb.append("[npc.NameIsFull] в настоящее время [npc.targetBasedWord(горишь, горит)] страстью, но всё ещё [npc.genderBasedWord(способен, способна)] сосредоточиться на том, чтобы действовать медленно и нежно.");
							break;
						case FOUR_IMPASSIONED:
							sb.append("[npc.NameIsFull] полностью [npc.genderBasedWord(охвачен, охвачена)] страстью, но всё ещё [npc.genderBasedWord(способен, способна)] сосредоточиться на том, чтобы действовать медленно и нежно.");
							break;
						case FIVE_BURNING:
							sb.append("[npc.NameIsFull] полностью [npc.genderBasedWord(поглощён, поглощена)] страстью, но, тем не менее, по-прежнему [npc.genderBasedWord(способен, способна)] сосредоточиться на том, чтобы действовать медленно и нежно.");
							break;
					}
					break;
				case DOM_NORMAL:
				case SUB_NORMAL:
					switch(this) {
						case ZERO_COLD:
							sb.append("Хотя [npc.nameIsFull] сейчас совсем не [npc.genderBasedWord(заинтересован, заинтересована)] в сексе, [npc.she] всё же [npc.genderBasedWord(способен, способна)] заставить себя вести себя так, как будто [npc.sheIs] [npc.genderBasedWord(возбуждён, возбуждена)] и хочет секса.");
							break;
						case ONE_HORNY:
							sb.append("[npc.NameIsFull] сейчас очень [npc.genderBasedWord(возбуждён, возбуждена)] и более чем [npc.genderBasedWord(счастлив, счастлива)] заняться сексом в данный момент.");
							break;
						case TWO_AMOROUS:
							sb.append("[npc.NameIsFull] в данный момент [npc.targetBasedWord(испытываешь, испытывает)] более чем сильное влечение и очень [npc.genderBasedWord(рад, рада)] возможности заняться сексом прямо сейчас.");
							break;
						case THREE_LUSTFUL:
							sb.append("[npc.NameIsFull] в данный момент [npc.targetBasedWord(горишь, горит)] страстью и в восторге от того, что прямо сейчас [npc.targetBasedWord(занимаешься, занимается)] сексом.");
							break;
						case FOUR_IMPASSIONED:
							sb.append("[npc.NameIsFull] полностью [npc.genderBasedWord(охвачен, охвачена)] страстью, но всё ещё [npc.genderBasedWord(способен, способна)] удержаться от того, чтобы слишком увлечься.");
							break;
						case FIVE_BURNING:
							sb.append("[npc.NameIsFull] полностью [npc.genderBasedWord(поглощён, поглощена)] страстью, но, тем не менее, всё ещё [npc.genderBasedWord(способен, способна)] удержаться от того, чтобы слишком увлечься.");
							break;
					}
					break;
				case DOM_ROUGH:
				case SUB_EAGER:
					switch(this) {
						case ZERO_COLD:
							sb.append("Хотя [npc.nameIsFull] сейчас совсем не [npc.genderBasedWord(заинтересован, заинтересована)] в сексе, [npc.she] всё же [npc.genderBasedWord(способен, способна)] заставить себя вести себя так, как будто [npc.sheIs] чрезвычайно [npc.genderBasedWord(возбуждён, возбуждена)].");
							break;
						case ONE_HORNY:
							sb.append("[npc.NameIsFull] сейчас очень [npc.genderBasedWord(возбуждён, возбуждена)] и более чем [npc.genderBasedWord(счастлив, счастлива)] заняться сексом в данный момент.");
							break;
						case TWO_AMOROUS:
							sb.append("[npc.NameIsFull] в данный момент [npc.targetBasedWord(испытываешь, испытывает)] более чем сильное влечение и очень [npc.genderBasedWord(рад, рада)] возможности заняться сексом прямо сейчас.");
							break;
						case THREE_LUSTFUL:
							sb.append("[npc.NameIsFull] в данный момент [npc.targetBasedWord(горишь, горит)] страстью и в восторге от того, что прямо сейчас [npc.targetBasedWord(занимаешься, занимается)] сексом.");
							break;
						case FOUR_IMPASSIONED:
							sb.append("[npc.NameIsFull] полностью [npc.genderBasedWord(охвачен, охвачена)] страстью, и действительно [npc.targetBasedWord(начинаешь, начинает)] увлекаться.");
							break;
						case FIVE_BURNING:
							sb.append("[npc.NameIsFull] полностью [npc.genderBasedWord(поглощён, поглощена)] страстью, и полностью [npc.genderBasedWord(потерял, потеряла)] себя в удовольствии от секса.");
							break;
					}
					break;
				case SUB_RESISTING:
					switch(this) {
						case ZERO_COLD:
							sb.append("[npc.NameIsFull] совершенно не [npc.genderBasedWord(заинтересован, заинтересована)] в сексе прямо сейчас, и отчаянно [npc.targetBasedWord(пытаешься, пытается)] сопротивляться тому, что сейчас происходит с [npc.targetBasedWord(тобой, н[npc.morphSingleNameInstr([npc.herHim])])].");
							break;
						case ONE_HORNY:
							sb.append("[npc.NameIsFull] сейчас довольно [npc.genderBasedWord(возбуждён, возбуждена)], но, несмотря на это, [npc.sheIs] совсем не [npc.genderBasedWord(доволен, довольна)] [npc.targetBasedWord(твоей, [npc.her])] текущей ситуацией и отчаянно [npc.targetBasedWord(сопротивляешься, сопротивляется)] сексу.");
							break;
						case TWO_AMOROUS:
							sb.append("[npc.NameIsFull] в настоящее время [npc.targetBasedWord(испытываешь, испытывает)] более чем лёгкое влечение, но, несмотря на это, [npc.sheIs] совсем не [npc.genderBasedWord(доволен, довольна)] [npc.targetBasedWord(твоей, [npc.her])] текущей ситуацией и отчаянно [npc.targetBasedWord(сопротивляешься, сопротивляется)] сексу.");
							break;
						case THREE_LUSTFUL:
							sb.append("[npc.NameIsFull] в настоящее время [npc.targetBasedWord(горишь, горит)] страстью, но, несмотря на это, [npc.sheIs] совсем не [npc.genderBasedWord(доволен, довольна)] [npc.targetBasedWord(твоей, [npc.her])] текущей ситуацией и отчаянно [npc.targetBasedWord(сопротивляешься, сопротивляется)] сексу.");
							break;
						case FOUR_IMPASSIONED:
							sb.append("[npc.NameIsFull] полностью [npc.genderBasedWord(охвачен, охвачена)] страстью, но, несмотря на это, [npc.sheIs] совсем не [npc.genderBasedWord(доволен, довольна)] [npc.targetBasedWord(твоей, [npc.her])] текущей ситуацией и отчаянно [npc.targetBasedWord(сопротивляешься, сопротивляется)] сексу.");
							break;
						case FIVE_BURNING:
							sb.append("[npc.NameIsFull] полностью [npc.genderBasedWord(поглощён, поглощена)] страстью, но, несмотря на это, [npc.sheIs] совсем не [npc.genderBasedWord(доволен, довольна)] [npc.targetBasedWord(твоей, [npc.her])] текущей ситуацией и отчаянно [npc.targetBasedWord(сопротивляешься, сопротивляется)] сексу.");
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
