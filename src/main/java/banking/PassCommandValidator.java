package banking;

public class PassCommandValidator extends CommandValidator {

	public static final int MIN_MONTH = 1;
	public static final int MAX_MONTH = 60;

	public PassCommandValidator(Bank bank) {
		super(bank);
	}

	@Override
	public boolean validate(String input) {
		int time;
		inputArray = inputProcess(input);

		if (isExtraSpace(inputArray) || !isEnoughInput(inputArray)) {
			return false;
		}

		if (!isNumberString(inputArray[1])) {
			return false;
		}
		time = setTime();

		return time >= MIN_MONTH && time <= MAX_MONTH;

	}

	private int setTime() {

		return Integer.parseInt(inputArray[1]);
	}
}
