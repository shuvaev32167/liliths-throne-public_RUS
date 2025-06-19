package com.lilithsthrone.game.dialogue.places.dominion;

import com.lilithsthrone.game.character.npc.dominion.Daddy;
import com.lilithsthrone.game.character.npc.dominion.Felicia;
import com.lilithsthrone.game.character.npc.dominion.Fiammetta;
import com.lilithsthrone.game.character.quests.Quest;
import com.lilithsthrone.game.character.quests.QuestLine;
import com.lilithsthrone.game.dialogue.DialogueFlagValue;
import com.lilithsthrone.game.dialogue.DialogueManager;
import com.lilithsthrone.game.dialogue.DialogueNode;
import com.lilithsthrone.game.dialogue.npcDialogue.dominion.DaddyDialogue;
import com.lilithsthrone.game.dialogue.places.dominion.feliciaApartment.FeliciaApartment;
import com.lilithsthrone.game.dialogue.places.dominion.zaranixHome.ZaranixHomeGroundFloor;
import com.lilithsthrone.game.dialogue.places.dominion.zaranixHome.ZaranixHomeGroundFloorRepeat;
import com.lilithsthrone.game.dialogue.responses.Response;
import com.lilithsthrone.game.dialogue.utils.UtilText;
import com.lilithsthrone.main.Main;
import com.lilithsthrone.utils.Util;
import com.lilithsthrone.utils.colours.PresetColour;
import com.lilithsthrone.world.Season;
import com.lilithsthrone.world.Weather;
import com.lilithsthrone.world.WorldType;
import com.lilithsthrone.world.places.PlaceType;

import java.time.Month;

/**
 * @since 0.1.0
 * @version 0.3.3.10
 * @author Innoxia
 */
public class DemonHome {
    
    private static Felicia getFelicia() {
        return ((Felicia)Main.game.getNpc(Felicia.class));
    }
	
    private static String getAdditionalDescriptions() {
    	StringBuilder sb = new StringBuilder();
    	
		if(Main.game.getCurrentWeather()==Weather.MAGIC_STORM) {
			sb.append(
					"<p>"
                            + "Магическая буря, бушующая над головой, привлекла в эту область большое количество демонов-энфорсеров."
                            + " Не поддаваясь воздействию бурной силы грозы, эти элитные стражи внимательно следят за тобой, когда ты проходишь через практически пустынный район, известный как «Дом демонов»."
                            + " Никто не сможет напасть на тебя под их бдительным взглядом, и ты сможешь спокойно продолжить свой путь..."
					+ "</p>");
		}

		if(Main.game.getDateNow().getMonth()==Month.OCTOBER) {
			sb.append(
				"<p>"
                        + "<b style='color:" + PresetColour.BASE_ORANGE.toWebHexString() + ";'>Октябрь;</b> <b style='color:" + PresetColour.GENERIC_ARCANE.toWebHexString() + ";'>Месяц Лилит:</b><br/>"
                        + "Оранжевые, чёрные и фиолетовые флаги развеваются почти из каждого окна, и, подняв глаза, ты видишь, что через улицу натянуты большие плакаты, на каждом из которых написан свой лозунг, прославляющий правление Лилит."
                        + " Иногда появляющиеся демоны обычно одеты в костюмы в стиле Хэллоуина, что ничуть не помогает развеять жуткую атмосферу."
				+ "</p>");
		}
		if(Main.game.getDialogueFlags().hasFlag(DialogueFlagValue.hasSnowedThisWinter) && Main.game.getSeason()==Season.WINTER) {
			sb.append(
				"<p>"
                        + "Рабочие оленоморфы отлично справляются с уборкой снега с улиц Доминиона, но крыши домов, деревья и верхушки фонарных столбов по-прежнему покрыты толстым слоем белого снега."
                        + " Ты видишь, как твой выдох выходит из рта в виде небольшого облачка конденсата, но, несмотря на явные признаки низкой температуры воздуха, твоя магическая аура защищает твоё тело от ощущения холода."
				+ "</p>");
		}
		
		return sb.toString();
    }

