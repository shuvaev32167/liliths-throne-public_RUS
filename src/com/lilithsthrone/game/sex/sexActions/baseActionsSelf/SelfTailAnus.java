package com.lilithsthrone.game.sex.sexActions.baseActionsSelf;

import com.lilithsthrone.game.character.attributes.CorruptionLevel;
import com.lilithsthrone.game.dialogue.utils.UtilText;
import com.lilithsthrone.game.sex.*;
import com.lilithsthrone.game.sex.sexActions.SexAction;
import com.lilithsthrone.game.sex.sexActions.SexActionType;
import com.lilithsthrone.main.Main;
import com.lilithsthrone.utils.Util;
import com.lilithsthrone.utils.Util.Value;

/**
 * @since 0.1.79
 * @version 0.4.0.0
 * @author Innoxia
 */
public class SelfTailAnus {
	
	public static final SexAction SELF_TAIL_ANUS_PENETRATION = new SexAction(
			SexActionType.START_ONGOING,
			ArousalIncrease.THREE_NORMAL,
			ArousalIncrease.ONE_MINIMUM,
			CorruptionLevel.THREE_DIRTY,
			Util.newHashMapOfValues(new Value<>(SexAreaPenetration.TAIL, SexAreaOrifice.ANUS)),
			SexParticipantType.SELF) {

		@Override
		public String getActionTitle() {
			return "Tail-peg (self)";
		}

		@Override
		public String getActionDescription() {
			return "Start fucking [npc.her] own [npc.ass] with [npc.her] [npc.tail+].";
		}

		@Override
		public String getDescription() {
			return UtilText.returnStringAtRandom(
                    "Snaking [npc.her] [npc.tail] up to [npc.her] [npc.ass], [npc.name] tease the tip over [npc.her] [npc.asshole+], [npc.moaning] in delight before thrusting it deep inside.",
                    "[npc.Name] snake [npc.her] [npc.tail] up to [npc.her] [npc.ass], [npc.moaning] in delight as [npc.she] force it deep into [npc.her] inviting [npc.asshole].",
                    "Sliding the tip of [npc.her] [npc.tail+] up to [npc.her] neglected [npc.asshole], [npc.name] suddenly thrust it deep inside, letting out [npc.a_moan+] as [npc.she] start tail-fucking [npc.her] own [npc.ass].",
                    "[npc.Name] eagerly thrust [npc.her] [npc.tail+] deep into [npc.her] needy [npc.asshole], letting out a series of [npc.moans+] as [npc.she] start tail-fucking [npc.her] own [npc.ass].");
		}
	};
	
	public static final SexAction DOM_SELF_TAIL_ANUS_GENTLE = new SexAction(
			SexActionType.ONGOING,
			ArousalIncrease.THREE_NORMAL,
			ArousalIncrease.ONE_MINIMUM,
			CorruptionLevel.TWO_HORNY,
			Util.newHashMapOfValues(new Value<>(SexAreaPenetration.TAIL, SexAreaOrifice.ANUS)),
			SexParticipantType.SELF,
			SexPace.DOM_GENTLE) {
		
		@Override
		public String getActionTitle() {
			return "Gentle tail-pegging (self)";
		}

		@Override
		public String getActionDescription() {
			return "Gently fuck [npc.her] own [npc.ass] with [npc.her] [npc.tail+].";
		}

		@Override
		public String getDescription() {
			return UtilText.returnStringAtRandom(
                    "[npc.A_moan+] escapes from between [npc.namePos] [npc.lips+] as [npc.she] slowly push [npc.her] [npc.tail] deep inside [npc.her] [npc.asshole+].",
                    "Gently pumping [npc.her] [npc.tail] in and out of [npc.her] [npc.asshole+], [npc.name] start letting out a series of delighted [npc.moans] as [npc.she] slowly fuck [npc.her] own [npc.ass].",
                    "Slowly driving [npc.her] [npc.tail] deep inside [npc.her] [npc.asshole], [npc.name] let out a little whimper as [npc.she] "
							+(Main.sex.getCharacterPerformingAction().hasPenis() && !Main.sex.getCharacterPerformingAction().hasVagina()
                            ? "use it to gently start massaging [npc.her] prostate."
                            : "gently fuck [npc.her] own [npc.ass+]."),
                    "Focusing on pleasuring [npc.her] [npc.ass+], [npc.name] start gently pumping [npc.her] [npc.tail] in and out of [npc.her] [npc.asshole+].");
		}
		
	};
	
