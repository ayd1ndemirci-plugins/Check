package ayd1ndemirci.check.listener;

import ayd1ndemirci.EconomyAPI.api.EconomyAPI;
import cn.nukkit.Player;
import cn.nukkit.block.BlockID;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerInteractEntityEvent;
import cn.nukkit.event.player.PlayerInteractEvent;
import cn.nukkit.item.Item;
import cn.nukkit.item.ItemID;

public class PlayerListener implements Listener {

    @EventHandler
    public void onUseCheck(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Item item = event.getItem();
        if (item == null) return;
        if (item.getNamedTag() == null || !item.getNamedTag().contains("price")) return;
        if (event.getAction() != PlayerInteractEvent.Action.RIGHT_CLICK_AIR && event.getAction() != PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK) {
            return;
        }
        if (event.getBlock().getName() == "Item Frame" || event.getBlock().getName() == "Glow Item Frame") return;
        if (item.getCount() > 1) {
            //
            return;
        }
        int amount = item.getNamedTag().getInt("price");
        player.getInventory().removeItem(item);
        EconomyAPI.addMoney(player.getName(), amount);
        player.sendMessage(String.format("§8» §2§o%s §r§adeğerinde çek bozduruldu.", EconomyAPI.formatMoney(amount)));
    }
}
