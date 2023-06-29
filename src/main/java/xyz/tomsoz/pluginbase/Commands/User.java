package xyz.tomsoz.pluginbase.Commands;

import org.bukkit.*;
import org.bukkit.advancement.Advancement;
import org.bukkit.advancement.AdvancementProgress;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.block.*;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.sign.Side;
import org.bukkit.conversations.Conversation;
import org.bukkit.conversations.ConversationAbandonedEvent;
import org.bukkit.entity.*;
import org.bukkit.entity.memory.MemoryKey;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.*;
import org.bukkit.map.MapView;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.profile.PlayerProfile;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.net.InetSocketAddress;
import java.util.*;

public class User implements IUser {
    Player p;

    public User(Player p) {
        this.p = p;
    }

    @Override
    public boolean isOnline() {
        return p.isOnline();
    }

    @NotNull
    @Override
    public String getName() {
        return p.getName();
    }

    @NotNull
    @Override
    public PlayerInventory getInventory() {
        return p.getInventory();
    }

    @NotNull
    @Override
    public Inventory getEnderChest() {
        return p.getEnderChest();
    }

    @NotNull
    @Override
    public MainHand getMainHand() {
        return p.getMainHand();
    }

    @Override
    public boolean setWindowProperty(@NotNull InventoryView.Property property, int i) {
        return p.setWindowProperty(property, i);
    }

    @Override
    public int getEnchantmentSeed() {
        return p.getEnchantmentSeed();
    }

    @Override
    public void setEnchantmentSeed(int i) {
        p.setEnchantmentSeed(i);
    }

    @NotNull
    @Override
    public InventoryView getOpenInventory() {
        return p.getOpenInventory();
    }

    @Nullable
    @Override
    public InventoryView openInventory(@NotNull Inventory inventory) {
        return p.openInventory(inventory);
    }

    @Nullable
    @Override
    public InventoryView openWorkbench(@Nullable Location location, boolean b) {
        return p.openWorkbench(location, b);
    }

    @Nullable
    @Override
    public InventoryView openEnchanting(@Nullable Location location, boolean b) {
        return p.openEnchanting(location, b);
    }

    @Override
    public void openInventory(@NotNull InventoryView inventoryView) {
        p.openInventory(inventoryView);
    }

    @Nullable
    @Override
    public InventoryView openMerchant(@NotNull Villager villager, boolean b) {
        return p.openMerchant(villager, b);
    }

    @Nullable
    @Override
    public InventoryView openMerchant(@NotNull Merchant merchant, boolean b) {
        return p.openMerchant(merchant, b);
    }

    @Override
    public void closeInventory() {
        p.closeInventory();
    }

    /**
     * @deprecated
     */
    @NotNull
    @Override
    public ItemStack getItemInHand() {
        return p.getItemInHand();
    }

    /**
     * @param itemStack
     * @deprecated
     */
    @Override
    public void setItemInHand(@Nullable ItemStack itemStack) {
        p.setItemInHand(itemStack);
    }

    @NotNull
    @Override
    public ItemStack getItemOnCursor() {
        return p.getItemOnCursor();
    }

    @Override
    public void setItemOnCursor(@Nullable ItemStack itemStack) {
        p.setItemOnCursor(itemStack);
    }

    @Override
    public boolean hasCooldown(@NotNull Material material) {
        return p.hasCooldown(material);
    }

    @Override
    public int getCooldown(@NotNull Material material) {
        return p.getCooldown(material);
    }

    @Override
    public void setCooldown(@NotNull Material material, int i) {
        p.setCooldown(material, i);
    }

    @Override
    public int getSleepTicks() {
        return p.getSleepTicks();
    }

    @Override
    public boolean sleep(@NotNull Location location, boolean b) {
        return p.sleep(location, b);
    }

    @Override
    public void wakeup(boolean b) {
        p.wakeup(b);
    }

    @NotNull
    @Override
    public Location getBedLocation() {
        return p.getBedLocation();
    }

    @NotNull
    @Override
    public GameMode getGameMode() {
        return p.getGameMode();
    }

    @Override
    public void setGameMode(@NotNull GameMode gameMode) {
        p.setGameMode(gameMode);
    }

    @Override
    public boolean isBlocking() {
        return p.isBlocking();
    }

    @Override
    public boolean isHandRaised() {
        return p.isHandRaised();
    }

    @Nullable
    @Override
    public ItemStack getItemInUse() {
        return p.getItemInUse();
    }

    @Override
    public int getExpToLevel() {
        return p.getExpToLevel();
    }

    @Override
    public float getAttackCooldown() {
        return p.getAttackCooldown();
    }

    @Override
    public boolean discoverRecipe(@NotNull NamespacedKey namespacedKey) {
        return p.discoverRecipe(namespacedKey);
    }

    @Override
    public int discoverRecipes(@NotNull Collection<NamespacedKey> collection) {
        return p.discoverRecipes(collection);
    }

    @Override
    public boolean undiscoverRecipe(@NotNull NamespacedKey namespacedKey) {
        return p.undiscoverRecipe(namespacedKey);
    }

    @Override
    public int undiscoverRecipes(@NotNull Collection<NamespacedKey> collection) {
        return p.undiscoverRecipes(collection);
    }

    @Override
    public boolean hasDiscoveredRecipe(@NotNull NamespacedKey namespacedKey) {
        return p.hasDiscoveredRecipe(namespacedKey);
    }

    @NotNull
    @Override
    public Set<NamespacedKey> getDiscoveredRecipes() {
        return p.getDiscoveredRecipes();
    }

    /**
     * @deprecated
     */
    @Nullable
    @Override
    public Entity getShoulderEntityLeft() {
        return p.getShoulderEntityLeft();
    }

    /**
     * @param entity
     * @deprecated
     */
    @Override
    public void setShoulderEntityLeft(@Nullable Entity entity) {
        p.setShoulderEntityLeft(entity);
    }

    /**
     * @deprecated
     */
    @Nullable
    @Override
    public Entity getShoulderEntityRight() {
        return p.getShoulderEntityRight();
    }

    /**
     * @param entity
     * @deprecated
     */
    @Override
    public void setShoulderEntityRight(@Nullable Entity entity) {
        p.setShoulderEntityRight(entity);
    }

    @Override
    public boolean dropItem(boolean b) {
        return p.dropItem(b);
    }

    @Override
    public float getExhaustion() {
        return p.getExhaustion();
    }

    @Override
    public void setExhaustion(float v) {
        p.setExhaustion(v);
    }

