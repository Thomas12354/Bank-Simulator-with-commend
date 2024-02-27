import java.util.ArrayList;

public class CommandStorage {
	private ArrayList<String> invalidStorage;

	CommandStorage() {
		invalidStorage = new ArrayList<>();
	}

	public void addInvalidCommand(String input) {
		invalidStorage.add(input);
	}

	public ArrayList getInvalidCommands() {
		return invalidStorage;
	}
}
