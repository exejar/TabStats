package club.maxstats.tabstats;

import club.maxstats.tabstats.playerapi.WorldLoader;
import club.maxstats.tabstats.util.References;
import net.minecraft.command.ICommand;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.util.Arrays;

@Mod(modid = References.MODID, name = References.MODNAME, clientSideOnly = true, version = References.VERSION, acceptedMinecraftVersions = "1.8.9")
public class TabStats {
    private static TabStats tabStats;
    private WorldLoader statWorld;

    /* Pre Initialization Event, Called before the initialization of Forge */
    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        tabStats = this;
        this.statWorld = new WorldLoader();
        this.registerListeners(statWorld);
    }

    /* Initialization Event, Called during the initialization of Forge */
    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {

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

    public static TabStats getTabStats() { return tabStats; }

    public WorldLoader getStatWorld() { return statWorld; }
}

//