    public static final DialogueNode DEMON_HOME_GATE = new DialogueNode("Дом демонов (Врата)", "Дом демонов", false) {
		
		@Override
		public int getSecondsPassed() {
			return DominionPlaces.TRAVEL_TIME_STREET;
		}
		
		@Override
		public String getContent() {
			UtilText.nodeContentSB.setLength(0);

			UtilText.nodeContentSB.append(
					"<p>"
                            + "Здесь, через улицу, были построены огромные железные ворота, отделяющие обычные районы Доминиона от района, известного как «Дом демонов»."
                            + " Здесь размещено полдюжины элитных демонов-энфорсеров, которые внимательно следят за всеми, кто приходит и уходит."
					+ "</p>"
					+ "<p>"
                            + "Когда ты идёшь вперёд, чтобы пройти через ворота, ты видишь, как один из этих демонических стражников пристально смотрит на тебя."
                            + " Игнорируя их пронзительные взгляды, ты шагаешь вперёд, вздыхая с облегчением, когда проходишь на другую сторону, не будучи [pc.genderBasedWord(остановленным, остановленной)]."
					+ "</p>");
			
			UtilText.nodeContentSB.append(getAdditionalDescriptions());
			
			return UtilText.nodeContentSB.toString();
		}

		@Override
		public Response getResponse(int responseTab, int index) {
			return null;
		}
	};

    public static final DialogueNode DEMON_HOME_STREET = new DialogueNode("Дом демонов", "Дом демонов", false) {
		
		@Override
		public int getSecondsPassed() {
			return DominionPlaces.TRAVEL_TIME_STREET;
		}
		
		@Override
		public String getContent() {
			UtilText.nodeContentSB.setLength(0);
			
			
			UtilText.nodeContentSB.append(
					"<p>"
                            + "От широких улиц с мраморной мостовой до безупречных фасадов зданий в стиле ампир – совершенно очевидно, что район «Дом демонов» является одним из самых престижных районов Доминиона."
                            + " Многочисленные искусно вырезанные статуи, большинство из которых изображают тех или иных демонов, разбросаны по всей территории, и, учитывая их тематику,"
                            + " ты предполагаешь, что именно эти скульптуры дали название этой местности."
					+ "</p>"
					+ "<p>"
                            + "Прогуливаясь по улице, ты проходишь мимо нескольких огороженных частных садов; их пышная зелень помогает разбавить монотонность кремово-белых каменных фасадов окружающих зданий."
                            + " Несмотря на то, что «Дом демонов» немного тише, чем большинство других районов Доминиона, ты замечаешь, что на улицах патрулирует немного больше энфорсеров;"
                            + " доказательства того, что богатые и влиятельные жители города пользуются дополнительной защитой."
					+ "</p>");
			
			if(Main.game.getPlayerCell().getPlace().getPlaceType().equals(PlaceType.DOMINION_DEMON_HOME_DADDY)) {
				UtilText.nodeContentSB.append(
						"<p>"
                                + "<b style='color:" + PresetColour.RACE_DEMON.toWebHexString() + ";'>Место жительства [daddy.morphSingleNameGene([daddy.name])]:</b><br/>"
                                + "Квартира [daddy.morphSingleNameGene([daddy.name])] расположена в этом конкретном районе «Дома демонов»."
							+ Daddy.getAvailabilityText()
						+ "</p>");
			}

			if(Main.game.getPlayerCell().getPlace().getPlaceType().equals(PlaceType.DOMINION_DEMON_HOME_ARTHUR)) {
				UtilText.nodeContentSB.append(
						"<p>"
								+ "<b style='color:" + PresetColour.RACE_HUMAN.toWebHexString() + ";'>Величественные башни:</b><br/>"
                                + "Дом Артура, «Величественные башни», находится в этом конкретном районе «Дома демонов»."
						+ "</p>");
			}

			UtilText.nodeContentSB.append(getAdditionalDescriptions());
			
			return UtilText.nodeContentSB.toString();
		}

		@Override
		public Response getResponse(int responseTab, int index) {
			return null;
		}
	};

