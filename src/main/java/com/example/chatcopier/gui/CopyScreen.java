package com.example.chatcopier.gui;

import com.example.chatcopier.ChatCopier;
import com.example.chatcopier.utils.Config;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraftforge.fml.client.config.GuiCheckBox;

import java.io.IOException;

public class CopyScreen extends GuiScreen {
    private static final int BUTTON_WIDTH = 100;
    private static final int BUTTON_HEIGHT = 20;

    private GuiTextField copyTextField;
    private GuiButton copyButton;
    private GuiCheckBox copyCheckBox;

    @Override
    public void initGui() {
        copyTextField = new GuiTextField(0, fontRendererObj, width / 2 - 50, height / 2 - 50, 100, 20);
        copyTextField.setMaxStringLength(50);
        copyTextField.setText(Config.getCopyText());
        copyTextField.setFocused(true);

        copyButton = new GuiButton(0, width / 2 - BUTTON_WIDTH / 2, height / 2, BUTTON_WIDTH, BUTTON_HEIGHT, "Update");
        buttonList.add(copyButton);

        copyCheckBox = new GuiCheckBox(1, width / 2 - BUTTON_WIDTH / 2, height / 2 -70, "Copy to Clipboard", Config.isClicked);
        buttonList.add(copyCheckBox);
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        if (button == copyButton) {
            String newText = copyTextField.getText();
            Config.setCopyText(newText);

            boolean copyToClipboard = copyCheckBox.isChecked();
            Config.setAccessibility(String.valueOf(copyToClipboard));

            mc.displayGuiScreen(null);
        }
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);
        copyTextField.textboxKeyTyped(typedChar, keyCode);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        copyTextField.drawTextBox();

        copyCheckBox.drawButton(mc, mouseX, mouseY);

        super.drawScreen(mouseX, mouseY, partialTicks);
    }
}
