public class GenericResultDemo {

    static class Result<T> {
        private final boolean success;
        private final String  message;
        private final T       data;

        private Result(boolean success, String message, T data) {
            this.success = success;
            this.message = message;
            this.data    = data;
        }

        static <T> Result<T> ok(T data) {
            return new Result<>(true, "success", data);
        }

        static <T> Result<T> fail(String message) {
            return new Result<>(false, message, null);
        }

        boolean isSuccess() { return success; }
        String  getMessage() { return message; }
        T       getData()    { return data;    }

        @Override
        public String toString() {
            return "Result{success=" + success
                 + ", message='" + message + "'"
                 + ", data=" + data + "}";
        }
    }

    public static void main(String[] args) {
        Result<String>  nameResult  = Result.ok("Amy");
        Result<Integer> scoreResult = Result.ok(92);
        Result<String>  failResult  = Result.fail("找不到資料");

        System.out.println(nameResult);
        System.out.println(scoreResult);
        System.out.println(failResult);

        // 不需要 cast
        String  name  = nameResult.getData();
        Integer score = scoreResult.getData();
        System.out.println("name=" + name + " score=" + score);

        // 失敗時 data == null
        if (!failResult.isSuccess()) {
            System.out.println("失敗原因：" + failResult.getMessage());
            System.out.println("data=" + failResult.getData());
        }
    }
}