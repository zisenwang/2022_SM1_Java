public enum AgeCategory {
    BABY("baby "),CHILD("child "),ADULT("adult "),SENIOR("senior ");

    private String text;

    AgeCategory(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
