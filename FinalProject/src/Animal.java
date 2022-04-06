

public class Animal extends Character{
    private String species;
    private boolean pet;

    public Animal(){
        this.pet = false;
        this.species = "dog";
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public void setPet(boolean pet) {
        this.pet = pet;
    }

    public String getSpecies() {
        return species;
    }

    public boolean isPet() {
        return pet;
    }

    @Override
    public String toString() {
        String res ="";
        res += species;
        if (isPet()){
            res += " is pet";
        }
        return  res;
    }

    public static void main(String[] args){
        Animal animal = new Animal();
        animal.setPet(false);
        animal.setSpecies("dog");
        System.out.println(animal);
    }
}
