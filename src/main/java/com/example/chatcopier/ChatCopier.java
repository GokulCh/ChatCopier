package com.example.chatcopier;

import com.example.chatcopier.commands.CopyCommand;
import com.example.chatcopier.gui.CopyScreen;
import com.example.chatcopier.utils.Config;
import net.minecraft.client.Minecraft;
import net.minecraft.event.ClickEvent;
import net.minecraft.event.HoverEvent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;

@Mod(modid = ChatCopier.MODID, version = ChatCopier.VERSION)
public class ChatCopier {
    public static final String MODID = "chatcopy";
    public static final String VERSION = "1.0";

    static ArrayList<String> colorCode = new ArrayList<String>() {
        {
            add("a");add("b");add("c");add("d");add("e");add("f");add("0");add("1");add("2");
            add("3");add("4");add("5");add("6");add("7");add("8");add("9");add("l");add("m");
            add("n");add("o");add("k");add("r");add("e");add("*");
        }
    };
    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        Config.jsonFile();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(this);
        ClientCommandHandler.instance.registerCommand(new CopyCommand());
        Config.copyText = Config.getCopyText();
        Config.isClicked = Config.getAccessibility();
    }

    @SubscribeEvent
    public void onChatReceived(ClientChatReceivedEvent event) {
        if (!Config.isClicked) return;
        String unformattedMessage = event.message.getUnformattedText();
        IChatComponent formattedMessage = event.message;

        if (unformattedMessage.contains("§")) {
            for (int c = 0; c < colorCode.size(); c++) {
                unformattedMessage = unformattedMessage.replace("\u00A7" + colorCode.get(c), "");
            }
        }

        ChatComponentText newMessage = new ChatComponentText("");
        newMessage.appendSibling(formattedMessage);

        ChatComponentText copyLabel = new ChatComponentText(" " + EnumChatFormatting.GRAY + "[" + EnumChatFormatting.GREEN + Config.copyText + EnumChatFormatting.GRAY + "]");
        copyLabel.getChatStyle().setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ChatComponentText("Copy Message")));
        copyLabel.getChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/chatcopier " + unformattedMessage));
        newMessage.appendSibling(copyLabel);
        event.message = newMessage;
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            if (Config.isOpen) {
                Minecraft.getMinecraft().displayGuiScreen(new CopyScreen());
                Config.isOpen = !Config.isOpen;
            }

        } else if (event.phase == TickEvent.Phase.END) {
            // Code to run on the end of each game tick
        }
    }
}
