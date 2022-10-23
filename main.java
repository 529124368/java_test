class Man {
    private String name;
    private Integer age;

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }
}

public class main {
    public static void main(String[] args) {
        System.out.println("Hello world");
        Man man = new Man();
        man.setAge(10);
        man.setName("llll");
        Tools to = new Tools();
        to.setAge(123);
        System.out.print(man.getAge());
        System.out.print(to.getAge());
    }
}