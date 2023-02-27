    package com.example.chatcopier.utils;

    import net.minecraftforge.fml.common.Loader;

    import java.io.File;
    import java.io.FileWriter;
    import java.nio.charset.StandardCharsets;
    import java.nio.file.Files;
    import java.util.List;
    import java.util.Scanner;

    public class Config {
        private static final File FILE = new File(Loader.instance().getConfigDir(), "copyChatConfig.txt");
        public static String copyText = "";
        public static boolean isClicked = false;
        public static boolean isOpen = false;


        public static void jsonFile() {
            try {
                if (!FILE.exists()) {
                    FILE.createNewFile();

                    FileWriter writer = new FileWriter(FILE);
                    writer.write("CopyText: Copy\nAccessible: true");
                    writer.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        public static void setCopyText(String newText) {
            try {
                if (FILE.exists()) {
                    List<String> lines = Files.readAllLines(FILE.toPath(), StandardCharsets.UTF_8);
                    lines.set(0, "CopyText: " + newText);
                    Files.write(FILE.toPath(), lines, StandardCharsets.UTF_8);
                    copyText = newText;
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        public static String getCopyText() {
            try {
                if (!FILE.exists()) {
                    return "";
                }
                String firstLine = Files.lines(FILE.toPath()).findFirst().orElse("");
                if (firstLine.startsWith("CopyText: ")) {
                    return firstLine.substring("CopyText: ".length());
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return "";
        }
        public static void setAccessibility(String newText) {
            try {
                if (FILE.exists()) {
                    List<String> lines = Files.readAllLines(FILE.toPath(), StandardCharsets.UTF_8);
                    lines.set(1, "Accessible: " + newText);
                    Files.write(FILE.toPath(), lines, StandardCharsets.UTF_8);
                    isClicked = Boolean.parseBoolean(newText);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        public static boolean getAccessibility() {
            try {
                if (!FILE.exists()) {
                    return false;
                }
                List<String> lines = Files.readAllLines(FILE.toPath());
                if (lines.size() >= 2 && lines.get(1).startsWith("Accessible: ")) {
                    String value = lines.get(1).substring("Accessible: ".length()).trim();
                    return Boolean.parseBoolean(value);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return false;
        }
    }

