package com.lilithsthrone.game.dialogue.places.dominion.lilayashome;

import com.lilithsthrone.game.Game;
import com.lilithsthrone.game.character.GameCharacter;
import com.lilithsthrone.game.character.attributes.*;
import com.lilithsthrone.game.character.effects.Perk;
import com.lilithsthrone.game.character.effects.StatusEffect;
import com.lilithsthrone.game.character.npc.NPC;
import com.lilithsthrone.game.character.npc.NPCFlagValue;
import com.lilithsthrone.game.character.persona.SexualOrientation;
import com.lilithsthrone.game.character.race.Subspecies;
import com.lilithsthrone.game.dialogue.DialogueFlagValue;
import com.lilithsthrone.game.dialogue.DialogueNode;
import com.lilithsthrone.game.dialogue.DialogueNodeType;
import com.lilithsthrone.game.dialogue.companions.CompanionManagement;
import com.lilithsthrone.game.dialogue.companions.OccupantManagementDialogue;
import com.lilithsthrone.game.dialogue.places.dominion.nightlife.NightlifeDistrict;
import com.lilithsthrone.game.dialogue.responses.Response;
import com.lilithsthrone.game.dialogue.responses.ResponseEffectsOnly;
import com.lilithsthrone.game.dialogue.responses.ResponseSex;
import com.lilithsthrone.game.dialogue.responses.ResponseTag;
import com.lilithsthrone.game.dialogue.utils.BodyChanging;
import com.lilithsthrone.game.dialogue.utils.MiscDialogue;
import com.lilithsthrone.game.dialogue.utils.UtilText;
import com.lilithsthrone.game.occupantManagement.slave.SlaveJob;
import com.lilithsthrone.game.occupantManagement.slave.SlaveJobSetting;
import com.lilithsthrone.game.occupantManagement.slave.SlavePermissionSetting;
import com.lilithsthrone.game.sex.ImmobilisationType;
import com.lilithsthrone.game.sex.SexPace;
import com.lilithsthrone.game.sex.managers.universal.SMBath;
import com.lilithsthrone.game.sex.managers.universal.SMGeneric;
import com.lilithsthrone.game.sex.managers.universal.SMShower;
import com.lilithsthrone.game.sex.positions.SexPosition;
import com.lilithsthrone.game.sex.positions.slots.SexSlot;
import com.lilithsthrone.game.sex.positions.slots.SexSlotLyingDown;
import com.lilithsthrone.game.sex.positions.slots.SexSlotStanding;
import com.lilithsthrone.main.Main;
import com.lilithsthrone.utils.Units;
import com.lilithsthrone.utils.Util;
import com.lilithsthrone.utils.Util.Value;
import com.lilithsthrone.utils.colours.PresetColour;
import com.lilithsthrone.utils.time.DateAndTime;
import com.lilithsthrone.utils.time.SolarElevationAngle;
import com.lilithsthrone.utils.translate.russian.Morpher;
import com.lilithsthrone.world.places.GenericPlace;
import com.lilithsthrone.world.places.PlaceUpgrade;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.*;
import java.util.stream.Collectors;

import static com.lilithsthrone.utils.Constants.RUSSIAN_LOCALE;

/**
 * @since 0.1.75
 * @version 0.3.5.5
 * @author Innoxia
 */
public class RoomPlayer {
	
	private static int sleepTimeInMinutes = 240;

    private static GameCharacter makeupTarget;
    
    private static List<GameCharacter> slavesWashing;
    
    public static GameCharacter getMakeupTarget() {
        if(makeupTarget==null) {
            return Main.game.getPlayer();
        }
        return makeupTarget;
    }
    
    /**
     * @param sleepTimeInMinutes Calls an endTurn(sleepTimeInMinutes*60) so that NPCs have their status effects updated before the next scene is parsed.
     */
	public static void applySleep(int sleepTimeInMinutes) {
		List<GameCharacter> charactersPresent = new ArrayList<>(LilayaHomeGeneric.getSlavesAndOccupantsPresent());
		charactersPresent.addAll(Main.game.getPlayer().getCompanions());
		charactersPresent.add(Main.game.getPlayer());

		for(GameCharacter character : charactersPresent) {
			character.applySleep(sleepTimeInMinutes);
		}
		
		slavesPresentWhenGoingToSleep = slavesInRoom(Main.game.getHourOfDay());
//		Main.game.getTextStartStringBuilder().append("X: "+Main.game.getHourOfDay());

		Main.game.getPlayer().setActive(false);
		Main.game.endTurn(sleepTimeInMinutes*60);
		Main.game.getPlayer().setActive(true);
		Main.game.endTurnTimeTakenAddition = Main.game.endTurnTimeTaken;

		slavesPresentWhenWaking = slavesInRoom(Main.game.getHourOfDay());
		slavesToWakePlayer = slavesInRoom(Main.game.getHourOfDay()).stream().filter((npc) -> npc.hasSlaveJobSetting(SlaveJob.BEDROOM, SlaveJobSetting.BEDROOM_WAKE_UP)).collect(Collectors.toList());
	}
	
    public static final DialogueNode ROOM_SET_ALARM = new DialogueNode("Установить будильник", "", true) {
		@Override
		public void applyPreParsingEffects() {
			super.applyPreParsingEffects();
			if(Main.game.getDialogueFlags().getSavedLong("player_phone_alarm") < 0) {
				// If unset, default to 8:00 AM
				Main.game.getDialogueFlags().setSavedLong("player_phone_alarm", 8*60);
			}
		}
		@Override
		public String getContent() {
			long alarmTime = Main.game.getDialogueFlags().getSavedLong("player_phone_alarm");
			String alarmTimeStr = Units.time(LocalTime.ofSecondOfDay(alarmTime*60));
			return "<div><p style='text-align:center;'>Taking out your phone, you open the alarm app and prepare to set a time for it to go off...</p></div>"
					+ "<div class='cosmetics-inner-container' style='margin:1% 10%; width:78%; padding:1%; box-sizing:border-box; position:relative;'>"
						+ "<p style='margin:0; padding:0;'>"
                    + "<b>Установить будильник</b>"
						+"</p>"
						+ "<div class='container-full-width' style='width:35%; text-align:center; float:left; position:relative; padding:0; margin:0;'>"
							+ "<div id='PLAYER_ALARM_DECREASE_LARGE' class='normal-button' style='width:48%; margin:1%; padding:0;'>"
								+ "[style.boldBad(-1 hour)]"
							+ "</div>"
							+ "<div id='PLAYER_ALARM_DECREASE' class='normal-button' style='width:48%; margin:1%; padding:0;'>"
								+ "[style.boldBadMinor(-5 minutes)]"
							+ "</div>"
						+ "</div>"
						+ "<div class='container-full-width' style='width:28%; margin:1%; padding:0; text-align:center; float:left; position:relative;'>"
							+ alarmTimeStr
						+ "</div>"
						+ "<div class='container-full-width' style='width:35%; text-align:center; float:left; position:relative; padding:0; margin:0;'>"
							+ "<div id='PLAYER_ALARM_INCREASE' class='normal-button' style='width:48%; margin:1%; padding:0;'>"
								+ "[style.boldGoodMinor(+5 minutes)]"
							+ "</div>"
							+ "<div id='PLAYER_ALARM_INCREASE_LARGE' class='normal-button' style='width:48%; margin:1%; padding:0;'>"
								+ "[style.boldGood(+1 hour)]"
							+ "</div>"
						+ "</div>"
					+ "</div>";
		}
		@Override
		public Response getResponse(int responseTab, int index) {
			if(index == 1) {
                return new Response("Установить будильник", "Your alarm will be set to the time that you've entered.", Main.game.getSavedDialogueNode());

			} else if(index == 2) {
				return new Response("Delete alarm", "Delete your alarm, leaving it unset.", Main.game.getSavedDialogueNode()) {
					@Override
					public void effects() {
						Main.game.getDialogueFlags().removeSavedLong("player_phone_alarm");
					}
				};

			}

			return null;
		}
		@Override
		public DialogueNodeType getDialogueNodeType() {
			return DialogueNodeType.OPTIONS;
		}
	};
	
	private static String getShowerSlavesDescription(List<GameCharacter> slavesWashing) {
		StringBuilder sb = new StringBuilder();
		
		boolean soloSlave = slavesWashing.size()==1;
		List<String> names = new ArrayList<>();
		for(GameCharacter npc : slavesWashing) {
			names.add("<span style='color:"+npc.getFemininity().getColour().toWebHexString()+";'>"+npc.getName()+"</span>");
		}
		
		sb.append("<p>");
			if(soloSlave) {
				sb.append(UtilText.parse(slavesWashing,
						"Having been instructed to assist you in washing yourself, your slave, "+Util.stringsToStringList(names, false)+", similarly leaves [npc.her] clothes by the door before following you into the bathroom."));
					sb.append(UtilText.parse(slavesWashing,
							" Thankfully, your luxurious shower is spacious enough that [npc.name] can quite comfortably fit in alongside you."));
				
			} else {
				sb.append("Having been instructed to assist you in washing yourself, your slaves, "+Util.stringsToStringList(names, false)+", similarly leave their clothes by the door before following you into the bathroom.");
					sb.append(UtilText.parse(slavesWashing,
							" Thankfully, your luxurious shower is spacious enough that your slaves can quite comfortably fit in alongside you."));
			}
		sb.append("</p>");
		
		// Slave reactions while helping wash:
		
		List<GameCharacter> washingNice = slavesWashing.stream().filter(npc -> npc.getObedienceBasic()==ObedienceLevelBasic.OBEDIENT || npc.getAffectionLevelBasic(Main.game.getPlayer())==AffectionLevelBasic.LIKE).collect(Collectors.toList());
		boolean firstWashing = true;
		for(GameCharacter npc : washingNice) {
			sb.append("<p>");
			List<String> start = new ArrayList<>();
			List<String> speech = new ArrayList<>();
			
			if(npc.hasSlavePermissionSetting(SlavePermissionSetting.BEHAVIOUR_PROFESSIONAL)) {
				if(firstWashing) {
					if(Main.game.getPlayer().hasHair()) {
						start.add("Turning on the taps, [npc.name] dutifully starts to help you in washing your [pc.hair(true)] and body. Raising [npc.her] voice so as to be heard over the sound of running water, [npc.she] says,");
					} else {
						start.add("Turning on the taps, [npc.name] dutifully starts to help you in washing your body. Raising [npc.her] voice so as to be heard over the sound of running water, [npc.she] says,");
					}
				} else {
					start.add("Picking up a bar of soap, [npc.name] assists [npc.her] fellow "+(washingNice.size()>2?"slaves":"slave")+" in cleaning your body. As [npc.she] sets about [npc.her] task, [npc.she] says,");
					start.add("Stepping forwards, with a bar of soap in [npc.hand], [npc.name] sets about cleaning your body, saying as [npc.she] does so,");
					start.add("Stepping up beside [npc.her] fellow "+(washingNice.size()>2?"slaves":"slave")+", [npc.name] starts cleaning your body, saying,");
				}
				speech.add("[npc.speech(I hope this is to your satisfaction, [pc.name].)]");
				speech.add("[npc.speech(Please let me know if you need me to do anything differently, [pc.name].)]");
				speech.add("[npc.speech(I'll be sure to do a good job in cleaning you, [pc.name].)]");
				
			} else if(npc.hasSlavePermissionSetting(SlavePermissionSetting.BEHAVIOUR_SEDUCTIVE)) {
				if(firstWashing) {
					if(npc.hasBreasts()) {
						start.add("Turning on the taps, [npc.name] moves up close behind you, before soaping your back down and starting to clean you."
								+ " Suddenly, [npc.she] steps forwards, and, pressing [npc.her] [npc.breasts+] against your back, [npc.she] seductively [npc.moans],");
					} else {
						start.add("Turning on the taps, [npc.name] moves up close behind you, before soaping your back down and starting to clean you."
								+ " Suddenly, [npc.she] steps forwards, and, pressing [npc.herself] against your back, [npc.she] seductively [npc.moans],");
					}
				} else {
					if(npc.hasBreasts()) {
						start.add("Stepping forwards, [npc.name] presses [npc.her] [npc.breasts+] against your back, before seductively [npc.moaning],");
						start.add("Rubbing a bar of soap over your back, [npc.name] suddenly steps forwards, pressing [npc.her] [npc.breasts+] against you as [npc.she] seductively [npc.moans],");
						start.add("Deciding to start by cleaning your back, [npc.name] steps around behind you, before leaning forwards and pressing [npc.her] [npc.breasts+] against you, [npc.moaning],");
					} else {
						start.add("Stepping forwards, [npc.name] presses [npc.herself] against your back, before seductively [npc.moaning],");
						start.add("Rubbing a bar of soap over your back, [npc.name] suddenly steps forwards, pressing [npc.herself] against you as [npc.she] seductively [npc.moans],");
						start.add("Deciding to start by cleaning your back, [npc.name] steps around behind you, before leaning forwards and pressing [npc.herself] against you, [npc.moaning],");
					}
				}
				speech.add("[npc.speech(You don't mind if I get this close, do you?)]");
				speech.add("[npc.speech(You like the feeling of me being this close, don't you?)]");
				speech.add("[npc.speech(That's right, relax and let me take care of you...)]");
				speech.add("[npc.speech(Perhaps once you're clean, you'd like to do something dirty with me...)]");
				
			} else if(npc.hasSlavePermissionSetting(SlavePermissionSetting.BEHAVIOUR_SLUTTY)) {
				if(firstWashing) {
					if(npc.hasBreasts()) {
						start.add("Turning on the taps, [npc.name] moves up close behind you, before immediately pressing [npc.her] [npc.breasts+] against your back."
								+ " Obviously seeing this as an opportunity to have some fun with you, [npc.she] reaches around to start groping your body, seductively [npc.moaning] into your [pc.ear] as [npc.she] does this,");
					} else {
						start.add("Turning on the taps, [npc.name] moves up close behind you, before immediately pressing [npc.her] body in against your back."
								+ " Obviously seeing this as an opportunity to have some fun with you, [npc.she] reaches around to start groping your body, seductively [npc.moaning] into your [pc.ear] as [npc.she] does this,");
					}
				} else {
					if(npc.hasBreasts()) {
						start.add("Stepping forwards, [npc.name] presses [npc.her] [npc.breasts+] against your back, before seductively [npc.moaning],");
						start.add("Rubbing a bar of soap over your back, [npc.name] suddenly steps forwards, pressing [npc.her] [npc.breasts+] against you as [npc.she] seductively [npc.moans],");
						start.add("Deciding to start by cleaning your back, [npc.name] steps around behind you, before leaning forwards and pressing [npc.her] [npc.breasts+] against you, [npc.moaning],");
					} else {
						start.add("Stepping forwards, [npc.name] presses [npc.herself] against your back, before seductively [npc.moaning],");
						start.add("Rubbing a bar of soap over your back, [npc.name] suddenly steps forwards, pressing [npc.herself] against you as [npc.she] seductively [npc.moans],");
						start.add("Deciding to start by cleaning your back, [npc.name] steps around behind you, before leaning forwards and pressing [npc.herself] against you, [npc.moaning],");
					}
				}
				speech.add("[npc.speech(Oh yeah, you like me feeling you up like this, don't you?)]");
				speech.add("[npc.speech(All this close contact is really turning me on; you wanna fuck after this?)]");
				speech.add("[npc.speech(This is making me so horny... You're up for having a good fuck after this, aren't you?)]");
				
			} else if(npc.hasSlavePermissionSetting(SlavePermissionSetting.BEHAVIOUR_STANDARD)
					|| npc.hasSlavePermissionSetting(SlavePermissionSetting.BEHAVIOUR_WHOLESOME)) {
				if(npc.isShy()) {
					if(firstWashing) {
						if(Main.game.getPlayer().hasHair()) {
							start.add("Turning on the taps, [npc.name] nervously shuffles forwards, before starting to help you wash your [pc.hair(true)] and body."
									+ " Doing [npc.her] best to be heard over the sound of running water, [npc.she] raises [npc.her] voice a little and says,");
						} else {
							start.add("Turning on the taps, [npc.name] nervously shuffles forwards, before starting to help you wash your body."
									+ " Doing [npc.her] best to be heard over the sound of running water, [npc.she] raises [npc.her] voice a little and says,");
						}
					} else {
						start.add("Clutching a bar of soap, [npc.name] nervously shuffles forwards, before helping [npc.her] fellow "+(washingNice.size()>2?"slaves":"slave")+" to clean you. Raising [npc.her] voice a little, [npc.she] says,");
						start.add("Stepping forwards with a bar of soap in [npc.hand], [npc.name] nervously sets about cleaning your body. Squeaking above the noise of running water, [npc.she] says,");
						start.add("Stepping up beside [npc.her] fellow "+(washingNice.size()>2?"slaves":"slave")+", [npc.name] blushes as [npc.she] starts to clean your body, saying,");
					}
					if(npc.hasSlavePermissionSetting(SlavePermissionSetting.BEHAVIOUR_WHOLESOME)) {
						speech.add("[npc.speech(I love being so close to you, [pc.name]?)]");
						speech.add("[npc.speech(I'm so happy to be able to help you like this...)]");
						speech.add("[npc.speech(Getting to help you like this makes me so happy...)]");
					} else {
						speech.add("[npc.speech(I'm not doing this wrong, am I, [pc.name]?)]");
						speech.add("[npc.speech(Just let me know if I'm doing this wrong...)]");
						speech.add("[npc.speech(I hope this is how you wanted me to wash you...)]");
					}
					
				} else if(npc.isKind()) {
					if(firstWashing) {
						if(Main.game.getPlayer().hasHair()) {
							start.add("Turning on the taps, [npc.name] happily steps forwards, before gently starting to help you wash your [pc.hair(true)] and body."
									+ " Doing [npc.her] best to be heard over the sound of running water, [npc.she] raises [npc.her] voice and says,");
						} else {
							start.add("Turning on the taps, [npc.name] happily steps forwards, before gently starting to help you wash your body."
									+ " Doing [npc.her] best to be heard over the sound of running water, [npc.she] raises [npc.her] voice and says,");
						}
					} else {
						start.add("Clutching a bar of soap, [npc.name] happily steps forwards, before helping [npc.her] fellow "+(washingNice.size()>2?"slaves":"slave")+" to clean you. Raising [npc.her] voice, [npc.she] says,");
						start.add("Stepping forwards with a bar of soap in [npc.hand], [npc.name] happily sets about cleaning your body. Raising [npc.her] voice above the noise of running water, [npc.she] says,");
						start.add("Stepping up beside [npc.her] fellow "+(washingNice.size()>2?"slaves":"slave")+", [npc.name] smiles as [npc.she] starts to clean your body, saying,");
					}
					if(npc.hasSlavePermissionSetting(SlavePermissionSetting.BEHAVIOUR_WHOLESOME)) {
						speech.add("[npc.speech(I love being so close to you, [pc.name]. Please let me know if you want me to anything differently.)]");
						speech.add("[npc.speech(I'm so happy to be able to help you like this; I'm so lucky to be owned by you.)]");
						speech.add("[npc.speech(Let me take care of you, [pc.name]; I want to show you how much you mean to me.)]");
					} else {
						speech.add("[npc.speech(Let me know if you need me to do anything differently, ok, [pc.name]?)]");
						speech.add("[npc.speech(Please tell me if you need me to do anything differently.)]");
						speech.add("[npc.speech(Let me take care of you, [pc.name].)]");
					}
					
				} else {
					if(firstWashing) {
						if(Main.game.getPlayer().hasHair()) {
							start.add("Turning on the taps, [npc.name] steps forwards, before starting to help you wash your [pc.hair(true)] and body."
									+ " Doing [npc.her] best to be heard over the sound of running water, [npc.she] raises [npc.her] voice and says,");
						} else {
							start.add("Turning on the taps, [npc.name] steps forwards, before starting to help you wash your body."
									+ " Doing [npc.her] best to be heard over the sound of running water, [npc.she] raises [npc.her] voice and says,");
						}
					} else {
						start.add("Clutching a bar of soap, [npc.name] steps forwards, before helping [npc.her] fellow "+(washingNice.size()>2?"slaves":"slave")+" to clean you. Raising [npc.her] voice, [npc.she] says,");
						start.add("Stepping forwards with a bar of soap in [npc.hand], [npc.name] sets about cleaning your body. Raising [npc.her] voice above the noise of running water, [npc.she] says,");
						start.add("Stepping up beside [npc.her] fellow "+(washingNice.size()>2?"slaves":"slave")+", [npc.name] smiles as [npc.she] starts to clean your body, saying,");
					}
					if(npc.hasSlavePermissionSetting(SlavePermissionSetting.BEHAVIOUR_WHOLESOME)) {
						speech.add("[npc.speech(I love being so close to you, [pc.name]. Please let me know if you want me to anything differently.)]");
						speech.add("[npc.speech(I'm so happy to be able to help you like this; I'm so lucky to be owned by you.)]");
						speech.add("[npc.speech(Let me take care of you, [pc.name]; I want to show you how much you mean to me.)]");
					} else {
						speech.add("[npc.speech(Let me know if you need me to do anything differently.)]");
						speech.add("[npc.speech(Just tell me if you need me to do anything differently.)]");
						speech.add("[npc.speech(Let me take care of you, [pc.name].)]");
					}
				}
			}
			sb.append(UtilText.parse(npc,Util.randomItemFrom(start)));
			sb.append(" ");
			sb.append(UtilText.parse(npc,Util.randomItemFrom(speech)));
			sb.append("</p>");
			firstWashing = false;
		}
		
		List<GameCharacter> washingRude = new ArrayList<>(slavesWashing);
		washingRude.removeAll(washingNice);
		for(GameCharacter npc : washingRude) {
			sb.append("<p>");
			List<String> start = new ArrayList<>();
			List<String> speech = new ArrayList<>();
			
				if(npc.isShy()) {
					if(firstWashing) {
						start.add("Reluctantly turning on the taps, [npc.name] steps forwards, before half-heartedly starting to help you wash yourself."
								+ " Trying to use the sound of the running water to mask [npc.her] comments, [npc.she] mutters,");
					} else {
						start.add("Reluctantly stepping forwards, [npc.name] casts [npc.her] gaze to the floor as [npc.she] half-heartedly starts to help you wash yourself. Letting out a weary sigh, [npc.she] mutters,");
						start.add("Looking down at the floor, [npc.name] steps forwards and reluctantly starts to help you wash yourself. With an exasperated sigh, [npc.she] mutters under [npc.her] breath,");
						start.add("Clearly not happy about it, [npc.name] nevertheless steps forwards and half-heartedly starts to help you wash yourself. Muttering under [npc.her] breath, [npc.she] sighs,");
					}
					speech.add("[npc.speech(I hate this...)]");
					speech.add("[npc.speech(How did I ever end up having to do things like this...)]");
					speech.add("[npc.speech(This sucks...)]");
					
				} else if(npc.isSelfish()) {
					if(firstWashing) {
						start.add("Reluctantly turning on the taps, [npc.name] steps forwards, before half-heartedly starting to help you wash yourself."
								+ " With a clear tone of animosity in [npc.her] voice, [npc.she] complains,");
					} else {
						start.add("Reluctantly stepping forwards, [npc.name] half-heartedly sets about helping you wash yourself. Letting out a weary sigh, [npc.she] complains,");
						start.add("Rolling [npc.her] [npc.eyes], [npc.name] steps forwards and reluctantly starts to help you wash yourself, complaining as [npc.she] does so,");
						start.add("With an annoyed sigh, [npc.name] steps forwards and half-heartedly starts to help you wash yourself. Not even trying to hide [npc.her] annoyance, [npc.she] angrily says,");
					}
					speech.add("[npc.speech(I really hate doing this, you know? Just get some other slave to help you next time!)]");
					speech.add("[npc.speech(Why do you insist on getting me to do this?! Isn't it clear that I hate helping you out like this?!)]");
					speech.add("[npc.speech(This really fucking sucks. How about you get some other slave to wash you next time?)]");
					
				} else {
					if(firstWashing) {
						start.add("Reluctantly turning on the taps, [npc.name] steps forwards, before half-heartedly starting to help you wash yourself. In a clear sign of displeasure, [npc.she] sighs,");
					} else {
						start.add("Reluctantly stepping forwards, [npc.name] half-heartedly sets about helping you wash yourself. Letting out a weary sigh, [npc.she] sighs,");
						start.add("Rolling [npc.her] [npc.eyes], [npc.name] steps forwards and reluctantly starts to help you wash yourself, complaining as [npc.she] does so,");
						start.add("With an annoyed sigh, [npc.name] steps forwards and half-heartedly starts to help you wash yourself. Not even trying to hide [npc.her] annoyance, [npc.she] sighs,");
					}
					speech.add("[npc.speech(Is there nobody else you can ask to do this?)]");
					speech.add("[npc.speech(I really wish you didn't make me do this...)]");
					speech.add("[npc.speech(This sucks...)]");
					speech.add("[npc.speech(I really hate having to do this, you know?)]");
				}

			sb.append(UtilText.parse(npc,Util.randomItemFrom(start)));
			sb.append(" ");
			sb.append(UtilText.parse(npc,Util.randomItemFrom(speech)));
			sb.append("</p>");
			firstWashing = false;
		}
		
		return sb.toString();
	}
	