    @Override
    public float getSaturation() {
        return p.getSaturation();
    }

    @Override
    public void setSaturation(float v) {
        p.setSaturation(v);
    }

    @Override
    public int getFoodLevel() {
        return p.getFoodLevel();
    }

    @Override
    public void setFoodLevel(int i) {
        p.setFoodLevel(i);
    }

    @Override
    public int getSaturatedRegenRate() {
        return p.getSaturatedRegenRate();
    }

    @Override
    public void setSaturatedRegenRate(int i) {
        p.setSaturatedRegenRate(i);
    }

    @Override
    public int getUnsaturatedRegenRate() {
        return p.getUnsaturatedRegenRate();
    }

    @Override
    public void setUnsaturatedRegenRate(int i) {
        p.setUnsaturatedRegenRate(i);
    }

    @Override
    public int getStarvationRate() {
        return p.getStarvationRate();
    }

    @Override
    public void setStarvationRate(int i) {
        p.setStarvationRate(i);
    }

    @Nullable
    @Override
    public Location getLastDeathLocation() {
        return p.getLastDeathLocation();
    }

    @Override
    public void setLastDeathLocation(@Nullable Location location) {
        p.setLastDeathLocation(location);
    }

    @Nullable
    @Override
    public Firework fireworkBoost(@NotNull ItemStack itemStack) {
        return p.fireworkBoost(itemStack);
    }

    @NotNull
    @Override
    public PlayerProfile getPlayerProfile() {
        return p.getPlayerProfile();
    }

    @Override
    public boolean isBanned() {
        return p.isBanned();
    }

    @Override
    public boolean isWhitelisted() {
        return p.isWhitelisted();
    }

    @Override
    public void setWhitelisted(boolean b) {
        p.setWhitelisted(b);
    }

    @Nullable
    @Override
    public Player getPlayer() {
        return p.getPlayer();
    }

    @Override
    public long getFirstPlayed() {
        return p.getFirstPlayed();
    }

    @Override
    public long getLastPlayed() {
        return p.getLastPlayed();
    }

    @Override
    public boolean hasPlayedBefore() {
        return p.hasPlayedBefore();
    }

    @NotNull
    @Override
    public String getDisplayName() {
        return p.getDisplayName();
    }

    @Override
    public void setDisplayName(@Nullable String s) {
        p.setDisplayName(s);
    }

    @NotNull
    @Override
    public String getPlayerListName() {
        return p.getPlayerListName();
    }

    @Override
    public void setPlayerListName(@Nullable String s) {
        p.setPlayerListName(s);
    }

    @Nullable
    @Override
    public String getPlayerListHeader() {
        return p.getPlayerListHeader();
    }

    @Override
    public void setPlayerListHeader(@Nullable String s) {
        p.setPlayerListHeader(s);
    }

    @Nullable
    @Override
    public String getPlayerListFooter() {
        return p.getPlayerListFooter();
    }

    @Override
    public void setPlayerListFooter(@Nullable String s) {
        p.setPlayerListFooter(s);
    }

    @Override
    public void setPlayerListHeaderFooter(@Nullable String s, @Nullable String s1) {
        p.setPlayerListHeaderFooter(s, s1);
    }

    @NotNull
    @Override
    public Location getCompassTarget() {
        return p.getCompassTarget();
    }

    @Override
    public void setCompassTarget(@NotNull Location location) {
        p.setCompassTarget(location);
    }

    @Nullable
    @Override
    public InetSocketAddress getAddress() {
        return p.getAddress();
    }

    @Override
    public boolean isConversing() {
        return p.isConversing();
    }

    @Override
    public void acceptConversationInput(@NotNull String s) {
        p.acceptConversationInput(s);
    }

    @Override
    public boolean beginConversation(@NotNull Conversation conversation) {
        return p.beginConversation(conversation);
    }

    @Override
    public void abandonConversation(@NotNull Conversation conversation) {
        p.abandonConversation(conversation);
    }

    @Override
    public void abandonConversation(@NotNull Conversation conversation, @NotNull ConversationAbandonedEvent conversationAbandonedEvent) {
        p.abandonConversation(conversation, conversationAbandonedEvent);
    }

    @Override
    public void sendRawMessage(@NotNull String s) {
        p.sendRawMessage(s);
    }

    @Override
    public void sendRawMessage(@Nullable UUID uuid, @NotNull String s) {
        p.sendRawMessage(uuid, s);
    }

    @Override
    public void kickPlayer(@Nullable String s) {
        p.kickPlayer(s);
    }

    @Override
    public void chat(@NotNull String s) {
        p.chat(s);
    }

    @Override
    public boolean performCommand(@NotNull String s) {
        return p.performCommand(s);
    }

    @NotNull
    @Override
    public Location getLocation() {
        return p.getLocation();
    }

    @Nullable
    @Override
    public Location getLocation(@Nullable Location location) {
        return p.getLocation(location);
    }

    @NotNull
    @Override
    public Vector getVelocity() {
        return p.getVelocity();
    }

    @Override
    public void setVelocity(@NotNull Vector vector) {
        p.setVelocity(vector);
    }

    @Override
    public double getHeight() {
        return p.getHeight();
    }

    @Override
    public double getWidth() {
        return p.getWidth();
    }

    @NotNull
    @Override
    public BoundingBox getBoundingBox() {
        return p.getBoundingBox();
    }

    /**
     * @deprecated
     */
    @Override
    public boolean isOnGround() {
        return p.isOnGround();
    }

    @Override
    public boolean isInWater() {
        return p.isInWater();
    }

    @NotNull
    @Override
    public World getWorld() {
        return p.getWorld();
    }

    @Override
    public void setRotation(float v, float v1) {
        p.setRotation(v, v1);
    }

    @Override
    public boolean teleport(@NotNull Location location) {
        return p.teleport(location);
    }

    @Override
    public boolean teleport(@NotNull Location location, @NotNull PlayerTeleportEvent.TeleportCause teleportCause) {
        return p.teleport(location, teleportCause);
    }

    @Override
    public boolean teleport(@NotNull Entity entity) {
        return p.teleport(entity);
    }

    @Override
    public boolean teleport(@NotNull Entity entity, @NotNull PlayerTeleportEvent.TeleportCause teleportCause) {
        return p.teleport(entity, teleportCause);
    }

    @NotNull
    @Override
    public List<Entity> getNearbyEntities(double v, double v1, double v2) {
        return p.getNearbyEntities(v, v1, v2);
    }

    @Override
    public int getEntityId() {
        return p.getEntityId();
    }

