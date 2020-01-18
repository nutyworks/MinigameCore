package me.nuty.minigamecore.minigame;

import me.nuty.minigamecore.MinigameCore;
import me.nuty.minigamecore.arena.IArena;
import me.nuty.minigamecore.scoreboard.MinigameScoreboardManager;
import me.nuty.minigamecore.util.Constant;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scoreboard.DisplaySlot;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class AbstractMinigame implements IMinigame {

    private String identifier;
    private String name;
    private int maxPlayers;
    private int minPlayers;
    private List<Player> participants;
    private MinigameStatus status;
    private IArena arena;
    private MinigameResult result;
    private int id;
    private long startTime;
    private int startLeftTime;
    private BukkitTask bukkitTask;
    private MinigameScoreboardManager pregameScoreboard;

    /**
     * {@link AbstractMinigame} is a abstract class to make a minigame easier.
     * <p>
     * Methods to be declared in class (override methods):
     * - {@link IMinigame#initialize(int)}
     * - {@link IMinigame#start()}
     * - {@link IMinigame#join(Player)}
     * <p>
     * Things to be declared in constructor (use setters):
     * - identifier: String
     * - name: String
     * - minPlayers: int
     * - maxPlayers: int
     */
    public AbstractMinigame() {
        participants = new ArrayList<>();
        status = MinigameStatus.WAITING;
        result = new MinigameResult();
        startLeftTime = Integer.MAX_VALUE;
    }

    @Override
    public void initConstructor() {
        pregameScoreboard = new MinigameScoreboardManager(name, 4);
        pregameScoreboard.setSlot(DisplaySlot.SIDEBAR);
        pregameScoreboard.setText(1, ChatColor.GRAY + "ID: " + id);
        pregameScoreboard.setText(3, "기다리는 중...");

        bukkitTask = new BukkitRunnable() {
            @Override
            public void run() {
                if (status.equals(MinigameStatus.WAITING)) {
                    pregameScoreboard.setText(3, "기다리는 중...");
                } else if (status.equals(MinigameStatus.READY)) {
                    startLeftTime--;

                    pregameScoreboard.setText(3, ChatColor.AQUA + Integer.toString(startLeftTime) + ChatColor.YELLOW + "초 후 시작");

                    if (startLeftTime <= 5 && startLeftTime > 0) {
                        sendUniverseMessage(ChatColor.YELLOW + "게임이 " + ChatColor.AQUA + startLeftTime
                                + ChatColor.YELLOW + "초 후 시작됩니다.");
                    }

                    if (startLeftTime <= 0) {
                        start();
                        status = MinigameStatus.STARTED;
                        cancel();
                    }
                } else if (status.equals(MinigameStatus.STARTED) || status.equals(MinigameStatus.ENDED)) {
                    cancel();
                }
            }
        }.runTaskTimer(MinigameCore.getInstance(), 0, 20L);
    }

    @Override
    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int getMaxPlayers() {
        return maxPlayers;
    }

    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    @Override
    public int getMinPlayers() {
        return minPlayers;
    }

    public void setMinPlayers(int minPlayers) {
        this.minPlayers = minPlayers;
    }

    @Override
    public List<Player> getParticipants() {
        return participants;
    }

    @Override
    public void setParticipants(List<Player> participants) {
        this.participants = participants;
    }

    @Override
    public void addParticipant(Player participant) {

        participant.setFoodLevel(20);
        participant.setSaturation(20);
        participant.setHealth(20);
        participant.setInvulnerable(false);
        participant.setAllowFlight(false);
        participant.setGameMode(GameMode.ADVENTURE);
        participant.setScoreboard(pregameScoreboard.getScoreboard());

        this.participants.add(participant);
        this.join(participant);
        MinigameCore.getInstance().getPlayerManager().addParticipated(participant, getId());

        this.sendUniverseMessage(participant.getDisplayName() + ChatColor.GREEN + "이(가) 게임에 참여했습니다. (" +
                ChatColor.YELLOW + participants.size() + ChatColor.GREEN + "/" + ChatColor.YELLOW + maxPlayers
                + ChatColor.GREEN + ")");

        // TODO 플레이어 입장 시 시작 추가하기
        if (participants.size() >= maxPlayers) {
            setStartLeftTime(5, false);
            status = MinigameStatus.READY;

            sendUniverseMessage(ChatColor.YELLOW + "게임이 " + ChatColor.AQUA + startLeftTime
                    + ChatColor.YELLOW + "초 후 시작됩니다.");
        } else if (participants.size() >= maxPlayers * 0.5) {
            setStartLeftTime(15, false);
            status = MinigameStatus.READY;

            sendUniverseMessage(ChatColor.YELLOW + "게임이 " + ChatColor.AQUA + startLeftTime
                    + ChatColor.YELLOW + "초 후 시작됩니다.");
        } else if (participants.size() >= maxPlayers * 0.25) {
            setStartLeftTime(30, false);
            status = MinigameStatus.READY;

            sendUniverseMessage(ChatColor.YELLOW + "게임이 " + ChatColor.AQUA + startLeftTime
                    + ChatColor.YELLOW + "초 후 시작됩니다.");
        }
    }

    @Override
    public void removeParticipant(Player participant) {

        MinigameCore.getInstance().getPlayerManager().removeParticipated(participant);

        this.participants.remove(participant);
        this.sendUniverseMessage(participant.getDisplayName() + ChatColor.RED + "이(가) 게임에서 나갔습니다. (" +
                ChatColor.YELLOW + participants.size() + ChatColor.RED + "/" + ChatColor.YELLOW + maxPlayers
                + ChatColor.RED + ")");

        if (status.equals(MinigameStatus.STARTED))
            playerLeft(participant);

        if (participants.size() < minPlayers && status.equals(MinigameStatus.READY)) {
            setStartLeftTime(Integer.MAX_VALUE, true);
            status = MinigameStatus.WAITING;

            this.sendUniverseMessage(ChatColor.RED + "참여자가 부족하여 시작이 취소되었습니다.");
        }
    }

    @Override
    public void moveToLobby(Player participant) {
        participant.setFoodLevel(20);
        participant.setSaturation(100000);
        participant.setHealth(20);
        participant.setInvulnerable(true);
        participant.setAllowFlight(false);
        participant.setGameMode(GameMode.ADVENTURE);
        participant.setScoreboard(MinigameCore.getInstance().getLobbyScoreboard().getScoreboard());

        for(Player target : participants) {
            target.showPlayer(MinigameCore.getInstance(), participant);
        }
        participant.teleport(Constant.LOBBY);
    }

    @Override
    public MinigameStatus getStatus() {
        return status;
    }

    @Override
    public void setStatus(MinigameStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "AbstractMinigame(identifier=" + identifier + ", name=" + name + ", minPlayers="
                + minPlayers + ", maxPlayers=" + maxPlayers + ", participants=" + participants + ")";
    }

    @Override
    public void destroy(boolean forced) {

        if (forced)
            sendUniverseMessage(ChatColor.RED + "게임이 강제로 종료되었습니다.");
        else {
            sendUniverseMessage("");
            sendUniverseMessage(ChatColor.GOLD + "----------- [ 게임 결과 ] -----------");
            sendUniverseMessage("");
            sendUniverseMessage("              우승: " + result.getWinners().get(0).getName());
            sendUniverseMessage("");
            sendUniverseMessage(ChatColor.GOLD + "--------------------------------");
            sendUniverseMessage("");
        }

        new BukkitRunnable() {
            @Override
            public void run() {

                for (Player participant : participants) {
                    moveToLobby(participant);
                }

                Iterator<Player> iter = participants.iterator();

                while (iter.hasNext()) {
                    Player participant = iter.next();

                    MinigameCore.getInstance().getPlayerManager().removeParticipated(participant);

                    iter.remove();
                }

                for (Player participant : participants) {
                    removeParticipant(participant);
                }

                status = MinigameStatus.ENDED;

                bukkitTask.cancel();

                arena.delete();

                MinigameCore.getInstance().getMinigameManager().removeListedMinigame(getId());
                System.out.println("Minigame id " + id + " has been ended");

            }
        }.runTaskLater(MinigameCore.getInstance(), forced ? 2L : 200L);
    }

    @Override
    public IArena getArena() {
        return arena;
    }

    @Override
    public void setArena(IArena arena) {
        this.arena = arena;
    }

    @Override
    public MinigameResult getResult() {
        return result;
    }

    @Override
    public void setResult(MinigameResult result) {
        this.result = result;
    }

    @Override
    public void sendUniverseMessage(String chat) {
        for (Player player : getParticipants()) {
            player.sendMessage(chat);
        }
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    abstract public void join(Player player);

    @Override
    public long getStartTime() {
        return startTime;
    }

    @Override
    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    @Override
    public int getStartLeftTime() {
        return startLeftTime;
    }

    @Override
    public void setStartLeftTime(int startLeftTime, boolean forced) {
        if (forced)
            this.startLeftTime = startLeftTime;
        else if (startLeftTime < this.startLeftTime) {
            this.startLeftTime = startLeftTime;
        }
    }

    abstract public void initialize(int id);

    abstract public void start();

    abstract public void playerLeft(Player participant);
}