	private static String getBathSlavesDescription(List<GameCharacter> slavesWashing) {
		StringBuilder sb = new StringBuilder();
		
		boolean soloSlave = slavesWashing.size()==1;
		List<String> names = new ArrayList<>();
		for(GameCharacter npc : slavesWashing) {
			names.add("<span style='color:"+npc.getFemininity().getColour().toWebHexString()+";'>"+npc.getName()+"</span>");
		}
		
		sb.append("<p>");
			if(soloSlave) {
				sb.append(UtilText.parse(slavesWashing,
						"Having been instructed to assist you in washing yourself, your slave, "+Util.stringsToStringList(names, false)+", similarly leaves [npc.her] clothes by the door before following you into the bathroom."));
					sb.append(UtilText.parse(slavesWashing,
							" Thankfully, your luxurious bathroom is spacious enough that the room doesn't feel overcrowded with [npc.name] in here as well."));
				
			} else {
				sb.append("Having been instructed to assist you in washing yourself, your slaves, "+Util.stringsToStringList(names, false)+", similarly leave their clothes by the door before following you into the bathroom.");
					sb.append(UtilText.parse(slavesWashing,
							" Thankfully, your luxurious bathroom is spacious enough that the room doesn't feel overcrowded with your slaves in here as well."));
			}
		sb.append("</p>");
		
		// Slave reactions while helping wash:
		
		List<GameCharacter> washingNice = slavesWashing.stream().filter(npc -> npc.getObedienceBasic()==ObedienceLevelBasic.OBEDIENT || npc.getAffectionLevelBasic(Main.game.getPlayer())==AffectionLevelBasic.LIKE).collect(Collectors.toList());
		boolean firstWashing = true;
		for(GameCharacter npc : washingNice) {
			sb.append("<p>");
			List<String> start = new ArrayList<>();
			List<String> speech = new ArrayList<>();
			
			if(npc.hasSlavePermissionSetting(SlavePermissionSetting.BEHAVIOUR_PROFESSIONAL)) {
				if(firstWashing) {
					if(Main.game.getPlayer().hasHair()) {
						start.add("Turning on the taps and running you a bath, [npc.name] dutifully starts to help you in washing your [pc.hair(true)] and body. Raising [npc.her] voice so as to be heard over the sound of running water, [npc.she] says,");
					} else {
						start.add("Turning on the taps and running you a bath, [npc.name] dutifully starts to help you in washing your body. Raising [npc.her] voice so as to be heard over the sound of running water, [npc.she] says,");
					}
				} else {
					start.add("Picking up a bar of soap, [npc.name] assists [npc.her] fellow "+(washingNice.size()>2?"slaves":"slave")+" in cleaning your body. As [npc.she] sets about [npc.her] task, [npc.she] says,");
					start.add("Stepping forwards, with a bar of soap in [npc.hand], [npc.name] sets about cleaning your body, saying as [npc.she] does so,");
					start.add("Stepping up beside [npc.her] fellow "+(washingNice.size()>2?"slaves":"slave")+", [npc.name] starts cleaning your body, saying,");
				}
				speech.add("[npc.speech(I hope this is to your satisfaction, [pc.name].)]");
				speech.add("[npc.speech(Please let me know if you need me to do anything differently, [pc.name].)]");
				speech.add("[npc.speech(I'll be sure to do a good job in cleaning you, [pc.name].)]");
				
			} else if(npc.hasSlavePermissionSetting(SlavePermissionSetting.BEHAVIOUR_SEDUCTIVE)) {
				if(firstWashing) {
					if(npc.hasBreasts()) {
						start.add("Turning on the taps and running you a bath, [npc.name] moves up close behind you, before soaping your back down and starting to clean you."
								+ " Suddenly, [npc.she] steps forwards, and, pressing [npc.her] [npc.breasts+] against your back, [npc.she] seductively [npc.moans],");
					} else {
						start.add("Turning on the taps and running you a bath, [npc.name] moves up close behind you, before soaping your back down and starting to clean you."
								+ " Suddenly, [npc.she] steps forwards, and, pressing [npc.herself] against your back, [npc.she] seductively [npc.moans],");
					}
				} else {
					if(npc.hasBreasts()) {
						start.add("Stepping forwards, [npc.name] presses [npc.her] [npc.breasts+] against your back, before seductively [npc.moaning],");
						start.add("Rubbing a bar of soap over your back, [npc.name] suddenly steps forwards, pressing [npc.her] [npc.breasts+] against you as [npc.she] seductively [npc.moans],");
						start.add("Deciding to start by cleaning your back, [npc.name] steps around behind you, before leaning forwards and pressing [npc.her] [npc.breasts+] against you, [npc.moaning],");
					} else {
						start.add("Stepping forwards, [npc.name] presses [npc.herself] against your back, before seductively [npc.moaning],");
						start.add("Rubbing a bar of soap over your back, [npc.name] suddenly steps forwards, pressing [npc.herself] against you as [npc.she] seductively [npc.moans],");
						start.add("Deciding to start by cleaning your back, [npc.name] steps around behind you, before leaning forwards and pressing [npc.herself] against you, [npc.moaning],");
					}
				}
				speech.add("[npc.speech(You don't mind if I get this close, do you?)]");
				speech.add("[npc.speech(You like the feeling of me being this close, don't you?)]");
				speech.add("[npc.speech(That's right, relax and let me take care of you...)]");
				speech.add("[npc.speech(Perhaps once you're clean, you'd like to do something dirty with me...)]");
				
			} else if(npc.hasSlavePermissionSetting(SlavePermissionSetting.BEHAVIOUR_SLUTTY)) {
				if(firstWashing) {
					if(npc.hasBreasts()) {
						start.add("Turning on the taps and running you a bath, [npc.name] moves up close behind you, before immediately pressing [npc.her] [npc.breasts+] against your back."
								+ " Obviously seeing this as an opportunity to have some fun with you, [npc.she] reaches around to start groping your body, seductively [npc.moaning] into your [pc.ear] as [npc.she] does this,");
					} else {
						start.add("Turning on the taps and running you a bath, [npc.name] moves up close behind you, before immediately pressing [npc.her] body in against your back."
								+ " Obviously seeing this as an opportunity to have some fun with you, [npc.she] reaches around to start groping your body, seductively [npc.moaning] into your [pc.ear] as [npc.she] does this,");
					}
				} else {
					if(npc.hasBreasts()) {
						start.add("Stepping forwards, [npc.name] presses [npc.her] [npc.breasts+] against your back, before seductively [npc.moaning],");
						start.add("Rubbing a bar of soap over your back, [npc.name] suddenly steps forwards, pressing [npc.her] [npc.breasts+] against you as [npc.she] seductively [npc.moans],");
						start.add("Deciding to start by cleaning your back, [npc.name] steps around behind you, before leaning forwards and pressing [npc.her] [npc.breasts+] against you, [npc.moaning],");
					} else {
						start.add("Stepping forwards, [npc.name] presses [npc.herself] against your back, before seductively [npc.moaning],");
						start.add("Rubbing a bar of soap over your back, [npc.name] suddenly steps forwards, pressing [npc.herself] against you as [npc.she] seductively [npc.moans],");
						start.add("Deciding to start by cleaning your back, [npc.name] steps around behind you, before leaning forwards and pressing [npc.herself] against you, [npc.moaning],");
					}
				}
				speech.add("[npc.speech(Oh yeah, you like me feeling you up like this, don't you?)]");
				speech.add("[npc.speech(All this close contact is really turning me on; you wanna fuck after this?)]");
				speech.add("[npc.speech(This is making me so horny... You're up for having a good fuck after this, aren't you?)]");
				
			} else if(npc.hasSlavePermissionSetting(SlavePermissionSetting.BEHAVIOUR_STANDARD)
					|| npc.hasSlavePermissionSetting(SlavePermissionSetting.BEHAVIOUR_WHOLESOME)) {
				if(npc.isShy()) {
					if(firstWashing) {
						if(Main.game.getPlayer().hasHair()) {
							start.add("Turning on the taps and running you a bath, [npc.name] nervously shuffles forwards, before starting to help you wash your [pc.hair(true)] and body."
									+ " Doing [npc.her] best to be heard over the sound of running water, [npc.she] raises [npc.her] voice a little and says,");
						} else {
							start.add("Turning on the taps and running you a bath, [npc.name] nervously shuffles forwards, before starting to help you wash your body."
									+ " Doing [npc.her] best to be heard over the sound of running water, [npc.she] raises [npc.her] voice a little and says,");
						}
					} else {
						start.add("Clutching a bar of soap, [npc.name] nervously shuffles forwards, before helping [npc.her] fellow "+(washingNice.size()>2?"slaves":"slave")+" to clean you. Raising [npc.her] voice a little, [npc.she] says,");
						start.add("Stepping forwards with a bar of soap in [npc.hand], [npc.name] nervously sets about cleaning your body. Squeaking above the noise of running water, [npc.she] says,");
						start.add("Stepping up beside [npc.her] fellow "+(washingNice.size()>2?"slaves":"slave")+", [npc.name] blushes as [npc.she] starts to clean your body, saying,");
					}
					if(npc.hasSlavePermissionSetting(SlavePermissionSetting.BEHAVIOUR_WHOLESOME)) {
						speech.add("[npc.speech(I love being so close to you, [pc.name]?)]");
						speech.add("[npc.speech(I'm so happy to be able to help you like this...)]");
						speech.add("[npc.speech(Getting to help you like this makes me so happy...)]");
					} else {
						speech.add("[npc.speech(I'm not doing this wrong, am I, [pc.name]?)]");
						speech.add("[npc.speech(Just let me know if I'm doing this wrong...)]");
						speech.add("[npc.speech(I hope this is how you wanted me to wash you...)]");
					}
					
				} else if(npc.isKind()) {
					if(firstWashing) {
						if(Main.game.getPlayer().hasHair()) {
							start.add("Turning on the taps and running you a bath, [npc.name] happily steps forwards, before gently starting to help you wash your [pc.hair(true)] and body."
									+ " Doing [npc.her] best to be heard over the sound of running water, [npc.she] raises [npc.her] voice and says,");
						} else {
							start.add("Turning on the taps and running you a bath, [npc.name] happily steps forwards, before gently starting to help you wash your body."
									+ " Doing [npc.her] best to be heard over the sound of running water, [npc.she] raises [npc.her] voice and says,");
						}
					} else {
						start.add("Clutching a bar of soap, [npc.name] happily steps forwards, before helping [npc.her] fellow "+(washingNice.size()>2?"slaves":"slave")+" to clean you. Raising [npc.her] voice, [npc.she] says,");
						start.add("Stepping forwards with a bar of soap in [npc.hand], [npc.name] happily sets about cleaning your body. Raising [npc.her] voice above the noise of running water, [npc.she] says,");
						start.add("Stepping up beside [npc.her] fellow "+(washingNice.size()>2?"slaves":"slave")+", [npc.name] smiles as [npc.she] starts to clean your body, saying,");
					}
					if(npc.hasSlavePermissionSetting(SlavePermissionSetting.BEHAVIOUR_WHOLESOME)) {
						speech.add("[npc.speech(I love being so close to you, [pc.name]. Please let me know if you want me to anything differently.)]");
						speech.add("[npc.speech(I'm so happy to be able to help you like this; I'm so lucky to be owned by you.)]");
						speech.add("[npc.speech(Let me take care of you, [pc.name]; I want to show you how much you mean to me.)]");
					} else {
						speech.add("[npc.speech(Let me know if you need me to do anything differently, ok, [pc.name]?)]");
						speech.add("[npc.speech(Please tell me if you need me to do anything differently.)]");
						speech.add("[npc.speech(Let me take care of you, [pc.name].)]");
					}
					
				} else {
					if(firstWashing) {
						if(Main.game.getPlayer().hasHair()) {
							start.add("Turning on the taps and running you a bath, [npc.name] steps forwards, before starting to help you wash your [pc.hair(true)] and body."
									+ " Doing [npc.her] best to be heard over the sound of running water, [npc.she] raises [npc.her] voice and says,");
						} else {
							start.add("Turning on the taps and running you a bath, [npc.name] steps forwards, before starting to help you wash your body."
									+ " Doing [npc.her] best to be heard over the sound of running water, [npc.she] raises [npc.her] voice and says,");
						}
					} else {
						start.add("Clutching a bar of soap, [npc.name] steps forwards, before helping [npc.her] fellow "+(washingNice.size()>2?"slaves":"slave")+" to clean you. Raising [npc.her] voice, [npc.she] says,");
						start.add("Stepping forwards with a bar of soap in [npc.hand], [npc.name] sets about cleaning your body. Raising [npc.her] voice above the noise of running water, [npc.she] says,");
						start.add("Stepping up beside [npc.her] fellow "+(washingNice.size()>2?"slaves":"slave")+", [npc.name] smiles as [npc.she] starts to clean your body, saying,");
					}
					if(npc.hasSlavePermissionSetting(SlavePermissionSetting.BEHAVIOUR_WHOLESOME)) {
						speech.add("[npc.speech(I love being so close to you, [pc.name]. Please let me know if you want me to anything differently.)]");
						speech.add("[npc.speech(I'm so happy to be able to help you like this; I'm so lucky to be owned by you.)]");
						speech.add("[npc.speech(Let me take care of you, [pc.name]; I want to show you how much you mean to me.)]");
					} else {
						speech.add("[npc.speech(Let me know if you need me to do anything differently.)]");
						speech.add("[npc.speech(Just tell me if you need me to do anything differently.)]");
						speech.add("[npc.speech(Let me take care of you, [pc.name].)]");
					}
				}
			}
			sb.append(UtilText.parse(npc,Util.randomItemFrom(start)));
			sb.append(" ");
			sb.append(UtilText.parse(npc,Util.randomItemFrom(speech)));
			sb.append("</p>");
			firstWashing = false;
		}
		
		List<GameCharacter> washingRude = new ArrayList<>(slavesWashing);
		washingRude.removeAll(washingNice);
		for(GameCharacter npc : washingRude) {
			sb.append("<p>");
			List<String> start = new ArrayList<>();
			List<String> speech = new ArrayList<>();
			
				if(npc.isShy()) {
					if(firstWashing) {
						start.add("Reluctantly turning on the taps, [npc.name] steps forwards, before half-heartedly starting to help you wash yourself."
								+ " Trying to use the sound of the running water to mask [npc.her] comments, [npc.she] mutters,");
					} else {
						start.add("Reluctantly stepping forwards, [npc.name] casts [npc.her] gaze to the floor as [npc.she] half-heartedly starts to help you wash yourself. Letting out a weary sigh, [npc.she] mutters,");
						start.add("Looking down at the floor, [npc.name] steps forwards and reluctantly starts to help you wash yourself. With an exasperated sigh, [npc.she] mutters under [npc.her] breath,");
						start.add("Clearly not happy about it, [npc.name] nevertheless steps forwards and half-heartedly starts to help you wash yourself. Muttering under [npc.her] breath, [npc.she] sighs,");
					}
					speech.add("[npc.speech(I hate this...)]");
					speech.add("[npc.speech(How did I ever end up having to do things like this...)]");
					speech.add("[npc.speech(This sucks...)]");
					
				} else if(npc.isSelfish()) {
					if(firstWashing) {
						start.add("Reluctantly turning on the taps, [npc.name] steps forwards, before half-heartedly starting to help you wash yourself."
								+ " With a clear tone of animosity in [npc.her] voice, [npc.she] complains,");
					} else {
						start.add("Reluctantly stepping forwards, [npc.name] half-heartedly sets about helping you wash yourself. Letting out a weary sigh, [npc.she] complains,");
						start.add("Rolling [npc.her] [npc.eyes], [npc.name] steps forwards and reluctantly starts to help you wash yourself, complaining as [npc.she] does so,");
						start.add("With an annoyed sigh, [npc.name] steps forwards and half-heartedly starts to help you wash yourself. Not even trying to hide [npc.her] annoyance, [npc.she] angrily says,");
					}
					speech.add("[npc.speech(I really hate doing this, you know? Just get some other slave to help you next time!)]");
					speech.add("[npc.speech(Why do you insist on getting me to do this?! Isn't it clear that I hate helping you out like this?!)]");
					speech.add("[npc.speech(This really fucking sucks. How about you get some other slave to wash you next time?)]");
					
				} else {
					if(firstWashing) {
						start.add("Reluctantly turning on the taps, [npc.name] steps forwards, before half-heartedly starting to help you wash yourself. In a clear sign of displeasure, [npc.she] sighs,");
					} else {
						start.add("Reluctantly stepping forwards, [npc.name] half-heartedly sets about helping you wash yourself. Letting out a weary sigh, [npc.she] sighs,");
						start.add("Rolling [npc.her] [npc.eyes], [npc.name] steps forwards and reluctantly starts to help you wash yourself, complaining as [npc.she] does so,");
						start.add("With an annoyed sigh, [npc.name] steps forwards and half-heartedly starts to help you wash yourself. Not even trying to hide [npc.her] annoyance, [npc.she] sighs,");
					}
					speech.add("[npc.speech(Is there nobody else you can ask to do this?)]");
					speech.add("[npc.speech(I really wish you didn't make me do this...)]");
					speech.add("[npc.speech(This sucks...)]");
					speech.add("[npc.speech(I really hate having to do this, you know?)]");
				}

			sb.append(UtilText.parse(npc,Util.randomItemFrom(start)));
			sb.append(" ");
			sb.append(UtilText.parse(npc,Util.randomItemFrom(speech)));
			sb.append("</p>");
			firstWashing = false;
		}
		
		return sb.toString();
	}