    public static final DialogueNode DEMON_HOME_STREET_ARTHUR = new DialogueNode("Дом демонов", "Дом демонов", false) {
		
		@Override
		public int getSecondsPassed() {
			return DominionPlaces.TRAVEL_TIME_STREET;
		}
		
		@Override
		public String getContent() {
			return DEMON_HOME_STREET.getContent();
		}

		@Override
		public Response getResponse(int responseTab, int index) {
			if (index == 1) {
				if (Main.game.getPlayer().getQuest(QuestLine.MAIN) == Quest.MAIN_1_B_DEMON_HOME) {
                    return new Response("Величественные башни", "Найди квартиру Артура в здании, следуя инструкциям, которые дала тебя Лилайя.", DEMON_HOME_ARTHURS_APARTMENT);
					
				} else if (Main.game.getPlayer().isQuestProgressGreaterThan(QuestLine.MAIN, Quest.MAIN_1_B_DEMON_HOME)) {
                    return new Response("Величественные башни", "Отправляйся в жилой дом «Величественные башни».", DEMON_HOME_ARTHURS_APARTMENT);
					
				} else {
					return null;
				}

			} else {
				return null;
			}
		}
	};

    public static final DialogueNode DEMON_HOME_STREET_ZARANIX = new DialogueNode("Дом демонов", "Дом демонов", false) {
		
		@Override
		public int getSecondsPassed() {
			return DominionPlaces.TRAVEL_TIME_STREET;
		}
		
		@Override
		public String getContent() {
			return DEMON_HOME_STREET.getContent();
		}

		@Override
		public Response getResponse(int responseTab, int index) {
			if (index == 1) {
				if (Main.game.getPlayer().getQuest(QuestLine.MAIN) == Quest.MAIN_1_H_THE_GREAT_ESCAPE) {
                    return new Response("Дом Зараникса", "A little way down the road from Arthur's apartment building stands the home of Zaranix; the demon that Scarlett told you about.", ZaranixHomeGroundFloor.OUTSIDE);
					
				} else if (Main.game.getPlayer().isQuestProgressGreaterThan(QuestLine.MAIN, Quest.MAIN_1_H_THE_GREAT_ESCAPE)) {
                    return new Response("Дом Зараникса", "Pay Zaranix another visit.", ZaranixHomeGroundFloorRepeat.OUTSIDE);
				}
				return null;

			} else {
				return null;
			}
		}
	};

    public static final DialogueNode DEMON_HOME_STREET_DADDY = new DialogueNode("Дом демонов", "Дом демонов", false) {
		
		@Override
		public int getSecondsPassed() {
			return DominionPlaces.TRAVEL_TIME_STREET;
		}
		
		@Override
		public String getContent() {
			return DEMON_HOME_STREET.getContent();
		}

		@Override
		public Response getResponse(int responseTab, int index) {
			if (index == 1) {
				if(!Daddy.isAvailable()) {
					return new Response("[daddy.Name]",
							Daddy.getAvailabilityText(),
							null);
					
				} else if(Main.game.getPlayer().hasCompanions()) {
					return new Response("[daddy.Name]",
							"[style.italicsBad(You cannot meet [daddy.name] while you have companions in your party!)]",
							null);
					
				} else {
					return new Response("[daddy.Name]",
							"Head over to [daddy.namePos] apartment and knock on [daddy.her] door.",
							DaddyDialogue.MEETING) {
						@Override
						public void effects() {
							if(Main.game.getPlayer().isQuestProgressLessThan(QuestLine.SIDE_DADDY, Quest.DADDY_MEETING)) {
								Main.game.getTextStartStringBuilder().append(Main.game.getPlayer().setQuestProgress(QuestLine.SIDE_DADDY, Quest.DADDY_MEETING));
							}
							Main.game.getPlayer().setLocation(WorldType.DADDYS_APARTMENT, PlaceType.DADDY_APARTMENT_ENTRANCE);
							Main.game.getNpc(Daddy.class).setLocation(Main.game.getPlayer(), false);
						}
					};
					
				}

			} else {
				return null;
			}
		}
	};