    @Override
    public int getFireTicks() {
        return p.getFireTicks();
    }

    @Override
    public void setFireTicks(int i) {
        p.setFireTicks(i);
    }

    @Override
    public int getMaxFireTicks() {
        return p.getMaxFireTicks();
    }

    @Override
    public boolean isVisualFire() {
        return p.isVisualFire();
    }

    @Override
    public void setVisualFire(boolean b) {
        p.setVisualFire(b);
    }

    @Override
    public int getFreezeTicks() {
        return p.getFreezeTicks();
    }

    @Override
    public void setFreezeTicks(int i) {
        p.setFreezeTicks(i);
    }

    @Override
    public int getMaxFreezeTicks() {
        return p.getMaxFreezeTicks();
    }

    @Override
    public boolean isFrozen() {
        return p.isFrozen();
    }

    @Override
    public void remove() {
        p.remove();
    }

    @Override
    public boolean isDead() {
        return p.isDead();
    }

    @Override
    public boolean isValid() {
        return p.isValid();
    }

    @Override
    public void sendMessage(@NotNull String s) {
        p.sendMessage(s);
    }

    @Override
    public void sendMessage(@NotNull String... strings) {
        p.sendMessage(strings);
    }

    @Override
    public void sendMessage(@Nullable UUID uuid, @NotNull String s) {
        p.sendMessage(uuid, s);
    }

    @Override
    public void sendMessage(@Nullable UUID uuid, @NotNull String... strings) {
        p.sendMessage(uuid, strings);
    }

    @NotNull
    @Override
    public Server getServer() {
        return p.getServer();
    }

    @Override
    public boolean isPersistent() {
        return p.isPersistent();
    }

    @Override
    public void setPersistent(boolean b) {
        p.setPersistent(b);
    }

    /**
     * @deprecated
     */
    @Nullable
    @Override
    public Entity getPassenger() {
        return p.getPassenger();
    }

    /**
     * @param entity
     * @deprecated
     */
    @Override
    public boolean setPassenger(@NotNull Entity entity) {
        return p.setPassenger(entity);
    }

    @NotNull
    @Override
    public List<Entity> getPassengers() {
        return p.getPassengers();
    }

    @Override
    public boolean addPassenger(@NotNull Entity entity) {
        return p.addPassenger(entity);
    }

    @Override
    public boolean removePassenger(@NotNull Entity entity) {
        return p.removePassenger(entity);
    }

    @Override
    public boolean isEmpty() {
        return p.isEmpty();
    }

    @Override
    public boolean eject() {
        return p.eject();
    }

    @Override
    public float getFallDistance() {
        return p.getFallDistance();
    }

    @Override
    public void setFallDistance(float v) {
        p.setFallDistance(v);
    }

    @Nullable
    @Override
    public EntityDamageEvent getLastDamageCause() {
        return p.getLastDamageCause();
    }

    @Override
    public void setLastDamageCause(@Nullable EntityDamageEvent entityDamageEvent) {
        p.setLastDamageCause(entityDamageEvent);
    }

    @NotNull
    @Override
    public UUID getUniqueId() {
        return p.getUniqueId();
    }

    @Override
    public int getTicksLived() {
        return p.getTicksLived();
    }

    @Override
    public void setTicksLived(int i) {
        p.setTicksLived(i);
    }

    @Override
    public void playEffect(@NotNull EntityEffect entityEffect) {
        p.playEffect(entityEffect);
    }

    @NotNull
    @Override
    public EntityType getType() {
        return p.getType();
    }

    @NotNull
    @Override
    public Sound getSwimSound() {
        return p.getSwimSound();
    }

    @NotNull
    @Override
    public Sound getSwimSplashSound() {
        return p.getSwimSplashSound();
    }

    @NotNull
    @Override
    public Sound getSwimHighSpeedSplashSound() {
        return p.getSwimHighSpeedSplashSound();
    }

    @Override
    public boolean isInsideVehicle() {
        return p.isInsideVehicle();
    }

    @Override
    public boolean leaveVehicle() {
        return p.leaveVehicle();
    }

    @Nullable
    @Override
    public Entity getVehicle() {
        return p.getVehicle();
    }

    @Override
    public boolean isCustomNameVisible() {
        return p.isCustomNameVisible();
    }

    @Override
    public void setCustomNameVisible(boolean b) {
        p.setCustomNameVisible(b);
    }

    @Override
    public boolean isVisibleByDefault() {
        return p.isVisibleByDefault();
    }

    @Override
    public void setVisibleByDefault(boolean b) {
        p.setVisibleByDefault(b);
    }

    @Override
    public boolean isGlowing() {
        return p.isGlowing();
    }

    @Override
    public void setGlowing(boolean b) {
        p.setGlowing(b);
    }

    @Override
    public boolean isInvulnerable() {
        return p.isInvulnerable();
    }

    @Override
    public void setInvulnerable(boolean b) {
        p.setInvulnerable(b);
    }

    @Override
    public boolean isSilent() {
        return p.isSilent();
    }

    @Override
    public void setSilent(boolean b) {
        p.setSilent(b);
    }

    @Override
    public boolean hasGravity() {
        return p.hasGravity();
    }

    @Override
    public void setGravity(boolean b) {
        p.setGravity(b);
    }

    @Override
    public int getPortalCooldown() {
        return p.getPortalCooldown();
    }

    @Override
    public void setPortalCooldown(int i) {
        p.setPortalCooldown(i);
    }

    @NotNull
    @Override
    public Set<String> getScoreboardTags() {
        return p.getScoreboardTags();
    }

    @Override
    public boolean addScoreboardTag(@NotNull String s) {
        return p.addScoreboardTag(s);
    }

    @Override
    public boolean removeScoreboardTag(@NotNull String s) {
        return p.removeScoreboardTag(s);
    }

    @NotNull
    @Override
    public PistonMoveReaction getPistonMoveReaction() {
        return p.getPistonMoveReaction();
    }

    @NotNull
    @Override
    public BlockFace getFacing() {
        return p.getFacing();
    }

    @NotNull
    @Override
    public Pose getPose() {
        return p.getPose();
    }

    @NotNull
    @Override
    public SpawnCategory getSpawnCategory() {
        return p.getSpawnCategory();
    }

    @Override
    public boolean isSneaking() {
        return p.isSneaking();
    }

    @Override
    public void setSneaking(boolean b) {
        p.setSneaking(b);
    }

    @Override
    public boolean isSprinting() {
        return p.isSprinting();
    }

