class InvalidPostfixException extends Exception {
    InvalidPostfixException(String s) {
        super(s);
    }
}

class InvalidExprException extends Exception {
    InvalidExprException(String s) {
        super(s);
    }
}

public class Calculator {
    int t;
    int t2;

    public int evaluatePostFix(String s) throws InvalidPostfixException {
        String[] sp = split(s);
        MyStack stack = new MyStack();
        try {
            for (int i = 0; i < t; i++) {
                if (sp[i].compareTo("*") == 0) {
                    int a = (int) stack.pop();
                    int b = (int) stack.pop();
                    stack.push(a * b);
                } else if (sp[i].compareTo("+") == 0) {
                    int a = (int) stack.pop();
                    int b = (int) stack.pop();
                    stack.push(a + b);
                } else if (sp[i].compareTo("-") == 0) {
                    int a = (int) stack.pop();
                    int b = (int) stack.pop();
                    stack.push(b - a);
                } else {
                    if (Integer.parseInt(sp[i]) < 0) {
                        throw new InvalidExprException("InvalidPostException");
                    }
                    stack.push(Integer.parseInt(sp[i]));
                }
            }
            int ans = (int) stack.pop();
            if (stack.isEmpty()) {
                return ans;
            } else {
                throw new InvalidPostfixException("InvalidPostfixException");
            }
        } catch (Exception e) {
            throw new InvalidPostfixException("InvalidPostfixException");
        }
    }

    private String[] split(String S) {
        String[] separate = new String[S.length()];
        t = 0;
        for (int i = 0; i < separate.length; i++) {
            separate[i] = "";
        }
        for (int i = 0; i < S.length(); i++, t++) {
            while (i < S.length() && S.charAt(i) == ' ') {
                i++;
            }
            while (i < S.length() && S.charAt(i) != ' ') {
                separate[t] += S.charAt(i);
                i++;
            }
        }
        if (S.length() >= 1 && separate[t - 1] == "") {
            t--;
        }
        return separate;
    }

    private String[] split_forconvertexpression(String s) {
        String[] st = new String[s.length()];
        for (int i = 0; i < st.length; i++) {
            st[i] = "";
        }
        t2 = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == ' ') {
                continue;
            }
            if (s.charAt(i) >= '0' && s.charAt(i) <= '9') {
                while (i < s.length() && s.charAt(i) >= '0' && s.charAt(i) <= '9') {
                    st[t2] += s.charAt(i);
                    i++;
                }
                i--;
            } else {
                st[t2] = Character.toString(s.charAt(i));
            }
            t2++;
        }
        return st;
    }

    public String convertExpression(String s) throws InvalidExprException {
        MyStack stack = new MyStack();
        String[] arr = split_forconvertexpression(s);
        String ans = "";
        try {
            for (int i = 0; i < t2; i++) {
                if (arr[i].compareTo("(") == 0 || arr[i].compareTo("*") == 0) {
                    if (i != 0 && arr[i].compareTo("(") == 0 && arr[i - 1].compareTo("*") != 0
                            && arr[i - 1].compareTo("(") != 0 && arr[i - 1].compareTo("+") != 0
                            && arr[i - 1].compareTo("-") != 0) {
                        stack.push("*");
                        System.out.println("executed");
                    }
                    stack.push(arr[i]);
                } else if (arr[i].compareTo(")") == 0) {
                    while (!stack.isEmpty() && String.valueOf(stack.top()).compareTo("(") != 0) {
                        ans += String.valueOf(stack.pop()) + " ";
                    }
                    stack.pop();
                } else if (arr[i].compareTo("+") == 0 || arr[i].compareTo("-") == 0) {
                    while (!stack.isEmpty() && String.valueOf(stack.top()).compareTo("(") != 0) {
                        ans += String.valueOf(stack.pop()) + " ";
                    }
                    stack.push(arr[i]);
                } else {
                    ans += arr[i] + " ";
                }
            }
            while (!stack.isEmpty()) {
                ans += String.valueOf(stack.pop()) + " ";
            }
            int a = evaluatePostFix(ans);
        } catch (Exception e) {
            throw new InvalidExprException("InvalidExprException");
        }
        return ans.substring(0, ans.length() - 1);
    }
}