	public static final SexAction DOM_SELF_TAIL_ANUS_NORMAL = new SexAction(
			SexActionType.ONGOING,
			ArousalIncrease.THREE_NORMAL,
			ArousalIncrease.ONE_MINIMUM,
			CorruptionLevel.TWO_HORNY,
			Util.newHashMapOfValues(new Value<>(SexAreaPenetration.TAIL, SexAreaOrifice.ANUS)),
			SexParticipantType.SELF,
			SexPace.DOM_NORMAL) {
		
		@Override
		public String getActionTitle() {
			return "Tail-pegging (self)";
		}

		@Override
		public String getActionDescription() {
			return "Concentrate on fucking [npc.her] own [npc.ass] with [npc.her] [npc.tail+].";
		}

		@Override
		public String getDescription() {
			return UtilText.returnStringAtRandom(
                    "[npc.A_moan+] escapes from between [npc.namePos] [npc.lips+] as [npc.she] greedily push [npc.her] [npc.tail] deep inside [npc.her] [npc.asshole+].",
                    "Pumping [npc.her] [npc.tail] in and out of [npc.her] [npc.asshole+], [npc.name] start letting out a series of delighted [npc.moans] as [npc.she] rhythmically fuck [npc.her] own [npc.ass].",
                    "Driving [npc.her] [npc.tail] deep inside [npc.her] [npc.asshole], [npc.name] let out [npc.a_moan] as [npc.she] "
							+(Main.sex.getCharacterPerformingAction().hasPenis() && !Main.sex.getCharacterPerformingAction().hasVagina()
                            ? "use it to start massaging [npc.her] prostate."
                            : "steadily fuck [npc.her] own [npc.ass+]."),
                    "Focusing on pleasuring [npc.her] [npc.ass+], [npc.name] start pumping [npc.her] [npc.tail] in and out of [npc.her] [npc.asshole+].");
		}
	};
	
	public static final SexAction DOM_SELF_TAIL_ANUS_ROUGH = new SexAction(
			SexActionType.ONGOING,
			ArousalIncrease.THREE_NORMAL,
			ArousalIncrease.ONE_MINIMUM,
			CorruptionLevel.THREE_DIRTY,
			Util.newHashMapOfValues(new Value<>(SexAreaPenetration.TAIL, SexAreaOrifice.ANUS)),
			SexParticipantType.SELF,
			SexPace.DOM_ROUGH) {
		
		@Override
		public String getActionTitle() {
			return "Rough tail-pegging (self)";
		}

		@Override
		public String getActionDescription() {
			return "Roughly fuck [npc.her] own [npc.ass] with [npc.her] [npc.tail+].";
		}

		@Override
		public String getDescription() {
			return UtilText.returnStringAtRandom(
                    "[npc.A_moan+] escapes from between [npc.namePos] [npc.lips+] as [npc.she] roughly slam [npc.her] [npc.tail] deep inside [npc.her] [npc.asshole+], before starting to roughly fuck [npc.her] own [npc.ass].",
                    "Roughly pumping [npc.her] [npc.tail] in and out of [npc.her] [npc.asshole+], [npc.name] start letting out a series of delighted [npc.moans] as [npc.she] ruthlessly fuck [npc.her] own [npc.ass].",
                    "Forcefully driving [npc.her] [npc.tail] deep inside [npc.her] [npc.asshole], [npc.name] let out [npc.a_moan] as [npc.she] "
							+(Main.sex.getCharacterPerformingAction().hasPenis() && !Main.sex.getCharacterPerformingAction().hasVagina()
                            ? "start roughly grinding it up against [npc.her] prostate."
                            : "roughly fuck [npc.her] own [npc.ass+]."),
                    "Focusing on pleasuring [npc.her] [npc.ass+], [npc.name] start roughly slamming [npc.her] [npc.tail] in and out of [npc.her] [npc.asshole+].");
		}

	};
	
