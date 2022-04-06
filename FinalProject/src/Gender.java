public enum Gender {
    MALE("male"),FEMALE("female"),UNKNOWN("unknown");

    private String text;

    Gender(String text){
        this.text=text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}

