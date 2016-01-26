public class DateDistance {
	public static void main(String[] args) {
		long result = (distance(Long.parseLong(args[0], 10), 
								Long.parseLong(args[1], 10),
								Long.parseLong(args[2], 10),
								Long.parseLong(args[3], 10),
								Long.parseLong(args[4], 10),
								Long.parseLong(args[5], 10)));
		if (result < 0) {
			System.out.println("One of your dates was formatted incorrectly. Please enter consecutive dates in the form Month Day Year");
		} else {
			System.out.println(result);
		}
	}

	public static boolean isCommonYear(long year) {
		return !((year % 4 == 0) && (year % 100 != 0) || (year % 400 == 0));
	}

	//Formula crafted by Curtis McEnroe.  Genius way of compressing a rather larger switch statement into one line of code.
	//https://cmcenroe.me/2014/12/05/days-in-month-formula.html
	public static long monthLength(long month, long year) {
		switch ((int) month) {
			case 1: return 31;
			case 2: 
				if (isCommonYear(year)) {
					return 28;
				} else {
					return 29;
				}
			case 3: return 31;
			case 4: return 30;
			case 5: return 31;
			case 6: return 30;
			case 7:
			case 8: return 31;
			case 9: return 30;
			case 10: return 31;
			case 11: return 30;
			case 12: return 31;
			default: return -1;
		}
	}

	public static boolean isRealDate(long month, long day, long year) {
		return (year >= 0 && month > 0 && month <= 12 && day > 0 && day <= monthLength(month, year));
	}

	public static long distance(long month0, long day0, long year0, long month1, long day1, long year1) {
		if (!isRealDate(month0, day0, year0) || !isRealDate(month1, day1, year1)) {
			return -1;
		}
		
		int totalDays = 0;
		long firstMonth = 0, firstDay = 0, firstYear = 0, secondMonth = 0, secondDay = 0, secondYear = 0;
		

		//Because the user is allowed to enter dates in any order, the dates must then be ordered chronologically
		if (year0 < year1 || (year0 == year1 && month0 < month1) || (year0 == year1 && month0 == month1 && day0 < day1)) {
			firstMonth = month0;
			secondMonth = month1;
			firstDay = day0;
			secondDay = day1;
			firstYear = year0;
			secondYear = year1;
		} else if (year1 < year0 || (year0 == year1 && month1 < month0) || (year0 == year1 && month0 == month1 && day1 < day0)) {
			firstMonth = month1;
			secondMonth = month0;
			firstDay = day1;
			secondDay = day0;
			firstYear = year1;
			secondYear = year0;
		}
		
		//Determine distance by counting upwards from the first date
		//Cases where years are equal
		if (firstYear == secondYear && firstMonth == secondMonth && firstDay == secondDay) {
			return 0;
		} else if (firstYear == secondYear && firstMonth == secondMonth) {
			for (long i = firstDay; i < secondDay; i++) {
				totalDays++;
			}
			return totalDays;
		} else if (firstYear == secondYear) {
			for (long i = firstDay; i <= monthLength(firstMonth, firstYear); i++) {
				totalDays++;
			}
			for (long i = firstMonth + 1; i < secondMonth; i++) {
				totalDays += monthLength(firstMonth, firstYear);
			}
			for (long i = 1; i < secondDay; i++) {
				totalDays++;
			}
			return totalDays;
		} else {
			for (long i = firstDay; i < monthLength(firstMonth, firstYear); i++) {
				totalDays++;
			}
			for (long i = firstMonth + 1; i <= 12; i++) {
				totalDays += monthLength(i, firstYear);
			}
			for (long i = firstYear + 1; i < secondYear; i++) {
				if (isCommonYear(i)) {
					totalDays += 365;
				} else {
					totalDays += 366;
				}
			}
			for (long i = 1; i < secondMonth; i++) {
				totalDays += monthLength(i, secondYear);
			}
			for (long i = 1; i <= secondDay; i++) {
				totalDays ++;
			}
		}
		return totalDays;
	}
}
