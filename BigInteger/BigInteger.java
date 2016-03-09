public class BigInteger {
    private int[] bigIntArray;
    private int sign;
    public static final BigInteger ZERO = new BigInteger("0");
    public static final BigInteger ONE = new BigInteger("1");
    public static final BigInteger TEN = new BigInteger("10");

    public BigInteger(String val) {
        String trimmedVal = val.trim();

        trimmedVal = trimmedVal.replaceFirst("^0+(?!$)", "");

        if (trimmedVal.substring(0,1).equals("+")) {
            this.sign = 1;
            trimmedVal = trimmedVal.substring(1);
        } else if (trimmedVal.substring(0,1).equals("-")) {
            this.sign = -1;
            trimmedVal = trimmedVal.substring(1);
        } else {
            this.sign = 1;
        }

        for (int i = 0; i < trimmedVal.length(); i++) {
            try {
                String stringOfChar = Character.toString(trimmedVal.charAt(i));
                Integer.parseInt(stringOfChar);
            } catch (Exception e) {
                throw new IllegalArgumentException();
            }
        }

        if (trimmedVal.equals("0")) {
            this.sign = 0;
        }

        int length = trimmedVal.length();
        this.bigIntArray = new int[length];
        int position = length - 1;
        for (int i = 0; i < length; i++) {
            this.bigIntArray[position] = Character.getNumericValue(trimmedVal.charAt(i));
            position--;
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
        return this.toString().substring(0,1).equals("-") ? new BigInteger(this.toString().substring(1)) : new BigInteger(this.toString());
    }

    public BigInteger divideByTwo() {
        BigInteger result = new BigInteger(this.toString());
        if (result.isOdd()) {
            result.bigIntArray[0]--;
        }

        for (int i = 0; i < result.bigIntArray.length; i++) {
            try {
                if (result.bigIntArray[i + 1] % 2 != 0) {
                    result.bigIntArray[i] = (result.bigIntArray[i] + 10) / 2;
                } else {
                    result.bigIntArray[i] /= 2;
                }
            } catch (Exception e) {
                result.bigIntArray[i] /= 2;
            }
        }

        return result;
    }

    public BigInteger multiplyByTwo() {
        BigInteger result = this.add(this);
        return result;
    }

    public boolean isOdd() {
        return this.bigIntArray[0] % 2 != 0;
    }

    public BigInteger add(BigInteger val) {
        String result = "";

        BigInteger thisAbs = this.abs();
        BigInteger valAbs = val.abs();
        if (this.sign < 0) {
            return val.subtract(thisAbs);
        } else if (val.sign < 0) {
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

        result = reverse(result);
        return new BigInteger(result);
    }

    public BigInteger subtract(BigInteger val) {
        String result = "";

        BigInteger thisAbs = this.abs();
        BigInteger valAbs = val.abs();
        if (val.sign < 0) {
            return this.add(valAbs);
        } else if (val.sign > 0) {
            if (this.sign < 0) {
                return new BigInteger("-" + thisAbs.add(valAbs).toString());
            } else {
                if (this.compareTo(val) < 0) {
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
            result += Integer.toString(this.bigIntArray[i]);
        }
        if (this.sign < 0) {
            result = "-" + result;
        }
        return result;
    }

    public int compareTo(BigInteger val) {
        int firstLength = this.bigIntArray.length;
        int secondLength = val.bigIntArray.length;
        if (this.sign < val.sign) {
            return -1;
        } else if (this.sign > val.sign) {
            return 1;
        } else {
            if (this.sign == 1) {
                if (firstLength < secondLength) {
                    return -1;
                } else if (firstLength > secondLength) {
                    return 1;
                } else {
                    for (int i = firstLength - 1; i >= 0; i--) {
                        if (this.bigIntArray[i] < val.bigIntArray[i]) {
                            return -1;
                        } else if (this.bigIntArray[i] > val.bigIntArray[i]) {
                            return 1;
                        } else {
                            continue;
                        }
                    }
                    return 0;
                }
            } else {
                if (firstLength > secondLength) {
                    return -1;
                } else if (firstLength < secondLength) {
                    return 1;
                } else {
                    for (int i = firstLength - 1; i >= 0; i--) {
                        if (this.bigIntArray[i] > val.bigIntArray[i]) {
                            return -1;
                        } else if (this.bigIntArray[i] < val.bigIntArray[i]) {
                            return 1;
                        } else {
                            continue;
                        }
                    }
                    return 0;
                }
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

    /*public BigInteger divide(BigInteger val) {
        BigInteger result = new BigInteger(this.toString());
        BigInteger divisor = new BigInteger(val.toString());
        for (divisor; divisor > 1; divisor = divisor.divideByTwo()) {
            this.divideByTwo();
        }

        if (divisor.isOdd()) {
            divisor.multiplyByTwo();
            result.multiplyByTwo();
        }
    }*/

    /*public BigInteger remainder(BigInteger val) {

    }*/
}