    @Override
    public void setSprinting(boolean b) {
        p.setSprinting(b);
    }

    @Override
    public void saveData() {
        p.saveData();
    }

    @Override
    public void loadData() {
        p.loadData();
    }

    @Override
    public boolean isSleepingIgnored() {
        return p.isSleepingIgnored();
    }

    @Override
    public void setSleepingIgnored(boolean b) {
        p.setSleepingIgnored(b);
    }

    @Nullable
    @Override
    public Location getBedSpawnLocation() {
        return p.getBedSpawnLocation();
    }

    @Override
    public void setBedSpawnLocation(@Nullable Location location) {
        p.setBedSpawnLocation(location);
    }

    @Override
    public void incrementStatistic(@NotNull Statistic statistic) throws IllegalArgumentException {
        p.incrementStatistic(statistic);
    }

    @Override
    public void decrementStatistic(@NotNull Statistic statistic) throws IllegalArgumentException {
        p.decrementStatistic(statistic);
    }

    @Override
    public void incrementStatistic(@NotNull Statistic statistic, int i) throws IllegalArgumentException {
        p.incrementStatistic(statistic, i);
    }

    @Override
    public void decrementStatistic(@NotNull Statistic statistic, int i) throws IllegalArgumentException {
        p.decrementStatistic(statistic, i);
    }

    @Override
    public void setStatistic(@NotNull Statistic statistic, int i) throws IllegalArgumentException {
        p.setStatistic(statistic, i);
    }

    @Override
    public int getStatistic(@NotNull Statistic statistic) throws IllegalArgumentException {
        return p.getStatistic(statistic);
    }

    @Override
    public void incrementStatistic(@NotNull Statistic statistic, @NotNull Material material) throws IllegalArgumentException {
        p.incrementStatistic(statistic, material);
    }

    @Override
    public void decrementStatistic(@NotNull Statistic statistic, @NotNull Material material) throws IllegalArgumentException {
        p.decrementStatistic(statistic, material);
    }

    @Override
    public int getStatistic(@NotNull Statistic statistic, @NotNull Material material) throws IllegalArgumentException {
        return p.getStatistic(statistic, material);
    }

    @Override
    public void incrementStatistic(@NotNull Statistic statistic, @NotNull Material material, int i) throws IllegalArgumentException {
        p.incrementStatistic(statistic, material, i);
    }

    @Override
    public void decrementStatistic(@NotNull Statistic statistic, @NotNull Material material, int i) throws IllegalArgumentException {
        p.decrementStatistic(statistic, material, i);
    }

    @Override
    public void setStatistic(@NotNull Statistic statistic, @NotNull Material material, int i) throws IllegalArgumentException {
        p.setStatistic(statistic, material, i);
    }

    @Override
    public void incrementStatistic(@NotNull Statistic statistic, @NotNull EntityType entityType) throws IllegalArgumentException {
        p.incrementStatistic(statistic, entityType);
    }

    @Override
    public void decrementStatistic(@NotNull Statistic statistic, @NotNull EntityType entityType) throws IllegalArgumentException {
        p.decrementStatistic(statistic, entityType);
    }

    @Override
    public int getStatistic(@NotNull Statistic statistic, @NotNull EntityType entityType) throws IllegalArgumentException {
        return p.getStatistic(statistic, entityType);
    }

    @Override
    public void incrementStatistic(@NotNull Statistic statistic, @NotNull EntityType entityType, int i) throws IllegalArgumentException {
        p.incrementStatistic(statistic, entityType, i);
    }

    @Override
    public void decrementStatistic(@NotNull Statistic statistic, @NotNull EntityType entityType, int i) {
        p.decrementStatistic(statistic, entityType, i);
    }

    @Override
    public void setStatistic(@NotNull Statistic statistic, @NotNull EntityType entityType, int i) {
        p.setStatistic(statistic, entityType, i);
    }

    @Override
    public void setBedSpawnLocation(@Nullable Location location, boolean b) {
        p.setBedSpawnLocation(location, b);
    }

    /**
     * @param location
     * @param b
     * @param b1
     * @deprecated
     */
    @Override
    public void playNote(@NotNull Location location, byte b, byte b1) {
        p.playNote(location, b, b1);
    }

    @Override
    public void playNote(@NotNull Location location, @NotNull Instrument instrument, @NotNull Note note) {
        p.playNote(location, instrument, note);
    }

    @Override
    public void playSound(@NotNull Location location, @NotNull Sound sound, float v, float v1) {
        p.playSound(location, sound, v, v1);
    }

    @Override
    public void playSound(@NotNull Location location, @NotNull String s, float v, float v1) {
        p.playSound(location, s, v, v1);
    }

    @Override
    public void playSound(@NotNull Location location, @NotNull Sound sound, @NotNull SoundCategory soundCategory, float v, float v1) {
        p.playSound(location, sound, soundCategory, v, v1);
    }

    @Override
    public void playSound(@NotNull Location location, @NotNull String s, @NotNull SoundCategory soundCategory, float v, float v1) {
        p.playSound(location, s, soundCategory, v, v1);
    }

    @Override
    public void playSound(@NotNull Entity entity, @NotNull Sound sound, float v, float v1) {
        p.playSound(entity, sound, v, v1);
    }

    @Override
    public void playSound(@NotNull Entity entity, @NotNull String s, float v, float v1) {
        p.playSound(entity, s, v, v1);
    }

    @Override
    public void playSound(@NotNull Entity entity, @NotNull Sound sound, @NotNull SoundCategory soundCategory, float v, float v1) {
        p.playSound(entity, sound, soundCategory, v, v1);
    }

    @Override
    public void playSound(@NotNull Entity entity, @NotNull String s, @NotNull SoundCategory soundCategory, float v, float v1) {
        p.playSound(entity, s, soundCategory, v, v1);
    }

    @Override
    public void stopSound(@NotNull Sound sound) {
        p.stopSound(sound);
    }

    @Override
    public void stopSound(@NotNull String s) {
        p.stopSound(s);
    }

    @Override
    public void stopSound(@NotNull Sound sound, @Nullable SoundCategory soundCategory) {
        p.stopSound(sound, soundCategory);
    }

    @Override
    public void stopSound(@NotNull String s, @Nullable SoundCategory soundCategory) {
        p.stopSound(s, soundCategory);
    }

    @Override
    public void stopSound(@NotNull SoundCategory soundCategory) {
        p.stopSound(soundCategory);
    }

    @Override
    public void stopAllSounds() {
        p.stopAllSounds();
    }

