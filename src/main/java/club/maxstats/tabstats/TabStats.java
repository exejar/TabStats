package club.maxstats.tabstats;

import club.maxstats.tabstats.command.TabStatsCommand;
import club.maxstats.tabstats.config.TabStatsConfig;
import club.maxstats.tabstats.listener.ApiKeyListener;
import club.maxstats.tabstats.listener.GameOverlayListener;
import club.maxstats.tabstats.playerapi.WorldLoader;
import club.maxstats.tabstats.util.References;
import gg.essential.vigilance.Vigilance;
import net.minecraft.client.Minecraft;
import net.minecraft.command.ICommand;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.io.File;
import java.util.Arrays;

@Mod(modid = References.MODID, name = References.MODNAME, clientSideOnly = true, version = References.VERSION, acceptedMinecraftVersions = "1.8.9")
public class TabStats {
    
    /* TODO: (- not done, o done)
    o Add: Working background color picker.
    - Fix: Header and footer issues.
    o Add: Text shadow boolean.
    o Add: Adjust with bossbar boolean.
    */
    
    private static TabStats tabStats;
    private WorldLoader statWorld;
    public static final File modDir = new File(new File(Minecraft.getMinecraft().mcDataDir, "MaxStats"), References.MODNAME);
    private TabStatsConfig config;

    /* Pre Initialization Event, Called before the initialization of Forge */
    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        if (!modDir.exists()) modDir.mkdir();
        tabStats = this;
    }

    /* Initialization Event, Called during the initialization of Forge */
    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        registerCommands(new TabStatsCommand());
        Vigilance.initialize();
        this.config = new TabStatsConfig();
        this.config.preload();
        this.statWorld = new WorldLoader();
        this.registerListeners(this.statWorld, new GameOverlayListener(), new ApiKeyListener());
    }

    /* Post Initialization Event, Called after the initialization of Forge */
    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {

    }

    private void registerListeners(Object... listeners) {
        Arrays.stream(listeners).forEachOrdered(MinecraftForge.EVENT_BUS::register);
    }

    /* used to register forge commands */
    private void registerCommands(ICommand... commands) {
        Arrays.stream(commands).forEachOrdered(ClientCommandHandler.instance::registerCommand);
    }

    public TabStatsConfig getConfig() {
        return this.config;
    }

    public static TabStats getTabStats() { return tabStats; }

    public WorldLoader getStatWorld() { return this.statWorld; }
}