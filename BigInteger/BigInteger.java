public class BigInteger {
    private int[] bigIntArray;
    private int sign;
    public static final BigInteger ZERO = new BigInteger("0");
    public static final BigInteger ONE = new BigInteger("1");
    public static final BigInteger TEN = new BigInteger("10");

    public BigInteger(String val) {
        String trimmedVal = val.trim();

        for (int i = 0; i < trimmedVal.length(); i++) {
            try {
                if (!(trimmedVal.charAt(i) == '-') && !(trimmedVal.charAt(i) == '+')) {
                    String stringOfChar = Character.toString(trimmedVal.charAt(i));
                    Integer.parseInt(stringOfChar);
                }
            } catch (Exception e) {
                throw new IllegalArgumentException();
            }
        }

        if (trimmedVal.substring(0,1).equals("+")) {
            this.sign = 1;
            trimmedVal = trimmedVal.substring(1);
        } else if (trimmedVal.substring(0,1).equals("-")) {
            this.sign = -1;
            trimmedVal = trimmedVal.substring(1);
        } else {
            this.sign = 1;
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

        if (result.bigIntArray[0] % 2 != 0) {
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

    public BigInteger add(BigInteger val) {
        String result = "";

        BigInteger thisAbs = this.abs();
        BigInteger valAbs = val.abs();
        if (this.abs().compareTo(val.abs()) < 0) {
            if (this.sign < 0 && val.sign > 0) {
                return val.subtract(thisAbs);
            } else if (this.sign > 0 && val.sign < 0) {
                return new BigInteger("-" + valAbs.subtract(this).toString());
            } else if (this.sign < 0 && val.sign < 0) {
                return new BigInteger("-" + thisAbs.add(valAbs).toString());
            }
        } else if (this.abs().compareTo(val.abs()) > 0) {
            if (this.sign < 0 && val.sign > 0) {
                return new BigInteger("-" + thisAbs.subtract(val).toString());
            } else if (this.sign > 0 && val.sign < 0) {
                return this.subtract(valAbs);
            } else if (this.sign < 0 && val.sign < 0) {
                return new BigInteger("-" + thisAbs.add(valAbs).toString());
            }
        } else {
            if (this.sign < 0 && val.sign > 0) {
                return this.ZERO;
            } else if (this.sign > 0 && val.sign < 0) {
                return this.ZERO;
            }
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
        if (this.abs().compareTo(val.abs()) < 0) {
            if (this.sign > 0 && val.sign > 0) {
                return new BigInteger("-" + val.subtract(this).toString());
            } else if (this.sign < 0 && val.sign > 0) {
                return new BigInteger("-" + thisAbs.add(val).toString());
            } else if (this.sign > 0 && val.sign < 0) {
                return this.add(valAbs);
            } else if (this.sign < 0 && val.sign < 0) {
                return new BigInteger(valAbs.subtract(thisAbs).toString());
            }
        } else if (this.abs().compareTo(val.abs()) > 0) {
            if (this.sign < 0 && val.sign > 0) {
                return new BigInteger("-" + thisAbs.add(val).toString());
            } else if (this.sign > 0 && val.sign < 0) {
                return this.add(valAbs);
            } else if (this.sign < 0 && val.sign < 0) {
                return new BigInteger("-" + thisAbs.subtract(valAbs).toString());
            }
        } else {
            if (this.sign < 0 && val.sign > 0) {
                return new BigInteger("-" + thisAbs.add(val).toString());
            } else if (this.sign > 0 && val.sign < 0) {
                return new BigInteger(valAbs.add(this).toString());
            } else {
                return this.ZERO;
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
        result = result.replaceFirst("^0+(?!$)", "");
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

    /*public BigInteger multiply(BigInteger val) {

    }

    public BigInteger divide(BigInteger val) {

    }

    public BigInteger remainder(BigInteger val) {

    }*/
}