    /**
     * @param location
     * @param effect
     * @param i
     * @deprecated
     */
    @Override
    public void playEffect(@NotNull Location location, @NotNull Effect effect, int i) {
        p.playEffect(location, effect, i);
    }

    @Override
    public <T> void playEffect(@NotNull Location location, @NotNull Effect effect, @Nullable T t) {
        p.playEffect(location, effect, t);
    }

    @Override
    public boolean breakBlock(@NotNull Block block) {
        return p.breakBlock(block);
    }

    /**
     * @param location
     * @param material
     * @param b
     * @deprecated
     */
    @Override
    public void sendBlockChange(@NotNull Location location, @NotNull Material material, byte b) {
        p.sendBlockChange(location, material, b);
    }

    @Override
    public void sendBlockChange(@NotNull Location location, @NotNull BlockData blockData) {
        p.sendBlockChange(location, blockData);
    }

    @Override
    public void sendBlockChanges(@NotNull Collection<BlockState> collection, boolean b) {
        p.sendBlockChanges(collection, b);
    }

    @Override
    public void sendBlockDamage(@NotNull Location location, float v) {
        p.sendBlockDamage(location, v);
    }

    @Override
    public void sendBlockDamage(@NotNull Location location, float v, @NotNull Entity entity) {
        p.sendBlockDamage(location, v, entity);
    }

    @Override
    public void sendBlockDamage(@NotNull Location location, float v, int i) {
        p.sendBlockDamage(location, v, i);
    }

    @Override
    public void sendEquipmentChange(@NotNull LivingEntity livingEntity, @NotNull EquipmentSlot equipmentSlot, @Nullable ItemStack itemStack) {
        p.sendEquipmentChange(livingEntity, equipmentSlot, itemStack);
    }

    @Override
    public void sendEquipmentChange(@NotNull LivingEntity livingEntity, @NotNull Map<EquipmentSlot, ItemStack> map) {
        p.sendEquipmentChange(livingEntity, map);
    }

    @Override
    public void sendSignChange(@NotNull Location location, @Nullable String[] strings) throws IllegalArgumentException {
        p.sendSignChange(location, strings);
    }

    @Override
    public void sendSignChange(@NotNull Location location, @Nullable String[] strings, @NotNull DyeColor dyeColor) throws IllegalArgumentException {
        p.sendSignChange(location, strings, dyeColor);
    }

    @Override
    public void sendSignChange(@NotNull Location location, @Nullable String[] strings, @NotNull DyeColor dyeColor, boolean b) throws IllegalArgumentException {
        p.sendSignChange(location, strings, dyeColor, b);
    }

    @Override
    public void sendMap(@NotNull MapView mapView) {
        p.sendMap(mapView);
    }

    @Override
    public void sendHurtAnimation(float v) {
        p.sendHurtAnimation(v);
    }

    @Override
    public void addCustomChatCompletions(@NotNull Collection<String> collection) {
        p.addCustomChatCompletions(collection);
    }

    @Override
    public void removeCustomChatCompletions(@NotNull Collection<String> collection) {
        p.removeCustomChatCompletions(collection);
    }

    @Override
    public void setCustomChatCompletions(@NotNull Collection<String> collection) {
        p.setCustomChatCompletions(collection);
    }

    @Override
    public void updateInventory() {
        p.updateInventory();
    }

    @Nullable
    @Override
    public GameMode getPreviousGameMode() {
        return p.getPreviousGameMode();
    }

    @Override
    public void setPlayerTime(long l, boolean b) {
        p.setPlayerTime(l, b);
    }

    @Override
    public long getPlayerTime() {
        return p.getPlayerTime();
    }

    @Override
    public long getPlayerTimeOffset() {
        return p.getPlayerTimeOffset();
    }

    @Override
    public boolean isPlayerTimeRelative() {
        return p.isPlayerTimeRelative();
    }

    @Override
    public void resetPlayerTime() {
        p.resetPlayerTime();
    }

    @Nullable
    @Override
    public WeatherType getPlayerWeather() {
        return p.getPlayerWeather();
    }

    @Override
    public void setPlayerWeather(@NotNull WeatherType weatherType) {
        p.setPlayerWeather(weatherType);
    }

    @Override
    public void resetPlayerWeather() {
        p.resetPlayerWeather();
    }

    @Override
    public int getExpCooldown() {
        return p.getExpCooldown();
    }

    @Override
    public void setExpCooldown(int i) {
        p.setExpCooldown(i);
    }

    @Override
    public void giveExp(int i) {
        p.giveExp(i);
    }

    @Override
    public void giveExpLevels(int i) {
        p.giveExpLevels(i);
    }

    @Override
    public float getExp() {
        return p.getExp();
    }

    @Override
    public void setExp(float v) {
        p.setExp(v);
    }

    @Override
    public int getLevel() {
        return p.getLevel();
    }

    @Override
    public void setLevel(int i) {
        p.setLevel(i);
    }

    @Override
    public int getTotalExperience() {
        return p.getTotalExperience();
    }

    @Override
    public void setTotalExperience(int i) {
        p.setTotalExperience(i);
    }

    @Override
    public void sendExperienceChange(float v) {
        p.sendExperienceChange(v);
    }

    @Override
    public void sendExperienceChange(float v, int i) {
        p.sendExperienceChange(v, i);
    }

    @Override
    public boolean getAllowFlight() {
        return p.getAllowFlight();
    }

    @Override
    public void setAllowFlight(boolean b) {
        p.setAllowFlight(b);
    }

    /**
     * @param player
     * @deprecated
     */
    @Override
    public void hidePlayer(@NotNull Player player) {
        p.hidePlayer(player);
    }

    @Override
    public void hidePlayer(@NotNull Plugin plugin, @NotNull Player player) {
        p.hidePlayer(plugin, player);
    }

    /**
     * @param player
     * @deprecated
     */
    @Override
    public void showPlayer(@NotNull Player player) {
        p.showPlayer(player);
    }

    @Override
    public void showPlayer(@NotNull Plugin plugin, @NotNull Player player) {
        p.showPlayer(plugin, player);
    }

    @Override
    public boolean canSee(@NotNull Player player) {
        return p.canSee(player);
    }

    @Override
    public void hideEntity(@NotNull Plugin plugin, @NotNull Entity entity) {
        p.hideEntity(plugin, entity);
    }

    @Override
    public void showEntity(@NotNull Plugin plugin, @NotNull Entity entity) {
        p.showEntity(plugin, entity);
    }

