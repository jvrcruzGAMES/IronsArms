package com.mumu17.ironsarms.utils;

import com.mumu17.ironsarms.network.RequestSyncChargedManaMessage;
import com.tacz.guns.api.item.IGun;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;

public class GunTags {

    public static void addMana(ItemStack stack, int mana) {
        setMana(stack, getMana(stack) + mana);
    }

    public static void setMana(ItemStack stack, int mana) {
        if (!isTargetItem(stack) || mana < 0) return;
        CustomData.update(DataComponents.CUSTOM_DATA, stack, tag -> tag.putInt(RequestSyncChargedManaMessage.MANA, mana));
    }

    public static int getMana(ItemStack stack) {
        return containsManaTag(stack) ? getCustomData(stack).copyTag().getInt(RequestSyncChargedManaMessage.MANA) : 0;
    }

    public static boolean containsManaTag(ItemStack stack) {
        return isTargetItem(stack) && getCustomData(stack).contains(RequestSyncChargedManaMessage.MANA);
    }

    public static boolean isTargetItem(ItemStack stack) {
        return stack != null && !stack.isEmpty() && stack.getItem() instanceof IGun;
    }

    public static boolean containsCustomTag(ItemStack stack, String key) {
        return stack != null && !stack.isEmpty() && getCustomData(stack).contains(key);
    }

    public static CompoundTag getCustomTagCopy(ItemStack stack) {
        return getCustomData(stack).copyTag();
    }

    public static void updateCustomTag(ItemStack stack, java.util.function.Consumer<CompoundTag> updater) {
        if (stack == null || stack.isEmpty()) return;
        CustomData.update(DataComponents.CUSTOM_DATA, stack, updater);
    }

    private static CustomData getCustomData(ItemStack stack) {
        return stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
    }
}