	public static final DialogueNode DEMON_HOME_ARTHURS_APARTMENT = new DialogueNode("", "-", true) {
		@Override
		public int getSecondsPassed() {
			return DominionPlaces.TRAVEL_TIME_STREET;
		}
		@Override
		public String getContent() {
			return UtilText.parseFromXMLFile("places/dominion/arthursApartment/apartment", "DEMON_HOME_ARTHURS_APARTMENT");
		}
		@Override
		public Response getResponse(int responseTab, int index) {
			if (index == 1) {
				if (Main.game.getPlayer().getQuest(QuestLine.MAIN) == Quest.MAIN_1_B_DEMON_HOME) {
					return new Response("Arthur's room", "Head up to Arthur's room.", DEMON_HOME_ARTHURS_APARTMENT_ARTHURS_ROOM){
						@Override
						public void effects() {
							if (Main.game.getPlayer().getQuest(QuestLine.MAIN) == Quest.MAIN_1_B_DEMON_HOME) {
								Main.game.getTextEndStringBuilder().append(Main.game.getPlayer().setQuestProgress(QuestLine.MAIN, Quest.MAIN_1_C_WOLFS_DEN));
							}
						}
					};
					
				} else if (Main.game.getPlayer().isQuestProgressGreaterThan(QuestLine.MAIN, Quest.MAIN_1_B_DEMON_HOME)) {
					return new Response("Arthur's room", "Arthur is no longer living here...", null);
				}
				
			} else if (index == 2) {
				if (Main.game.getPlayer().isQuestProgressGreaterThan(QuestLine.MAIN, Quest.MAIN_1_B_DEMON_HOME)) {
					return new Response("[felicia.Name]'s room", "Head up to [felicia.namePos] room.", DEMON_HOME_ARTHURS_APARTMENT_FELICIAS_ROOM);
				}
				
			} else if (index == 0) {
				return new Response("Leave", "Leave the building and head back out into Demon Home.", DEMON_HOME_STREET_ARTHUR);
			}
			
			return null;
		}
	};

    public static final DialogueNode DEMON_HOME_ARTHURS_APARTMENT_ARTHURS_ROOM_END = new DialogueNode("Комната Артура", "-", true, true) {
		@Override
		public void applyPreParsingEffects() {
            getFelicia().setPlayerKnowsName(true);
		}
		@Override
		public int getSecondsPassed() {
			return 5*60;
		}
		@Override
		public String getContent() {
			return UtilText.parseFromXMLFile("places/dominion/arthursApartment/apartment", "DEMON_HOME_ARTHURS_APARTMENT_ARTHURS_ROOM_END");
		}
		@Override
		public Response getResponse(int responseTab, int index) {
			if (index == 1) {
				return new Response("Leave", "You've done all you can here. Head back outside to Demon Home.", DEMON_HOME_STREET_ARTHUR){
                        @Override
                        public void effects() {
                            getFelicia().equipInsideClothing();
                            getFelicia().setLocation(WorldType.FELICIA_APARTMENT, PlaceType.FELICIA_APARTMENT_LIVING_AREA, true);
                        }
                    };
			}
			return null;
		}
	};
    public static final DialogueNode DEMON_HOME_ARTHURS_APARTMENT_ARTHURS_ROOM = new DialogueNode("Комната Артура", "-", true) {
		@Override
		public void applyPreParsingEffects() {
            getFelicia().equipOutsideClothing();
            getFelicia().setLocation(Main.game.getPlayer(), false);
		}
		@Override
		public int getSecondsPassed() {
			return 5*60;
		}
		@Override
		public String getLabel() {
            return "Комната Артура";
		}
		@Override
		public String getContent() {
			return UtilText.parseFromXMLFile("places/dominion/arthursApartment/apartment", "DEMON_HOME_ARTHURS_APARTMENT_ARTHURS_ROOM");
		}
		@Override
		public Response getResponse(int responseTab, int index) {
			if(index==1) {
				return new Response("Question dog-girl", "Ask the dog-girl if she knows anything about Arthur's arrest.", DEMON_HOME_ARTHURS_APARTMENT_ARTHURS_ROOM_END);
			}
			return null;
		}
	};