    @Override
    public boolean canSee(@NotNull Entity entity) {
        return p.canSee(entity);
    }

    @Override
    public boolean isFlying() {
        return p.isFlying();
    }

    @Override
    public void setFlying(boolean b) {
        p.setFlying(b);
    }

    @Override
    public float getFlySpeed() {
        return p.getFlySpeed();
    }

    @Override
    public void setFlySpeed(float v) throws IllegalArgumentException {
        p.setFlySpeed(v);
    }

    @Override
    public float getWalkSpeed() {
        return p.getWalkSpeed();
    }

    @Override
    public void setWalkSpeed(float v) throws IllegalArgumentException {
        p.setWalkSpeed(v);
    }

    /**
     * @param s
     * @deprecated
     */
    @Override
    public void setTexturePack(@NotNull String s) {
        p.setTexturePack(s);
    }

    @Override
    public void setResourcePack(@NotNull String s) {
        p.setResourcePack(s);
    }

    @Override
    public void setResourcePack(@NotNull String s, @Nullable byte[] bytes) {
        p.setResourcePack(s, bytes);
    }

    @Override
    public void setResourcePack(@NotNull String s, @Nullable byte[] bytes, @Nullable String s1) {
        p.setResourcePack(s, bytes, s1);
    }

    @Override
    public void setResourcePack(@NotNull String s, @Nullable byte[] bytes, boolean b) {
        p.setResourcePack(s, bytes, b);
    }

    @Override
    public void setResourcePack(@NotNull String s, @Nullable byte[] bytes, @Nullable String s1, boolean b) {
        p.setResourcePack(s, bytes, s1, b);
    }

    @NotNull
    @Override
    public Scoreboard getScoreboard() {
        return p.getScoreboard();
    }

    @Override
    public void setScoreboard(@NotNull Scoreboard scoreboard) throws IllegalArgumentException, IllegalStateException {
        p.setScoreboard(scoreboard);
    }

    @Nullable
    @Override
    public WorldBorder getWorldBorder() {
        return p.getWorldBorder();
    }

    @Override
    public void setWorldBorder(@Nullable WorldBorder worldBorder) {
        p.setWorldBorder(worldBorder);
    }

    @Override
    public boolean isHealthScaled() {
        return p.isHealthScaled();
    }

    @Override
    public void setHealthScaled(boolean b) {
        p.setHealthScaled(b);
    }

    @Override
    public double getHealthScale() {
        return p.getHealthScale();
    }

    @Override
    public void setHealthScale(double v) throws IllegalArgumentException {
        p.setHealthScale(v);
    }

    @Nullable
    @Override
    public Entity getSpectatorTarget() {
        return p.getSpectatorTarget();
    }

    @Override
    public void setSpectatorTarget(@Nullable Entity entity) {
        p.setSpectatorTarget(entity);
    }

    /**
     * @param s
     * @param s1
     * @deprecated
     */
    @Override
    public void sendTitle(@Nullable String s, @Nullable String s1) {
        p.sendTitle(s, s1);
    }

    @Override
    public void sendTitle(@Nullable String s, @Nullable String s1, int i, int i1, int i2) {
        p.sendTitle(s, s1, i, i1, i2);
    }

    @Override
    public void resetTitle() {
        p.resetTitle();
    }

    @Override
    public void spawnParticle(@NotNull Particle particle, @NotNull Location location, int i) {
        p.spawnParticle(particle, location, i);
    }

    @Override
    public void spawnParticle(@NotNull Particle particle, double v, double v1, double v2, int i) {
        p.spawnParticle(particle, v, v1, v2, i);
    }

    @Override
    public <T> void spawnParticle(@NotNull Particle particle, @NotNull Location location, int i, @Nullable T t) {
        p.spawnParticle(particle, location, i, t);
    }

    @Override
    public <T> void spawnParticle(@NotNull Particle particle, double v, double v1, double v2, int i, @Nullable T t) {
        p.spawnParticle(particle, v, v1, v2, i, t);
    }

    @Override
    public void spawnParticle(@NotNull Particle particle, @NotNull Location location, int i, double v, double v1, double v2) {
        p.spawnParticle(particle, location, i, v, v1, v2);
    }

    @Override
    public void spawnParticle(@NotNull Particle particle, double v, double v1, double v2, int i, double v3, double v4, double v5) {
        p.spawnParticle(particle, v, v1, v2, i, v3, v4, v5);
    }

    @Override
    public <T> void spawnParticle(@NotNull Particle particle, @NotNull Location location, int i, double v, double v1, double v2, @Nullable T t) {
        p.spawnParticle(particle, location, i, v, v1, v2, t);
    }

    @Override
    public <T> void spawnParticle(@NotNull Particle particle, double v, double v1, double v2, int i, double v3, double v4, double v5, @Nullable T t) {
        p.spawnParticle(particle, v, v1, v2, i, v3, v4, v5, t);
    }

    @Override
    public void spawnParticle(@NotNull Particle particle, @NotNull Location location, int i, double v, double v1, double v2, double v3) {
        p.spawnParticle(particle, location, i, v, v1, v2, v3);
    }

    @Override
    public void spawnParticle(@NotNull Particle particle, double v, double v1, double v2, int i, double v3, double v4, double v5, double v6) {
        p.spawnParticle(particle, v, v1, v2, i, v3, v4, v5, v6);
    }

    @Override
    public <T> void spawnParticle(@NotNull Particle particle, @NotNull Location location, int i, double v, double v1, double v2, double v3, @Nullable T t) {
        p.spawnParticle(particle, location, i, v, v1, v2, v3, t);
    }

    @Override
    public <T> void spawnParticle(@NotNull Particle particle, double v, double v1, double v2, int i, double v3, double v4, double v5, double v6, @Nullable T t) {
        p.spawnParticle(particle, v, v1, v2, i, v3, v4, v5, v6, t);
    }

    @NotNull
    @Override
    public AdvancementProgress getAdvancementProgress(@NotNull Advancement advancement) {
        return p.getAdvancementProgress(advancement);
    }

    @Override
    public int getClientViewDistance() {
        return p.getClientViewDistance();
    }

    @Override
    public int getPing() {
        return p.getPing();
    }

    @NotNull
    @Override
    public String getLocale() {
        return p.getLocale();
    }

    @Override
    public void updateCommands() {
        p.updateCommands();
    }

    @Override
    public void openBook(@NotNull ItemStack itemStack) {
        p.openBook(itemStack);
    }

    @Override
    public void openSign(@NotNull Sign sign) {
        p.openSign(sign);
    }

