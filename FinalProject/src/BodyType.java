public enum BodyType {
    AVERAGE("average "), ATHLETIC("athletic "), OVERWEIGHT("overweight "),
    UNSPECIFIED("unspecified ");

    private String text;

    BodyType(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}

