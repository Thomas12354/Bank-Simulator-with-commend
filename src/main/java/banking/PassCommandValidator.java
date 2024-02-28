package banking;

public class PassCommandValidator extends CommandValidator {

	public PassCommandValidator(Bank bank) {
		super(bank);
	}

	@Override
	public boolean validate(String input) {
		int time;
		inputArray = inputProcess(input);

		if (isExtraSpace(inputArray) || !isEnoughInput(inputArray) || !isNumberString(inputArray[1])) {
			return false;
		}

		time = setTime();

		return time >= 1 && time <= 60;

	}

	private int setTime() {

		return Integer.parseInt(inputArray[1]);
	}
}
