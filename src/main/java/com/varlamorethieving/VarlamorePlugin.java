package com.varlamorethieving;

import com.google.inject.Provides;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import javax.inject.Inject;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.NPC;
import net.runelite.api.NpcID;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.events.GameStateChanged;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.game.NpcUtil;
import net.runelite.client.game.npcoverlay.HighlightedNpc;
import net.runelite.client.game.npcoverlay.NpcOverlayService;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.components.colorpicker.ColorPickerManager;
import net.runelite.client.ui.overlay.OverlayManager;

@Slf4j
@PluginDescriptor(
	name = "Varlamore Thieving Notifier",
	description = "Notifies and marks wealthy citizens in Varlamore for pickpocketing",
	tags = {"varlamore", "thieving", "tags", "highlight", "npcs", "overlay"}

)
// Add overlay for stats and NPC
// Notify upon text message in chatbox
// Restrict the plugin to the region in varlamore square
public class VarlamorePlugin extends Plugin
{
	private static final int MAX_ACTOR_VIEW_RANGE = 15;

	@Inject
	private Client client;

	@Inject
	private VarlamoreConfig config;

	@Inject
	private OverlayManager overlayManager;

	@Inject
	private WealthyCitizenOverlay wealthyCitizenOverlay;

	@Inject
	private ClientThread clientThread;

	@Inject
	private NpcOverlayService npcOverlayService;

	@Inject
	private NpcUtil npcUtil;

	@Inject
	private ConfigManager configManager;

	@Inject
	private ColorPickerManager colorPickerManager;

	// NPCs to highlight
	public static final Set<Integer> WEALTHY_CITIZEN_BASE_IDS = Set.of(
		NpcID.WEALTHY_CITIZEN,
		NpcID.WEALTHY_CITIZEN_13303,
		NpcID.WEALTHY_CITIZEN_13304,
		NpcID.WEALTHY_CITIZEN_13305
	);

	private WorldPoint lastPlayerLocation;

	@Getter(AccessLevel.PACKAGE)
	private final Map<NPC, HighlightedNpc> highlightedNpcs = new HashMap<>();

	private final Function<NPC, HighlightedNpc> isHighlighted = highlightedNpcs::get;


	@Provides
	VarlamoreConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(VarlamoreConfig.class);
	}

	@Override
	protected void startUp() throws Exception
	{
		npcOverlayService.registerHighlighter(isHighlighted);
		overlayManager.add(wealthyCitizenOverlay);
//		clientThread.invoke(() ->
//		{
//			rebuild();
//		});
	}

	@Override
	protected void shutDown() throws Exception
	{
		npcOverlayService.unregisterHighlighter(isHighlighted);
		overlayManager.remove(wealthyCitizenOverlay);
//		clientThread.invoke(() ->
//		{
//			//
//		});
	}

	@Subscribe
	public void onGameStateChanged(GameStateChanged state)
	{
		// Check for chat dialogue by child and by wealthy citizen (Look for wealthy citizen movement)
		if (state.getGameState() == GameState.LOGIN_SCREEN ||
			state.getGameState() == GameState.HOPPING)
		{
			lastPlayerLocation = null;
		}
	}

	@Subscribe
	public void onConfigChanged(ConfigChanged configChanged)
	{
		if (configChanged.getGroup().equals(VarlamoreConfig.GROUP))
		{
			return;
		}
		clientThread.invoke(this::rebuild);
	}

	void rebuild()
	{
		return;
	}
}
