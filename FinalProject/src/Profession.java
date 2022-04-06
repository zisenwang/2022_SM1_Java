public enum Profession {
    DOCTOR("doctor "),CEO("ceo "),CRIMINAL("criminal "),HOMELESS("homeless "),
    UNEMPLOYED("unemployed "),TEACHER("teacher "),ENGINEER("engineer "),NONE("none ");

    private String text;

    Profession(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
