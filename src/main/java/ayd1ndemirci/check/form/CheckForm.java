package ayd1ndemirci.check.form;

import ayd1ndemirci.check.Main;
import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.form.element.custom.ElementInput;
import cn.nukkit.form.element.custom.ElementLabel;
import cn.nukkit.form.response.CustomResponse;
import cn.nukkit.form.window.CustomForm;
import cn.nukkit.item.Item;
import cn.nukkit.item.ItemID;
import cn.nukkit.nbt.tag.CompoundTag;
import ayd1ndemirci.EconomyAPI.api.EconomyAPI;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CheckForm implements Listener {

    public static void openForm(Player player) {
        String money = EconomyAPI.formatMoney(EconomyAPI.getMoney(player.getName()));
        CustomForm form = new CustomForm("Çek Menüsü");
        form.addElement(new ElementLabel(String.format("§6Paran: §g%s", money)));
        form.addElement(new ElementInput("Miktar gir", "5000"));
        player.sendForm(form, Main.CHECK_FORM_ID);
    }

    @EventHandler
    public void onFormEvent(PlayerFormRespondedEvent event) {
        Player player = event.getPlayer();
        if (event.getFormID() == Main.CHECK_FORM_ID) {
            if (event.getWindow() instanceof CustomForm) {
                if (event.getResponse() != null) {
                    CustomResponse response = (CustomResponse) event.getResponse();
                    String input = response.getInputResponse(1);
                    int money = EconomyAPI.getMoney(player.getName());
                    try {
                        int inputAsInt = Integer.parseInt(input);
                        if (inputAsInt < 1) {
                            player.sendMessage("§8» §c0 ve daha küçük sayılar giremezsin.");
                            return;
                        }
                        if (inputAsInt > money) {
                            player.sendMessage("§8» §cParan yetersiz.");
                            return;
                        }

                        Item item = Item.get(ItemID.PAPER);
                        item.setCustomName(String.format("§2%s §r§adeğerinde çek\n§r§7Oluşturan: %s", EconomyAPI.formatMoney(inputAsInt), player.getName()));
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                        String formattedDate = dateFormat.format(new Date());
                        item.setLore(String.format("§r§6Oluşturulma Tarihi: §c%s", formattedDate));
                        CompoundTag tag = item.hasCompoundTag() ? item.getNamedTag() : new CompoundTag();
                        tag.putInt("price", inputAsInt);
                        tag.putString("player", player.getName());
                        item.setNamedTag(tag);
                        item.getNamedTag().putLong("dupeFix", (int) System.nanoTime());
                        if (player.getInventory().contains(item)) {
                            player.sendMessage("§8» §cEnvanterinde yer yok.");
                            return;
                        }
                        player.sendMessage(String.format("§8» §2§o%s §r§adeğerinde çek oluşturuldu.", EconomyAPI.formatMoney(inputAsInt)));
                        EconomyAPI.takeMoney(player.getName(), inputAsInt);
                        player.getInventory().addItem(item);
                    } catch (NumberFormatException e) {
                        player.sendMessage("§8» §cLütfen geçerli bir sayı girin.");
                    }
                }
            }
        }
    }
}