	public static final DialogueNode DEMON_HOME_ARTHURS_APARTMENT_FELICIAS_ROOM = new DialogueNode("", "", true) {
		public int h;
        @Override
        public void applyPreParsingEffects() {
            h = Main.game.getHourOfDay();
            if(h >= 6 && h <= 14) {
                getFelicia().setLocation(Main.game.getPlayer(), false);
                getFelicia().setIntroducedToPlayer(true);
            }
        }
		@Override
		public int getSecondsPassed() {
			return 2*60;
		}
		@Override
		public String getLabel() {
			return "[felicia.NamePos] Room";
		}
		@Override
		public String getContent() {
			return UtilText.parseFromXMLFile("places/dominion/arthursApartment/apartment", "DEMON_HOME_ARTHURS_APARTMENT_FELICIAS_ROOM");
		}

		@Override
		public Response getResponse(int responseTab, int index) {
			if(index==1) {
				if(Main.game.getDialogueFlags().hasFlag(DialogueFlagValue.feliciaRejectedPlayer)) {
	                 return new Response("Leave", "Looks like Felicia doesn't want to talk to you. Head back outside to Demon Home.", DEMON_HOME_STREET_ARTHUR);
				}
				if(h < 6 || h > 14) {
					return new Response("Leave", "Looks like [felicia.name] isn't here. Head back outside to Demon Home.", DEMON_HOME_STREET_ARTHUR);
					
				} else if(h >= 6 && h <= 14) {
	                if (!Main.game.getDialogueFlags().hasFlag(DialogueFlagValue.feliciaToldAboutArthur)) {
                        return new Response("Вход", "Enter [felicia.namePos] home.", FeliciaApartment.ARTHUR_WHEREABOUTS) {
	                        @Override
	                        public void effects() {
	                            getFelicia().setLocation(WorldType.FELICIA_APARTMENT, PlaceType.FELICIA_APARTMENT_LIVING_AREA, false);
	                            Main.game.getPlayer().setLocation(WorldType.FELICIA_APARTMENT, PlaceType.FELICIA_APARTMENT_LIVING_AREA);
	                        }
	                    };
	                    
	                } else {
                        return new Response("Вход", "Enter [felicia.namePos] home.", FeliciaApartment.FELICIA_GREETINGS) {
	                        @Override
	                        public void effects() {
	                            getFelicia().setLocation(WorldType.FELICIA_APARTMENT, PlaceType.FELICIA_APARTMENT_LIVING_AREA, false);
	                            Main.game.getPlayer().setLocation(WorldType.FELICIA_APARTMENT, PlaceType.FELICIA_APARTMENT_LIVING_AREA);
	                        }
	                    }; 
	                }
	                
				}
			}
			return null;
		}
	};

