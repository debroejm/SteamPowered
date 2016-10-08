package com.majorpotato.steampowered.recipe;


import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class RecipeComponentList {

    protected ArrayList<Listing> components = new ArrayList<Listing>();
    public List<Listing> getComponents() { return components; }

    /**
     * Constructor. Creates a listing of items to use to compare to an Inventory
     * @param components - Variable list of Object components.
     * @throws IllegalArgumentException if an argument is neither an ItemStack, nor a pairing of Item/Integer or String/Integer
     */
    public RecipeComponentList(Object ... components) throws IllegalArgumentException {
        for(int i = 0; i < components.length; i++) {
            if(components[i] instanceof String) {
                if(i == components.length-1 || !(components[i+1] instanceof Integer))
                    throw new IllegalArgumentException("OreDictionary Name Must be Followed by an Integer Count!");
                this.components.add(new DictionaryListing((String)components[i], (Integer)components[i+1]));
                i++;
            } else if(components[i] instanceof Item) {
                if(i == components.length-1 || !(components[i+1] instanceof Integer))
                    throw new IllegalArgumentException("Item Entry Must be Followed by an Integer Count!");
                this.components.add(new SpecificListing((Item)components[i], (Integer)components[i+1]));
                i++;
            } else if(components[i] instanceof ItemStack) {
                this.components.add(new SpecificListing((ItemStack)components[i]));
            } else {
                throw new IllegalArgumentException("Argument Must be an Instance of ItemStack, or a Pair Instance of Item/Integer or String/Integer");
            }
        }
    }

    /**
     * Checks if an Inventory contains all of th components specified in this listing.
     * @param inventory to check; must implement IInventory
     * @return True if the Inventory matches, False otherwise
     */
    public boolean inventoryMatches(IInventory inventory) { return inventoryMatches(inventory, 0, inventory.getSizeInventory()); }

    /**
     * Checks if a section of an Inventory contains all of the components specified in this listing.
     * @param inventory to check; must implement IInventory
     * @param startIndex to start searching at (Inclusive)
     * @param endIndex to stop searching at (Exclusive)
     * @return True if the Inventory matches, False otherwise or if the given indices are out of bounds
     */
    public boolean inventoryMatches(IInventory inventory, int startIndex, int endIndex) {
        if(startIndex > endIndex || startIndex < 0 || endIndex > inventory.getSizeInventory()) return false;
        ItemStack[] fauxInventory = new ItemStack[endIndex-startIndex];
        for(int i = 0; i < endIndex-startIndex; i++) {
            ItemStack stack = inventory.getStackInSlot(startIndex + i);
            fauxInventory[i] = stack == null ? null : stack.copy();
        }

        for(Listing entry : components) {
            int amountNeeded = entry.getCount();
            for(ItemStack stack : fauxInventory) {
                if(entry.matches(stack) && stack.stackSize > 0) {
                    if(amountNeeded > stack.stackSize) {
                        amountNeeded -= stack.stackSize;
                        stack.stackSize = 0;
                    } else {
                        stack.stackSize -= amountNeeded;
                        amountNeeded = 0;
                        break;
                    }
                }
            }
            if(amountNeeded > 0) return false;
        }

        return true;
    }

    /**
     * Checks if an Inventory has room for the components in this listing
     * @param inventory to check; must implement IInventory
     * @return True if there is room for all components, False otherwise
     */
    public boolean inventoryHasRoom(IInventory inventory) { return inventoryHasRoom(inventory, 0, inventory.getSizeInventory()); }

    /**
     * Checks if a section of Inventory has room for the components in this listing
     * @param inventory to check; must implement IInventory
     * @param startIndex to start searching at (Inclusive)
     * @param endIndex to stop searching at (Exclusive)
     * @return True if there is room for all components, False otherwise
     */
    // TODO: Modify to give priority to Nonnull entries
    public boolean inventoryHasRoom(IInventory inventory, int startIndex, int endIndex) {
        if(startIndex > endIndex || startIndex < 0 || endIndex > inventory.getSizeInventory()) return false;
        ItemStack[] fauxInventory = new ItemStack[endIndex-startIndex];
        for(int i = 0; i < endIndex-startIndex; i++) {
            ItemStack stack = inventory.getStackInSlot(startIndex + i);
            fauxInventory[i] = stack == null ? null : stack.copy();
        }

        for(Listing entry : components) {
            int amountNeeded = entry.getCount();
            for(int i = 0; i < fauxInventory.length; i++) {
                ItemStack stack = fauxInventory[i];
                if(stack == null) {
                    ItemStack out = entry.getNewItemStack();
                    if(amountNeeded > inventory.getInventoryStackLimit()) {
                        out.stackSize = inventory.getInventoryStackLimit();
                        fauxInventory[i] = out;
                        amountNeeded -= inventory.getInventoryStackLimit();
                    } else {
                        fauxInventory[i] = out;
                        amountNeeded = 0;
                        break;
                    }
                } else if(entry.matches(stack) && stack.stackSize < Math.min(stack.getMaxStackSize(), inventory.getInventoryStackLimit())) {
                    int limit = Math.min(stack.getMaxStackSize(), inventory.getInventoryStackLimit());
                    if(amountNeeded > limit-stack.stackSize) {
                        amountNeeded -= (limit-stack.stackSize);
                        stack.stackSize = limit;
                    } else {
                        stack.stackSize += amountNeeded;
                        amountNeeded = 0;
                        break;
                    }
                }
            }
            if(amountNeeded > 0) return false;
        }

        return true;
    }

    /**
     * Removes the components of this listing from an Inventory
     * @param inventory to remove from; must implement IInventory
     */
    public void removeFromInventory(IInventory inventory) { removeFromInventory(inventory, 0, inventory.getSizeInventory()); }

    /**
     * Removes the components of this listing from a section of Inventory.
     * @param inventory to remove from; must implement IInventory
     * @param startIndex to start at (Inclusive)
     * @param endIndex to end at (Exclusive)
     */
    public void removeFromInventory(IInventory inventory, int startIndex, int endIndex) {
        if(startIndex > endIndex || startIndex < 0 || endIndex > inventory.getSizeInventory()) return;

        for(Listing entry : components) {
            int amountNeeded = entry.getCount();
            for(int i = startIndex; i < endIndex; i++) {
                ItemStack stack = inventory.getStackInSlot(i);
                if(entry.matches(stack) && stack != null && stack.stackSize > 0) {
                    if(amountNeeded > stack.stackSize) {
                        amountNeeded -= stack.stackSize;
                        inventory.setInventorySlotContents(i, null);
                    } else {
                        stack.stackSize -= amountNeeded;
                        if(stack.stackSize < 1) inventory.setInventorySlotContents(i, null);
                        break;
                    }
                }
            }
        }
    }

    /**
     * Adds the components of this listing to an Inventory
     * @param inventory to add to; must implement IInventory
     */
    public void addToInventory(IInventory inventory) { addToInventory(inventory, 0, inventory.getSizeInventory()); }

    /**
     * Adds the components of this listing to a section of Inventory
     * @param inventory to add to; must implement IInventory
     * @param startIndex to start at (Inclusive)
     * @param endIndex to end at (Exclusive)
     */
    // TODO: Modify to give priority to Nonnull entries
    public void addToInventory(IInventory inventory, int startIndex, int endIndex) {
        if(startIndex > endIndex || startIndex < 0 || endIndex > inventory.getSizeInventory()) return;

        for(Listing entry : components) {
            int amountNeeded = entry.getCount();
            for(int i = startIndex; i < endIndex; i++) {
                ItemStack stack = inventory.getStackInSlot(i);
                if(stack == null) {
                    ItemStack out = entry.getNewItemStack();
                    if(amountNeeded > inventory.getInventoryStackLimit()) {
                        out.stackSize = inventory.getInventoryStackLimit();
                        inventory.setInventorySlotContents(i, out);
                        amountNeeded -= inventory.getInventoryStackLimit();
                    } else {
                        inventory.setInventorySlotContents(i, out);
                        amountNeeded = 0;
                        break;
                    }
                } else if(entry.matches(stack) && stack.stackSize < Math.min(stack.getMaxStackSize(), inventory.getInventoryStackLimit())) {
                    int limit = Math.min(stack.getMaxStackSize(), inventory.getInventoryStackLimit());
                    if(amountNeeded > limit-stack.stackSize) {
                        amountNeeded -= (limit-stack.stackSize);
                        stack.stackSize = limit;
                    } else {
                        stack.stackSize += amountNeeded;
                        amountNeeded = 0;
                        break;
                    }
                }
            }
        }
    }



    public static abstract class Listing {
        protected int count;
        public int getCount() { return count; }
        public Listing(int count) { this.count = count; }
        public abstract boolean matches(ItemStack stack);
        public abstract ItemStack getNewItemStack();
    }

    public static class SpecificListing extends Listing {
        protected ItemStack item;
        public ItemStack getItem() { return item; }
        public SpecificListing(@Nonnull Item item, int count) { this(new ItemStack(item, count)); }
        public SpecificListing(@Nonnull ItemStack stack) {
            super(stack.stackSize);
            this.item = stack;
        }
        @Override
        public boolean matches(ItemStack stack) { return item.isItemEqual(stack); }
        @Override
        public ItemStack getNewItemStack() { return item.copy(); }
        @Override
        public boolean equals(Object other) {
            return other instanceof SpecificListing && ((SpecificListing)other).getItem().isItemEqual(item);
        }
    }

    public static class DictionaryListing extends Listing {
        protected String name;
        public String getOreDictName() { return name; }
        public DictionaryListing(@Nonnull String name, int count) {
            super(count);
            this.name = name;
        }
        @Override
        public boolean matches(ItemStack stack) {
            List<ItemStack> entries = OreDictionary.getOres(name);
            for(ItemStack entry : entries)
                if(entry.isItemEqual(stack)) return true;
            return false;
        }
        @Override
        public ItemStack getNewItemStack() {
            return OreDictionary.getOres(name).get(0);
        }
        @Override
        public boolean equals(Object other) {
            return other instanceof DictionaryListing && ((DictionaryListing)other).getOreDictName().equals(name);
        }
    }
}
