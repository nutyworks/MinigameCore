package me.nuty.minigamecore.event;

import me.nuty.minigamecore.MinigameCore;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.TabCompleteEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TabComplete implements Listener {
    @EventHandler
    public void onTabComplete(TabCompleteEvent event) {
        String[] args = event.getBuffer().split(" ");

        List<String> arg1Complete = new ArrayList<>(Arrays.asList("add", "destroy", "join", "help", "leave", "list"));
        List<String> completion = new ArrayList<>();

        if (event.getBuffer().equals("/game "))
            completion.addAll(arg1Complete);
        else if (args[0].equals("/game") && args.length == 2 && !event.getBuffer().endsWith(" ")) {
            for (String c : arg1Complete) {
                if (c.startsWith(args[1]))
                    completion.add(c);
            }
        } else if(event.getBuffer().equals("/game add ")) {
            completion.addAll(MinigameCore.getInstance().getMinigameManager().getRegisteredMinigames().keySet());
        } else if(args[0].equals("/game") && args[1].equals("add") && args.length == 3 && !event.getBuffer().endsWith(" ")) {
            for (String c : MinigameCore.getInstance().getMinigameManager().getRegisteredMinigames().keySet()) {
                if (c.startsWith(args[2]))
                    completion.add(c);
            }
        }

        event.setCompletions(completion);
    }
}
