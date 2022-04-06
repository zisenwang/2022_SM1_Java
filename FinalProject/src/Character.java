abstract public class Character {
    private int age;
    private Gender gender;
    private BodyType bodyType;
    private EthicalEngine.Decision role;

    public Character(){
        this.age = 0;
        this.gender = Gender.UNKNOWN;
        this.bodyType = BodyType.UNSPECIFIED;
    }

    public void setAge(int age){
        if (age >= 0){this.age = age;}
        // TODO: May throw an exception here
    }
    public int getAge(){
        return this.age;
    }

    public void setBodyType(BodyType bodyType) {
        this.bodyType = bodyType;
    }

    public BodyType getBodyType() {
        return bodyType;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Gender getGender(){
        return this.gender;
    }


}