	public static final SexAction SUB_SELF_TAIL_ANUS_NORMAL = new SexAction(
			SexActionType.ONGOING,
			ArousalIncrease.THREE_NORMAL,
			ArousalIncrease.ONE_MINIMUM,
			CorruptionLevel.TWO_HORNY,
			Util.newHashMapOfValues(new Value<>(SexAreaPenetration.TAIL, SexAreaOrifice.ANUS)),
			SexParticipantType.SELF,
			SexPace.SUB_NORMAL) {
		
		@Override
		public String getActionTitle() {
			return "Tail-pegging (self)";
		}

		@Override
		public String getActionDescription() {
			return "Concentrate on fucking [npc.her] own [npc.ass] with [npc.her] [npc.tail+].";
		}

		@Override
		public String getDescription() {
			return UtilText.returnStringAtRandom(
                    "[npc.A_moan+] escapes from between [npc.namePos] [npc.lips+] as [npc.she] greedily push [npc.her] [npc.tail] deep inside [npc.her] [npc.asshole+].",
                    "Pumping [npc.her] [npc.tail] in and out of [npc.her] [npc.asshole+], [npc.name] start letting out a series of delighted [npc.moans] as [npc.she] rhythmically fuck [npc.her] own [npc.ass].",
                    "Driving [npc.her] [npc.tail] deep inside [npc.her] [npc.asshole], [npc.name] let out [npc.a_moan] as [npc.she] "
							+(Main.sex.getCharacterPerformingAction().hasPenis() && !Main.sex.getCharacterPerformingAction().hasVagina()
                            ? "use it to start massaging [npc.her] prostate."
                            : "steadily fuck [npc.her] own [npc.ass+]."),
                    "Focusing on pleasuring [npc.her] [npc.ass+], [npc.name] start pumping [npc.her] [npc.tail] in and out of [npc.her] [npc.asshole+].");
		}
	};
	
	public static final SexAction SUB_SELF_TAIL_ANUS_EAGER = new SexAction(
			SexActionType.ONGOING,
			ArousalIncrease.THREE_NORMAL,
			ArousalIncrease.ONE_MINIMUM,
			CorruptionLevel.THREE_DIRTY,
			Util.newHashMapOfValues(new Value<>(SexAreaPenetration.TAIL, SexAreaOrifice.ANUS)),
			SexParticipantType.SELF,
			SexPace.SUB_EAGER) {
		
		@Override
		public String getActionTitle() {
			return "Eager tail-pegging (self)";
		}

		@Override
		public String getActionDescription() {
			return "Eagerly start fucking [npc.her] own [npc.ass] with [npc.her] [npc.tail+].";
		}

		@Override
		public String getDescription() {
			return UtilText.returnStringAtRandom(
                    "[npc.A_moan+] escapes from between [npc.namePos] [npc.lips+] as [npc.she] eagerly slam [npc.her] [npc.tail] deep inside [npc.her] [npc.asshole+], before starting to desperately fuck [npc.her] own [npc.ass].",
                    "Enthusiastically pumping [npc.her] [npc.tail] in and out of [npc.her] [npc.asshole+], [npc.name] start letting out a series of delighted [npc.moans] as [npc.she] frantically fuck [npc.her] own [npc.ass].",
                    "Desperately driving [npc.her] [npc.tail] deep inside [npc.her] [npc.asshole], [npc.name] let out [npc.a_moan] as [npc.she] "
							+(Main.sex.getCharacterPerformingAction().hasPenis() && !Main.sex.getCharacterPerformingAction().hasVagina()
                            ? "start eagerly grinding it up against [npc.her] prostate."
                            : "eagerly fuck [npc.her] own [npc.ass+]."),
                    "Focusing on pleasuring [npc.her] [npc.ass+], [npc.name] eagerly start slamming [npc.her] [npc.tail] in and out of [npc.her] [npc.asshole+].");
		}
	};
	
	public static final SexAction SELF_TAIL_ANUS_STOP_PENETRATION = new SexAction(
			SexActionType.STOP_ONGOING,
			ArousalIncrease.ONE_MINIMUM,
			ArousalIncrease.ONE_MINIMUM,
			CorruptionLevel.ZERO_PURE,
			Util.newHashMapOfValues(new Value<>(SexAreaPenetration.TAIL, SexAreaOrifice.ANUS)),
			SexParticipantType.SELF) {

		@Override
		public String getActionTitle() {
			return "Stop tail-peg (self)";
		}

		@Override
		public String getActionDescription() {
			return "Stop fucking [npc.her]self with [npc.her] [npc.tail].";
		}

		@Override
		public String getDescription() {
            return "With [npc.a_groan+], [npc.name] slide [npc.her] [npc.tail+] out of [npc.her] [npc.asshole+].";
		}
	};
}
