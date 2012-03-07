import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class BestBefore {
	public static void main(String[] args) throws IOException {
		BufferedReader stdin = new BufferedReader(new InputStreamReader(
				System.in));
		String input = stdin.readLine();
		String[] numbers = input.split("/");
		ArrayList<MyDate> dates = new ArrayList<MyDate>();
		for (int i = 0; i < 3; i++) {
			MyDate date1 = new MyDate(numbers[i], numbers[(i + 1) % 3],
					numbers[(i + 2) % 3]);
			if (date1.isValid()) {
				dates.add(date1);
			}
			MyDate date2 = new MyDate(numbers[i], numbers[(i + 2) % 3],
					numbers[(i + 1) % 3]);
			if (date2.isValid()) {
				dates.add(date2);
			}
		}
		MyDate best = null;
		for (MyDate myDate : dates) {
			if (best == null || myDate.isEarlierThan(best)) {
				best = myDate;
			}
		}
		System.out.println(best == null ? input + " is illegal" : best);
	}
}

class DateUtil {
	public static boolean isLeapYear(int year) {
		return ((year % 4 == 0) && !(year % 100 == 0)) || (year % 400 == 0);
	}

	public static int getDaysOfTheMonthInTheYear(int month, int year) {
		switch (month) {
		case 1:
		case 3:
		case 5:
		case 7:
		case 8:
		case 10:
		case 12:
			return 31;
		case 4:
		case 6:
		case 9:
		case 11:
			return 30;
		case 2:
			return isLeapYear(year) ? 29 : 28;
		default:
			return -1;
		}
	}
}

class MyDate {
	public MyDate(String year, String month, String day) {
		this.year = Integer.parseInt(year) + (year.length() == 4 ? 0 : 2000);
		this.month = Integer.parseInt(month);
		this.day = Integer.parseInt(day);
	}

	int year;
	int month;
	int day;

	public int getYear() {
		return this.year;
	}

	public int getMonth() {
		return this.month;
	}

	public int getDay() {
		return this.day;
	}

	public boolean isValid() {
		return (this.year >= 2000 && this.year < 3000)
				&& (this.day > 0 && this.day <= DateUtil
						.getDaysOfTheMonthInTheYear(this.month, this.year));
	}

	public boolean isEarlierThan(MyDate myDate) {
		return this.year < myDate.getYear()
				|| (this.year == myDate.getYear() && (this.month < myDate
						.getMonth() || (this.year == myDate.getYear()
						&& this.month == myDate.getMonth() && this.day < myDate
						.getDay())));
	}

	@Override
	public String toString() {
		return this.year + "-" + (this.month < 10 ? "0" : "") + this.month
				+ "-" + (this.day < 10 ? "0" : "") + this.day;
	}

}