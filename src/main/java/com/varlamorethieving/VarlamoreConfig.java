package com.varlamorethieving;

import java.awt.Color;
import net.runelite.client.config.Alpha;
import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.ConfigSection;
import net.runelite.client.config.Range;

@ConfigGroup(VarlamoreConfig.GROUP)
public interface VarlamoreConfig extends Config
{
	String GROUP = "varlamoreTheiving";

	@ConfigSection(
		name = "Render style",
		description = "The render style of NPC highlighting",
		position = 0
	)
	String renderStyleSection = "renderStyleSection";

	@ConfigItem(
		position = 0,
		keyName = "hightlightHullOnMarked",
		name = "Highlight hull on marked",
		description = "Configures the hull of the Wealthy Citizen to pickpocket",
		section = renderStyleSection
	)
	default boolean highlightHullOnMarked()
	{
		return true;
	}

	@ConfigItem(
		position = 1,
		keyName = "highlightTileOnMarked",
		name = "Highlight tile on marked",
		description = "Configures the tile of the Wealthy Citizen to pickpocket",
		section = renderStyleSection
	)
	default boolean highlightTile()
	{
		return false;
	}

	@ConfigItem(
		position = 2,
		keyName = "highlightOutlineOnMarked",
		name = "Highlight outline on marked",
		description = "Configures the outline of the Wealthy Citizen to pickpocket",
		section = renderStyleSection
	)
	default boolean highlightOutline()
	{
		return false;
	}

	@Alpha
	@ConfigItem(
		position = 3,
		keyName = "highlightColor",
		name = "Highlight color",
		description = "Color of the Wealthy Citizen highlight border, menu, and text",
		section = renderStyleSection
	)
	default Color highlightColor()
	{
		return Color.CYAN;
	}

	@Alpha
	@ConfigItem(
		position = 4,
		keyName = "fillColor",
		name = "Fill color",
		description = "Color of the Wealthy Citizen highlight fill",
		section = renderStyleSection
	)
	default Color fillColor()
	{
		return new Color(0, 255, 255, 20);
	}

	@ConfigItem(
		position = 5,
		keyName = "borderWidth",
		name = "Border width",
		description = "Width of the highlighted Wealthy Citizen border",
		section = renderStyleSection
	)
	default double borderWidth()
	{
		return 2;
	}

	@ConfigItem(
		position = 6,
		keyName = "outlineFeather",
		name = "Outline feather",
		description = "Specify between 0-4 how much of the model outline should be faded",
		section = renderStyleSection
	)
	@Range(
		min = 0,
		max = 4
	)
	default int outlineFeather()
	{
		return 1;
	}
}
