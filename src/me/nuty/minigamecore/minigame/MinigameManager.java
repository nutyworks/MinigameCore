package me.nuty.minigamecore.minigame;

import java.util.HashMap;
import java.util.Map;

public class MinigameManager {
    private Map<String, Class<? extends IMinigame>> registeredMinigames = new HashMap<>();
    private Map<Integer, IMinigame> listedMinigames = new HashMap<>();

    private int nextMinigameID = 0;

    /**
     * Registers minigame to MinigameManager (for server-side)
     *
     * @param identifier identifier(string) of minigame
     * @param minigame   class of minigame
     */
    public void registerMinigame(String identifier, Class<? extends IMinigame> minigame) {
        registeredMinigames.put(identifier, minigame);

        System.out.println(minigame.getName() + " added");
    }

    /**
     * Queue the minigame from its class (for ingame)
     *
     * @param identifier minigame string id
     * @return IMinigame object
     */
    public IMinigame listMinigame(String identifier) {

        Class<? extends IMinigame> minigameClass;
        IMinigame minigame;

        if (registeredMinigames.containsKey(identifier)) {
            minigameClass = registeredMinigames.get(identifier);

            try {
                minigame = minigameClass.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();

                return null;
            }

        } else {
            return null;
        }

        listedMinigames.put(nextMinigameID, minigame);

        minigame.initialize(nextMinigameID);

        nextMinigameID++;

        return minigame;
    }

    public void removeListedMinigame(int id) {
        listedMinigames.remove(id);
    }

    public Map<Integer, IMinigame> getListedMinigames() {
        return listedMinigames;
    }

    public IMinigame getListedMinigameByID(int id) {
        return listedMinigames.getOrDefault(id, null);
    }

    public Map<String, Class<? extends IMinigame>> getRegisteredMinigames() {
        return registeredMinigames;
    }
}
