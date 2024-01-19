package io.github.kgriff0n;

import net.fabricmc.loader.api.FabricLoader;

import java.io.*;
import java.nio.file.Path;
import java.util.Properties;

public class Config {

    public static String coinflipSelf;
    public static String coinflipOthers;
    public static String ecseeTitle;
    public static String feedSelf;
    public static String feedOthers;
    public static String flyEnabledSelf;
    public static String flyDisabledSelf;
    public static String flyEnabledOthers;
    public static String flyDisabledOthers;
    public static String healSelf;
    public static String healOthers;
    public static String invseeTitle;
    public static String trashTitle;

    public static Path configPath = FabricLoader.getInstance().getConfigDir();
    public static String properties = configPath + "/lambda.properties";

    public static void loadFile() {
        Properties lambdaConfigs = new Properties();
        try {
            lambdaConfigs.load(new FileInputStream(properties));
        } catch (IOException e) {
            Lambda.LOGGER.error("Can't load file.");
        }

        coinflipSelf = lambdaConfigs.getProperty("coinflip.self");
        coinflipOthers = lambdaConfigs.getProperty("coinflip.others");
        ecseeTitle = lambdaConfigs.getProperty("ecsee.title");
        feedSelf = lambdaConfigs.getProperty("feed.self");
        feedOthers = lambdaConfigs.getProperty("feed.others");
        flyEnabledSelf = lambdaConfigs.getProperty("fly.enabled.self");
        flyDisabledSelf = lambdaConfigs.getProperty("fly.disabled.self");
        flyEnabledOthers = lambdaConfigs.getProperty("fly.enabled.others");
        flyDisabledOthers = lambdaConfigs.getProperty("fly.disabled.others");
        healSelf = lambdaConfigs.getProperty("heal.self");
        healOthers = lambdaConfigs.getProperty("heal.others");
        invseeTitle = lambdaConfigs.getProperty("invsee.title");
        trashTitle = lambdaConfigs.getProperty("trash.title");
    }

    public static boolean exist() {
        return new File(properties).exists();
    }

    public static void writeDefaultConfig() {

        try (Writer writer = new FileWriter(properties)) {
            writer.write("# Lambda configuration file\n\n");
            writer.write("# Don't edit the following line\n");
            writer.write("version=v1.0.0\n\n");

            writer.write("# Coinflip display format\n");
            writer.write("coinflip.self=You got %s\n");
            writer.write("coinflip.others=%s flipped a coin and got %s\n\n");

            writer.write("# Ecsee title\n");
            writer.write("ecsee.title=%s's Ender Chest\n\n");

            writer.write("# Feed display format\n");
            writer.write("feed.self=You've been fed\n");
            writer.write("feed.others=%s has been fed\n\n");

            writer.write("# Fly display format\n");
            writer.write("fly.enabled.self=You can now fly\n");
            writer.write("fly.disabled.self=You can no longer fly\n");
            writer.write("fly.enabled.others=%s can now fly\n");
            writer.write("fly.disabled.others=%s can no longer fly\n\n");

            writer.write("# Heal display format\n");
            writer.write("heal.self=You've been healed\n");
            writer.write("heal.others=%s has been healed\n\n");

            writer.write("# Invsee title\n");
            writer.write("invsee.title=%s's Inventory\n\n");

            writer.write("# Trash title\n");
            writer.write("trash.title=Trash\n\n");

        } catch (IOException e) {
            Lambda.LOGGER.info("Can't write file.");
        }
    }

    public static void createConfigFile() {
        File database = new File(properties);
        try {
            database.createNewFile();
        } catch (IOException e) {
            Lambda.LOGGER.error("Can't create file.");
        }
    }

}