    @Override
    public void openSign(@NotNull Sign sign, @NotNull Side side) {
        p.openSign(sign, side);
    }

    @Override
    public void showDemoScreen() {
        p.showDemoScreen();
    }

    @Override
    public boolean isAllowingServerListings() {
        return p.isAllowingServerListings();
    }

    @NotNull
    @Override
    public Spigot spigot() {
        return p.spigot();
    }

    @NotNull
    @Override
    public Map<String, Object> serialize() {
        return p.serialize();
    }

    @Override
    public double getEyeHeight() {
        return p.getEyeHeight();
    }

    @Override
    public double getEyeHeight(boolean b) {
        return p.getEyeHeight(b);
    }

    @NotNull
    @Override
    public Location getEyeLocation() {
        return p.getEyeLocation();
    }

    @NotNull
    @Override
    public List<Block> getLineOfSight(@Nullable Set<Material> set, int i) {
        return p.getLineOfSight(set, i);
    }

    @NotNull
    @Override
    public Block getTargetBlock(@Nullable Set<Material> set, int i) {
        return p.getTargetBlock(set, i);
    }

    @NotNull
    @Override
    public List<Block> getLastTwoTargetBlocks(@Nullable Set<Material> set, int i) {
        return p.getLastTwoTargetBlocks(set, i);
    }

    @Nullable
    @Override
    public Block getTargetBlockExact(int i) {
        return p.getTargetBlockExact(i);
    }

    @Nullable
    @Override
    public Block getTargetBlockExact(int i, @NotNull FluidCollisionMode fluidCollisionMode) {
        return p.getTargetBlockExact(i, fluidCollisionMode);
    }

    @Nullable
    @Override
    public RayTraceResult rayTraceBlocks(double v) {
        return p.rayTraceBlocks(v);
    }

    @Nullable
    @Override
    public RayTraceResult rayTraceBlocks(double v, @NotNull FluidCollisionMode fluidCollisionMode) {
        return p.rayTraceBlocks(v, fluidCollisionMode);
    }

    @Override
    public int getRemainingAir() {
        return p.getRemainingAir();
    }

    @Override
    public void setRemainingAir(int i) {
        p.setRemainingAir(i);
    }

    @Override
    public int getMaximumAir() {
        return p.getMaximumAir();
    }

    @Override
    public void setMaximumAir(int i) {
        p.setMaximumAir(i);
    }

    @Override
    public int getArrowCooldown() {
        return p.getArrowCooldown();
    }

    @Override
    public void setArrowCooldown(int i) {
        p.setArrowCooldown(i);
    }

    @Override
    public int getArrowsInBody() {
        return p.getArrowsInBody();
    }

    @Override
    public void setArrowsInBody(int i) {
        p.setArrowsInBody(i);
    }

    @Override
    public int getMaximumNoDamageTicks() {
        return p.getMaximumNoDamageTicks();
    }

    @Override
    public void setMaximumNoDamageTicks(int i) {
        p.setMaximumNoDamageTicks(i);
    }

    @Override
    public double getLastDamage() {
        return p.getLastDamage();
    }

    @Override
    public void setLastDamage(double v) {
        p.setLastDamage(v);
    }

    @Override
    public int getNoDamageTicks() {
        return p.getNoDamageTicks();
    }

    @Override
    public void setNoDamageTicks(int i) {
        p.setNoDamageTicks(i);
    }

    @Nullable
    @Override
    public Player getKiller() {
        return p.getKiller();
    }

    @Override
    public boolean addPotionEffect(@NotNull PotionEffect potionEffect) {
        return p.addPotionEffect(potionEffect);
    }

    /**
     * @param potionEffect
     * @param b
     * @deprecated
     */
    @Override
    public boolean addPotionEffect(@NotNull PotionEffect potionEffect, boolean b) {
        return p.addPotionEffect(potionEffect, b);
    }

    @Override
    public boolean addPotionEffects(@NotNull Collection<PotionEffect> collection) {
        return p.addPotionEffects(collection);
    }

    @Override
    public boolean hasPotionEffect(@NotNull PotionEffectType potionEffectType) {
        return p.hasPotionEffect(potionEffectType);
    }

    @Nullable
    @Override
    public PotionEffect getPotionEffect(@NotNull PotionEffectType potionEffectType) {
        return p.getPotionEffect(potionEffectType);
    }

    @Override
    public void removePotionEffect(@NotNull PotionEffectType potionEffectType) {
        p.removePotionEffect(potionEffectType);
    }

    @NotNull
    @Override
    public Collection<PotionEffect> getActivePotionEffects() {
        return p.getActivePotionEffects();
    }

    @Override
    public boolean hasLineOfSight(@NotNull Entity entity) {
        return p.hasLineOfSight(entity);
    }

    @Override
    public boolean getRemoveWhenFarAway() {
        return p.getRemoveWhenFarAway();
    }

    @Override
    public void setRemoveWhenFarAway(boolean b) {
        p.setRemoveWhenFarAway(b);
    }

    @Nullable
    @Override
    public EntityEquipment getEquipment() {
        return p.getEquipment();
    }

    @Override
    public boolean getCanPickupItems() {
        return p.getCanPickupItems();
    }

    @Override
    public void setCanPickupItems(boolean b) {
        p.setCanPickupItems(b);
    }

    @Override
    public boolean isLeashed() {
        return p.isLeashed();
    }

    @NotNull
    @Override
    public Entity getLeashHolder() throws IllegalStateException {
        return p.getLeashHolder();
    }

    @Override
    public boolean setLeashHolder(@Nullable Entity entity) {
        return p.setLeashHolder(entity);
    }

    @Override
    public boolean isGliding() {
        return p.isGliding();
    }

    @Override
    public void setGliding(boolean b) {
        p.setGliding(b);
    }

    @Override
    public boolean isSwimming() {
        return p.isSwimming();
    }

    @Override
    public void setSwimming(boolean b) {
        p.setSwimming(b);
    }

    @Override
    public boolean isRiptiding() {
        return p.isRiptiding();
    }

    @Override
    public boolean isSleeping() {
        return p.isSleeping();
    }

    @Override
    public boolean isClimbing() {
        return p.isClimbing();
    }

    @Override
    public void setAI(boolean b) {
        p.setAI(b);
    }

    @Override
    public boolean hasAI() {
        return p.hasAI();
    }

    @Override
    public void attack(@NotNull Entity entity) {
        p.attack(entity);
    }

    @Override
    public void swingMainHand() {
        p.swingMainHand();
    }

