public class Human extends Character{
    private AgeCategory ageCategory;
    private Profession profession;
    private boolean you;
    private boolean pregnant;

    public Human(){
        this.profession = Profession.NONE;
        this.pregnant = false;
        this.you = false;
    }

    @Override
    public void setAge(int age){
        super.setAge(age);
        this.setAgeCategory();
    }

    private void  setAgeCategory(){
        int a = this.getAge();
        if (a<=4) {this.ageCategory = AgeCategory.BABY;}
        else if (a<=16){this.ageCategory = AgeCategory.CHILD;}
        else if (a<=68){this.ageCategory = AgeCategory.ADULT;}
        else{this.ageCategory = AgeCategory.SENIOR;}
    }

    public void setProfession(Profession profession) {
        if (this.ageCategory == AgeCategory.ADULT){
            this.profession = profession;
        }
        // TODO: May throw an exception
    }

    public void setYou(boolean you, boolean repeated) {
        if (!(repeated)) {
            this.you = you;
        }
    }

    public void setPregnant(boolean pregnant) {
        if (this.ageCategory == AgeCategory.ADULT && this.getGender() == Gender.FEMALE){
            this.pregnant = pregnant;
        }
    }

    public AgeCategory getAgeCategory() {
        return ageCategory;
    }

    public Profession getProfession() {
        return profession;
    }

    public boolean isYou() {
        return you;
    }

    public boolean isPregnant() {
        return pregnant;
    }

    @Override
    public String toString(){
        String res = "";
        // [you]
        if (isYou()){res += "you ";}
        // <bodyType>
        res += this.getBodyType().getText();
        // <age category>
        res += this.getAgeCategory().getText();
        // [profession]
        if (this.getProfession() != Profession.NONE){res += this.getProfession().getText();}
        //  <gender>
        res += this.getGender().getText();
        // [pregnant]
        if (isPregnant()){res += " pregnant";}
        return res;
    }
    public static void main(String[] args){
        Human human = new Human();
        human.setAge(15);
        human.setGender(Gender.FEMALE);
        human.setBodyType(BodyType.ATHLETIC);
        human.setPregnant(true);
        human.setYou(true,false);
        System.out.println(human);
    }

}
