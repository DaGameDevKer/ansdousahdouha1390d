package dev.revere.alley.util;

import io.netty.buffer.Unpooled;
import lombok.experimental.UtilityClass;
import dev.revere.alley.util.reflection.ReflectionUtils;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

@UtilityClass
public class BookUtil {
    /**
     * Opens a book for a player.
     *
     * @param player the player
     * @param book   the book
     */
    public void openBook(Player player, ItemStack book) {
        try {
            if (book.getType() != Material.WRITTEN_BOOK) {
                throw new IllegalArgumentException("ItemStack must be a written book");
            }

            int slot = player.getInventory().getHeldItemSlot();
            ItemStack oldItem = player.getInventory().getItemInHand();
            player.getInventory().setItem(slot, book);

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
            dataOutputStream.writeInt(0);

            PacketPlayOutCustomPayload packet = new PacketPlayOutCustomPayload("MC|BOpen", new PacketDataSerializer(Unpooled.wrappedBuffer(byteArrayOutputStream.toByteArray())));
            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
            player.getInventory().setItem(slot, oldItem);
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    /**
     * Creates a book ItemStack with the given title, author, and pages.
     *
     * @param title  the title of the book
     * @param author the author of the book
     * @param pages  the pages of the book
     * @return the book ItemStack
     */
    public ItemStack createBook(String title, String author, String[] pages) {
        ItemStack bookItem = new ItemStack(Material.WRITTEN_BOOK, 1);
        Class<?> craftItemStack = ReflectionUtils.getCraftBukkitClassFromName("inventory.CraftItemStack");
        net.minecraft.server.v1_8_R3.ItemStack itemStackCopy = CraftItemStack.asNMSCopy(bookItem);
        NBTTagCompound nbt = new NBTTagCompound();
        NBTTagList pagesList = new NBTTagList();
        nbt.setString("title", title);
        nbt.setString("author", author);
        for (String page : pages) {
            pagesList.add(new NBTTagString(page));
        }
        nbt.set("pages", pagesList);
        itemStackCopy.setTag(nbt);
        return CraftItemStack.asBukkitCopy(itemStackCopy);
    }
}