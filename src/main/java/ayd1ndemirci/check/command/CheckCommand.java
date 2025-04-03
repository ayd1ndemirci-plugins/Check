package ayd1ndemirci.check.command;

import ayd1ndemirci.check.form.CheckForm;
import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;

public class CheckCommand extends Command {
    public CheckCommand() {
        super(
                "check",
                "Çek menüsünü açar"
        );
        setAliases(new String[]{"cek"});
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if (!(sender instanceof Player)) return false;
        CheckForm.openForm((Player) sender);
        return true;
    }
}