    @Override
    public void swingOffHand() {
        p.swingOffHand();
    }

    @Override
    public boolean isCollidable() {
        return p.isCollidable();
    }

    @Override
    public void setCollidable(boolean b) {
        p.setCollidable(b);
    }

    @NotNull
    @Override
    public Set<UUID> getCollidableExemptions() {
        return p.getCollidableExemptions();
    }

    @Nullable
    @Override
    public <T> T getMemory(@NotNull MemoryKey<T> memoryKey) {
        return p.getMemory(memoryKey);
    }

    @Override
    public <T> void setMemory(@NotNull MemoryKey<T> memoryKey, @Nullable T t) {
        p.setMemory(memoryKey, t);
    }

    @Nullable
    @Override
    public Sound getHurtSound() {
        return p.getHurtSound();
    }

    @Nullable
    @Override
    public Sound getDeathSound() {
        return p.getDeathSound();
    }

    @NotNull
    @Override
    public Sound getFallDamageSound(int i) {
        return p.getFallDamageSound(i);
    }

    @NotNull
    @Override
    public Sound getFallDamageSoundSmall() {
        return p.getFallDamageSoundSmall();
    }

    @NotNull
    @Override
    public Sound getFallDamageSoundBig() {
        return p.getFallDamageSoundBig();
    }

    @NotNull
    @Override
    public Sound getDrinkingSound(@NotNull ItemStack itemStack) {
        return p.getDrinkingSound(itemStack);
    }

    @NotNull
    @Override
    public Sound getEatingSound(@NotNull ItemStack itemStack) {
        return p.getEatingSound(itemStack);
    }

    @Override
    public boolean canBreatheUnderwater() {
        return p.canBreatheUnderwater();
    }

    @NotNull
    @Override
    public EntityCategory getCategory() {
        return p.getCategory();
    }

    @Override
    public boolean isInvisible() {
        return p.isInvisible();
    }

    @Override
    public void setInvisible(boolean b) {
        p.setInvisible(b);
    }

    @Nullable
    @Override
    public AttributeInstance getAttribute(@NotNull Attribute attribute) {
        return p.getAttribute(attribute);
    }

    @Override
    public void damage(double v) {
        p.damage(v);
    }

    @Override
    public void damage(double v, @Nullable Entity entity) {
        p.damage(v, entity);
    }

    @Override
    public double getHealth() {
        return p.getHealth();
    }

    @Override
    public void setHealth(double v) {
        p.setHealth(v);
    }

    @Override
    public double getAbsorptionAmount() {
        return p.getAbsorptionAmount();
    }

    @Override
    public void setAbsorptionAmount(double v) {
        p.setAbsorptionAmount(v);
    }

    /**
     * @deprecated
     */
    @Override
    public double getMaxHealth() {
        return p.getMaxHealth();
    }

    /**
     * @param v
     * @deprecated
     */
    @Override
    public void setMaxHealth(double v) {
        p.setMaxHealth(v);
    }

    /**
     * @deprecated
     */
    @Override
    public void resetMaxHealth() {
        p.resetMaxHealth();
    }

    @Nullable
    @Override
    public String getCustomName() {
        return p.getCustomName();
    }

    @Override
    public void setCustomName(@Nullable String s) {
        p.setCustomName(s);
    }

    @Override
    public void setMetadata(@NotNull String s, @NotNull MetadataValue metadataValue) {
        p.setMetadata(s, metadataValue);
    }

    @NotNull
    @Override
    public List<MetadataValue> getMetadata(@NotNull String s) {
        return p.getMetadata(s);
    }

    @Override
    public boolean hasMetadata(@NotNull String s) {
        return p.hasMetadata(s);
    }

    @Override
    public void removeMetadata(@NotNull String s, @NotNull Plugin plugin) {
        p.removeMetadata(s, plugin);
    }

    @Override
    public boolean isPlayer() {
        return true;
    }

    @Override
    public User asUser() {
        return new User(p);
    }

    @Override
    public boolean isPermissionSet(@NotNull String s) {
        return p.isPermissionSet(s);
    }

    @Override
    public boolean isPermissionSet(@NotNull Permission permission) {
        return p.isPermissionSet(permission);
    }

    @Override
    public boolean hasPermission(@NotNull String s) {
        return p.hasPermission(s);
    }

    @Override
    public boolean hasPermission(@NotNull Permission permission) {
        return p.hasPermission(permission);
    }

    @NotNull
    @Override
    public PermissionAttachment addAttachment(@NotNull Plugin plugin, @NotNull String s, boolean b) {
        return p.addAttachment(plugin, s, b);
    }

    @NotNull
    @Override
    public PermissionAttachment addAttachment(@NotNull Plugin plugin) {
        return p.addAttachment(plugin);
    }

    @Nullable
    @Override
    public PermissionAttachment addAttachment(@NotNull Plugin plugin, @NotNull String s, boolean b, int i) {
        return p.addAttachment(plugin, s, b, i);
    }

    @Nullable
    @Override
    public PermissionAttachment addAttachment(@NotNull Plugin plugin, int i) {
        return p.addAttachment(plugin, i);
    }

    @Override
    public void removeAttachment(@NotNull PermissionAttachment permissionAttachment) {
        p.removeAttachment(permissionAttachment);
    }

    @Override
    public void recalculatePermissions() {
        p.recalculatePermissions();
    }

    @NotNull
    @Override
    public Set<PermissionAttachmentInfo> getEffectivePermissions() {
        return p.getEffectivePermissions();
    }

    @Override
    public boolean isOp() {
        return p.isOp();
    }

    @Override
    public void setOp(boolean b) {
        p.setOp(b);
    }

    @NotNull
    @Override
    public PersistentDataContainer getPersistentDataContainer() {
        return p.getPersistentDataContainer();
    }

    @Override
    public void sendPluginMessage(@NotNull Plugin plugin, @NotNull String s, @NotNull byte[] bytes) {
        p.sendPluginMessage(plugin, s, bytes);
    }

    @NotNull
    @Override
    public Set<String> getListeningPluginChannels() {
        return p.getListeningPluginChannels();
    }

    @NotNull
    @Override
    public <T extends Projectile> T launchProjectile(@NotNull Class<? extends T> aClass) {
        return p.launchProjectile(aClass);
    }

    @NotNull
    @Override
    public <T extends Projectile> T launchProjectile(@NotNull Class<? extends T> aClass, @Nullable Vector vector) {
        return p.launchProjectile(aClass, vector);
    }
}
