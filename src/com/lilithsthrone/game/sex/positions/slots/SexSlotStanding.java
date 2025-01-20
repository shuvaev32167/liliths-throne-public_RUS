package com.lilithsthrone.game.sex.positions.slots;

import com.lilithsthrone.game.character.GameCharacter;
import com.lilithsthrone.main.Main;

/**
 * All SexSlots that are used in the STANDING position.
 * 
 * @since 0.3.4
 * @version 0.3.4
 * @author Innoxia
 */
public class SexSlotStanding {

	public static final SexSlot STANDING_DOMINANT = new SexSlot(
			"Standing",
			"standing",
            "[npc.Name] lean heavily into [npc2.namePos] [npc2.breasts] и let out [npc.a_moan+] as [npc.she] prepare to reach [npc.her] climax.",
			true,
			SexSlotTag.STANDING) {
		@Override
		public String getOrgasmDescription(GameCharacter orgasmingCharacter, GameCharacter targetedCharacter) {
			SexSlot targetedSlot = Main.sex.getSexPositionSlot(targetedCharacter);
			if(orgasmingCharacter.equals(targetedCharacter)) {
                return "[npc.Name] let out [npc.a_moan+] as [npc.she] prepare to reach [npc.her] climax.";
			}
			if(targetedSlot.hasTag(SexSlotTag.PERFORMING_ORAL)) {
                return "With a small thrust of [npc.her] [npc.hips], [npc.name] push [npc.her] groin into [npc2.namePos] [npc2.face], before letting out [npc.a_moan+] as [npc.she] reach [npc.her] climax.";
			}
			if(targetedSlot.hasTag(SexSlotTag.PERFORMING_ORAL_BEHIND)) {
                return "With a small backwards thrust of [npc.her] [npc.hips], [npc.name] push [npc.her] rear end back into [npc2.namePos] [npc2.face], before letting out [npc.a_moan+] as [npc.she] reach [npc.her] climax.";
			}
			if(targetedSlot.hasTag(SexSlotTag.STANDING_BEHIND)) {
                return "Leaning back into [npc2.name], [npc.name] let out [npc.a_moan+] as [npc.she] reach [npc.her] climax.";
			}
			if(targetedCharacter.isTaur()) {
                return "[npc.Name] reach around and wrap [npc.her] [npc.arms] around [npc2.namePos] back, pulling [npc2.herHim] close and letting out [npc.a_moan+] as [npc.she] prepare to reach [npc.her] climax.";
			}
            return "[npc.Name] reach around and grab [npc2.namePos] [npc2.ass+], pulling [npc2.herHim] close and letting out [npc.a_moan+] as [npc.she] prepare to reach [npc.her] climax.";
		}
	};
	
	public static final SexSlot STANDING_DOMINANT_TWO = new SexSlot(STANDING_DOMINANT) {
		@Override
		public String getDescription() {
			return "standing (2nd)";
		}
		@Override
		public String getOrgasmDescription(GameCharacter orgasmingCharacter, GameCharacter targetedCharacter) {
			return STANDING_DOMINANT.getOrgasmDescription(orgasmingCharacter, targetedCharacter);
		}
	};
	public static final SexSlot STANDING_DOMINANT_THREE = new SexSlot(STANDING_DOMINANT) {
		@Override
		public String getDescription() {
			return "standing (3rd)";
		}
		@Override
		public String getOrgasmDescription(GameCharacter orgasmingCharacter, GameCharacter targetedCharacter) {
			return STANDING_DOMINANT.getOrgasmDescription(orgasmingCharacter, targetedCharacter);
		}
	};
	public static final SexSlot STANDING_DOMINANT_FOUR = new SexSlot(STANDING_DOMINANT) {
		@Override
		public String getDescription() {
			return "standing (4th)";
		}
		@Override
		public String getOrgasmDescription(GameCharacter orgasmingCharacter, GameCharacter targetedCharacter) {
			return STANDING_DOMINANT.getOrgasmDescription(orgasmingCharacter, targetedCharacter);
		}
	};

	public static final SexSlot STANDING_SUBMISSIVE = new SexSlot(
			"Standing",
			"standing (in front)",
            "[npc.Name] lean heavily into [npc2.namePos] [npc2.breasts] и let out [npc.a_moan+] as [npc.she] prepare to reach [npc.her] climax.",
			true);

	public static final SexSlot STANDING_SUBMISSIVE_TWO = new SexSlot(STANDING_SUBMISSIVE) {
		@Override
		public String getDescription() {
			return "standing (2nd in front)";
		}
	};
	public static final SexSlot STANDING_SUBMISSIVE_THREE = new SexSlot(STANDING_SUBMISSIVE) {
		@Override
		public String getDescription() {
			return "standing (3rd in front)";
		}
	};
	public static final SexSlot STANDING_SUBMISSIVE_FOUR = new SexSlot(STANDING_SUBMISSIVE) {
		@Override
		public String getDescription() {
			return "standing (4th in front)";
		}
	};

