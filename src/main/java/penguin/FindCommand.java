package penguin;

import java.util.ArrayList;
import java.util.List;

public class FindCommand extends Command {
    public FindCommand(String input) {
        super(input);
    }

    @Override
    public boolean execute(TaskList tasks, Ui ui, Storage storage) throws PenguinException {
        String keyword = input.length() >= 5 ? input.substring(5).trim() : "";
        if (keyword.isEmpty()) {
            throw new PenguinException("Format: find <keyword>");
        }

        String lowered = keyword.toLowerCase();
        List<Task> matches = new ArrayList<>();
        List<Integer> indices = new ArrayList<>(); // 1-based

        List<Task> all = tasks.getTasks();
        for (int i = 0; i < all.size(); i++) {
            Task t = all.get(i);
            if (t.getDescription().toLowerCase().contains(lowered)) {
                matches.add(t);
                indices.add(i + 1);
            }
        }

        ui.showMatches(matches, indices);
        return false;
    }
}
