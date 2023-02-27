package com.example.chatcopier.commands;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

import com.example.chatcopier.ChatCopier;
import com.example.chatcopier.gui.CopyScreen;
import com.example.chatcopier.utils.Config;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

public class CopyCommand extends CommandBase {

    @Override
    public String getCommandName() {
        return "chatcopier";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/chatcopier <message>";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        if (args.length == 0) {
            Config.isOpen = true;
            return;
        }
        String message = String.join(" ", args);

        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(new StringSelection(message), null);

        sender.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN + "Copied to clipboard!"));
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }
}