	public static final SexSlot STANDING_SUBMISSIVE_BEHIND = new SexSlot(
			"Standing",
			"standing (behind)",
            "[npc.Name] pull [npc2.name] back in against [npc.her] [npc.breasts] и let out [npc.a_moan+] as [npc.she] prepare to reach [npc.her] climax.",
			true,
			SexSlotTag.STANDING_BEHIND);

	public static final SexSlot STANDING_SUBMISSIVE_BEHIND_TWO = new SexSlot(STANDING_SUBMISSIVE) {
		@Override
		public String getDescription() {
			return "standing (2nd behind)";
		}
	};
	public static final SexSlot STANDING_SUBMISSIVE_BEHIND_THREE = new SexSlot(STANDING_SUBMISSIVE) {
		@Override
		public String getDescription() {
			return "standing (3rd behind)";
		}
	};
	public static final SexSlot STANDING_SUBMISSIVE_BEHIND_FOUR = new SexSlot(STANDING_SUBMISSIVE) {
		@Override
		public String getDescription() {
			return "standing (4th behind)";
		}
	};

	public static final SexSlot PERFORMING_ORAL = new SexSlot(
			"Performing oral (front)",
			"performing oral (front)",
            "[npc.Name] reach up and place a [npc.hand] on one of [npc2.namePos] [npc2.legs], before letting out [npc.a_moan+] as [npc.she] prepare to reach [npc.her] climax.",
			false,
			SexSlotTag.PERFORMING_ORAL) {
		@Override
		public boolean isStanding(GameCharacter target) {
			return Main.sex.getTargetedPartner(target).isSizeDifferenceTallerThan(target);
		}
	};
	
	public static final SexSlot PERFORMING_ORAL_TWO = new SexSlot(
			"Performing oral (front)",
			"performing oral (2nd front)",
			null,
			false,
			SexSlotTag.PERFORMING_ORAL) {
		@Override
		public boolean isStanding(GameCharacter target) {
			return Main.sex.getTargetedPartner(target).isSizeDifferenceTallerThan(target);
		}
	};

	public static final SexSlot PERFORMING_ORAL_THREE = new SexSlot(
			"Performing oral (front)",
			"performing oral (3rd front)",
			null,
			false,
			SexSlotTag.PERFORMING_ORAL) {
		@Override
		public boolean isStanding(GameCharacter target) {
			return Main.sex.getTargetedPartner(target).isSizeDifferenceTallerThan(target);
		}
	};

	public static final SexSlot PERFORMING_ORAL_FOUR = new SexSlot(
			"Performing oral (front)",
			"performing oral (4th front)",
			null,
			false,
			SexSlotTag.PERFORMING_ORAL) {
		@Override
		public boolean isStanding(GameCharacter target) {
			return Main.sex.getTargetedPartner(target).isSizeDifferenceTallerThan(target);
		}
	};

	public static final SexSlot PERFORMING_ORAL_BEHIND = new SexSlot(
			"Performing oral (behind)",
			"performing oral (behind)",
            "[npc.Name] reach up and place a [npc.hand] on one of [npc2.namePos] [npc2.legs], before letting out [npc.a_moan+] as [npc.she] prepare to reach [npc.her] climax.",
			false,
			SexSlotTag.PERFORMING_ORAL_BEHIND) {
		@Override
		public boolean isStanding(GameCharacter target) {
			return Main.sex.getTargetedPartner(target).isSizeDifferenceTallerThan(target);
		}
	};

	public static final SexSlot PERFORMING_ORAL_BEHIND_TWO = new SexSlot(
			"Performing oral (behind)",
			"performing oral (2nd behind)",
			null,
			false,
			SexSlotTag.PERFORMING_ORAL_BEHIND) {
		@Override
		public boolean isStanding(GameCharacter target) {
			return Main.sex.getTargetedPartner(target).isSizeDifferenceTallerThan(target);
		}
	};

	public static final SexSlot PERFORMING_ORAL_BEHIND_THREE = new SexSlot(
			"Performing oral (behind)",
			"performing oral (3rd behind)",
			null,
			false,
			SexSlotTag.PERFORMING_ORAL_BEHIND) {
		@Override
		public boolean isStanding(GameCharacter target) {
			return Main.sex.getTargetedPartner(target).isSizeDifferenceTallerThan(target);
		}
	};

	public static final SexSlot PERFORMING_ORAL_BEHIND_FOUR = new SexSlot(
			"Performing oral (behind)",
			"performing oral (4th behind)",
			null,
			false,
			SexSlotTag.PERFORMING_ORAL_BEHIND) {
		@Override
		public boolean isStanding(GameCharacter target) {
			return Main.sex.getTargetedPartner(target).isSizeDifferenceTallerThan(target);
		}
	};
}
