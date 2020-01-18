package me.nuty.minigamecore.minigame;

import me.nuty.minigamecore.arena.IArena;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

public interface IMinigame {

    /**
     * Returns the identifier of minigame.
     *
     * @return the indetifer
     */
    String getIdentifier();

    /**
     * Returns the name of minigame.
     *
     * @return the name
     */
    String getName();

    /**
     * Returns the maximum player of minigame.
     *
     * @return maxPlayers
     */
    int getMaxPlayers();

    /**
     * Returns the minimum player of minigame.
     *
     * @return minPlayers
     */
    int getMinPlayers();

    /**
     * Returns participants of minigame.
     *
     * @return participants
     */
    List<Player> getParticipants();

    void setParticipants(List<Player> participants);

    void addParticipant(Player participant);

    void removeParticipant(Player participant);

    /**
     * Returns the status of minigame.
     *
     * @return status
     */
    MinigameStatus getStatus();

    void setStatus(MinigameStatus status);

    /**
     * Returns the arena currently game is played
     *
     * @return arena
     */
    IArena getArena();

    void setArena(IArena arena);

    /**
     * Returns the result of minigame.
     *
     * @return result
     */
    MinigameResult getResult();

    void setResult(MinigameResult result);

    /**
     * Returns the start time of minigame. -1 if waiting for players.
     *
     * @return startTime
     */
    long getStartTime();

    void setStartTime(long startTime);

    int getStartLeftTime();

    void setStartLeftTime(int startLeftTime, boolean forced);

    /**
     * Initialize the minigame.
     * Called when '/minigame add [id]' is executed.
     */
    void initialize(int id);

    /**
     * Starts the minigame.
     * Called when leftStartTime is 0.
     */
    void start();

    /**
     * Ends the minigame and returns result of minigame.
     *
     * @return gameResult
     */
    void destroy(boolean forced);

    /**
     * Called when player joins minigame.
     *
     * @param participant a player who joined the minigame
     */
    void join(Player participant);

    void sendUniverseMessage(String chat);

    /**
     * Returns the id of minigame
     *
     * @return id
     */
    int getId();
    void setId(int id);

    public void moveToLobby(Player participant);
}