	public static final DialogueNode AUNT_HOME_PLAYERS_ROOM_CALENDAR = new DialogueNode("Календарь", "", true) {
		@Override
		public void applyPreParsingEffects() {
			StringBuilder sb = new StringBuilder();

			sb.append("<p>"
					+ "Ты подходишь к одной стороне своей комнаты, где к стене прикреплён календарь."
					+ " Очевидно, что он зачарован, поскольку, листая страницы, ты обнаруживаешь, что картинка каждого месяца меняется в зависимости от того, о чём ты думаешь в данный момент.");

			if(Main.game.getPlayer().getSexualOrientation()==SexualOrientation.ANDROPHILIC) {
				sb.append(" По мере того как ты думаешь о каждом месяце, на странице появляется тематически одетый человек, инкуб или какой-нибудь зверопарень.");
			} else {
				sb.append(" По мере того как ты думаешь о каждом месяце, на странице появляется тематически одетая женщина, суккуб или какая-нибудь зверодувушка.");
			}

			if(Main.game.getPlayer().getCorruptionLevel()==CorruptionLevel.ZERO_PURE) {
				sb.append(" Чем больше ты листаешь календарь туда-сюда, тем более раздетым становится объект каждой фотографии, пока ты вдруг не понимаешь, что делаешь, и в шоке отступаешь назад.");
			} else {
				sb.append(" Чем больше ты листаешь календарь туда-сюда, тем более раздетым становится объект каждой фотографии, и ты начинаешь понемногу заводиться...");
			}
			sb.append("</p>");

			if(Main.game.getDialogueFlags().values.contains(DialogueFlagValue.knowsDate)) {
				sb.append("<p>"
						+ "Внезапно вспомнив, что именно ты [pc.genderBasedWord(хотел, хотела)] посмотреть, ты просматриваешь календарь, чтобы найти текущую дату,");
			} else {
				sb.append("<p>"
						+ "Ты так [pc.genderBasedWord(увлёкся, увлеклась)] сменой фотографий, что на мгновение [pc.genderBasedWord(забыл, забыла)], что именно [pc.genderBasedWord(хотел, хотела)] выяснить."
						+ " Встряхнув головой, ты перелистываешь календарь, чтобы узнать текущую дату,");
			}

			sb.append(" и видишь, что сегодня <b style='color:" + PresetColour.BASE_BLUE_LIGHT.toWebHexString() + ";'>"
						+ Units.date(Main.game.getDateNow(), Units.DateType.LONG)
					+ "</b>. Быстро посчитав " + (Main.game.getPlayer().getAttributeValue(Attribute.MAJOR_ARCANE) < IntelligenceLevel.ONE_AVERAGE.getMaximumValue() ? "(с помощью калькулятора твоего телефона)" : "")
					+ ", ты узнаёшь, что находишься в этом мире <b style='color:" + PresetColour.GENERIC_EXCELLENT.toWebHexString() + ";'>" + Main.game.getDayNumber() + " " + Morpher.morphCountableNoun(Main.game.getDayNumber(), "день") + "</b>."
					+ "</p>");

			if(!Main.game.getDialogueFlags().values.contains(DialogueFlagValue.knowsDate)) {
				sb.append("<p>"
						+ "[pc.thought(Что... " + Main.game.getDateNow().format(DateTimeFormatter.ofPattern("yyyy", RUSSIAN_LOCALE)) + "?! Мне нужно поговорить с Лилайей об этом...)]"
						+ "</p>");
			}

			sb.append("<p>"
					+ "Ты замечаешь, что на каждой странице календаря есть несколько абзацев с подробным описанием событий, которые случаются в этом месяце."
					+ "</p>");

			Main.game.getTextStartStringBuilder().append(sb);

			Main.game.getDialogueFlags().values.add(DialogueFlagValue.knowsDate);
		}
		@Override
		public String getContent() {
			return "";
		}

//		@Override
//		public String getResponseTabTitle(int index) {
//			return LilayaHomeGeneric.getLilayasHouseStandardResponseTabs(index);
//		}

		@Override
		public Response getResponse(int responseTab, int index) {
//			if(responseTab==1) {
//				return LilayaHomeGeneric.getLilayasHouseFastTravelResponses(index);
//			}
			if (index == 0) {
				return new Response("Назад", "Отойди от календаря.", ROOM);
			} else if(index==1) {
				return new Response("Январь", "Прочитать информацию на странице января. [style.italicsMinorBad(В настоящее время нет особых мероприятий в январе.)]", AUNT_HOME_PLAYERS_ROOM_CALENDAR_JANUARY);
			} else if(index==2) {
				return new Response("Февраль", "Прочитать информацию на странице февраля. [style.italicsMinorBad(В настоящее время нет специальных мероприятий в феврале.)]", AUNT_HOME_PLAYERS_ROOM_CALENDAR_FEBRUARY);
			} else if(index==3) {
				return new Response("Март", "Прочитать информацию на странице марта. [style.italicsMinorBad(В настоящее время нет специальных мероприятий в марте.)]", AUNT_HOME_PLAYERS_ROOM_CALENDAR_MARCH);
			} else if(index==4) {
				return new Response("Апрель", "Прочитать информацию на странице апреля. [style.italicsMinorBad(В настоящее время нет специальных мероприятий в апреле.)]", AUNT_HOME_PLAYERS_ROOM_CALENDAR_APRIL);
			} else if(index==5) {
				return new Response("Май", "Прочитать информацию на странице мая.", AUNT_HOME_PLAYERS_ROOM_CALENDAR_MAY);
			} else if(index==6) {
				return new Response("Июнь", "Прочитать информацию на странице июня.", AUNT_HOME_PLAYERS_ROOM_CALENDAR_JUNE);
			} else if(index==7) {
				return new Response("Июль", "Прочитать информацию на странице июля. [style.italicsMinorBad(В настоящее время нет специальных мероприятий в июле.)]", AUNT_HOME_PLAYERS_ROOM_CALENDAR_JULY);
			} else if(index==8) {
				return new Response("Август", "Прочитать информацию на странице августа [style.italicsMinorBad(В настоящее время нет специальных мероприятий в августе.)]", AUNT_HOME_PLAYERS_ROOM_CALENDAR_AUGUST);
			} else if(index==9) {
				return new Response("Сентябрь", "Прочитать информацию на странице сентября. [style.italicsMinorBad(В настоящее время нет специальных мероприятий в сентябре.)]", AUNT_HOME_PLAYERS_ROOM_CALENDAR_SEPTEMBER);
			} else if(index==10) {
				return new Response("Октябрь", "Прочитать информацию на странице октября.", AUNT_HOME_PLAYERS_ROOM_CALENDAR_OCTOBER);
			} else if(index==11) {
				return new Response("Ноябрь", "Прочитать информацию на странице ноября. [style.italicsMinorBad(В настоящее время нет специальных мероприятий в ноябре.)]", AUNT_HOME_PLAYERS_ROOM_CALENDAR_NOVEMBER);
			} else if(index==12) {
				return new Response("Декабрь", "Прочитать информацию на странице декабря.", AUNT_HOME_PLAYERS_ROOM_CALENDAR_DECEMBER);
			} else {
				return null;
			}
		}
	};

	public static final DialogueNode ROOM = new DialogueNode("Твоя комната", "", false) {
		@Override
		public void applyPreParsingEffects() {
			makeupTarget = Main.game.getPlayer();
		}
		@Override
		public String getContent() {
			StringBuilder sb = new StringBuilder();

			GenericPlace place = Main.game.getPlayerCell().getPlace();
			
			sb.append("<p>"
					+ "Твоя спальня расположена рядом с главной лестницей, соединяющей прихожую с коридором второго этажа, и является одной из самых больших комнат во всём особняке."
					+ " Напротив главного дверного проема в комнате четыре больших створчатых окна, из которых открывается прекрасный вид на сад во внутреннем дворе, а слева ещё одна дверь ведёт в личную ванную комнату."
				+ "</p>");
			
			if(place.getPlaceUpgrades().contains(PlaceUpgrade.LILAYA_PLAYER_ROOM_BED)) {
				sb.append("<p>"
						+ "Комната хорошо обставлена, в ней есть два набора ящиков и шкаф во всю высоту, что обеспечивает всё необходимое для хранения вещей."
						+ " Кроме этих предметов мебели, в комнате есть диван, письменный стол со стулом и зеркало во весь рост."
						+ "</p>");
			} else {
				sb.append("<p>"
						+ "У правой стены стоит кровать королевского размера, а два набора ящиков и шкаф во всю высоту шкафа обеспечат всё необходимое для хранения вещей."
						+ " Кроме этих предметов мебели, в комнате есть диван, письменный стол со стулом и зеркало во весь рост."
					+ "</p>");
			}
			
			sb.append(
					"<p>"
							+ "Как и всё остальное, что в твоём мире обычно работает от электричества, освещение, радиаторы и водопровод, похоже, питаются от магии."
					+ "</p>");
			
			sb.append(LilayaHomeGeneric.getRoomModificationsDescription(false));

			List<NPC> charactersPresent = LilayaHomeGeneric.getSlavesAndOccupantsPresent();
			if(!charactersPresent.isEmpty()) {
				List<String> names = new ArrayList<>();
				boolean soloSlave = charactersPresent.size()==1;
				for(NPC npc : charactersPresent) {
					names.add("<span style='color:"+npc.getFemininity().getColour().toWebHexString()+";'>"+npc.getName()+"</span>");
				}
				sb.append("<p>"
						+ "В твоей комнате " + (soloSlave ? "будет находиться твой раб" : "будут находиться твои рабы") + "; " + Util.stringsToStringList(names, false) + ".");
				
				List<NPC> greetings = charactersPresent.stream().filter(npc -> npc.hasSlaveJobSetting(SlaveJob.BEDROOM, SlaveJobSetting.BEDROOM_GREETING)).collect(Collectors.toList());
				names = new ArrayList<>();
				for(NPC npc : greetings) {
					names.add(npc.getName());
				}
				soloSlave = greetings.size()==1;
				if(!greetings.isEmpty()) {
					sb.append(" Having been instructed to greet you upon your arrival, "
								+(soloSlave
										?UtilText.parse(charactersPresent.get(0), "[npc.she] steps forwards and welcomes you back.")
										:Util.stringsToStringList(names, false)+" step forwards and welcome you back.")
							+ "</p>");

					List<NPC> greetingsNice = greetings.stream().filter(npc -> npc.getObedienceBasic()==ObedienceLevelBasic.OBEDIENT || npc.getAffectionLevelBasic(Main.game.getPlayer())==AffectionLevelBasic.LIKE).collect(Collectors.toList());
					for(NPC npc : greetingsNice) {
						sb.append("<p>");
						List<String> speechGreetings = new ArrayList<>();
						List<String> endGreetings = new ArrayList<>();
						List<String> endSpeechGreetings = new ArrayList<>();
						
						if(npc.hasSlavePermissionSetting(SlavePermissionSetting.BEHAVIOUR_PROFESSIONAL)) {
							speechGreetings.add("[npc.speech(Welcome back, [pc.name],)]");
							speechGreetings.add("[npc.speech(Good [style.morning], [pc.name],)]");
							speechGreetings.add("[npc.speech(Welcome home, [pc.name],)]");
							
							if(npc.isFeminine()) {
								endGreetings.add("[npc.name] says, curtsying respectfully towards you.");
								endGreetings.add("[npc.name] greets you, curtsying respectfully as [npc.she] does so.");
								endGreetings.add("[npc.name] addresses you, curtsying in the process.");
							} else {
								endGreetings.add("[npc.name] says, bowing respectfully towards you.");
								endGreetings.add("[npc.name] greets you, bowing respectfully as [npc.she] does so.");
								endGreetings.add("[npc.name] addresses you, bowing in the process.");
							}
							
						} else if(npc.hasSlavePermissionSetting(SlavePermissionSetting.BEHAVIOUR_SEDUCTIVE)) {
							speechGreetings.add("[npc.speech(Back so soon, [pc.name]?)]");
							speechGreetings.add("[npc.speech(Back already, [pc.name]?)]");
							speechGreetings.add("[npc.speech(You're back to see me so soon, [pc.name]?)]");
							
							if(npc.isFeminine()) {
								endGreetings.add("[npc.name] coyly asks, before biting [npc.her] lip and winking at you.");
								endGreetings.add("[npc.name] coyly asks, before turning slightly to one side and flashing you a seductive smile.");
								endGreetings.add("[npc.name] coyly asks, winking at you before blowing you a little kiss.");
							} else {
								endGreetings.add("[npc.name] flirtatiously asks, before flashing you a charming smile.");
								endGreetings.add("[npc.name] flirtatiously asks, before puffing his chest up and flashing you a winning smile.");
								endGreetings.add("[npc.name] flirtatiously asks, smiling at you in a charming manner.");
							}
							
						} else if(npc.hasSlavePermissionSetting(SlavePermissionSetting.BEHAVIOUR_SLUTTY)) {
							speechGreetings.add("[npc.speech(You're back to fuck, right, [pc.name]?)]");
							speechGreetings.add("[npc.speech(Oh, [pc.name], you're here for a good fuck, aren't you?)]");
							speechGreetings.add("[npc.speech(Hey, [pc.name], you wanna fuck, right?)]");

							endGreetings.add("[npc.name] bluntly asks,");
							endGreetings.add("[npc.name] tactlessly asks,");
							endGreetings.add("[npc.name] gracelessly asks,");
							
							if(npc.isFeminine()) {
								endSpeechGreetings.add("[npc.speech(You know I'm always horny for you...)]");
								endSpeechGreetings.add("[npc.speech(I can't stop fantasising about you...)]");
								endSpeechGreetings.add("[npc.speech(I get so horny just thinking about you...)]");
							} else {
								endSpeechGreetings.add("[npc.speech(I want you so badly right now.)]");
								endSpeechGreetings.add("[npc.speech(I can't stop fantasising about you.)]");
								endSpeechGreetings.add("[npc.speech(You know I'm always ready whenever you are.)]");
							}
							
						} else if(npc.hasSlavePermissionSetting(SlavePermissionSetting.BEHAVIOUR_STANDARD)) {
							speechGreetings.add("[npc.speech(Hello, [pc.name],)]");
							speechGreetings.add("[npc.speech(Good [style.morning], [pc.name],)]");
							speechGreetings.add("[npc.speech(Welcome back, [pc.name],)]");
							
							if(npc.isShy()) {
								endGreetings.add("[npc.name] says, looking at the floor and shyly shuffling [npc.her] [npc.feet].");
								endGreetings.add("[npc.name] says in a quiet voice, before blushing and looking down at the floor.");
								endGreetings.add("[npc.name] quietly greets you while shyly shuffling [npc.her] [npc.feet].");
								
							} else if(npc.isKind()) {
								endGreetings.add("[npc.name] says, smiling kindly at you,");
								endGreetings.add("[npc.name] says in a kind voice, before gently smiling at you and continuing,");
								endGreetings.add("[npc.name] happily greets you in a loving tone,");
								
								endSpeechGreetings.add("[npc.speech(Is there anything I can do for you?)]");
								endSpeechGreetings.add("[npc.speech(How are you this [style.morning]?)]");
								endSpeechGreetings.add("[npc.speech(Please let me know if there's anything I can do for you!)]");
								
							} else {
								endGreetings.add("[npc.name] calls out, before smiling happily at you.");
								endGreetings.add("[npc.name] happily greets you, before smiling and waiting for your orders.");
								endGreetings.add("[npc.name] says, smiling at you.");
								endGreetings.add("[npc.name] happily says, before smiling at you.");
								endGreetings.add("[npc.name] addresses you, before smiling and waiting to see what it is you'll do next.");
								endGreetings.add("[npc.name] greets you with a smile.");
							}
							
						} else if(npc.hasSlavePermissionSetting(SlavePermissionSetting.BEHAVIOUR_WHOLESOME)) {
							speechGreetings.add("[npc.speech(Hello, [pc.name],)]");
							speechGreetings.add("[npc.speech(Good [style.morning], [pc.name],)]");
							speechGreetings.add("[npc.speech(Welcome home, [pc.name],)]");
							
							if(npc.isShy()) {
								endGreetings.add("[npc.name] says, looking at the floor and shyly shuffling [npc.her] [npc.feet], before continuing,");
								endGreetings.add("[npc.name] says in a quiet voice, before blushing and shyly continuing,");
								endGreetings.add("[npc.name] quietly greets you while shyly shuffling [npc.her] [npc.feet], before blushing and continuing,");
								
								endSpeechGreetings.add("[npc.speech(I'm so happy that you're back; I really missed you...)]");
								endSpeechGreetings.add("[npc.speech(It really makes me happy to see you again...)]");
								endSpeechGreetings.add("[npc.speech(I really missed you while you were gone...)]");
								
							} else if(npc.isKind()) {
								endGreetings.add("[npc.name] says, smiling happily at you for a moment, before continuing,");
								endGreetings.add("[npc.name] says in a kind voice, before smiling at you in a loving manner and continuing,");
								endGreetings.add("[npc.name] greets you in a kind tone, before throwing you a loving smile and continuing,");

								endSpeechGreetings.add("[npc.speech(It's so good to see you again; it felt like you were gone forever!)]");
								endSpeechGreetings.add("[npc.speech(I'm so happy to see you again; I hope that you won't be gone for so long next time!)]");
								endSpeechGreetings.add("[npc.speech(I really missed you, you know; thank goodness you're back now!)]");
								
							} else {
								endGreetings.add("[npc.name] cheerfully says, before continuing,");
								endGreetings.add("[npc.name] happily says, before smiling at you and continuing,");
								endGreetings.add("[npc.name] greets you in an exited tone, before flashing you a happy smile and continuing,");

								endSpeechGreetings.add("[npc.speech(I'm so happy to see you again!)]");
								endSpeechGreetings.add("[npc.speech(It really makes me happy to see you again!)]");
								endSpeechGreetings.add("[npc.speech(I hope you aren't gone for so long next time! I miss you, you know?)]");
							}
						}
						sb.append(UtilText.parse(npc,Util.randomItemFrom(speechGreetings)));
						sb.append(" ");
						sb.append(UtilText.parse(npc,Util.randomItemFrom(endGreetings)));
						if(!endSpeechGreetings.isEmpty()) {
							sb.append(" ");
							sb.append(UtilText.parse(npc,Util.randomItemFrom(endSpeechGreetings)));
						}
						sb.append("</p>");
					}
					
					List<NPC> greetingsRude = new ArrayList<>(greetings);
					greetingsRude.removeAll(greetingsNice);
					for(NPC npc : greetingsRude) {
						sb.append("<p>");
						List<String> speechGreetings = new ArrayList<>();
						List<String> endGreetings = new ArrayList<>();
						
							if(npc.isShy()) {
								speechGreetings.add("[npc.speech(Oh, it's just you,)]");
								speechGreetings.add("[npc.speech(Damn, I was hoping it was someone else,)]");
								speechGreetings.add("[npc.speech(Oh no, not you,)]");
								speechGreetings.add("[npc.speech(Why did [pc.she] have to come back,)]");
								
								endGreetings.add("[npc.name] mutters under [npc.her] breath, before looking at the floor and shuffling [npc.her] [npc.feet].");
								endGreetings.add("[npc.name] mumbles, before casting [npc.her] gaze to the floor and refusing to look up at you.");
								endGreetings.add("[npc.name] mutters in annoyance, before shuffling [npc.her] [npc.feet] and letting out another quiet curse.");
								
							} else if(npc.isSelfish()) {
								speechGreetings.add("[npc.speech(Eugh, what do you want now, [pc.name]?)]");
								speechGreetings.add("[npc.speech(And what exactly do you want <i>this</i> time, [pc.name]?)]");
								speechGreetings.add("[npc.speech(So, why are you back?)]");
								speechGreetings.add("[npc.speech(Why did you have to come back?)]");
								
								endGreetings.add("[npc.name] angrily asks, not even trying to conceal [npc.her] animosity towards you.");
								endGreetings.add("[npc.name] questions, before narrowing [npc.her] [npc.eyes] and glaring angrily at you.");
								endGreetings.add("[npc.name] snaps, before glaring at you with resentment in [npc.her] [npc.eyes].");
								
							} else {
								speechGreetings.add("[npc.speech(Just do whatever it is you're here to do, [pc.name],)]");
								speechGreetings.add("[npc.speech(Go on then, [pc.name], do whatever it is you're here to do,)]");
								speechGreetings.add("[npc.speech(Let's just get this over with,)]");
								speechGreetings.add("[npc.speech(Go on then, tell me what it is you want this time,)]");
								
								endGreetings.add("[npc.name] sighs, before rolling [npc.her] [npc.eyes] in annoyance.");
								endGreetings.add("[npc.name] sighs in annoyance, crossing [npc.her] [npc.arms] and waiting for you to make the next move.");
								endGreetings.add("[npc.name] sighs, clearly not at all happy with being assigned to your room.");
								endGreetings.add("[npc.name] says, before letting out a weary sigh and tapping [npc.her] [npc.foot] on the floor.");
								endGreetings.add("[npc.name] says, before sighing and rolling [npc.her] [npc.eyes] in a clear sign of displeasure.");
								endGreetings.add("[npc.name] says, crossing [npc.her] [npc.arms] and letting out an annoyed sigh.");
							}

						sb.append(UtilText.parse(npc,Util.randomItemFrom(speechGreetings)));
						sb.append(" ");
						sb.append(UtilText.parse(npc,Util.randomItemFrom(endGreetings)));
						sb.append("</p>");
					}
				}
			}
			
			return sb.toString();
		}

		@Override
		public String getResponseTabTitle(int index) {
			return LilayaHomeGeneric.getLilayasHouseStandardResponseTabs(index);
		}
		
		@Override
		public Response getResponse(int responseTab, int index) {
			return getResponseRoom(responseTab, index);
		}
	};

