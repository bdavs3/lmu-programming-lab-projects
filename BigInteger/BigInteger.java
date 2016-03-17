public class BigInteger {
    private int[] bigIntArray;
    private int sign;
    public static final BigInteger ZERO = new BigInteger("0");
    public static final BigInteger ONE = new BigInteger("1");
    public static final BigInteger TEN = new BigInteger("10");

    public BigInteger(String val) {
        String trimmedVal = val.trim();

        trimmedVal = trimmedVal.replaceFirst("^0+(?!$)", "");   //Regular expression for removing leading zeroes from the input

        if (trimmedVal.substring(0,1).equals("+")) {
            this.sign = 1;
            trimmedVal = trimmedVal.substring(1);
        } else if (trimmedVal.substring(0,1).equals("-")) {
            this.sign = -1;
            trimmedVal = trimmedVal.substring(1);
        } else if (trimmedVal.equals("0")) {
            this.sign = 0;
        } else {
            this.sign = 1;
        }

        int length = trimmedVal.length();
        this.bigIntArray = new int[length];
        int position = length - 1;
        for (int i = 0; i < length; i++) {
            try {
                String stringOfChar = Character.toString(trimmedVal.charAt(i));                 //Cannot simply use Character.getNumericValue(char)
                Integer.parseInt(stringOfChar);                                                 //because alphabet characters (e.g. a,b,c) have integer
                this.bigIntArray[position] = Character.getNumericValue(trimmedVal.charAt(i));   //values (10,11,12)... so chars must be convertered
                position--;                                                                     //before it is determined whether they are numerical digits
            } catch (Exception e) {
                throw new IllegalArgumentException();
            }
        }
    }

    public String reverse(String str) {
        String reversedString = "";
        for (int i = str.length() - 1; i >= 0; i--) {
            reversedString += str.charAt(i);
        }
        return reversedString;
    }

    public BigInteger abs() {
        return this.sign < 0 ? new BigInteger(this.toString().substring(1)) : new BigInteger(this.toString());
    }

    public boolean isOdd() {
        return this.bigIntArray[0] % 2 != 0;
    }

    /*Here, there are two main steps in BigInteger addition... First, columns are added a number of times equal to the
    smaller length BigInteger.  Then, the leftover columns from the larger length BigInteger are combined with any residual
    carryOver and represented in the result.*/
    public BigInteger add(BigInteger val) {
        String result = "";

        BigInteger thisAbs = this.abs();
        BigInteger valAbs = val.abs();
        if (this.sign < 0) {
            return val.subtract(thisAbs);   //This portion interacts with the subtract method in order to account for the
        } else if (val.sign < 0) {          //various signs / magnitudes of the two BigIntegers in question
            return this.subtract(valAbs);
        }

        int smallerLength = this.bigIntArray.length < val.bigIntArray.length ? this.bigIntArray.length : val.bigIntArray.length;
        int largerLength = this.bigIntArray.length > val.bigIntArray.length ? this.bigIntArray.length : val.bigIntArray.length;

        int carryOver = 0;

        for (int i = 0; i < smallerLength; i++) {
            int addedColumn = this.bigIntArray[i] + val.bigIntArray[i] + carryOver;
            carryOver = 0;
            if (addedColumn <= 9) {
                result += Integer.toString(addedColumn);
            } else {
                result += Integer.toString(addedColumn - 10);
                if (i + 2 > largerLength) {
                    result += "1";
                } else {
                    carryOver++;
                }
            }
        }

        for (int i = smallerLength; i < largerLength; i++) {
            int addedColumn;
            try {
                addedColumn = this.bigIntArray[i] + carryOver;
            } catch (Exception e) {
                addedColumn = val.bigIntArray[i] + carryOver;
            }
            carryOver = 0;
            if (addedColumn <= 9) {
                result += Integer.toString(addedColumn);
            } else {
                result += Integer.toString(addedColumn - 10);
                if (i + 2 > largerLength) {
                    result += "1";
                } else {
                    carryOver++;
                }
            }
        }

        result = reverse(result);       //Because bigIntArrays are stored backwards with respect to the integer in question
        return new BigInteger(result);
    }

    //Subtraction works the same as addition (see above)
    public BigInteger subtract(BigInteger val) {
        String result = "";

        BigInteger thisAbs = this.abs();
        BigInteger valAbs = val.abs();
        if (val.sign < 0) {
            return this.add(valAbs);
        } else if (val.sign > 0) {
            if (this.sign < 0) {
                return new BigInteger("-" + thisAbs.add(valAbs).toString());       //Continued logic from the addition() method
            } else {                                                               //which altogether accounts for various signs
                if (this.compareTo(val) < 0) {                                     //and magnitudes of the two BigIntegers
                    return new BigInteger("-" + val.subtract(this).toString());
                }
            }
        }

        int smallerLength = this.bigIntArray.length < val.bigIntArray.length ? this.bigIntArray.length : val.bigIntArray.length;
        int largerLength = this.bigIntArray.length > val.bigIntArray.length ? this.bigIntArray.length : val.bigIntArray.length;

        int carryOver = 0;
        for (int i = 0; i < smallerLength; i++) {
            int subtractedColumn = this.bigIntArray[i] - val.bigIntArray[i] - carryOver;
            carryOver = 0;
            if (subtractedColumn >= 0) {
                result += Integer.toString(subtractedColumn);
            } else if (subtractedColumn < 0) {
                result += Integer.toString(10 + subtractedColumn);
                carryOver++;
            }
        }

        for (int i = smallerLength; i < largerLength; i++) {
            int addedColumn;
            try {
                addedColumn = this.bigIntArray[i] - carryOver;
            } catch (Exception e) {
                addedColumn = val.bigIntArray[i] - carryOver;
            }
            carryOver = 0;
            if (addedColumn >= 0) {
                result += Integer.toString(addedColumn);
            } else {
                result += Integer.toString(addedColumn + 10);
                if (i + 2 > largerLength) {
                    result += "1";
                } else {
                    carryOver++;
                }
            }
        }

        if (this.compareTo(val) < 0) {
            result += "-";
        }

        result = reverse(result);
        return new BigInteger(result);
    }

    public String toString() {
        String result = "";
        int length = this.bigIntArray.length;
        for (int i = length - 1; i >= 0; i--) {
            result += Integer.toString(this.bigIntArray[i]);    //No need to reverse the result in this method,
        }                                                       //because the "for" loop counts down instead of up
        if (this.sign < 0) {
            result = "-" + result;
        }
        return result;
    }

    //First compares the signs, then the lengths (if needed), and finally the actually digits (rarely needed)
    public int compareTo(BigInteger val) {
        int firstLength = this.bigIntArray.length;
        int secondLength = val.bigIntArray.length;
        if (this.sign < val.sign) {
            return -1;
        } else if (this.sign > val.sign) {
            return 1;
        } else {
            if (firstLength < secondLength) {
                return this.sign == 1 ? -1 : 1;
            } else if (firstLength > secondLength) {
                return this.sign == 1 ? 1 : -1;
            } else {
                for (int i = firstLength - 1; i >= 0; i--) {
                    if (this.bigIntArray[i] < val.bigIntArray[i]) {
                        return this.sign == 1 ? -1 : 1;
                    } else if (this.bigIntArray[i] > val.bigIntArray[i]) {
                        return this.sign == 1 ? 1 : 1;
                    }
                }
                return 0;
            }
        }
    }

    public boolean equals(Object x) {
        return (x instanceof BigInteger && this.toString().equals(((BigInteger) x).toString()));
    }

    public static BigInteger valueOf(long val) {
        String arg = Long.toString(val);
        return new BigInteger(arg);
    }

    /*This method works like traditional column-wise multiplication... the last digit of one number is multiplied by all the digits of the
    other number, then the result is stored in a "row", which is added to the result. 
    Then, the next digit of the first number is again multiplied by all the digits of the other number, etc.
    Note that a "padding" of zeroes is necessary each time a new digit from the first number is being multiplied because the "row"
    starts with the 1's place, moves on the 10's place, 100's place, etc.*/
    public BigInteger multiply(BigInteger val) {
        BigInteger result = this.ZERO;
        int smallerLength = this.bigIntArray.length < val.bigIntArray.length ? this.bigIntArray.length : val.bigIntArray.length;
        int largerLength = this.bigIntArray.length > val.bigIntArray.length ? this.bigIntArray.length : val.bigIntArray.length;

        for (int i = 0; i < smallerLength; i++) {
            String row = "";

            char[] array = new char[i];
            for (int j = 0; j < array.length; j++) {
                array[j] = '0';
            }
            String padding = new String(array);
            row += padding;

            int carryOver = 0;
            for (int k = 0; k < largerLength; k++) {
                int multipliedColumn;
                if (smallerLength == this.bigIntArray.length) {
                    multipliedColumn = this.bigIntArray[i] * val.bigIntArray[k] + carryOver;
                } else {
                    multipliedColumn = val.bigIntArray[i] * this.bigIntArray[k] + carryOver;
                }
                carryOver = multipliedColumn / 10;
                row += String.valueOf(multipliedColumn % 10);
                if (k + 2 > largerLength) {
                    row += String.valueOf(carryOver);
                }
            }
            row = reverse(row);
            result = result.add(new BigInteger(row));
        }

        result.sign = this.sign * val.sign;
        return result;
    }

    public BigInteger divide(BigInteger val) {
        BigInteger thisAbs = this.abs();
        BigInteger valAbs = val.abs();
        if (valAbs.compareTo(thisAbs) > 0) {
            return this.ZERO;
        }

        if (val.equals(this.ZERO)) {
            throw new ArithmeticException();    //Accounts for the infinite case
        }

        BigInteger result = this.ONE;
        BigInteger intermediate = new BigInteger(valAbs.toString());

        while (intermediate.multiply(this.TEN).compareTo(thisAbs) < 0) {
            result = result.multiply(this.TEN);
            intermediate = intermediate.multiply(this.TEN);
        }

        
        result = result.add(thisAbs.subtract(intermediate).divide(valAbs));
        result.sign = this.sign * val.sign;
        return result;
    }

    public BigInteger remainder(BigInteger val) {
        BigInteger result = this.divide(val);
        result = result.multiply(val);
        result = result.abs();
        result = this.abs().subtract(result);
        return result;
    }
}