	public static final DialogueNode DEMON_HOME_SEX_SHOP = new DialogueNode("", "", false) {
		@Override
		public void applyPreParsingEffects() {
            if(Main.game.getPlayer().getQuest(QuestLine.SIDE_DOLL_FACTORY)==Quest.DOLL_FACTORY_7A && !Main.game.getDialogueFlags().hasFlag("innoxia_doll_factory_ending_riot_witnessed")) {
            	Main.game.getNpc(Fiammetta.class).setLocation(Main.game.getPlayer());
            	Main.game.appendToTextEndStringBuilder(Main.game.getPlayer().incrementMoney(50_000));
            }
            if(Main.game.getPlayer().getQuest(QuestLine.SIDE_DOLL_FACTORY)==Quest.DOLL_FACTORY_7A
            		&& Main.game.getSecondsPassed() - Main.game.getDialogueFlags().getSavedLong("doll_quest_choice_time") < (7 * 24 * 60 * 60)) {
            	int daysToGo = 7 - (int) (((Main.game.getSecondsPassed() - Main.game.getDialogueFlags().getSavedLong("doll_quest_choice_time")) / (24 * 60 * 60)));
            	if(daysToGo<=1) {
                	UtilText.addSpecialParsingString("[style.italicsGood(Lovienne's Luxuries will reopen in a day or so.)]", true);
            	} else {
                	UtilText.addSpecialParsingString("[style.italicsMinorGood(Lovienne's Luxuries will reopen in about "+daysToGo+" days...)]", true);
            	}
            }
            if(Main.game.getPlayer().getQuest(QuestLine.SIDE_DOLL_FACTORY)==Quest.DOLL_FACTORY_7B
            		&& Main.game.getSecondsPassed() - Main.game.getDialogueFlags().getSavedLong("doll_quest_choice_time") < (24 * 60 * 60)) {
            	UtilText.addSpecialParsingString("[style.italicsGood(Lovienne's Luxuries will reopen in a day or so.)]", true);
            }
		}
		@Override
		public boolean isTravelDisabled() {
			if(Main.game.getPlayer().getQuest(QuestLine.SIDE_DOLL_FACTORY)==Quest.DOLL_FACTORY_7A) {
				if(Main.game.getSecondsPassed() - Main.game.getDialogueFlags().getSavedLong("doll_quest_choice_time") < (7 * 24 * 60 * 60)) {
					return !Main.game.getDialogueFlags().hasFlag("innoxia_doll_factory_ending_riot_witnessed");
				} else {
					return !Main.game.getDialogueFlags().hasFlag("innoxia_doll_factory_ending_reopen_scene_seen");
				}
			}
			return false;
		}
		@Override
		public int getSecondsPassed() {
			return DominionPlaces.TRAVEL_TIME_STREET;
		}
		@Override
		public String getContent() {
			if(Main.game.getPlayer().getQuest(QuestLine.SIDE_DOLL_FACTORY)==Quest.DOLL_FACTORY_7A) {
				if(Main.game.getSecondsPassed() - Main.game.getDialogueFlags().getSavedLong("doll_quest_choice_time") < (7 * 24 * 60 * 60)) {
					return UtilText.parseFromXMLFile("places/dominion/dominionPlaces", "DEMON_HOME_SEX_SHOP_RIOT");
				}
				if(!Main.game.getDialogueFlags().hasFlag("innoxia_doll_factory_ending_reopen_scene_seen")) {
					return UtilText.parseFromXMLFile("places/dominion/dominionPlaces", "DEMON_HOME_SEX_SHOP_RIOT_ENDED");
				}
			}
			
			if(Main.game.getPlayer().getQuest(QuestLine.SIDE_DOLL_FACTORY)==Quest.DOLL_FACTORY_7B
					&& (Main.game.getSecondsPassed() - Main.game.getDialogueFlags().getSavedLong("doll_quest_choice_time") < (24 * 60 * 60))) {
				return UtilText.parseFromXMLFile("places/dominion/dominionPlaces", "DEMON_HOME_SEX_SHOP_FIA_CLOSED");
			}
			
			return UtilText.parseFromXMLFile("places/dominion/dominionPlaces", "DEMON_HOME_SEX_SHOP");
		}
		@Override
		public Response getResponse(int responseTab, int index) {
			if (index == 1) {
				if(Main.game.getPlayer().getQuest(QuestLine.SIDE_DOLL_FACTORY)==Quest.DOLL_FACTORY_7A
						&& ((Main.game.getSecondsPassed() - Main.game.getDialogueFlags().getSavedLong("doll_quest_choice_time") < (7 * 24 * 60 * 60))// 7 days
							|| !Main.game.getDialogueFlags().hasFlag("innoxia_doll_factory_ending_riot_witnessed")
							|| !Main.game.getDialogueFlags().hasFlag("innoxia_doll_factory_ending_reopen_scene_seen"))) {
					if(!Main.game.getDialogueFlags().hasFlag("innoxia_doll_factory_ending_riot_witnessed")) {
						return new Response("Goodbye",
								"Say goodbye to Fiammetta.",
								DEMON_HOME_SEX_SHOP_RIOT_CONTINUE) {
							@Override
							public void effects() {
								Main.game.getNpc(Fiammetta.class).returnToHome();
								Main.game.getDialogueFlags().setFlag("innoxia_doll_factory_ending_riot_witnessed", true);
								// If enough time has passed to reopen, reset timer so one more day is needed
								if(Main.game.getSecondsPassed() - Main.game.getDialogueFlags().getSavedLong("doll_quest_choice_time") >= (7 * 24 * 60 * 60)) {
									Main.game.getDialogueFlags().setSavedLong("doll_quest_choice_time", Main.game.getSecondsPassed() - (6 * 24 * 60 * 60));
								}
							}
						};
						
					} else if((Main.game.getSecondsPassed() - Main.game.getDialogueFlags().getSavedLong("doll_quest_choice_time") >= (7 * 24 * 60 * 60))
							&& !Main.game.getDialogueFlags().hasFlag("innoxia_doll_factory_ending_reopen_scene_seen")) {
                        return new Response("Продолжить",
								"Continue on your way.",
								DEMON_HOME_SEX_SHOP) {
							@Override
							public void effects() {
								Main.game.getDialogueFlags().setFlag("innoxia_doll_factory_ending_reopen_scene_seen", true);
							}
						};
						
					} else {
						int daysRemaining = 7 - (int) ((Main.game.getSecondsPassed() - (Main.game.getDialogueFlags().getSavedLong("doll_quest_choice_time"))) / (24 * 60 * 60));

                        return new Response("Роскошь Ловиенны",
								"Due to the public unrest, Lovienne's Luxuries is currently [style.colourBad(closed)]."
									+ "<br/>[style.italicsMinorGood(It's likely to reopen within "+Util.intToString(daysRemaining)+" "+(daysRemaining<=1?"day":"days")+" or so...)]",
								null);
					}
					
				} else if(Main.game.getPlayer().getQuest(QuestLine.SIDE_DOLL_FACTORY)==Quest.DOLL_FACTORY_7B
						&& Main.game.getDialogueFlags().hasSavedLong("doll_quest_choice_time")
						&& (Main.game.getSecondsPassed() - Main.game.getDialogueFlags().getSavedLong("doll_quest_choice_time") < (24 * 60 * 60))) { // 1 day
                    return new Response("Роскошь Ловиенны",
							"Due to having to deal with the situation with Fiammetta's article, Lovienne's Luxuries is currently [style.colourBad(closed)]."
								+ "<br/>[style.italicsMinorGood(It's likely to reopen within a day or two...)]",
							null);
					
				} else if(!Main.game.isHourBetween(11, 23)) {
                    return new Response("Роскошь Ловиенны",
							"Lovienne's Luxuries is open between [units.time(11)]-[units.time(23)], and as such is currently [style.colourBad(closed)].",
							null);
					
				} else {
                    return new Response("Роскошь Ловиенны",
							"Push open the front door and enter Lovienne's Luxuries.",
							DialogueManager.getDialogueFromId("innoxia_places_dominion_sex_shop_generic_enter")) {
						@Override
						public void effects() {
							Main.game.getPlayer().setLocation(WorldType.getWorldTypeFromId("innoxia_dominion_sex_shop"), PlaceType.getPlaceTypeFromId("innoxia_dominion_sex_shop_exit"));
						}
					};
					
				}
				
			} else if(index==2) {
				if(Main.game.getPlayer().getQuest(QuestLine.SIDE_DOLL_FACTORY)==Quest.DOLL_FACTORY_7A
						&& ((Main.game.getSecondsPassed() - Main.game.getDialogueFlags().getSavedLong("doll_quest_choice_time") < (7 * 24 * 60 * 60))// 7 days
								|| !Main.game.getDialogueFlags().hasFlag("innoxia_doll_factory_ending_riot_witnessed")
								|| !Main.game.getDialogueFlags().hasFlag("innoxia_doll_factory_ending_reopen_scene_seen"))
						&& !Main.game.getDialogueFlags().hasFlag("innoxia_doll_factory_ending_riot_witnessed")) {
					return null; // If talking to Fia, don't show 'Fia' action
				}
				
				if(Main.game.getPlayer().getQuest(QuestLine.SIDE_DOLL_FACTORY)==Quest.DOLL_FACTORY_7A
						&& (Main.game.getSecondsPassed() - Main.game.getDialogueFlags().getSavedLong("doll_quest_choice_time") >= (7 * 24 * 60 * 60))
						&& !Main.game.getDialogueFlags().hasFlag("innoxia_doll_factory_ending_reopen_scene_seen")) {
					return null; // If this is the 'riot end' scene, don't show the 'Fia' action
				}
				
				if(Main.game.getPlayer().getQuest(QuestLine.SIDE_DOLL_FACTORY)==Quest.DOLL_FACTORY_2) {
					if(!Main.game.isHourBetween(1, 4)) {
						return new Response("Find Fia",
								"You need to wait until it's between [units.time(1)]-[units.time(4)] to break into Lovienne's Luxuries with Fia...",
								null);
						
					} else if(Main.game.getCurrentWeather()==Weather.MAGIC_STORM) {
						return new Response("Find Fia",
								"Although you're here at the right time, between [units.time(1)]-[units.time(4)], Fia can't show up due to the ongoing arcane storm...",
								null);
						
					} else {
						return new Response("Find Fia",
								"Look for Fia so that the two of you can break into Lovienne's Luxuries and search for evidence of where the kidnapped people are being held."
									+"<br/>[style.italicsCombat(Be prepared, for this will start a lengthy section of the side quest during which there may be difficult fights!)]",
								DialogueManager.getDialogueFromId("innoxia_places_dominion_sex_shop_factory_meet_fia_start"));
					}
					
				} else if(Main.game.getPlayer().hasQuestInLine(QuestLine.SIDE_DOLL_FACTORY, Quest.DOLL_FACTORY_7A)) {
					if(!Main.game.getPlayer().isQuestCompleted(QuestLine.SIDE_DOLL_FACTORY)) {
						return new Response("Fia",
								"Fia hasn't come out of hiding yet, and will only do so after Loveinne's Luxuries reopens..."
									+ (!Main.game.getDialogueFlags().hasFlag("innoxia_doll_factory_ending_reopen_scene_seen")
											?"<br/>[style.italics(Fia will return after you've entered the newly-reopened Loveinne's Luxuries.)]"
											:""),
								null);
					} else {
						if(!Main.game.isHourBetween(19, 00)) {
							return new Response("Fia",
									"Fia isn't around at this time..."
									+ "<br/><i>Return between the hours of [units.time(19)]-[units.time(00)] to find Fia.</i>",
									null);
							
						} else if(Main.game.getDialogueFlags().hasFlag("innoxia_fia_bar_seen")) {
							return new Response("Find Fia",
									"You've already met Fia this evening, and won't be able to find her again until tomorrow.",
									null);
								
						} else {
							return new Response("Fia",
									"Look for Fia in the nearby bars.",
									DialogueManager.getDialogueFromId("innoxia_places_dominion_demon_home_fia_start"));
						}
					}
					
				} else if(Main.game.getPlayer().hasQuestInLine(QuestLine.SIDE_DOLL_FACTORY, Quest.DOLL_FACTORY_7B) && Main.game.getPlayer().isQuestCompleted(QuestLine.SIDE_DOLL_FACTORY)) {
					if(!Main.game.isHourBetween(16, 22)) {
                        return new Response("Ангеликс",
								"Angelixx and her sons are out at 'work' at this time, so you'll have to come back later if you wanted to see them."
								+ "<br/><i>Return between the hours of [units.time(16)]-[units.time(22)] to meet Angelixx and her sons.</i>",
								null);
						
					} else if(Main.game.getDialogueFlags().hasFlag("innoxia_angelixx_apartment_visited")) {
                        return new Response("Ангеликс",
									"You've already paid a visit to Angelixx's apartment this evening, and can't do so again until tomorrow.",
									null);
							
					} else {
                        return new Response("Ангеликс",
								"Head up to Angelixx's apartment and pay her and her sons a visit.",
								DialogueManager.getDialogueFromId("innoxia_places_dominion_angelixx_apartment_generic_visit"));
					}
				}
			}
			return null;
		}
	};
	

	public static final DialogueNode DEMON_HOME_SEX_SHOP_RIOT_CONTINUE = new DialogueNode("", "", false, true) {
		@Override
		public int getSecondsPassed() {
			return DominionPlaces.TRAVEL_TIME_STREET;
		}
		@Override
		public String getContent() {
			return UtilText.parseFromXMLFile("places/dominion/dominionPlaces", "DEMON_HOME_SEX_SHOP_RIOT_CONTINUE");
		}
		@Override
		public Response getResponse(int responseTab, int index) {
			return DEMON_HOME_SEX_SHOP.getResponse(responseTab, index);
		}
	};
}
