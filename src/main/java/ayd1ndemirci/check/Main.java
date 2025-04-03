package ayd1ndemirci.check;

import ayd1ndemirci.check.command.CheckCommand;
import ayd1ndemirci.check.form.CheckForm;
import ayd1ndemirci.check.listener.PlayerListener;
import cn.nukkit.plugin.PluginBase;

public class Main extends PluginBase {

    public final static int CHECK_FORM_ID = 900000315;
    @Override
    public void onEnable() {
        getServer().getCommandMap().register("check", new CheckCommand());
        getServer().getPluginManager().registerEvents(new CheckForm(), this);
        getServer().getPluginManager().registerEvents(new PlayerListener(), this);
    }
}