	private static List<GameCharacter> slavesInRoom(int hour) {
		List<GameCharacter> charactersPresent = new ArrayList<>();
		
		for(String slaveId : Main.game.getPlayer().getSlavesOwned()) {
			try {
				GameCharacter slave = Main.game.getNPCById(slaveId);
				if(slave.getSlaveJob(hour)==SlaveJob.BEDROOM) {
					charactersPresent.add(slave);
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return charactersPresent;
	}
	
	private static List<GameCharacter> slavesWantingToSexPlayer(int hour) {
		List<GameCharacter> charactersPresent = new ArrayList<>();
		
		for(String slaveId : Main.game.getPlayer().getSlavesOwned()) {
			try {
				GameCharacter slave = Main.game.getNPCById(slaveId);
				if(slave.getSlaveJob(hour)==SlaveJob.BEDROOM
						&& slave.hasSlavePermissionSetting(SlavePermissionSetting.SEX_INITIATE_PLAYER)
						&& slave.isAttractedTo(Main.game.getPlayer())
						&& (slave.hasStatusEffect(StatusEffect.PENT_UP_SLAVE) || !((NPC)slave).hasFlag(NPCFlagValue.slaveBedroomHadSleepSex))) {
					charactersPresent.add(slave);
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return charactersPresent;
	}

	private static Response getResponseRoom(int responseTab, int index) {
		if(responseTab==1) {
			return LilayaHomeGeneric.getLilayasHouseFastTravelResponses(index);

		} else if(responseTab==0) {
			if(index==0) {
				return null;

			} else if (index == 1) {
                return new Response("Отдых (1ч)",
                        "Отдохнуть час. А также восполнение твоих " + Attribute.HEALTH_MAXIMUM.getName() + " и " + Attribute.MANA_MAXIMUM.getName() + ", вы также получите статусный эффект 'Хорошо отдохнувший'.",
						AUNT_HOME_PLAYERS_ROOM_SLEEP){
					@Override
					public void effects() {
						sleepTimeInMinutes = 60;
						applySleep(sleepTimeInMinutes);
					}
				};

			} else if (index == 2) {
                return new Response("Отдых (4ч)",
                        "Отдохнуть 4 часа. А также восполнение твоих " + Attribute.HEALTH_MAXIMUM.getName() + " и " + Attribute.MANA_MAXIMUM.getName() + ", вы также получите статусный эффект 'Хорошо отдохнувший'",
						AUNT_HOME_PLAYERS_ROOM_SLEEP){
					@Override
					public void effects() {
						sleepTimeInMinutes = 60 * 4;
						applySleep(sleepTimeInMinutes);
					}
				};

            } else if (index == 3) {
                return new Response("Отдых (8ч)",
                        "Отдохнуть 8 часов. А также восполнение твоих " + Attribute.HEALTH_MAXIMUM.getName() + " и " + Attribute.MANA_MAXIMUM.getName() + ", вы также получите статусный эффект 'Хорошо отдохнувший'",
                        AUNT_HOME_PLAYERS_ROOM_SLEEP){
                    @Override
                    public void effects() {
                        sleepTimeInMinutes = 60 * 8;
                        applySleep(sleepTimeInMinutes);
                    }
                };

            } else if (index == 4) {
                return new Response("Отдых (12ч)",
                        "Отдохнуть 12 часов. А также восполнение твоих " + Attribute.HEALTH_MAXIMUM.getName() + " и " + Attribute.MANA_MAXIMUM.getName() + ", вы также получите статусный эффект 'Хорошо отдохнувший'",
						AUNT_HOME_PLAYERS_ROOM_SLEEP){
					@Override
					public void effects() {
						sleepTimeInMinutes = 60 * 12;
						applySleep(sleepTimeInMinutes);
					}
				};

			} else if (index == 5) {
				int timeUntilChange = Main.game.getMinutesUntilNextMorningOrEvening() + 5; // Add 5 minutes so that if the days are drawing in, you don't get stuck in a loop of always sleeping to sunset/sunrise
				LocalDateTime[] sunriseSunset = DateAndTime.getTimeOfSolarElevationChange(Main.game.getDateNow(), SolarElevationAngle.SUN_ALTITUDE_SUNRISE_SUNSET, Game.DOMINION_LATITUDE, Game.DOMINION_LONGITUDE);
                return new Response("Отдых до " + (Main.game.isDayTime() ? "Заката" : "Восхода"),
                        "Отдохнуть " + (timeUntilChange >= 60 ? timeUntilChange / 60 + " часов " : " ")
                                + (timeUntilChange % 60 != 0 ? timeUntilChange % 60 + " минут" : "")
							+ (Main.game.isDayTime()
                                ? " пока не пройдет пять минут заката (" + Units.time(sunriseSunset[1].plusMinutes(5)) + ")."
                                : " пока не пройдет пять минут рассвета (" + Units.time(sunriseSunset[0].plusMinutes(5)) + ").")
                                + "  также восполнение твоих " + Attribute.HEALTH_MAXIMUM.getName() + " и " + Attribute.MANA_MAXIMUM.getName() + ", вы также получите статусный эффект 'Хорошо отдохнувший'",
							AUNT_HOME_PLAYERS_ROOM_SLEEP){
					@Override
					public void effects() {
						sleepTimeInMinutes = timeUntilChange;
						applySleep(sleepTimeInMinutes);
					}
				};

			} else if (index == 6) {
                return new Response("Управление комнатой", "Войдите в экран управления для этой конкретной комнаты.", OccupantManagementDialogue.ROOM_UPGRADES) {
					@Override
					public void effects() {
						OccupantManagementDialogue.cellToInspect = Main.game.getPlayerCell();
					}
				};

			}  else if (index == 7) {
				if(Main.game.getPlayer().isAbleToAccessRoomManagement()) {
                    return new Response("Управление людьми", "Войдите в экран управления своими рабами и дружественными обитателями.", ROOM) {
						@Override
						public DialogueNode getNextDialogue() {
							return OccupantManagementDialogue.getSlaveryRoomListDialogue(null, null);
						}
						@Override
						public void effects() {
							CompanionManagement.initManagement(Main.game.getDefaultDialogue(), 0, null);
						}
					};
				} else {
                    return new Response("Управление людьми", "Чтобы получить доступ к этому меню, вам нужна лицензия работорговца или разрешение Лилаи на размещение ваших друзей или кукол!", null);
				}

			} else if (index == 8) {
				if(Main.game.getDialogueFlags().values.contains(DialogueFlagValue.knowsDate)) {
                    return new Response("Календарь", "Взгляните еще раз на заколдованный календарь, висящий на стене.", AUNT_HOME_PLAYERS_ROOM_CALENDAR);
				} else {
                    return new Response("<span style='color:" + PresetColour.GENERIC_EXCELLENT.toWebHexString() + ";'>Календарь</span>", "На одной стене висел календарь. Посмотрите на него поближе.", AUNT_HOME_PLAYERS_ROOM_CALENDAR);
				}

			} else if (index == 9) {
                return new Response("Установить будильник", "Установите будильник на телефоне, чтобы просыпаться в определенное время.", RoomPlayer.ROOM_SET_ALARM) {
					@Override
					public void effects() {
						Main.game.saveDialogueNode();
					}
				};

			} else if (index == 10) {
				long alarmTime = Main.game.getDialogueFlags().getSavedLong("player_phone_alarm");
				if(alarmTime >= 0) {
					String alarmTimeStr = Main.game.getDisplayTime(LocalTime.ofSecondOfDay(alarmTime*60));
					int timeUntilAlarm = Main.game.getMinutesUntilTimeInMinutes((int)alarmTime);

                    return new Response("Отдых до будильника (" + alarmTimeStr + ")",
                            "Отдохнуть "
									+ (timeUntilAlarm==0
                                    ? "24ч "
                                    : ((timeUntilAlarm >= 60 ? timeUntilAlarm / 60 + " часов, " : "")
                                    + (timeUntilAlarm % 60 != 0 ? timeUntilAlarm % 60 + " минут, " : "")))
                                    + "пока не прозвенит будильник. Также восполняет втои " + Attribute.HEALTH_MAXIMUM.getName() + " и " + Attribute.MANA_MAXIMUM.getName() + ", вы также получите статусный эффект 'Хорошо отдохнувший'",
							AUNT_HOME_PLAYERS_ROOM_SLEEP) {
						@Override
						public void effects() {
							sleepTimeInMinutes = timeUntilAlarm==0?24*60:timeUntilAlarm;
							RoomPlayer.applySleep(sleepTimeInMinutes);
						}
					};
				} else {
                    return new Response("Отдых до будильника (Отключён)", "<span style='color:" + PresetColour.GENERIC_BAD.toWebHexString() + ";'>Ваш будильник отключен!</span>", null);
				}
			}

			List<NPC> charactersPresent = LilayaHomeGeneric.getSlavesAndOccupantsPresent();

			int indexPresentStart = 11;
			if(index-indexPresentStart<charactersPresent.size() && index-indexPresentStart>=0) {
				NPC character = charactersPresent.get(index-indexPresentStart);
				return LilayaHomeGeneric.interactWithNPC(character);
			}

		} else if(responseTab==2) {
			if (index == 1) {
				return new Response("Quick shower",
						"Use your room's ensuite to take a quick shower."
								+ "<br/>[style.italicsGood(Cleans <b>a maximum of "+Units.fluid(500)+"</b> of fluids from all orifices.)]"
								+ "<br/>[style.italicsGood(This will clean <b>only</b> your currently equipped clothing.)]",
//								+ "<br/>[style.italicsMinorBad(This does <b>not</b> clean companions.)]",
						AUNT_HOME_PLAYERS_ROOM_QUICK_SHOWER){
					@Override
					public void effects() {
						List<NPC> charactersPresent = LilayaHomeGeneric.getSlavesAndOccupantsPresent();
						slavesWashing = charactersPresent.stream().filter((npc) -> npc.hasSlaveJobSetting(SlaveJob.BEDROOM, SlaveJobSetting.BEDROOM_HELP_WASH)).collect(Collectors.toList());
						for(GameCharacter npc : slavesWashing) {
							npc.applyWash(true, true, StatusEffect.CLEANED_SHOWER, 120+30);
						}

						Main.game.getTextEndStringBuilder().append("<p style='text-align:center'><i>You leave your clothes outside of your bathroom so that they can be cleaned while you wash yourself...</i></p>");
						Main.game.getTextEndStringBuilder().append(Main.game.getPlayer().applyWash(false, false, null, 240+30));
					}
					@Override
					public int getSecondsPassed() {
						return 10*60;
					}
				};

			} else if (index == 2) {
				return new Response("Thorough shower",
						"Use your room's en-suite to take a shower, and spend some time thoroughly cleaning yourself."
								+ "<br/>[style.italicsExcellent(This will clean <b>all</b> fluids out of all your orifices.)]"
								+ "<br/>[style.italicsGood(This will clean <b>only</b> your currently equipped clothing.)]",
//								+ "<br/>[style.italicsMinorBad(This does <b>not</b> clean companions.)]",
						AUNT_HOME_PLAYERS_ROOM_THOROUGH_SHOWER){
					@Override
					public void effects() {
						List<NPC> charactersPresent = LilayaHomeGeneric.getSlavesAndOccupantsPresent();
						slavesWashing = charactersPresent.stream().filter((npc) -> npc.hasSlaveJobSetting(SlaveJob.BEDROOM, SlaveJobSetting.BEDROOM_HELP_WASH)).collect(Collectors.toList());
						for(GameCharacter npc : slavesWashing) {
							npc.applyWash(true, true, StatusEffect.CLEANED_SHOWER, 240+30);
						}

						Main.game.getTextEndStringBuilder().append("<p style='text-align:center'><i>You leave your clothes outside of your bathroom so that they can be cleaned while you wash yourself...</i></p>");
						Main.game.getTextEndStringBuilder().append(Main.game.getPlayer().applyWash(true, false, StatusEffect.CLEANED_SHOWER, 240+30));
					}
					@Override
					public int getSecondsPassed() {
						return 30*60;
					}
				};

			} else if(index==3) {
				return new Response("Bath time",
						"Use your room's en-suite to take a bath, and spend some time thoroughly cleaning yourself."
								+ "<br/>[style.italicsExcellent(This will clean <b>all</b> fluids out of all your orifices.)]"
								+ "<br/>[style.italicsExcellent(This will clean <b>all</b> clothing in your inventory.)]",
//								+ "<br/>[style.italicsMinorGood(This <b>does</b> clean companions.)]",
						AUNT_HOME_PLAYERS_ROOM_BATH){
					@Override
					public void effects() {
						List<GameCharacter> charactersPresent = new ArrayList<>(LilayaHomeGeneric.getSlavesAndOccupantsPresent());
						slavesWashing = charactersPresent.stream().filter((npc) -> npc.hasSlaveJobSetting(SlaveJob.BEDROOM, SlaveJobSetting.BEDROOM_HELP_WASH)).collect(Collectors.toList());
						for(GameCharacter npc : slavesWashing) {
							npc.applyWash(true, true, StatusEffect.CLEANED_BATH, 240+30);
						}

						Main.game.getTextEndStringBuilder().append("<p style='text-align:center'><i>You leave your clothes outside of your bathroom so that they can be cleaned while you wash yourself...</i></p>");
						Main.game.getTextEndStringBuilder().append(Main.game.getPlayer().applyWash(true, true, StatusEffect.CLEANED_BATH, 240+30));
					}
					@Override
					public int getSecondsPassed() {
						return 30*60;
					}
				};

			} else if(index==11) {
				return new ResponseEffectsOnly(
						UtilText.parse(getMakeupTarget(), "Target: <b style='color:"+getMakeupTarget().getFemininity().getColour().toWebHexString()+";'>[npc.Name]</b>"),
						"Cycle the targeted character for applying makeup to.") {
					@Override
					public void effects() {
						List<GameCharacter> companions = Util.newArrayListOfValues(Main.game.getPlayer());
						companions.addAll(Main.game.getCharactersPresent());
//						companions.removeIf((c) -> !c.isPlayer() && (!c.isSlave() || !c.getOwner().isPlayer()));
						if(!companions.isEmpty()) {
							for(int i=0; i<companions.size();i++) {
								if(companions.get(i).equals(getMakeupTarget())) {
									if(i==companions.size()-1) {
										makeupTarget = companions.get(0);
										break;

									} else {
										makeupTarget = companions.get(i+1);
										break;
									}
								}
							}
						}
						Main.game.updateResponses();
					}
				};

			} else if(index==12) {
				return new Response("Hairstyle & Makeup",
						UtilText.parse(getMakeupTarget(), "There's an impressive assortment of makeup and hair-styling tools in one of your bathroom's cabinets. If you wanted to, you could spend some time improving [npc.namePos] appearance..."),
						AUNT_HOME_PLAYERS_ROOM_MAKEUP){
					@Override
					public int getSecondsPassed() {
						return 5*60;
					}
				};

			}
		}
		return null;
	}
	
//	private static int getHourPlusSleep() {
//		return (Main.game.getHourOfDay() + (sleepTimeInMinutes/60))%24;
//	}
	private static List<GameCharacter> slavesPresentWhenGoingToSleep;
	private static List<GameCharacter> slavesPresentWhenWaking;
	private static List<GameCharacter> slavesToWakePlayer;

	public static final DialogueNode AUNT_HOME_PLAYERS_ROOM_SLEEP = new DialogueNode("Твоя комната", "", false) {

		@Override
		public boolean isTravelDisabled() {
			return !slavesWantingToSexPlayer(Main.game.getHourOfDay()).isEmpty();
		}
		
		@Override
		public String getContent() {
			StringBuilder sb = new StringBuilder();
			
			// Going to sleep:
			
			if(!slavesPresentWhenGoingToSleep.isEmpty()) {
				boolean soloSlave = slavesPresentWhenGoingToSleep.size()==1;
				
				List<String> names = new ArrayList<>();
				slavesPresentWhenGoingToSleep.stream().forEach((npc) -> names.add(npc.getName()));

				sb.append("<p>"
						+ "Feeling tired and in need of some rest, you head over to your bed and collapse down onto the mattress, before pulling back the covers and slipping beneath them."
						+ " Realising that you want to go to sleep, "+Util.stringsToStringList(names, false)+(soloSlave?" quickly draws the curtains":" quickly draw the curtains")+", before stepping back over towards you."
					+ "</p>");

				// Sleeping arrangements:
				List<GameCharacter> floorSlaves = slavesPresentWhenGoingToSleep.stream().filter((npc) -> npc.hasSlaveJobSetting(SlaveJob.BEDROOM, SlaveJobSetting.BEDROOM_SLEEP_FLOOR)).collect(Collectors.toList());
				List<GameCharacter> onBedSlaves = slavesPresentWhenGoingToSleep.stream().filter((npc) -> npc.hasSlaveJobSetting(SlaveJob.BEDROOM, SlaveJobSetting.BEDROOM_SLEEP_ON_BED)).collect(Collectors.toList());
				if(!floorSlaves.isEmpty() || !onBedSlaves.isEmpty()) {
					sb.append("<p>");
					boolean soloFloor = floorSlaves.size()==1;
					List<String> floorNames = new ArrayList<>();
					floorSlaves.stream().forEach((npc) -> floorNames.add(npc.getName()));
					if(!floorSlaves.isEmpty()) {
						sb.append(soloFloor
								?UtilText.parse(floorSlaves.get(0), "Knowing that [npc.sheIs] not allowed to sleep on your bed, [npc.name] lies down on the floor and prepares to try to get some sleep.")
								:"Knowing that they aren't allowed to sleep on your bed, "+Util.stringsToStringList(floorNames, false)+" lie down on the floor and prepare to try to get some sleep.");
					}
					if(!onBedSlaves.isEmpty()) {
						boolean soloOnBed = onBedSlaves.size()==1;
						List<String> onBedNames = new ArrayList<>();
						onBedSlaves.stream().forEach((npc) -> onBedNames.add(npc.getName()));
						if(!floorSlaves.isEmpty()) {
							sb.append(soloOnBed
									?UtilText.parse(onBedSlaves.get(0),  "Stepping past "+Util.stringsToStringList(floorNames, false)+", [npc.name] climbs up onto your bed, before letting out a relaxed sigh and curling up on your covers.")
									:"Stepping past "+(soloFloor?UtilText.parse(floorSlaves.get(0), "[npc.name]"):"your slaves on the floor")
										+", "+Util.stringsToStringList(onBedNames, false)+" climb up onto your bed, before each letting out relaxed sighs and curling up on your covers.");
						} else {
							sb.append(soloOnBed
									?UtilText.parse(onBedSlaves.get(0), "Knowing that [npc.sheIs] allowed to sleep on the covers, [npc.name] climbs up onto your bed, before letting out a relaxed sigh and curling up, ready to sleep.")
									:"Knowing that they're allowed to sleep on the covers, "+Util.stringsToStringList(onBedNames, false)+" climb up onto your bed, before each letting out relaxed sighs and curling up, ready to sleep.");
						}
					}
					sb.append("</p>");
				}

				List<GameCharacter> inBedSlaves = slavesPresentWhenGoingToSleep.stream().filter((npc) -> npc.hasSlaveJobSetting(SlaveJob.BEDROOM, SlaveJobSetting.BEDROOM_SLEEP_IN_BED)).collect(Collectors.toList());
				if(!inBedSlaves.isEmpty()) {
					sb.append("<p>");
					boolean soloInBed = inBedSlaves.size()==1;
					List<String> inBedNames = new ArrayList<>();
					inBedSlaves.stream().forEach((npc) -> inBedNames.add(npc.getName()));
					sb.append(soloInBed
							?UtilText.parse(inBedSlaves.get(0), "Having been given your explicit permission to do so, [npc.name] pulls back a corner of the duvet, and then quickly slips in under the covers.")
							:"Having been given your explicit permission to do so, "+Util.stringsToStringList(inBedNames, false)
								+" pull back the top two corners of the duvet, and then "+(inBedSlaves.size()==2?"both":"each")+" of them quickly slip in under the covers.");
					sb.append("</p>");
					
					List<GameCharacter> niceSlaves = inBedSlaves.stream().filter(npc -> npc.getObedienceBasic()==ObedienceLevelBasic.OBEDIENT || npc.getAffectionLevelBasic(Main.game.getPlayer())==AffectionLevelBasic.LIKE).collect(Collectors.toList());
					for(GameCharacter npc : niceSlaves) {
						sb.append("<p>");
						List<String> speechGreetings = new ArrayList<>();
						List<String> endGreetings = new ArrayList<>();
						List<String> endSpeechGreetings = new ArrayList<>();
						
						if(npc.hasSlavePermissionSetting(SlavePermissionSetting.BEHAVIOUR_PROFESSIONAL)) {
							speechGreetings.add(UtilText.parse(npc, "[npc.speech(Allow me to keep you warm, [pc.name],)]"));
							speechGreetings.add(UtilText.parse(npc, "[npc.speech(Please let me provide you with some extra comfort, [pc.name],)]"));
							speechGreetings.add(UtilText.parse(npc, "[npc.speech(Please allow me to keep you warm, [pc.name],)]"));
							
							endGreetings.add(UtilText.parse(npc, "[npc.name] offers, before snuggling up against you."));
							endGreetings.add(UtilText.parse(npc, "[npc.name] offers, moving up beneath the sheets to snuggle in alongside you."));
							endGreetings.add(UtilText.parse(npc, "[npc.name] says, before shuffling up against you and pressing [npc.her] body against yours."));
							
						} else if(npc.hasSlavePermissionSetting(SlavePermissionSetting.BEHAVIOUR_SEDUCTIVE)) {
							speechGreetings.add(UtilText.parse(npc, "[npc.speech(Perhaps we could do something else in this bed after you wake up?)]"));
							speechGreetings.add(UtilText.parse(npc, "[npc.speech(After you've got some rest, perhaps the two of us could spend some energy together?)]"));
							speechGreetings.add(UtilText.parse(npc, "[npc.speech(Would you like to have some fun with me after you've woken up, [pc.name]?)]"));
							
							endGreetings.add(UtilText.parse(npc, "[npc.name] teases, pressing [npc.herself] against you."));
							endGreetings.add(UtilText.parse(npc, "[npc.name] seductively suggests, before moving up beneath the sheets to press [npc.herself] against you."));
							endGreetings.add(UtilText.parse(npc, "[npc.name] teases, before sliding up beneath the sheets to press [npc.her] body against you."));
							
						} else if(npc.hasSlavePermissionSetting(SlavePermissionSetting.BEHAVIOUR_SLUTTY)) {
							if(npc.hasBreasts()) {
								speechGreetings.add(UtilText.parse(npc, "[npc.speech(You can use my tits as a pillow if you want,)]"));
								speechGreetings.add(UtilText.parse(npc, "[npc.speech(Give my tits a good grope; it'll help you sleep better,)]"));
								speechGreetings.add(UtilText.parse(npc, "[npc.speech(Come on, play with my tits a bit before you sleep,)]"));
								
								endGreetings.add(UtilText.parse(npc, "[npc.name] offers, pressing [npc.her] [npc.breastSize] breasts in against you."));
								endGreetings.add(UtilText.parse(npc, "[npc.name] cheekily suggests, before sliding up under the covers and pressing [npc.her] [npc.breastSize] breasts into you."));
								endGreetings.add(UtilText.parse(npc, "[npc.name] suggests, before shuffling up and pressing [npc.her] [npc.breastSize] breasts against you."));
								
							} else {
								speechGreetings.add(UtilText.parse(npc, "[npc.speech(We can have a good fuck after you've slept, right?)]"));
								speechGreetings.add(UtilText.parse(npc, "[npc.speech(After you've got some rest, you'll be up for having a good fuck, won't you?)]"));
								speechGreetings.add(UtilText.parse(npc, "[npc.speech(When you get up, we can have a good fuck, right?)]"));
								
								endGreetings.add(UtilText.parse(npc, "[npc.name] bluntly asks, reaching down under the covers to stroke your [pc.leg]."));
								endGreetings.add(UtilText.parse(npc, "[npc.name] cheekily suggests, before sliding up under the covers and pressing [npc.herself] against you."));
								endGreetings.add(UtilText.parse(npc, "[npc.name] suggests, before shuffling up and pressing [npc.herself] against you."));
							}
							
						} else if(npc.hasSlavePermissionSetting(SlavePermissionSetting.BEHAVIOUR_STANDARD)) {
							if(npc.isShy()) {
								speechGreetings.add(UtilText.parse(npc, "[npc.speech(Erm, [pc.name], just let me know if I'm taking up too much room,)]"));
								speechGreetings.add(UtilText.parse(npc, "[npc.speech(Just let me know if I'm getting in your way,)]"));
								speechGreetings.add(UtilText.parse(npc, "[npc.speech(If I'm getting too close, just let me know,)]"));
								
								endGreetings.add(UtilText.parse(npc, "[npc.name] shyly says, using the top edge of the sheets to hide [npc.her] blushing [npc.face]."));
								endGreetings.add(UtilText.parse(npc, "[npc.name] says, nervously clutching the sheets as [npc.she] shuffles up alongside you."));
								endGreetings.add(UtilText.parse(npc, "[npc.name] mutters, furiously blushing as [npc.she] moves up alongside you beneath the sheets."));
								
							} else {
								speechGreetings.add(UtilText.parse(npc, "[npc.speech(Let me get a little closer,)]"));
								speechGreetings.add(UtilText.parse(npc, "[npc.speech(I just need to get comfortable,)]"));
								speechGreetings.add(UtilText.parse(npc, "[npc.speech(There we go, that's better,)]"));
								
								endGreetings.add(UtilText.parse(npc, "[npc.name] says, shuffling up alongside you before nestling in against your [pc.breasts]."));
								endGreetings.add(UtilText.parse(npc, "[npc.name] says, before sliding up under the covers and pressing [npc.herself] against you."));
								endGreetings.add(UtilText.parse(npc, "[npc.name] says, smiling as [npc.she] presses [npc.herself] against you."));
							}
							
						} else if(npc.hasSlavePermissionSetting(SlavePermissionSetting.BEHAVIOUR_WHOLESOME)) {
							speechGreetings.add(UtilText.parse(npc, "[npc.speech(I love getting to sleep with you, [pc.name],)]"));
							speechGreetings.add(UtilText.parse(npc, "[npc.speech(This is the best; getting to sleep with you like this,)]"));
							speechGreetings.add(UtilText.parse(npc, "[npc.speech(Getting to sleep with you is the best part of the day,)]"));
							
							if(npc.isShy()) {
								endGreetings.add(UtilText.parse(npc, "[npc.name] shyly says, using the top edge of the sheets to hide [npc.her] blushing [npc.face] as [npc.she] shuffles up to press [npc.herself] against you."));
								endGreetings.add(UtilText.parse(npc, "[npc.name] says, nervously clutching the sheets as [npc.she] shuffles up alongside you."));
								endGreetings.add(UtilText.parse(npc, "[npc.name] nervously mutters, furiously blushing as [npc.she] moves up alongside you beneath the sheets."));
								
							} else {
								endGreetings.add(UtilText.parse(npc, "[npc.name] says, smiling happily as [npc.she] snuggles up against you."));
								endGreetings.add(UtilText.parse(npc, "[npc.name] says, before sliding up under the covers and lovingly pressing [npc.herself] against you."));
								endGreetings.add(UtilText.parse(npc, "[npc.name] lovingly says, before shuffling up and pressing [npc.herself] against you."));
							}
						}
						if(npc.getTailType().isSuitableForSleepHugging()) {
							endSpeechGreetings.add(UtilText.parse(npc, "With a satisfied sigh, the [npc.race] moves [npc.her] [npc.tail+] around beneath the covers, before laying it over your body and using it to give you a loving tail-hug."));
							endSpeechGreetings.add(UtilText.parse(npc, "Moving [npc.her] [npc.tail+] around beneath the covers, the [npc.race] wraps it around your [pc.leg], before moving in even closer against you."));
							endSpeechGreetings.add(
									UtilText.parse(npc, "Displaying an impressive level of control over [npc.her] [npc.tail+], the [npc.race] swiftly moves it up and wraps it around your lower body, before snuggling in even closer against you."));
						}
						
						sb.append(UtilText.parse(npc,Util.randomItemFrom(speechGreetings)));
						sb.append(" ");
						sb.append(UtilText.parse(npc,Util.randomItemFrom(endGreetings)));
						if(!endSpeechGreetings.isEmpty()) {
							sb.append(" ");
							sb.append(UtilText.parse(npc,Util.randomItemFrom(endSpeechGreetings)));
						}
						sb.append("</p>");
					}
					
					List<GameCharacter> rudeSlaves = new ArrayList<>(inBedSlaves);
					rudeSlaves.removeAll(niceSlaves);
					for(GameCharacter npc : rudeSlaves) {
						sb.append("<p>");
							List<String> speechGreetings = new ArrayList<>();
							List<String> endGreetings = new ArrayList<>();
							if(npc.isShy()) {
								speechGreetings.add(UtilText.parse(npc, "[npc.speech(Please respect my side of the bed,)]"));
								speechGreetings.add(UtilText.parse(npc, "[npc.speech(I really would prefer it if you didn't get too close,)]"));
								speechGreetings.add(UtilText.parse(npc, "[npc.speech(I'd really like it if you didn't touch me,)]"));
								
								endGreetings.add(UtilText.parse(npc, "[npc.name] nervously requests, before rolling over and turning [npc.her] back to you."));
								endGreetings.add(UtilText.parse(npc, "[npc.name] mutters, before shuffling away from you."));
								endGreetings.add(UtilText.parse(npc, "[npc.name] shyly requests, before shuffling away under the covers and turning [npc.her] back to you."));
								
							} else if(npc.isSelfish()) {
								speechGreetings.add(UtilText.parse(npc, "[npc.speech(Don't roll over into my personal space in your sleep,)]"));
								speechGreetings.add(UtilText.parse(npc, "[npc.speech(Keep to your side of the bed,)]"));
								speechGreetings.add(UtilText.parse(npc, "[npc.speech(There's plenty of room on your side of the bed; don't come into mine,)]"));
								
								endGreetings.add(UtilText.parse(npc, "[npc.name] warns, letting out an annoyed huff as [npc.she] shuffles away from you."));
								endGreetings.add(UtilText.parse(npc, "[npc.name] bluntly states, before shuffling away from you and letting out a frustrated sigh."));
								endGreetings.add(UtilText.parse(npc, "[npc.name] says, before turning [npc.her] back to you."));
								
							} else {
								speechGreetings.add(UtilText.parse(npc, "[npc.speech(I'm just going to keep to my part of the bed,)]"));
								speechGreetings.add(UtilText.parse(npc, "[npc.speech(I'll just stay over here on my side,)]"));
								speechGreetings.add(UtilText.parse(npc, "[npc.speech(I'll leave you plenty of room, don't worry,)]"));
								
								endGreetings.add(UtilText.parse(npc, "[npc.name] says, letting out a weary sigh as [npc.she] shuffles away from you."));
								endGreetings.add(UtilText.parse(npc, "[npc.name] sighs, shuffling away a little before rolling over and turning [npc.her] back on you."));
								endGreetings.add(UtilText.parse(npc, "[npc.name] sighs, before turning [npc.her] back to you and shuffling over to the edge of the bed."));
							}
							sb.append(UtilText.parse(npc,Util.randomItemFrom(speechGreetings)));
							sb.append(" ");
							sb.append(UtilText.parse(npc,Util.randomItemFrom(endGreetings)));
						sb.append("</p>");
					}
				}
				
			} else {
				sb.append("<p>"
						+ "You set your phone's alarm before drawing the curtains, lying on your bed and closing your eyes."
						+ " You feel extremely safe and comfortable here in Lilaya's home, and soon drift off to sleep, thinking about all the things that have happened to you recently..."
					+ "</p>");
				
			}
			
			
			// Sleeping:
			sb.append("<p>"
					+ "[style.italics(...)]"
				+ "</p>");
			
			
			// Waking up:
			if(!slavesPresentWhenWaking.isEmpty()) {
				int hour = Main.game.getHourOfDay();
				String morningString = "evening";
				if(hour<4) {
					morningString = "evening";
				} else if(hour<12) {
					morningString = "morning";
				}else if(hour<17) {
					morningString = "afternoon";
				}

				List<GameCharacter> hornySlaves = slavesWantingToSexPlayer(Main.game.getHourOfDay());
				
//				boolean soloSlave = slavesToWakePlayer.size()==1;
				
				if(!slavesToWakePlayer.isEmpty()) {
					List<String> names = new ArrayList<>();
					slavesToWakePlayer.stream().forEach((npc) -> names.add(npc.getName()));
					
					GameCharacter slaveWaking = Util.randomItemFrom(slavesToWakePlayer);
//					sb.append("<p>"
//							+ (soloSlave
//								?UtilText.parse(slaveWaking,
//									"With you and [npc.name] now in your respective positions, you ask [npc.herHim] to wake you at the time that you'd like to be getting up."
//										+ " After [npc.sheHas] reassured you that [npc.she] won't let you sleep in too late, you let out a contented sigh, close your eyes, and start to drift off to sleep...")
//								:UtilText.parse(slaveWaking,
//									"With your slaves now being settled into their respective positions, you ask [npc.name] to wake you at the time that you'd like to be getting up."
//										+ " After [npc.sheHas] reassured you that [npc.she] won't let you sleep in too late, you let out a contented sigh, close your eyes, and start to drift off to sleep..."))
//						+ "</p>"
//						+ "<p>"
//							+ "[style.italics(...)]"
//						+ "</p>");
					
					if(hornySlaves.isEmpty()) {
						sb.append("<p>");
						if(slaveWaking.hasSlavePermissionSetting(SlavePermissionSetting.BEHAVIOUR_PROFESSIONAL)) {
							sb.append(UtilText.parse(slaveWaking,
									"[npc.speech([pc.Name]? It's the appointed hour for you to be waking up,)]"
									+ " you hear a voice calling out to you, and as you slowly open your [pc.eyes], you see [npc.name] smiling down at you."
									+ " Seeing that you're awake, [npc.she] withdraws to open the curtains, calling over [npc.her] shoulder as [npc.she] does so, [npc.speech(Good "+morningString+", [pc.name]!)]"));
						} else if(slaveWaking.hasSlavePermissionSetting(SlavePermissionSetting.BEHAVIOUR_SEDUCTIVE)) {
							sb.append(UtilText.parse(slaveWaking, 
									"[npc.speech(Come on, [pc.name], it's time to wake up,)]"
									+ " you hear a voice seductively whispering into your [pc.ear], and as you slowly open your [pc.eyes], you see [npc.name] smiling down at you and biting [npc.her] lip."
									+ " Seeing that you're awake, [npc.she] runs a hand over your [pc.chest], before withdrawing to open the curtains. Calling over [npc.her] shoulder as [npc.she] does so, [npc.she] teases,"
									+ " [npc.speech(Good "+morningString+", [pc.name]... So, is there anything <i>special</i> that you wanted to do today?)]"));
						} else if(slaveWaking.hasSlavePermissionSetting(SlavePermissionSetting.BEHAVIOUR_SLUTTY)) {
							sb.append(UtilText.parse(slaveWaking,
									"[npc.speech(If you get up now, you can make time for a quick fuck, can't you?)]"
									+ " you hear a voice asking, and as you slowly open your [pc.eyes], you see [npc.name] hungrily gazing down at you."
									+ " Seeing that you're awake, [npc.she] leans down to kiss you fully on the [pc.lips], before withdrawing to open the curtains. Calling over [npc.her] shoulder as [npc.she] does so, [npc.she] teases,"
									+ " [npc.speech(Good "+morningString+", [pc.name]! So, you want to fuck me now, right?)]"));
						} else if(slaveWaking.hasSlavePermissionSetting(SlavePermissionSetting.BEHAVIOUR_STANDARD)) {
							if(slaveWaking.isShy()) {
								sb.append(UtilText.parse(slaveWaking,
										"[npc.speech([pc.Name]? Erm, excuse me, [pc.name]? It's time to wake up,)]"
										+ " you hear a voice nervously calling out to you, and as you slowly open your [pc.eyes], you see [npc.name] worriedly looking down at you."
											+ " Seeing that you're awake, [npc.she] breaths a little sigh of relief and hurries off to open the curtains, calling out as [npc.she] does so, [npc.speech(Good "+morningString+", [pc.name]!)]"));
							} else {
								sb.append(UtilText.parse(slaveWaking, 
										"[npc.speech([pc.Name]? Come on, [pc.name], it's time to wake up,)]"
										+ " you hear a voice calling out to you, and as you slowly open your [pc.eyes], you see [npc.name] smiling down at you."
												+ " Seeing that you're awake, [npc.she] withdraws to open the curtains, calling over [npc.her] shoulder as [npc.she] does so, [npc.speech(Good "+morningString+", [pc.name]!)]"));
							}
						} else if(slaveWaking.hasSlavePermissionSetting(SlavePermissionSetting.BEHAVIOUR_WHOLESOME)) {
							if(slaveWaking.isShy()) {
								sb.append(UtilText.parse(slaveWaking,
										"[npc.speech([pc.Name]? Um... Come on, sleepy... It's time to wake up,)]"
										+ " you hear a voice shyly calling out to you, and as you slowly open your [pc.eyes], you see [npc.name] looking down at you."
												+ " Seeing that you're awake, [npc.she] breaths a little sigh of relief and hurries off to open the curtains, quietly calling out as [npc.she] does so,"
												+ " [npc.speech(Good "+morningString+", [pc.name]... I hope you have a wonderful day today...)]"));
							} else if(slaveWaking.isKind()) {
								sb.append(UtilText.parse(slaveWaking,
										"[npc.speech([pc.Name]? Come on, sleepy-head! You don't want to stay in bed forever do you?)]"
										+ " you hear a voice calling out to you, and as you slowly open your [pc.eyes], you see [npc.name] smiling down at you."
												+ " Seeing that you're awake, [npc.she] gives your face a gentle, loving stroke, before hurrying off to open the curtains, calling out over [npc.her] shoulder as [npc.she] does so,"
												+ " [npc.speech(Good "+morningString+", [pc.name]! I hope you have a wonderful day today!)]"));
							} else {
								sb.append(UtilText.parse(slaveWaking,
										"[npc.speech([pc.Name]? Come on, sleepy-head! You don't want to stay in bed forever do you?)]"
										+ " you hear a voice calling out to you, and as you slowly open your [pc.eyes], you see [npc.name] smiling down at you."
												+ " Seeing that you're awake, [npc.she] stands up and hurries off to open the curtains, calling out over [npc.her] shoulder as [npc.she] does so,"
												+ " [npc.speech(Good "+morningString+", [pc.name]! I hope you have a wonderful day today!)]"));
							}
						}
						sb.append("</p>");

						sb.append(UtilText.parse(slaveWaking,
								"<p>"
									+ "As comfortable as you are, you decide that you'd better do as [npc.name] says and get out of bed, and so, after a minute of lying beneath the covers, you do just that."
									+ " With a satisfied yawn and stretch of your [pc.arms], you allow "+ Util.stringsToStringList(names, false)+" to assist you as you gather your things, and with one final stretch, you get ready to set out once more..."
								+ "</p>"));
					}
					
				} else {
//					sb.append("<p>"
//								+ (soloSlave
//									?UtilText.parse(slavesPresentWhenGoingToSleep.get(0),
//											"With you and [npc.name] now in your respective positions, you set your phone's alarm and place it on the bedside cabinet beside you."
//											+ " Letting out a contented sigh, you close your eyes and start to drift off to sleep...")
//									:"With your slaves now being settled into their respective positions, you set your phone's alarm and place it on the bedside cabinet beside you."
//										+ " Letting out a contented sigh, you close your eyes and start to drift off to sleep...")
//							+ "</p>"
//							+ "<p>"
//								+ "[style.italics(...)]"
//							+ "</p>");

					if(hornySlaves.isEmpty()) {
						List<String> names = new ArrayList<>();
						slavesPresentWhenWaking.stream().forEach((npc) -> names.add(npc.getName()));
						sb.append(
								"<p>"
									+ "<i>Beep-beep... beep-beep... bee-</i>"
								+ "</p>"
								+ "<p>"
									+ "Rolling over, you fumble for your phone, turning off the alarm before sinking back onto your bed with a sigh."
									+ " As comfortable as you are, you decide that you'd better get out of bed, and so, after a minute of lying beneath the covers, you do just that."
									+ " With a satisfied yawn and stretch of your [pc.arms], you allow "+ Util.stringsToStringList(names, false)+" to assist you as you gather your things, and with one final stretch, you get ready to set out once more..."
								+ "</p>");
					}
				}

				if(!hornySlaves.isEmpty()) {
					Collections.shuffle(hornySlaves);
					boolean soloHornySex = hornySlaves.size()==1;
					List<String> hornyNames = new ArrayList<>();
					hornySlaves.stream().forEach((npc) -> hornyNames.add(npc.getName()));
					if(Main.game.getPlayer().hasTrait(Perk.HEAVY_SLEEPER, true)) {
						if(!hornySlaves.get(0).isMute()) {
							sb.append(UtilText.parse(hornySlaves,
									"<p>"
										+ "[npc.speech(~Mmm!~ That's right... Don't wake up...)]"
									+ "</p>"
									+ "<p>"
										+ "Being such a deep sleeper, these words uttered by [npc.name] don't even come close to waking you up."
										+ (soloHornySex
												?" Sliding onto your bed, [npc.name] grins hungrily down at you."
												:" Sliding onto your bed, "+Util.stringsToStringList(hornyNames, false)+" grin hungrily down at you.")
										+ " With a horny [npc.moan], the [npc.race] starts gently groping you, eagerly panting as [npc.she] does so,"
										+ " [npc.speech(I'm going to fuck you, [pc.name], and you'll never even know...)]"
									+ "</p>"));
						} else {
							sb.append(UtilText.parse(hornySlaves,
									"<p>"
										+ "Quietly sneaking over to your bed, [npc.name] looks down at you as you sleep and lets out an excited moan."
										+ " Being such a deep sleeper, this noise doesn't even come close to waking you up."
										+ (soloHornySex
												?" Sliding onto your bed, [npc.name] grins hungrily down at you."
												:" Sliding onto your bed, "+Util.stringsToStringList(hornyNames, false)+" grin hungrily down at you.")
										+ " Licking [npc.her] [npc.lips], the [npc.race] starts gently groping you, eagerly panting as [npc.she] prepares to fuck you in your sleep..."
									+ "</p>"));
						}
						
					} else {
						sb.append(UtilText.parse(hornySlaves,
								"<p>"
									+ (soloHornySex
											?"[npc.speech(~Mmm!~ That's right... I've got you now...)]"
											:"[npc2.speech(~Mmm!~ That's right... I've got you now...)] the voice of [npc2.name] penetrates into your sleeping mind.")
								+ "</p>"
								+ "<p>"
									+ "For a moment, you aren't quite sure if you're dreaming or not, but then you suddenly feel a strange weight shifting on top of you, which instantly jolts you awake."
									+ (soloHornySex
											?" Opening your eyes, you see [npc.name] sitting on your chest, grinning hungrily down at you."
											:" Opening your eyes, you see "+Util.stringsToStringList(hornyNames, false)+" leaning over you, each of them with a hungry grin on their faces.")
									+ " With a horny [npc.moan], [npc.name] eagerly exclaims,"
									+ " [npc.speech(Good "+morningString+", [pc.name]! I hope you're ready to fuck!)]"
								+ "</p>"));
					}
					return sb.toString();
				}
				
			} else {
				sb.append("<p>"
						+ "<i>Beep-beep... beep-beep... bee-</i>"
					+ "</p>"
					+ "<p>"
						+ "Rolling over, you fumble for your phone, turning off the alarm before sinking back onto your bed with a sigh."
						+ " As comfortable as you are, you decide that you'd better get out of bed, and so, after a minute of lying beneath the covers, you do just that."
						+ " Opening the curtains, you set about gathering your things, and then with one final stretch, you get ready to set out once more..."
					+ "</p>");
			}
			
			if(!slavesWantingToSexPlayer(Main.game.getHourOfDay()).isEmpty()) {
				sb.append("<p style='text-align:center;'>"
							+ "[style.italicsGood(You feel completely refreshed!)]"
						+ "</p>");
			}
			
			return sb.toString();
		}

		@Override
		public String getResponseTabTitle(int index) {
			if(!slavesWantingToSexPlayer(Main.game.getHourOfDay()).isEmpty()) {
				return null;
			}
			return LilayaHomeGeneric.getLilayasHouseStandardResponseTabs(index);
		}
		
		@Override
		public Response getResponse(int responseTab, int index) {
			List<GameCharacter> hornySlaves = slavesWantingToSexPlayer(Main.game.getHourOfDay());
			if(!hornySlaves.isEmpty()) {
				boolean soloSex = hornySlaves.size()==1;
				List<String> names = new ArrayList<>();
				hornySlaves.stream().forEach((npc) -> names.add(npc.getName()));
				List<GameCharacter> spectators = new ArrayList<>(slavesInRoom(Main.game.getHourOfDay()));
				spectators.removeAll(hornySlaves);
				
				UtilText.addSpecialParsingString(String.valueOf(soloSex), true);
				UtilText.addSpecialParsingString(Util.stringsToStringList(names, false), false);
				
				if(index==1) {
					if(Main.game.getPlayer().hasTrait(Perk.HEAVY_SLEEPER, true)) {
						return new ResponseSex("Sleep sex",
								(soloSex
									?UtilText.parse(hornySlaves.get(0), "[npc.Name] fucks you in your sleep...")
									:Util.stringsToStringList(names, false)+" fuck you in your sleep..."),
								false,
								false,
								new SMGeneric(
										hornySlaves,
										Util.newArrayListOfValues(Main.game.getPlayer()),
										null,
										null,
										ResponseTag.PREFER_MISSIONARY){
									@Override
									public SexPace getStartingSexPaceModifier(GameCharacter character) {
										if(!character.isPlayer()) {
											return SexPace.DOM_GENTLE;
										}
										return super.getStartingSexPaceModifier(character);
									}
									@Override
									public SexPace getForcedSexPace(GameCharacter character) {
										if(!character.isPlayer()) {
											return SexPace.DOM_GENTLE;
										}
										return super.getForcedSexPace(character);
									}
									@Override
									public Map<ImmobilisationType, Map<GameCharacter, Set<GameCharacter>>> getStartingCharactersImmobilised() {
										Map<ImmobilisationType, Map<GameCharacter, Set<GameCharacter>>> map = new HashMap<>();
										map.put(ImmobilisationType.SLEEP, new HashMap<>());
										map.get(ImmobilisationType.SLEEP).put(hornySlaves.get(0), Util.newHashSetOfValues(Main.game.getPlayer()));
										return map;
									}
								},
								POST_WAKE_UP_SEX,
								UtilText.parseFromXMLFile("places/dominion/lilayasHome/playersRoom", "BED_SEX_START_SLEEP", hornySlaves)) {
							@Override
							public void effects() {
								Main.game.getPlayer().addStatusEffect(StatusEffect.SLEEPING_HEAVY, -1);
								for(GameCharacter slave : hornySlaves) {
									((NPC)slave).addFlag(NPCFlagValue.slaveBedroomHadSleepSex);
								}
							}
						};
						
					} else {
						return new ResponseSex("Sex",
								(soloSex
									?UtilText.parse(hornySlaves.get(0), "[npc.Name] forces [npc.herself] on you...")
									:Util.stringsToStringList(names, false)+" force themselves on you..."),
								!hornySlaves.stream().anyMatch(s->s.isWillingToRape(Main.game.getPlayer()) && s.hasSlavePermissionSetting(SlavePermissionSetting.SEX_RAPIST)),
								!hornySlaves.stream().anyMatch(s->s.isWillingToRape(Main.game.getPlayer()) && s.hasSlavePermissionSetting(SlavePermissionSetting.SEX_RAPIST)),
								new SMGeneric(
										hornySlaves,
										Util.newArrayListOfValues(Main.game.getPlayer()),
										spectators,
										null,
										ResponseTag.PREFER_MISSIONARY),
								POST_WAKE_UP_SEX,
								UtilText.parseFromXMLFile("places/dominion/lilayasHome/playersRoom", "BED_SEX_START", hornySlaves));
					}
					
				} else if(index==2) {
					if(Main.game.getPlayer().hasTrait(Perk.HEAVY_SLEEPER, true)) {
						return new Response("Refuse",
								"As you're deeply asleep, you can't say no!",
								null);
						
					} else if(hornySlaves.stream().anyMatch(s->s.isWillingToRape(Main.game.getPlayer()) && s.hasSlavePermissionSetting(SlavePermissionSetting.SEX_RAPIST))) {
						GameCharacter rapist = hornySlaves.stream().filter(s->s.isWillingToRape(Main.game.getPlayer()) && s.hasSlavePermissionSetting(SlavePermissionSetting.SEX_RAPIST)).findFirst().get();
						return new Response("Refuse",
								UtilText.parse(rapist, "As you've given [npc.herHim] permission to rape, [npc.nameIsFull] not going to take no for an answer!"),
								null);
						
					} else {
						return new Response("Refuse",
								(soloSex
										?UtilText.parse(hornySlaves.get(0), "You really aren't in the mood right now, so firmly tell [npc.name] to stop.")
										:"You really aren't in the mood right now, so firmly tell "+ Util.stringsToStringList(names, false)+" to stop."),
								REFUSE_SLAVE_SEX);
						
					}
				}
				return null;
			}
			return getResponseRoom(responseTab, index);
		}

		@Override
		public boolean isInventoryDisabled() {
			return false;
		}
	};
	
	public static final DialogueNode POST_WAKE_UP_SEX = new DialogueNode("Finished", "", false) {
		@Override
		public void applyPreParsingEffects() {
			List<GameCharacter> hornySlaves = new ArrayList<>(Main.sex.getDominantParticipants(false).keySet());
			boolean soloSex = hornySlaves.size()==1;
			List<String> names = new ArrayList<>();
			hornySlaves.stream().forEach((npc) -> names.add(npc.getName()));
			
			UtilText.addSpecialParsingString(String.valueOf(soloSex), true);
			UtilText.addSpecialParsingString(Util.stringsToStringList(names, false), false);
			
			Main.game.appendToTextStartStringBuilder(UtilText.parseFromXMLFile("places/dominion/lilayasHome/playersRoom", "POST_WAKE_UP_SEX", hornySlaves));
			
			Main.game.getPlayer().wakeUp();
		}
		@Override
		public String getDescription() {
			if(Main.game.getPlayer().isAsleep()) {
				return "You continue to sleep...";
			}
			return "You collapse back onto your bed...";
		}
		@Override
		public String getContent() {
			return "";
		}

		@Override
		public String getResponseTabTitle(int index) {
			return LilayaHomeGeneric.getLilayasHouseStandardResponseTabs(index);
		}
		
		@Override
		public Response getResponse(int responseTab, int index) {
			return getResponseRoom(responseTab, index);
		}

		@Override
		public boolean isInventoryDisabled() {
			return false;
		}
	};
	
	public static final DialogueNode REFUSE_SLAVE_SEX = new DialogueNode("", "", false) {
		@Override
		public void applyPreParsingEffects() {
			List<GameCharacter> hornySlaves = new ArrayList<>(slavesWantingToSexPlayer(Main.game.getHourOfDay()));
			boolean soloSex = hornySlaves.size()==1;
			List<String> names = new ArrayList<>();
			hornySlaves.stream().forEach((npc) -> names.add(npc.getName()));
			
			UtilText.addSpecialParsingString(String.valueOf(soloSex), true);
			UtilText.addSpecialParsingString(Util.stringsToStringList(names, false), false);
			
			Main.game.appendToTextStartStringBuilder(UtilText.parseFromXMLFile("places/dominion/lilayasHome/playersRoom", "REFUSE_SLAVE_SEX", hornySlaves));
			
			Main.game.getPlayer().wakeUp();
		}
		@Override
		public String getContent() {
			return "";
		}
		@Override
		public String getResponseTabTitle(int index) {
			return LilayaHomeGeneric.getLilayasHouseStandardResponseTabs(index);
		}
		@Override
		public Response getResponse(int responseTab, int index) {
			return getResponseRoom(responseTab, index);
		}
		@Override
		public boolean isInventoryDisabled() {
			return false;
		}
	};

	public static final DialogueNode AUNT_HOME_PLAYERS_ROOM_QUICK_SHOWER = new DialogueNode("Твоя комната", "", true) {
		@Override
		public void applyPreParsingEffects() {
			// Make sure that the washing slaves don't disappear during this scene:
			for(GameCharacter slave : slavesWashing) {
				slave.setLocation(Main.game.getPlayer(), false);
			}
		}
		@Override
		public String getContent() {
			List<NPC> charactersPresent = LilayaHomeGeneric.getSlavesAndOccupantsPresent();
			List<GameCharacter> slavesWashing = charactersPresent.stream().filter((npc) -> npc.hasSlaveJobSetting(SlaveJob.BEDROOM, SlaveJobSetting.BEDROOM_HELP_WASH)).collect(Collectors.toList());
			UtilText.nodeContentSB.setLength(0);
			
			UtilText.nodeContentSB.append("<p>");
				UtilText.nodeContentSB.append(
						"Wanting to clean yourself, but not spend too much time doing so, you step into your large ensuite bathroom and decide upon taking a quick shower."
								+ " Disrobing, you leave your clothing by the door, before stepping over to your luxurious marble-and-glass walk-in shower.");
			UtilText.nodeContentSB.append("</p>");
			
			if(!slavesWashing.isEmpty()) {
				UtilText.nodeContentSB.append(getShowerSlavesDescription(slavesWashing));
				
			} else {
				UtilText.nodeContentSB.append(
						"<p>"
							+ "Turning on the tap, you let out a relaxed sigh as you feel the warm water flowing down over your naked body."
							+ " Not wanting to spend too much time in the shower, you focus on quickly cleaning yourself..."
						+ "</p>");
			}
			
			return UtilText.nodeContentSB.toString();
		}
		@Override
		public Response getResponse(int responseTab, int index) {
			SexSlot[] showerSlots = new SexSlot[] {
					SexSlotStanding.STANDING_SUBMISSIVE,
					SexSlotStanding.STANDING_SUBMISSIVE_BEHIND,
					SexSlotStanding.STANDING_SUBMISSIVE_TWO,
					SexSlotStanding.STANDING_SUBMISSIVE_BEHIND_TWO};
			
			if(index==1) {
				return new Response("Finish", "Finish having a shower and return to your room.", ROOM) {
					@Override
					public void effects() {
						Main.game.getTextStartStringBuilder().append("<p>");
							if(!slavesWashing.isEmpty()) {
									if(slavesWashing.size()==1) {
										Main.game.getTextStartStringBuilder().append(UtilText.parse(slavesWashing,
												"Not wanting to spend too much time in the shower, you soon tell [npc.name] to turn off the tap and help you to dry off."
													+ " After doing this, the two of you get dressed and step back into your room..."));
									} else {
										Main.game.getTextStartStringBuilder().append(
												"Not wanting to spend too much time in the shower, you soon tell your slaves to turn off the tap and help you to dry off."
													+ " After doing this, you all get dressed and step back into your room...");
									}
								
							} else {
								Main.game.getTextStartStringBuilder().append(
										"After a while, you feel as though you've spent enough time in the shower, and after turning off the tap and drying yourself with a fluffy towel, you get dressed and step back into your room...");
							}
						Main.game.getTextStartStringBuilder().append("</p>");
					}
				};
				
			} else if(index==2) { // If you change this, be aware that it is called in AUNT_HOME_PLAYERS_ROOM_THOROUGH_SHOWER
				if(slavesWashing.isEmpty()) {
					return new Response("Sex", "You do not have any slaves assigned to your bedroom with the washing permission, so there's nobody for you to have sex with...", null);
				}
				
				Map<GameCharacter, SexSlot> slaveSlots = new HashMap<>();
				for(int i=0 ; i<slavesWashing.size() && i<4; i++) {
					slaveSlots.put(slavesWashing.get(i), showerSlots[i]);
				}
				UtilText.addSpecialParsingString(String.valueOf(slavesWashing.size()), true);
				return new ResponseSex("Sex",
						slavesWashing.size()==1
							?UtilText.parse(slavesWashing, "Have dominant sex with [npc.name] in the shower.")
							:"Have dominant sex with your slaves in the shower.",
						true, false,
						new SMShower(SexPosition.STANDING,
								Util.newHashMapOfValues(new Value<>(Main.game.getPlayer(), SexSlotStanding.STANDING_DOMINANT)),
								slaveSlots),
						null,
						null,
						AFTER_SHOWER_SEX,
						UtilText.parseFromXMLFile("places/dominion/lilayasHome/playersRoom", "START_SHOWER_SEX_AS_DOM", slavesWashing));
				
			} else if(index==3) {  // If you change this, be aware that it is called in AUNT_HOME_PLAYERS_ROOM_THOROUGH_SHOWER
				if(slavesWashing.isEmpty()) {
					return new Response("Submissive sex", "You do not have any slaves assigned to your bedroom with the washing permission, so there's nobody for you to have submissive sex with...", null);
				}
				if(!slavesWashing.stream().anyMatch(s->s.isAttractedTo(Main.game.getPlayer()))) {
					return new Response("Submissive sex",
							slavesWashing.size()==1
								?UtilText.parse(slavesWashing, "As [npc.name] isn't attracted to you, [npc.she] is unwilling to be the dominant partner in sex...")
								:"Have dominant sex with your slaves in the shower.",
							null);
				}
				Map<GameCharacter, SexSlot> slaveSlots = new HashMap<>();
				List<GameCharacter> attractedSlaves = slavesWashing.stream().filter(s->s.isAttractedTo(Main.game.getPlayer())).collect(Collectors.toList());
				for(int i=0 ; i<attractedSlaves.size(); i++) {
					slaveSlots.put(attractedSlaves.get(i), showerSlots[i]);
				}
				UtilText.addSpecialParsingString(String.valueOf(slavesWashing.size()), true);
				return new ResponseSex("Submissive sex",
						attractedSlaves.size()==1
								?UtilText.parse(attractedSlaves, "Let [npc.name] dominantly fuck you in the shower.")
										:"Let your slaves dominantly fuck you in the shower.",
						true, true,
						new SMShower(SexPosition.STANDING,
								slaveSlots,
								Util.newHashMapOfValues(new Value<>(Main.game.getPlayer(), SexSlotStanding.STANDING_DOMINANT))),
						null,
						null,
						AFTER_SHOWER_SEX,
						UtilText.parseFromXMLFile("places/dominion/lilayasHome/playersRoom", "START_SHOWER_SEX_AS_SUB", attractedSlaves));
			}
			
			return null;
		
		}
	};

	public static final DialogueNode AUNT_HOME_PLAYERS_ROOM_THOROUGH_SHOWER = new DialogueNode("Твоя комната", "", true) {
		@Override
		public void applyPreParsingEffects() {
			// Make sure that the washing slaves don't disappear during this scene:
			for(GameCharacter slave : slavesWashing) {
				slave.setLocation(Main.game.getPlayer(), false);
			}
		}
		@Override
		public String getContent() {
			List<NPC> charactersPresent = LilayaHomeGeneric.getSlavesAndOccupantsPresent();
			List<GameCharacter> slavesWashing = charactersPresent.stream().filter((npc) -> npc.hasSlaveJobSetting(SlaveJob.BEDROOM, SlaveJobSetting.BEDROOM_HELP_WASH)).collect(Collectors.toList());
			UtilText.nodeContentSB.setLength(0);
			
			UtilText.nodeContentSB.append("<p>");
				UtilText.nodeContentSB.append(
						"Wanting to spend some time thoroughly cleaning yourself, you step into your large ensuite bathroom and decide upon taking a long shower."
						+ " Disrobing, you leave your clothing by the door, before stepping over to your luxurious marble-and-glass walk-in shower.");
			UtilText.nodeContentSB.append("</p>");
			
			if(!slavesWashing.isEmpty()) {
				UtilText.nodeContentSB.append(getShowerSlavesDescription(slavesWashing));
				
			} else {
				UtilText.nodeContentSB.append(
						"<p>"
							+ "Turning on the tap, you let out a contented sigh as you feel the warm water flowing down over your naked body."
							+ " Deciding that it wouldn't be so bad to take some time out in order to relax, you don't rush as you set about thoroughly cleaning yourself."
						+ "</p>");
			}
			
			return UtilText.nodeContentSB.toString();
		}
		@Override
		public Response getResponse(int responseTab, int index) {
			if(index==1) {
				return new Response("Finish", "Finish having a shower and return to your room.", ROOM) {
					@Override
					public void effects() {
						Main.game.getTextStartStringBuilder().append("<p>");
							if(!slavesWashing.isEmpty()) {
									if(slavesWashing.size()==1) {
										Main.game.getTextStartStringBuilder().append(UtilText.parse(slavesWashing,
												"After making sure that every last inch of your body is sparkling clean, you tell [npc.name] to turn off the tap and help you to dry off."
													+ " After doing this, the two of you get dressed and step back into your room..."));
									} else {
										Main.game.getTextStartStringBuilder().append(
												"After making sure that every last inch of your body is sparkling clean, you tell your slaves to turn off the tap and help you to dry off."
													+ " After doing this, you all get dressed and step back into your room...");
									}
								
							} else {
								Main.game.getTextStartStringBuilder().append(
										"After a while, you feel as though you've spent enough time in the shower, and after turning off the tap and drying yourself with a fluffy towel, you get dressed and step back into your room...");
							}
						Main.game.getTextStartStringBuilder().append("</p>");
					}
				};
				
			} if(index==2) {
				return AUNT_HOME_PLAYERS_ROOM_QUICK_SHOWER.getResponse(responseTab, index);
				
			} else if(index==3) {
				return AUNT_HOME_PLAYERS_ROOM_QUICK_SHOWER.getResponse(responseTab, index);
			}
			
			return null;
		}
	};
	
	public static final DialogueNode AFTER_SHOWER_SEX = new DialogueNode("Finished", "", true) {
		@Override
		public void applyPreParsingEffects() {
			for(GameCharacter npc : slavesWashing) {
				npc.applyWash(true, true, StatusEffect.CLEANED_SHOWER, 240+30);
			}
			Main.game.getPlayer().applyWash(true, true, StatusEffect.CLEANED_SHOWER, 240+30);
		}
		@Override
		public String getDescription() {
			return "Having had their fun, your slaves remind you that you have other things you need to be getting on with...";
		}
		@Override
		public String getContent() {
			return UtilText.parseFromXMLFile("places/dominion/lilayasHome/playersRoom", "AFTER_SHOWER_SEX", slavesWashing);
		}
		@Override
		public Response getResponse(int responseTab, int index) {
			if(index==1) {
                return new Response("Продолжить", "Now that you've had your fun, it's time to return to your room.", ROOM);
			}
			return null;
		}
	};

	public static final DialogueNode AUNT_HOME_PLAYERS_ROOM_BATH = new DialogueNode("Твоя комната", "", true) {
		@Override
		public void applyPreParsingEffects() {
			// Make sure that the washing slaves don't disappear during this scene:
			for(GameCharacter slave : slavesWashing) {
				slave.setLocation(Main.game.getPlayer(), false);
			}
		}
		@Override
		public String getContent() {
			UtilText.nodeContentSB.setLength(0);
			
			UtilText.nodeContentSB.append("<p>");
				UtilText.nodeContentSB.append(
						"Wanting to spend some time relaxing and getting cleaned, you step into your large ensuite bathroom and decide upon taking a long bath."
						+ " Disrobing, you leave your clothing by the door, before stepping over to your luxurious marble bathtub.");
			UtilText.nodeContentSB.append("</p>");
			
			if(!slavesWashing.isEmpty()) {
				UtilText.nodeContentSB.append(getBathSlavesDescription(slavesWashing));
				
			} else {
				UtilText.nodeContentSB.append(
						"<p>"
							+ "Turning on the taps and running yourself a bath, you slip down into the hot water and let out a contented sigh."
							+ " Deciding that it wouldn't be so bad to take some time out in order to relax, you don't rush as you set about thoroughly cleaning yourself..."
						+"</p>");
			}
			
			return UtilText.nodeContentSB.toString();
		}
		@Override
		public Response getResponse(int responseTab, int index) {
			SexSlot[] bathSlots = new SexSlot[] {
					SexSlotLyingDown.MISSIONARY,
					SexSlotLyingDown.BESIDE,
					SexSlotLyingDown.BESIDE_TWO,
					SexSlotLyingDown.BESIDE_THREE};
			
			if(index==1) {
				return new Response("Finish", "Finish having a bath and return to your room.", ROOM) {
					@Override
					public void effects() {
						Main.game.getTextStartStringBuilder().append("<p>");
							if(!slavesWashing.isEmpty()) {
									if(slavesWashing.size()==1) {
										Main.game.getTextStartStringBuilder().append(UtilText.parse(slavesWashing,
												"After making sure that every last inch of your body is sparkling clean, you pull the plug, [pc.step] out of the bath, and tell [npc.name] to help you to dry off."
														+ " After doing this, the two of you get dressed and step back into your room..."));
									} else {
										Main.game.getTextStartStringBuilder().append(
												"After making sure that every last inch of your body is sparkling clean, you pull the plug, you [pc.step] out of the bath, and tell your slaves to help you to dry off."
														+ " After doing this, you all get dressed and step back into your room...");
									}
								
							} else {
								Main.game.getTextStartStringBuilder().append(
										"After having a nice relaxing soak, you feel as though you've spent enough time in the bath."
										+ " Pulling the plug and drying yourself off, you quickly get dressed and step back into your room...");
							}
						Main.game.getTextStartStringBuilder().append("</p>");
					}
				};
				
			} else if(index==2) {
				if(slavesWashing.isEmpty()) {
					return new Response("Sex", "You do not have any slaves assigned to your bedroom with the washing permission, so there's nobody for you to have sex with...", null);
				}
				
				Map<GameCharacter, SexSlot> slaveSlots = new HashMap<>();
				for(int i=0 ; i<slavesWashing.size() && i<4; i++) {
					slaveSlots.put(slavesWashing.get(i), i==0?SexSlotLyingDown.LYING_DOWN:bathSlots[i]);
				}
				UtilText.addSpecialParsingString(String.valueOf(slavesWashing.size()), true);
				return new ResponseSex("Sex",
						slavesWashing.size()==1
							?UtilText.parse(slavesWashing, "Have dominant sex with [npc.name] in the bath.")
							:"Have dominant sex with your slaves in the bath.",
						true, false,
						new SMBath(SexPosition.LYING_DOWN,
								Util.newHashMapOfValues(new Value<>(Main.game.getPlayer(), SexSlotLyingDown.MISSIONARY)),
								slaveSlots),
						null,
						null,
						AFTER_BATH_SEX,
						UtilText.parseFromXMLFile("places/dominion/lilayasHome/playersRoom", "START_BATH_SEX_AS_DOM", slavesWashing));
				
			} else if(index==3) {
				if(slavesWashing.isEmpty()) {
					return new Response("Submissive sex", "You do not have any slaves assigned to your bedroom with the washing permission, so there's nobody for you to have submissive sex with...", null);
				}
				if(!slavesWashing.stream().anyMatch(s->s.isAttractedTo(Main.game.getPlayer()))) {
					return new Response("Submissive sex",
							slavesWashing.size()==1
								?UtilText.parse(slavesWashing, "As [npc.name] isn't attracted to you, [npc.she] is unwilling to be the dominant partner in sex...")
								:"Have dominant sex with your slaves in the bath.",
							null);
				}
				Map<GameCharacter, SexSlot> slaveSlots = new HashMap<>();
				List<GameCharacter> attractedSlaves = slavesWashing.stream().filter(s->s.isAttractedTo(Main.game.getPlayer())).collect(Collectors.toList());
				for(int i=0 ; i<attractedSlaves.size(); i++) {
					slaveSlots.put(attractedSlaves.get(i), bathSlots[i]);
				}
				UtilText.addSpecialParsingString(String.valueOf(attractedSlaves.size()), true);
				return new ResponseSex("Submissive sex",
						attractedSlaves.size()==1
								?UtilText.parse(attractedSlaves, "Let [npc.name] dominantly fuck you in the bath.")
										:"Let your slaves dominantly fuck you in the bath.",
						true, true,
						new SMBath(SexPosition.LYING_DOWN,
								slaveSlots,
								Util.newHashMapOfValues(new Value<>(Main.game.getPlayer(), SexSlotLyingDown.LYING_DOWN))),
						null,
						null,
						AFTER_BATH_SEX,
						UtilText.parseFromXMLFile("places/dominion/lilayasHome/playersRoom", "START_BATH_SEX_AS_SUB", attractedSlaves));
			}
			
			return null;
		}
	};

	public static final DialogueNode AFTER_BATH_SEX = new DialogueNode("Finished", "", true) {
		@Override
		public void applyPreParsingEffects() {
			for(GameCharacter npc : slavesWashing) {
				npc.applyWash(true, true, StatusEffect.CLEANED_BATH, 240+30);
			}
			Main.game.getPlayer().applyWash(true, true, StatusEffect.CLEANED_BATH, 240+30);
		}
		@Override
		public String getDescription() {
			return "Having had their fun, your slaves remind you that you have other things you need to be getting on with...";
		}
		@Override
		public String getContent() {
			return UtilText.parseFromXMLFile("places/dominion/lilayasHome/playersRoom", "AFTER_BATH_SEX", slavesWashing);
		}
		@Override
		public Response getResponse(int responseTab, int index) {
			if(index==1) {
                return new Response("Продолжить", "Now that you've had your fun, it's time to return to your room.", ROOM);
			}
			return null;
		}
	};
	
	public static final DialogueNode AUNT_HOME_PLAYERS_ROOM_MAKEUP = new DialogueNode("Hairstyle & Makeup", "", true) {
		@Override
		public void applyPreParsingEffects() {
			BodyChanging.setTarget(getMakeupTarget());
		}
		@Override
		public String getHeaderContent() {
			return MiscDialogue.getMakeupDialogue(true,
					BodyChanging.getTarget().isPlayer()
						?"You sit down in front of the mirror and prepare to get started on improving your appearance..."
						:UtilText.parse(BodyChanging.getTarget(), "You get [npc.name] to sit down in front of the mirror and prepare to get started on improving [npc.her] appearance...")).getHeaderContent();
		}
		@Override
		public String getContent() {
			return "";
		}
		@Override
		public Response getResponse(int responseTab, int index) {
			if(index==1) {
				return new Response("Finish", "Finish doing your makeup in the mirror and return to your bedroom.", ROOM);
			}
			return null;
		}
	};
	
	/** Calendar's associated animal-morphs are based on the twelve animals of the Chinese zodiac, with the Monkey being replaced with a demon, the Rooster with a harpy, and the Snake with a lamia.
	 *  The ordering of the demon and harpy have also been switched, so that October has demons.<br/>
	 *  There is also a 15% chance of giving a different, random animal-morph for each month.<br/>
	 * Animals are:<br/>
	 * Rat, Cow, Tiger, Rabbit, Dragon, Lamia (Snake), Horse, Sheep/Goat, Harpy (Rooster), Demon (Monkey), Dog, Pig
	 */
	private static String getCalendarImageDescription(Month month) {
		StringBuilder sb = new StringBuilder();

		sb.append("<p>"
				+ "Перелистываешь календаря, пока не попадёшь на страницу " + month.getDisplayName(TextStyle.FULL, RUSSIAN_LOCALE) + ", видишь, что в этом месяце на картинке ");
		if (Main.game.getPlayer().getSexualOrientation() == SexualOrientation.ANDROPHILIC) {
			sb.append("изображён ");
		} else {
			sb.append("изображена ");
		}
		if(Util.random.nextInt()<15) {
			if(Main.game.getPlayer().getSexualOrientation()==SexualOrientation.ANDROPHILIC) {
				sb.append(UtilText.returnStringAtRandom(
						"красивый тритон, который деловито разминает мускулы, сидя на скале, окатываемой волнами.",
						"мускулистый оленопарень, который с ухмылкой демонстрирует свой огромный член."));
			} else {
				sb.append(UtilText.returnStringAtRandom(
						"прекрасная русалка, которая с удовольствием демонстрирует свою обнаженную грудь, сидя на скале, окатываемой волнами.",
						"изящная оленодевушка, которая склонилась над деревянным столом и демонстрирует свою мокрую киску."));
			}

		} else {
			switch(month) {
				case JANUARY:
					if(Main.game.getPlayer().getSexualOrientation()==SexualOrientation.ANDROPHILIC) {
						sb.append("подтянутый " + Subspecies.RAT_MORPH.getSingularMaleName(null) + ", который озорно ухмыляется, поглаживая свой толстый эрегированный член.");
					} else {
						sb.append("похотливая " + Subspecies.RAT_MORPH.getSingularFemaleName(null) + ", которая перегнулась через стол, чтобы показать свою капающую киску.");
					}
					break;
				case FEBRUARY:
					if(Main.game.getPlayer().getSexualOrientation()==SexualOrientation.ANDROPHILIC) {
						sb.append("топлес " + Subspecies.COW_MORPH.getSingularMaleName(null) + "."
								+ " Его огромные мышцы напрягаются, когда он несёт срубленное дерево через одно плечо, а между его ног можно заметить огромную выпуклость, которая давит на ткань его шорт.");
					} else {
						sb.append("чёрно-белая " + Subspecies.COW_MORPH.getSingularFemaleName(null) + ", которая сидит на маленькой доильной платформе."
								+ " Со счастливой улыбкой на лице она деловито щиплет и дёргает свои набухшие соски, заставляя струйку молока вытекать в металлическое ведро.");
					}
					break;
				case MARCH:
					if(Main.game.getPlayer().getSexualOrientation()==SexualOrientation.ANDROPHILIC) {
						sb.append("свирепого вида " + Subspecies.getSubspeciesFromId("innoxia_panther_subspecies_tiger").getSingularMaleName(null) + "."
								+ " Приняв доминирующую позу, он оскалился в зубастой ухмылке, явно возбуждённый тем, что его огромный кошачий член выставлен на всеобщее обозрение.");
					} else {
						sb.append("a fierce-looking "+Subspecies.getSubspeciesFromId("innoxia_panther_subspecies_tiger").getSingularFemaleName(null)+"."
								+ " Приняв доминирующую позу, она оскалилась в зубастой ухмылке, явно возбужденная тем, что её большая грудь и тугая киска выставлены на всеобщее обозрение.");
					}
					break;
				case APRIL:
					if(Main.game.getPlayer().getSexualOrientation()==SexualOrientation.ANDROPHILIC) {
						sb.append("красивый " + Subspecies.RABBIT_MORPH.getSingularMaleName(null) + ", который держит свой массивный член в одной руке, а другой соблазнительно помахивает.");
					} else {
						sb.append("три краснеющих " + Subspecies.RABBIT_MORPH.getPluralFemaleName(null) + ", стоят на четвереньках, бок о бок, демонстрируя свои киски.");
					}
					break;
				case MAY:
					if(Main.game.getPlayer().getSexualOrientation()==SexualOrientation.ANDROPHILIC) {
						sb.append("могущественный дракон, восседающий на золотом троне, стоящем на вершине огромной кучи сокровищ."
								+ " Его огромный, покрытый чешуёй член выставлен на всеобщее обозрение, и с ухмылкой на лице он смотрит на тебя выжидающе, как будто ждёт, что ты заберёшься на него и попробуешь на вкус.");
					} else {
						sb.append("могущественная драконица, восседающая на золотом троне, стоящем на вершине огромной кучи сокровищ."
								+ " Её мокрая чешуйчатая киска полностью выставлена напоказ, и с ухмылкой на лице она смотрит на тебя выжидающе, как будто ждет, когда ты заберешься к ней и попробуешь на вкус.");
					}
					break;
				case JUNE:
					if(Main.game.getPlayer().getSexualOrientation()==SexualOrientation.ANDROPHILIC) {
						sb.append("экзотически выглядящий мужчина-ламия."
								+ " Он явно возбуждён и жаждет секса с кем-то, потому что его члены-близнецы высунулись из клоаки; их головки уже блестят на солнце от слизистой спермы, которую они начинают выделять.");
					} else {
						sb.append("экзотически выглядящая женщина-ламия."
								+ " Она явно возбуждена и жаждет секса с кем-то, ведь она тянется вниз, чтобы раздвинуть свою клоаку и показать свою мокрую от капель киску.");
					}
					break;
				case JULY:
					if(Main.game.getPlayer().getSexualOrientation()==SexualOrientation.ANDROPHILIC) {
						sb.append("впечатляюще одаренный " + Subspecies.HORSE_MORPH.getSingularMaleName(null) + ", который напрягает мускулы, демонстрируя свой полностью эрегированный член.");
					} else {
						sb.append("подтянутая  " + Subspecies.HORSE_MORPH.getSingularFemaleName(null) + ", прислонившаяся к забору и виляющая хвостом в разные стороны, чтобы продемонстрировать свою звериную киску.");
					}
					break;
				case AUGUST:
					if(Main.game.getPlayer().getSexualOrientation()==SexualOrientation.ANDROPHILIC) {
						sb.append("овцепаень и козлопарень, стоящие бок о бок и демонстрирующие свои эрегированные члены, игриво подмигивающие.");
					} else {
						sb.append("шерстяные овечкодевушка и козодевушка, которые лежат и раздвигают ножки, демонстрируя свои тугие, влажные киски.");
					}
					break;
				case SEPTEMBER:
					if(Main.game.getPlayer().getSexualOrientation()==SexualOrientation.ANDROPHILIC) {
						sb.append("необычайно мужественная гарпия."
								+ " Несмотря на то, что размер его члена оставляет желать лучшего, он очень красив. Ты чувствуешь, как учащается сердцебиение, когда видишь, как он подмигивает тебе.");
					} else {
						sb.append("красивая женщина-гарпия."
								+ " Несмотря на то, что она охотно демонстрирует свою мокрую киску, выражение её лица выражает снисходительное превосходство,"
								+ " и у тебя создается впечатление, что она выдвинет какое-то возмутительное требование в обмен на разрешение заняться с ней сексом.");
					}
					break;
				case OCTOBER:
					if(Main.game.getPlayer().getSexualOrientation()==SexualOrientation.ANDROPHILIC) {
						sb.append("подтянутый, симпатичный " + Subspecies.DEMON.getSingularMaleName(null) + ", который заманчиво подмигивает тебе, проводя пальцами по своему огромному эрегированному члену.");
					} else {
						sb.append("подтянутая, красивая " + Subspecies.DEMON.getSingularFemaleName(null) + ", в одной лишь шляпе ведьмы, которая заманчиво подмигивает тебе, проводя пальцами по своей мокрой киске и огромной груди.");
					}
					break;
				case NOVEMBER:
					if(Main.game.getPlayer().getSexualOrientation()==SexualOrientation.ANDROPHILIC) {
						sb.append("энергичный " + Subspecies.DOG_MORPH.getSingularMaleName(null) + ", который улыбается тебе, поглаживая свой эрегированный, узловатый собачий член.");
					} else {
						sb.append("возбужденно выглядящая " + Subspecies.DOG_MORPH.getSingularFemaleName(null) + ", которая опустилась на четвереньки, приподняв бедра, чтобы показать свою мокрую киску.");
					}
					break;
				case DECEMBER:
					if(Main.game.getPlayer().getSexualOrientation()==SexualOrientation.ANDROPHILIC) {
						sb.append("мускулистый хрякопарень, который с предвкушением ухмыляется, поглаживая свой огромный член и пару массивных, наполненных спермой яиц.");
					} else {
						sb.append("симпатичная, румяная свинодевушка, которая прислонилась спиной к стене и тянется вниз, чтобы раздвинуть свою пухлую розовую киску.");
					}
					break;
			}
		}

		sb.append(" Полюбовавшись на картинку несколько мгновений, ты заставляешь себя отвести взгляд и прочитать информацию, написанную ниже:"
				+ "</p>");

		return sb.toString();
	}

	public static final DialogueNode AUNT_HOME_PLAYERS_ROOM_CALENDAR_JANUARY = new DialogueNode("Календарь", "", true) {

		@Override
		public String getContent() {
			UtilText.nodeContentSB.setLength(0);

			UtilText.nodeContentSB.append(getCalendarImageDescription(Month.JANUARY));

			UtilText.nodeContentSB.append(
					"<h6 style='text-align:center;'>"
							+ "[style.italicsMinorBad(В настоящее время нет особых мероприятий в январе.)]");

			return UtilText.nodeContentSB.toString();
		}
		@Override
		public Response getResponse(int responseTab, int index) {
			if(index==1) {
				return new Response("Январь", "Ты уже просматриваешь страницу календаря, посвященную январю.", null);
			}
			return AUNT_HOME_PLAYERS_ROOM_CALENDAR.getResponse(responseTab, index);
		}
	};

	public static final DialogueNode AUNT_HOME_PLAYERS_ROOM_CALENDAR_FEBRUARY = new DialogueNode("Календарь", "", true) {

		@Override
		public String getContent() {
			UtilText.nodeContentSB.setLength(0);

			UtilText.nodeContentSB.append(getCalendarImageDescription(Month.FEBRUARY));

			UtilText.nodeContentSB.append(
					"<h6 style='text-align:center;'>"
							+ "[style.italicsMinorBad(В настоящее время нет особых мероприятий в феврале.)]");

			return UtilText.nodeContentSB.toString();
		}
		@Override
		public Response getResponse(int responseTab, int index) {
			if(index==2) {
				return new Response("Февраль", "Ты уже просматриваешь страницу календаря, посвященную февралю.", null);
			}
			return AUNT_HOME_PLAYERS_ROOM_CALENDAR.getResponse(responseTab, index);
		}
	};

	public static final DialogueNode AUNT_HOME_PLAYERS_ROOM_CALENDAR_MARCH = new DialogueNode("Календарь", "", true) {

		@Override
		public String getContent() {
			UtilText.nodeContentSB.setLength(0);

			UtilText.nodeContentSB.append(getCalendarImageDescription(Month.MARCH));

			UtilText.nodeContentSB.append(
					"<h6 style='text-align:center;'>"
							+ "[style.italicsMinorBad(В настоящее время нет особых мероприятий в марте.)]");

			return UtilText.nodeContentSB.toString();
		}
		@Override
		public Response getResponse(int responseTab, int index) {
			if(index==3) {
				return new Response("Март", "Ты уже просматриваешь страницу календаря, посвященную марту.", null);
			}
			return AUNT_HOME_PLAYERS_ROOM_CALENDAR.getResponse(responseTab, index);
		}
	};

	public static final DialogueNode AUNT_HOME_PLAYERS_ROOM_CALENDAR_APRIL = new DialogueNode("Календарь", "", true) {

		@Override
		public String getContent() {
			UtilText.nodeContentSB.setLength(0);

			UtilText.nodeContentSB.append(getCalendarImageDescription(Month.APRIL));

			UtilText.nodeContentSB.append(
					"<h6 style='text-align:center;'>"
							+ "[style.italicsMinorBad(В настоящее время нет особых мероприятий в апреле.)]");

			return UtilText.nodeContentSB.toString();
		}
		@Override
		public Response getResponse(int responseTab, int index) {
			if(index==4) {
				return new Response("Апрель", "Ты уже просматриваешь страницу календаря, посвященную апрелю.", null);
			}
			return AUNT_HOME_PLAYERS_ROOM_CALENDAR.getResponse(responseTab, index);
		}
	};

	public static final DialogueNode AUNT_HOME_PLAYERS_ROOM_CALENDAR_MAY = new DialogueNode("Календарь", "", true) {

		@Override
		public String getContent() {
			UtilText.nodeContentSB.setLength(0);

			UtilText.nodeContentSB.append(getCalendarImageDescription(Month.MAY));

			UtilText.nodeContentSB.append(
					"<h4 style='text-align:center;'>"
							+ "<span style='color:" + PresetColour.BASE_BLUE_LIGHT.toWebHexString() + ";'>Май</span>"
					+ "</h4>"
					+ "<h6 style='text-align:center;'>"
							+ "<span style='color:" + PresetColour.BASE_PINK_LIGHT.toWebHexString() + ";'>Неделя материнства</span>"
						+ "<br/>"
							+ "8-14 мая"
					+ "</h6>"
					+ "<p><i>"
							+ "Вторая неделя мая - это время, когда чествуют матерей, материнство и природу материнской связи между матерью и ребёнком."
							+ " В это время всем жителям Доминиона бесплатно предоставляются препараты, повышающие фертильность, которые раздаются волонтёрами на главных бульварах."
							+ " Таким образом Лилит демонстрирует свою любовь к матерям и заботится о том, чтобы их стало гораздо больше.!"
					+ "</i></p>");
			
			return UtilText.nodeContentSB.toString();
		}


		@Override
		public Response getResponse(int responseTab, int index) {
			if(index==5) {
				return new Response("Май", "Ты уже просматриваешь страницу календаря, посвященную маю.", null);
			}
			return AUNT_HOME_PLAYERS_ROOM_CALENDAR.getResponse(responseTab, index);
		}
	};

	public static final DialogueNode AUNT_HOME_PLAYERS_ROOM_CALENDAR_JUNE = new DialogueNode("Календарь", "", true) {

		@Override
		public String getContent() {
			UtilText.nodeContentSB.setLength(0);

			UtilText.nodeContentSB.append(getCalendarImageDescription(Month.JUNE));

			UtilText.nodeContentSB.append(
					"<h4 style='text-align:center;'>"
							+ "<span style='color:" + PresetColour.BASE_BLUE_LIGHT.toWebHexString() + ";'>Июнь</span>"
					+ "</h4>"
					+"<h6 style='text-align:center;'>"
							+ "<span style='color:" + PresetColour.BASE_BLUE.toWebHexString() + ";'>Неделя отцовства</span>"
						+ "<br/>"
							+ "15-21 июня"
					+ "</h6>"
					+ "<p><i>"
							+ "Третья неделя июня - это время, когда чествуют отцов, отцовство и природу отцовской связи между отцом и ребёнком."
							+ " В это время всем жителям Доминиона бесплатно предоставляются препараты, повышающие фертильность, которые раздаются волонтёрами на главных бульварах."
							+ " Таким образом Лилит демонстрирует свою любовь к отцам и заботится о том, чтобы их было гораздо больше!"
					+ "</i></p>");
			
			return UtilText.nodeContentSB.toString();
		}


		@Override
		public Response getResponse(int responseTab, int index) {
			if(index==6) {
				return new Response("Июнь", "Ты уже просматриваешь страницу календаря, посвященную июню.", null);
			}
			return AUNT_HOME_PLAYERS_ROOM_CALENDAR.getResponse(responseTab, index);
		}
	};

	public static final DialogueNode AUNT_HOME_PLAYERS_ROOM_CALENDAR_JULY = new DialogueNode("Календарь", "", true) {

		@Override
		public String getContent() {
			UtilText.nodeContentSB.setLength(0);

			UtilText.nodeContentSB.append(getCalendarImageDescription(Month.JULY));

			UtilText.nodeContentSB.append(
					"<h6 style='text-align:center;'>"
							+ "[style.italicsMinorBad(В настоящее время нет особых мероприятий в июле.)]");

			return UtilText.nodeContentSB.toString();
		}
		@Override
		public Response getResponse(int responseTab, int index) {
			if(index==7) {
				return new Response("Июль", "Ты уже просматриваешь страницу календаря, посвященную июлю.", null);
			}
			return AUNT_HOME_PLAYERS_ROOM_CALENDAR.getResponse(responseTab, index);
		}
	};

	public static final DialogueNode AUNT_HOME_PLAYERS_ROOM_CALENDAR_AUGUST = new DialogueNode("Календарь", "", true) {

		@Override
		public String getContent() {
			UtilText.nodeContentSB.setLength(0);

			UtilText.nodeContentSB.append(getCalendarImageDescription(Month.AUGUST));

			UtilText.nodeContentSB.append(
					"<h6 style='text-align:center;'>"
							+ "[style.italicsMinorBad(В настоящее время нет особых мероприятий в августе.)]");

			return UtilText.nodeContentSB.toString();
		}
		@Override
		public Response getResponse(int responseTab, int index) {
			if(index==8) {
				return new Response("Август", "Ты уже просматриваешь страницу календаря, посвященную августу.", null);
			}
			return AUNT_HOME_PLAYERS_ROOM_CALENDAR.getResponse(responseTab, index);
		}
	};

	public static final DialogueNode AUNT_HOME_PLAYERS_ROOM_CALENDAR_SEPTEMBER = new DialogueNode("Календарь", "", true) {

		@Override
		public String getContent() {
			UtilText.nodeContentSB.setLength(0);

			UtilText.nodeContentSB.append(getCalendarImageDescription(Month.SEPTEMBER));

			UtilText.nodeContentSB.append(
					"<h6 style='text-align:center;'>"
							+ "[style.italicsMinorBad(В настоящее время нет особых мероприятий в сентябре.)]");

			return UtilText.nodeContentSB.toString();
		}
		@Override
		public Response getResponse(int responseTab, int index) {
			if(index==9) {
				return new Response("Сентябрь", "Ты уже просматриваешь страницу календаря, посвященную сентябрю.", null);
			}
			return AUNT_HOME_PLAYERS_ROOM_CALENDAR.getResponse(responseTab, index);
		}
	};

	public static final DialogueNode AUNT_HOME_PLAYERS_ROOM_CALENDAR_OCTOBER = new DialogueNode("Календарь", "", true) {

		@Override
		public String getContent() {
			UtilText.nodeContentSB.setLength(0);

			UtilText.nodeContentSB.append(getCalendarImageDescription(Month.OCTOBER));

			UtilText.nodeContentSB.append(
					"<h4 style='text-align:center;'>"
							+ "<span style='color:" + PresetColour.BASE_BLUE_LIGHT.toWebHexString() + ";'>Октябрь</span>"
					+ "</h4>"
					+"<h6 style='text-align:center;'>"
							+ "<span style='color:" + PresetColour.GENERIC_ARCANE.toWebHexString() + ";'>Месяц Лилит</span>"
						+ "<br/>"
							+ "Весь месяц"
					+ "</h6>"
					+ "<p><i>"
							+ "Октябрь был выбран самой Лилит как месяц, в котором весь Доминион демонстрирует свою преданность своей славной королеве!"
							+ " Знамёна и ленты, как правило, традиционных цветов Лилит - оранжевого, пурпурного и чёрного, - гордо развеваются над каждым зданием, чтобы показать нашей королеве, насколько преданны ей подданные!"
							+ " Хотя все жители должны праздновать правление Лилит, самые набожные из её почитателей наряжаются в традиционные демонические костюмы, чтобы доказать свою преданность."
					+ "</p>"
					+ "<p>"
							+ "Официально разрешенный «Культ Лилит» - самая фанатичная группа сторонников королевы, и их очень легко распознать в октябре,"
							+ " поскольку они отказываются носить что-либо, кроме традиционных ведьминских нарядов, подобных тем, что носила сама Лилит в прошлые века."
							+ " В остальное время года эти культисты довольствуются тем, что совершают свои акты преданности в уединении, но в октябре они могут проявить немалое рвение,"
							+ " но в октябре они становятся весьма ревностными и иногда даже подходят к людям и требуют от них проявления преданности!"
					+ "</i></p>");
			
			return UtilText.nodeContentSB.toString();
		}


		@Override
		public Response getResponse(int responseTab, int index) {
			if(index==10) {
				return new Response("Октябрь", "Ты уже просматриваешь страницу календаря, посвященную октябрю.", null);
			}
			return AUNT_HOME_PLAYERS_ROOM_CALENDAR.getResponse(responseTab, index);
		}
	};

	public static final DialogueNode AUNT_HOME_PLAYERS_ROOM_CALENDAR_NOVEMBER = new DialogueNode("Календарь", "", true) {

		@Override
		public String getContent() {
			UtilText.nodeContentSB.setLength(0);

			UtilText.nodeContentSB.append(getCalendarImageDescription(Month.NOVEMBER));

			UtilText.nodeContentSB.append(
					"<h6 style='text-align:center;'>"
							+ "[style.italicsMinorBad(В настоящее время нет особых мероприятий в ноябре.)]");

			return UtilText.nodeContentSB.toString();
		}
		@Override
		public Response getResponse(int responseTab, int index) {
			if(index==11) {
				return new Response("Ноябрь", "Ты уже просматриваешь страницу календаря, посвященную ноябрю.", null);
			}
			return AUNT_HOME_PLAYERS_ROOM_CALENDAR.getResponse(responseTab, index);
		}
	};

	public static final DialogueNode AUNT_HOME_PLAYERS_ROOM_CALENDAR_DECEMBER = new DialogueNode("Календарь", "", true) {

		@Override
		public String getContent() {
			UtilText.nodeContentSB.setLength(0);
			
			UtilText.nodeContentSB.append(getCalendarImageDescription(Month.DECEMBER));

			UtilText.nodeContentSB.append(
					"<h4 style='text-align:center;'>"
							+ "<span style='color:" + PresetColour.BASE_BLUE_LIGHT.toWebHexString() + ";'>Декабрь</span>"
					+ "</h4>"
					+ "<h6 style='text-align:center;'>"
							+ "<span style='color:" + PresetColour.BASE_GOLD.toWebHexString() + ";'>Юлэтид</span>"
						+ "<br/>"
							+ "Весь месяц"
					+ "</h6>"
					+ "<i>"
					+ "<p>"
							+ "Празднование Юлэтида длится весь декабрь, а иногда даже затягивается на январь и февраль!"
							+ " Дарить подарки, устраивать пиры и вечеринки - вот способы, которыми празднуется Юлэтид."
							+ " Поскольку этот праздник совпадает с прибытием в Доминион оленоморфов, стало традицией, что подарками, которые дарят во время Юлэтида, являются предметы, купленные у этих оленей-морфов."
					+ "</p>"
					+ "<p>"
							+ "Фигура, связанная с этим сезоном, - Лилин «Йолнир» (что означает „Йоль“ или «Йольская фигура»)."
							+ " Об этой Лилин известно немного, кроме того, что её имя нарушает традицию, согласно которой имена всех Лилин начинаются на букву «Л», и что она является лидером «Дикой охоты»."
					+ "</p>"
					+ "<p>"
							+ "Состоящая из бродячей орды призванных арканных элементалей, «Дикая охота» была изгнана из Доминиона много лет назад, и теперь ее можно встретить только во время Юлэтида в Фолойских полях и близлежащих лесах."
					+ "</p>"
					+ "</i>");
			
			return UtilText.nodeContentSB.toString();
		}


		@Override
		public Response getResponse(int responseTab, int index) {
			if(index==12) {
				return new Response("Декабрь", "Ты уже просматриваешь страницу календаря, посвященную декабрю.", null);
			}
			return AUNT_HOME_PLAYERS_ROOM_CALENDAR.getResponse(responseTab, index);
		}
	};


	public static final DialogueNode AUNT_HOME_PLAYERS_ROOM_CLUBBER_TAKEN_HOME = new DialogueNode("Твоя комната", "", true) {

		@Override
		public int getSecondsPassed() {
			return 30*60;
		}

		@Override
		public String getContent() {
			return UtilText.parseFromXMLFile("places/dominion/nightlife/theWateringHole", "AUNT_HOME_PLAYERS_ROOM_CLUBBER_TAKEN_HOME", NightlifeDistrict.getClubbersPresent());
		}

		@Override
		public Response getResponse(int responseTab, int index) {
			if(index==1) {
				return new ResponseSex("Sex (dom)", UtilText.parse(NightlifeDistrict.getClubbersPresent(), "Have dominant sex with [npc.name]."),
						true, true,
						new SMGeneric(
								Util.newArrayListOfValues(Main.game.getPlayer()),
								Util.newArrayListOfValues(NightlifeDistrict.getClubbersPresent().get(0)),
						null,
						null), BACK_HOME_AFTER_CLUBBER_SEX, UtilText.parseFromXMLFile("places/dominion/nightlife/theWateringHole", "AUNT_HOME_PLAYERS_ROOM_CLUBBER_TAKEN_HOME_SEX_AS_DOM", NightlifeDistrict.getClubbersPresent()));
				
			} else if(index==2) {
				return new ResponseSex("Sex (sub)", UtilText.parse(NightlifeDistrict.getClubbersPresent(), "Have submissive sex with [npc.name]."),
						true, true,
						new SMGeneric(
								Util.newArrayListOfValues(NightlifeDistrict.getClubbersPresent().get(0)),
								Util.newArrayListOfValues(Main.game.getPlayer()),
						null,
						null), BACK_HOME_AFTER_CLUBBER_SEX, UtilText.parseFromXMLFile("places/dominion/nightlife/theWateringHole", "AUNT_HOME_PLAYERS_ROOM_CLUBBER_TAKEN_HOME_SEX_AS_SUB", NightlifeDistrict.getClubbersPresent()));
				
			} else if(index==4) {
				return new Response("Say goodbye",
						UtilText.parse(NightlifeDistrict.getClubbersPresent(), "Tell [npc.name] that you've changed your mind, sending [npc.herHim] home with the promise of seeing [npc.herHim] at the club another time."
								+ "</br>[style.italicsGood(Saves this character, who can then be encountered in the club again.)]"),
						AUNT_HOME_PLAYERS_ROOM_CLUBBER_TAKEN_HOME_SEND_HOME) {
					@Override
					public void effects() {
						Main.game.getTextEndStringBuilder().append(UtilText.parseFromXMLFile("places/dominion/nightlife/theWateringHole", "AUNT_HOME_PLAYERS_ROOM_CLUBBER_TAKEN_HOME_CHANGE_MIND", NightlifeDistrict.getClubbersPresent()));
						NightlifeDistrict.removeClubbers();
						Main.game.setRequestAutosave(true);
					}
				};
				
			} else if(index==5) {
				return new Response("Send home",
						UtilText.parse(NightlifeDistrict.getClubbersPresent(), "Tell [npc.name] that you've changed your mind and abruptly send [npc.herHim] home."
								+ "</br>[style.italicsBad(Removes this character from the game.)]"),
						AUNT_HOME_PLAYERS_ROOM_CLUBBER_TAKEN_HOME_SEND_HOME) {
					@Override
					public void effects() {
						Main.game.getTextEndStringBuilder().append(UtilText.parseFromXMLFile("places/dominion/nightlife/theWateringHole", "AUNT_HOME_PLAYERS_ROOM_CLUBBER_TAKEN_HOME_CHANGE_MIND_RUDE", NightlifeDistrict.getClubbersPresent()));
						NightlifeDistrict.removeClubbers();
						Main.game.setRequestAutosave(true);
					}
				};
				
			} else {
				return null;
			}
		}
	};

	public static final DialogueNode BACK_HOME_AFTER_CLUBBER_SEX = new DialogueNode("Твоя комната", "", true) {
		
		@Override
		public int getSecondsPassed() {
			return 15*60;
		}
		
		@Override
		public String getContent() {
			if(Main.sex.getNumberOfOrgasms(NightlifeDistrict.getPartner())>=NightlifeDistrict.getPartner().getOrgasmsBeforeSatisfied()) {
				return UtilText.parseFromXMLFile("places/dominion/nightlife/theWateringHole", "BACK_HOME_AFTER_CLUBBER_SEX", NightlifeDistrict.getClubbersPresent());
			} else {
				return UtilText.parseFromXMLFile("places/dominion/nightlife/theWateringHole", "BACK_HOME_AFTER_CLUBBER_SEX_NO_ORGASM", NightlifeDistrict.getClubbersPresent());
			}
		}

		@Override
		public Response getResponse(int responseTab, int index) {
			if(index==1) {
				return new Response("See again",
						UtilText.parse(NightlifeDistrict.getClubbersPresent(), "Tell [npc.name] that you hope to see [npc.herHim] again.</br>"
								+ "[style.italicsGood(Saves this character, who can then be encountered in the club again.)]"),
						BACK_HOME_AFTER_SEX) {
					@Override
					public void effects() {
						Main.game.getTextStartStringBuilder().append(UtilText.parseFromXMLFile("places/dominion/nightlife/theWateringHole", "BACK_HOME_AFTER_SEX_SEE_AGAIN", NightlifeDistrict.getClubbersPresent()));
						NightlifeDistrict.saveClubbers();
						Main.game.setRequestAutosave(true);
					}
				};
				
			} else if(index==2) {
				return new Response("Hope not (gentle)",
						UtilText.parse(NightlifeDistrict.getClubbersPresent(), "Make a non-committal response, secretly hoping that you won't see [npc.name] again.</br>[style.italicsBad(Removes this character from the game.)]"),
						BACK_HOME_AFTER_SEX) {
					@Override
					public void effects() {
						Main.game.getTextStartStringBuilder().append(UtilText.parseFromXMLFile("places/dominion/nightlife/theWateringHole", "BACK_HOME_AFTER_SEX_DO_NOT_SEE_AGAIN", NightlifeDistrict.getClubbersPresent()));
						NightlifeDistrict.removeClubbers();
						Main.game.setRequestAutosave(true);
					}
				};
				
			} else if(index==3) {
				return new Response("Hope not (harsh)",
						UtilText.parse(NightlifeDistrict.getClubbersPresent(), "Crudely tell [npc.name] that you were only interested in fucking [npc.herHim].</br>[style.italicsBad(Removes this character from the game.)]"),
						BACK_HOME_AFTER_SEX) {
					@Override
					public void effects() {
						Main.game.getTextStartStringBuilder().append(UtilText.parseFromXMLFile("places/dominion/nightlife/theWateringHole", "BACK_HOME_AFTER_SEX_DO_NOT_SEE_AGAIN_RUDE", NightlifeDistrict.getClubbersPresent()));
						NightlifeDistrict.removeClubbers();
						Main.game.setRequestAutosave(true);
					}
				};
			}
			return null;
		}
	};

	public static final DialogueNode BACK_HOME_AFTER_SEX = new DialogueNode("Твоя комната", "", false) {
		
		@Override
		public int getSecondsPassed() {
			return 2*60;
		}
		
		@Override
		public String getContent() {
			return "";
		}

		@Override
		public String getResponseTabTitle(int index) {
			return LilayaHomeGeneric.getLilayasHouseStandardResponseTabs(index);
		}
		
		@Override
		public Response getResponse(int responseTab, int index) {
			return ROOM.getResponse(responseTab, index);
		}
	};

	public static final DialogueNode AUNT_HOME_PLAYERS_ROOM_CLUBBER_TAKEN_HOME_SEND_HOME = new DialogueNode("Твоя комната", "", false) {
		
		@Override
		public int getSecondsPassed() {
			return 2*60;
		}
		
		@Override
		public String getContent() {
			return "";
		}

		@Override
		public String getResponseTabTitle(int index) {
			return LilayaHomeGeneric.getLilayasHouseStandardResponseTabs(index);
		}
		
		@Override
		public Response getResponse(int responseTab, int index) {
			return ROOM.getResponse(responseTab, index);
		}
	};
}
